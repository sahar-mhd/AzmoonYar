package Panels.manager.correction;

import Examination.Examination;
import Examination.Question.Descriptive;
import Examination.Question.Question;
import People.*;
import SaveLoad.SaveExam;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class CorrectionDialog extends JDialog {

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    private final SaveLoad.SaveAnswers saveAnswer = new SaveLoad.SaveAnswers();
    private final SaveLoad.SaveExam saveExam = new SaveExam();

    private int n = 0;

    private final Manager m;
    private final String st;
    private final Examination ex;

    private ArrayList<Object> answer;
    private ArrayList<Descriptive> que = new ArrayList<>();
    private final ArrayList<Double> grades = new ArrayList<>();
    private final ArrayList<String> questions = new ArrayList<>();

    private final JTextArea question = new JTextArea();
    private final JTextArea ans = new JTextArea();
    private final JTextArea sansw = new JTextArea();

    private final JSpinner rank = new JSpinner(new SpinnerNumberModel(20, 0, 20, 0.01));

    private final JLabel quName = new JLabel();
    private final JLabel score = new JLabel("نمره");
    private JLabel answerPic = new JLabel();

    private final JButton next = new JButton("بعدی");

    public CorrectionDialog(Manager m, String studentUsername, Examination ex) {
        this.m = m;
        this.ex = ex;
        this.st = studentUsername;
        init();
    }

    private void init() {
        setTitle("تصحیح سوالات تشریحی");
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setBounds(d.width / 4, d.height / 4, d.width / 2, d.height / 2);
        setResizable(false);
        getContentPane().setLayout(null);

        answer = saveAnswer.loadAnswers(st,
                ex.getName(), m.getUserName(), getWidth(), getHeight());
        ArrayList<Question> quest = saveExam.loadQ(ex);

        quest.stream().filter((q) -> (q instanceof Descriptive)).forEachOrdered((q) -> {
            que.add((Descriptive) q);
        });

        add(score);
        score.setBounds(getWidth() / 8, getHeight() / 3, getWidth() / 8, getHeight() / 6);

        add(rank);
        rank.setBounds(0, getHeight() / 3, getWidth() / 4, getHeight() / 6);

        quName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "",
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                new java.awt.Font(null, 1, d.height / 24)));
        quName.setText(que.get(n).getName());
        add(quName);
        quName.setBounds(0, 0, getWidth(), getHeight() / 6);

        question.setText(que.get(n).getQuestion());
        question.setEditable(false);
        add(question);
        question.setBounds(getWidth() / 4, getHeight() / 6, getWidth() * 3 / 4, getHeight() / 6);

        ans.setText(que.get(n).getAnswer());
        ans.setEditable(false);
        add(ans);
        ans.setBounds(getWidth() / 4, getHeight() / 3, getWidth() * 3 / 4, getHeight() / 6);

        JLabel label = new JLabel("پاسخ دانشجو");
        add(label);
        label.setBounds(getWidth() / 8, getHeight() / 2, getWidth() / 8, getHeight() / 8);

        sansw.setEditable(false);
        add(sansw);
        sansw.setBounds(getWidth() / 4, getHeight() / 2, getWidth() * 9 / 16, getHeight() / 4);
        sansw.setVisible(false);


        if (que.size() == 1) {
            next.setText("اتمام");
            next.addActionListener((e) -> {
                questions.add(que.get(n).getName());
                grades.add((Double) rank.getValue());
                saveAnswer.saveGrade(st, grades, ex.getName() + " " + m.getUserName(), questions);
                dispose();
            });
        } else {
            next.addActionListener((e) -> {
                questions.add(que.get(n).getName());
                grades.add((Double) rank.getValue());
                n++;
                nextAction();
            });
        }
        add(next);
        next.setBounds(getWidth() / 2, getHeight() * 5 / 6, getWidth() / 4, getHeight() / 12);

        nextAction();
        
        setVisible(true);
    }

    public void nextAction() {

        if (sansw != null) {
            sansw.setVisible(false);
        }
        if (answerPic != null) {
            answerPic.setVisible(false);
            answerPic = null;
        }
        StringBuffer sb = null;
        
        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i) instanceof String) {
                if (answer.get(i).equals(que.get(n).getName())) {
                    if (answer.get(i + 1) instanceof StringBuffer) {
                        sb = (StringBuffer) answer.get(i + 1);
                    } else if (answer.get(i + 1) instanceof JLabel) {
                        answerPic = (JLabel) answer.get(i + 1);
                    }
                }
            }
        }

        quName.setText(que.get(n).getName());
        question.setText(que.get(n).getQuestion());

        if (sb != null) {
            sansw.setText(sb.toString());
            sansw.setVisible(true);
        }
        if (answerPic != null) {
            add(answerPic);
            answerPic.setBounds(getWidth() * 13 / 16, getHeight() / 2, getWidth() * 3 / 16, getHeight() / 4);
            answerPic.setVisible(true);
        }

        if (que.size() == n + 1) {
            next.setText("اتمام");
            for (ActionListener l : next.getActionListeners()) {
                next.removeActionListener(l);
            }
            next.addActionListener((e) -> {
                answerPic = null;
                questions.add(que.get(n).getName());
                grades.add((Double) rank.getValue());
                saveAnswer.saveGrade(st, grades, ex.getName() + " " + m.getUserName(), questions);
                dispose();
            });
        }
    }
}
