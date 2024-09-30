package task2.Domain;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import task2.Application.LocalUtilities;

public class Instructor {

    private final String NAME;
    private final ArrayList<SwimLesson> LESSONS = new ArrayList<>();

    public Instructor(String n) {
        this.NAME = n;
    }

    public String getName() {
        return NAME;
    }

    public ArrayList<SwimLesson> getLessons() {
        return LESSONS;
    }

    public void assignLesson(SwimLesson sl) {
        this.getLessons().add(sl);
    }

    public void issueQualification(SwimStudent ss, Qualification q) {
        ss.addQualificationAward(q);
    }

    // method used to print relevant data of an instructor in use case 2
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.getLessons().isEmpty()) {
            sb.append("\n");
            sb.append(this.getName()).append(" does not have any lessons assigned currently.").append("\n");
        } else {
            this.getLessons().stream().map(sl -> {
                sb.append("\n");
                sb.append(LocalUtilities.repeat("-", 32)).append("\n");
                sb.append(String.format("| %-8s%20s |\n", "Day:", sl.getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH)));
                return sl;
            }).map(sl -> {
                sb.append(String.format("| %-8s%20s |\n", "Time:", sl.getStartTime().toString()));
                return sl;
            }).map(sl -> {
                sb.append(String.format("| %-8s%20s |\n", "Level:", sl.getLevel().getDisplayName()));
                return sl;
            }).map(sl -> {
                sb.append(LocalUtilities.repeat("-", 12));
                sb.append("ATTENDEES");
                sb.append(LocalUtilities.repeat("-", 11)).append("\n");
                if (sl.getStudents().isEmpty()) {
                    sb.append(String.format("| %-28s |\n", "No student registered."));
                } else {
                    sl.getStudents().forEach(ss -> {
                        sb.append(String.format("| %-28s |\n", ss.getName()));
                    });
                }
                return sl;                
            }).forEachOrdered(_item -> {
                sb.append(LocalUtilities.repeat("-", 32)).append("\n");
            });
        }

        return sb.toString();
    }
}
