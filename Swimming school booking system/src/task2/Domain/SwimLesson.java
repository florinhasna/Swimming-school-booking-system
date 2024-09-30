package task2.Domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import task2.Application.LocalUtilities;

public class SwimLesson {

    private final DayOfWeek DAY;
    private final LocalTime START_TIME;
    private final Level LEVEL;
    private final Instructor INSTRUCTOR;
    private final ArrayList<SwimStudent> STUDENTS = new ArrayList<>();

    public SwimLesson(DayOfWeek day, LocalTime startTime, Level level, Instructor instructor) {
        this.DAY = day;
        this.START_TIME = startTime;
        this.LEVEL = level;
        this.INSTRUCTOR = instructor;
    }

    public DayOfWeek getDay() {
        return DAY;
    }

    public LocalTime getStartTime() {
        return START_TIME;
    }

    public Level getLevel() {
        return LEVEL;
    }

    public Instructor getInstructor() {
        return INSTRUCTOR;
    }

    public ArrayList<SwimStudent> getStudents() {
        return STUDENTS;
    }

    public int getAvailablePlaces() {
        int LIMIT = 4;
        return LIMIT - this.getStudents().size();
    }

    public boolean isFull() {
        return this.getAvailablePlaces() == 0;
    }
    
    public void assignStudent(SwimStudent ss) {
        this.getStudents().add(ss);
    }
    
    public void unassignStudent(SwimStudent ss) {
        this.getStudents().remove(ss);
    }

    // relevant SwimLesson data format to be printed when invoked in use case 2
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        
        sb.append(LocalUtilities.repeat("-", 33)).append("\n");
        
        sb.append(String.format("| %-9s | %-4s | %-9s |\n", 
                this.getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH), this.getStartTime().toString(), this.getLevel().getDisplayName()));
        
        sb.append(LocalUtilities.repeat("-", 33)).append("\n");
        
        sb.append(String.format("| %-30s|\n", this.getInstructor().getName().concat(" (host)")));
        
        // if there are allocated STUDENTS, print them
        if (!this.getStudents().isEmpty()) {
            sb.append(LocalUtilities.repeat("-", 33)).append("\n");
            sb.append(String.format("| %-10s%-10s%10s|\n", "", "ATTENDEES:", ""));
            this.getStudents().forEach(ss -> {
                sb.append(String.format("| %-30s|\n", ss.getName()));
            });
        }
        
        sb.append(LocalUtilities.repeat("-", 33)).append("\n");
        
        sb.append(String.format("| %-30s|\n", (this.isFull()) ? "The session is fully booked." : getAvailablePlaces() + " available space/s."));

        sb.append(LocalUtilities.repeat("-", 33)).append("\n");

        return sb.toString();
    }
}
