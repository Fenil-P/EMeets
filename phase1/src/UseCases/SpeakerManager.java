package UseCases;

import Entities.*;
import Exceptions.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Use case in charge of creation of speakers and responsible for managing speakers.
 *
 * @version 0.1
 */

public class SpeakerManager implements java.io.Serializable {
    private ArrayList<Speaker> speakerList;
    private ArrayList<String> listIDs;

    public SpeakerManager() {
        this.speakerList = new ArrayList<Speaker>();
        this.listIDs = new ArrayList<String>();
    }

    /**
     *
     * @return a randomized, unique ID for each speaker created
     */
    public String generateID(){
        String ID = "Speaker";
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10000);
        while (this.listIDs.contains(ID + randomNum)){
            randomNum = ThreadLocalRandom.current().nextInt(0, 10000);
        }
        listIDs.add(ID + randomNum);
        return ID + randomNum;
    }

    /**
     *
     * @param email the email of the speaker to be created
     * @param name the name of the speaker
     * @return whether the speaker was created or not.
     */

    public boolean createSpeaker(String email, String name) {
        String userID = this.generateID();
        Speaker speakerTBC = new Speaker(email, userID, name);
        ArrayList<String> list_IDs = new ArrayList<String>();

        for ( Speaker speaker: this.speakerList) {
            list_IDs.add(speaker.getUserID());
        }
        if (list_IDs.contains(speakerTBC.getUserID())) {
            return false;
        }
        else {
            this.speakerList.add(speakerTBC);
            return true;
        }
    }

    /**
     *
     * @param email the email of the speaker to be created
     * @param name the name of the speaker
     * @param password the name of the speaker
     * @return whether the speaker was created or not.
     */

    public boolean createSpeaker(String email, String name, String password) {
        String userID = this.generateID();
        Speaker speakerTBC = new Speaker(email, userID, name, password);
        ArrayList<String> list_IDs = new ArrayList<String>();

        for ( Speaker speaker: this.speakerList) {
            list_IDs.add(speaker.getUserID());
        }
        if (list_IDs.contains(speakerTBC.getUserID())) {
            return false;
        }
        else {
            this.speakerList.add(speakerTBC);
            this.listIDs.add(userID);
            return true;
        }
    }

    /*
    public boolean createSpeaker(String email, String name, String userID,
    String password, ArrayList<String> Friends) throws Errors.DoesNotExistError {
        Entities.Speaker speakerTBC = new Entities.Speaker(email, userID, name);
        speakerTBC.setPassword(password);
        for (String friendID: Friends) {
            speakerTBC.addtoContacts(friendID);
        }
        ArrayList<String> list_IDs = new ArrayList<String>();
        for ( Entities.Speaker speaker: this.speakerList) {
            list_IDs.add(speaker.getUserID());
        }
        if (list_IDs.contains(speakerTBC.getUserID())) {
            return false;
        }
        else {
            this.speakerList.add(speakerTBC);
            return true;
        }
    }
    */

    /**
     * queries if a speaker is available at a certain date
     * @param date date of query
     * @param time time of query
     * @param speaker the speaker
     * @param tm the talkmanager object
     * @return if the speaker is available at that time
     * @throws DoesNotExistError if speaker does not exist
     */
    public boolean isSpeakerAvailable(GregorianCalendar date, LocalTime time, Speaker speaker, TalkManager tm)
            throws DoesNotExistError {
        LocalTime time1 = time.plusHours(1);
        for (String talkID: speaker.getHostingTalks()) {

            try {
                Talk talk = tm.findTalk(talkID);}
            catch(Exception ignored){

            }

            LocalTime eventTime = tm.findTalk(talkID).getStartTime();
            LocalTime eventTime1 = tm.findTalk(talkID).getStartTime().plusHours(1);
            if ((eventTime1.compareTo(time) > 0 && eventTime1.compareTo(time1) < 0) ||
                    (eventTime.compareTo(time) > 0 && eventTime.compareTo(time1) < 0)){
                return false;
            }
        }
        return true;
    }

    /**
     * Finds speaker by ID
     * @param ID finds speaker by this ID
     * @return the speaker object
     * @throws DoesNotExistError if speaker does not exist
     */
    public Speaker findSpeaker(String ID) throws DoesNotExistError {
        for (Speaker speaker : speakerList) {
            if (speaker.getSpeakerID().equals(ID)) {
                return speaker;
            }
            if (speaker.getName().equals(ID)) {
                return speaker;
            }
        }
        throw new DoesNotExistError("This ID does not exist");
    }

    /**
     * Finds speaker by their name
     * @param name name of the speaker
     * @return the Speaker Object
     * @throws DoesNotExistError if the speaker does not exist
     */
    public Speaker findSpeakerByName(String name) throws DoesNotExistError {
        for (Speaker speaker: speakerList){
            if (speaker.getName().equals(name)){
                return speaker;
            }
        }
        throw new DoesNotExistError("There is no speaker with this name");
    }

    /**
     * Set a speaker object's ID
     * @param SpeakerName the name of the speaker
     * @param SpeakerID the ID of the speaket to be set
     * @throws DoesNotExistError if speaker DNE
     */

    public void setSpeakerID(String SpeakerName, String SpeakerID) throws DoesNotExistError {
        Speaker speakerfound = this.findSpeakerByName(SpeakerName);
        speakerfound.setUserID(SpeakerID);
    }

    /**
     * Set speaker object's name
     * @param SpeakerName speaker name to be found
     * @param newName new name of the speaker
     * @throws DoesNotExistError if speaker DNE
     */
    public void setSpeakerName(String SpeakerName, String newName) throws DoesNotExistError {
        Speaker speakerfound = this.findSpeakerByName(SpeakerName);
        speakerfound.setName(newName);
    }

    /**
     * Set speaker object's email
     * @param SpeakerName speaker name to be found
     * @param newEmail new Email of the speaker
     * @throws DoesNotExistError if speaker DNE
     */
    public void setSpeakerEmail(String SpeakerName, String newEmail) throws DoesNotExistError {
        Speaker speakerfound = this.findSpeakerByName(SpeakerName);
        speakerfound.setName(newEmail);
    }

    /**
     * Set speaker object's email
     * @param speaker speaker ID to be found
     * @param newpass new password of the speaker
     * @throws DoesNotExistError if speaker DNE
     */

    public void setSpeakerPassword(String speaker, String newpass) throws DoesNotExistError {
        Speaker speakerfound = this.findSpeaker(speaker);
        speakerfound.setPassword(newpass);
    }

    /**
     *
     * @param speaker speaker ID
     * @return list of speakers talks
     * @throws DoesNotExistError if speaker DNE
     */
    public ArrayList<String> getSpeakerEvents(String speaker) throws DoesNotExistError {
        Speaker speakerfound = this.findSpeaker(speaker);
        return speakerfound.getHostingTalks();
    }


    /**
     *
     * @param speakerName name of speaker
     * @return ID of speaker
     * @throws DoesNotExistError if speaker by name of speakerName DNE
     */
    public String getSpeakerID(String speakerName) throws DoesNotExistError {
        return findSpeakerByName(speakerName).getUserID();
    }

    /**
     *
     * @param speakerID ID of speaker
     * @return password of the speaker
     * @throws DoesNotExistError if speaker with above ID DNE
     */
    public String getPasswordViaID(String speakerID) throws DoesNotExistError {
        Speaker speaker = this.findSpeaker(speakerID);
        return speaker.getPassword();
    }

    /**
     *
     * @param speakerID ID of speaker
     * @return name of speaker with above ID
     * @throws DoesNotExistError if speaker with above ID DNE
     */
    public String getNameViaID(String speakerID) throws DoesNotExistError {
        Speaker speaker = this.findSpeaker(speakerID);
        return speaker.getName();
    }

    /**
     *
     * @param speakerID ID of the speaker
     * @return email of the speaker with above ID
     * @throws DoesNotExistError if the speaker with above ID DNE
     */
    public String getEmailViaID(String speakerID) throws DoesNotExistError {
        Speaker speaker = this.findSpeaker(speakerID);
        return speaker.getEmail();
    }

    /**
     *
     * @return list of all speaker in event
     */
    public ArrayList<Speaker> getSpeakerList(){return speakerList;}

    /**
     *
     * @param speakerID ID of speaker
     * @param friendID ID of the attendee
     * @param am attendee manager of attendee
     * @throws DoesNotExistError if cannot find the friend =(
     */
    // A speaker adds a user as a friend
    public void setContactViaID(String speakerID, String friendID, AttendeeManager am) throws DoesNotExistError {
        Speaker speaker = findSpeaker(speakerID);
        try {
            Attendee attendeeAdded = am.findAttendee(friendID);
        } catch (DoesNotExistError e) {
            Speaker speakerAdded = findSpeaker(friendID);
        }
        speaker.addtoContacts(friendID);
    }

    /**
     *
     * @param speakerID ID of a speaker
     * @param friendID ID of the previously friendID
     * @throws DoesNotExistError if friend does not exist =)
     */
    // Removes friend
    public void removeContactViaID(String speakerID, String friendID) throws DoesNotExistError {
        Speaker speaker = findSpeaker(speakerID);
        speaker.removefromContacts(friendID);
    }
}
