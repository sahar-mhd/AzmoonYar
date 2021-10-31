package SaveLoad;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SavePoll {

    /**
     *
     * @param examFilename
     * @param studentUsername
     * @return true if this student finished exam and can start polling
     */
    public boolean examIsFinished(String examFilename, String studentUsername) {
        File file = new File("Exams\\" + examFilename + "\\Answers\\"
                + studentUsername);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * saves student's view
     *
     * @param examFilename
     * @param view student's opinion about this exam
     * @param studentUsername
     * @return false if this student saved view before
     */
    public boolean savePoll(String examFilename, String view, String studentUsername) {
        File file = new File("Exams\\" + examFilename + "\\poll.txt");
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(SavePoll.class.getName()).log(Level.SEVERE, null, ex);
        }
        File f = new File("Exams\\" + examFilename + "\\Answers\\"
                + studentUsername + "\\poll.txt");
        if (f.exists()) {
            return false;
        } else {
            try {
                f.createNewFile();
                FileWriter bw = new FileWriter(file, true);
                
                bw.write(view + "\r\n");
                
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(SavePoll.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }
}
