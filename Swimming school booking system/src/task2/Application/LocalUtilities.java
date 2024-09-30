package task2.Application;

import task2.Domain.Medal;
import task2.Domain.Level;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/* Responsible with reading inputs from the user and converting to relevant objects
and other useful tools */
public class LocalUtilities {

    /* Read input from user, return the DayOfWeek if is valid, throws
    exception otherwise */
    public static DayOfWeek getDay() throws InputMismatchException, InterruptedException {
        String input = promptSelect(String.class);

        if (input.equals("0")) {
            throw new InterruptedException("Action cancelled");
        }

        if (isDayValid(input)) {
            return DayOfWeek.valueOf(input.toUpperCase());
        } else {
            throw new InputMismatchException("Unaccepted day input");
        }
    }

    // reads an input as a string and returns the Medal objects, if the name matches
    public static Medal getMedal() throws InterruptedException {
        String input = promptSelect(String.class);
        if (input.equals("0")) {
            throw new InterruptedException("Action cancelled");
        }

        Medal m = null;

        if (input.equalsIgnoreCase(Medal.BRONZE.getDisplayName())) {
            m = Medal.BRONZE;
        } else if (input.equalsIgnoreCase(Medal.SILVER.getDisplayName())) {
            m = Medal.SILVER;
        } else if (input.equalsIgnoreCase(Medal.GOLD.getDisplayName())) {
            m = Medal.GOLD;
        }

        return m;
    }

    /* Method to check if a give string input is one of the accepted days */
    private static boolean isDayValid(String input) {
        // unique set of elements for accepted days
        HashSet<DayOfWeek> acceptedDays = new HashSet<>();
        acceptedDays.add(DayOfWeek.MONDAY);
        acceptedDays.add(DayOfWeek.WEDNESDAY);
        acceptedDays.add(DayOfWeek.FRIDAY);

        for (DayOfWeek dow : acceptedDays) {
            if (dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH).equalsIgnoreCase(input)) {
                return true;
            }
        }

        return false;
    }

    /* Read time input, as a String in a special format, converts and return the LocalTime object  */
    public static LocalTime getTime() throws InputMismatchException, InterruptedException {
        final String TIME_FORMAT = "HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);

        String input = promptSelect(String.class);
        if (input.equals("0")) {
            throw new InterruptedException("Action cancelled");
        }
        LocalTime givenTime;

        try {
            givenTime = LocalTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new InputMismatchException("Invalid time format, must be HH:mm");
        }

        if (checkTimeRange(givenTime)) {
            return givenTime;
        } else {
            throw new InputMismatchException("Time out of range");
        }
    }

    // validate a given time to be in an accepted range
    private static boolean checkTimeRange(LocalTime lt) {
        // earliest start 17:00
        LocalTime MIN_START = LocalTime.NOON.plusHours(5).minusMinutes(1);
        // latest start 19:30
        LocalTime MAX_START = LocalTime.NOON.plusHours(7).plusMinutes(31);

        return lt.isAfter(MIN_START) && lt.isBefore(MAX_START);
    }

    // checks if a string input matches a level description, returns that level if so
    public static Level getLevel() throws InputMismatchException, InterruptedException {
        String input = promptSelect(String.class);
        if (input.equals("0")) {
            throw new InterruptedException("Action cancelled");
        }
        Level[] levels = {Level.NOVICE, Level.IMPROVER, Level.ADVANCED};

        for (Level l : levels) {
            if (l.getDisplayName().equalsIgnoreCase(input)) {
                return l;
            }
        }

        throw new InputMismatchException("Invalid time format, must be HH:mm");
    }

    // check if the given distance is member of the arraylist
    public static boolean checkDistanceInput(String distance, ArrayList<String> distances) {
        boolean result = true;

        for (String s : distances) {
            if(distance.equalsIgnoreCase(s)){
                result = false;
            }
        }

        return result;
    }
    
    public static boolean testName(String name) {
        String nameRegex = "^[A-Za-z]+\\s+[A-Za-z]+$";

        return name.matches(nameRegex);
    }

    // repeat a string for a number of times
    public static String repeat(String charSeq, int numberOfTimes) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numberOfTimes; i++) {
            sb.append(charSeq);
        }

        return sb.toString();
    }

    /* generic method to get different type of inputs, the argument is the type desired
    throws exception if type not supported */
    public static <T> T promptSelect(Class<T> type) throws InputMismatchException {
        Scanner input = new Scanner(System.in);
        System.out.print(" > ");

        // user inputs an integer
        if (type.equals(Integer.class
        )) {
            try {
                return type.cast(input.nextInt());
            } catch (InputMismatchException e) {
                throw new InputMismatchException("Integer required");
            }
        } else { // return a string
            return type.cast(input.nextLine().trim());
        }
    }
}
