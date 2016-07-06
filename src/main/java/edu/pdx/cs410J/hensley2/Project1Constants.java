package edu.pdx.cs410J.hensley2;

/**
 * Created by hensleym on 6/28/16.
 */
public class Project1Constants {

    public static final String README_OPTION = "-README";

    private Project1Constants(){}

    public static final String README =
            "usage: java -jar apptbook-<version>.jar [options] <args>\n" +
                    "args are (in this order):\n" +
                    "owner: The person whose owns the appt book - use double quotes to include first name and last name\n" +
                    "example: \"Jeff Smith\"\n"+
                    "description: A description of the appointment - use double quotes for multi word description.\n" +
                    "*Note* Description Cannot be empty!\n" +
                    "example: \"This is a Description\"\n" +
                    "beginTime: When the appt begins (24-hour time)\n" +
                    "endTime: When the appt ends (24-hour time)\n" +
                    "Date and time should be in the format: mm/dd/yyyy hh:mm (Do not use quotes for date/time)\n\n\n" +
                    "options are (options may appear in any order):\n" +
                    "-print Prints a description of the new appointment\n" +
                    "-README Prints a README for this project and exits\n";

}
