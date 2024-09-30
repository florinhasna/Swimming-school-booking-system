package task2.UI;

import task2.Domain.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import task2.Application.*;

/* Class responsible with the displaying given data or messages to the user, or 
getting user inputs. */
public class UserInterface {

    public int loadMenuAndSelect() {

        System.out.println("----------------MENU------------------");
        System.out.println("View swim student info...............1");
        System.out.println("View swim lesson details.............2");
        System.out.println("View instructor schedule.............3");
        System.out.println("Add swim student.....................4");
        System.out.println("Award qualification..................5");
        System.out.println("Move swim student from waiting list..6");
        System.out.println("EXIT.................................0");
        System.out.println("--------------------------------------\n");

        System.out.println("At any given stage of the program, you");
        System.out.println("can go back or abort by typing \"0\".\n");

        System.out.print("Enter choice");

        return LocalUtilities.promptSelect(Integer.class);
    }

    /* Print lists section */
    // generic method used to print list of students or instructors
    public <T> void printListOfNames(ArrayList<T> aList) {
        StringBuilder sb = new StringBuilder("\n");
        String type = "";

        for (T object : aList) {
            if (object instanceof SwimStudent) {
                type = "student";
                sb.append("\t").append(((SwimStudent) object).getName()).append(" - ");
                sb.append(((SwimStudent) object).getLevel().getDisplayName()).append("\n");
            } else if (object instanceof Instructor) {
                type = "instructor";
                sb.append("\t").append(((Instructor) object).getName()).append("\n");
            }
        }

        sb.append((type.equals("instructor")) ? "Choose an " : "Choose a ");
        sb.append(type).append(" from the list:\n");
        System.out.println(sb.toString());
    }

    // to print any lesson
    public void printLessons(List<SwimLesson> lessons) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(LocalUtilities.repeat("-", 35)).append("\n");
        lessons.forEach(sl -> {
            sb.append(String.format("| %-10s | %-4s | %-10s |\n",
                    sl.getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH), sl.getStartTime().toString(), sl.getLevel().getDisplayName()));
        });
        sb.append(LocalUtilities.repeat("-", 35)).append("\n");
        System.out.println(sb.toString());
        System.out.println("To see a lesson's details(e.g. host, students, etc.), enter:");
    }

    // to print lessons of a certain level
    public void printFilteredLessons(List<SwimLesson> lessons) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append(LocalUtilities.repeat("-", 53)).append("\n");
        lessons.forEach(sl -> {
            sb.append(String.format("| %-10s | %-4s | %-10s | %-15s |\n",
                    sl.getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH), sl.getStartTime().toString(), sl.getLevel().getDisplayName(),
                    (sl.getAvailablePlaces() == 0) ? "FULLY BOOKED" : sl.getAvailablePlaces() + " available"
            ));
        });
        sb.append(LocalUtilities.repeat("-", 53)).append("\n");
        System.out.println(sb.toString());
    }

    public void printListOfUnachievedAwards(ArrayList<String> aList) {
        StringBuilder sb = new StringBuilder("\n");

        sb.append("Choose from the following list of un-achieved awards:\n\n");

        aList.forEach(s -> {
            sb.append("\t").append(s).append("\n");
        });

        System.out.println(sb.toString());
    }

    /* Get inputs section */
    public String getNameInput() {
        System.out.print("Full Name");
        return LocalUtilities.promptSelect(String.class);
    }

    public DayOfWeek getDayInput() throws InterruptedException {
        DayOfWeek aDay = null;
        do {
            try {
                System.out.print("Day");
                aDay = LocalUtilities.getDay();
            } catch (InputMismatchException e) {
                this.invalidDayMessage();
            } catch (InterruptedException e) {
                this.goingBackMessage();
                throw new InterruptedException();
            }
        } while (aDay == null);

        return aDay;
    }

    public LocalTime getTimeInput() throws InterruptedException {
        LocalTime time = null;
        do {
            try {
                System.out.print("Time");
                time = LocalUtilities.getTime();
            } catch (InputMismatchException e) {
                this.invalidTimeMessage();
            } catch (InterruptedException e) {
                this.goingBackMessage();
                throw new InterruptedException();
            }
        } while (time == null);

        return time;
    }

    public Level getLevelInput() throws InterruptedException {
        Level level = null;
        do {
            try {
                System.out.print("Level");
                level = LocalUtilities.getLevel();
            } catch (InputMismatchException e) {
                this.invalidLevelMessage();
            } catch (InterruptedException e) {
                this.goingBackMessage();
                throw new InterruptedException();
            }
        } while (level == null);

        return level;
    }

    public String printAwardSelection() {
        StringBuilder sb = new StringBuilder("\n");

        sb.append("Which type of certificate would you like to issue?\n\n");
        sb.append("\tDistance Swim Certificate\n");
        sb.append("\t\tOR\n");
        sb.append("\tPersonal Survival Medals\n");

        sb.append("\nType \"distance\" or \"survival\"");

        System.out.print(sb.toString());

        return LocalUtilities.promptSelect(String.class);
    }

    public String getDistanceInput() {
        System.out.print("Enter distance (e.g. \"20m\")");

        return LocalUtilities.promptSelect(String.class);
    }

    public Medal getMedalInput() throws InterruptedException {
        Medal medal = null;

        do {
            try {
                System.out.print("Medal");
                medal = LocalUtilities.getMedal();
            } catch (InterruptedException e) {
                this.goingBackMessage();
                throw new InterruptedException();
            }
        } while (medal == null);

        return medal;
    }

    /* Informative messages section */
    public void exitMessage() {
        System.out.println("Exiting program...");
    }

    public void menuSelectAgainMessage() {
        System.out.println("Enter the right label number, (e.g. enter 3 to search)...\n");
    }

    public void studentAssignedConfirmation(SwimLesson sl, SwimStudent ss) {
        StringBuilder sb = new StringBuilder("\n");

        sb.append("The student ").append(ss.getName().toUpperCase());
        sb.append(" was successfully booked for the class on ");
        sb.append(sl.getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase());
        sb.append(" at ").append(sl.getStartTime().toString()).append("\n");

        System.out.println(sb.toString());
    }

    public void studentAddedToWaitingListConfirmation(SwimStudent ss) {
        StringBuilder sb = new StringBuilder("\n");

        sb.append("Since the lesson is full, the student ").append(ss.getName().toUpperCase());
        sb.append(" was added to the waiting list.").append("\n");

        System.out.println(sb.toString());
    }

    public void printAwardConfirmation(SwimStudent ss, Qualification q) {
        StringBuilder sb = new StringBuilder("\n");

        sb.append("The ").append(q.getAward().toUpperCase());
        sb.append((q instanceof PersonalSurvival) ? " medal " : "m award ");
        sb.append("was successfully issued to ").append(ss.getName().toUpperCase());
        sb.append("\n");

        if (q instanceof DistanceSwim) {
            if (((DistanceSwim) q).getDistance() == Level.NOVICE.getMetersForThirdAward()) {
                sb.append("The student was promoted to IMPROVERS and placed on the waiting list.");
                sb.append("\n");
            } else if (((DistanceSwim) q).getDistance() == Level.IMPROVER.getMetersForThirdAward()) {
                sb.append("The student was promoted to ADVANCED and placed on the waiting list.");
                sb.append("\n");
            }
        }

        System.out.println(sb.toString());
    }

    public void allAwardsGivenMessage(String name) {
        System.out.println();
        System.out.println("All certificates of this category have been already issued to " + name.toUpperCase() + ".");
        System.out.println();
    }

    public void printAddMessage() {
        System.out.println("To add a new swim student, you will be required to");
        System.out.println("type the full name (e.g. \"John Doe\") and select");
        System.out.println("the session the student wishes to attend.");
        System.out.println();
    }

    public void awardQualificationMessage() {
        System.out.println("To award a qualification, you will need to select");
        System.out.println("the issuing instructor and the awarded student from");
        System.out.println("the printed lists.");
    }
    
    public void moveFromWaitingListMessage() {
        System.out.println("To move a student from the waiting list, enter");
        System.out.println("student's full name from the printed list and");
        System.out.println("select the desired session from the table by day");
        System.out.println("and time.");
    }

    public void emptyWaitListMessage() {
        System.out.println();
        System.out.println("The waiting list is currently empty.");
        System.out.println();
    }

    public void goingBackMessage() {
        System.out.println();
        System.out.println("Action cancelled by the user, going back...");
        System.out.println();
    }

    /* Invalid messages section */
    public void nameAlreadyUsedMessage(String name) {
        System.out.println();
        System.out.println("There is already a person with the name " + name + " registered in the system.");
        System.out.println();
    }
    
    public void invalidNameMessage(String name) {
        System.out.println();
        System.out.println("The name " + name + " is invalid or nonexistent, try again or type \"0\" to go back...");
        System.out.println();
    }

    public void invalidNameInput() {
        System.out.println();
        System.out.println("You need to enter both first name and last name...");
        System.out.println("With no special characters or numbers...");
        System.out.println();
    }

    public void invalidDayMessage() {
        System.out.println();
        System.out.println("The day is not valid, please check and try again...");
        System.out.println();
    }

    public void invalidTimeMessage() {
        System.out.println();
        System.out.println("The time is not right, please check and try again...");
        System.out.println();
    }

    public void invalidLevelMessage() {
        System.out.println();
        System.out.println("The given level is not correct, please check and try again...");
        System.out.println();
    }

    public void sessionNotFoundMessage() {
        System.out.println();
        System.out.println("A session was not found, check the data you entered and try again...");
        System.out.println();
    }

    public void fullLessonMessage() {
        System.out.println("Unfortunately, all lessons are at full capacity currently, try again later.");
        System.out.println();
    }

    public void printErrorMessage() {
        System.out.println();
        System.out.println("An error has occurred while processing the given input, try again...");
        System.out.println();
    }
}
