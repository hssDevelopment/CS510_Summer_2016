package edu.pdx.cs410J.hensley2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

/**
 * A Text Parser class that parses an Appointment Book from file.
 *
 */
public class TextParser implements AppointmentBookParser {

    /**
     * The filePath where the AppointmentBook is written to.
     */
    private String filePath;

    private static final LineReader lineReader = new LineReader();

    /**
     * Sets the file path
     * @param filePath the file path of the file
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Parses an appointment book from a given file path.
     * @return an appointment book from file
     * book
     * @throws ParserException
     */
    @SuppressWarnings("AccessStaticViaInstance")
    @Override
    public AppointmentBook parse() throws ParserException {
        AppointmentBook appt = new AppointmentBook();
        if (filePath == null) {
            throw new ParserException("The File Path is empty!");
        }

        try {
            Stream<String> stream = Files.lines(Paths.get(filePath));
            List<String> lines = stream.collect(Collectors.toList());
            for (String line : lines) {
                if (line.contains(LineReader.OWNER_MARKER))
                    appt.setOwnerName(lineReader.readOwner(line));
                else {
                    appt.addAppointment(lineReader.readAppointment(line));
                }
            }

        }
        //Assume that the file does not exist and return a new appointment
        catch (IOException e) {
            //Do nothing - if file doesn't exist, we will create it once
            //the program runs.
        }

        return appt;
    }
}
