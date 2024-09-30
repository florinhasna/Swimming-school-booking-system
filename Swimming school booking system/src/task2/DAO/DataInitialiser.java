package task2.DAO;

import task2.Domain.SwimStudent;
import task2.Domain.Level;
import task2.Domain.Instructor;
import task2.Domain.SwimLesson;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

/* Responsible with creating new objects of required data for testing */ 
public class DataInitialiser {

    private final ArrayList<SwimStudent> students = new ArrayList<>();
    private final ArrayList<Instructor> instructors = new ArrayList<>();
    private final ArrayList<SwimLesson> lessons = new ArrayList<>();

    public DataInitialiser() {
    }

    public ArrayList<SwimStudent> getStudents() {
        return students;
    }

    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }

    public ArrayList<SwimLesson> getLessons() {
        return lessons;
    }

    public void createStudents() {
        String[] studentNames = {
            "Ethan Turner",
            "Sophia Parker",
            "Mason Hall",
            "Ava Scott",
            "Landon Young",
            "Chloe Adams",
            "Daniel Walker",
            "Grace Harris",
            "Oliver Nelson",
            "Emma Turner",
            "Carter King",
            "Zoe Mitchell",
            "Jackson Wilson",
            "Aria Martinez",
            "Lucas Davis",
            "Lily Turner",
            "Logan Smith",
            "Avery Turner",
            "Nathan White",
            "Ella Turner",
            "Caleb Bell",
            "Scarlett Carter",
            "Wyatt Turner",
            "Madison Miller",
            "James Turner",
            "Isabella Harris",
            "Liam Turner",
            "Sofia Martin",
            "Andrew Turner",
            "Mia Taylor",
            "Gabriel Lewis",
            "Abigail Turner",
            "Henry Nelson",
            "Aubrey Hall",
            "William Turner",
            "Avery Clark",
            "Emily Turner",
            "Matthew Davis",
            "Harper Turner",
            "Nicholas Baker",
            "Zara Turner",
            "Elijah Evans",
            "Brooklyn Harris",
            "David Turner",
            "Aaliyah Turner",
            "John Lewis",
            "Charlotte Turner",
            "Christopher Ward",
            "Ariana Turner",
            "Olivia Patterson",
            "Ryan Turner",
            "Eva Harris",
            "Aria Turner",
            "Evan Turner",
            "Stella Turner",
            "Isaac Turner",
            "Layla Turner",
            "Jordan Turner",
            "Ruby Turner",
            "Tyler Turner",
            "Nora Turner",
            "Connor Turner",
            "Ava Turner",
            "Jason Turner",
            "Leah Turner",
            "Colton Turner",
            "Hannah Turner",
            "Julian Turner",
            "Rick Grimes",
            "Sansa Stark",
            "Arya Stark",
            "Darryl Dixon",
            "Katie Wood",
            "Chris Woodhouse",
            "Sebastian Stan",
            "Sadik Hyseni",
            "Ily Bird",
            "Rufus Croat",
            "Kate Hillborn",
            "Emilia Stormborn",
            "Urs Martinez",
            "Rodrik Riverside",
            "Dan Ethan",
            "Ethan Stuart",
            "Stuart Stan",
            "Ava Stan",
            "Jack Willow",
            "Peter Celtimore",
            "Ragnar Lothbrok",
            "Bjorn Ragnarson",
            "Uhtred Bebbanburg",
            "Joe Simons",
            "Simon Francois",
            "Cicy Fussmaker",
            "Cillian Crow",
            "Valentine Dean",
            "Dean Wild",
            "Karstark Killy",
            "Coco Patel",
            "Nicholas Hasna"
        };

        for (String s : studentNames) {
            this.getStudents().add(new SwimStudent(s));
        }
    }

    public void createInstructors() {
        String[] instructorNames = {"Caleb Montgomery", "Isabella Harper", "Lily Chen"};

        for (String s : instructorNames) {
            this.getInstructors().add(new Instructor(s));
        }
    }

    public void createSessions() {
        ArrayList<DayOfWeek> days = new ArrayList<>();
        days.add(DayOfWeek.MONDAY);
        days.add(DayOfWeek.WEDNESDAY);
        days.add(DayOfWeek.FRIDAY);

        LocalTime firstStart = LocalTime.NOON.plusHours(5);
        LocalTime secondStart = LocalTime.NOON.plusHours(5).plusMinutes(30);
        LocalTime thirdStart = LocalTime.NOON.plusHours(6);
        LocalTime fourthStart = LocalTime.NOON.plusHours(6).plusMinutes(30);
        LocalTime fifthStart = LocalTime.NOON.plusHours(7);
        LocalTime sixthStart = LocalTime.NOON.plusHours(7).plusMinutes(30);

        for (DayOfWeek d : days) {
            this.getLessons().add(new SwimLesson(d, firstStart, Level.NOVICE, this.getInstructors().get(0)));
            this.getLessons().add(new SwimLesson(d, firstStart, Level.IMPROVER, this.getInstructors().get(1)));
            this.getLessons().add(new SwimLesson(d, firstStart, Level.ADVANCED, this.getInstructors().get(2)));

            this.getLessons().add(new SwimLesson(d, secondStart, Level.IMPROVER, this.getInstructors().get(0)));
            this.getLessons().add(new SwimLesson(d, secondStart, Level.ADVANCED, this.getInstructors().get(1)));
            this.getLessons().add(new SwimLesson(d, secondStart, Level.NOVICE, this.getInstructors().get(2)));

            this.getLessons().add(new SwimLesson(d, thirdStart, Level.ADVANCED, this.getInstructors().get(0)));
            this.getLessons().add(new SwimLesson(d, thirdStart, Level.NOVICE, this.getInstructors().get(1)));
            this.getLessons().add(new SwimLesson(d, thirdStart, Level.IMPROVER, this.getInstructors().get(2)));

            this.getLessons().add(new SwimLesson(d, fourthStart, Level.NOVICE, this.getInstructors().get(0)));
            this.getLessons().add(new SwimLesson(d, fourthStart, Level.IMPROVER, this.getInstructors().get(1)));
            this.getLessons().add(new SwimLesson(d, fourthStart, Level.ADVANCED, this.getInstructors().get(2)));
            
            this.getLessons().add(new SwimLesson(d, fifthStart, Level.IMPROVER, this.getInstructors().get(0)));
            this.getLessons().add(new SwimLesson(d, fifthStart, Level.NOVICE, this.getInstructors().get(1)));
            this.getLessons().add(new SwimLesson(d, fifthStart, Level.ADVANCED, this.getInstructors().get(2)));
            
            this.getLessons().add(new SwimLesson(d, sixthStart, Level.NOVICE, this.getInstructors().get(0)));
            this.getLessons().add(new SwimLesson(d, sixthStart, Level.ADVANCED, this.getInstructors().get(1)));
            this.getLessons().add(new SwimLesson(d, sixthStart, Level.IMPROVER, this.getInstructors().get(2)));
        }
        
        this.getInstructors().forEach(i -> {
            this.getLessons().stream().filter(sl -> (sl.getInstructor().equals(i))).forEachOrdered(sl -> {
                i.assignLesson(sl);
            });
        });
    }
}
