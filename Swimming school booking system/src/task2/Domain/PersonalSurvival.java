package task2.Domain;

public class PersonalSurvival extends Qualification {
    private final Medal LEVEL; // award category: Bronze, Silver or Gold
    

    public PersonalSurvival(Medal level, Instructor i) {
        super(i);
        this.LEVEL = level;
    }

    public Medal getLevel() {
        return LEVEL;
    }
    
    @Override
    public String getAward() {
        return this.getLevel().getDisplayName();
    }
    
    // invoked to print award of a student holding it
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("| %-30s%6s |", this.getIssuer().getName(), this.getAward()));
        
        return sb.toString();
    }
}
