package Panels.manager;

import Panels.manager.manageExams.ManageExams;
import Panels.manager.newExam.NewExam;
import Panels.Init;
import Panels.Groups.Groups;
import Panels.manager.History.History;
import Panels.manager.correction.Correction;
import People.*;
import SaveLoad.SaveStudent;
import java.awt.*;
import javax.swing.*;

public class ManagerPanel {

    private JPanel choices;

    private JButton history;
    private JButton manageExams;
    private JButton newExam;
    private JButton groups;
    private JButton correction;

    private JButton backToLogin;

    private Manager manager; //The manager
    private SaveLoad.SaveStudent saver = new SaveStudent();
    private Groups gr;
    private History his;
    private Correction c;
    private ManageExams me;
    private NewExam ne;

    /**
     * Initializes the manager panel
     *
     * @param f is jframe
     */
    private void initialize(JFrame f) {

        f.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
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

        history = new JButton("تاریخچه آزمون ها");
        history.setFont(new java.awt.Font("B Nazanin", 1, w / 28));
        choices.add(history);
        history.setBounds(0, h / 7, choices.getWidth(), h / 7);
        history.addActionListener((q)->{
            if (gr != null) {
                groups.setEnabled(true);
                gr.setVisible(false);
                if (gr.getChat() != null){
                    gr.getChat().setVisible(false);
                }
                gr = null;
            }

            if (c != null) {
                correction.setEnabled(true);
                c.getMain().setVisible(false);
                if (c.getExamSelected() != null) {
                    c.getExamSelected().setVisible(false);
                }
                c = null;
            }
            
            JButton source = (JButton) q.getSource();
            source.setEnabled(false);
            his=new History();
            his.setBounds(w/3, 0, w/3, h*9/10);
            his.initialize(manager, source,f);
            f.add(his);
        });

        manageExams = new JButton("مدیریت آزمون ها");
        manageExams.setFont(new java.awt.Font("B Nazanin", 1, w / 28));
        manageExams.addActionListener((e) -> {
            if (his != null){
                history.setEnabled(true);
                his.setVisible(false);
                if (his.getSp() != null){
                    his.getSp().setVisible(false);
                }
                his = null;
            }
            
            if (gr != null) {
                groups.setEnabled(true);
                gr.setVisible(false);
                if (gr.getChat() != null){
                    gr.getChat().setVisible(false);
                }
                gr = null;
            }

            if (c != null) {
                correction.setEnabled(true);
                c.getMain().setVisible(false);
                if (c.getExamSelected() != null) {
                    c.getExamSelected().setVisible(false);
                }
                c = null;
            }
            me = new ManageExams(manager);
        });
        choices.add(manageExams);
        manageExams.setBounds(0, h * 2 / 7, choices.getWidth(), h / 7);

        newExam = new JButton("ایجاد آزمون");
        newExam.setFont(new java.awt.Font("B Nazanin", 1, w / 28));
        newExam.addActionListener((e) -> {
            if (his != null){
                history.setEnabled(true);
                his.setVisible(false);
                if (his.getSp() != null){
                    his.getSp().setVisible(false);
                }
                his = null;
            }
            
            if (gr != null) {
                gr.setVisible(false);
                groups.setEnabled(true);
                if (gr.getChat() != null){
                    gr.getChat().setVisible(false);
                }
                gr = null;
            }

            if (c != null) {
                c.getMain().setVisible(false);
                correction.setEnabled(true);
                if (c.getExamSelected() != null) {
                    c.getExamSelected().setVisible(false);
                }
                c = null;
            }
            ne = new NewExam(manager);
        });
        choices.add(newExam);
        newExam.setBounds(0, h * 3 / 7, choices.getWidth(), h / 7);

        groups = new JButton("گروه ها");
        groups.setFont(new java.awt.Font("B Nazanin", 1, w / 28));
        choices.add(groups);
        groups.setBounds(0, h * 4 / 7, choices.getWidth(), h / 7);
        groups.addActionListener((g) -> {
            if (his != null){
                history.setEnabled(true);
                his.setVisible(false);
                if (his.getSp() != null){
                    his.getSp().setVisible(false);
                }
                his = null;
            }
            
            if (c != null) {
                correction.setEnabled(true);
                c.getMain().setVisible(false);
                if (c.getExamSelected() != null) {
                    c.getExamSelected().setVisible(false);
                }
                c = null;
            }
            JButton source = (JButton) g.getSource();
            source.setEnabled(false);
            gr = new Groups();
            gr.initialize(manager.getUserName(), source, "m", f);
            gr.setBounds(w / 3, 0, w / 3, h - h / 10);
            f.add(gr);
        });

        correction = new JButton("تصحیح");
        correction.setFont(new java.awt.Font("B Nazanin", 1, w / 28));
        correction.addActionListener((e) -> {
            if (his != null){
                history.setEnabled(true);
                his.setVisible(false);
                if (his.getSp() != null){
                    his.getSp().setVisible(false);
                }
                his = null;
            }
            
            if (gr != null) {
                groups.setEnabled(true);
                gr.setVisible(false);
                if (gr.getChat() != null){
                    gr.getChat().setVisible(false);
                }
                gr = null;
            }
            correction.setEnabled(false);
            c = new Correction(manager, f);
        });
        choices.add(correction);
        correction.setBounds(0, h * 5 / 7, choices.getWidth(), h / 7);

        choices.setVisible(true);
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
            if(his!=null){
                his.setVisible(false);
                if(his.getSp()!=null){
                    his.getSp().setVisible(false);
                }
            }
            if (gr != null) {
                gr.setVisible(false);
                if (gr.getChat() != null) {
                    gr.getChat().setVisible(false);
                }
            }
            
            if (c != null) {
                c.getMain().setVisible(false);
                f.remove(c.getMain());
                if (c.getExamSelected() != null) {
                    c.getExamSelected().setVisible(false);
                    f.remove(c.getExamSelected());
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

    public ManagerPanel(Person person, JFrame f) {
        this.manager = (Manager) person;
        initialize(f);
    }

}
