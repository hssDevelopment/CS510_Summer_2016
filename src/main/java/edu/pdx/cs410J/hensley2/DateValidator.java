package edu.pdx.cs410J.hensley2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hensleym on 6/28/16.
 * Using MkYong Example of using the simple date format to do the validation
 * http://www.mkyong.com/java/how-to-check-if-date-is-valid-in-java/
 */
public class DateValidator {

    private static String validDateFormat = "M/d/yyyy H:m";
    private static SimpleDateFormat sdf = new SimpleDateFormat(validDateFormat);

    {
        sdf.setLenient(false);
    }

    private DateValidator(){}

    /**
     * Takes the two command line strings for date and time
     * and validates if they are in the correct format. Correct
     * format is month (either 1 or 2 digits)/d (either 1 or two digits)/yyyy hour(0-23):minute(0-59)
     *
     * @param monthDayYearString
     * @param hourMinuteString
     * @return
     */
    public static Boolean validateCliDateFormat(String monthDayYearString, String hourMinuteString){
        if(monthDayYearString == null || hourMinuteString == null){
            return false;
        }

        //Cli Date Format cannot contain an am or pm
        if(hourMinuteString.toLowerCase().contains("am")
                || hourMinuteString.toLowerCase().contains("pm")){
            return false;
        }

        sdf.setLenient(false);

        try {
            sdf.parse(monthDayYearString + " " + hourMinuteString);
        }
        catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * Validates that the start date is before the end date. Also validates the date strings are valid.
     * @param beginMonthDayYearString
     * @param beginHourMinuteString
     * @param endMonthDayYearString
     * @param endHourMinuteString
     * @return
     */
    public static Boolean validateAppointmentTime(String beginMonthDayYearString, String beginHourMinuteString,
                                                  String endMonthDayYearString, String endHourMinuteString){
        if(!DateValidator.validateCliDateFormat(beginMonthDayYearString, beginHourMinuteString) ||
                !DateValidator.validateCliDateFormat(endMonthDayYearString, endHourMinuteString)){
            return false;
        }

        try{
            Date startDate = sdf.parse(beginMonthDayYearString + " " + beginHourMinuteString);
            Date endDate = sdf.parse(endMonthDayYearString + " " + endHourMinuteString);

            //Validate Start Date is less than end date
            return endDate.after(startDate);
        }
        catch (ParseException e){
            return false;
        }

    }

}
