package task2.Domain;

public abstract class Qualification {
    private final Instructor ISSUER;
    // qualification level

    public Qualification(Instructor i) {
        this.ISSUER = i;
    }

    public Instructor getIssuer() {
        return ISSUER;
    }
    
    public abstract String getAward();
    
    @Override
    public abstract String toString();
}
