package edu.pdx.cs410J.hensley2;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.pdx.cs410J.AbstractAppointmentBook;

/**
 * validation utility for project. Using MkYong Example of using the simple date format to do the
 * validation http://www.mkyong.com/java/how-to-check-if-date-is-valid-in-java/
 */
public class Validator {

    private static final String validDateFormat = "M/d/yyyy H:m";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(validDateFormat);

    static {
        sdf.setLenient(false);
    }

    private Validator() {
    }

    /**
     * <p>Takes the two command line strings for date and time and validates if they are in the
     * correct format. Correct format is month (either 1 or 2 digits)/d (either 1 or two
     * digits)/yyyy hour(0-23):minute(0-59) </p>
     *
     * @param monthDayYearString the month/day/year string to validate
     * @param hourMinuteString   the hour:minute string to validate
     * @return If the month/day/year string and hour:minute string make up a valid {@link
     * Appointment} time
     */
    public static Boolean validateCliDateFormat(String monthDayYearString, String hourMinuteString) {
        if (monthDayYearString == null || hourMinuteString == null) {
            return false;
        }

        //Cli Date Format cannot contain an am or pm
        if (hourMinuteString.toLowerCase().contains("am")
                || hourMinuteString.toLowerCase().contains("pm")) {
            return false;
        }

        sdf.setLenient(false);

        try {
            sdf.parse(monthDayYearString + " " + hourMinuteString);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * Validates that the start date is before the end date. Also validates the date strings are
     * valid.
     *
     * @param beginMonthDayYearString the begin month/day/year string
     * @param beginHourMinuteString   the beginning hour:minute string
     * @param endMonthDayYearString   the end month/day/year string
     * @param endHourMinuteString     the end hour:minute string
     * @return whether the date/time strings passed in are valid and whether the beginning time is
     * less than the end time.
     */
    public static Boolean validateAppointmentTime(String beginMonthDayYearString, String beginHourMinuteString,
                                                  String endMonthDayYearString, String endHourMinuteString) {
        if (!Validator.validateCliDateFormat(beginMonthDayYearString, beginHourMinuteString) ||
                !Validator.validateCliDateFormat(endMonthDayYearString, endHourMinuteString)) {
            return false;
        }

        try {
            Date startDate = sdf.parse(beginMonthDayYearString + " " + beginHourMinuteString);
            Date endDate = sdf.parse(endMonthDayYearString + " " + endHourMinuteString);

            //Validate Start Date is less than end date
            return endDate.after(startDate);
        } catch (ParseException e) {
            return false;
        }

    }

}
