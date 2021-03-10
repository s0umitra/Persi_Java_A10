package libraries;

public class ContactNotFoundException extends Exception {

    public ContactNotFoundException() {
        super("Exception thrown > Contact not found!");
    }
}