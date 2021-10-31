package Panels.manager.newExam;

import Examination.Examination;
import Examination.Question.TrueFalse;
import People.Manager;
import SaveLoad.SaveExam;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Date;
import javax.swing.*;

public class SelectTrueFalse extends JPanel {

    private JLabel qnumber;

    private JTextArea question;
    private JRadioButton tru;
    private JRadioButton fals;

    private Dimension d;

    /**
     * if manager wants to add a trueFalse question
     *
     * @param dialog NewExam
     * @param d Dimension
     * @param cont continue button
     * @param error label
     * @param qn question name text field
     * @param duration
     * @param saveEx SaveExam
     * @param exam Examination
     * @param m Manager
     * @param number
     */
    public void trueFalseSelected(NewExam dialog, Dimension d, JButton cont,
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
                dialog.getHeight() * 3 / 7);

        tru = new JRadioButton("صحیح");
        tru.setFont(new java.awt.Font("B Nazanin", 1, d.height / 40));
        option.add(tru);
        add(tru);
        tru.setBounds(dialog.getWidth() / 4, dialog.getHeight() * 4 / 7,
                dialog.getWidth() / 4, dialog.getHeight() / 8);

        fals = new JRadioButton("غلط");
        fals.setFont(new java.awt.Font("B Nazanin", 1, d.height / 40));
        option.add(fals);
        add(fals);
        fals.setBounds(dialog.getWidth() * 3 / 4, dialog.getHeight() * 4 / 7,
                dialog.getWidth() / 4, dialog.getHeight() / 8);

        for (ActionListener al : cont.getActionListeners()) {
            cont.removeActionListener(al);
        }
        cont.addActionListener((e) -> {
            contActionListener(error, qn, duration, dialog, number, saveEx, exam, m);
        });
        dialog.add(this);
        setBounds(0, 0, dialog.getWidth(), dialog.getHeight());
    }

    private void contActionListener(JLabel error, JTextField qn, JSpinner duration,
            JDialog dialog, int number,
            SaveExam saveEx, Examination exam, Manager m) {
        error.setVisible(false);
        switch (1) {
            case 1:
                if (!tru.isSelected() && !fals.isSelected()) {
                    error.setText("فیلد ها پر شوند.");
                    error.setVisible(true);
                    break;
                }
            case 2:
                if (question.getText().equals("")) {
                    error.setText("فیلد ها پر شوند.");
                    error.setVisible(true);
                    break;
                }
            default:
                TrueFalse tf = new TrueFalse(qn.getText(), question.getText());
                Time time = new Time(((Date) duration.getValue()).getTime());
                tf.setDuration(time);
                
                tf.setNumber(number);

                if (tru.isSelected()) {
                    tf.setAnswer(true);
                } else if (fals.isSelected()) {
                    tf.setAnswer(false);
                }
                switch (saveEx.addQ(exam, tf, m.getUserName())) {
                    case 2:
                        exam.addQuestion(tf);
                        JOptionPane.showMessageDialog(dialog, "سوال ذخیره شد.", null, JOptionPane.OK_OPTION);
                        dialog.remove(this);
                        dialog.setBounds(d.width / 3, d.height / 3, d.width / 3, d.height / 3);
                        if (dialog instanceof NewExam) {
                            ((NewExam) dialog).questions();
                        }
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
