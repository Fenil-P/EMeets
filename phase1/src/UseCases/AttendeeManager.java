package UseCases;

import Entities.*;
import Exceptions.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 *Attendee Manager Class: USE CASE
 *
 * @version 1.1
 */
public class AttendeeManager  implements java.io.Serializable {
    private ArrayList <Attendee> attendeeList;
    private ArrayList <String> attendeeIDList;

    /**
     * Constructor for Attendee Manager
     */
    public AttendeeManager(){
        this.attendeeList = new ArrayList<>();
        this.attendeeIDList = new ArrayList<>();
    }

    /**
     * Instantiates an attendee with an ID userID
     * @param email new users e mail
     * @param userID new users ID
     * @param name new users name
     */
    public void createAttendee(String email, String userID, String name){
        Attendee attendeeToBeAdded = new Attendee(email, userID, name);
        attendeeList.add(attendeeToBeAdded);
        attendeeIDList.add(userID);
    }

    /**
     * Instantiates an attendee using only their email and name
     * @param email new users e mail
     * @param name new users name
     */
    public void createAttendee(String email, String name){
        String userID = this.generateID();
        Attendee attendeeToBeAdded = new Attendee(email, userID, name);
        attendeeList.add(attendeeToBeAdded);
        attendeeIDList.add(userID);
    }

    /**
     * Instantiates an organizer with an ID userID
     * @param email new organizer's email
     * @param userID new organizer's user ID
     * @param name new organizer's name
     */
    public void createOrganizer(String email, String userID, String name){
        Organizer organizerToBeAdded = new Organizer(email, userID, name);
        attendeeList.add(organizerToBeAdded);
        attendeeIDList.add(userID);
    }

    /**
     * Overloaded method that creates the attendee's password
     * @param email Attendee email
     * @param userID Attendee userID
     * @param name Attendee name
     * @param password Attendee password
     * @throws DoesNotExistError If user does not exist
     */
    public void createAttendee(String email, String userID, String name, String password) throws DoesNotExistError {
        Attendee attendeeToBeAdded = new Attendee(email, userID, name);
        attendeeList.add(attendeeToBeAdded);
        attendeeIDList.add(userID);
        setAttendeePassword(userID, password);
    }

    /**
     * Overloaded method that creates the organizer's password
     * @param email Organizer email
     * @param userID Organizer userID
     * @param name Organizer name
     * @param password Organizer password
     * @throws DoesNotExistError If user does not exist
     */
    public void createOrganizer(String email, String userID, String name, String password) throws DoesNotExistError {
        Organizer organizerToBeAdded = new Organizer(email, userID, name);
        attendeeList.add(organizerToBeAdded);
        attendeeIDList.add(userID);
        setAttendeePassword(userID, password);
    }

    /**
     * Gets the name of the attendee with ID userID
     * @param userID the ID of the user in question
     * @return the name of the user
     * @throws DoesNotExistError if the user with userID does not exist
     */
    public String getAttendeeName(String userID) throws DoesNotExistError {
        return findAttendee(userID).getName();
    }

    /**
     * Sets the name of the attendee with ID userID and name name
     * @param userID the ID of the attendee in question
     * @param name the name of the attendee in question
     * @throws DoesNotExistError if the user with ID userID does not exist
     */
    public void setAttendeeName(String userID, String name) throws DoesNotExistError {
        findAttendee(userID).setName(name);
    }

    /**
     * Gets the email of the attendee
     * @param userID the ID of attendee in question
     * @return the String email of the attendee in question
     * @throws DoesNotExistError if the user with ID userID does not exist
     */
    public String getAttendeeEmail(String userID) throws DoesNotExistError {
        return findAttendee(userID).getEmail();
    }

    /**
     * Sets the email of the attendee
     * @param userID the ID of the attendee in question
     * @param email the String email to be set for the attendee in question
     * @throws DoesNotExistError if the user with ID userID does not exist
     */
    public void setAttendeeEmail(String userID, String email) throws DoesNotExistError {
        findAttendee(userID).setEmail(email);
    }

    /**
     * Gets the password of the account of the attendee with ID userID
     * @param userID the ID of the attendee in question
     * @return the String password of the account of the attendee in question
     * @throws DoesNotExistError if the user with ID userID does not exist
     */
    public String getAttendeePassword(String userID) throws DoesNotExistError {
        return findAttendee(userID).getPassword();
    }

    /**
     * Sets the password of the account of the attendee with ID userID
     * @param userID the ID of the attendee in question
     * @param password the password to be set for the attendee in question
     * @throws DoesNotExistError if the user with ID userID does not exist
     */
    public void setAttendeePassword(String userID, String password) throws DoesNotExistError {
        findAttendee(userID).setPassword(password);
    }

    /**
     * Gets the ID of the attendee with email email
     * @param email The email of the attendee in question
     * @return the String user ID of the attendee in question
     * @throws DoesNotExistError if a user with email email does not exist
     */
    public String getAttendeeID(String email) throws DoesNotExistError {
        return findAttendee(email).getUserID();
    }

    /**
     * Adds an attendee to another attendee's contact list using only the involved attendees' user IDs
     * @param attendeeID User ID of the adding attendee
     * @param attendeeAddedID User ID of the added attendee
     */
    public void setContactViaID(String attendeeID, String attendeeAddedID) throws DoesNotExistError {
        Attendee attendee = findAttendee(attendeeID);
        Attendee attendeeAdded = findAttendee(attendeeAddedID);
        this.addContact(attendee, attendeeAdded);
    }

    /**
     * Adds an attendee contactToBeAdded to the contact list of attendee attendee
     * @param attendee Entities.Attendee having a contact added to their friendslist
     * @param contactToBeAdded Entities.Attendee object representing the person that is being added to the contact list
     */
    public void addContact(Attendee attendee , Attendee contactToBeAdded) {
        attendee.addtoContacts(contactToBeAdded.getUserID());
    }

    /**
     * Removes an attendee contactToBeRemoved from the contact list of attendee attendee
     * @param attendee Entities.Attendee who is having the contact removed from their friendslist
     * @param contactToBeRemoved Entities.Attendee object representing the person being removed from the friends list
     */
    public void removeContact(Attendee attendee , Attendee contactToBeRemoved) {
        String friendID = contactToBeRemoved.getUserID();
        if (attendee.getContacts().contains(friendID)) {
            attendee.removefromContacts(friendID);
        }
    }
    /**
     * Removes an attendee attendee from a talk Event
     * @param attendee Entities.Attendee to deregister
     * @param Event Entities.Talk in question
     */
    public void removeEvent(Attendee attendee , Talk Event) {
        String eID = Event.getEventID();
        attendee.deregisterFromEvent(eID);
    }

    /**
     * Registers attendee attendee to the talk Event
     * @param attendee Entities.Attendee to register
     * @param Event Entities.Talk in question
     */
    public void registerEvent(Attendee attendee , Talk Event) {
        String eID = Event.getEventID();
        attendee.registerForEvent(eID);
    }

    /**
     * Checks if this attendee is available to attend a talk at the given date and time
     * @param date - Date object, date to be checked
     * @param time - LocalTime object, time to be checked
     * @return true if and only if the attendee is available to attend a talk
     */
    public boolean isAttendeeAvailable(GregorianCalendar date, LocalTime time, Attendee attendee, TalkManager tm) throws DoesNotExistError {
        LocalTime time1 = time.plusHours(1);
        for (String talkID: attendee.getRegisteredEvents()) {
            LocalTime eventTime = tm.findTalk(talkID).getStartTime();
            LocalTime eventTime1 = tm.findTalk(talkID).getStartTime().plusHours(1);
            if ((eventTime1.compareTo(time) > 0 && eventTime1.compareTo(time1) < 0) || (eventTime.compareTo(time) > 0 && eventTime.compareTo(time1) < 0)){
                return false;
            }
        }
        return true;
    }

    /**
     * Finds the attendee with user ID ID
     * @param ID Either Entities.User ID or Email
     * @return the attendee associated with the Entities.User ID / Email
     */
    public Attendee findAttendee(String ID) throws DoesNotExistError {
        for (Attendee attendee: attendeeList) {
            if (attendee.getUserID().equals(ID)) {
                return attendee;
            }
            if (attendee.getEmail().equals(ID)) {
                return attendee;
            }

        }
        throw new DoesNotExistError("This ID does not exist");
    }
    /**
     * Generates a random ID from the attendee list
     * @return "Entities.Attendee" + ID
     */
    public String generateID(){
        String ID = "Attendee";
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10000);
        while (attendeeIDList.contains(ID + randomNum)){
            randomNum = ThreadLocalRandom.current().nextInt(0, 10000);
        }
        attendeeIDList.add(ID + randomNum);
        return ID + randomNum;
    }

    /**
     * Gets the list of attendees
     * Returns the attendee list
     * @return this.attendeeList
     */
    public ArrayList<Attendee> getAttendeeList() {
        return this.attendeeList;
    }

    /**
     * Returns the registered events of an attendee
     * @param a An attendee
     * @return a.getRegisteredEvents()
     */
    public ArrayList<String> getRegisteredTalks(Attendee a) {
        return a.getRegisteredEvents();
    }

    /**
     * Returns whether or not a user with user ID userID is an solely attendee or an organizer
     * @param userID The user ID of the attendee/organizer
     * @return "Organizer" or "Attendee"
     * @throws DoesNotExistError If the user with ID userID does not exist
     */
    public String attendeeOrOrganizer(String userID) throws DoesNotExistError {
        if (findAttendee(userID).isOrganizer()) {
            return "Organizer";
        }
        return "Attendee";
    }

    /**
     * Returns the contact list of the user with user ID userID
     * @param userID The user ID of the user
     * @return attendee.getContacts()
     * @throws DoesNotExistError If the user with userID does not exist
     */
    public ArrayList<String> getContactsID(String userID) throws DoesNotExistError {
        Attendee attendee = findAttendee(userID);
        return attendee.getContacts();
    }
    public ArrayList<String> getAttendeeIDList(){return this.attendeeIDList;}

    /**
     * Adds a speaker with ID speakerID to the contact list of an attendee with ID attendeeID.
     * @param attendeeID the ID of the attendee in question
     * @param speakerID the ID of the speaker in question
     * @throws DoesNotExistError if the attendee with ID attendeeID does not exist
     */
    public void addSpeakerToContacts(String attendeeID, String speakerID) throws DoesNotExistError {
        Attendee attendee = findAttendee(attendeeID);
        attendee.addtoContacts(speakerID);
    }

    /**
     * Removes a speaker with ID speakerID from the contact list of an attendee with ID attendeeID
     * @param attendeeID the ID of the attendee in question
     * @param speakerID the ID of the speaker in question
     * @throws DoesNotExistError if the attendee with ID attendeeID does not exist
     */
    public void removeSpeakerFromContactsUsingID(String attendeeID, String speakerID) throws DoesNotExistError {
        Attendee attendee = findAttendee(attendeeID);
        attendee.removefromContacts(speakerID);
    }


}
