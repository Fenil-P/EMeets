package Controllers;

import UseCases.*;

/**
 * Class that stores all use cases and allows for serialization of the use cases needed to maintain data.
 *
 * @version 1.0
 */

public class UseCaseStorage implements java.io.Serializable {
    private TalkManager tm;
    private RoomManager rm;
    private SpeakerManager sm;
    private AttendeeManager am;
    private InboxManager im;

    /**
     *
     * @param tm a talkmanager to be stored and accessed
     * @param rm a roommanager to be stored and accessed
     * @param sm a speakermanager to be stored and accessed
     * @param am an attendeemanager to be stored and accessed
     * @param im an inboxmanager to be stored and accessed
     */
    public UseCaseStorage(TalkManager tm, RoomManager rm, SpeakerManager sm, AttendeeManager am, InboxManager im){
        this.tm = tm;
        this.rm = rm;
        this.sm = sm;
        this.am = am;
        this.im = im;
    }

    /**
     *
     * @return the stored attendeemanager
     */

    public AttendeeManager getAttendeeManager() {
        return am;
    }


    /**
     *
     * @return the stored talk manager
     */
    public TalkManager getTalkManager(){
        return tm;
    }

    /**
     *
     * @return the stored speakermanager use case
     */
    public SpeakerManager getSpeakerManager(){
        return sm;
    }

    /**
     *
     * @return the stored inboxmanager
     */
    public InboxManager getInboxManager(){
        return im;
    }


    /**
     *
     * @return the stored roommanager use case
     */
    public RoomManager getRoomManager(){
        return rm;
    }

    public UserManager getUserManager() {
    }
}
