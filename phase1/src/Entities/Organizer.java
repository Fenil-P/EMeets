package Entities;

import java.util.ArrayList;

/**
 * Represents an Entities.Organizer, which is a special type of Entities.Attendee
 *
 * @version 0.1
 */

public class Organizer extends Attendee implements java.io.Serializable {

    private String organizerID;
    private String email;
    private String name;
    private String userID;
    private String password;
    private ArrayList<Attendee> contacts;
    private ArrayList<Talk> registeredTalks;

    /**
     *
     * @param email the email of the organizer
     * @param userID the UID of the organizer
     * @param name the name or ght organizer
     */

    public Organizer(String email, String userID, String name) {
        super(email, userID, name);
        this.email = email;
        this.userID = userID;
        this.name = name;
        this.password = "qwerty";
        this.organizerID = userID;
    }

    /**
     *
     * @return organizer ID
     */

    public String getOrganizerID() {
        return this.organizerID;
    }

    /**
     *
     * @param organizerID
     */

    public void setOrganizerID(String organizerID) {
        this.organizerID = organizerID;
        this.userID = organizerID;
    }

    /**
     *
     * @return a string representation of an organizer.
     */
    @Override
    public String toString() {
        return "Entities.Organizer{" +
                "organizerID=" + organizerID +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", contacts=" + contacts +
                '}';
    }

    /**
     *
     * @return if this organizer is a speaker, which is always false
     */
    public boolean isSpeaker() {return false;}

    /**
     *
     * @return if this organizer is an organizer, which is always true.
     */
    public boolean isOrganizer() {return true;}
}
