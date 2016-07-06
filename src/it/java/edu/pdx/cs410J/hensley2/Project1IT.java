package edu.pdx.cs410J.hensley2;

import org.junit.Test;

import edu.pdx.cs410J.InvokeMainTestCase;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project1.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("Missing command line arguments"));
        assertThat(result.getOut(), equalTo(""));
    }

    @Test
    public void testNullCommandLineArguments() {
        MainMethodResult result = invokeMain(null);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("Missing command line arguments"));
        assertThat(result.getOut(), equalTo(""));
    }


    @Test
    public void testReadmeCommandLineArgument() {
        MainMethodResult result = invokeMain("-README", "-print", "Mike Hensley", "A Description",
                "01/01/2016", "04:00", "01/01/2016", "05:00");
        assertThat(result.getOut(), containsString(Project1Constants.README));
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getErr(), equalTo(""));

    }

    @Test
    public void testReadmeCommandInSecondPosition() {
        MainMethodResult result = invokeMain("-print", "-README", "Mike Hensley", "A Description",
                "01/01/2016", "04:00", "01/01/2016", "05:00");
        System.out.println(result.getErr());
        assertThat(result.getOut(), containsString(Project1Constants.README));
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getErr(), equalTo(""));
    }

    @Test
    public void testTooManyCommandLineOptions() {
        MainMethodResult result = invokeMain("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("Too many command line arguments"));
        assertThat(result.getErr(), containsString("Number Entered: 10"));
        assertThat(result.getErr(), containsString("Number Expected: " + CliParser.MAX_COMMAND_LINE_ARGS));
    }

    @Test
    public void testInvalidOptionsArgument() {
        MainMethodResult result = invokeMain("-print", "Mike Hensley", "-InvalidOption", "A Description",
                "01/01/2016", "04:00", "01/01/2016", "6:00");

        assertThat(result.getOut(), equalTo(""));
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString("Invalid Option! Please see -README for valid options"));
    }

    @Test
    public void testEmptyDescription() {
        MainMethodResult result = invokeMain("-print", "Owner", "", "06/05/2016", "14:25", "06/06/2016",
                "14:10");

        assertThat(result.getErr(), containsString("Description cannot be empty!"));
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getOut(), equalTo(""));
    }

    @Test
    public void testInvalidStartDate() {
        MainMethodResult result = invokeMain("-print", "Owner", "Description", "Invalid Date",
                "14:25", "06/06/2016", "14:10");

        assertThat(result.getErr(), containsString("Invalid Date Format - Date Format must be dd/mm/yyyy hh:mm"));
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getOut(), equalTo(""));
    }

    @Test
    public void testInvalidStartTime() {
        MainMethodResult result = invokeMain("-print", "Owner", "Description", "05/05/2016",
                "Invalid Time", "06/06/2016", "14:10");

        assertThat(result.getErr(), containsString("Invalid Date Format - Date Format must be dd/mm/yyyy hh:mm"));
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getOut(), equalTo(""));
    }

    @Test
    public void testInvalidEndDate() {
        MainMethodResult result = invokeMain("-print", "Owner", "Description", "05/05/2016",
                "12:30", "Invalid Date", "14:10");

        assertThat(result.getErr(), containsString("Invalid Date Format - Date Format must be dd/mm/yyyy hh:mm"));
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getOut(), equalTo(""));
    }

    @Test
    public void testInvalidEndTime() {
        MainMethodResult result = invokeMain("-print", "Owner", "Description", "05/05/2016",
                "12:30", "06/05/2016", "Invalid Time");

        assertThat(result.getErr(), containsString("Invalid Date Format - Date Format must be dd/mm/yyyy hh:mm"));
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getOut(), equalTo(""));
    }

    @Test
    public void testStartDateBiggerThanEndDate() {
        MainMethodResult result = invokeMain("-print", "Owner", "Description", "05/10/2016",
                "12:30", "05/05/2016", "13:30");

        assertThat(result.getErr(), containsString("Appointment Start Time must be less than End Time"));
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getOut(), equalTo(""));
    }

    @Test
    public void testStartTimeBiggerThanEndTime() {
        MainMethodResult result = invokeMain("-print", "Owner", "Description", "05/10/2016",
                "12:30", "05/10/2016", "11:30");

        assertThat(result.getErr(), containsString("Appointment Start Time must be less than End Time"));
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getOut(), equalTo(""));
    }


    @Test
    public void testStartTimeAndEndTimeAreEqual() {

        MainMethodResult result = invokeMain("-print", "Some Owner", "Some Description", "05/05/2016",
                "12:30", "05/05/2016", "12:30");

        assertThat(result.getErr(), containsString("Appointment Start Time must be less than End Time"));
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getOut(), equalTo(""));
    }

    @Test
    public void testValidOptionsWithoutPrint() {
        MainMethodResult result = invokeMain("Some Owner", "Some Description", "05/05/2016",
                "12:30", "05/05/2016", "16:30");

        assertThat(result.getErr(), equalTo(""));
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getOut(), equalTo(""));
    }

    @Test
    public void testValidOptionsWithPrint() {
        MainMethodResult result = invokeMain("-print", "Mike Hensley", "Get New Tires", "05/05/2016",
                "12:30", "05/05/2016", "16:30");

        assertThat(result.getErr(), equalTo(""));
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getOut(), containsString("Get New Tires from 05/05/2016 12:30 until 05/05/2016 16:30"));
    }
}