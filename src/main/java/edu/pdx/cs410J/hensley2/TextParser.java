package edu.pdx.cs410J.hensley2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.text.html.parser.Parser;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

/**
 * Created by hensleym on 7/6/16.
 */
public class TextParser implements AppointmentBookParser {

    /**
     * The filePath where the AppointmentBook is written to.
     */
    private String filePath;

    private static LineReader lineReader = new LineReader();

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    @Override
    public AppointmentBook parse() throws ParserException {
       AppointmentBook appt = new AppointmentBook();
        if (filePath == null){
            throw new ParserException("The File Path is empty!");
        }

        try{
            Stream<String> stream = Files.lines(Paths.get(filePath));
            List<String> lines = stream.collect(Collectors.toList());
            for(String line : lines){
                if(line.contains(lineReader.OWNER_MARKER))
                    appt.setOwnerName(lineReader.readOwner(line));
                else{
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
