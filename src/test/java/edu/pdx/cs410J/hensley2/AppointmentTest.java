package edu.pdx.cs410J.hensley2;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Appointment} class.
 */
public class AppointmentTest {
    private static final String START_TIME = "Start Time";
    private static final String END_TIME = "End Time";
    private static final String DESCRIPTION = "Description";
    private Appointment appointment;

    @Before
    public void setUpTest(){
        appointment = new Appointment();
    }

    @Test
    public void appointmentFieldsShouldBeInitializedToNull () throws Exception{
        //Set up test

        //SUT

        //Verify
        assertThat(FieldUtils.readField(appointment, "beginTime", true), is(nullValue()));
        assertThat(FieldUtils.readField(appointment, "endTime", true), is(nullValue()));
        assertThat(FieldUtils.readField(appointment, "description", true), is(nullValue()));
    }

    @Test
    public void testAppointmentGetters() throws Exception{
        //Set up test
        FieldUtils.writeField(appointment, "beginTime", START_TIME, true);
        FieldUtils.writeField(appointment, "endTime", END_TIME, true);
        FieldUtils.writeField(appointment, "description", DESCRIPTION, true);

        //SUT

        //Verify
        assertThat(appointment.getBeginTimeString(), is(START_TIME));
        assertThat(appointment.getEndTimeString(), is(END_TIME));
        assertThat(appointment.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void testAppointmentSetters() throws Exception{
        //Set up test


        //SUT
        appointment.setBeginTime(START_TIME);
        appointment.setEndTime(END_TIME);
        appointment.setDescription(DESCRIPTION);

        //Verify
        assertThat(FieldUtils.readField(appointment, "beginTime", true), is(START_TIME));
        assertThat(FieldUtils.readField(appointment, "endTime", true), is(END_TIME));
        assertThat(FieldUtils.readField(appointment, "description", true), is(DESCRIPTION));
    }

}
