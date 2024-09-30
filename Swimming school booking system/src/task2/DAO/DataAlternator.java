package task2.DAO;

import task2.Domain.DistanceSwim;
import task2.Domain.SwimStudent;
import task2.Domain.Level;
import task2.Domain.Instructor;
import task2.Domain.SwimLesson;
import task2.Domain.Qualification;
import task2.Domain.WaitingList;
import java.util.ArrayList;

/* Responsible with setting objects in different states */
public class DataAlternator {

    public DataAlternator() {

    }

    // method to set some students to improver and advanced
    public void setLevels(ArrayList<Instructor> instructors, ArrayList<SwimStudent> students) {
        for (int idx = 0; idx < students.size(); idx++) {
            // for every 5 students, promote to improvers
            if (idx % 5 == 0) {
                if (!students.get(idx).isOnWaitingList()) {

                    boolean flag = true;
                    for (Qualification q : students.get(idx).getQualifications()) {
                        // check if the distance value was already awarded
                        if (q.getAward().contains(20 + "")) {
                            flag = false;
                        }
                    }

                    // if flag is true, then the distance award was not already awarded
                    if (flag) {
                        instructors.get(0).issueQualification(students.get(idx), new DistanceSwim(20, instructors.get(0)));
                    }

                    // for every 10 students, promote from improvers to advanced
                    if (idx % 10 == 0) {
                        flag = true;
                        for (Qualification q : students.get(idx).getQualifications()) {
                            // check if the distance value was already awarded
                            if (q.getAward().contains(400 + "")) {
                                flag = false;
                            }
                        }

                        if (flag) {
                            instructors.get(1).issueQualification(students.get(idx), new DistanceSwim(400, instructors.get(1)));
                        }
                    }

                }
            }
        }

    }

    public void assignStudentsToSessions(ArrayList<SwimStudent> students, ArrayList<SwimLesson> lessons, WaitingList aWaitingList) {
        students.forEach(ss -> {
            int anIndex;
            do {
                anIndex = (int) (0 + Math.random() * (lessons.size() - 1));
            } while (!ss.getLevel().equals(lessons.get(anIndex).getLevel()));
            // if the lesson of anIndex position is full, try again
            if (lessons.get(anIndex).isFull()) {
                do {
                    anIndex = (int) (0 + Math.random() * (lessons.size() - 1));
                } while (!ss.getLevel().equals(lessons.get(anIndex).getLevel()));
            }
            // if is full again, place student on waiting list
            if (lessons.get(anIndex).isFull()) {
                aWaitingList.addToWaitingList(ss);
                ss.setIsOnWaitingList(true);
            }
            // if student not allocated and not on the waiting list, allocate lesson
            if (!ss.isAllocated() && !ss.isOnWaitingList()) {
                lessons.get(anIndex).assignStudent(ss);
                ss.setBookedLesson(lessons.get(anIndex));
            }
        });
    }

    // to promote after students were assigned to lessons
    public void promoteToImproverAndAdvanced(ArrayList<Instructor> instructors, ArrayList<SwimStudent> students,
            WaitingList aWaitingList) {
        for (int idx = 0; idx < students.size(); idx++) {
            // for every 10 students, promote to improvers and add on waiting list
            if (idx % 16 == 0 && students.get(idx).getLevel().equals(Level.NOVICE)) {
                instructors.get(2).issueQualification(students.get(idx), new DistanceSwim(20, instructors.get(2)));
                aWaitingList.addToWaitingList(students.get(idx));
                students.get(idx).setIsOnWaitingList(true);
            } // at every 15, if the student is improver, promote to advanced and add on waiting list
            else if (idx % 15 == 0 && students.get(idx).getLevel().equals(Level.IMPROVER)) {
                instructors.get(1).issueQualification(students.get(idx), new DistanceSwim(400, instructors.get(1)));
                aWaitingList.addToWaitingList(students.get(idx));
                students.get(idx).setIsOnWaitingList(true);
            }
        }
    }
}
