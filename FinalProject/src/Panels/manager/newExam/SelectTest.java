package Panels.manager.newExam;

import Examination.Examination;
import Examination.Question.Test;
import People.Manager;
import SaveLoad.SaveExam;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Date;
import javax.swing.*;

public class SelectTest extends JPanel {

    private JLabel qnumber;

    private JTextArea sw1;
    private JTextArea sw2;
    private JTextArea sw3;
    private JTextArea sw4;
    private JTextArea question;

    private JRadioButton first;
    private JRadioButton second;
    private JRadioButton third;
    private JRadioButton fourth;

    Dimension d;

    /**
     * if manager chose test question type
     *
     * @param dialog NewExam
     * @param d Dimension
     * @param cont continue button
     * @param error error label
     * @param qn question name test field
     * @param duration
     * @param saveEx saveExam
     * @param exam Examination
     * @param m Manager
     * @param number
     */
    public void testSelected(NewExam dialog, Dimension d, JButton cont,
            JLabel error, JTextField qn, JSpinner duration,
            SaveExam saveEx, Examination exam, Manager m, int number) {
        this.d = d;
        setLayout(null);
        ButtonGroup option = new ButtonGroup();

        qnumber = new JLabel("متن سوال");
        qnumber.setFont(new java.awt.Font("B Nazanin", 1, d.height / 32));
        add(qnumber);
        qnumber.setBounds(0, 0, dialog.getWidth() / 6, dialog.getHeight() / 8);

        question = new JTextArea();
        add(question);
        question.setBounds(dialog.getWidth() / 6, 0, dialog.getWidth() * 5 / 6,
                dialog.getHeight() * 2 / 7);

        first = new JRadioButton("گزینه 1");
        first.setFont(new java.awt.Font("B Nazanin", 1, d.height / 40));
        option.add(first);
        add(first);
        first.setBounds(0, dialog.getHeight() * 2 / 7, dialog.getWidth() / 6,
                dialog.getHeight() / 8);

        sw1 = new JTextArea();//gozine 1 field
        add(sw1);
        sw1.setBounds(dialog.getWidth() / 6, dialog.getHeight() * 2 / 7,
                dialog.getWidth() * 5 / 6, dialog.getHeight() / 7);

        second = new JRadioButton("گزینه 2");
        second.setFont(new java.awt.Font("B Nazanin", 1, d.height / 40));
        option.add(second);
        add(second);
        second.setBounds(0, dialog.getHeight() * 3 / 7, dialog.getWidth() / 6,
                dialog.getHeight() / 8);

        sw2 = new JTextArea();//gozine 2 field
        add(sw2);
        sw2.setBounds(dialog.getWidth() / 6, dialog.getHeight() * 3 / 7,
                dialog.getWidth() * 5 / 6, dialog.getHeight() / 7);

        third = new JRadioButton("گزینه 3");
        third.setFont(new java.awt.Font("B Nazanin", 1, d.height / 40));
        option.add(third);
        add(third);
        third.setBounds(0, dialog.getHeight() * 4 / 7, dialog.getWidth() / 6,
                dialog.getHeight() / 8);

        sw3 = new JTextArea();//gozine 3 field
        add(sw3);
        sw3.setBounds(dialog.getWidth() / 6, dialog.getHeight() * 4 / 7,
                dialog.getWidth() * 5 / 6, dialog.getHeight() / 7);

        fourth = new JRadioButton("گزینه 4");
        fourth.setFont(new java.awt.Font("B Nazanin", 1, d.height / 40));
        option.add(fourth);
        add(fourth);
        fourth.setBounds(0, dialog.getHeight() * 5 / 7, dialog.getWidth() / 6,
                dialog.getHeight() / 8);

        sw4 = new JTextArea();//gozine 4 field
        add(sw4);
        sw4.setBounds(dialog.getWidth() / 6, dialog.getHeight() * 5 / 7,
                dialog.getWidth() * 5 / 6, dialog.getHeight() / 7);

        for (ActionListener al : cont.getActionListeners()) {
            cont.removeActionListener(al);
        }
        cont.addActionListener((e) -> {
            contActionListener(error, qn, duration, number, saveEx, exam, m, dialog);
        });
        dialog.add(this);
        setBounds(0, 0, dialog.getWidth(), dialog.getHeight());
    }

    private void contActionListener(JLabel error, JTextField qn,
            JSpinner duration, int number,
            SaveExam saveEx, Examination exam, Manager m, NewExam dialog) {
        switch (1) {
            case 1:
                if (sw1.getText().equals("") || sw2.getText().equals("")
                        || sw3.getText().equals("") || sw4.getText().equals("")
                        || qnumber.getText().equals("")) {
                    error.setText("فیلد ها را پر کنید.");
                    error.setVisible(true);
                    break;
                }
            case 2:
                if (!first.isSelected()
                        && !second.isSelected() && !third.isSelected() && !fourth.isSelected()) {
                    error.setText("فیلد ها را پر کنید.");
                    error.setVisible(true);
                    break;
                }
            default:
                error.setVisible(false);
                Test t = new Test(qn.getText(), question.getText());
                Time time = new Time(((Date) duration.getValue()).getTime());
                t.setDuration(time);

                t.setNumber(number);
                t.setSw1(sw1.getText());
                t.setSw2(sw2.getText());
                t.setSw3(sw3.getText());
                t.setSw4(sw4.getText());

                if (first.isSelected()) {
                    t.setT(sw1.getText());
                } else if (second.isSelected()) {
                    t.setT(sw2.getText());
                } else if (third.isSelected()) {
                    t.setT(sw3.getText());
                } else {
                    t.setT(sw4.getText());
                }
                switch (saveEx.addQ(exam, t, m.getUserName())) {
                    case 2:
                        error.setVisible(false);
                        exam.addQuestion(t);
                        JOptionPane.showMessageDialog(dialog, "سوال ذخیره شد.", null, JOptionPane.OK_OPTION);
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
