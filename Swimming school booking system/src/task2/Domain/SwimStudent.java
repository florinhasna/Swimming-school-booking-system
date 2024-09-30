package task2.Domain;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import task2.Application.LocalUtilities;

public class SwimStudent {

    private final String name;
    private Level level;
    private final ArrayList<Qualification> qualificationsAwarded = new ArrayList<>();
    private SwimLesson bookedLesson;
    private boolean isOnWaitingList = false;

    public SwimStudent(String name) {
        this.name = name;
        this.level = Level.NOVICE;
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level aLevel) {
        this.level = aLevel;
    }

    public ArrayList<Qualification> getQualifications() {
        return qualificationsAwarded;
    }
    
    // returns the list of all DistanceSwim qualifications
    public ArrayList<DistanceSwim> getDistanceSwimAwards() {
        ArrayList<DistanceSwim> ds = new ArrayList<>();
        this.getQualifications().stream().filter(q -> (q instanceof DistanceSwim)).forEachOrdered(q -> {
            ds.add((DistanceSwim) q);
        });

        return ds;
    }

    // returns the list of all PersonalSurvival qualifications
    public ArrayList<PersonalSurvival> getPersonalSurvivalAwards() {
        ArrayList<PersonalSurvival> ps = new ArrayList<>();
        this.getQualifications().stream().filter(q -> (q instanceof PersonalSurvival)).forEachOrdered(q -> {
            ps.add((PersonalSurvival) q);
        });

        return ps;
    }

    public SwimLesson getBookedLesson() {
        return bookedLesson;
    }

    // used when allocated a lesson
    public void setBookedLesson(SwimLesson sl) {
        this.bookedLesson = sl;
        this.setIsOnWaitingList(false);
    }

    // add a qualification
    public void addQualificationAward(Qualification q) {
        this.getQualifications().add(q);

        // check if the student needs to be promoted
        if (q.getAward().equals(this.getLevel().getMetersForThirdAward() + "")) {
            if (this.getLevel().equals(Level.NOVICE)) {
                this.setLevel(Level.IMPROVER);
            } else {
                this.setLevel(Level.ADVANCED);
            }
        }
    }

    // check if is allocated or not for the week
    public boolean isAllocated() {
        return bookedLesson != null; // if is not null, return true
    }

    // if the student is on the waiting list
    public boolean isOnWaitingList() {
        return this.isOnWaitingList;
    }

    public void setIsOnWaitingList(boolean toSet) {
        this.isOnWaitingList = toSet;
    }

    // Relevant format to be printed when requested, used for the first use case
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");

        sb.append(LocalUtilities.repeat("-", 12));
        sb.append("SESSION TO ATTEND");
        sb.append(LocalUtilities.repeat("-", 11)).append("\n");

        if (this.isAllocated()) {
            sb.append(String.format("| %-11s%25s |\n", "Day:", this.getBookedLesson().getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH)));
            sb.append(String.format("| %-11s%25s |\n", "Time:", this.getBookedLesson().getStartTime().toString()));
            sb.append(String.format("| %-11s%25s |\n", "Instructor:", this.getBookedLesson().getInstructor().getName()));
        } else if (this.isOnWaitingList() && this.getLevel().equals(Level.NOVICE)) {
            sb.append(String.format("| %-36s |\n", "Currently on WAITING list."));
        } else {
            sb.append(String.format("| %-36s |\n", "No booked lesson."));
        }

        sb.append(LocalUtilities.repeat("-", 6));
        sb.append("DISTANCE SWIM QUALIFICATIONS");
        sb.append(LocalUtilities.repeat("-", 6)).append("\n");

        if (this.getDistanceSwimAwards().isEmpty()) {
            sb.append(String.format("| %-36s |\n", "No distance swim awards."));
        } else {
            this.getDistanceSwimAwards().sort(Comparator.comparingInt(DistanceSwim::getDistance));
            this.getDistanceSwimAwards().forEach(q -> {
                sb.append(q.toString()).append("\n");
            });
        }

        if (this.getLevel().equals(Level.ADVANCED)) {
            sb.append(LocalUtilities.repeat("-", 9));
            sb.append("PERSONAL SURVIVAL MEDALS");
            sb.append(LocalUtilities.repeat("-", 8)).append("\n");

            if (this.getPersonalSurvivalAwards().isEmpty()) {
                sb.append(String.format("| %-36s |\n", "No personal survival medal."));
            } else {
                getPersonalSurvivalAwards().forEach(ps -> {
                    sb.append(ps.toString()).append("\n");
                });
            }
        }

        sb.append(LocalUtilities.repeat("-", 40)).append("\n");

        return sb.toString();
    }
}
