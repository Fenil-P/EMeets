package Controllers;

import Entities.*;
import Exceptions.*;
import UseCases.RoomManager;
import UseCases.SpeakerManager;
import UseCases.TalkManager;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Handles talk organizing functionality for the PresenterMain access point.
 *
 * @version 1.0
 */
public class TalkOrganizer {

    private UserAccount ua;
    private TalkManager tm;
    private RoomManager rm;
    private SpeakerManager sm;

    public TalkOrganizer(UserAccount useraccount, UseCaseStorage usecasestorage) {
        this.ua = useraccount;
        this.tm = usecasestorage.getTalkManager();
        this.rm = usecasestorage.getRoomManager();
        this.sm = usecasestorage.getSpeakerManager();

    }

    /**
     * Add a talk to the conference.
     * @param title - the title of the talk.
     * @param date - the date of the talk.
     * @param startTime - the time the talk starts.
     * @param speaker - the speaker of the talk.
     * @param room - the room where the talk takes place.
     */
    public void addTalk(String title, GregorianCalendar date, LocalTime startTime, String speaker,
                        Room room) throws DoesNotExistError {
        if (this.ua.isOrganizer()) {
            String talkID = this.tm.generateID();
            this.tm.createTalk(title, date, startTime, speaker, talkID);
            this.rm.addEventToSchedule(room, talkID, this.tm, this.sm);
        }
    }

    /**
     * Displays available slots where an organizer can schedule an event with the given speaker
     * @param speakerName - String, name of speaker to host talk
     * @throws DoesNotExistError
     */
    public void getAvailableSlots(String speakerName) throws DoesNotExistError {
        Entities.Speaker speaker = this.sm.findSpeakerByName(speakerName);
        if (this.ua.isOrganizer()) {
            ArrayList<String> availableSlots = new ArrayList<String>();
            ArrayList<Entities.Room> rooms = this.rm.getRooms();
            for (Room room : rooms) {
                for (Map.Entry<GregorianCalendar, ArrayList<String>> eventsOnDate : room.getSchedule().entrySet()) {
                    LocalTime time = room.getOpenTime();
                    while (time.compareTo(room.getClosingTime()) < 0) {
                        if (this.rm.isRoomAvailable(eventsOnDate.getKey(), time, room,
                                this.tm) && this.sm.isSpeakerAvailable(eventsOnDate.getKey(), time, speaker, this.tm)) {
                            String availability = "Room " + room.getRoomID() + " and " + speaker.getName() +
                                    " are available on " + eventsOnDate.getValue().toString() + " at " +
                                    time.toString();
                            availableSlots.add(availability);
                        }
                        time = time.plusHours(1);
                    }
                }
            }
            for (String availability: availableSlots){
                System.out.println(availability);
            }
        }
    }

}
