package edu.pdx.cs410J.hensley2;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;

/**
 * Created by hensleym on 6/29/16.
 *
 * With help from this stack overflow article:
 * http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
 */
public class CliParserTest {

    private String[] testArgs = {"arg1", "arg2", "arg3"};

    @Test
    public void shouldBuildCliParser() throws Exception {
        //Set up the test


        //SUT
        CliParser parser = CliParser.build(testArgs);

        //Verify
        assertThat(parser, is(notNullValue()));
        assertThat(FieldUtils.readField(parser, "parsedArgs", true), is(notNullValue()));
        assertThat(FieldUtils.readField(parser, "cliArgs", true), equalTo(Arrays.asList(testArgs)));
    }

    @Test
    public void shouldThrowExceptionWhenNullIsPassedToBuild(){
        //Set up the test

        //SUT
        try{
            CliParser.build(null);
            fail("Should not reach this point - exception should be thrown for null");
        }
        catch(IllegalArgumentException e){
            assertThat(e.getMessage(), containsString("Missing command line arguments"));
        }

    }


    @Test
    public void validateAllShouldCallParsingMethodsInOrder() throws Exception {
        //Set up the test
        CliParser parser = Mockito.mock(CliParser.class);
        when(parser.validateAll()).thenCallRealMethod();
        parser.validateAll();

        //SUT
        InOrder inOrder = Mockito.inOrder(parser);

        //Verify
        inOrder.verify(parser).validateNonNullArgs();
        inOrder.verify(parser).validateMaxNumberOfArgs();
        inOrder.verify(parser).validateOptions();
        inOrder.verify(parser).validateArguments();
    }


    @Test
    public void shouldThrowExceptionWithLessThanSixArguments() throws Exception {
        //Set up the test
        CliParser parser = CliParser.build(testArgs);

        //SUT
        try{
            parser.validateArguments();
            fail("Invalid Arguments Accepted");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), containsString("Invalid number of arguments!"));
            assertThat(e.getMessage(), containsString("Number Expected: " + 6));
            assertThat(e.getMessage(), containsString("Number Received: " + 3));
        }
    }

    @Test
    public void shouldThrowExceptionWithMoreThanSixArguments() throws Exception {
        //Set up the test
        String [] args = {"one", "two", "three", "four", "five", "six", "seven", "eight"};
        CliParser parser = CliParser.build(args);

        //SUT
        try{
            parser.validateArguments();
            fail("Invalid Arguments Accepted");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), containsString("Invalid number of arguments!"));
            assertThat(e.getMessage(), containsString("Number Expected: " + 6));
            assertThat(e.getMessage(), containsString("Number Received: " + 8));
        }
    }

    @Test
    public void shouldNotThrowIllegalArgumentExceptionWithSixArguments(){
        //Set up the test
        String [] args = {"one", "two", "three", "four", "five", "six"};
        CliParser parser = CliParser.build(args);

        //SUT
        parser.validateArguments();
    }

    @Test
    public void shouldThrowExceptionIfInvalidOptionIsPresent(){
        //Set up test
        String expectedMessage = "Invalid Option! Please see -README for valid options";
        String [] args1 = {"one", "two", "three", "four", "-illegalOption"};
        String [] args2 = { "-illegalOption", "one", "two", "three", "four"};
        String [] args3 = { "one", "-illegalOption", "two", "three", "four"};

        //SUT
        try{
            CliParser.build(args1).validateOptions();
            fail("Invalid Arguments Accepted");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is(expectedMessage));
        }

        try{
            CliParser.build(args2).validateOptions();
            fail("Invalid Arguments Accepted");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is(expectedMessage));

        }
        try{
            CliParser.build(args3).validateOptions();
            fail("Invalid Arguments Accepted");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is(expectedMessage));
        }
    }

    @Test
    public void shouldThrowExceptionWhenValidOptionsAreNotInFirstOrSecondPosition(){
        //Set up test
        String expectedMessage = "Option at Invalid Position. Option must come before args";
        String [] args1 = {"one", "two", "three", "four", "-print"};
        String [] args2 = {"one", "two", "-print", "three", "four"};
        String [] args3 = { "one", "two", "three", "four", "-README"};
        String [] args4 = { "one",  "two", "-README", "three", "four"};

        //SUT
        try{
            CliParser.build(args1).validateOptions();
            fail("Invalid Arguments Accepted");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is(expectedMessage));
        }

        try{
            CliParser.build(args2).validateOptions();
            fail("Invalid Arguments Accepted");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is(expectedMessage));

        }
        try{
            CliParser.build(args3).validateOptions();
            fail("Invalid Arguments Accepted");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is(expectedMessage));
        }
        try{
            CliParser.build(args4).validateOptions();
            fail("Invalid Arguments Accepted");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is(expectedMessage));
        }
    }

    @Test
    public void shouldNotThrowAnExceptionWithValidOptionsInCorrectPosition(){
        //Set up the test
        String [] args1 = {"-print", "-README","one", "two", "three", "four"};
        String [] args2 = {"-print", "one", "two", "three", "four"};
        String [] args3 = {"-README","one", "two", "three", "four"};

        //SUT
        CliParser.build(args1).validateOptions();
        CliParser.build(args2).validateOptions();
        CliParser.build(args3).validateOptions();

        //Verify
    }

    //If there are more than 8 total arguments (options plus appointment data), throw exception
    @Test
    public void shouldThrowExceptionWhenMaxNumberOfArgsExceeded(){
        //Set up the test
        String [] args1 = {"-print", "-README","one", "two", "three", "four", "five", "six", "seven"};
        try{
            //SUT
            CliParser.build(args1).validateMaxNumberOfArgs();
            fail("Too many arguments passed the validator");
        }
        catch(IllegalArgumentException e){

            //Verify
            assertThat(e.getMessage(), containsString("Too many command line arguments."));
            assertThat(e.getMessage(), containsString("Number Entered: " + 9));
            assertThat(e.getMessage(), containsString("Max Number Expected: " + 8));
        }
    }

    @Test
    public void shouldValidateArgsOfLengthSixSevenAndEight(){
        //Set up the test
        String [] args1 = {"-print", "-README","one", "two", "three", "four", "five", "six"};
        String [] args2 = {"-print", "one", "two", "three", "four", "five", "six"};
        String [] args3 = {"one", "two", "three", "four", "five", "six"};

        //SUT
        CliParser.build(args1).validateMaxNumberOfArgs();
        CliParser.build(args2).validateMaxNumberOfArgs();
        CliParser.build(args3).validateMaxNumberOfArgs();

        //Verify
    }

    @Test
    public void shouldThrowExceptionWhenEmptyArgumentsArPassedIn(){
        //Set up the test
        String [] args1 = {};
        try{
            //SUT
            CliParser.build(args1).validateNonNullArgs();
            fail("Parser accepted Empty Args");
        }
        catch (IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is("Missing command line arguments"));
        }
    }

    //Parse Args Tests
    @Test
    public void shouldThrowIllegalArgumentExceptionWithInvalidBeginDate(){
        //Set up the test
        String [] args1 = {"-print", "-README","Owner", "Description", "Invalid Date", "14:25", "06/06/2016", "1:25"};
        CliParser parser = CliParser.build(args1);

        try{
            //SUT
            parser.parseArgs();
            fail("Invalid begin date passed in - should not pass!");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is("Invalid Date Format - Date Format must be dd/mm/yyyy hh:mm"));
        }
    }


    @Test
    public void shouldThrowIllegalArgumentExceptionWithInvalidBeginTime(){
        //Set up the test
        String [] args1 = {"-print", "-README","Owner", "Description", "06/05/2016", "Invalid Time", "06/06/2016", "1:25"};
        CliParser parser = CliParser.build(args1);

        try{
            //SUT
            parser.parseArgs();
            fail("Invalid begin time passed in - should not pass!");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is("Invalid Date Format - Date Format must be dd/mm/yyyy hh:mm"));
        }
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWithInvalidEndDate(){
        //Set up the test
        String [] args1 = {"-print", "-README","Owner", "Description", "06/05/2016", "14:25", "Invalid Date", "1:25"};
        CliParser parser = CliParser.build(args1);

        try{
            //SUT
            parser.parseArgs();
            fail("Invalid end date passed in - should not pass!");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is("Invalid Date Format - Date Format must be dd/mm/yyyy hh:mm"));
        }
    }


    @Test
    public void shouldThrowIllegalArgumentExceptionWithInvalidEndTime(){
        //Set up the test
        String [] args1 = {"-print", "-README","Owner", "Description", "06/05/2016", "14:25", "06/06/2016", "Invalid Time"};
        CliParser parser = CliParser.build(args1);

        try{
            //SUT
            parser.parseArgs();
            fail("Invalid end time passed in - should not pass!");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is("Invalid Date Format - Date Format must be dd/mm/yyyy hh:mm"));
        }
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenStartDateIsGreaterThanEndDate(){
        //Set up the test
        String [] args1 = {"-print", "-README","Owner", "Description", "06/07/2016", "14:25", "06/06/2016", "16:00"};
        CliParser parser = CliParser.build(args1);

        try{
            //SUT
            parser.parseArgs();
            fail("Invalid start time passed in - should be less than or equal to end time");
        }
        catch(IllegalArgumentException e){
            assertThat(e.getMessage(), is("Appointment Start Time must be less than End Time"));
        }
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenStartTimeEqualsEndTime(){
        //Set up the test
        String [] args1 = {"-print", "-README","Owner", "Description", "06/07/2016", "14:25", "06/07/2016", "14:25"};
        CliParser parser = CliParser.build(args1);

        try{
            //SUT
            parser.parseArgs();
            fail("Invalid start time passed in - should be less than or equal to end time");
        }
        catch(IllegalArgumentException e){
            assertThat(e.getMessage(), is("Appointment Start Time must be less than End Time"));
        }
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenStartTimeIsGreaterThanEndTime(){
        //Set up the test
        String [] args1 = {"-print", "-README","Owner", "Description", "06/07/2016", "14:25", "06/07/2016", "14:00"};
        CliParser parser = CliParser.build(args1);

        try{
            //SUT
            parser.parseArgs();
            fail("Invalid start time passed in - should be less than or equal to end time");
        }
        catch(IllegalArgumentException e){
            assertThat(e.getMessage(), is("Appointment Start Time must be less than End Time"));
        }
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenDescriptionIsEmpty(){
        //Set up the test
        String [] args1 = {"-print", "-README","Owner", "", "06/05/2016", "14:25", "06/06/2016", "14:10"};
        CliParser parser = CliParser.build(args1);

        try{
            //SUT
            parser.parseArgs();
            fail("Invalid Description passed in!");
        }
        catch(IllegalArgumentException e){
            //Verify
            assertThat(e.getMessage(), is("Description cannot be empty!"));
        }
    }

    @Test
    public void shouldParseValidOptionsIntoMap() throws Exception{
        //Set up the test
        String [] args1 = {"-print", "Owner", "Description", "06/05/2016", "14:25", "06/06/2016", "14:10"};
        CliParser parser = CliParser.build(args1).parseOptions();

        //SUT
        Map<String, String> parsed = (Map<String, String>) FieldUtils.readField(parser, "parsedArgs", true);

        //Verify
        assertThat(parsed.get(CliParser.PRINT_FLAG), not(nullValue()));
        assertThat(parsed.get(CliParser.PRINT_FLAG), is("true"));
    }

    @Test
    public void shouldParseValidArgumentsIntoMap(){
        //Set up the test
        String [] args1 = {"-print", "-README","Owner", "Description", "06/05/2016", "14:25", "06/06/2016", "14:10"};
        String [] args2 = {"Owner2", "Description2", "06/07/2016", "14:35", "06/08/2016", "14:15"};

        //SUT
        Map<String, String> parsed = CliParser.build(args1).parseArgs();

        //Verify
        assertThat(parsed.get(CliParser.OWNER_KEY), is("Owner"));
        assertThat(parsed.get(CliParser.DESCRIPTION_KEY), is("Description"));
        assertThat(parsed.get(CliParser.BEGIN_TIME_KEY), is("06/05/2016 14:25"));
        assertThat(parsed.get(CliParser.END_TIME_KEY), is("06/06/2016 14:10"));

        //SUT
        parsed = CliParser.build(args2).parseArgs();

        //Verify
        assertThat(parsed.get(CliParser.OWNER_KEY), is("Owner2"));
        assertThat(parsed.get(CliParser.DESCRIPTION_KEY), is("Description2"));
        assertThat(parsed.get(CliParser.BEGIN_TIME_KEY), is("06/07/2016 14:35"));
        assertThat(parsed.get(CliParser.END_TIME_KEY), is("06/08/2016 14:15"));
    }

}