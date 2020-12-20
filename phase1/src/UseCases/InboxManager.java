package UseCases;

import Entities.*;
import Exceptions.*;

import java.util.HashMap;
import java.util.ArrayList;
/**
 * <h1>Inbox Manager Use Case Class</h1>
 * Manages the inboxes of attendees including:
 * <ul>
 *     <li>Storing a hashmap of messages and their associated users inboxes</li>
 *     <li>Allow viewing of a specific inbox</li>
 *     <li>Adding of messages to a specific inbox</li>
 *     <li>Removal of messages from a specific inbox</li>
 * </ul> *
 *
 * @author  Adam Paslawski
 * @version 1.0
 *
 */
public class InboxManager implements java.io.Serializable {
    //A message center has a message DB of usernames and array list of messages
    /**
     * Data representation of chats and their associated inboxes.
     * This includes Entities.Room chats to message lists for that room as well as user to user chats.
     */
    private HashMap<String , ArrayList<Message>> messageDb;
    public InboxManager(){
        this.messageDb = new HashMap<String , ArrayList<Message>>();
    }
    /**
     *@return A list of message objects, the inbox of the specified user.
     * @param user Entities.Attendee object of user having their inbox retrieved for viewing.
     */
    public ArrayList<Message> getMessages(User user){
        return messageDb.get(user.getUserID());
    }
    /**
     *@param messageToBeAdded message object to be sent.
     *@param userReceiving attendee object of recipient.
     */
    public Boolean addMessage(Message messageToBeAdded , User userReceiving){
        ArrayList<Message> newList = messageDb.get(userReceiving.getUserID());
        newList.add(messageToBeAdded);
        messageDb.put(userReceiving.getUserID(), newList);
        return getMessages(userReceiving).contains(messageToBeAdded);
    }
    /**
     *Checks the User as that recipient added before adding a message to the recipients inbox.
     *@param userSending Object of User that is sending the message.
     *@param recipient Object of the User receiving the message.
     *@param messageToBeAdded Id of the message object being sent from userName to recipient.
     *TODO add an exception for user not being on contacts as well as an exception for user not existing.
     */
    public void sendMessage(User userSending, User recipient , Message messageToBeAdded) throws DoesNotExistError {
        //Check if user has a contact in its contact list
        if(userSending.getContacts().contains(recipient.getUserID())){
            addMessage(messageToBeAdded, recipient);
        }
        else{
            throw new DoesNotExistError("The user you are trying to send to does not exist in your contact list.");
        }
    }
    /**
     * Deletes a message from a users inbox.
     * @param user user to have a message deleted from their inbox.
     * @param messageToBeDeleted message object to be deleted from the inbox.
     */
    public Boolean deleteMessage(User user, Message messageToBeDeleted) throws DoesNotExistError {
        //Check if that message is in inbox
        if(messageDb.get(user.getName()).contains(messageToBeDeleted)) {
            //delete Entities.Message
            messageDb.get(user.getName()).remove(messageToBeDeleted);
            return true;
        }else{
            throw new DoesNotExistError("The message you are trying to delete does not exist in this inbox.");
        }
    }
    /**
     * sends multiple messages to a list of Users.
     * @param users list of User objects.
     * @param messageToBeSent message object to be sent to all users.
     */
    public <T> void sendMultiple(ArrayList<T> users,Message messageToBeSent){
        for (T user : users){
            addMessage(messageToBeSent, (User) user);
        }
    }
}
