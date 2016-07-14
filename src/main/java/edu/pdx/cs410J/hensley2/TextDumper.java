package edu.pdx.cs410J.hensley2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

/**
 * Class to write an appointment book to file. http://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
 * for example of FileWriter/Buffered Writer.
 */
public class TextDumper implements AppointmentBookDumper {

    private final String filePath;

    TextDumper(String filePath) {
        this.filePath = filePath;
    }

    @Override
    /**
     * Writes appointment book to a file in this format:
     * owner:name
     * description, start date, end date
     * file path.
     * @throws IOException
     */
    public void dump(final AbstractAppointmentBook book) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        StringBuilder sb = new StringBuilder();

        sb.append("owner:");
        sb.append(book.getOwnerName());
        sb.append("\n");

        Collection<Appointment> appointments = book.getAppointments();
        for (AbstractAppointment appt : appointments) {
            sb.append(appt.getDescription());
            sb.append(",");

            sb.append(appt.getBeginTimeString());
            sb.append(",");

            sb.append(appt.getEndTimeString());
            sb.append("\n");
        }

        sb.deleteCharAt(sb.length() - 1);
        bw.write(sb.toString());
        bw.close();
    }
}
