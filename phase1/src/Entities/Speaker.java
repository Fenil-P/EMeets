package Entities;

import Entities.User;

import java.util.ArrayList;

/**
 * Respesentation of a speaker class
 *
 * @version 0.1
 */

public class Speaker extends User implements java.io.Serializable {


    private String speakerID;
    private String email;
    private String name;
    private String userID;
    private String password;
    private ArrayList<String> contacts;
    private ArrayList<String> hostingTalks;

    /**
     *
     * @param email the email
     * @param name the speaker name
     * @param userID the userid of the speaker
     *
     */

    public Speaker(String email, String userID, String name) {
        super(email, userID, name);
        this.email = email;
        this.userID = userID;
        this.name = name;
        this.speakerID = userID;
        this.contacts = new ArrayList<String>();
        this.hostingTalks = new ArrayList<String>();

    }
    /**
     *
     * @param email the email
     * @param name the speaker name
     * @param userID the userid of the speaker
     * @param password the password of the speaker
     *              *
     */

    public Speaker(String email, String userID, String name, String password) {
        super(email, userID, name);
        this.email = email;
        this.userID = userID;
        this.name = name;
        this.speakerID = userID;
        this.password = password;
        this.contacts = new ArrayList<String>();
        this.hostingTalks = new ArrayList<String>();

    }
    /**
     *
     * @return string representation of a speaker
     */

    @Override
    public String toString() {
        return "Entities.Speaker{" +
                "organizerID=" + speakerID +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", contacts=" + contacts +
                '}';
    }

    /**
     *
     * @return the list of talks this speaker is hosting
     */
    public ArrayList<String> getHostingTalks() {
        return this.hostingTalks;
    }

    /**
     *
     * @param niceTalk add talk from registration
     * @return if talk added
     */




    public boolean addHostEvent(String niceTalk) {
        if (this.hostingTalks.contains(niceTalk)) {
            return false;
        }
        this.hostingTalks.add(niceTalk);
        return true;
    }

    /**
     *
     * @param badTalk remove talk from registration
     * @return whether talk has been removed
     */

    public boolean removeHostEvent(String badTalk) {
        if (!(this.hostingTalks.contains(badTalk))) {
            return false;
        }
        this.hostingTalks.remove(badTalk);
        return true;
    }

    /**
     *
     * @return the speakerID of this speaker
     */

    public String getSpeakerID(){return userID;}

    /**
     *
     * @return the password of this speaker
     */

    public String getPassword(){return password;}
    /**
     *
     * @return if this speaker is a speaker, which is always true
     */
    public boolean isSpeaker() {return true;}

    /**
     *
     * @return if this speaker is an organizer, which is always false
     */
    public boolean isOrganizer() {return false;}
}

