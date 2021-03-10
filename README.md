# Persi_Java_A10
(Done for the "Core Java" Course provided by the Persistent Systems)

### Project Structure:

![](https://github.com/s0umitra/Persi_Java_A10/blob/main/.readme/pStructure.jpg)

- Folder "db" contains Movies.txt which is used to populate the list
- Folder "src" contains the source codes
- src.RunSystem -> main program
- src.Libraries -> contains the functions used by the main program

### Problem Statement:


- Create class Contact using following fields along with getters and setters
  - contactID
  - ContactName
  - Email Address
  - List<String> contactNumber


- Create ContactService class along with following methods

1.	Add contacts : void addContact(Contact contact, List<Contact> contacts)
2.	void removeContact(Contact contact, List<contact> contacts) throws ContactNotFoundException
3.	Contact searchContactByName(String name, List<Contact> contact) throws ContactNotFoundException
4.	Search should be able to find the contacts based on the partial input
For example contact No is 8977448730
	Then it should be able to find contacts based on any of the i/p provided as mentioned below:-
a)	74
b)	448
c)	8730
All contacts present these values should be added in searched list
List<Contact> SearchContactByNumber(String number, List<Contact> contact) throws ContactNotFoundException
5.	addContactNumber(int contactId, String contactNo, List<contact> contacts)
Implement method to add provided contactNo to the existing list of contact numbers of the specified contact Id. Update the appropriate contact object.
6.	void sortContactsByName(List<Contact> contacts)
7.	void readContactsFromFile(List<Contact> contacts, String fileName)
8.	void serializeContactDetails(List<Contacts> contacts, String filename)
9.	List<Contact> deserializeContact(String filename)
10.	Set<Contact> populateContactFromDb()
11.	Boolean addContacts(List<Contacts> existingContact, Set<Contact> newContacts)


### Requirements:
- Java Runtime Environment
- MySQL JDBC connector

### License

MIT