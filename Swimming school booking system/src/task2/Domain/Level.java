package task2.Domain;

/* Enum used for SwimStudent three different levels */
public enum Level {
    NOVICE(5, 10, 20, "Novice"),
    IMPROVER(100, 200, 400, "Improvers"),
    ADVANCED(800, 1500, 3000, "Advanced");
    
    private final int METERS_FOR_FIRST_AWARD;
    private final int METERS_FOR_SECOND_AWARD;
    private final int METERS_FOR_THIRD_AWARD;
    private final String DISPLAY_NAME;
    
    private Level (int mff, int mfs, int mft, String dn){
        this.METERS_FOR_FIRST_AWARD = mff;
        this.METERS_FOR_SECOND_AWARD = mfs;
        this.METERS_FOR_THIRD_AWARD = mft;
        this.DISPLAY_NAME = dn;
    }

    public int getMetersForFirstAward() {
        return METERS_FOR_FIRST_AWARD;
    }

    public int getMetersForSecondAward() {
        return METERS_FOR_SECOND_AWARD;
    }

    public int getMetersForThirdAward() {
        return METERS_FOR_THIRD_AWARD;
    }
    
    // returns an array holding all the distances required for a certificate of a level
    public int[] getMeterThresholds() {
        int[] awardLevels = new int[3];
        awardLevels[0] = getMetersForFirstAward();
        awardLevels[1] = getMetersForSecondAward();
        awardLevels[2] = getMetersForThirdAward();
        return awardLevels;
    }
    
    public String getDisplayName() {
        return DISPLAY_NAME;
    }
}
