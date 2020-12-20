package Entities;

import java.util.Date;
import java.util.ArrayList;
/**
 * <h1>Entities.Message Entity Class</h1>
 * Representation of messages as objects. *
 * @author  Adam Paslawski
 * @version 1.0
 */
public class Message implements java.io.Serializable {
    private String from;
    private String to;
    private ArrayList<String> recipientList;
    private String subject;
    private String body;
    private Date time;

    /**
     * Constructor for the message object when sent to a single person.
     * @param from attendee object from which the message is being passed.
     * @param to attendee object that the message is being passed to.
     * @param subject String object containing the subject of the message.
     * @param body String object containing the body of the message.
     */
    public Message(String from,String to,String subject,String body){
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.time = new Date();
    }

    /**
     * Constructor for a message sent to many people
     */
    public Message(String from,ArrayList<String> recipientList,String subject,String body){
        this.from = from;
        this.recipientList = recipientList;
        this.subject = subject;
        this.body = body;
        this.time = new Date();
    }
    public Message(String body){
        this.body = body;
    }
    /**
     * Overiding the toString method
     * @return a String representation for the message object.
     */
    @Override
    public String toString() {

        return "From: "+ this.from +"\n"+
                "To: " + this.to + "\n"+
                "Date: " + this.time + "\n"+
                "subject: " + this.subject + "\n"+
                "message: " + this.body + "\n";
    }


    // Implementing builder
    public static class messageBuilder {

    }
}