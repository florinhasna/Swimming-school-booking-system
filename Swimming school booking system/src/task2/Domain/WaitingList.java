package task2.Domain;

import java.util.ArrayList;
import java.util.Comparator;

/* Waiting list for students not being able to join an available lesson */
public class WaitingList {

    private final ArrayList<SwimStudent> students = new ArrayList<>();

    public WaitingList() {

    }

    public ArrayList<SwimStudent> getStudents() {
        return students;
    }

    public void addToWaitingList(SwimStudent ss) {
        // to avoid duplication
        if(!this.getStudents().contains(ss)){
            this.getStudents().add(ss);
        }
    }

    public void removeFromWaitingList(SwimStudent ss) {
        this.getStudents().remove(ss);
    }

    // returns a sorted array by level, novice to advanced
    public ArrayList<SwimStudent> getSortedArray() {
        ArrayList<SwimStudent> sortedArray = new ArrayList<>();

        for (Level l : Level.values()) {
            addSortedStudents(sortedArray, l);
        }

        return sortedArray;
    }

    // gets a list for the students of a level to be added to
    private void addSortedStudents(ArrayList<SwimStudent> listToAddTo, Level ofLevel) {
        ArrayList<SwimStudent> newList = new ArrayList<>();

        this.getStudents().stream().filter(ss -> (ss.getLevel().equals(ofLevel))).forEachOrdered(newList::add);

        // sort by name
        newList.sort(Comparator.comparing(SwimStudent::getName));
        listToAddTo.addAll(newList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        students.forEach(ss -> {
            sb.append(ss.getName()).append("\n");
        });

        return sb.toString();
    }
}
