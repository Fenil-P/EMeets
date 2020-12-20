package UseCases;

import Entities.*;
import Exceptions.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Stores all the talks in the system
 *
 * @version 0.1
 */

public class TalkManager implements java.io.Serializable {
    // TODO: javadoc
    // Stores entire list of Talks
    // Can initialize a talk and add it to list, can also initialize a speaker and add it to a talk
    // Create ID
    private ArrayList<Talk> talks;
    private ArrayList<String> ids;

    /**
     * Initializes a talk manager
     */
    public TalkManager() {
        this.talks = new ArrayList<>();
    }

    /**
     * create a talk with following parameters
     * @param title title of talk
     * @param date date of talk
     * @param startTime starttime of talk
     * @param speakers speakers of talk
     */

    public void createTalk(String title, GregorianCalendar date, LocalTime startTime, String speakers){
        Talk talk = new Talk(title, date, startTime, speakers, this.generateID());
        this.talks.add(talk);
    }

    /**
     * create a talk with following parameters
     * @param title title of talk
     * @param date date of talk
     * @param startTime starttime of talk
     * @param speakers speaker of talk
     * @param talkID talkID of talk
     */
    // Overloaded to be able to generate the id outside so we can keep track of the id in the place the talk was made
    public void createTalk(String title, GregorianCalendar date, LocalTime startTime, String speakers, String talkID){
        Talk talk = new Talk(title, date, startTime, speakers, talkID);
        this.talks.add(talk);
        this.ids = new ArrayList<>();
    }

    /**
     *
     * @return a randomly generated ID for the talk
     */
    public String generateID(){
        if (this.ids == null)
            this.ids = new ArrayList<>();
        String ID = "Talk";
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10000);
        while (ids.contains(ID + randomNum)){
            randomNum = ThreadLocalRandom.current().nextInt(0, 10000);
        }
        ids.add(ID + randomNum);
        return ID + randomNum;
    }

    /**
     *
     * @param talk talk object in question
     * @param speaker the new speaker's ID
     */
    public void replaceSpeaker(Talk talk, String speaker){
        talk.changeSpeaker(speaker);
    }

    /**
     *
     * @param talk talk object in question
     * @param speaker the new speaker object
     */
    public void replaceSpeaker(Talk talk, Speaker speaker){
        talk.changeSpeaker(speaker.getUserID());
    }

    /**
     *
     * @param ID talkID
     * @return if this talk with ID is stored within the talkmanager
     */
    public boolean isTalk(String ID){
        for (Talk talk : talks) {
            if (talk.getEventID().equals(ID)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param ID talkID
     * @return the talk with the above ID
     * @throws DoesNotExistError if talk DNE
     */
    public Talk findTalk(String ID) throws DoesNotExistError {
        for (Talk talk : talks) {
            if (talk.getEventID().equals(ID)) {
                return talk;
            }
        }
        throw new DoesNotExistError("This Talk with ID does not exist");
    }

    /**
     *
     * @return all of the talk IDs in the system
     */
    public ArrayList<String> getAllTalks() {
        ArrayList<String> ts = new ArrayList<>();
        for (Talk t : this.talks){
            ts.add(t.getEventID());
        }
        return ts;
    }

    /**
     *
     * @param ID ID of the talk
     * @return the talk with the above ID's string representation
     * @throws DoesNotExistError if talk DNE
     */
    public String displayTalk(String ID) throws DoesNotExistError {
        // Talk: Orange, Date: Wed Nov 04 2020, Start time: 20:05:54.649, Attendees: [], Speakers: []
        return this.findTalk(ID).toString();
    }

    /**
     *
     * @param ID Id of the talk
     * @return title of the talk with above ID
     * @throws DoesNotExistError if talk does not exist
     */
    public String getTalkTitle(String ID) throws DoesNotExistError {
        return this.findTalk(ID).getTitle();
    }

    /**
     *
     * @param ID talk ID
     * @param title new title of the talk
     * @throws DoesNotExistError if talk DNE
     */
    public void setTalkTitle(String ID, String title) throws DoesNotExistError {
        this.findTalk(ID).setTitle(title);
    }

    /**
     *
     * @param ID talk ID
     * @return date of the talk with ID
     * @throws DoesNotExistError if talk does not exist
     */
    public GregorianCalendar getTalkDate(String ID) throws DoesNotExistError {
        return this.findTalk(ID).getDate();
    }

    /**
     * sets new date for the talk
     * @param ID ID of the talk Object
     * @param date the new date of the talk
     * @throws DoesNotExistError if the date does not exist
     */

    public void setTalkDate(String ID, GregorianCalendar date) throws DoesNotExistError {
        this.findTalk(ID).setDate(date);
    }

    /**
     *
     * @param ID the id of the talk
     * @return the starttime of the talk
     * @throws DoesNotExistError if talk DNE
     */
    public LocalTime getTalkStartTime(String ID) throws DoesNotExistError {
        return this.findTalk(ID).getStartTime();
    }

    /**
     *
     * @param ID ID of the talk
     * @param startTime the new starttime of the talk to be set
     * @throws DoesNotExistError if talk with ID dne
     */
    public void setTalkStartTime(String ID, LocalTime startTime) throws DoesNotExistError {
        this.findTalk(ID).setStartTime(startTime);
    }

    /**
     *
     * @param ID ID of the talk
     * @return list of attendees of the talk with above ID
     * @throws DoesNotExistError if talk DNE
     */
    public ArrayList<String> getTalkAttendees(String ID) throws DoesNotExistError {
        return this.findTalk(ID).getAttendees();
    }

    /**
     *
     * @param ID ID of the talk
     * @param user AttendeeID to be added
     * @throws DoesNotExistError if talk with ID ID DNE
     */
    public void addTalkAttendee(String ID, String user) throws DoesNotExistError {
        this.findTalk(ID).addAttendee(user);
    }

    /**
     *
     * @param ID ID of the talk
     * @param user UserID to be removed from talk with ID ID
     * @throws DoesNotExistError if talk with ID ID does not exist
     */

    public void removeTalkAttendee(String ID, String user) throws DoesNotExistError {
        this.findTalk(ID).removeAttendee(user);
    }

    /**
     *
     * @param ID ID of the talk
     * @return the speaker ID of the speaker of this talk
     * @throws DoesNotExistError if the talk with ID ID is not stored within this manager
     */

    public String getTalkSpeaker(String ID) throws DoesNotExistError {
        return this.findTalk(ID).getSpeaker();
    }

    /*
     */

    /**
     * Changes the Talk ID of a Talk
     * @param ID ID of the talk
     * @param newID the New ID of the talk
     * @throws DoesNotExistError if the talk with this ID does not exist
     */
    public void changeTalkID(String ID, String newID) throws DoesNotExistError {
        this.findTalk(ID).setEventID(newID);
    }

}
