package Panels.manager.manageExams;

import Examination.Examination;
import People.*;
import SaveLoad.SaveExam;
import SaveLoad.SaveStudent;
import excel.ReadExcel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddStudent {

    private ArrayList<Student> students = new ArrayList<>();

    private JButton next;//add next student
    private JButton finish;//finished adding students

    private JTextField name;
    private JTextField lastname;
    private JTextField studentNo;

    private JLabel error;
    private JLabel n;//name
    private JLabel ln;//lastname
    private JLabel sn;//studentNo

    private Student s;
    private SaveStudent saver = new SaveStudent();

    /**
     *
     * @param d dimension of screen
     * @param f JFrame
     * @param ex Examination
     */
    public AddStudent(Dimension d, JDialog f, Examination ex) {

        int w = f.getWidth();

        JLabel label = new JLabel("به چه روشی دانشجویان را اضافه می کنید؟");
        label.setFont(new java.awt.Font("B Nazanin", 1, w / 32));

        JButton[] options = new JButton[2];

        options[0] = new JButton("با اکسل");
        options[0].setFont(new java.awt.Font("B Nazanin", 1, w / 34));
        options[0].addActionListener((e) -> {
            Window window = SwingUtilities.getWindowAncestor(options[1]);
            if (window != null) {
                window.setVisible(false);
            }
            ex.getOwner().addExamination(new Examination(ex.getOwner(), "nothing"));
            JFileChooser jfc = new JFileChooser(".");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "excel");
            jfc.setFileFilter(filter);
            jfc.setAcceptAllFileFilterUsed(false);
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                ReadExcel re = new ReadExcel();
                ArrayList<Student> stus = re.readExcel(jfc.getSelectedFile());
                boolean sw=false;
                for (int i = 0; i < stus.size(); i++) {
                    stus.get(i).addExamination(ex);
                    int l = saver.addStudentToExam(stus.get(i), ex);
                    if (l == 0 || l == 2) {
                        ex.addStudent(stus.get(i));
                        sw=true;
                        students.add(stus.get(i));
                    }
                }
                SaveExam se = new SaveExam();
                se.addStudents(students, ex);
                if (sw) {
                    JOptionPane.showMessageDialog(f, "دانشجویان با موفقیت اضافه شدند.", null, JOptionPane.OK_OPTION);
                } else {
                    JOptionPane.showMessageDialog(f, "با خطا مواجه شد.", null, JOptionPane.OK_OPTION);
                }
            }
        });

        options[1] = new JButton("به صورت دستی");
        options[1].setFont(new java.awt.Font("B Nazanin", 1, w / 34));
        options[1].addActionListener((e) -> {
            //close JOptionPane
            Window window = SwingUtilities.getWindowAncestor(options[1]);
            if (window != null) {
                window.setVisible(false);
            }

            final JDialog frame = new JDialog(f, "", true);
            ex.getOwner().addExamination(new Examination(ex.getOwner(), "nothing"));
            setAddStudent(d.width, d.height, frame, ex);

        });

        JOptionPane.showOptionDialog(f, label,
                "افزودن دانشجو", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

    }

    private void setAddStudent(int width, int height, JDialog frame, Examination exam) {
        int w = width / 3;
        int h = height / 3;

        // <editor-fold defaultstate="collapsed" desc="set JDialog and components">
        frame.setTitle("افزودن دستی دانشجویان");
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setBounds(w, h, w, h);
        frame.setPreferredSize(new Dimension(w, h));
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        error = new JLabel();
        error.setFont(new java.awt.Font("B Nazanin", 1, height / 36));
        error.setForeground(Color.red);
        frame.add(error);
        error.setBounds(w / 4, h * 3 / 5, w * 3 / 4, h / 10);
        error.setVisible(false);

        next = new JButton("بعدی");
        next.setFont(new java.awt.Font("B Nazanin", 1, height / 34));
        next.addActionListener((e) -> {
            error.setVisible(false);
            nextButtonListener(exam, frame);
        });
        frame.add(next);
        next.setBounds(w / 2, h * 3 / 4, w / 4, h / 8);

        finish = new JButton("اتمام");
        finish.setFont(new java.awt.Font("B Nazanin", 1, height / 34));
        finish.addActionListener((e) -> {
            SaveExam se = new SaveExam();
            se.addStudents(students, exam);
            frame.removeAll();
            frame.setVisible(false);
        });
        frame.add(finish);
        finish.setBounds(w / 4, h * 3 / 4, w / 4, h / 8);

        name = new JTextField();
        frame.add(name);
        name.setBounds(w / 4, h / 10, w / 2, h / 10);

        lastname = new JTextField();
        frame.add(lastname);
        lastname.setBounds(w / 4, h * 3 / 10, w / 2, h / 10);

        studentNo = new JTextField();
        frame.add(studentNo);
        studentNo.setBounds(w / 4, h / 2, w / 2, h / 10);

        n = new JLabel("نام");
        n.setFont(new java.awt.Font("B Nazanin", 1, height / 36));
        frame.add(n);
        n.setBounds(w / 4, 0, w / 4, h / 10);

        ln = new JLabel("نام خانوادگی");
        ln.setFont(new java.awt.Font("B Nazanin", 1, height / 36));
        frame.add(ln);
        ln.setBounds(w / 4, h / 5, w / 2, h / 10);

        sn = new JLabel("شماره دانشجویی");
        sn.setFont(new java.awt.Font("B Nazanin", 1, height / 36));
        frame.add(sn);
        sn.setBounds(w / 4, h * 2 / 5, w / 2, h / 10);

        frame.setVisible(true);// </editor-fold> 
    }

    private void nextButtonListener(Examination exam, JDialog frame) {
        if (name.getText().equals("") || lastname.getText().equals("")
                || studentNo.getText().equals("")) {
            error.setText("اطلاعات را کامل وارد کنید.");
            error.setVisible(true);
        } else {
            s = new Student(name.getText(), lastname.getText(),
                    studentNo.getText(), studentNo.getText(), studentNo.getText());
            s.addExamination(exam);
            int l = saver.addStudentToExam(s, exam);
            if (l == 0 || l == 2) {
                error.setVisible(false);
                exam.addStudent(s);
                JOptionPane.showMessageDialog(frame, "دانشجو با موفقیت اضافه شد.", null, JOptionPane.OK_OPTION);
                name.setText("");
                lastname.setText("");
                studentNo.setText("");
                students.add(s);
            } else if (l == 1) {
                error.setText("اطلاعات نادرست است.");
                error.setVisible(true);
            }else if(l==4){
                error.setText("دانشجو این آزمون را دارد.");
                error.setVisible(true);
            }
        }
    }
}
