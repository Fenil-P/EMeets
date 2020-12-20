package UseCases;

import Entities.*;
import Exceptions.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Representation of a roomManager, or a manager which stores all rooms
 *
 * @version 1.1.1
 */

public class RoomManager implements java.io.Serializable {
    private ArrayList<Room> rooms;
    private ArrayList<String> ids;

    public RoomManager(){
        rooms = new ArrayList<Room>();
        ids = new ArrayList<>();
    }

    /**
     * Creates a new room with a corresponding schedule and adds them to the list of rooms and schedules respectively
     * @param openHour - int, hour that the room opens
     * @param openMinute - int, minute that the room opens (e.x, if the room opens at 9:30am, openHour == 9, openMinute == 30
     * @param closingHour - int, hour that the room closes
     * @param closingMinute - int, minute that the room closes
     */
    public void addRoom(int openHour, int openMinute, int closingHour, int closingMinute){
        String roomID = this.generateID();
        Room room = new Room(openHour, openMinute, closingHour, closingMinute, roomID, capacity);
        rooms.add(room);
    }

    public String generateID(){
        if(ids == null)
            ids = new ArrayList<>();
        String ID = "Room";
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10000);
        while (ids.contains(ID + randomNum)){
            randomNum = ThreadLocalRandom.current().nextInt(0, 10000);
        }
        ids.add(ID + randomNum);
        return ID + randomNum;
    }


    /**
     * Return Entities.Room with given ID
     * @param ID int, ID of room to find
     * @return Entities.Room with given ID
     */
    public Room findRoom(String ID) throws DoesNotExistError {
        for (Room room : rooms) {
            if (room.getRoomID().equals(ID)) {
                return room;
            }
        }
        throw new DoesNotExistError("This ID does not exist");
    }

    /**
     * Attempts to add attendee to list of registered attendees in talk
     * @param talkID - ID of event to add attendee to
     * @param attendeeID -  ID of attendee to be added
     * @param am - UseCases.AttendeeManager object storing attendees
     * @param tm - UseCases.TalkManager object storing talks
     * @return true - if and only if attendee was successfully added
     */

    public boolean addAttendee(String talkID, String attendeeID, UserManager am, TalkManager tm) throws DoesNotExistError {
        for (Room room: rooms){
            Talk talk = tm.findTalk(talkID);
            Attendee attendee = am.findAttendee(attendeeID);
            if (room.isTalkScheduled(talkID) & talk.getAttendees().size() < room.getCapacity() &
                    am.isAttendeeAvailable(talk.getDate(), talk.getStartTime(), attendee, tm)){
                talk.getAttendees().add(attendeeID);
                return true;
            }
        }
        return false;
    }


    /**
     * Attempts to add talk to schedule if the speaker and room are available
     * @param room - room to add talk to
     * @param talkID - ID of talk to be added
     * @param tm - UseCases.TalkManager object storing talks
     * @param sm - UseCases.SpeakerManager object storing speakers
     * @return true - if and only if room was added to schedule successfully
     */


    public boolean addEventToSchedule(Room room, String talkID, TalkManager tm, SpeakerManager sm) throws DoesNotExistError {
        Talk talk = tm.findTalk(talkID);
        if (this.isRoomAvailable(talk.getDate(),
                talk.getStartTime(), room, tm) && sm.isSpeakerAvailable(talk.getDate(),
                talk.getStartTime(), sm.findSpeaker(talk.getSpeaker()), tm)){
            room.addEvent(talk);
            return true;
        }
        return false;
    }

    /**
     * Attempts to remove talk from schedule
     * @param talkID - String, ID of talk to be removed
     * @param tm - UseCases.TalkManager object storing talks
     * @param am - UseCases.AttendeeManager object storing attendees
     * @param sm - UseCases.SpeakerManager object storing speakers
     * @return true - if and only if talk was found in the schedule and successfully removed
     */

    public boolean removeEventFromSchedule(String talkID, TalkManager tm, AttendeeManager am, SpeakerManager sm) throws DoesNotExistError {
        Talk talk = tm.findTalk(talkID);
        for (Room room: rooms){
            if (room.isTalkScheduled(talkID)){
                room.removeEvent(talk);
                for (String attendeeID: talk.getAttendees()){
                    am.removeEvent(am.findAttendee(attendeeID), talk);
                }
                sm.findSpeaker(talk.getSpeaker()).removeHostEvent(talkID);
                return true;
            }

        }
        return false;
    }

    /**
     * Prints talks in a schedule with a given title
     * @param title - String, title to be checked
     * @param tm - UseCases.TalkManager object storing talks
     */
    public void displayTalksByTitle(String title, TalkManager tm) throws DoesNotExistError {
        ArrayList<Talk> allTalksWithTitle = new ArrayList<Talk>(this.getEventsByTitle(title, tm));
        for (Talk talkWithTitle: allTalksWithTitle){
            System.out.println(talkWithTitle);
        }
    }

    /**
     * Prints talks in a schedule with a given Entities.Speaker
     * @param speakerID - String, the ID of the speaker whose talks to be found
     * @param tm - UseCases.TalkManager object storing talks
     */
    public void displayTalksBySpeaker(String speakerID, TalkManager tm, SpeakerManager sm) throws DoesNotExistError {
        ArrayList<Talk> allTalksBySpeaker = new ArrayList<Talk>(this.getEventsBySpeaker(speakerID, tm, sm));
        for (Talk talkBySpeaker: allTalksBySpeaker){
            System.out.println(talkBySpeaker);
        }
    }

    /**
     * Prints talks scheduled on a given date and time
     * @param date - Date object, date to be checked
     * @param time - LocalTime object, time to be checked
     * @param tm - UseCases.TalkManager object that stores all talks
     */

    public void displayTalksByTime(GregorianCalendar date, LocalTime time, TalkManager tm) throws DoesNotExistError {
        ArrayList<Talk> allTalksAtTime = new ArrayList<Talk>(this.getEventsByTime(date, time, tm));
        for (Talk talkAtTime: allTalksAtTime){
            System.out.println(talkAtTime);
        }
    }

    /**
     * Displays entire schedule
     * @param tm - UseCases.TalkManager object which stores talks
     */
    public String displaySchedule(TalkManager tm) throws DoesNotExistError {
        String fullSchedule = "";
        for (Room room: rooms){
            String scheduleString = "Schedule for " + room.getRoomID() + ": ";
            for (Map.Entry<GregorianCalendar, ArrayList<String>> talksOnDate: room.getSchedule().entrySet()){
                String talksString = "{";
                for (String talkID: talksOnDate.getValue()){
                    Talk talk = tm.findTalk(talkID);
                    talksString = talksString + talk.toString() + ", ";
                }
                talksString = talksString.substring(0, talksString.length() - 2);
                talksString = talksString + "}";
                scheduleString = scheduleString + talksOnDate.getKey().toZonedDateTime().format(DateTimeFormatter.ofPattern("d MMM uuuu")) + ": " + talksString + "\n";
            }
            fullSchedule = fullSchedule + scheduleString;
        }
        return fullSchedule;
    }


    /**
     * Check if room is available on given date and time
     * @param date - Date object, date to be checked
     * @param time - LocalTime object, time to be checked
     * @param room - Entities.Room object, room to be checked if available
     * @param tm - UseCases.TalkManager object storing talks
     * @return true if and only if room is available at given time
     */
    public boolean isRoomAvailable(GregorianCalendar date, LocalTime time, Room room, TalkManager tm) throws DoesNotExistError {
        if (time.compareTo(room.getClosingTime().minusHours(1)) > 0 || time.compareTo(room.getOpenTime()) < 0) {
            return false;
        }
        LocalTime time1 = time.plusHours(1);
        if(room.getSchedule().get(date) == null)
            return true;
        for (String talkID: room.getSchedule().get(date)) {
            LocalTime eventTime = tm.findTalk(talkID).getStartTime();
            LocalTime eventTime1 = tm.findTalk(talkID).getStartTime().plusHours(1);
            if ((eventTime1.compareTo(time) > 0 && eventTime1.compareTo(time1) < 0) || (eventTime.compareTo(time) > 0 && eventTime.compareTo(time1) < 0)){
                return false;
            }
        }
        return true;
    }

    /**
     * Return list of talks scheduled on given date and time
     * @param date - Date object, date to be checked
     * @param time - LocalTime object, time to be checked
     * @param tm - UseCases.TalkManager object storing talks
     * @return ArrayList</Entities.Talk>, list of events scheduled on date at time
     */
    public ArrayList<Talk> getEventsByTime(GregorianCalendar date, LocalTime time, TalkManager tm) throws DoesNotExistError {
        ArrayList<Talk> eventsOnDate = new ArrayList<Talk>();
        for (Room room: rooms){
            if (room.getSchedule().containsKey(date)){
                for (String talkID: room.getSchedule().get(date)){
                    if (tm.findTalk(talkID).getStartTime().equals(time)){
                        eventsOnDate.add(tm.findTalk(talkID));
                    }
                }
            }
        }
        return eventsOnDate;
    }

    /**
     * Gets talks with given title
     * @param title - String, title of talks to be found in schedule
     * @param tm - UseCases.TalkManager object storing talks
     * @return ArrayList</Entities.Talk> containing talks with given title
     */
    public ArrayList<Talk> getEventsByTitle(String title, TalkManager tm) throws DoesNotExistError {
        ArrayList<Talk> eventsWithTitle = new ArrayList<Talk>();
        for (Room room: rooms) {
            for (Map.Entry<GregorianCalendar, ArrayList<String>> eventsOnDate : room.getSchedule().entrySet()) {
                for (String talkID : eventsOnDate.getValue()) {
                    if (tm.findTalk(talkID).getTitle().equals(title)) {
                        eventsWithTitle.add(tm.findTalk(talkID));
                    }
                }
            }
        }
        return eventsWithTitle;
    }

    /**
     * Gets talks with given speaker
     * @param speakerID - String which is the ID of the Entities.Speaker whose talks to be found
     * @param tm - UseCases.TalkManager object storing talks
     * @return ArrayList</Entities.Talk> containing talks given by speaker
     */
    public ArrayList<Talk> getEventsBySpeaker(String speakerID, TalkManager tm, SpeakerManager sm) throws DoesNotExistError {
        ArrayList<Talk> eventsBySpeaker = new ArrayList<Talk>();
        for (Room room: rooms){
            for  (Map.Entry<GregorianCalendar, ArrayList<String>> eventsOnDate: room.getSchedule().entrySet()) {
                for (String talkID : eventsOnDate.getValue()) {
                    if (sm.findSpeaker(tm.findTalk(talkID).getSpeaker()).getSpeakerID().equals(speakerID)) {
                        eventsBySpeaker.add(tm.findTalk(talkID));
                    }
                }
            }
        }
        return eventsBySpeaker;
    }

    /**
     *
     * @return list of all rooms
     */
    public ArrayList<Room> getRooms(){
        return rooms;
    }


    /**
     *
     * @return all of the room IDs in the system
     */
    public ArrayList<String> getAllRooms() {
        ArrayList<String> ts = new ArrayList<>();
        for (Room t : this.rooms){
            ts.add(t.getRoomID());
        }
        return ts;
    }


}
