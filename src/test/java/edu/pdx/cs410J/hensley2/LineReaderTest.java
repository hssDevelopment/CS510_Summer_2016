package edu.pdx.cs410J.hensley2;

import org.junit.Before;
import org.junit.Test;

import static com.sun.tools.doclint.Entity.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.ParserException;

/**
 * Created by hensleym on 7/6/16.
 */
public class LineReaderTest {

    private LineReader lineReader;

    @Before
    public void setUpTest() {
        lineReader = new LineReader();
    }

    @Test(expected=ParserException.class)
    public void shouldThrowParserExceptionWhenLineIsNull() throws Exception{
        lineReader.readAppointment(null);
    }

    @Test(expected=ParserException.class)
    public void shouldThrowParserExceptionWhenLineIsEmpty() throws Exception{
        lineReader.readAppointment("");
    }

    @Test(expected=ParserException.class)
    public void shouldThrowParserExceptionWhenLineHasTooManyCommas() throws Exception{
        lineReader.readAppointment("Description,1,1,1");
    }

    @Test
    public void shouldReturnNewAppointmentWhenValidLinePassedIn() throws Exception {
        AbstractAppointment appointment = lineReader
                .readAppointment("Some Description,01/01/2016 12:00,01/01/2016 14:00");
        assertThat(appointment, is(notNullValue()));

        assertThat(appointment.getDescription(), is("Some Description"));
        assertThat(appointment.getBeginTimeString(), is("01/01/2016 12:00"));
        assertThat(appointment.getEndTimeString(), is("01/01/2016 14:00"));

    }

}