import libraries.Contact;
import libraries.ContactNotFoundException;
import libraries.ContactService;
import libraries.DbManager;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class RunSystem {

    public void printMenu() {
        System.out.print("\n1)  Create a new Contact\n");
        System.out.print("2)  Remove a Contact\n");
        System.out.print("3)  Search Contact By Name\n");
        System.out.print("4)  Search Contact By Number\n");
        System.out.print("5)  Add a number to Contact\n");
        System.out.print("6)  Sort Contacts By Name\n");
        System.out.print("7)  Read Contacts from a File\n");
        System.out.print("8)  Serialize Contact Details\n");
        System.out.print("9)  Deserialize Contacts\n");
        System.out.print("10) Get Contacts from Database\n");
        System.out.print("11) Merge Contacts\n");
        System.out.print("12) Show Available Contacts\n");
        System.out.print("13) Exit\n");
    }

    public static void main(String[] args) throws ContactNotFoundException, IOException, ClassNotFoundException, SQLException {
        int choice;
        Scanner sc = new Scanner(System.in);
        RunSystem rs = new RunSystem();
        ContactService cs = new ContactService();
        List<Contact> contacts = new ArrayList<>();
        Set<Contact> contactsdb = new HashSet<>();
        cs.readContactsFromFile(contacts, "d");

        System.out.println("*************************************************");
        System.out.println("**  Auto Populating contacts from Contact.txt  **");

        do {
            rs.printMenu();
            choice = sc.nextInt();

            if (choice == 1) {
                int id, tN;
                String name, mail;
                List<String> nums = new ArrayList<>();

                System.out.print("Enter Contact ID: ");
                id = sc.nextInt();

                System.out.print("Contact Name: ");
                name = sc.next();

                System.out.print("Contact Email: ");
                mail = sc.next();

                System.out.print("Number of Contact Number[s]: ");
                tN = sc.nextInt();
                for (int i=0; i<tN; i++) {
                    System.out.print("Number " + (i+1) + ": ");
                    nums.add(sc.next());
                }
                contacts.add(cs.createContact(id, name, mail, nums));
                System.out.println("Contact Added!");
            }
            else if (choice == 2) {
                cs.printer(contacts);
                System.out.print("Enter Contact ID: ");
                cs.removeContact(cs.getContactById(sc.nextInt(), contacts), contacts);
                System.out.println("Contact Removed!");
            }
            else if (choice == 3) {
                System.out.print("Enter Contact Name: ");
                System.out.println(cs.searchContactByName(sc.next(), contacts));
            }
            else if (choice == 4) {
                System.out.print("Enter Number (partial also accepted): ");
                cs.printer(cs.searchContactByNumber(sc.next(), contacts));
            }
            else if (choice == 5) {
                int id;
                cs.printer(contacts);
                System.out.print("Enter Id:");
                id = sc.nextInt();
                System.out.println("Enter Number");
                cs.addContactNumber(id, sc.next(), contacts);
                System.out.println("Number added!");
            }
            else if (choice == 6) {
                cs.sortContactsByName(contacts);
                cs.printer(contacts);
            }
            else if (choice == 7) {
                System.out.print("Enter file path with extension (enter d for defaults): ");
                cs.readContactsFromFile(contacts, sc.next());
            }
            else if (choice == 8) {
                System.out.print("Enter file path with extension (enter d for defaults): ");
                cs.serializeContactDetails(contacts, sc.next());
            }
            else if (choice == 9) {
                System.out.print("Enter file path with extension (enter d for defaults): ");
                List<Contact> res = cs.deserializeContact(sc.next());
                contacts =  res == null ? contacts: res;
            }
            else if (choice == 10) {
                System.out.println("Initializing database with Contact table and values...");
                System.out.println("Fetching Contacts...");
                contactsdb = cs.populateContactFromDb();
                cs.printer(contactsdb);
            }
            else if (choice == 11) {
                if (contactsdb.isEmpty()) {
                    System.out.println("No Contacts found to Merge!");
                } else {
                    System.out.println(cs.addContacts(contacts, contactsdb) ? "Contacts Merged Successfully!": "Failed to Merge");
                }
            }
            else if (choice == 12) {
                cs.printer(contacts);
            }
            else if (choice == 13) {
                System.out.println("Closing...");
                System.exit(3);
            }

        } while (true);
    }

}
