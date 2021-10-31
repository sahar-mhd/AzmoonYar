package Panels.student;

import Examination.Examination;
import People.Student;
import SaveLoad.SaveAnswers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;

public class ShowExams extends JScrollPane {

    private final ArrayList<JButton> button = new ArrayList<>();

    private final SaveAnswers saveAnswers = new SaveAnswers();

    private final JPanel exams = new JPanel();
    private JPanel examSelected;

    public ShowExams(JFrame f, Student st) {
        init(f, st);
    }

    private void init(JFrame f, Student st) {
        exams.setLayout(new BoxLayout(exams, BoxLayout.Y_AXIS));

        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                JButton b = (JButton) actionEvent.getSource();
                if (examSelected != null){
                    examSelected.setVisible(false);
                    examSelected = null;
                }
                examSelected = new JPanel();
                
                getExamSelected().setLayout(new BoxLayout(getExamSelected(), BoxLayout.Y_AXIS));
                getExamSelected().setSize(f.getWidth() / 3, f.getHeight());

                SimpleDateFormat formatt = new SimpleDateFormat("EEE MMM dd HH:mm yyyy");

                for (Examination e : st.getExaminations()) {
                    if (e.getName().equals(b.getName())) {
                        button.forEach((but) -> {
                            but.setEnabled(true);
                        });
                        b.setEnabled(false);

                        JLabel info = new JLabel("<html>نام آزمون: " + e.getName()
                                + "<br/>تاریخ شروع: " + formatt.format(e.getStart())
                                + "<br/>تاریخ پایان: " + formatt.format(e.getStop())
                                + "<br/>تعداد سوالات: " + e.getQuestion().size()
                                + "</html>", SwingConstants.CENTER);
                        getExamSelected().add(info);

                        JButton jb = new JButton("شرکت در آزمون");
                        jb.addActionListener((l) -> {
                            jbAction(jb, e, st);
                        });
                        getExamSelected().add(jb);

                        JButton moroor = new JButton("مرور");
                        moroor.addActionListener((l) -> {
                            if (!e.isMoroor()) {
                                JOptionPane.showMessageDialog(null, "مرور مجاز نیست.",
                                        null, JOptionPane.OK_OPTION);
                            } else if (!saveAnswers.searchAnswer(e.getName(),
                                    e.getOwner().getUserName(), st.getUserName())) {
                                JOptionPane.showMessageDialog(null,
                                        "در آزمون شرکت نکرده اید.", null, JOptionPane.OK_OPTION);
                            } else {
                                new Review(e, st.getUserName());
                            }
                        });
                        getExamSelected().add(moroor);
                        break;
                    }
                }

                getExamSelected().setVisible(true);
                f.add(getExamSelected());
                getExamSelected().setLocation(0, 0);
                f.revalidate();
            }
        };

        int n = 0;
        for (Examination e : st.getExaminations()) {
            button.add(new JButton(e.getName()));
            button.get(n).setName(e.getName());
            button.get(n).addActionListener(actionListener);
            exams.add(button.get(n));
            n++;
        }

        this.setViewportView(exams);
        f.add(this);
        setBounds(f.getWidth() / 3, 0, f.getWidth() / 3,
                f.getHeight());
    }

    private void jbAction(JButton jb, Examination e, Student st) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        Date d = new Date(formatter.format(new Date(System.currentTimeMillis())));
        if (e.getStart().after(d) && e.getStop().after(d)) {
            JOptionPane.showMessageDialog(null,
                    "آزمون شروع نشده.", null,
                    JOptionPane.OK_OPTION);
        } else if (e.getStop().before(d)) {
            JOptionPane.showMessageDialog(null,
                    "آزمون به پایان رسیده.", null,
                    JOptionPane.OK_OPTION);
        } else if (e.getQuestion().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "سوالی موجود نیست.", null,
                    JOptionPane.OK_OPTION);
        } else if (saveAnswers.searchAnswer(e.getName(),
                e.getOwner().getUserName(), st.getUserName())) {
            JOptionPane.showMessageDialog(null,
                    "قبلا در این آزمون شرکت کرده اید.",
                    null, JOptionPane.OK_OPTION);
            jb.setEnabled(false);
        } else {
            jb.setEnabled(false);
            new ExamWindow(e, st);
        }
    }

    public void removeExamSelected(JFrame f) {
        getExamSelected().setVisible(false);
        f.remove(getExamSelected());
    }

    /**
     * @return the examSelected
     */
    public JPanel getExamSelected() {
        return examSelected;
    }
}
