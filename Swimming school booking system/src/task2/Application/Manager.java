package task2.Application;

import task2.Domain.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import task2.DAO.DataLoader;
import task2.UI.UserInterface;

/* Class responsible with handling user inputs and loading the appropriate requested
existent data, or relevant messages from the UI class. */
public class Manager {

    UserInterface UI;
    DataLoader data;

    public Manager(UserInterface UI, DataLoader data) {
        this.UI = UI;
        this.data = data;
    }

    public UserInterface getUI() {
        return UI;
    }

    public DataLoader getData() {
        return data;
    }

    // loads and loops the menu, as well as getting the option from the menu
    public void startProgram() {
        int choice = -1;

        do {
            try {
                choice = this.getUI().loadMenuAndSelect();
                this.processInput(choice); // process the selection
            } catch (InputMismatchException e) {
                this.getUI().printErrorMessage();
            }
        } while (choice != 0);
    }

    // processes the selection and redirects to the relevant request
    private void processInput(int input) {
        switch (input) {
            case 1:
                viewStudentInfo();
                break;
            case 2:
                viewLessonInfo();
                break;
            case 3:
                viewInstructorSchedule();
                break;
            case 4:
                addSwimStudent();
                break;
            case 5:
                awardQualification();
                break;
            case 6:
                moveSwimStudentFromWaitingList();
                break;
            default:
                if (input == 0) { // option to exit the program
                    this.getUI().exitMessage();
                } else { // or it is an invalid message
                    this.getUI().menuSelectAgainMessage();
                }
        }
    }

    // retrieve a SwimStudent by name from a sorted list, and print its details
    private void viewStudentInfo() {
        try {
            // print the details of a selected swim student
            System.out.println(this.getObjectFromList(this.getData().getSortedStudents(), SwimStudent::getName));
        } catch (InterruptedException e) {
            this.getUI().goingBackMessage(); // choice to go back
        }
    }

    // retrieve a SwimLesson from a list, and print its details
    private void viewLessonInfo() {
        // print list of lessons
        this.getUI().printLessons(this.getData().getLessons());

        // initialise a new variable to hold a SwimLesson object
        SwimLesson sl;

        try { // get lesson by day, time and level
            sl = this.getLesson(this.getUI().getDayInput(), this.getUI().getTimeInput(), this.getUI().getLevelInput());
        } catch (InterruptedException e) {
            return; // choice to go back
        }

        if (sl != null) { // if the session was found, print its details
            System.out.println(sl);
        } else { // session not found case
            this.getUI().sessionNotFoundMessage();
        }
    }

    // retrieve an Instructor from a sorted list, and print its details
    private void viewInstructorSchedule() {
        try {
            // print the details of a selected instructor
            System.out.println(this.getObjectFromList(this.getData().getSortedInstructors(), Instructor::getName));
        } catch (InterruptedException e) {
            this.getUI().goingBackMessage(); //if the choice is to go back
        }
    }

    // read data for new student, initialise the object, select a lesson for the new student
    public void addSwimStudent() {
        this.getUI().printAddMessage();
        // get name input
        String name;

        do {
            name = this.getUI().getNameInput();

            // if it is going back
            if (this.isGoingBack(name)){
                this.getUI().goingBackMessage();
                return;
            }

            // if name is not correct
            if (!LocalUtilities.testName(name)) {
                this.getUI().invalidNameInput();
            }
            
            // if the name is already in use
            for(SwimStudent ss : this.getData().getStudents()) {
                if(name.equalsIgnoreCase(ss.getName())) {
                    this.getUI().nameAlreadyUsedMessage(name);
                    return;
                }
            }
        } while (!LocalUtilities.testName(name)); // break the loop when input is validated

        // create SS object
        SwimStudent newSS = new SwimStudent(name);

        try {
            this.bookStudentToLesson(newSS);
        } catch (InterruptedException ex) { // action cancelled
            return;
        }
        this.getData().getStudents().add(newSS);
    }

    /* get instructor and member by name from sorted lists, issue relevant qualification */
    public void awardQualification() {
        this.getUI().awardQualificationMessage();
        
        Instructor anInstructor;
        SwimStudent aStudent;

        try {
            // get instructor
            anInstructor = this.getObjectFromList(this.getData().getSortedInstructors(), Instructor::getName);
            // get student
            aStudent = this.getObjectFromList(this.getData().getSortedStudents(), SwimStudent::getName);
        } catch (InterruptedException e) {
            this.getUI().goingBackMessage();
            return;
        }

        // if student is advanced, choose type of qualification
        if (aStudent.getLevel().equals(Level.ADVANCED)) {
            String selection;

            do {
                selection = this.getUI().printAwardSelection();
                // check if going back
                if (this.isGoingBack(selection)) {
                    this.getUI().goingBackMessage();
                    return;
                }
            } while (!(selection.equalsIgnoreCase("survival") || selection.equalsIgnoreCase("distance")));

            // select distance swim or personal survival
            if (selection.equalsIgnoreCase("survival")) {
                try {
                    this.awardPersonalSurvivalMedal(aStudent, anInstructor);
                    return;
                } catch (InterruptedException e) {
                    return;
                }
            }
        }

        this.awardDistanceSwim(aStudent, anInstructor);
    }

    /* issue a personal survival medal to a student by an instructor */
    private void awardPersonalSurvivalMedal(SwimStudent aStudent, Instructor anInstructor) throws InterruptedException {
        ArrayList<String> unachievedMedals = this.getUnachievedMedals(aStudent);

        // check if all medals were awarded
        if (unachievedMedals.isEmpty()) {
            this.getUI().allAwardsGivenMessage(aStudent.getName());
            throw new InterruptedException();
        }
        this.getUI().printListOfUnachievedAwards(unachievedMedals);

        PersonalSurvival ps;

        try {
            ps = new PersonalSurvival(this.getUI().getMedalInput(), anInstructor);
        } catch (InterruptedException e) {
            throw new InterruptedException();
        }

        anInstructor.issueQualification(aStudent, ps);
        this.getUI().printAwardConfirmation(aStudent, ps);
    }

    /* issue a distance swim certificate to a student by an instructor */
    private void awardDistanceSwim(SwimStudent aStudent, Instructor anInstructor) {
        ArrayList<String> unachievedDistance = this.getUnachievedAwards(aStudent);

        // check if all distance awards were issued
        if (unachievedDistance.isEmpty()) {
            this.getUI().allAwardsGivenMessage(aStudent.getName());
            return;
        }

        // print list of awards that are not already achieved
        this.getUI().printListOfUnachievedAwards(unachievedDistance);

        String distance;

        do { // loop until a distance that is member of the unachievedDistance is given
            distance = this.getUI().getDistanceInput();

            if (this.isGoingBack(distance)) {
                this.getUI().goingBackMessage();
                return;
            }
        } while (LocalUtilities.checkDistanceInput(distance, unachievedDistance));

        DistanceSwim ds = new DistanceSwim(Integer.parseInt(distance.toLowerCase().replace("m", "").trim()), anInstructor);

        // select from the list and record the award with the selected instructor
        anInstructor.issueQualification(aStudent, ds);

        // add on waiting list if the student is being promoted
        if (ds.getDistance() == Level.NOVICE.getMetersForThirdAward()
                || ds.getDistance() == Level.IMPROVER.getMetersForThirdAward()) {
            this.getData().getWaitList().addToWaitingList(aStudent);
        }

        this.getUI().printAwardConfirmation(aStudent, ds);
    }

    // select a student from a sorted list by levels, book the student to a session
    private void moveSwimStudentFromWaitingList() {
        this.getUI().moveFromWaitingListMessage();
        // waiting list
        WaitingList waitList = this.getData().getWaitList();

        if (waitList.getStudents().isEmpty()) {
            this.getUI().emptyWaitListMessage();
            return;
        }

        SwimStudent ss;

        try { // get the selected student
            ss = getObjectFromList(waitList.getSortedArray(), SwimStudent::getName);
        } catch (InterruptedException e) { // going back case
            this.getUI().goingBackMessage();
            return;
        }

        try {
            // book the student to a selected lesson
            this.bookStudentToLesson(ss);
        } catch (InterruptedException ex) {
            return;
        }

        // remove student from the waiting list if allocated to a session of its current level
        if (ss.isAllocated() && ss.getLevel().equals(ss.getBookedLesson().getLevel())) {
            waitList.removeFromWaitingList(ss);
        }
    }

    // generic method only used for printing a list of students or instructor, select name from list 
    // returns object matching the name
    private <T> T getObjectFromList(ArrayList<T> sortedList, Function<T, String> nameFunction) throws InterruptedException {
        // print the list
        this.getUI().printListOfNames(sortedList);

        try {
            // flag to exit the loop when chooses to leave
            boolean escapeFlag = true;
            do {
                // get selection
                String nameInput = this.getUI().getNameInput();

                for (T object : sortedList) {
                    if (nameInput.trim().equalsIgnoreCase(nameFunction.apply(object))) {
                        return object; // return object if found
                    }
                }

                // choice to leave
                if (this.isGoingBack(nameInput)) {
                    escapeFlag = false;
                } else { // invalid name 
                    this.getUI().invalidNameMessage(nameInput);
                }
            } while (escapeFlag);

        } catch (InputMismatchException e) { // error
            this.getUI().printErrorMessage();
        }

        // the choice was to go back
        throw new InterruptedException();
    }

    // print lessons for student's level, get lesson selection if there are any available
    private void bookStudentToLesson(SwimStudent ss) throws InterruptedException {
        // display list of student's level sessions indicating availability
        List<SwimLesson> newSL = this.getData().getLessons().stream()
                .filter(SwimLesson -> SwimLesson.getLevel().equals(ss.getLevel()))
                .collect(Collectors.toList());

        // print the list
        this.getUI().printFilteredLessons(newSL);

        // check if the sessions are full
        if (this.checkIfSessionsAreFull(newSL)) {
            this.getUI().fullLessonMessage();
            return; // prevent from choosing a lesson
        }

        SwimLesson sl = null;

        do {
            // get lesson selection from the list
            try {
                sl = this.getLesson(newSL, this.getUI().getDayInput(), this.getUI().getTimeInput());
            } catch (InterruptedException e) {
                // action cancelled by user
                throw new InterruptedException();
            }

            if (sl == null) {
                this.getUI().sessionNotFoundMessage();
            }
        } while (sl == null);

        this.recordBooking(ss, sl);
    }

    // process the request of booking a student to a lesson
    private void recordBooking(SwimStudent ss, SwimLesson sl) {
        // if is full, allow to add on waiting list
        if (sl.isFull()) {
            // record by system (add on waiting list)
            this.getData().getWaitList().addToWaitingList(ss);
            ss.setIsOnWaitingList(true);
            // print confirmation message
            this.getUI().studentAddedToWaitingListConfirmation(ss);
        } else {
            // assign student and session
            sl.assignStudent(ss);
            // if allocated already, case when promoted and removed from waiting list
            if (ss.isAllocated()) {
                ss.getBookedLesson().unassignStudent(ss);
            }
            ss.setBookedLesson(sl);
            // print confirmation message
            this.getUI().studentAssignedConfirmation(sl, ss);
        }
    }

    // check if the lessons in a list are full
    private boolean checkIfSessionsAreFull(List<SwimLesson> aList) {
        boolean flag = true;
        for (SwimLesson sl : aList) {
            if (!sl.isFull()) { // if is not full set as false
                flag = false;
            }
        }

        return flag;
    }

    // method to get a SwimLesson object by day, time and level
    private SwimLesson getLesson(DayOfWeek aDay, LocalTime aTime, Level aLevel) {
        for (SwimLesson sl : this.getData().getLessons()) {
            if (sl.getDay().equals(aDay) && sl.getStartTime().equals(aTime) && sl.getLevel().equals(aLevel)) {
                return sl;
            }
        }

        return null; // not found
    }

    // overloaded method to get SwimLesson object from a given list by day and time
    private SwimLesson getLesson(List<SwimLesson> aList, DayOfWeek aDay, LocalTime aTime) {
        for (SwimLesson sl : aList) {
            if (sl.getDay().equals(aDay) && sl.getStartTime().equals(aTime)) {
                return sl;
            }
        }

        return null; // not found
    }

    // returns an arraylist of unachieved DistanceSwim awards of a student
    private ArrayList<String> getUnachievedAwards(SwimStudent ss) {
        ArrayList<String> unachieved = new ArrayList<>();

        // get levels in a list
        ArrayList<Level> levels = new ArrayList<>();
        levels.add(Level.NOVICE);
        levels.add(Level.IMPROVER);
        levels.add(Level.ADVANCED);

        // loop previous level/s and current
        for (int i = 0; i <= levels.indexOf(ss.getLevel()); i++) {
            // loop the arrays of integers representing distance awards
            for (int distance : levels.get(i).getMeterThresholds()) {
                boolean flag = true; // remains true if the award is unachieved
                // loop all the distance swim qualifications issued to the student
                for (DistanceSwim ds : ss.getDistanceSwimAwards()) {
                    if (ds.getDistance() == distance) {
                        flag = false; // award is achieved
                        break;
                    }
                }

                // if the award is unachieved
                if (flag) {
                    unachieved.add(distance + "m");
                }
            }
        }

        return unachieved;
    }

    // returns an arraylist of unachieved PersonalSurvival medals of a student
    private ArrayList<String> getUnachievedMedals(SwimStudent ss) {
        ArrayList<String> unachieved = new ArrayList<>();

        // loop all Medal values
        for (Medal m : Medal.values()) {
            boolean flag = true; // remains true if unachieved
            for (PersonalSurvival ps : ss.getPersonalSurvivalAwards()) {
                if (m.equals(ps.getLevel())) {
                    flag = false; // was achieved
                    break;
                }
            }

            // if the object ps is checked and was not achieved
            if (flag) {
                unachieved.add(m.getDisplayName());
            }
        }

        return unachieved;
    }

    // check if the choice is to go back or cancel the action
    private boolean isGoingBack(String input) {
        return input.trim().equals("0");
    }
}
