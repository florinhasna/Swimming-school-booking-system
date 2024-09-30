package task2.Domain;

/* Enum used for personal survival qualification awards */
public enum Medal {
    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold");
    
    private final String DISPLAY_NAME;
    
    private Medal(String displayName){
        this.DISPLAY_NAME = displayName;
    }
    
    public String getDisplayName() {
        return this.DISPLAY_NAME;
    }
    
}
