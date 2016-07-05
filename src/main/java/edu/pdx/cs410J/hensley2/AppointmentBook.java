package edu.pdx.cs410J.hensley2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by hensleym on 6/26/16.
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

    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName){
        this.ownerName = ownerName;
    }

    @Override
    public Collection getAppointments() {
        return this.appointments;
    }

    @Override
    public void addAppointment(AbstractAppointment appt) {
        appointments.add(appt);
    }
}
