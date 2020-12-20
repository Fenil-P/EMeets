package Controllers;

import Exceptions.DoesNotExistError;

import java.time.LocalTime;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Map;

/**
 * Handles Viewing of schedules as a controller class
 */

public class ScheduleViewer {
    Controllers.UserAccount userAccount;


    public ScheduleViewer(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * Returns representation of the entire schedule
     * @param ucs-UseCaseStorage
     * @return String representing the entire schedule
     * @throws DoesNotExistError
     * @throws DoesNotExistError
     */
    public String viewEntireSchedule(UseCaseStorage ucs) throws DoesNotExistError, DoesNotExistError {
        return ucs.getRoomManager().displaySchedule(ucs.getTalkManager());
    }

    /**
     * Displays a list of events with the given title or speaker name
     * @param searchType- Must be either "Title" or "Speaker Name", whichever type the user wants to search events by
     * @param searchValue- the title of an event, if searchType is "Title", or the name of a speaker, if searchType is "Speaker name"
     * @param ucs - UseCaseStorage
     * @throws DoesNotExistError
     */
    public void viewEvents(String searchType, String searchValue, UseCaseStorage ucs) throws DoesNotExistError {
        if (searchType.equals("Title")) {
            ucs.getRoomManager().displayTalksByTitle(searchValue, ucs.getTalkManager());
        }
        if (searchType.equals("Speaker name")) {
            ucs.getRoomManager().displayTalksBySpeaker(ucs.getSpeakerManager().findSpeakerByName(searchValue).getSpeakerID(),
                    ucs.getTalkManager(), ucs.getSpeakerManager());
        }
    }

    /**
     * Displays a list of events on the given date at the given time
     * @param month - integer, searching for events in this month
     * @param day - integer, searching for events on this day
     * @param year - integer, searching for events on this year
     * @param hour - integer, searching for events at this hour
     * @param minute - integer, searching for events on this minute
     * @param ucs - UseCaseStorage
     * @throws DoesNotExistError
     */
    public void viewEvents(int month, int day, int year, int hour, int minute, UseCaseStorage ucs) throws DoesNotExistError {
        GregorianCalendar date = new GregorianCalendar(year, month, day);
        LocalTime time = java.time.LocalTime.of(hour, minute);
        ucs.getRoomManager().displayTalksByTime(date, time, ucs.getTalkManager());
    }

    /**
     * Displays events that this user is registered for
     * @param ucs - UseCaseStorage
     * @throws DoesNotExistError
     */
    public void viewMyEvents(UseCaseStorage ucs) throws DoesNotExistError {
        Entities.Attendee attendee = ucs.getAttendeeManager().findAttendee(userAccount.getUserID());
        for (String talkID : ucs.getAttendeeManager().getRegisteredTalks(attendee)) {
            System.out.println(ucs.getTalkManager().findTalk(talkID));
        }
    }

    /**
     * Displays events that are available for this user to register for
     * @param ucs - UseCaseStorage
     * @throws DoesNotExistError
     */
    public void viewAvailableEvents(UseCaseStorage ucs) throws DoesNotExistError {
        ArrayList<String> availableEvents = new ArrayList<String>();
        ArrayList<Entities.Room> rooms = ucs.getRoomManager().getRooms();
        for (Entities.Room room : rooms) {
            for (Map.Entry<GregorianCalendar, ArrayList<String>> eventsOnDate : room.getSchedule().entrySet()) {
                for (String talkID : eventsOnDate.getValue()) {
                    Entities.Talk talk = ucs.getTalkManager().findTalk(talkID);
                    Entities.Attendee attendee = ucs.getAttendeeManager().findAttendee(userAccount.getUserID());
                    if (ucs.getRoomManager().addAttendee(talkID, userAccount.getUserID(), ucs.getAttendeeManager(), ucs.getTalkManager())) {
                        availableEvents.add(talkID);
                        ucs.getAttendeeManager().removeEvent(attendee, talk);
                    }
                }
            }
        }
        for (String talkID : availableEvents) {
            System.out.println(ucs.getTalkManager().findTalk(talkID));
        }
    }
}
