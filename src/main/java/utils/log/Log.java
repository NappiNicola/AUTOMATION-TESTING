package utils.log;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

    private static boolean enabled = true;
    private static Integer line = 1;
    private static String separator = "------------------------------------";
    private static FileWriter fw;
    static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    static {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        try {
            // Inizializza il FileWriter una sola volta in modalità append
            fw = new FileWriter("Log.txt", true);
            fw.append("<<--- Run: ").append(formattedDateTime).append(" --->>").append("\n\n");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error during log file init: " + e.getMessage(), e);
        }
    }

    public static void setEnabled(boolean enabled) {
        Log.enabled = enabled;
    }


    static public void info(String msg) {

        if(!enabled) {
            return;
        }

        log.log(Level.INFO, msg);

        try {
            if (fw != null) {
                fw.append(line.toString().concat(". ")).append(msg).append('\n');
                fw.flush();
                line++;
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error during writing in Log file: " + e.getMessage(), e);
        }
    }

    static public void loggerOFF(){
        log.setLevel(Level.OFF);
    }

    public static void close() {
        try {
            if (fw != null) {
                fw.append(separator).append('\n');
                fw.close();
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error in Log file closing process: " + e.getMessage(), e);
        }
    }

}
