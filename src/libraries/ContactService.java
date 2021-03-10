package libraries;

import javax.security.auth.login.AccountLockedException;
import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

public class ContactService implements java.io.Serializable {

    public void printer(Collection contacts) {
        if (contacts.isEmpty()) {
            System.out.println("0 Contacts found!");

        }
        else {
            System.out.println("-----------------------------------");
            for (Object c: contacts) {
                System.out.println((Contact)c);
            }
            System.out.println("-----------------------------------");
        }
    }

    public void addContact(Contact contact, List<Contact> contacts) {
        contacts.add(contact);
    }

    public Contact getContactById(int contactId, List<Contact> contacts) throws ContactNotFoundException {
        for (Contact c: contacts) {
            if (c.getContactID() == contactId) {
                return c;
            }
        }
        throw new ContactNotFoundException();
    }

    public void addContactNumber(int contactId, String contactNo, List<Contact> contacts) throws ContactNotFoundException {
        getContactById(contactId, contacts).getContactNumber().add(contactNo);
    }

    public void removeContact(Contact contact, List<Contact> contacts) {
        contacts.remove(contact);
    }
    
    public Contact searchContactByName(String name, List<Contact> contact) throws ContactNotFoundException {
        for (Contact c: contact) {
            if (String.valueOf(c.getContactName()).equals(name)) {
                return c;
            }
        }
        throw new ContactNotFoundException();
    }

    public List<Contact> searchContactByNumber(String number, List<Contact> contact) throws ContactNotFoundException {
        List<Contact> reqResult = new ArrayList<>();
        for (Contact c: contact) {
            if (String.valueOf(c.getContactNumber()).contains(number)) {
                reqResult.add(c);
            }
        }
        if (reqResult.isEmpty()) {
            throw new ContactNotFoundException();
        }
        return reqResult;
    }

    public Contact createContact(int contactID, String ContactName, String email, List<String> contactNumber) {

        Contact c = new Contact();
        c.setContactID(contactID);
        c.setContactName(ContactName);
        c.setEmail(email);
        c.setContactNumber(contactNumber);

        return c;
    }

    public Contact readContact(String... params) {
        int contactID = Integer.parseInt(params[0]);
        String ContactName = params[1];
        String email = params[2];

        List<String> contactNumber = new ArrayList<>();
        for (int i=3; i<params.length; i++) {
            assert false;
            contactNumber.add(params[i]);
        }

        return createContact(contactID, ContactName, email, contactNumber);
    }

    public void readContactsFromFile(List<Contact> contacts, String fileName) throws FileNotFoundException {

        if (fileName.equals("d")) fileName = Paths.get("").toAbsolutePath().toString() + "\\db\\" + "Contact.txt";
        File file = new File(fileName);

        try (Scanner sc = new Scanner(file)) {
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                addContact(readContact(line.split(",")), contacts);
            }
            sc.close();
            System.out.println("Contacts added!");
        }
        catch (FileNotFoundException e) {
            System.out.println("Invalid File path!");
        }
    }

    public void sortContactsByName(List<Contact> contacts) {
        NameComparator nc = new NameComparator();
        System.out.println("Sorting Contacts by Name:");
        contacts.sort(nc);
    }

    public void serializeContactDetails(List<Contact> contacts , String filename) throws IOException {

        if (filename.equals("d")) filename = "SerializedData.s";

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(contacts);
            oos.flush();

            oos.close();
            fos.close();

            System.out.println("Movies Serialized to: " + filename);

        } catch (FileNotFoundException e) {
            System.out.println("Invalid File path!");
        }
    }

    public List<Contact> deserializeContact(String filename) throws IOException, ClassNotFoundException {

        if (filename.equals("d")) filename = "SerializedData.s";

        try (FileInputStream fis = new FileInputStream(filename)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<Contact> cons = (List<Contact>) ois.readObject();

            fis.close();
            ois.close();

            System.out.println("Movies Deserialized from: " + filename);
            return cons;

        } catch (FileNotFoundException e) {
            System.out.println("Invalid File path!");
            System.out.println("Retaining old contacts");
        }
        return null;
    }

    public Set<Contact> populateContactFromDb() throws SQLException, ClassNotFoundException {
        DbManager dm = new DbManager();
        dm.initTable();
        dm.populateDb();
        Set<Contact> cons = dm.readFromDb();
        dm.close();

        return cons;
    }

    public boolean addContacts(List<Contact> existingContact, Set<Contact> newContacts) {
        return existingContact.addAll(newContacts);
    }

    public static void main(String[] args) throws ContactNotFoundException, IOException, ClassNotFoundException, SQLException {
        ContactService cs = new ContactService();
        List<Contact> contacts = new ArrayList<>();
        cs.readContactsFromFile(contacts, "d");
        cs.printer(contacts);
        cs.addContactNumber(1, "4328432", contacts);
        cs.printer(contacts);
//        cs.sortContactsByName(contacts);
//        cs.serializeContactDetails(contacts, "d");
//        contacts = cs.deserializeContact("d");
//        Set<Contact> c2 = cs.populateContactFromDb();
//        System.out.println(cs.addContacts(contacts, c2));
    }
}

class NameComparator implements Comparator<Contact> {

    @Override
    public int compare(Contact o1, Contact o2) {
        return o1.getContactName().compareTo(o2.getContactName());
    }
}