package Panels.student.results;

import SaveLoad.Loader;
import SaveLoad.SaveAnswers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Results extends JScrollPane {

    private Loader loader;
    private ArrayList<String> exams;
    private SaveAnswers saveAns = new SaveAnswers();

    private JButton[] buttons;
    private JButton exit;

    private JPanel panel;
    private JPanel p;

    private ActionListener listener;

    private JScrollPane sp;

    public void initialize(String username, JButton source, JFrame f) {
        int w = f.getWidth(), h = f.getHeight();
        loader = new Loader();
        exit = new JButton("بازگشت");

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(exit);

        exams = new ArrayList<>();
        exams = loader.load(username, "s");
        buttons = new JButton[exams.size()];

        for (int i = 0; i < exams.size(); i++) {
            buttons[i] = new JButton(exams.get(i));
            panel.add(buttons[i]);
        }

        exit.addActionListener((e) -> {
            sp.setVisible(false);
            setVisible(false);
            source.setEnabled(true);
        });

        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sp != null) {
                    sp.setVisible(false);
                }
                sp = new JScrollPane();
                p = new JPanel();

                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                JButton button = (JButton) e.getSource();
                String s = button.getText();
                if (!saveAns.searchAnswer(s.substring(0, s.lastIndexOf(" ")),
                        s.substring(s.lastIndexOf(" ")+1), username)) {
                    JOptionPane.showMessageDialog(null, "در آزمون شرکت نکرده اید.",
                            null, JOptionPane.OK_OPTION);
                } else if (!(loader.graded(button.getText(), username))) {
                    JLabel notGraded = new JLabel("در حال تصحیح");
                    p.add(notGraded);
                    notGraded.setVisible(true);
                } else {
                    JLabel wholeGrade = new JLabel("نمره کلی: ");
                    wholeGrade.setText(wholeGrade.getText() + loader.getgrade(button.getText(), username));
                    p.add(wholeGrade);
                    JLabel gradeofeachQ = new JLabel("<html>نمره هر سوال: ", SwingConstants.CENTER);
                    ArrayList<String> st = loader.gradeofeachQ(button.getText(), username);
                    for (int i = 0; i < st.size(); i++) {
                        gradeofeachQ.setText(gradeofeachQ.getText() + st.get(i) + "<br/>");
                    }
                    gradeofeachQ.setText(gradeofeachQ.getText() + "</html>");
                    p.add(gradeofeachQ);
                    Object[] o = loader.getDegree(button.getText(), username);
                    JLabel degree = new JLabel("رتبه: ");
                    degree.setText(degree.getText() + o[0]);
                    p.add(degree);
                    JLabel avg = new JLabel("میانگین نمرات امتحان: ");
                    avg.setText(avg.getText() + o[1]);
                    p.add(avg);
                }
                getSp().setViewportView(p);
                getSp().setBounds(0, 0, w / 3, h * 9 / 10);
                f.add(getSp());
            }
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(listener);
        }

        setViewportView(panel);
    }

    /**
     * @return the sp
     */
    public JScrollPane getSp() {
        return sp;
    }
}
