package Panels.student;

import Panels.Init;
import Panels.Groups.Groups;
import Panels.student.results.Results;
import People.*;
import java.awt.*;
import javax.swing.*;

public class StudentPanel {

    private JButton exams;
    private JButton groups;
    private JButton backToLogin;
    private JButton poll;
    private JButton results;

    private JPanel choices;

    private Student student;
    private Person person;

    private Groups gr;
    private ShowExams se;
    private Poll po;
    private Results res;

    public StudentPanel(Person person, JFrame f) {
        this.student = (Student) person;
        initialize(f);
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    private void initialize(JFrame f) {
        f.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        int w = f.getWidth(), h = f.getHeight();

        choices = new JPanel();
        choices.setSize(w / 3, h);
        choices.setLayout(null);

        ImageIcon image = new ImageIcon("pic\\logout.png");
        Image temp = image.getImage().getScaledInstance(choices.getWidth() / 4,
                choices.getWidth() / 4, Image.SCALE_DEFAULT);
        image.setImage(temp);

        backToLogin = new JButton(image);
        backToLogin.addActionListener((e) -> {
            backActionListener(f);
        });
        choices.add(backToLogin);
        backToLogin.setBounds(choices.getWidth() * 3 / 4, 0, choices.getWidth() / 4,
                choices.getWidth() / 4);

        exams = new JButton(" آزمون ها");
        exams.setFont(new java.awt.Font("B Nazanin", 1, w / 28));
        exams.addActionListener((e) -> {
            if (po != null) {
                po.getExams().setVisible(false);
                if (po.getExamSelected() != null) {
                    po.getExamSelected().setVisible(false);
                }
                poll.setEnabled(true);
                po = null;
            }

            if (gr != null) {
                gr.setVisible(false);
                if (gr.getChat() != null) {
                    gr.getChat().setVisible(false);
                }
                groups.setEnabled(true);
                gr = null;
            }
            if (res != null) {
                res.setVisible(false);
                if (res.getSp() != null) {
                    res.getSp().setVisible(false);
                }
                results.setEnabled(true);
            }
            
            exams.setEnabled(false);
            se = new ShowExams(f, student);
        });
        
        choices.add(exams);
        exams.setBounds(0, h / 7, choices.getWidth(), h / 7);

        groups = new JButton("گروه ها");
        groups.setFont(new java.awt.Font("B Nazanin", 1, w / 28));
        choices.add(groups);
        groups.setBounds(0, h * 2 / 7, choices.getWidth(), h / 7);
        groups.addActionListener((e) -> {
            if (po != null) {
                po.getExams().setVisible(false);
                if (po.getExamSelected() != null) {
                    po.getExamSelected().setVisible(false);
                }
                poll.setEnabled(true);
                po = null;
            }

            if (se != null) {
                se.setVisible(false);
                if (se.getExamSelected() != null) {
                    se.removeExamSelected(f);
                }
                exams.setEnabled(true);
                se = null;
            }
            if (res != null) {
                res.setVisible(false);
                if (res.getSp() != null) {
                    res.getSp().setVisible(false);
                }
                results.setEnabled(true);
            }

            JButton source = (JButton) e.getSource();
            source.setEnabled(false);
            gr = new Groups();
            gr.initialize(student.getUserName(), source, "s", f);
            gr.setBounds(w / 3, 0, w / 3, h - h / 10);
            f.add(gr);
        });

        poll = new JButton("نظر سنجی");
        poll.setFont(new java.awt.Font("B Nazanin", 1, w / 28));
        poll.addActionListener((e) -> {
            if (se != null) {
                se.setVisible(false);
                if (se.getExamSelected() != null) {
                    se.removeExamSelected(f);
                }
                exams.setEnabled(true);
                se = null;
            }

            if (gr != null) {
                gr.setVisible(false);
                if (gr.getChat() != null) {
                    gr.getChat().setVisible(false);
                }
                groups.setEnabled(true);
                gr = null;
            }
            if (res != null) {
                res.setVisible(false);
                if (res.getSp() != null) {
                    res.getSp().setVisible(false);
                }
                results.setEnabled(true);
            }

            poll.setEnabled(false);
            
            po = new Poll(student, f);

        });
        choices.add(poll);
        poll.setBounds(0, h * 3 / 7, choices.getWidth(), h / 7);
        
        results = new JButton("نتایج");
        results.setFont(new java.awt.Font("B Nazanin", 1, w / 28));
        choices.add(results);
        results.setBounds(0, h * 4 / 7, choices.getWidth(), h / 7);
        results.addActionListener((e) -> {
            if (po != null) {
                po.getExams().setVisible(false);
                if (po.getExamSelected() != null) {
                    po.getExamSelected().setVisible(false);
                }
                poll.setEnabled(true);
                po = null;
            }
            if (se != null) {
                se.setVisible(false);
                if (se.getExamSelected() != null) {
                    se.removeExamSelected(f);
                }
                exams.setEnabled(true);
                se = null;
            }
            if (gr != null) {
                gr.setVisible(false);
                if (gr.getChat() != null) {
                    gr.getChat().setVisible(false);
                }
                groups.setEnabled(true);
                gr = null;
            }

            JButton source = (JButton) e.getSource();
            source.setEnabled(false);
            res = new Results();
            res.initialize(student.getUserName(), source, f);
            res.setBounds(w / 3, 0, w / 3, h - h / 10);
            f.add(res);
        });

        f.add(choices);
        choices.setLocation(w * 2 / 3, 0);

    }

    private void backActionListener(JFrame f) {
        f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        int w = f.getWidth();

        JLabel label = new JLabel("آیا می خواهید از حساب کاربری خود خارج شوید؟");
        label.setFont(new java.awt.Font("B Nazanin", 1, w / 32));
        JButton[] options = new JButton[2];

        options[0] = new JButton("بله");
        options[0].setFont(new java.awt.Font("B Nazanin", 1, w / 34));
        options[0].addActionListener(l -> {
            //close JOptionPane
            Window window = SwingUtilities.getWindowAncestor(options[1]);
            if (window != null) {
                window.setVisible(false);
            }
            if (gr != null) {
                gr.setVisible(false);
                if (gr.getChat() != null) {
                    gr.getChat().setVisible(false);
                }
            }
            if (se != null) {
                se.setVisible(false);
                if (se.getExamSelected() != null) {
                    se.removeExamSelected(f);
                }
            }
            if (po != null) {
                po.getExams().setVisible(false);
                f.remove(po.getExams());
                if (po.getExamSelected() != null) {
                    po.getExamSelected().setVisible(false);
                    f.remove(po.getExamSelected());
                }
                poll.setEnabled(true);
                po = null;
            }
            if (res != null) {
                res.setVisible(false);
                if (res.getSp() != null) {
                    res.getSp().setVisible(false);
                }
            }
            choices.setVisible(false);
            Init.getBack();
        });

        options[1] = new JButton("خیر");
        options[1].setFont(new java.awt.Font("B Nazanin", 1, w / 34));
        options[1].addActionListener(l -> {
            //close JOptionPane
            Window window = SwingUtilities.getWindowAncestor(options[1]);
            if (window != null) {
                window.setVisible(false);
            }
        });
        JOptionPane.showOptionDialog(null, label,
                "خروج", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

}
