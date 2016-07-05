package edu.pdx.cs410J.hensley2;

import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hensleym on 6/28/16.
 */
public class DateValidatorTest {

    @Test
    public void shouldInvalidateNullValues(){
        assertThat(DateValidator.validateCliDateFormat(null, null), is(false));
        assertThat(DateValidator.validateCliDateFormat("Some String", null), is(false));
        assertThat(DateValidator.validateCliDateFormat(null, "Some String"), is(false));
    }

    @Test
    public void shouldNotValidateNonDateString(){
        assertThat(DateValidator.validateCliDateFormat("Some String", "Some String"), is(false));
    }

    @Test
    public void shouldNotValidateDateStringInWrongFormat(){
        assertThat(DateValidator.validateCliDateFormat("July 4th, 2004", "4:00"), is(false));
        assertThat(DateValidator.validateCliDateFormat("06/07/2004", "1:00 pm"), is(false));
        assertThat(DateValidator.validateCliDateFormat("12-4-2010", "3:00"), is(false));
        assertThat(DateValidator.validateCliDateFormat("4 4 2016", "15:00"), is(false));
        assertThat(DateValidator.validateCliDateFormat("06/07/2004", "1:00 am"), is(false));
    }

    @Test
    public void shouldNotValidateDateStringWithInvalidTime(){
        assertThat(DateValidator.validateCliDateFormat("6/34/2016", "1:00"), is(false));
        assertThat(DateValidator.validateCliDateFormat("13/20/2016", "1:00"), is(false));
        assertThat(DateValidator.validateCliDateFormat("0/0/2016", "1:00"), is(false));

        assertThat(DateValidator.validateCliDateFormat("8/4/2000", "25:00"), is(false));
        assertThat(DateValidator.validateCliDateFormat("8/4/2000", "00:65"), is(false));
        assertThat(DateValidator.validateCliDateFormat("8/4/2000", "13:70"), is(false));
        assertThat(DateValidator.validateCliDateFormat("8/4/2000", "24:00"), is(false));
    }


    @Test
    public void shouldValidateDateStringInCorrectFormat(){
        //Test for 0 in the day
        assertThat(DateValidator.validateCliDateFormat("6/04/2016", "1:00"), is(true));
        assertThat(DateValidator.validateCliDateFormat("8/4/2000", "13:15"), is(true));

        //Test for 0 in the month
        assertThat(DateValidator.validateCliDateFormat("05/04/2016", "6:24"), is(true));
        assertThat(DateValidator.validateCliDateFormat("09/4/2000", "15:56"), is(true));

        //Test Minutes
        assertThat(DateValidator.validateCliDateFormat("05/04/2016", "00:00"), is(true));
        assertThat(DateValidator.validateCliDateFormat("09/4/2000", "1:15"), is(true));
        assertThat(DateValidator.validateCliDateFormat("09/4/2000", "01:45"), is(true));
        assertThat(DateValidator.validateCliDateFormat("09/4/2000", "23:59"), is(true));
    }

    @Test
    public void shouldNotValidateDateStringWithAmOrPm(){
        //Verify this date string is valid without the am or pm
        assertThat(DateValidator.validateCliDateFormat("04/04/2016", "1:00"), is(true));

        assertThat(DateValidator.validateCliDateFormat("04/04/2016", "1:00 pm"), is(false));
        assertThat(DateValidator.validateCliDateFormat("04/04/2016", "1:00 PM"), is(false));

        assertThat(DateValidator.validateCliDateFormat("04/04/2016", "1:00 AM"), is(false));
        assertThat(DateValidator.validateCliDateFormat("04/04/2016", "1:00 am"), is(false));

    }

    @Test
    public void validateAppointmentTimeShouldValidateStartAndEndTimesAreValid(){

        assertThat(DateValidator.validateAppointmentTime("Invalid", "1:00", "04/04/2016", "1:00"),
                    is(false));
        assertThat(DateValidator.validateAppointmentTime("01/01/2016", "Invalid", "04/04/2016", "1:00"),
                is(false));
        assertThat(DateValidator.validateAppointmentTime("01/01/2016", "1:00", "Invalid", "1:00"),
                is(false));
        assertThat(DateValidator.validateAppointmentTime("01/01/2016", "1:00", "04/04/2016", "Invalid"),
                is(false));
        //Verify that it passes the validate Appointment time with a real date string
        assertThat(DateValidator.validateAppointmentTime("01/01/2016", "1:00", "04/04/2016", "4:00"),
                is(true));
    }

    @Test
    public void shouldNotValidateAppointmentTimeIfStartDateIsEqualEndDate(){
        assertThat(DateValidator.validateAppointmentTime("04/04/2016", "1:00", "04/04/2016", "1:00"),
                is(false));
    }

    @Test
    public void shouldNotValidateAppointmentTimeIfStartTimeIsGreaterThanEndTime(){
        assertThat(DateValidator.validateAppointmentTime("04/04/2016", "5:00", "04/04/2016", "1:00"),
                is(false));
        assertThat(DateValidator.validateAppointmentTime("04/04/2016", "12:00", "04/04/2016", "11:00"),
                is(false));
    }

    @Test
    public void shouldNotValidateAppointmentTimeIfStartDateIsGreaterThanEndDate(){
        assertThat(DateValidator.validateAppointmentTime("04/06/2016", "5:00", "04/04/2016", "6:00"),
                is(false));
        assertThat(DateValidator.validateAppointmentTime("04/12/2016", "10:00", "04/04/2016", "11:00"),
                is(false));
    }

    //Appointment time is valid if the start date is before the end date
    @Test
    public void shouldValidateValidAppointmentTimes(){
        assertThat(DateValidator.validateAppointmentTime("04/06/2016", "5:00", "04/08/2016", "6:00"),
                is(true));
        assertThat(DateValidator.validateAppointmentTime("04/06/2015", "5:00", "04/08/2016", "6:00"),
                is(true));
        assertThat(DateValidator.validateAppointmentTime("05/24/2016", "21:00", "06/08/2016", "21:25"),
                is(true));
        assertThat(DateValidator.validateAppointmentTime("04/06/2016", "12:31", "12/08/2016", "16:00"),
                is(true));
        assertThat(DateValidator.validateAppointmentTime("07/06/2016", "5:00", "11/08/2016", "18:15"),
                is(true));
    }

}
