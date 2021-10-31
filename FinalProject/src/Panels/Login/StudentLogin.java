package Panels.Login;

import Panels.Init;
import Panels.student.StudentPanel;
import People.Student;
import SaveLoad.SaveStudent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class StudentLogin {

    static JTextField username, password, firstName, lastName, studentId;
    static JLabel studentPan, fnLabel, lnLabel, usernameLabel, passwordLabel, idLabel;
    static JButton confirm, back;
    static JLabel error = new JLabel();
    static SaveStudent saver = new SaveStudent();

    private static void remove() {
        fnLabel.setVisible(false);
        lnLabel.setVisible(false);
        firstName.setVisible(false);
        lastName.setVisible(false);
        idLabel.setVisible(false);
        studentId.setVisible(false);
        studentPan.setVisible(false);
        usernameLabel.setVisible(false);
        username.setVisible(false);
        passwordLabel.setVisible(false);
        password.setVisible(false);
        confirm.setVisible(false);
        back.setVisible(false);
        error.setVisible(false);
    }

    private static void confirmActionPerformed(java.awt.event.ActionEvent evt, JFrame f, String mode) throws ClassNotFoundException {
        if (mode.equals("register")) {
            Student s = new Student(firstName.getText(), lastName.getText(), username.getText(), password.getText(), studentId.getText());
            if (saver.add(s, "register", null) == 5) {
                remove();
                new StudentPanel(s, f);
            } else {
                error.setText("نام کاربری موجود است.");
                error.setVisible(true);
                f.repaint();
            }
        } else {
            Student s = new Student(username.getText(), password.getText());
            int check = saver.add(s, "login", null);
            if (check == 0) {
                remove();
                new StudentPanel(s, f);
            } else {
                error.setVisible(true);
                f.repaint();
                switch (check) {
                    case 1:
                        error.setText("رمز عبور اشتباه است.");
                        break;
                    case 2:
                        error.setText("نام کاربری موجود نیست.");
                        break;
                    default:
                        error.setText("خطا!");
                        break;
                        
                }
            }
        }
    }

    /**
     *
     * @param f is jframe
     * @param mode can be register or login
     */
    public static void entrance(JFrame f, String mode) {
        int height = f.getHeight();
        int width = f.getWidth();

        fnLabel = new JLabel("نام : ");
        lnLabel = new JLabel("نام خانوادگی : ");
        firstName = new JTextField();
        lastName = new JTextField();
        idLabel = new JLabel("شماره دانشجویی : ");
        studentId = new JTextField();

        // <editor-fold defaultstate="collapsed" desc="register mode">
        if (mode.equals("register")) {
            //first name field
            fnLabel.setFont(new java.awt.Font("B Nazanin", 1, height / 20));
            fnLabel.setBounds(width * 23 / 40, height * 4 / 15, width / 4, height / 16);
            f.add(fnLabel);

            firstName.setBounds(width * 3 / 8, height * 4 / 15, width / 5, height / 16);
            f.add(firstName);

            //last name field
            lnLabel.setFont(new java.awt.Font("B Nazanin", 1, height / 20));
            lnLabel.setBounds(width * 23 / 40, height * 5 / 15, width / 4, height / 16);
            f.add(lnLabel);

            lastName.setBounds(width * 3 / 8, height * 5 / 15, width / 5, height / 16);
            f.add(lastName);

            //id field
            idLabel.setFont(new java.awt.Font("B Nazanin", 1, height / 20));
            idLabel.setBounds(width * 23 / 40, height * 6 / 15, width / 4, height / 16);
            f.add(idLabel);

            studentId.setBounds(width * 3 / 8, height * 6 / 15, width / 5, height / 16);
            f.add(studentId);
        }// </editor-fold> 

        // <editor-fold defaultstate="collapsed" desc="buttons and labels">
        //student label
        studentPan = new JLabel("پنل دانشجو");
        studentPan.setBounds(width * 3 / 8, height / 15, width / 2, height / 9);
        studentPan.setFont(new java.awt.Font("B Nazanin", 1, height / 10));
        f.add(studentPan);

        //username field
        usernameLabel = new JLabel("نام کاربری : ");
        usernameLabel.setFont(new java.awt.Font("B Nazanin", 1, height / 20));
        usernameLabel.setBounds(width * 23 / 40, height * 7 / 15, width / 4, height / 16);
        f.add(usernameLabel);

        username = new JTextField();
        username.setBounds(width * 3 / 8, height * 7 / 15, width / 5, height / 16);
        f.add(username);

        //password field
        passwordLabel = new JLabel("رمز عبور : ");
        passwordLabel.setFont(new java.awt.Font("B Nazanin", 1, height / 20));
        passwordLabel.setBounds(width * 23 / 40, height * 8 / 15, width / 4, height / 16);
        f.add(passwordLabel);

        password = new JPasswordField();
        password.setBounds(width * 3 / 8, height * 8 / 15, width / 5, height / 16);
        f.add(password);

        //error
        error.setBounds(f.getWidth() * 3 / 8, f.getHeight() * 11 / 15, f.getWidth() / 2, f.getHeight() / 16);
        error.setFont(new java.awt.Font("B Nazanin", 1, f.getHeight() / 20));
        error.setForeground(Color.red);
        f.add(error);
        error.setVisible(false);

        //confirm button
        confirm = new JButton("تایید");
        confirm.setBounds(width * 13 / 32, height * 9 / 15, width / 8, height / 16);
        confirm.setFont(new java.awt.Font("B Nazanin", 1, height / 20));
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((mode.equals("register")) && (firstName.getText().equals("")
                        || lastName.getText().equals("")
                        || studentId.getText().equals(""))) {
                    error.setText("اطلاعات را کامل وارد کنید.");
                    error.setVisible(true);
                    f.repaint();
                } else if (username.getText().equals("") || password.getText().equals("")) {
                    error.setText("اطلاعات را کامل وارد کنید.");
                    error.setVisible(true);
                    f.repaint();
                } else {
                    try {
                        confirmActionPerformed(e, f, mode);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(StudentLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        f.add(confirm);

        //back button
        back = new JButton("بازگشت");
        back.setBounds(width * 13 / 32, height * 10 / 15, width / 8, height / 16);
        back.setFont(new java.awt.Font("B Nazanin", 1, height / 22));
        back.addActionListener((e) -> {
            remove();
            Init.getBack();
        });
        f.add(back);// </editor-fold> 
    }
}
