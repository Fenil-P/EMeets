package Controllers;

import UseCases.*;


/**
 * Generates a use case storage facade class for the use of the presenter in terms of access.
 *
 * @version 0.1
 */

public class UseCaseFactory {
    private UseCaseStorage ucs;

    public UseCaseFactory(){
        UseCases.RoomManager rm = new RoomManager();
        UseCases.AttendeeManager am = new AttendeeManager();
        UseCases.InboxManager im = new InboxManager();
        UseCases.SpeakerManager sm = new SpeakerManager();
        UseCases.TalkManager tm = new TalkManager();
        ucs = new UseCaseStorage(tm, rm, sm, am, im);
    }
    public UseCaseStorage getUseCaseStorage() {
        return ucs;
    }
}
