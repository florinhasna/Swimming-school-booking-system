package task2.DAO;

import java.util.ArrayList;
import java.util.Comparator;
import task2.Domain.Instructor;
import task2.Domain.SwimLesson;
import task2.Domain.SwimStudent;
import task2.Domain.WaitingList;

/* Responsible with passing the data created and alternated to the application layer */
public class DataLoader {
    private ArrayList<SwimStudent> students = new ArrayList<>();
    private ArrayList<Instructor> instructors = new ArrayList<>();
    private ArrayList<SwimLesson> lessons = new ArrayList<>();
    private final WaitingList waitList = new WaitingList();
    private final DataAlternator alternator = new DataAlternator();

    public DataLoader() {
    }

    public ArrayList<SwimStudent> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<SwimStudent> students) {
        this.students = students;
    }

    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(ArrayList<Instructor> instructors) {
        this.instructors = instructors;
    }

    public ArrayList<SwimLesson> getLessons() {
        return lessons;
    }
    
    public void setLessons(ArrayList<SwimLesson> lessons) {
        this.lessons = lessons;
    }
    
    public WaitingList getWaitList() {
        return waitList;
    }
    
    public void createInstances() {
        DataInitialiser data = new DataInitialiser();
        
        data.createStudents();
        data.createInstructors();
        data.createSessions();
        
        this.setStudents(data.getStudents());
        this.setInstructors(data.getInstructors());
        this.setLessons(data.getLessons());
    }
    
    public void assignToSessions() {
        alternator.assignStudentsToSessions(this.getStudents(), this.getLessons(), this.getWaitList());
        // promote some students after
        alternator.promoteToImproverAndAdvanced(this.getInstructors(), this.getStudents(), this.getWaitList());
    }
    
    public void alternateLevels() {        
        alternator.setLevels(this.getInstructors(), this.getStudents());
    }
    
    public ArrayList<SwimStudent> getSortedStudents() {
        // copy the original ArrayList
        ArrayList<SwimStudent> sortedStudentsByName = this.getStudents();
        // sort by name
        sortedStudentsByName.sort(Comparator.comparing(SwimStudent::getName));
        
        return sortedStudentsByName;
    }
    
    public ArrayList<Instructor> getSortedInstructors() {
        // copy the original ArrayList
        ArrayList<Instructor> sortedInstructorsByName = this.getInstructors();
        // sort by name
        sortedInstructorsByName.sort(Comparator.comparing(Instructor::getName));
        
        return sortedInstructorsByName;
    }
}
