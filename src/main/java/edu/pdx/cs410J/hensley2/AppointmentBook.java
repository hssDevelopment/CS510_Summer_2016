package edu.pdx.cs410J.hensley2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Link{AppointmentBook} class
 */
public class AppointmentBook extends AbstractAppointmentBook {

    /**
     * Name of the owner of the appointment book
     */
    private String ownerName;

    /**
     * Collection of appointment owned by this instance of AppointmentBook
     */
    private ArrayList<AbstractAppointment> appointments;

    AppointmentBook(){
        this.appointments = new ArrayList<>();
    }

    /**
     *
     * @return The owner's name of the appointment
     */
    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    /**
     *
     * @param ownerName The Name of the owner to set for this appointment book
     */
    public void setOwnerName(String ownerName){
        this.ownerName = ownerName;
    }

    /**
     *
     * @return All the appointments in this book
     */
    @Override
    public Collection getAppointments() {
        return this.appointments;
    }

    /**
     *
     * @param The new appointment to add
     */
    @Override
    public void addAppointment(AbstractAppointment appt) {
        appointments.add(appt);
    }
}
