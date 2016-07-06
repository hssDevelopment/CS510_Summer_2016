package edu.pdx.cs410J.hensley2;

import java.util.Map;

import static java.lang.System.exit;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

    public static void main(String[] args) {
        try {
            CliParser parser = CliParser.build(args);
            parser.validateNonNullArgs();

            //Check if README is in the first or second position, if so
            //Exit the program
            if (args[0].equals(Project1Constants.README_OPTION) ||
                    (args.length > 1 && args[1].equals(Project1Constants.README_OPTION))) {
                System.out.println(Project1Constants.README);
                exit(0);
            }

            Map<String, String> parsedArgs = parser
                    .validateAll()
                    .parseOptions()
                    .parseArgs();

            AppointmentBook ab = new AppointmentBook();
            ab.setOwnerName(parsedArgs.get(CliParser.OWNER_KEY));

            Appointment appt = new Appointment();
            appt.setDescription(parsedArgs.get(CliParser.DESCRIPTION_KEY));
            appt.setBeginTime(parsedArgs.get(CliParser.BEGIN_TIME_KEY));
            appt.setEndTime(parsedArgs.get(CliParser.END_TIME_KEY));

            ab.addAppointment(appt);

            if (parsedArgs.get(CliParser.PRINT_FLAG) != null) {
                System.out.println(appt.toString());
            }

            exit(0);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            exit(1);
        }

    }

}