package Panels.manager.History;

import Examination.Examination;
import People.Manager;
import SaveLoad.Loader;
import excel.WriteExcel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class History extends JScrollPane {

    private Loader loader;
    private WriteExcel we;
    private BarChartResults bc;
    private ArrayList<String> exams, students, deleted;
    private ArrayList<Double> avarages, grades;
    private JButton results, resultsfbf;
    private JButton[] buttons;
    private JPanel panel;
    private JButton exit;
    private ActionListener listener;
    private JScrollPane sp;
    private JPanel p;

    public void initialize(Manager m, JButton source, JFrame f) {
        int w = f.getWidth();
        int h = f.getHeight();
        we = new WriteExcel();
        loader = new Loader();
        panel = new JPanel();
        results = new JButton("نتایج بصورت آزمون به آزمون");
        resultsfbf = new JButton("نتایج بصورت فرد به فرد");
        exit = new JButton("بازگشت");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(exit);
        panel.add(results);
        exams = new ArrayList<>();
        exams = loader.load(m.getUserName(), "m");
        buttons = new JButton[exams.size()];
        for (int i = 0; i < exams.size(); i++) {
            buttons[i] = new JButton(exams.get(i));
            panel.add(buttons[i]);
        }
        exit.addActionListener((e) -> {
            sp.setVisible(false);
            setVisible(false);
            source.setEnabled(true);
        });
        results.addActionListener((r) -> {
            //prepare avarages
            avarages = new ArrayList<>();
            avarages = loader.AvarageGrades(exams, m.getUserName());
            JLabel label = new JLabel("نتایج به چه صورتی؟");
            label.setFont(new java.awt.Font("B Nazanin", 1, w / 32));
            JButton[] options = new JButton[2];
            options[0] = new JButton("اکسل");
            options[0].setFont(new java.awt.Font("B Nazanin", 1, w / 34));
            options[0].addActionListener((e) -> {
                File file = new File("Resultsof " + m.getUserName() + "'s exams.xlsx");
                try {
                    if (file.exists()) {
                        file.setReadOnly();
                        Desktop.getDesktop().open(file);
                    } else {
                        we.result(exams, avarages, m.getUserName(), "AzmoonBeAzmoon");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            options[1] = new JButton("نمو دار");
            options[1].setFont(new java.awt.Font("B Nazanin", 1, w / 34));
            options[1].addActionListener((e) -> {
                //close JOptionPane
                bc = new BarChartResults(avarages, exams);
                bc.setBounds(0, 0, w, h);
                bc.setModal(true);
                bc.setVisible(true);
            });
            JOptionPane.showOptionDialog(f, label,
                    "نتایج آزمون به آزمون", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        });
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sp != null) {
                    sp.setVisible(false);
                }
                JButton source = (JButton) e.getSource();
                sp = new JScrollPane();
                p = new JPanel();
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

                resultsfbf.addActionListener((r) -> {
                    resultsfbfActionListener(f, source, m.getUserName());
                });
                p.add(resultsfbf);
                SimpleDateFormat formatt = new SimpleDateFormat("EEE MMM dd HH:mm yyyy");
                for (Examination examination : m.getExaminations()) {
                    if (examination.getName().equals(source.getText())) {
                        JLabel info = new JLabel("<html>تاریخ شروع: " + formatt.format(examination.getStart())
                                + "<br/>تاریخ پایان: " + formatt.format(examination.getStop())
                                + "</html>", SwingConstants.CENTER);
                        p.add(info);
                    }
                }
                students = new ArrayList<>();
                students = loader.studentsInExam(source.getText()+" "+ m.getUserName());
                JLabel attendees = new JLabel("<html>شرکت کنندگان: ", SwingConstants.CENTER);
                for (int i = 0; i < students.size(); i++) {
                    attendees.setText(attendees.getText() + students.get(i) + "<br/>");
                }
                attendees.setText(attendees.getText() + "</html>");
                p.add(attendees);
                deleted = new ArrayList<>();
                deleted = loader.deletedstudents(source.getText(), m.getUserName());
                JLabel blacklist = new JLabel("<html>دانشجویان محروم: ", SwingConstants.CENTER);
                for (int i = 0; i < deleted.size(); i++) {
                    blacklist.setText(blacklist.getText() + deleted.get(i) + "<br/>");
                }
                blacklist.setText(blacklist.getText() + "</html>");
                p.add(blacklist);
                JLabel avarage = new JLabel("میانگین نمرات: ", SwingConstants.CENTER);
                p.add(avarage);
                avarages = new ArrayList<>();
                avarages = loader.AvarageGrades(exams, m.getUserName());
                for (int i = 0; i < exams.size(); i++) {
                    if (exams.get(i).equals(source.getText())) {
                        avarage.setText(avarage.getText() + avarages.get(i));
                    }
                }
                JButton review = new JButton("نتایج نظرسنجی");
                review.addActionListener((r) -> {
                    int[] amount = loader.pollResults(source.getText(), m.getUserName());
                    bc = new BarChartResults(amount, source.getText());
                    bc.setBounds(0, 0, w, h);
                    bc.setModal(true);
                    bc.setVisible(true);
                });
                p.add(review);

                getSp().setViewportView(p);
                getSp().setBounds(0, 0, w / 3, h);
                f.add(getSp());
            }
        };
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(listener);
        }

        setViewportView(panel);
    }

    private void resultsfbfActionListener(JFrame f, JButton source, String username) {
        int w = f.getWidth(), h = f.getHeight();
        students = new ArrayList<>();
        students = loader.studentsInExam(source.getText()+" "+ username);
        grades = new ArrayList<>();
        grades = loader.WholeGrades(source.getText()+" "+ username,"return");
        JLabel label = new JLabel("نتایج به چه صورتی؟");
        label.setFont(new java.awt.Font("B Nazanin", 1, w / 32));
        JButton[] options = new JButton[2];
        options[0] = new JButton("اکسل");
        options[0].setFont(new java.awt.Font("B Nazanin", 1, w / 34));
        options[0].addActionListener((a) -> {
            try {
                File file = new File("Resultsof " + source.getText() + " .xlsx");
                if (file.exists()) {
                    file.setReadOnly();
                    Desktop.getDesktop().open(file);
                } else {
                    we.result(students, grades, source.getText(), "FardBeFard");
                }
            } catch (IOException ex) {
                Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        options[1] = new JButton("نمو دار");
        options[1].setFont(new java.awt.Font("B Nazanin", 1, w / 34));
        options[1].addActionListener((a) -> {
            bc = new BarChartResults(grades, students, source.getText());
            bc.setBounds(0, 0, w, h);
            bc.setModal(true);
            bc.setVisible(true);
        });
        JOptionPane.showOptionDialog(f, label,
                "نتایج فرد به فرد", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    /**
     * @return the sp
     */
    public JScrollPane getSp() {
        return sp;
    }
}
