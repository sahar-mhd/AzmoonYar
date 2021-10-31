package Panels.manager.newExam;

import Examination.Examination;
import People.Manager;
import SaveLoad.SaveExam;
import SaveLoad.SaveManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class NewExam extends JDialog {

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private final Manager m;
    private Examination exam;
    private SaveManager saveMan = new SaveManager();
    private SaveExam saveEx = new SaveExam();
    private int number = 1;

    private JLabel name;
    private JLabel qnumber;
    private JLabel qname;
    private JLabel error;
    private JLabel startDate;
    private JLabel stopDate;

    private JTextField n;
    private JTextField qn;

    private JSpinner startd;
    private JSpinner stopd;
    private JSpinner examStartTime;
    private JSpinner examStopTime;

    private JButton exit;
    private JButton cont;//continue

    private ButtonGroup choose;

    public NewExam(Manager m) {
        this.m = m;
        init();
    }

    public void init() {

        setTitle("آزمون جدید");
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setBounds(d.width / 3, d.height / 3, d.width / 3, d.height / 3);
        setResizable(false);
        getContentPane().setLayout(null);

        error = new JLabel("فیلد ها خالی نباشند.");
        error.setFont(new java.awt.Font("B Nazanin", 1, d.height / 40));
        error.setForeground(Color.red);
        add(error);
        error.setBounds(getWidth() / 4, getHeight() * 2 / 3, getWidth() * 3 / 4, getHeight() / 10);
        error.setVisible(false);

        name = new JLabel("نام آزمون");
        name.setFont(new java.awt.Font("B Nazanin", 1, d.height / 36));
        add(name);
        name.setBounds(getWidth() * 3 / 4, getHeight() / 6, getWidth() / 4, getHeight() / 12);

        startDate = new JLabel("تاریخ شروع");
        startDate.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        add(startDate);
        startDate.setBounds(getWidth() * 3 / 4, getHeight() / 3, getWidth() / 4, getHeight() / 12);

        SpinnerDateModel model = new SpinnerDateModel();
        model.setCalendarField(Calendar.MINUTE);

        examStartTime = new JSpinner();
        examStartTime.setModel(model);
        examStartTime.setEditor(new JSpinner.DateEditor(examStartTime, "EEE MMM dd HH:mm yyyy"));
        examStartTime.setFont(new java.awt.Font("Comic Sans MS", 1, d.height / 40));
        add(examStartTime);
        examStartTime.setBounds(getWidth() / 4, getHeight() / 3, getWidth() / 2, getHeight() / 8);

        stopDate = new JLabel("تاریخ پایان");
        stopDate.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        add(stopDate);
        stopDate.setBounds(getWidth() * 3 / 4, getHeight() / 2, getWidth() / 4, getHeight() / 12);
        
        model = new SpinnerDateModel();
        model.setCalendarField(Calendar.MINUTE);

        examStopTime = new JSpinner();
        examStopTime.setModel(model);
        examStopTime.setEditor(new JSpinner.DateEditor(examStopTime, "EEE MMM dd HH:mm yyyy"));
        examStopTime.setFont(new java.awt.Font("Comic Sans MS", 1, d.height / 40));
        add(examStopTime);
        examStopTime.setBounds(getWidth() / 4, getHeight() / 2, getWidth() / 2, getHeight() / 8);

        n = new JTextField();
        add(n);
        n.setBounds(getWidth() / 4, getHeight() / 6, getWidth() / 2, getHeight() / 8);

        exit = new JButton("بازگشت");
        exit.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        for (ActionListener al : exit.getActionListeners()) {
            exit.removeActionListener(al);
        }
        exit.addActionListener((e) -> {
            getContentPane().removeAll();
            setVisible(false);
        });
        add(exit);
        exit.setBounds(getWidth() / 4, getHeight() * 3 / 4, getWidth() / 4, getHeight() / 8);

        cont = new JButton("ادامه");
        cont.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        for (ActionListener al : cont.getActionListeners()) {
            cont.removeActionListener(al);
        }
        cont.addActionListener((e) -> {

            try {
                
                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm yyyy");
                Date start = (Date) examStartTime.getValue();
                
                Date stop = (Date) examStopTime.getValue();
                
                Date da = formatter.parse(formatter.format(new Date(System.currentTimeMillis())));
                if (n.getText().equals("")) {
                    error.setVisible(true);
                } else if (start.after(stop)) {
                    error.setText("زمان پایان باید از زمان شروع بیشتر باشد.");
                    error.setVisible(true);
                } else if (start.before(da)) {
                    error.setText("زمان آزمون بعد از اکنون باشد.");
                    error.setVisible(true);
                } else {
                    error.setVisible(false);
                    exam = new Examination(m, n.getText());
                    
                    exam.setAllQuestionsTogether(false);
                    exam.setMoroor(true);
                    exam.setTartibi(true);
                    
                    exam.setStart(start);
                    exam.setStop(stop);
                    
                    switch (saveEx.add(exam, m.getUserName())) {
                        case 2:
                            m.addExamination(exam);
                            try {
                                saveMan.addExam(exam, m);
                            } catch (IOException ex) {
                                Logger.getLogger(NewExam.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            questions();
                            break;
                        case 0:
                            error.setText("این نام موجود است.");
                            error.setVisible(true);
                            break;
                        default:
                            error.setText("خطا");
                            error.setVisible(true);
                    }
                }
            } catch (ParseException ex) {
                Logger.getLogger(NewExam.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        add(cont);
        cont.setBounds(getWidth() / 2, getHeight() * 3 / 4, getWidth() / 4, getHeight() / 8);

        setVisible(true);
    }

    /**
     * to add questions to new exam
     */
    public void questions() {
        startDate.setVisible(false);
        stopDate.setVisible(false);

        name.setVisible(false);
        n.setVisible(false);

        examStartTime.setVisible(false);
        examStopTime.setVisible(false);

        error.setBounds(getWidth() / 4, getHeight() * 11 / 18, getWidth() / 2, getHeight() / 8);
        error.setVisible(false);

        qnumber = new JLabel("سوال " + number);
        qnumber.setFont(new java.awt.Font("B Nazanin", 1, d.height / 32));
        add(qnumber);
        qnumber.setBounds(getWidth() / 4, getHeight() / 50, getWidth() / 2, getHeight() / 8);

        qname = new JLabel("نام سوال");
        qname.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        add(qname);
        qname.setBounds(getWidth() / 3, getHeight() / 6, getWidth() / 3, getHeight() / 6);

        qn = new JTextField();
        add(qn);
        qn.setBounds(getWidth() / 3, getHeight() / 3, getWidth() / 3, getHeight() / 6);

        startDate = new JLabel("مدت زمان");
        startDate.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        add(startDate);
        startDate.setBounds(getWidth() * 3 / 4, getHeight() / 2, getWidth() / 4, getHeight() / 12);

        SpinnerDateModel model = new SpinnerDateModel();
        model.setCalendarField(Calendar.MINUTE);

        startd = new JSpinner();
        startd.setModel(model);
        startd.setEditor(new JSpinner.DateEditor(startd, "HH:mm:ss"));
        startd.setFont(new java.awt.Font("Comic Sans MS", 1, d.height / 40));
        add(startd);
        startd.setBounds(getWidth() / 4, getHeight() / 2, getWidth() / 2, getHeight() / 8);

        for (ActionListener al : cont.getActionListeners()) {
            cont.removeActionListener(al);
        }

        cont.addActionListener((e) -> {
            if (qn.getText().equals("")) {
                error.setText("فیلد ها تکمیل شوند.");
                error.setVisible(true);
            } else if (saveEx.questionNameExists(exam, qn.getText())) {
                error.setText("سوال با این نام موجود است.");
                error.setVisible(true);
            } else {
                error.setVisible(false);
                contActionListener();
            }
        });

        for (ActionListener al : exit.getActionListeners()) {
            exit.removeActionListener(al);
        }
        exit.addActionListener((e) -> {
            exitActionListener();
        });

        exit.setBounds(getWidth() / 4, getHeight() * 3 / 4, getWidth() / 4, getHeight() / 8);
        cont.setBounds(getWidth() / 2, getHeight() * 3 / 4, getWidth() / 4, getHeight() / 8);
    }

    private void contActionListener() {
        number++;
        qnumber.setText("نوع سوال");
        qname.setVisible(false);
        qn.setVisible(false);
        startDate.setVisible(false);
        startd.setVisible(false);
        stopDate.setVisible(false);

        choose = new ButtonGroup();

        JRadioButton test = new JRadioButton("تستی");
        test.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        add(test);
        test.setBounds(getWidth() / 4, getHeight() / 6, getWidth() / 2, getHeight() / 6);
        choose.add(test);

        JRadioButton trueFalse = new JRadioButton("صحیح غلط");
        trueFalse.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        add(trueFalse);
        trueFalse.setBounds(getWidth() / 4, getHeight() / 3, getWidth() / 2, getHeight() / 6);
        choose.add(trueFalse);

        JRadioButton descriptive = new JRadioButton("تشریحی");
        descriptive.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        add(descriptive);
        descriptive.setBounds(getWidth() / 4, getHeight() / 2, getWidth() / 2, getHeight() / 6);
        choose.add(descriptive);

        for (ActionListener al : cont.getActionListeners()) {
            cont.removeActionListener(al);
        }

        cont.addActionListener((e) -> {
            if (test.isSelected() || trueFalse.isSelected() || descriptive.isSelected()) {
                error.setVisible(false);
                setBounds(d.width / 4, d.height / 4, d.width / 2, d.height / 2);

                exit.setBounds(getWidth() / 3, getHeight() * 6 / 7, getWidth() / 6, getHeight() / 14);
                cont.setBounds(getWidth() / 2, getHeight() * 6 / 7, getWidth() / 6, getHeight() / 14);
                cont.setText("بعدی");
                error.setLocation(0, getHeight() * 6 / 7);

                remove(test);
                remove(trueFalse);
                remove(descriptive);

                if (test.isSelected()) {
                    SelectTest te = new SelectTest();
                    qnumber.setVisible(false);
                    te.testSelected(this, d, cont, error, qn, startd,
                            saveEx, exam, m, number - 1);
                } else if (trueFalse.isSelected()) {
                    SelectTrueFalse stf = new SelectTrueFalse();
                    qnumber.setVisible(false);
                    stf.trueFalseSelected(this, d, cont, error, qn,
                            startd, saveEx, exam, m, number - 1);
                } else if (descriptive.isSelected()) {
                    SelectDescriptive sd = new SelectDescriptive();
                    qnumber.setVisible(false);
                    sd.DescriptiveSeleted(this, cont, d, error, qn, startd,
                            stopd, saveEx, exam, m, number - 1);
                }
            } else {
                error.setText("یک گزینه را انتخاب نمایید.");
                error.setVisible(true);
            }
        });
    }

    private void exitActionListener() {
        JLabel label = new JLabel("تغییرات ذخیره نخواهند شد. آیا می خواهید پنجره ایجاد آزمون را ببندید؟");
        label.setFont(new java.awt.Font("B Nazanin", 1, d.width / 60));

        JButton[] options = new JButton[2];
        options[0] = new JButton("بله");
        options[0].setFont(new java.awt.Font("B Nazanin", 1, d.width / 60));
        options[0].addActionListener(l -> {
            JOptionPane.getRootFrame().dispose();
            dispose();
            saveEx.changeSettings(m, exam);
        });

        options[1] = new JButton("خیر");
        options[1].setFont(new java.awt.Font("B Nazanin", 1, d.width / 60));
        options[1].addActionListener(l -> {
            JOptionPane.getRootFrame().dispose();
            setVisible(true);
        });

        JOptionPane.showOptionDialog(this, label,
                "خروج", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

}
