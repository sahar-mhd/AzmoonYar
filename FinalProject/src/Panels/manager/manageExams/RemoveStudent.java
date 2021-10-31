package Panels.manager.manageExams;

import Examination.Examination;
import People.Student;
import SaveLoad.SaveExam;
import SaveLoad.SaveStudent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.*;

public class RemoveStudent extends JDialog{

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    
    private SaveStudent saver = new SaveStudent();
    private final Examination exam;
    private ArrayList<String> usernames = new ArrayList<>();
    
    private JPanel panel = new JPanel(null);
    private JLabel error;
    private JLabel username;
    
    private JTextField un;
    
    private JButton next;
    private JButton finish;
    
    public RemoveStudent (Examination exam){
        this.exam = exam;
        setTitle("حذف دانشجو");
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setBounds(d.width / 3, d.height / 3, d.width / 3, d.height / 3);
        setResizable(false);
        
        init();
    }
    private void init(){
        username = new JLabel("نام کاربری");
        username.setFont(new java.awt.Font("B Nazanin", 1, d.height / 40));
        panel.add(username);
        username.setBounds(getWidth() * 3 / 4, getHeight() / 6, getWidth() / 4, 
                getHeight() / 6);
        
        un = new JTextField();
        panel.add(un);
        un.setBounds(getWidth() / 4, getHeight() / 6, getWidth() / 2, getHeight() / 6);
        
        error = new JLabel();
        error.setFont(new java.awt.Font("B Nazanin", 1, d.height / 40));
        error.setForeground(Color.red);
        panel.add(error);
        error.setBounds(getWidth() / 4, getHeight() * 3 / 5, getWidth() * 3 / 4, 
                getHeight() / 10);
        error.setVisible(false);

        next = new JButton("بعدی");
        next.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        next.addActionListener((e) -> {
            error.setVisible(false);
            nextActionListener();
        });
        panel.add(next);
        next.setBounds(getWidth() / 2, getHeight() * 3 / 4, getWidth() / 4, 
                getHeight() / 8);
        
        finish = new JButton("اتمام");
        finish.setFont(new java.awt.Font("B Nazanin", 1, d.height / 34));
        finish.addActionListener((e) -> {
            saver.removeStudentOfExam(exam, usernames);
            removeAll();
            setVisible(false);
        });
        panel.add(finish);
        finish.setBounds(getWidth() / 4, getHeight() * 3 / 4, getWidth() / 4, 
                getHeight() / 8);
        
        add(panel);
        setVisible(true);
    }
    private void nextActionListener(){
        if (un.getText().equals("")){
            error.setText("نام کاربری دانشجو را وارد کنید.");
            error.setVisible(true);
        } else if (!saver.search(exam, un.getText())){
            error.setText("نام کاربری در آزمون موجود نیست.");
            error.setVisible(true);
        } else {
            usernames.add(un.getText());
            JOptionPane.showMessageDialog(null, "دانشجو با موفقیت از آزمون حذف شد.", 
                    null, JOptionPane.OK_OPTION);
            un.setText("");
        }
    }
}
