package edu.pdx.cs410J.hensley2;

import edu.pdx.cs410J.AbstractAppointment;

/**
 * {@link Appointment} class.
 */
public class Appointment extends AbstractAppointment {

    /**
     * Beginning time for the appointment
     */
    private String beginTime;

    /**
     * End time for the appointment
     */
    private String endTime;

    /**
     * The appointment description
     */
    private String description;

    /**
     * @return formatted Begin Time String for appointment
     */
    @Override
    public String getBeginTimeString() {
        return this.beginTime;
    }

    /**
     * @param beginTime The begin time to set
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }


    /**
     * @return formatted End Time String for appointment
     */
    @Override
    public String getEndTimeString() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    /**
     * Returns the description for the appointment
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description Sets the appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
