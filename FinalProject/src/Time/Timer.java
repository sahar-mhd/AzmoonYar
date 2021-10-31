package Time;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Timer extends Thread {

    private final Time time;
    private final JLabel label;
    private final Object[] o;

    public Timer(Time time, Object[] o) {
        this.time = time;
        this.o = o;

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        label = new JLabel();
        label.setFont(new Font("", Font.PLAIN, d.width / 60));
        label.setText(time.toString());
    }

    @Override
    public void run() {
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
        }
        time.setTime(time.getTime() - 1000);
        label.setText(time.toString());
        if (time.toString().equals("00:00:00")) {
            for (Object ob : o) {
                if (ob != null) {
                    if (ob instanceof JButton) {
                        ((JButton) ob).setEnabled(false);
                    } else if (ob instanceof JRadioButton) {
                        ((JRadioButton) ob).setEnabled(false);
                    } else if (ob instanceof JFileChooser){
                        ((JFileChooser) ob).cancelSelection();
                    } else if (ob instanceof JTextArea){
                        ((JTextArea) ob).setEditable(false);
                    }
                }
            }
            stop();
        }
        run();
    }

    /**
     * @return the label
     */
    public JLabel getLabel() {
        return label;
    }

}
