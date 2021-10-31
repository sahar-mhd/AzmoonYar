package Panels.student;

import Examination.Examination;
import Examination.Question.Question;
import SaveLoad.SaveAnswers;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Review extends JDialog {

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private int n = 0;
    private final SaveAnswers saveAns = new SaveAnswers();
    private final ArrayList<Question> que;
    private final ArrayList<Object> answer;

    private final JLabel questionName = new JLabel();
    private JLabel answerPic;

    private final JTextArea question = new JTextArea();
    private final JTextArea answ = new JTextArea();

    private final JButton finish = new JButton("اتمام");
    private final JButton next = new JButton("بعدی");
    private final JButton previous = new JButton("قبلی");

    public Review(Examination ex, String studentUsername) {
        this.que = ex.getQuestion();

        setBounds(d.width / 4, d.height / 4, d.width / 2, d.height / 2);
        this.answer = saveAns.loadAnswers(ex.getName() + " "
                + ex.getOwner().getUserName(), studentUsername, getWidth(), getHeight());
        init(ex);
    }

    private void init(Examination exam) {
        setTitle("مرور");
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setResizable(false);
        setLayout(null);

        answ.setEditable(false);
        add(answ);
        answ.setBounds(getWidth() / 4, getHeight()  / 2, getWidth() * 3 / 4, getHeight() / 4);
        answ.setVisible(false);

        questionName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, exam.getName(),
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                new java.awt.Font(null, 1, d.height / 24)));
        add(questionName);
        questionName.setBounds(0, 0, getWidth(), getHeight() / 6);


        question.setText(que.get(n).getQuestion());
        question.setEditable(false);
        add(question);
        question.setBounds(0, getHeight() / 6, getWidth(), getHeight() / 4);

        add(finish);
        finish.addActionListener((e) -> {
            dispose();
        });
        finish.setBounds(getWidth() / 8, getHeight() * 5 / 6, getWidth() / 4, getHeight() / 12);

        add(previous);
        previous.addActionListener((e) -> {
            preAction();
        });
        previous.setBounds(getWidth() * 3 / 8, getHeight() * 5 / 6, getWidth() / 4, getHeight() / 12);

        add(next);
        next.addActionListener((e) -> {
            nextAction();
        });
        next.setBounds(getWidth() * 5 / 8, getHeight() * 5 / 6, getWidth() / 4, getHeight() / 12);

        showAnswer();
        
        setVisible(true);
    }

    private void preAction() {
        if (n > 0) {
            n--;
            showAnswer();
        } else {
            JOptionPane.showMessageDialog(null, "سوال قبلی موجود نیست.", null, JOptionPane.OK_OPTION);
        }
    }

    private void nextAction() {
        if (n < que.size() - 1) {
            n++;
            showAnswer();
        } else {
            JOptionPane.showMessageDialog(null, "سوال بعدی موجود نیست.", null, JOptionPane.OK_OPTION);
        }
    }

    private void showAnswer() {
        if (answ != null) {
            answ.setVisible(false);
        }
        if (answerPic != null) {
            answerPic.setVisible(false);
            answerPic = null;
        }
        StringBuffer oText = null;

        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i) instanceof String) {
                if (answer.get(i).equals(que.get(n).getName())) {
                    if (answer.get(i + 1) instanceof StringBuffer) {
                        oText = (StringBuffer) answer.get(i + 1);
                    } else if (answer.get(i + 1) instanceof JLabel) {
                        answerPic = (JLabel) answer.get(i + 1);
                    }
                }
            }
        }

        questionName.setText(que.get(n).getName() + "(" + que.get(n).toString() + ")");
        question.setText(que.get(n).getQuestion());

        if (oText != null) {
            answ.setText(oText.toString());
            answ.setVisible(true);
        }
        if (answerPic != null) {
            add(answerPic);
            answerPic.setBounds(0, getHeight() / 2, getWidth() / 5, getHeight() / 6);
            answerPic.setVisible(true);
        }
    }
}
