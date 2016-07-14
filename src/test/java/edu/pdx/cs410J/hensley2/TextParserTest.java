package edu.pdx.cs410J.hensley2;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.ParserException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by hensleym on 7/10/16.
 */
public class TextParserTest {

    TextParser parser;

    @Before
    public void setUpTest() {
        parser = new TextParser();
    }


    @Test
    public void testSetFilePath() throws Exception {
        //Set up the test

        //SUT
        parser.setFilePath("testfile.txt");

        //Verify
        assertThat(FieldUtils.readField(parser, "filePath", true), equalTo("testfile.txt"));
    }

    @Test
    public void testParserWithNonExistentPath() throws Exception {
        //Set up test
        parser.setFilePath("InvalidPath");

        //SUT
        AbstractAppointmentBook appt = parser.parse();

        //Verify
        assertThat(appt.getAppointments().size(), equalTo(0));
    }

    @Test(expected = ParserException.class)
    public void testParserWithNullFilePath() throws Exception {
        //Set up test
        parser.setFilePath(null);

        //SUT
        AbstractAppointmentBook appt = parser.parse();
        fail("Should not parse with null file path");
    }

    @Test
    public void testParserWithValidFilePath() throws Exception {
        //Set up test
        parser.setFilePath("Project_2_Test_File.txt");

        //SUT
        AbstractAppointmentBook appt = parser.parse();

        //verify
        assertThat(appt.getAppointments().size(), equalTo(3));
        assertThat(appt.getOwnerName(), equalTo("Michael Hensley"));
    }

}