package Entities;

import java.util.ArrayList;

/**
 * Representation of the VIP Class. VIP class Accounts need to be created after a certain payment plan is confirmed,
 * or some other thing has occurred (TBD)
 *
 * @version 1.9
 */

public class VIPAttendee extends Attendee implements java.io.Serializable {

    private String password;
    private ArrayList<String> contacts;
    private ArrayList<String> registeredTalks;
    private final boolean isVIP;

    /**
     * @param email  email of the attendee
     * @param userID userID of the attendee
     * @param name   name of the attendee
     */

    public VIPAttendee(String email, String userID, String name) {
        super(email, userID, name);
        this.password = "qwerty";
        this.isVIP = true;
    }

    /**
     *
     * @return if this Attendee is a VIP, which is always false
     */
    public boolean isVIP() {return this.isVIP;}
}
