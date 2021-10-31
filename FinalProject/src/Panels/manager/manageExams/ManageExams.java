package Panels.manager.manageExams;

import Examination.Examination;
import Examination.Question.Question;
import People.Manager;
import SaveLoad.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ManageExams extends JDialog {

    private Manager manager;
    private SaveExam ex = new SaveExam();
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    private JScrollPane sp = new JScrollPane();

    private JButton newStudent;
    private JButton removeStudent;

    private JRadioButton tartibi;
    private JRadioButton tasadofi;

    private JCheckBox moroor;
    private JCheckBox allAtOnce;

    private JPanel panel = new JPanel();
    private JButton exit = new JButton("بازگشت");

    public ManageExams(Manager manager) {
        this.manager = manager;
        init();
    }

    private void init() {

        setTitle("مدیریت آزمون");
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setBounds(d.width / 3, d.height / 3, d.width / 3, d.height / 3);
        setResizable(false);

        ArrayList<Examination> exams = ex.loadExam(manager);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        ActionListener actionListener;
        actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                ButtonGroup group = new ButtonGroup();

                tartibi = new JRadioButton("ترتیبی", true);
                group.add(tartibi);
                panel.add(tartibi);

                tasadofi = new JRadioButton("تصادفی");
                group.add(tasadofi);
                panel.add(tasadofi);

                moroor = new JCheckBox("قابلیت مرور سوالات");
                moroor.setSelected(true);
                panel.add(moroor);
                
                allAtOnce = new JCheckBox("همه سوالات همزمان در اختیار کاربر قرار گیرد.");
                allAtOnce.setSelected(false);
                panel.add(allAtOnce);

                JButton b = (JButton) actionEvent.getSource();

                newStudent = new JButton("افزودن دانشجو");
                newStudent.addActionListener((e) -> {
                    new AddStudent(d, ManageExams.this, exams.get(Integer.parseInt(b.getName())));
                });
                panel.add(newStudent);

                removeStudent = new JButton("حذف دانشجو");
                removeStudent.addActionListener((e) -> {
                    new RemoveStudent(exams.get(Integer.parseInt(b.getName())));
                });
                panel.add(removeStudent);

                for (ActionListener e : exit.getActionListeners()) {
                    exit.removeActionListener(e);
                }
                exit.addActionListener((e) -> {
                    switch (JOptionPane.showConfirmDialog(null,
                            "تغییرات ذخیره شوند؟", null, JOptionPane.YES_NO_OPTION)) {
                        case JOptionPane.YES_OPTION:
                            exams.get(Integer.parseInt(b.getName())).setMoroor(moroor.isSelected());
                            if (tartibi.isSelected()) {
                                exams.get(Integer.parseInt(b.getName())).setTartibi(true);
                            } else if (tasadofi.isSelected()) {
                                exams.get(Integer.parseInt(b.getName())).setTartibi(false);
                            }
                            exams.get(Integer.parseInt(b.getName())).setAllQuestionsTogether(allAtOnce.isSelected());
                            ex.changeSettings(manager, exams.get(Integer.parseInt(b.getName())));
                        case JOptionPane.NO_OPTION:
                            setVisible(false);
                    }
                });
                panel.add(exit);
                sp.setViewportView(panel);
                add(sp);
                panel.setBounds(0, 0, getWidth(), getHeight());
            }
        };

        panel.add(exit);
        int n = 0;
        for (Examination exam : exams) {
            JButton b = new JButton(exam.getName());
            b.setName(n + "");
            b.addActionListener(actionListener);
            ArrayList<Question> qu = ex.loadQ(exam);
            if (qu != null) {
                qu.forEach((q) -> {
                    exam.addQuestion(q);
                });
            }
            panel.add(b);
            n++;
        }

        exit.addActionListener((e) -> {
            getContentPane().removeAll();
            setVisible(false);
        });

        sp.setViewportView(panel);
        add(sp);
        setVisible(true);
    }

}
