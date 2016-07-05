package edu.pdx.cs410J.hensley2;

import edu.pdx.cs410J.AbstractAppointment;

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
     *
     * @return formatted Begin Time String for appointment
     */
    @Override
    public String getBeginTimeString() {
        return this.beginTime;
    }

    /**
     *  Sets the begin time of the appointment
      * @param beginTime
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }


    /**
     *
     * @return formatted End Time String for appointment
     */
    @Override
    public String getEndTimeString() {
        return this.endTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }


    /**
     *
     * @return description for appointment
     */
    @Override
    public String getDescription(){
        return this.description;
    }

    /**
     * Returns the description of the appointment
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
