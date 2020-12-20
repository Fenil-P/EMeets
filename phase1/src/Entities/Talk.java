package Entities;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalTime;
import java.util.GregorianCalendar;

/**
 * A representation of a talk, which is an event within our system
 *
 * @version 1.1
 */

public class Talk implements java.io.Serializable {
    // Stores Entities.Speaker (only Entities.Speaker attached to one talk at a time)
    //startTime
    //title
    //date
    //attendees
    //getter for emails of attendees registered in Entities.Talk
    //getters, setters
    private String title;
    private GregorianCalendar date;
    private LocalTime startTime;
    private ArrayList<String> attendeesId;
    private String speakerId;
    private String EventID;

    /**
     * Initializes a Talk object
     * @param title the title of the talk
     * @param date the date of the talk
     * @param startTime when the talk starts
     * @param speakers the speakers in the talk
     * @param EventID the eventID of the talk
     */

    public Talk(String title, GregorianCalendar date, LocalTime startTime, String speakers, String EventID) {
        this.title = title;
        this.EventID = EventID;
        this.date = date;
        this.startTime = startTime;
        this.attendeesId = new ArrayList<>();
        this.speakerId = speakers;
    }

    /**
     *
     * @return string representation of the talk
     */
    @Override
    public String toString() {
        // Talk: Orange, Date: Wed Nov 04 2020, Start time: 20:05:54.649, Attendees: [], Speakers: []
        return "Talk: " + title +
                ", Date: " + date.toZonedDateTime().format(DateTimeFormatter.ofPattern("d MMM uuuu"))+
                ", Start time: " + startTime +
                ", Attendees: " + attendeesId +
                ", Speakers: " + speakerId;
    }

    /**
     *
     * @return title of the talk event
     */
    public String getTitle() {
        return this.title;
    }

    /**
     *
     * @param title set the title of the event
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the date of the event, in calendar format
     */

    public GregorianCalendar getDate() {
        return this.date;
    }

    /**
     * set the date:
     * @param date set the date of the event.
     */

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    /**
     *
     * @return start time of the event talk
     */
    public LocalTime getStartTime(){
        return this.startTime;
    }

    /**
     * sets start time:
     * @param startTime the start time of the event
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }


    /**
     *
     * @return list of all attendees
     */
    public ArrayList<String> getAttendees() {
        return this.attendeesId;
    }

    /**
     *
     * @param user add an attendeeID to a talk
     */
    public void addAttendee(String user){
        this.attendeesId.add(user);
    }

    /**
     *
     * @param user remove this Attendee with ID user from event
     */
    public void removeAttendee(String user) {
        this.attendeesId.remove(user);
    }

    /**
     *
     * @return the talk event speaker's ID
     */
    public String getSpeaker() {
        return this.speakerId;
    }

    /**
     *
     * @param speakerId change the speaker to another speaker
     */

    public void changeSpeaker(String speakerId) {
        this.speakerId = speakerId;
    }

    /**
     *
     * @return the eventID of this Talk Event
     */
    public String getEventID() {
        return this.EventID;
    }

    /**
     *
     * @param eventID set the eventID of the talk
     */
    public void setEventID(String eventID) {
        this.EventID = eventID;
    }
}
