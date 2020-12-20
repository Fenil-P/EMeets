package Controllers;


import Exceptions.DoesNotExistError;

import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import Gateway.*;

/**
 * <h1>Presenter Class</h1>
 * The main gateway for the program, this is a command line interface *
 * @author  Warren Liu
 * @version 0.1
 */

public class PresenterMain {

    public PresenterMain() throws FileNotFoundException, DoesNotExistError {

        Scanner input = new Scanner(System.in);

        // Useful Variables:
        // is active: is the system active?
        // isLoggedIn: is a user logged in?

        // Generate all needed controller first
        Serializer gate = new Serializer();
        UseCaseStorage ucs = gate.Read();

//        UseCaseFactory ucf = new UseCaseFactory();
//        UseCaseStorage ucs = ucf.getUseCaseStorage();
        UserAccountSystem uas = new UserAccountSystem(ucs);

        // Generate the needed use cases
        // Done

        // Store them in Use Case Storage

        System.out.println("Initializing Assets and Objects");

        // Initialize the Serialized .csv

        // AppGateway ag = new AppGateway(ucs);

        System.out.println("All objects initialized, data has been loaded.");
        System.out.println("Welcome to the TextChat Command Line Interface!");

        // System is Now initialized. We must now begin the main Loop

        boolean isActive = true;
        boolean isloggedIn = false;

        while (isActive) {

            boolean notLoggedIn = true;

            System.out.println("If you would like to log in, please press 1");
            System.out.println("Else, please enter 0 to quit the program and save the data.");
            System.out.println("If you do not do so, you will lose all data in your session");

            Boolean invalidinput1 = true;
            Scanner scanner_bruh = new Scanner(System.in);
            while (invalidinput1) {
                System.out.println("Enter your option:");
                try {
                    int inputed = scanner_bruh.nextInt();
                    if (inputed == 1) {
                        System.out.println("continuing");
                        invalidinput1 = false;
                    } else if (inputed == 0) {
                        isActive = false;
                        invalidinput1 = false;
                    } else {
                        System.out.println("Invalid Input");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input");
                    scanner_bruh.nextLine(); // clear the scanner
                }
            }


            if (isActive) {

                while (notLoggedIn) {

                    System.out.println("Please log in, or create an account.");
                    System.out.println("Please enter 1 to log in, or 2 to create an account.");
                    System.out.println("Alternatively, hit 0, then 0 again to abort login");

                    boolean chosen = false;
                    boolean loginmode = false;
                    boolean exitcondition = false;

                    while (!chosen) {

                        Scanner input2 = new Scanner(System.in);

                        try {
                            int choice = input2.nextInt();

                            if (choice == 1) {
                                loginmode = true;
                                chosen = true;
                            } else if (choice == 2) {
                                loginmode = false;
                                chosen = true;
                            } else if (choice == 0) {
                                exitcondition = true;
                                chosen = true;
                            } else {
                                System.out.println("Invalid Input");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Unacceptable input, please reenter");
                        }

                    }


                    // Find a Way for them to exit the login death bubble
                    if (exitcondition) {notLoggedIn = false;}

                    if (loginmode) {
                        Scanner input3 = new Scanner(System.in);

                        System.out.println("you are in login Mode");
                        System.out.println("Please input your name:");
                        String name1 = input3.nextLine();
                        System.out.println("Please input your email:");
                        String email1 = input3.nextLine();
                        System.out.println("Please enter your password:");
                        String password1 = input3.nextLine();

                        isloggedIn = uas.getUserAccount().login(email1, name1, password1);


                    } else if (!exitcondition){
                        Scanner input4 = new Scanner(System.in);

                        System.out.println("You are in Account Creation Mode");
                        System.out.println("Please input your desired name:");
                        String name = input4.nextLine();
                        System.out.println("Please input your desired email:");
                        String email = input4.nextLine();
                        System.out.println("Please enter your desired password:");
                        String password = input4.nextLine();


                        boolean typeinput = false;
                        while (!typeinput) {

                            System.out.println("Please indicate if you want to create an attendee account (1) or organizer Account (2)");
                            int choice = input.nextInt();

                            if (choice == 1) {
                                uas.getUserAccount().registerAttendee(email, name, password);
                                typeinput = true;
                            } else if (choice == 2) {
                                uas.getUserAccount().registerOrganizer(email, name, password);
                                typeinput = true;
                            } else {
                                System.out.println("Invalid Input");
                            }
                        }
                        System.out.println("Account Created");
                        isloggedIn = uas.getUserAccount().login(email, name, password);
                    }


                    if (isloggedIn) {
                        notLoggedIn = false;
                        isloggedIn = true;
                    } else {
                        System.out.println("login unsuccessful");
                    }
                }




                while (isloggedIn) {


                    // After logging in, the client will be prompted to enter an integer corresponding to the

                    System.out.println("Please See a list of below options to operate:");
                    System.out.println("Input a number to select an option");

                    // Other Functions Here

                    // Display a menu based on whether the user is a Speaker, Organizer, or Attendee

                    // For all:
                    // set/get email, password, name, add/remove friends, logout
                    // view entire schedule
                    // message a specific attendee/organizer/speaker, view inbox
                    System.out.println("1 - Change your email\n" +
                            "2 - Change your name\n" +
                            "3 - Change your password\n" +
                            "4 - View your email\n" +
                            "5 - View your name\n" +
                            "6 - View your password\n" +
                            "7 - Add a contact\n" +
                            "8 - Remove a contact\n" +
                            "9 - View entire schedule\n" +
                            "10 - Message someone\n" +
                            "11 - View messages\n" +
                            "12 - View contacts\n" +
                            "13 - Logout"); // 1-13 general options

                    if (uas.getUserAccount().isSpeaker()) {
                        // message an attendee, message all attendees at one or multiple of their talks
                        // view the entire schedule, view their talks
                        System.out.println("30 - View talks you are giving\n" +
                                "31 - Message all attendees of multiple of your talks\n" +
                                "32 - Message all attendees of a talk\n");
                        // print the specific options: 14+

                    } else if (uas.getUserAccount().isOrganizer()) {
                        // create rooms, speakers
                        // add a speaker to a room for an hour
                        // message all speakers, message all attendees
                        //
                        System.out.println("40 - Create a new Room\n" +
                                "41 - Create a new Speaker\n" +
                                "42 - Schedule a Talk\n" +
                                "43 - Message all speakers\n" +
                                "44 - Message all Attendees\n");
                        // print the specific options: 14+
                    } else if (uas.getUserAccount().isAttendee()) {
                        // view talks that have capacity
                        // sign up/cancel spot in talks
                        System.out.println("60 - View Talks available to sign up\n" +
                                "61 - View Talks already signed up in\n" +
                                "62 - Sign Up for a Talk\n" +
                                "63 - Cancel spot in a Talk\n");
                        // print the specific options: 14+
                    }

                    // Decide what to do based on the option picked
                    boolean valid  = false;
                    int selection = 0;
                    do {
                        try {
                            selection = input.nextInt();
                        } catch(Exception ex){
                            System.out.println("Please select a number only");
                            continue;
                        }
                        if (uas.getUserAccount().isSpeaker()) {
                            valid = (1 <= selection && selection < 14) || (30 <= selection && selection <= 32);
                        } else if (uas.getUserAccount().isOrganizer()) {
                            valid = (1 <= selection && selection < 14) || (40 <= selection && selection <= 44);
                        } else if (uas.getUserAccount().isAttendee()) {
                            valid = (1 <= selection && selection < 14) || (60 <= selection && selection <= 63);
                        } else {
                            System.out.println("Please select a valid option");
                        }
                    } while (!valid);

                    // Clear scanner for new input
                    input.nextLine();

                    if (selection == 1) {

                        // Change email
                        System.out.println("Enter your new email");
                        String newEmail = input.nextLine();

                        if (uas.getUserAccount().setEmail(newEmail)) {
                            System.out.println("Your email has been changed.");
                        } else {
                            System.out.println("There was a problem changing your email. Please try again.");
                        }

                    } else if (selection == 2) {

                        // Change name
                        System.out.println("Enter your new name");
                        String newName = input.nextLine();

                        if (uas.getUserAccount().setName(newName)) {
                            System.out.println("Your name has been changed.");
                        } else {
                            System.out.println("There was a problem changing your name. Please try again.");
                        }

                    } else if (selection == 3) {

                        // Change password
                        System.out.println("Enter your new password");
                        String newPass = input.nextLine();

                        if (uas.getUserAccount().setName(newPass)) {
                            System.out.println("Your password has been changed.");
                        } else {
                            System.out.println("There was a problem changing your password. Please try again.");
                        }

                    } else if (selection == 4) {

                        // View email
                        System.out.println("Your email is: " + uas.getUserAccount().getEmail());

                    } else if (selection == 5) {

                        // View name
                        System.out.println("Your name is: " + uas.getUserAccount().getName());

                    } else if (selection == 6) {

                        // View password
                        System.out.println("Your password is: " + uas.getUserAccount().getPassword());

                    } else if (selection == 7) {

                        // Add contact
                        System.out.println("Enter the id of the person you wish to add as a contact: ");
                        String newContact = input.nextLine();

                        if (uas.getUserAccount().addFriend(newContact)) {
                            System.out.println("Contact added successfully.");
                        } else {
                            System.out.println("Contact could not be added. Please try again.");
                        }

                    } else if (selection == 8) {

                        // Remove contact
                        System.out.println("Enter the id of the person who you wish to remove from you contact list: ");
                        String removeID = input.nextLine();

                        if (uas.getUserAccount().removeFriend(removeID)) {
                            System.out.println("Contact removed successfully.");
                        } else {
                            System.out.println("Contact could not be removed. Please try again.");
                        }

                    } else if (selection == 9) {

                        // View entire schedule
                        System.out.println(uas.getScheduleViewer().viewEntireSchedule(ucs));

                    } else if (selection == 10) {

                        // Send a private message
                        System.out.println("Enter the ID of the Person you wish to Message: ");
                        String email = input.nextLine();
                        System.out.println("Enter the Subject of the Message: ");
                        String subject = input.nextLine();
                        System.out.println("Enter the Content of the Message: ");
                        String content = input.nextLine();
                        if (uas.getUserAccount().sendMessage(subject, content, email, uas.getMessenger())) {
                            System.out.println("Message sent successfully!");
                        } else {
                            System.out.println("Message could not be sent. Please try again.");
                        }

                    } else if (selection == 11) {

                        // View messages
                        System.out.println(uas.getUserAccount().viewInbox(uas.getMessenger()));

                    } else if (selection == 12) {

                        // View contacts
                        try {
                            System.out.println("Here is a list of your contacts: " + uas.getUserAccount().viewContacts());
                        } catch (DoesNotExistError e) {
                            System.out.println("Your contacts could not be retrieved. Please try again.");
                        }

                    } else if (selection == 13) {

                        // log out
                        uas.getUserAccount().logout();
                        isloggedIn = false;

                    } else if (selection == 30) { // Speaker specific options

                        // View talks you are giving
                        uas.getScheduleViewer().viewMyEvents(ucs);

                    } else if (selection == 31) {

                        // Message all attendees of multiple of your talks

                        // First get the list of talks to message
                        ArrayList<String> talks = new ArrayList<String>();
                        Boolean enteringTalks = true;
                        String nextTalk;
                        while (enteringTalks) {
                            System.out.println("Enter the talk ID (\"exit\" to stop): ");
                            nextTalk = input.nextLine();
                            if (nextTalk.equals("exit")) {
                                enteringTalks = false;
                            } else {
                                talks.add(nextTalk);
                            }
                            System.out.println("You are about to message these talks: " + talks);
                        }

                        // Next get the actual message
                        System.out.println("Enter the Subject of the Message: ");
                        String subject = input.nextLine();
                        System.out.println("Enter the Content of the Message: ");
                        String content = input.nextLine();

                        // Send the message
                        if (uas.getUserAccount().messageMultipleTalks(talks, subject, content, uas.getMessenger())) {
                            System.out.println("Message successfully sent");
                        } else {
                            System.out.println("Your message could not be sent to everyone at those talks. Please try again.");
                        }

                    } else if (selection == 32) {

                        // Message all attendees of a talk
                        System.out.println("Enter the ID of the talk whose Attendees wish to send a message to: ");
                        String talkID = input.nextLine();
                        System.out.println("Enter the Subject of the Message: ");
                        String subject = input.nextLine();
                        System.out.println("Enter the Content of the Message: ");
                        String content = input.nextLine();

                        if (uas.getUserAccount().messageTalk(talkID, subject, content, uas.getMessenger())) {
                            System.out.println("Message successfully sent.");
                        } else {
                            System.out.println("Your message could not be sent to everyone successfully. Please try again.");
                        }
                    } else if (selection == 40) { // Organizer specific options
                        // Create a new room
                        int openHourDef = 0;
                        boolean temp1 = true;
                        while (temp1) {
                            System.out.println("At what hour does this room open? Please choose an integer between " +
                                    "9 and 17 inclusive.");
                            int openHour = input.nextInt();
                            if (9 <= openHour && openHour <= 17) {
                                openHourDef = openHour;
                                temp1 = false;
                            }
                        }

                        int openMinuteDef = 0;
                        boolean temp2 = true;
                        while (temp2) {
                            System.out.println("At what minute of the hour does the room open? Please choose an " +
                                    "integer between 0 and 59 inclusive.");
                            int openMinute = input.nextInt();
                            if (0 <= openMinute && openMinute <= 59) {
                                openMinuteDef = openMinute;
                                temp2 = false;
                            }
                        }

                        int closeHourDef = 0;
                        boolean temp3 = true;
                        while (temp3) {
                            System.out.println("At what hour does this room close? Please choose an integer between " +
                                    "9 and 17 inclusive. The room cannot close before it opens.");
                            int closeHour = input.nextInt();
                            if (9 <= closeHour && closeHour <= 17 && openHourDef < closeHour) {
                                closeHourDef = closeHour;
                                temp3 = false;
                            }
                        }
                        
                        int closeMinuteDef = 0;
                        boolean temp4 = true;
                        while (temp4) {
                            System.out.println("At what minute of the hour does the room close? Please choose an " +
                                    "integer between 0 and 59 inclusive.");
                            int closeMinute = input.nextInt();
                            if (0 <= closeMinute && closeMinute <= 59) {
                                closeMinuteDef = closeMinute;
                                temp4 = false;
                            }
                        }
                        
                        uas.getUserAccount().createRoom(openHourDef, openMinuteDef, closeHourDef, closeMinuteDef);
                        System.out.println("Room created!");

                    } else if (selection == 41) {
                        // Create a new speaker
                        boolean temp = true;
                        while (temp) {
                            System.out.println("What is the speaker's name?");
                            String name = input.nextLine();
                            System.out.println("What is the speaker's email?");
                            String email = input.nextLine();
                            System.out.println("What is the speaker's password?");
                            String password = input.nextLine();
                            if (uas.getUserAccount().createSpeaker(email, name, password)) {
                                temp = false;
                            } else {
                                System.out.println("This speaker already exists!");
                            }
                        }
                        System.out.println("Speaker created!");

                    } else if (selection == 42) {
                        // Schedule a Talk
                        TalkOrganizer to = new TalkOrganizer(uas.getUserAccount(), ucs);

                        System.out.println("Who is the speaker?");
                        String name = input.nextLine();
                        System.out.println("What is the title of the talk?");
                        String title = input.nextLine();
                        System.out.println("The following is a list of available slots to schedule a talk.");
                        to.getAvailableSlots(name); // check if this shows up on screen (method prints lines but return type is void)

                        // choosing a date:
                        /* are we assuming the conference occurs on certain days? If so, which ones?
                        following code does not make said assumption */
                        System.out.println("Now, let's schedule. In what year does talk take place?");
                        int year = input.nextInt();
                        System.out.println("In what month does the talk take place? January being 1, February " +
                                "being 2, and so on.");
                        int month = input.nextInt() - 1; // GregorianCalendar constructor says month value is 0-based (0 for January)
                        System.out.println("On what day of the month does the talk take place?");
                        int day = input.nextInt();
                        GregorianCalendar date = new GregorianCalendar(year, month, day);

                        // choosing a start time:
                        String correctTime = "00:00"; // placeholder time
                        boolean temp = true;
                        while (temp) {
                            System.out.println("What time does the talk start? Please enter a time between " +
                                    "09:00 and 16:00 inclusive.");
                            /* correct me if i'm wrong but since we assume talks are 1 hour each, the latest a
                            talk can start is 4pm right? */
                            input.nextLine();
                            String st = input.nextLine();
                            LocalTime correctTimeValue = null;
                            try { // tests valid input for time:
                                correctTimeValue = LocalTime.parse(st);
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid time given. Please try again.");
                            }
                            // tests if time is between given restrictions:
                            LocalTime min = LocalTime.parse("09:00");
                            LocalTime max = LocalTime.parse("16:00");
                            assert correctTimeValue != null;
                            if ((correctTimeValue.isAfter(min) || correctTimeValue.equals(min)) &&
                                    (correctTimeValue.isBefore(max) || correctTimeValue.equals(max))) {
                                correctTime = st;
                                temp = false;
                            }
                        }

                        // choosing a room:

                        System.out.println("What is the ID of the room where you want to schedule a talk?");
                        ucs.getRoomManager().getAllRooms().forEach(System.out::println);
                        String roomID = input.nextLine();
                        try {
                            ucs.getRoomManager().findRoom(roomID);
                        } catch (DoesNotExistError e) {
                            System.out.println("Room does not exist. Please try again.");
                        }

                        // Finally, Scheduling the talk:
                        to.addTalk(title, date, LocalTime.parse(correctTime), name,
                                ucs.getRoomManager().findRoom(roomID));
                        System.out.println("Talk scheduled!");

                    } else if (selection == 43) {

                        // Message all speakers
                        System.out.println("Enter the Subject of the Message: ");
                        String subject = input.nextLine();
                        System.out.println("Enter the Content of the Message: ");
                        String content = input.nextLine();
                        if (uas.getUserAccount().messageAllSpeakers(subject, content, uas.getMessenger())) {
                            System.out.println("Message sent successfully!");
                        } else {
                            System.out.println("Message could not be sent. Please try again.");
                        }

                    } else if (selection == 44) {

                        // Message all Attendees
                        System.out.println("Enter the Subject of the Message: ");
                        String subject = input.nextLine();
                        System.out.println("Enter the Content of the Message: ");
                        String content = input.nextLine();
                        if (uas.getUserAccount().messageAllAttendees(subject, content, uas.getMessenger())) {
                            System.out.println("Message sent successfully!");
                        } else {
                            System.out.println("Message could not be sent. Please try again.");
                        }

                    } else if (selection == 60) { // Attendee specific actions

                        // View available talks (maybe organizers should be able to see this too?)
                        uas.getScheduleViewer().viewAvailableEvents(ucs);

                    } else if (selection == 61) {
                        // View Talks already signed up in
                        Registrar r = new Registrar(ucs);

                        System.out.println("Here is the list of talks you are registered in: " +
                                r.getRegisteredTalks(ucs.getAttendeeManager().findAttendee(uas.getUserAccount().
                                        getUserID())));

                    } else if (selection == 62) {
                        // Sign up for a talk
                        Registrar r = new Registrar(ucs);

                        System.out.println("What is the ID of the talk you want to sign up for?");
                        String talkID = input.nextLine();
                        if (uas.getUserAccount().register(talkID, r)) {
                            System.out.println("Thanks for signing up!");
                        } else {
                            System.out.println("You were not able to sign up for that talk. Please try again.");
                        }

                    } else if (selection == 63) {
                        // Cancel spot in a talk
                        Registrar r = new Registrar(ucs);

                        System.out.println("What is the ID of the talk you want to cancel from?");
                        String talkID = input.nextLine();
                        if (uas.getUserAccount().cancel(talkID, r)) {
                            System.out.println("Cancellation successful.");
                        } else {
                            System.out.println("Cancellation failed. Please try again.");
                        }

                    } else {
                        // Somehow, invalid input (corresponds to no option)
                        // Add/remove more else ifs if options change
                        System.out.println("Invalid selection. Please try again.");
                    }



                    // After logging out, the user can close the program.

                    // At this moment in time, as soon as they log in, the user will be logged out.
//                    isloggedIn = false;
                }

                System.out.println("Logged Out Successfully");
            }

        }

        // Now, to close the system, we need to do a few things.

        // First, we need to recover every piece of data and write it to the .ser file
        gate.Write(ucs);


        System.out.println("All Data has been stored successfully");

        // Now we can end the program.

        System.out.println("Thank you for using TextChat!");
    }
}
