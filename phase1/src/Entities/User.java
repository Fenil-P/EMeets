package Entities;

import java.util.ArrayList;

/**
 * Representation of a user in the program
 *
 * @author Warren Liu
 * @version 0.1
 */

public class User implements java.io.Serializable {

    private String name;
    private String email;
    private String userID;
    private String password;
    private ArrayList<String> contacts;

    /**
     *
     * @param email email of the attendee
     * @param userID userID of the attendee
     * @param name name of the attendee
     *
     */

    public User(String email, String userID, String name) {
        this.email = email;
        this.name = name;
        this.userID = userID;
        this.password = "abcdefghijklmn";
        this.contacts = new ArrayList<String>();

    }

    /**
     *
     * @return the name of the Entities.User
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param name rename the attendee
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the email
     */

    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param email set the email
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return the string representation of this user
     *
     */

    @Override
    public String toString() {
        return "Entities.Attendee{" +
                "email='" + this.email + '\'' +
                ", userID=" + this.userID +
                '}';
    }

    /**
     *
     * @return password
     */

    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @param password set the password
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return userID
     */

    public String getUserID() {
        return this.userID;
    }

    /**
     *
     * @param userID return userID of the attendee
     */

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<String> getContacts() {
        return this.contacts;
    }

    /**
     *
     * @param friendID add a friend to contacts
     * @return whether contact is added
     */

    public boolean addtoContacts(String friendID) {
        if ((this.contacts.contains(friendID))) {
            return false;
        }
        this.contacts.add(friendID);
        return true;
    }

    /**
     *
     * @param notafriend Friendship ended with Mudasir, now Salman is my best friend
     * @return whether friend has been removed
     */

    public boolean removefromContacts(String notafriend) // throws Errors.DoesNotExistError
    {
        if (!(this.contacts.contains(notafriend))) {
            return false;
            // throw new Errors.DoesNotExistError("Error: This Friend is not in your friendlist" + ((Integer)notafriend).toString());

        }
        // Friendship ended with Mudasir, now Salman is my best friend
        this.contacts.remove(notafriend);
        return true;
    }
}
