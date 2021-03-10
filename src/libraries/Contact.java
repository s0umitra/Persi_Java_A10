package libraries;

import java.util.List;

public class Contact implements java.io.Serializable {
    private int contactID;
    private String ContactName;
    private String email;
    List<String> contactNumber;

    public int getContactID() {
        return this.contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return this.ContactName;
    }

    public void setContactName(String ContactName) {
        this.ContactName = ContactName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(List<String> contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return  "contactID=" + contactID +
                ", ContactName='" + ContactName + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber=" + contactNumber;
    }
}
