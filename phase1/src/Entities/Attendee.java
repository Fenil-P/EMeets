package Entities;


import java.util.ArrayList;

/**
 * Represents an attendee entity in our system.
 *
 * @version 0.1
 */
public class Attendee extends User implements java.io.Serializable {
    // TODO: javadoc
    /* Description from the CRC cards
    email
    password
    contacts
    registeredTalks
    getters,
    setters
     */

    // TODO: Make sure other class method names/parameter types and order are consistent with my guesses
    // go back and implement stuff once again consistent with the higher-level classes

    // Instance variables (in the crc)
    private String name;
    private String email;
    private String userID;
    private String password;
    private ArrayList<String> contacts;
    private ArrayList<String> registeredTalks;
    private boolean isVIP;

    /**
     *
     * @param email email of the attendee
     * @param userID userID of the attendee
     * @param name name of the attendee
     *
     */

    public Attendee(String email, String userID, String name) {
        super(email, userID, name);
        this.email = email; // added these setters in org and speaker as well, not sure why super isn't setting it
        this.userID = userID;
        this.name = name;
        this.password = "abcdefghijklmn";
        this.contacts = new ArrayList<String>();
        this.registeredTalks = new ArrayList<String>();
        this.isVIP = false;
    }

    /**
     *
     * @return the name of the thingy
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

    /**
     *
     * @return contactlist
     */

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
            //You are already friends with this person
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

    /**
     *
     * @return list of all registered events
     */

    public ArrayList<String> getRegisteredEvents() {
        return this.registeredTalks;
    }

    /**
     *
     * @param niceTalk add talk from registration
     * @return if talk added
     */

    public boolean registerForEvent(String niceTalk) {
        if (this.registeredTalks.contains(niceTalk)) {
            return false;
        }
        this.registeredTalks.add(niceTalk);
        return true;
    }

    /**
     *
     * @param badTalk remove talk from registration
     * @return whether talk has been removed
     */

    public boolean deregisterFromEvent(String badTalk) {
        if (!(this.registeredTalks.contains(badTalk))) {
            return false;
        }
        this.registeredTalks.remove(badTalk);
        return true;
    }

    /**
     *
     * @return if this attendee is a speaker, which is always false
     */
    public boolean isSpeaker() {return false;}

    /**
     *
     * @return if this attendee is an organizer, which is always false
     */
    public boolean isOrganizer() {return false;}

    /**
     *
     * @return if this Attendee is a VIP, which is always false
     */
    public boolean isVIP() {return this.isVIP;}

}
