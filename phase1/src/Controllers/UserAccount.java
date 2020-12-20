package Controllers;


import UseCases.*;
import Exceptions.*;


import java.util.ArrayList;


/**
 * A controller for the access of the User's account to perform needed functions
 *
 * @version 1.0
 */
public class UserAccount {

    // TODO: javadoc
    // Instance variables
    private String userID;
    private String userName;
    private String userEmail;
    private String userPassword;
    private UseCaseStorage useCaseStorage;
    private AttendeeManager attendeeManager;
    private SpeakerManager speakerManager;
    private Boolean isAttendee;
    private Boolean isOrganizer;
    private Boolean isSpeaker;

    /**
     *
     * @param useCases takes a UseCaseStorage object and stores it.
     */

    public UserAccount(UseCaseStorage useCases) {
        this.useCaseStorage = useCases;
        this.attendeeManager = useCases.getAttendeeManager();
        this.speakerManager = useCases.getSpeakerManager();
        this.isOrganizer = false;
        this.isSpeaker = false;
        this.isAttendee = false;
    }

    // Getters and Setters

    /**
     *
     * @return if the current accessed account is an organizer
     */
    public Boolean isOrganizer() {
        return this.isOrganizer;
    }

    /**
     *
     * @return if thecurrent accessed account is a speaker
     */
    public Boolean isSpeaker() {
        return this.isSpeaker;
    }

    /**
     *
     * @return if the current accessed account is an attendee
     */

    public Boolean isAttendee() {
        return this.isAttendee;
    }

    /**
     * Getter for the user's email.
     * @return The account's email.
     */
    public String getEmail() {
        return this.userEmail;
    }

    /**
     * Getter for the user's password.
     * @return The user's password.
     */
    public String getPassword() {
        return this.userPassword;
    }

    /**
     * Getter for the user's name.
     * @return The user's name.
     */
    public String getName() {
        return this.userName;
    }

    /**
     * Getter for the user's id.
     * @return The user's id.
     */
    public String getUserID() {
        return this.userID;
    }
    /**
     * Changes the user's email.
     * @param newEmail The new email to change to.
     */
    public Boolean setEmail(String newEmail) {
        try {
            this.attendeeManager.setAttendeeEmail(this.getUserID(), newEmail);
            this.userEmail = newEmail;
            return true;
        } catch (DoesNotExistError e) {
            try {
                this.speakerManager.setSpeakerEmail(this.getName(), newEmail);
                this.userEmail = newEmail;
                return true;
            } catch (DoesNotExistError e2) {
                return false;
            }
        }
    }

    /**
     * Changes the user's password
     * @param newPassword The new password to change to.
     */
    public Boolean setPassword(String newPassword) {
        try {
            this.attendeeManager.setAttendeePassword(this.getUserID(), newPassword);
            this.userPassword = newPassword;
            return true;
        } catch (DoesNotExistError e) {
            try {
                this.speakerManager.setSpeakerPassword(this.getName(), newPassword);
                this.userPassword = newPassword;
                return true;
            } catch (DoesNotExistError e2) {
                return false;
            }

        }
    }

    /**
     *
     * @param newName the new name of the attendee
     * @return if name was successfully set
     * @throws DoesNotExistError if attendee DNE
     */

    public Boolean setName(String newName) throws DoesNotExistError {
        try {
            this.attendeeManager.setAttendeeName(this.getUserID(), newName);
            this.userName = newName;
            return true;
        } catch (DoesNotExistError e) {
            try {
                this.speakerManager.setSpeakerName(this.getName(), newName);
                this.userName = newName;
                return true;
            } catch (DoesNotExistError e2) {
                return false;
            }
        }
    }

    // Login System

    /**
     * Log in to an Entities.Attendee/Entities.Organizer/Entities.Speaker account
     * @param email Email of the account.
     * @param name Name of the account.
     * @param password Password of the account.
     * @return Whether the login was successful or not.
     */
    public Boolean login(String email, String name, String password) {
        try {
            // Search in AttendeeManager first (user is Attendee/Organizer)
            String attendeeID = this.attendeeManager.getAttendeeID(email);

            if (attendeeManager.getAttendeeName(attendeeID).equals(name) &&
                    attendeeManager.getAttendeePassword(attendeeID).equals(password)) {

                // login successful
                this.userID = attendeeID;
                this.userName = name;
                this.userEmail = email;
                this.userPassword = password;
                if (attendeeManager.attendeeOrOrganizer(userID).equals("Attendee")) {
                    this.isAttendee = true;
                } else {
                    this.isOrganizer = true;
                }
                // was found in attendeeManager so not a speaker
                this.isSpeaker = false;
                return true;
            } else {
                return false;
            }
        } catch (DoesNotExistError e) {
            try {
                // Search in SpeakerManager next
                String speakerID = this.speakerManager.getSpeakerID(name);

                if (this.speakerManager.getEmailViaID(speakerID).equals(email) &&
                        this.speakerManager.getPasswordViaID(speakerID).equals(password)) {
                    // login successful
                    this.userID = speakerID;
                    this.userName = name;
                    this.userEmail = email;
                    this.userPassword = password;

                    this.isSpeaker = true;
                    this.isOrganizer = false;
                    this.isAttendee = false;

                    return true;
                } else {
//                    System.out.println(this.speakerManager.getEmailViaID(speakerID));
//                    System.out.println(this.speakerManager.getPasswordViaID(speakerID));
                    return false;
                }
            } catch (DoesNotExistError e2) {

                System.out.println(e);
                return false; // Was not found in AttendeeManager or SpeakerManager
            }
        }
    }

    /**
     *
     * @param email the email of the attendee
     * @param name the name of the attendee
     * @throws DoesNotExistError if downstream there is an error
     */

    public void registerAttendee(String email, String name) throws DoesNotExistError {
        // make new attendee and log in to this new attendee
        String id = this.attendeeManager.generateID();
        this.attendeeManager.createAttendee(email, id, name);
        String password = this.attendeeManager.getAttendeePassword(id);
        this.login(email, name, password);
    }

    /**
     * Overloaded method of the above
     * @param email the email
     * @param name the name
     * @param password password
     * @throws DoesNotExistError if login throws an error
     */
    public void registerAttendee(String email, String name, String password) throws DoesNotExistError {
        // make new attendee and log in to this new attendee
        String id = this.attendeeManager.generateID();
        this.attendeeManager.createAttendee(email, id, name, password);
        this.login(email, name, password);
    }

    /**
     *
     * @param email email of organizer
     * @param name the name
     * @throws DoesNotExistError if login fails
     */
    public void registerOrganizer(String email, String name) throws DoesNotExistError {
        String id = this.attendeeManager.generateID();
        this.attendeeManager.createOrganizer(email, id, name);
        String password = this.attendeeManager.getAttendeePassword(id);
        this.login(email, name, password);
    }
    /**
     * Overloaded method of registerOrganizer
     * @param password the password of the organizer
     * @param email email of organizer
     * @param name the name
     * @throws DoesNotExistError if login fails
     */

    public void registerOrganizer(String email, String name, String password) throws DoesNotExistError {
        String id = this.attendeeManager.generateID();
        this.attendeeManager.createOrganizer(email, id, name, password);
        this.login(email, name, password);
    }

    /**
     * logs out of current account
     */
    // Log out of the current account
    public void logout() {
        this.userID = "";
        this.isSpeaker = false;
        this.isOrganizer = false;
        this.isAttendee = false;
    }

    // Signup system

    /**
     *
     * @param talkID talk the guy wants to register for
     * @param registrar the registrar system
     * @return whether it has been registered
     */
    public Boolean register(String talkID, Registrar registrar) {
        // Only Attendees can register for events
        try {
            if (this.isAttendee()) {
                registrar.register(talkID, this.userID);
                return true;
            }
            return false;
        } catch (DoesNotExistError e) {
            return false;
        }
    }

    /**
     * Try to cancel an event
     * @param talkID ID they want to cancel
     * @param registrar registrar object used to deregister
     * @return whether the talk was cancelled
     * @throws DoesNotExistError if the talk does not exist
     */
    public Boolean cancel(String talkID, Registrar registrar) throws DoesNotExistError {
        // Only Attendees can cancel their spots in events
        try {
            if (this.isAttendee()) {
                registrar.cancel(this.userID, talkID);
                return true;
            }
            return false;
        } catch (DoesNotExistError e) {
            return false;
        }
    }

    // Messaging System

    /**
     *
     * @param m messenger
     * @return the inbox of this user
     * @throws DoesNotExistError if this user has no inbox
     */
    public ArrayList viewInbox(Messenger m) throws DoesNotExistError {
       return m.viewInbox(this.getUserID(), this.attendeeManager, this.speakerManager);
    }

    /**
     * send a message to a single attendee
     * @param subject the subject of message
     * @param content the content of message
     * @param recipientID the recipient ID
     * @param messenger the messenger controller in charge
     * @return whether message was sent
     */
    // send a message to a single attendee
    public Boolean sendMessage(String subject, String content, String recipientID, Messenger messenger) {
        try {
            messenger.sendPrivateMessage(content, this.getUserID(), recipientID, subject,
                    this.attendeeManager, this.speakerManager);
            return true;
        } catch (DoesNotExistError e) {
            return false;
        }
    }


    /**
     * Send a message as a speaker to all attendees at a talk
     * @param talkID talk a speaker is at
     * @param subject the subject
     * @param content the content of the message
     * @param messenger the messenger controller in charge
     * @return whether message was sent to all attendees
     */
    // speaker messages all at a talk they speak at
    public Boolean messageTalk(String talkID, String subject, String content, Messenger messenger){

        // Only Speakers can send a message to everyone attending a talk
        if (this.isSpeaker()) {

            // Check if they actually speak at that talk
            try {
                if (this.speakerManager.getSpeakerEvents(this.getUserID()).contains(talkID)) {

                    // Yes they do, so send a message
                    messenger.sendMassMessage(this.speakerManager.findSpeaker(this.getUserID()), subject, content,
                            useCaseStorage.getTalkManager().findTalk(talkID), this.attendeeManager);
                    return true;
                } else {
                    return false;
                }
            } catch (DoesNotExistError e) {
                return false;
            }
        } else {
            // Not a speaker
            return false;
        }
    }

    /**
     * Send a message to all events a speaker speaks at
     * @param talkList list of talks at which the speaker is speaking at
     * @param subject subject of message
     * @param content content of message
     * @param messenger the messenger controller
     * @return whether message was sent to all rooms
     */
    // A speaker can message multiple talks that they speak at
    public Boolean messageMultipleTalks (ArrayList<String> talkList, String subject, String content,
                                         Messenger messenger) {
        if (this.isSpeaker()) {
            Boolean fullySuccessful = true;
            for (String talkID : talkList) {
                if (!messageTalk(talkID, subject, content, messenger)) {
                    fullySuccessful = false;
                }
            }

            return fullySuccessful;
        } else {
            return false;
        }
    }

    /**
     * Message attendees as an organizer
     * @param subject the subject of message
     * @param content the content
     * @param messenger messenger controller
     * @return whether it was sent to all attendees at the conference
     */
    // Organizer messages all attendees at the conference
    public Boolean messageAllAttendees(String subject, String content, Messenger messenger) {

        // Only organizers can message everyone
        if (this.isOrganizer()) {
            try {
                messenger.sendMassMessage(this.attendeeManager.findAttendee(this.getUserID()), subject, content,
                        this.attendeeManager);
                return true;
            } catch (DoesNotExistError e) {
                return false;
            }
        } else {
            // Not an organizer
            return false;
        }
    }

    /**
     * Organizer messages all speakers at a conference
     * @param subject the subject of a message
     * @param content the content of a message
     * @param messenger the messenger controller
     * @return whether it was sent or not
     */
    // Organizer messages all speakers at the conference
    public Boolean messageAllSpeakers(String subject, String content, Messenger messenger) {

        // Only organizers can message all speakers
        if (this.isOrganizer()) {
            try {
                messenger.sendMassMessage(this.attendeeManager.findAttendee(this.getUserID()), subject, content,
                        this.speakerManager);
                return true;
            } catch (DoesNotExistError e) {
                return false;
            }
        } else {
            return false;
        }
    }
    /**
     * The User adds an Attendee as a friend, like Salman!
     * @param friendID ID of the Entities.Attendee to add as a friend.
     * @return Whether adding a friend was successful or not
     * @throws DoesNotExistError If the friend does not exist. :(
     */
    public Boolean addFriend(String friendID) {
        // You cannot be your own friend. Sadge
        if (this.getUserID().equals(friendID)) {
            return false;
        }

        if (this.isSpeaker()) {
            // Use SpeakerManager to add
            try {
                this.speakerManager.setContactViaID(this.getUserID(), friendID, this.attendeeManager);
                return true;
            } catch (DoesNotExistError e) {
                return false;
            }
        } else {
            // Use AttendeeManager to add
            try {
                // Attendee and attendee
                this.attendeeManager.setContactViaID(this.getUserID(), friendID);
                return true;
            } catch (DoesNotExistError e2) {
                // Attendee and speaker
                try {
                    // Check if friend exists then try adding
                    speakerManager.findSpeaker(friendID);
                    this.attendeeManager.addSpeakerToContacts(this.getUserID(), friendID);
                    return true;
                } catch(DoesNotExistError e3) {
                    return false;
                }
            }
        }
    }

    /**
     * remove a friend. For example, if your friendship with Mudasir was ended, you would use this.
     * @param removeID the friend ID that is to be removed
     * @return whether the friend has been removed
     */

    public Boolean removeFriend(String removeID) {
        try {
            this.attendeeManager.removeContact(attendeeManager.findAttendee(this.getUserID()),
                    this.attendeeManager.findAttendee(removeID));
            return true;
        } catch (DoesNotExistError e) {
            try {
                this.attendeeManager.removeSpeakerFromContactsUsingID(this.getUserID(), removeID);
                return true;
            } catch (DoesNotExistError e2) {
                return false;
            }
        }
    }

    /**
     * create a room as an organizer
     * @param openHour when the room opens in terms of the hour
     * @param openMinute the minute at which the room opens
     * @param closingHour hour at which the room closes
     * @param closingMinute the minute at which the room closes
     * @return if room was created
     */

    public Boolean createRoom(int openHour, int openMinute, int closingHour, int closingMinute) {
        // Only Organizers can create rooms
        if (this.isOrganizer()) {
            // All talks must take place between 9 am - 5 pm (hrs 9~17)
            if (openHour >= 9 && openHour <= 17 && closingHour >= 9 && closingHour <= 17 &&
                    openHour < closingHour) {
                this.useCaseStorage.getRoomManager().addRoom(openHour, openMinute, closingHour, closingMinute);
                return true;
            }
        }
        return false;
    }

    /**
     * As an organizer, create a speaker for the event
     * @param email email of speaker
     * @param name name of the speaker
     * @return whether the speaker was enabled or not
     */

    public Boolean createSpeaker(String email, String name) {
        // Only Organizers can create Speakers
        if (this.isOrganizer()) {
            this.speakerManager.createSpeaker(email, name);
            return true;
        }
        return false;
    }

    /**
     * As an organizer, create a speaker for the event. Overloaded above method with a password parameter.
     * @param email email of speaker
     * @param name name of the speaker
     * @param password password of the speaker
     * @return whether the speaker was enabled or not
     */

    public Boolean createSpeaker(String email, String name, String password) {
        // Only Organizers can create Speakers
        if (this.isOrganizer()) {
            return this.speakerManager.createSpeaker(email, name, password);

//            try {
//                this.speakerManager.setSpeakerPassword(name, password);
//                return true;
//            } catch (DoesNotExistError e) {
//                System.out.println(e);
//                return false;
//            }
        } else {
            return false;
        }
    }
    /**
     * Returns a list of contacts
     * @return list of contacts of the attendee
     * @throws DoesNotExistError if userID DNE.
     */
    public ArrayList<String> viewContacts() throws DoesNotExistError {
        return this.attendeeManager.getContactsID(this.getUserID());
    }
}
