package Controllers;

import Entities.*;
import UseCases.*;
import Exceptions.*;

import java.util.Date;
import java.util.ArrayList;

/**
 * The class that stores an Inbox Manager for access by other presenters.
 *
 * @version 0.1.1
 */
public class Messenger {
    private InboxManager im;

    /**
     * Constructor for Controllers.Messenger Controller
     * Stores Inbox Manager
     */
    public Messenger() {
        this.im = new InboxManager();
    }

    /**
     * Send private message from one user to another.
     * @param content The message itself
     * @param senderID The User ID of the sender
     * @param recipientID The User ID of the recipient
     * @param subject The subject of the message
     * @param am The User manager containing these Users and their information
     * @throws DoesNotExistError Error is thrown if the User does not exist
     */
    public void sendPrivateMessage(String content, String senderID, String recipientID, String subject, AttendeeManager am, SpeakerManager sm) throws DoesNotExistError {
        boolean senderIsAttendee = am.getAttendeeIDList().contains(senderID);
        boolean recipientIsAttendee = am.getAttendeeIDList().contains(recipientID);
        //Attendee to attendee
        if(senderIsAttendee && recipientIsAttendee){
            Date date = new Date();
            User sender = am.findAttendee(senderID);
            User recipient = am.findAttendee(recipientID);
            Message newMessage = new Message(sender.getName(), recipient.getName(), subject, content);
            this.im.sendMessage(sender, recipient, newMessage);
        }
        //Attendee to speaker
        if(senderIsAttendee && !recipientIsAttendee){
            Date date = new Date();
            User sender = am.findAttendee(senderID);
            User recipient = sm.findSpeaker(recipientID);
            Message newMessage = new Message(sender.getName(), recipient.getName(), subject, content);
            this.im.sendMessage(sender, recipient, newMessage);
        }
        //Speaker to attendee
        if(!senderIsAttendee && recipientIsAttendee){
            Date date = new Date();
            User sender = sm.findSpeaker(senderID);
            User recipient = am.findAttendee(recipientID);
            Message newMessage = new Message(sender.getName(), recipient.getName(), subject, content);
            this.im.sendMessage(sender, recipient, newMessage);
        }
        //Speaker to speaker
        if(!(senderIsAttendee && recipientIsAttendee)){
            Date date = new Date();
            User sender = sm.findSpeaker(senderID);
            User recipient = sm.findSpeaker(recipientID);
            Message newMessage = new Message(sender.getName(), recipient.getName(), subject, content);
            this.im.sendMessage(sender, recipient, newMessage);
        }
    }


    /**
     * @param from Name of person this message is from
     * @param subject The subject of the message
     * @param content The content of the message
     * @throws DoesNotExistError Error thrown if an attendee does not exist
     */
    public void sendMassMessage(User from, String subject, String content, AttendeeManager am) throws DoesNotExistError {
        //Construct Message
        Message messageToBeSent = new Message(from.getName(), am.getAttendeeList().toString(),subject,content );
    }

    /**
     *Mass Message to people in a specific talk
     */
    public void sendMassMessage(User from, String subject, String content, Talk talk, AttendeeManager am) throws DoesNotExistError {
        //Construct Message
        Message messageToBeSent = new Message(from.getName(), talk.getAttendees().toString(),subject,content );
        //List of Users
        ArrayList<String> UserList = talk.getAttendees();
        ArrayList<User> UserObjects = new ArrayList<User>();
        for (String UserID: UserList) {
            UserObjects.add(am.findAttendee(UserID));
        }
        im.sendMultiple(UserObjects, messageToBeSent);
    }
    public void sendMassMessage(User from, String subject, String content, SpeakerManager sm) throws DoesNotExistError {
        //Construct Message
        Message messageToBeSent = new Message(from.getName(), "All Speakers",subject,content );
        ArrayList<Speaker> UserObjects = sm.getSpeakerList();
        im.sendMultiple(UserObjects, messageToBeSent);
    }

    /**
     * @param userId User ID of inbox being reached
     * @param am Attendee manager
     * @param sm Speaker Manager
     * @return Array list of message objects, representing the users inbox.
     * @throws DoesNotExistError
     */
    public ArrayList<Message> viewInbox(String userId, AttendeeManager am, SpeakerManager sm) throws DoesNotExistError {
        if(am.getAttendeeIDList().contains(userId)){
            return this.im.getMessages(am.findAttendee(userId));
        }else{
            return this.im.getMessages(sm.findSpeaker(userId)) ;
        }
    }
}