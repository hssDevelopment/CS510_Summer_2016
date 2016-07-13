package edu.pdx.cs410J.hensley2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.ParserException;


/**
 * This class builds an appointment book factory from file. If no file marker exists,
 * then it will return a new appointment book.
 */
public class AppointmentBookFactory {

    private static AppointmentBookFactory factory;

    private TextParser parser;

    private AppointmentBookFactory(){}

    /**
     * Returns the appointment book factory instance for this project.
     * @return static instance of Appointment Book Factory
     */
    public static AppointmentBookFactory getInstance(){
        if(factory == null){
            factory = new AppointmentBookFactory();
            factory.parser = new TextParser();
        }
        return factory;
    }

    /**
     *
     * @param parsedArgs from the command line.
     * @return {@link AbstractAppointmentBook} instance. This method will check if there is
     * a file path. If null is passed in, a new AppointmentBook will be created. If an empty
     * String is passed in, it will also create a new AppointmentBook. Otherwise, it will
     * take the String, read the file, and build the appointment book.
     *
     */
    public AppointmentBook buildAppointmentBook(Map<String, String> parsedArgs)
            throws IllegalArgumentException{
        String filePath = parsedArgs.get(CliParser.FILE_KEY);
        if (filePath != null && !filePath.isEmpty()){
            try {
                TextParser parser = factory.parser;
                parser.setFilePath(filePath);
                AppointmentBook book = parser.parse();
                if(!book.getOwnerName().equals(parsedArgs.get(CliParser.OWNER_KEY)))
                    throw new IllegalArgumentException(("Parser Exception! Owner name mismatch.\n"
                    + "Owner name in file is different then owner name on command line"));
                return book;
            } catch (ParserException e) {
                throw new IllegalArgumentException("Parser Exception!" + "\n" + e.toString());
            }
        }

        else{
            return new AppointmentBook();
        }
    }
}
