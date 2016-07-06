package edu.pdx.cs410J.hensley2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p> The @Link{CliParser} for parsing command line arguments and options. Can validate various
 * parts of the command line arguments and also parse the options and arguments passed in. To
 * validate all arguments, call .build.validateAll.parseOptions.parseArgs(). </p>
 */
public class CliParser {

    public static final String PRINT_FLAG = "-print";
    public static final String OWNER_KEY = "owner";
    public static final String DESCRIPTION_KEY = "description";
    public static final String BEGIN_TIME_KEY = "beginTime";
    public static final String END_TIME_KEY = "endTime";

    private static final String OPTIONS_DELIMINATOR = "-";
    private static final Integer OWNER_POSITION = 0;
    private static final Integer DESCRIPTION_POSITION = 1;
    private static final Integer BEGIN_TIME_DATE_POSITION = 2;
    private static final Integer BEGIN_TIME_TIME_POSITION = 3;
    private static final Integer END_TIME_DATE_POSITION = 4;
    private static final Integer END_TIME_TIME_POSITION = 5;


    /**
     * Valid command line options
     */
    private static final List<String> VALID_OPTIONS = Arrays.asList("-print", "-README");

    /**
     * Number of expected args - the start date and end date take up 2 arguments so there should be
     * 6 total in this order: owner, description, startTime, endTime
     */
    private static final Integer EXPECT_ARG_SIZE = 6;


    /**
     * Max number of command line arguments, including options
     */
    protected static final Integer MAX_COMMAND_LINE_ARGS = EXPECT_ARG_SIZE + VALID_OPTIONS.size();

    /**
     * Command line arguments as a list
     */
    private List<String> cliArgs;

    /**
     * CLI argument to print appointment when program runs
     */
    private Map<String, String> parsedArgs;


    private CliParser() {
    }

    /**
     * Builds a new Cli Parser from command line arguments
     *
     * @param args - the command line arguments to build the parser with.
     * @return CliParser instance Will throw a {@link IllegalArgumentException if the arguments are
     * null}
     */

    public static CliParser build(String[] args) {
        if (args == null)
            throw new IllegalArgumentException("Missing command line arguments");
        CliParser parser = new CliParser();
        parser.parsedArgs = new HashMap<>();
        parser.cliArgs = Arrays.asList(args);
        return parser;
    }

    /**
     * Validates all command line arguments - should be called before parsing into Appointment
     * Argument. If arguments are invalid, CliParser will throw an exception.
     *
     * @return this instance of the CliParser for method chaining
     */
    public CliParser validateAll() {
        validateNonNullArgs();
        validateMaxNumberOfArgs();
        validateOptions();
        validateArguments();
        return this;
    }

    /**
     * Validates that there are the correct number of arguments passed in. Will throw a {@link
     * IllegalArgumentException} if the arguments are invalid.
     */
    public void validateArguments() {
        /**
         * Validate there are 6 args
         */

        List<String> args = cliArgs.stream()
                .filter(s -> !(s.startsWith(OPTIONS_DELIMINATOR)))
                .collect(Collectors.toList());

        if (args.size() != EXPECT_ARG_SIZE) {

            throw new IllegalArgumentException("Invalid number of arguments!\n" +
                    "Number Expected: " + Integer.toString(EXPECT_ARG_SIZE) + "\n" +
                    "Number Received: " + Integer.toString(args.size()));
        }
    }

    /**
     * Validates options passed in are valid. Will throw a {@link IllegalArgumentException} if the
     * options are invalid. Validates that options (all entries that start with a -) are valid
     * options. A valid option should be in position 1 or position 2 and be either -print or
     * -README
     */
    public void validateOptions() {

        cliArgs.stream()
                .filter(s -> s.startsWith(OPTIONS_DELIMINATOR))
                .forEach((option) -> {
                    if (!VALID_OPTIONS.contains(option)) {
                        throw new IllegalArgumentException("Invalid Option! Please see -README for valid options");
                    }
                    if (cliArgs.indexOf(option) > 1) {
                        throw new IllegalArgumentException("Option at Invalid Position. Option must come before args");
                    }
                });
    }

    /**
     * Validate that command line arguments are less than the number of possible command line
     * arguments. Will throw a {@link IllegalArgumentException} if the options are invalid.
     */

    public void validateMaxNumberOfArgs() {

        if (cliArgs.size() > MAX_COMMAND_LINE_ARGS) {
            throw new IllegalArgumentException("Too many command line arguments.\n +" +
                    "Number Entered: " + Integer.toString(cliArgs.size()) + "\n" +
                    "Max Number Expected: " + Integer.toString(MAX_COMMAND_LINE_ARGS));
        }
    }

    /**
     * Validate that command line arguments aren't empty. Will throw a
     *
     * {@link IllegalArgumentException} if the command line arguments are empty.
     */
    public void validateNonNullArgs() {
        if (cliArgs == null || cliArgs.size() == 0) {
            throw new IllegalArgumentException("Missing command line arguments");
        }
    }

    /**
     * parses the flag different valid options and puts it into the parsed args map. Returns
     *
     * @return the instance of this CliParser for method chaining.
     */
    public CliParser parseOptions() {

        if (this.cliArgs.contains(PRINT_FLAG)) {
            parsedArgs.put("-print", "true");
        }

        return this;
    }

    /**
     * Parses arguments from the non-option command line args args must be passed in this format:
     * owner description startDate(d/m/yyy) startDate(hh:mm) endDate(d/m/yyy) endDate(hh:mm)
     *
     * @return {@link Map}of the parsed arguments arguments.
     */
    public Map<String, String> parseArgs() {
        List<String> args = cliArgs.stream()
                .filter(s -> !(s.startsWith(OPTIONS_DELIMINATOR)))
                .collect(Collectors.toList());
        //Validate the dates
        if (!DateValidator.validateCliDateFormat(args.get(BEGIN_TIME_DATE_POSITION), args.get(BEGIN_TIME_TIME_POSITION)) ||
                !DateValidator.validateCliDateFormat(args.get(END_TIME_DATE_POSITION), args.get(END_TIME_TIME_POSITION))) {
            throw new IllegalArgumentException("Invalid Date Format - Date Format must be dd/mm/yyyy hh:mm");
        }

        //Validate start appointment time less than end appointment time
        if (!DateValidator.validateAppointmentTime(args.get(BEGIN_TIME_DATE_POSITION), args.get(BEGIN_TIME_TIME_POSITION),
                args.get(END_TIME_DATE_POSITION), args.get(END_TIME_TIME_POSITION))) {
            throw new IllegalArgumentException("Appointment Start Time must be less than End Time");
        }

        //Validate description is not empty
        if (args.get(DESCRIPTION_POSITION).isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty!");
        }

        parsedArgs.put(OWNER_KEY, args.get(OWNER_POSITION));
        parsedArgs.put(DESCRIPTION_KEY, args.get(DESCRIPTION_POSITION));
        parsedArgs.put(BEGIN_TIME_KEY, args.get(BEGIN_TIME_DATE_POSITION) + " " + args.get(BEGIN_TIME_TIME_POSITION));
        parsedArgs.put(END_TIME_KEY, args.get(END_TIME_DATE_POSITION) + " " + args.get(END_TIME_TIME_POSITION));
        return parsedArgs;
    }
}
