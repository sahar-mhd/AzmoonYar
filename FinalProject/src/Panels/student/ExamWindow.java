package Panels.student;

import Examination.Examination;
import Examination.Question.*;
import Panels.manager.correction.Correction;
import People.Student;
import SaveLoad.SaveAnswers;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import Time.Timer;
import Time.TimerDate;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.util.Collections;

public class ExamWindow extends JDialog {

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    public final Examination exam;
    private Question q;
    private Timer time;
    private TimerDate whole; //if all exams at once

    private final SaveAnswers saveAns = new SaveAnswers();
    public final Student st;

    private final JLabel examName = new JLabel();
    private final JLabel soorateSoal = new JLabel();
    private final JLabel quname = new JLabel();

    private JPanel tf;
    private JPanel des;
    private JPanel test;

    private final JButton next = new JButton("بعدی");
    private final JButton finish = new JButton("اتمام");
    private JButton img;
    private JButton text;

    private JRadioButton frb1;
    private JRadioButton frb2;
    private JRadioButton j1;
    private JRadioButton j2;
    private JRadioButton j3;
    private JRadioButton j4;

    private JFileChooser jfc;
    private JTextArea jta;

    private int n = 0;

    public ExamWindow(Examination exam, Student st) {
        this.exam = exam;
        this.st = st;
        init();
    }

    private void init() {

        setTitle("آزمون");
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setBounds(d.width / 4, d.height / 4, d.width / 2, d.height / 2);
        setResizable(false);
        setLayout(null);

        //sort questions
        if (exam.isTartibi()) {
            exam.getQuestion().sort((d1, d2) -> d1.compareTo(d2));
        } else if (!exam.isTartibi()) {
            Collections.shuffle(exam.getQuestion());
        }

        quname.setOpaque(true);
        quname.setBackground(Color.LIGHT_GRAY);
        add(quname);
        quname.setBounds(0, getHeight() / 6, getWidth() / 4, getHeight() / 6);

        soorateSoal.setOpaque(true);
        soorateSoal.setBackground(Color.white);

        q = exam.getQuestion().get(0);
        if (q instanceof Test) {
            test();
            add(test);
            test.setLocation(getWidth() / 4, getHeight() / 6);
        } else if (q instanceof Descriptive) {
            des();
            add(des);
            des.setLocation(getWidth() / 4, getHeight() / 6);
        } else if (q instanceof TrueFalse) {
            tf();
            add(tf);
            tf.setLocation(getWidth() / 4, getHeight() / 6);
        }

        String t = null;
        if (exam.isTartibi()) {
            t = "ترتیبی";
        } else {
            t = "تصادفی";
        }

        if (exam.isAllQuestionsTogether()) {
            whole = new TimerDate(getWidth(), getHeight(), exam.getStop(), this);
            whole.start();
        }

        examName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, exam.getName() + "(" + t + ")",
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                new java.awt.Font(null, 1, d.height / 24)));
        add(examName);
        examName.setBounds(0, 0, getWidth(), getHeight() / 6);

        finish.addActionListener((e) -> {
            switch (JOptionPane.showConfirmDialog(null, "پایان آزمون؟", null, JOptionPane.YES_NO_OPTION)) {
                case JOptionPane.YES_OPTION:
                    File file = new File("Exams\\" + exam.getName() + " "
                            + exam.getOwner().getUserName() + "\\Answers");

                    if (!file.exists()) {
                        file.mkdir();
                    }

                    File f = new File("Exams\\" + exam.getName() + " "
                            + exam.getOwner().getUserName() + "\\Answers\\"
                            + st.getUserName());
                    if (!f.exists()) {
                        f.mkdir();
                    }
                    nextAction();
                    dispose();
            }
        });
        add(finish);
        finish.setBounds(getWidth() / 4, getHeight() * 5 / 6, getWidth() / 4, getHeight() / 12);

        next.addActionListener((e) -> {
            switch (JOptionPane.showConfirmDialog(null, "سوال بعد؟", null, JOptionPane.YES_NO_OPTION)) {
                case JOptionPane.YES_OPTION:
                    nextAction();
                    if (exam.getQuestion().size() == n + 2) {
                        next.setVisible(false);
                        remove(next);
                    }
                    n++;
                    q = exam.getQuestion().get(n);
                    if (q instanceof Test) {
                        test();
                        add(test);
                        test.setBounds(getWidth() / 4, getHeight() / 6, getWidth() * 3 / 4, getHeight() * 2 / 3);
                        test.setVisible(true);
                    } else if (q instanceof Descriptive) {
                        des();
                        add(des);
                        des.setBounds(getWidth() / 4, getHeight() / 6, getWidth() * 3 / 4, getHeight() * 2 / 3);
                        des.setVisible(true);

                    } else if (q instanceof TrueFalse) {
                        tf();
                        add(tf);
                        tf.setBounds(getWidth() / 4, getHeight() / 6, getWidth() * 3 / 4, getHeight() * 2 / 3);
                        tf.setVisible(true);
                    }
            }
        });
        add(next);
        next.setBounds(getWidth() / 2, getHeight() * 5 / 6, getWidth() / 4, getHeight() / 12);
        if (exam.getQuestion().size() == 1) {
            next.setEnabled(false);
        }

        setVisible(true);
    }

    private void tf() {
        tf = new JPanel();
        tf.setLayout(null);
        tf.setSize(getWidth() * 3 / 4, getHeight() * 2 / 3);

        soorateSoal.setText(q.getQuestion());
        tf.add(soorateSoal);
        soorateSoal.setBounds(0, 0, tf.getWidth() * 3 / 4, tf.getHeight() / 2);

        ButtonGroup bg = new ButtonGroup();

        frb1 = new JRadioButton("صحیح");
        bg.add(frb1);
        tf.add(frb1);
        frb1.setBounds(tf.getWidth() / 4, tf.getHeight() / 2,
                tf.getWidth() / 4, tf.getHeight() / 4);

        frb2 = new JRadioButton("غلط");
        bg.add(frb2);
        tf.add(frb2);
        frb2.setBounds(tf.getWidth() * 3 / 4, tf.getHeight() / 2,
                tf.getWidth() / 4, tf.getHeight() / 4);

        quname.setText("نام سوال: " + q.getName());

        if (!exam.isAllQuestionsTogether()) {

            Object[] object = {frb1, frb2};
            time = new Timer(q.getDuration(), object);
            add(time.getLabel());
            time.getLabel().setBounds(0, getHeight() / 2, getWidth() / 4, getHeight() / 4);

            time.start();
        }
    }

    private void des() {
        des = new JPanel();
        des.setLayout(null);
        des.setSize(getWidth() * 3 / 4, getHeight() * 2 / 3);

        soorateSoal.setText(exam.getQuestion().get(n).getQuestion());
        des.add(soorateSoal);
        soorateSoal.setBounds(0, 0,
                des.getWidth() * 3 / 4, des.getHeight() / 3);

        img = new JButton("آپلود تصویر");

        text = new JButton("نوشتن متن");
        text.addActionListener((e) -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("نوشتن متن");
            dialog.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
            dialog.setModal(true);
            dialog.setBounds(d.width / 3, d.height / 3, d.width / 3, d.height / 3);
            dialog.setResizable(false);
            dialog.setLayout(null);

            jta = new JTextArea();
            dialog.add(jta);
            jta.setBounds(0, 0, dialog.getWidth(), dialog.getHeight() * 2 / 3);

            JButton b = new JButton("ذخیره");
            b.addActionListener((l) -> {
                if (text.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "متنی نوشته نشده.",
                            null, JOptionPane.OK_OPTION);
                } else {
                    text.setEnabled(false);
                    saveAns.saveAnswer(st, exam, q, jta.getText(), "", null);
                    dialog.dispose();

                    JTextArea showText = new JTextArea(jta.getText());
                    showText.setEditable(false);
                    des.add(showText);
                    showText.setBounds(0, des.getHeight() / 2, des.getWidth() / 2,
                            des.getHeight() / 3);
                    showText.setVisible(true);
                }
            });
            dialog.add(b);
            b.setBounds(dialog.getWidth() / 4, dialog.getHeight() * 3 / 4,
                    dialog.getWidth() / 4, dialog.getHeight() / 10);

            dialog.setVisible(true);
        });
        des.add(text);
        text.setBounds(des.getWidth() / 4, des.getHeight() / 3,
                des.getWidth() / 6, des.getHeight() / 10);

        img.addActionListener((e) -> {
            jfc = new JFileChooser(".");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg");
            jfc.setFileFilter(filter);
            jfc.setAcceptAllFileFilterUsed(false);

            if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                img.setEnabled(false);
                File fileSelected = jfc.getSelectedFile();

                BufferedImage image = null;
                try {
                    image = ImageIO.read(fileSelected);
                } catch (IOException ex) {
                    Logger.getLogger(ExamWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

                ImageIcon imag = new ImageIcon("jpg.png");
                Image temp = imag.getImage().getScaledInstance(getWidth() / 32,
                        getHeight() / 12, Image.SCALE_DEFAULT);
                imag.setImage(temp);

                JLabel showPicSelected = new JLabel("پاسخ به صورت عکس");

                showPicSelected.setIcon(imag);
                showPicSelected.setVisible(true);
                showPicSelected.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                showPicSelected.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            Desktop.getDesktop().open(fileSelected);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                des.add(showPicSelected);
                showPicSelected.setBounds(des.getWidth() / 2, des.getHeight() / 2,
                        des.getWidth() / 4, des.getHeight() / 3);

                saveAns.saveAnswer(st, exam, q, "", "img", image);
            }

        });
        des.add(img);
        img.setBounds(des.getWidth() / 2, des.getHeight() / 3,
                des.getWidth() / 6, des.getHeight() / 10);

        quname.setText("نام سوال: " + q.getName());

        if (!exam.isAllQuestionsTogether()) {

            Object[] object = {img, text, jfc, jta};
            time = new Timer(q.getDuration(), object);
            add(time.getLabel());
            time.getLabel().setBounds(0, getHeight() / 2, getWidth() / 4, getHeight() / 4);

            time.start();
        }
    }

    private void test() {
        test = new JPanel();
        test.setLayout(null);
        test.setSize(getWidth() * 3 / 4, getHeight() * 2 / 3);

        soorateSoal.setText(exam.getQuestion().get(n).getQuestion());
        test.add(soorateSoal);
        soorateSoal.setBounds(0, 0, test.getWidth() * 3 / 4, test.getHeight() / 5);

        ButtonGroup bg = new ButtonGroup();

        j1 = new JRadioButton(((Test) q).getSw1());
        bg.add(j1);
        test.add(j1);
        j1.setBounds(test.getWidth() / 4, test.getHeight() / 5,
                test.getWidth() * 3 / 4, test.getHeight() / 5);

        j2 = new JRadioButton(((Test) q).getSw2());
        bg.add(j2);
        test.add(j2);
        j2.setBounds(test.getWidth() / 4, test.getHeight() * 2 / 5,
                test.getWidth() * 3 / 4, test.getHeight() / 5);

        j3 = new JRadioButton(((Test) q).getSw3());
        bg.add(j3);
        test.add(j3);
        j3.setBounds(test.getWidth() / 4, test.getHeight() * 3 / 5,
                test.getWidth() * 3 / 4, test.getHeight() / 5);

        j4 = new JRadioButton(((Test) q).getSw4());
        bg.add(j4);
        test.add(j4);
        j4.setBounds(test.getWidth() / 4, test.getHeight() * 4 / 5,
                test.getWidth() * 3 / 4, test.getHeight() / 5);

        quname.setText("نام سوال: " + q.getName());

        if (!exam.isAllQuestionsTogether()) {

            Object[] object = {j1, j2, j3, j4};
            time = new Timer(q.getDuration(), object);
            add(time.getLabel());
            time.getLabel().setBounds(0, getHeight() / 2, getWidth() / 4, getHeight() / 4);

            time.start();
        }
    }

    public void nextAction() {
        if (time != null) {
            time.getLabel().setVisible(false);
        }
        if (test != null) {
            if (j1.isSelected()) {
                saveAns.saveAnswer(st, exam, q, j1.getText(), "", null);
            } else if (j2.isSelected()) {
                saveAns.saveAnswer(st, exam, q, j2.getText(), "", null);
            } else if (j3.isSelected()) {
                saveAns.saveAnswer(st, exam, q, j3.getText(), "", null);
            } else if (j4.isSelected()) {
                saveAns.saveAnswer(st, exam, q, j4.getText(), "", null);
            }
            test.setVisible(false);
            test = null;
        }
        if (tf != null) {
            if (frb1.isSelected()) {
                saveAns.saveAnswer(st, exam, q, "true", "", null);
            } else if (frb2.isSelected()) {
                saveAns.saveAnswer(st, exam, q, "false", "", null);
            }
            tf.setVisible(false);
            tf = null;
        }
        if (des != null) {
            des.setVisible(false);
            des = null;
        }
    }
}
