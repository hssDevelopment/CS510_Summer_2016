package edu.pdx.cs410J.hensley2;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import edu.pdx.cs410J.AbstractAppointment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by hensleym on 7/1/16.
 */
public class AppointmentBookTest {

    private static final String OWNER_NAME = "Owner Name";
    private AppointmentBook appointmentBook;
    private ArrayList<AbstractAppointment> appointments;

    @Before
    public void setUpTest() {
        appointmentBook = new AppointmentBook();
        appointments = new ArrayList<>();
    }

    @Test
    public void shouldInitializeListToHoldAbstractAppointments() throws Exception {
        //Set up test

        //SUT

        //Verify
        assertThat(FieldUtils.readField(appointmentBook, "appointments", true), is(notNullValue()));
    }

    @Test
    public void testGetters() throws Exception {
        //Set up the test
        FieldUtils.writeField(appointmentBook, "appointments", appointments, true);
        FieldUtils.writeField(appointmentBook, "ownerName", OWNER_NAME, true);

        //SUT

        //Verify
        assertThat(appointmentBook.getAppointments(), is(appointments));
        assertThat(appointmentBook.getOwnerName(), is(OWNER_NAME));
    }

    @Test
    public void testSetters() throws Exception {
        //Set up test

        //SUT
        appointmentBook.setOwnerName(OWNER_NAME);

        //verify
        assertThat(FieldUtils.readField(appointmentBook, "ownerName", true), is(OWNER_NAME));
    }

    @Test
    public void testAddAppointment() throws Exception {
        //Set up test
        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();
        Appointment appointment3 = new Appointment();
        FieldUtils.writeField(appointmentBook, "appointments", appointments, true);


        //SUT
        appointmentBook.addAppointment(appointment1);
        appointmentBook.addAppointment(appointment2);
        appointmentBook.addAppointment(appointment3);

        //verify
        assertThat(appointments.size(), is(3));
        assertThat(appointment1, is(appointments.get(0)));
        assertThat(appointment2, is(appointments.get(1)));
        assertThat(appointment3, is(appointments.get(2)));

    }

}