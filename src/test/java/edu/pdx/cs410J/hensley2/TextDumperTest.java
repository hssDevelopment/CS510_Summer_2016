package edu.pdx.cs410J.hensley2;

import org.junit.Test;

/**
 * Created by hensleym on 7/13/16.
 */
public class TextDumperTest {

    @Test
    public void shouldWriteValidAppointmentBookToFile() throws Exception {
        AppointmentBook appt = new AppointmentBook();
        appt.setOwnerName("Michael Hensley");

        Appointment Appointment1 = new Appointment();
        Appointment Appointment2 = new Appointment();
        Appointment Appointment3 = new Appointment();

        Appointment1.setDescription("Description");
        Appointment1.setBeginTime("01/01/2016 01:15");
        Appointment1.setEndTime("02/01/2016 2:15");

        appt.addAppointment(Appointment1);

        TextDumper dumper = new TextDumper("test-output.txt");
        dumper.dump(appt);
    }
}