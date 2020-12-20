package Controllers;

/**
 * Stores other controllers as a facade.
 *
 * @version 1.1
 */

public class UserAccountSystem {

    private Registrar registrar;
    private Messenger messenger;
    private TalkOrganizer talkOrganizer;
    private ScheduleViewer scheduleViewer;
    private UserAccount userAccount;

    /**
     *
     * @param useCaseStorage takes in a UseCaseStorage object and stores it
     */
    // create instances of the controllers
    public UserAccountSystem(UseCaseStorage useCaseStorage) {

        this.userAccount = new UserAccount(useCaseStorage);
        this.registrar = new Registrar(useCaseStorage);
        this.messenger = new Messenger();
        this.scheduleViewer = new ScheduleViewer(userAccount);
        this.talkOrganizer = new TalkOrganizer(userAccount, useCaseStorage);
    }

    /**
     *
     * @return the useraccount controller object
     */

    public UserAccount getUserAccount() {
        return this.userAccount;
    }


//    public UseCaseStorage getUseCaseStorage() {
//        return useCaseStorage;
//    }

    /**
     *
     * @return the registrar object
     */
    public Registrar getRegistrar() {
        return registrar;
    }

    /**
     *
     * @return the messenger object controller
     */

    public Messenger getMessenger() {
        return messenger;
    }

    /**
     *
     * @return the talkorganizer object controller stored
     */
    public TalkOrganizer getTalkOrganizer() {
        return talkOrganizer;
    }

    /**
     *
     * @return the scheduleviewer object stored.
     */
    public ScheduleViewer getScheduleViewer() {
        return scheduleViewer;
    }

}
