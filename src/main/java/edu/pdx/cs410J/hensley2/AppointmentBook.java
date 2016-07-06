package edu.pdx.cs410J.hensley2;

import java.util.ArrayList;
import java.util.Collection;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

/**
 * {@link AppointmentBook} class
 */
public class AppointmentBook extends AbstractAppointmentBook {

    /**
     * Name of the owner of the appointment book
     */
    private String ownerName;

    /**
     * Collection of appointment owned by this instance of AppointmentBook
     */
    private final ArrayList<AbstractAppointment> appointments;

    AppointmentBook() {
        this.appointments = new ArrayList<>();
    }

    /**
     * @return The owner's name of the appointment
     */
    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    /**
     * @param ownerName The Name of the owner to set for this appointment book
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @return All the appointments in this book
     */
    @Override
    public Collection getAppointments() {
        return this.appointments;
    }

    /**
     * @param appointment to be added to the appointment book.
     */
    @Override
    public void addAppointment(AbstractAppointment appointment) {
        appointments.add(appointment);
    }
}
