package Controllers;

import Entities.*;
import Exceptions.*;

import java.util.ArrayList;

/**
 * Handles registration purposes for the use case storage class.
 */

public class Registrar {

    private UseCaseStorage usecasestorage;

    public Registrar(UseCaseStorage usecasestorage) {
        this.usecasestorage = usecasestorage;
    }

    /**
     * Registers attendee for a talk.
     * @param talkID - ID of event to add attendee to
     * @param attendeeID -  ID of attendee to be added
     */
    public void register(String talkID, String attendeeID) throws DoesNotExistError {
        this.usecasestorage.getRoomManager().addAttendee(talkID, attendeeID, this.usecasestorage.getAttendeeManager(),
                this.usecasestorage.getTalkManager());
    }

    /**
     * Deregisters attendee from a talk.
     * @param userID - The attendee identification
     * @param talkID - The talk identification
     */
    public void cancel(String userID, String talkID) throws DoesNotExistError {
        this.usecasestorage.getAttendeeManager().removeEvent(this.usecasestorage.getAttendeeManager().findAttendee(userID), this.usecasestorage.getTalkManager().findTalk(talkID));
    }

    /**
     * Returns the list of events that an attendee is registered in.
     * @param attendee - The attendee in question.
     * @return list of the attendee's registered talks.
     */
    public ArrayList<String> getRegisteredTalks(Attendee attendee) {
        return this.usecasestorage.getAttendeeManager().getRegisteredTalks(attendee);
    }

}