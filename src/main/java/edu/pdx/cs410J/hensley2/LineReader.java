package edu.pdx.cs410J.hensley2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.ParserException;

/**
 * Reads a line into an appointment or parses the owner's name. Used by
 * Text Parser to read in lines from a file.
 */
public class LineReader {

    private static Integer DESCRIPTION_POSITION = 0;
    private static Integer BEGIN_TIME_POSITION = 1;
    private static Integer END_TIME_POSITION = 2;
    private static String DELIMITER = ",";

    private static String OWNER_DELIMITER = ":";
    public static String OWNER_MARKER = "owner";
    private static Integer OWNER_VALUE_POSITION = 1;

    /**
     *
     * @param line the line to read into an appointment
     * @return new Appointment when a line in the format
     * description,beginTime,endTime. Will throw a parser exception
     * if the line is invalid.
     *
     * @throws ParserException
     *
     */
    public AbstractAppointment readAppointment(String line) throws ParserException{
        checkIfEmpty(line);

        Appointment appointment = new Appointment();
        String[] splitLine = line.split(DELIMITER);

        if(splitLine.length != 3)
            throw new ParserException("Invalid Line Format: Expected description,begin time," +
                    "end time.\n Invalid Line: " + line);
        appointment.setDescription(splitLine[DESCRIPTION_POSITION]);
        appointment.setBeginTime(splitLine[BEGIN_TIME_POSITION]);
        appointment.setEndTime(splitLine[END_TIME_POSITION]);

        return appointment;
    }

    /**
     *
     * @param line
     * @return the name of the owner
     *
     * Will attempt to read the name of the owner of a string
     * owner:name_of_owner - if it is not in that format, this method
     * will throw a parser exception.
     * @throws ParserException
     */
    public String readOwner(String line) throws ParserException{
        checkIfEmpty(line);
        String[] splitOwnerLine = line.split(OWNER_DELIMITER);
        if(splitOwnerLine.length != 2 || !splitOwnerLine[0].equals(OWNER_MARKER)) {
            throw new ParserException("Invalid Owner Line Format: First line should be owner:<owner name>");
        }

        return splitOwnerLine[OWNER_VALUE_POSITION];

    }

    private void checkIfEmpty(final String line) throws ParserException {
        if(line == null || line.isEmpty()){
            throw new ParserException("File is malformed - empty line!");
        }
    }
}
