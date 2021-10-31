package Panels.manager.newExam;

import Examination.Examination;
import Examination.Question.Descriptive;
import People.Manager;
import SaveLoad.SaveExam;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Date;
import javax.swing.*;

public class SelectDescriptive extends JPanel {

    private JLabel qnumber;
    private JLabel qname;

    private JTextArea question;
    private JTextArea sw1;

    private Dimension d;

    /**
     * if manager wants to add a descriptive question
     * @param dialog NewExam
     * @param cont continue button
     * @param d Dimension
     * @param error Button
     * @param qn question name text field
     * @param startd start time of question
     * @param stopd stop time of question
     * @param saveEx SaveExam
     * @param exam Examination
     * @param m Manager
     * @param number
     */
    public void DescriptiveSeleted(NewExam dialog, JButton cont, Dimension d,
            JLabel error, JTextField qn, JSpinner startd, JSpinner stopd,
            SaveExam saveEx, Examination exam, Manager m, int number) {
        this.d = d;
        setLayout(null);

        qnumber = new JLabel("متن سوال");
        qnumber.setFont(new java.awt.Font("B Nazanin", 1, d.height / 32));
        add(qnumber);
        qnumber.setBounds(0, 0, dialog.getWidth() / 6, dialog.getHeight() / 8);

        question = new JTextArea();
        add(question);
        question.setBounds(dialog.getWidth() / 6, 0, dialog.getWidth() * 5 / 6, 
                dialog.getHeight() * 3 / 7);

        qname = new JLabel("پاسخ سوال");
        qname.setFont(new java.awt.Font("B Nazanin", 1, d.height / 32));
        add(qname);
        qname.setBounds(0, dialog.getHeight() * 3 / 7, dialog.getWidth() / 6, 
                dialog.getHeight() / 8);

        sw1 = new JTextArea();
        add(sw1);
        sw1.setBounds(dialog.getWidth() / 6, dialog.getHeight() * 3 / 7, 
                dialog.getWidth() * 5 / 6, dialog.getHeight() * 2 / 7);

        for (ActionListener al : cont.getActionListeners()) {
            cont.removeActionListener(al);
        }
        cont.addActionListener((e) -> {
            contActionListener(error, qn, startd, dialog, saveEx, exam, m, number);
        });
        
        dialog.add(this);
        setBounds(0, 0, dialog.getWidth(), dialog.getHeight());
    }

    private void contActionListener(JLabel error, JTextField qn, JSpinner duration,
            NewExam dialog, SaveExam saveEx, Examination exam, Manager m, int number) {
        if (question.getText().equals("") || sw1.getText().equals("")) {
            error.setText("فیلد ها پر شوند.");
            error.setVisible(true);
        } else {
            error.setVisible(false);
            Descriptive de = new Descriptive(qn.getText(), question.getText());
            Time time = new Time(((Date)duration.getValue()).getTime());
            de.setDuration(time);

            de.setNumber(number);
            de.setAnswer(sw1.getText());
            switch (saveEx.addQ(exam, de, m.getUserName())) {
                case 2:
                    exam.addQuestion(de);
                    JOptionPane.showMessageDialog(this, "سوال ذخیره شد.", null, JOptionPane.OK_OPTION);
                    dialog.remove(this);
                    dialog.setBounds(d.width / 3, d.height / 3, d.width / 3, d.height / 3);
                    dialog.questions();
                    break;
                case 3:
                    error.setText("سوال با این نام موجود است.");
                    error.setVisible(true);
                    break;
                case 1:
                    error.setText("این آزمون موجود نیست.");
                    error.setVisible(true);
                    break;
                default:
                    error.setText("خطا!");
                    error.setVisible(true);
            }
        }
    }
}
