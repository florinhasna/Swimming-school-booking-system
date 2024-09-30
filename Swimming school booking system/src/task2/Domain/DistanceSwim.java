package task2.Domain;

public class DistanceSwim extends Qualification {
    private final int distance;
    
    public DistanceSwim(int d, Instructor i) {
        super(i);
        this.distance = d;
    }

    public int getDistance() {
        return distance;
    }
    
    @Override
    public String getAward() {
        return this.getDistance() + "";
    }
    
    // invoked to print award of a student holding it
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("| %-31s%5s |", this.getIssuer().getName(), this.getAward().concat("m")));
        
        return sb.toString();
    }
}
