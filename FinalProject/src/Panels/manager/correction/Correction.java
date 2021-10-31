package Panels.manager.correction;

import Examination.Examination;
import Examination.Question.Descriptive;
import Examination.Question.Question;
import People.Manager;
import SaveLoad.SaveAnswers;
import SaveLoad.SaveStudent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.*;

public class Correction {

    private final Manager manager;

    private final SaveStudent saveStudent = new SaveStudent();
    private final SaveAnswers saveAnswers = new SaveAnswers();

    private final JFrame f;

    private final JPanel panel;
    private final JScrollPane main;

    private JScrollPane examSelected = new JScrollPane();
    private JPanel selected; //when an exam is selected and this panel wants to show student of this exam

    private ArrayList<JButton> button = new ArrayList<>();

    public Correction(Manager m, JFrame f) {
        this.f = f;
        panel = new JPanel();
        main = new JScrollPane();

        this.manager = m;
        init();
        main.setViewportView(panel);
    }

    public void init() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        ActionListener studetsAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                for (Examination ex : manager.getExaminations()) {
                    if ((b.getName()).indexOf(ex.getName() + " ") == 0) {
                        File filee = new File("Exams\\" + ex.getName() + " " + ex.getOwner().getUserName()
                                + "\\Answers\\" + b.getName().substring(b.getName().lastIndexOf(" ") + 1) + "\\grade.txt");

                        if (!saveAnswers.searchAnswer(ex.getName(),
                                manager.getUserName(),
                                b.getName().substring(b.getName().lastIndexOf(" ") + 1))) {

                            if (ex.getStop().getTime() <= System.currentTimeMillis()) {
                                File f = new File("Exams\\" + ex.getName() + " " + ex.getOwner().getUserName() + "\\Answers");
                                if (!f.exists()) {
                                    f.mkdir();
                                }
                                f = new File(f.getPath() + "\\" + b.getName().substring(b.getName().lastIndexOf(" ") + 1));
                                if (!f.exists()) {
                                    f.mkdir();
                                }
                                f = new File(f.getPath() + "\\grade.txt");
                                try {
                                    f.createNewFile();
                                    FileWriter fw = new FileWriter(f);
                                    File fi = new File("Exams\\" + ex.getName() + " " + ex.getOwner().getUserName());                              
                                    for (File fe : fi.listFiles()) {
                                        if (fe.getName().endsWith("Des.txt") ) {
                                            double dou=0;
                                            String str=fe.getName().substring(0, fe.getName().lastIndexOf(" "));
                                            str=str.substring(0, str.lastIndexOf(" "));
                                            fw.write(str+"\r\n"+dou+"\r\n");
                                        }
                                    }
                                    fw.close();
                                } catch (IOException ex1) {
                                    Logger.getLogger(Correction.class.getName()).log(Level.SEVERE, null, ex1);
                                }
                            }
                            JOptionPane.showMessageDialog(null, "دانشجو آزمون نداده است.",
                                    null, JOptionPane.OK_OPTION);
                        } else if (filee.exists()) {
                            JOptionPane.showMessageDialog(null, "تصحیح شده", null, JOptionPane.OK_OPTION);
                        } else if (saveAnswers.loadAnswers(b.getName()
                                .substring(b.getName().lastIndexOf(" ") + 1),
                                ex.getName(), manager.getUserName(), 10, 10).isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "جواب ها قابل تصحیح نیستند.", null,
                                    JOptionPane.OK_OPTION);
                        } else {
                            new CorrectionDialog(manager, b.getName()
                                    .substring(b.getName().lastIndexOf(" ") + 1), ex);
                        }
                        break;
                    }
                }
            }
        };

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                selected = new JPanel();
                ArrayList<JButton> studentButton = new ArrayList<>();

                int n = 0;
                ArrayList<String> str = saveStudent.StudentsOfExam("Exams\\" + b.getName()
                        + " " + manager.getUserName());
                if (str != null) {
                    for (String s : str) {
                        studentButton.add(new JButton(s));
                        studentButton.get(n).setName(b.getName() + " " + s);
                        studentButton.get(n).addActionListener(studetsAction);
                        selected.add(studentButton.get(n));
                        n++;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "دانشجویی موجود نیست.",
                            null, JOptionPane.OK_OPTION);
                }

                examSelected.setViewportView(selected);
                examSelected.setVisible(true);
                f.add(examSelected);
                examSelected.setBounds(0, 0, f.getWidth() / 3, f.getHeight());
            }
        };

        int n = 0;
        for (Examination e : manager.getExaminations()) {
            button.add(new JButton(e.getName()));
            button.get(n).setName(e.getName());
            button.get(n).addActionListener(actionListener);
            panel.add(button.get(n));
            n++;
        }

        main.setViewportView(panel);
        main.setVisible(true);
        f.add(main);
        main.setBounds(f.getWidth() / 3, 0, f.getWidth() / 3, f.getHeight());
    }

    /**
     * @return the main
     */
    public JScrollPane getMain() {
        return main;
    }

    /**
     * @return the examSelected
     */
    public JScrollPane getExamSelected() {
        return examSelected;
    }

}
