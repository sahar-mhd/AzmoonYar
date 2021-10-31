package Time;

import Panels.student.ExamWindow;
import java.io.File;
import static java.lang.Thread.sleep;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerDate extends Thread {

    private final JPanel panel = new JPanel();

    private final JLabel days = new JLabel();
    private final JLabel hour = new JLabel();
    private final JLabel min = new JLabel();
    private final JLabel sec = new JLabel();

    private final ExamWindow finish;

    private long duration;
    private long diffInSeconds;
    private long diffInMinutes;
    private long diffInHours;
    private long diffInDays;

    private final Date d;

    /**
     *
     * @param width of main frame
     * @param height of main frame
     * @param d Date
     * @param finished ExamWindow
     */
    public TimerDate(int width, int height, Date d, ExamWindow finished) {
        this.d = d;
        this.finish = finished;

        duration = d.getTime() - System.currentTimeMillis();
        diffInSeconds = duration / 1000 % 60;
        diffInMinutes = duration / (60 * 1000) % 60;
        diffInHours = duration / (60 * 60 * 1000);
        diffInDays = (int) ((d.getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24));

        finish.add(panel);
        panel.setLayout(null);
        panel.setBounds(0, height / 2, width / 4, height / 2);

        panel.add(sec);
        sec.setBounds(panel.getWidth() / 6, 0, panel.getWidth(), panel.getHeight() / 6);

        panel.add(min);
        min.setBounds(panel.getWidth() / 6, panel.getHeight() / 6, panel.getWidth(), panel.getHeight() / 6);

        panel.add(hour);
        hour.setBounds(panel.getWidth() / 6, panel.getHeight() / 3, panel.getWidth(), panel.getHeight() / 6);

        panel.add(days);
        days.setBounds(panel.getWidth() / 6, panel.getHeight() / 2, panel.getWidth(), panel.getHeight() / 6);
        if (diffInDays < 1) {
            days.setVisible(false);
        }
    }

    @Override
    public void run() {
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TimerDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        days.setText("روز " + diffInDays);
        hour.setText("ساعت " + diffInHours);
        min.setText("دقیقه " + diffInMinutes);
        sec.setText("ثانیه " + diffInSeconds);

        if (duration / 1000 == 0) {
            File file = new File ("Exams\\" + finish.exam.getName() + " "
                    + finish.exam.getOwner().getUserName() + "\\Answers");
            
            if (!file.exists()){
                file.mkdir();
            }
            
            File f = new File("Exams\\" + finish.exam.getName() + " "
                    + finish.exam.getOwner().getUserName() + "\\Answers\\"
                    + finish.st.getUserName());
            if (!f.exists()) {
                f.mkdir();
            }
            finish.nextAction();
            finish.dispose();
            stop();
//            for (Object o : object){
//                if (o != null){
//                    if (o instanceof JButton) {
//                        ((JButton) o).setEnabled(false);
//                        o.
//                    } else if (o instanceof JRadioButton) {
//                        ((JRadioButton) o).setEnabled(false);
//                    } else if (o instanceof JTextArea){
//                        ((JTextArea) o).setEditable(false);
//                    }
//                }
//            }
        }
        duration = duration - 1000;

        diffInSeconds = duration / 1000 % 60;
        diffInMinutes = duration / (60 * 1000) % 60;
        diffInHours = duration / (60 * 60 * 1000);
        diffInDays = (int) ((d.getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24));

        run();
    }

    /**
     * @return the panel
     */
    public JPanel getPanel() {
        return panel;
    }
}
