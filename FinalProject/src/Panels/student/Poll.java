package Panels.student;

import Examination.Examination;
import People.Student;
import SaveLoad.SavePoll;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Poll {

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    private final Student st;
    private final SavePoll savePoll = new SavePoll();

    private final JFrame f;

    private final JButton save = new JButton("ذخیره");
    private final ArrayList<JButton> exButton = new ArrayList<>();

    private final JScrollPane exams = new JScrollPane();
    private final JPanel examsPanel = new JPanel();

    private JScrollPane examSelected;
    private JPanel selectedPanel;

    private final ButtonGroup bg = new ButtonGroup();
    private final JRadioButton easy = new JRadioButton("آسان");
    private final JRadioButton normal = new JRadioButton("متوسط");
    private final JRadioButton hard = new JRadioButton("سخت");

    /**
     * opens a JDialog to show poll for questions of this exam
     *
     * @param st student
     * @param f JFrame
     */
    public Poll(Student st, JFrame f) {
        this.st = st;
        this.f = f;

        init();
        f.add(exams);
        exams.setBounds(f.getWidth() / 3, 0, f.getWidth() / 3, f.getHeight());
    }

    private void init() {

        examsPanel.setLayout(new BoxLayout(examsPanel, BoxLayout.Y_AXIS));

        ActionListener actionButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPanel = new JPanel();
                selectedPanel.setLayout(new BoxLayout(selectedPanel, BoxLayout.Y_AXIS));
                JButton b = (JButton) e.getSource();
                //if one button is selected others should be enabled
                exButton.stream().filter((button) -> (b != button)).forEachOrdered((button) -> {
                    button.setEnabled(true);
                });
                b.setEnabled(false);

                for (Examination s : st.getExaminations()) {
                    if (b.getName().equals(s.getName())) {
                        if (!savePoll.examIsFinished(s.getName() + " " + s.getOwner().getUserName(), st.getUserName())) {
                            JOptionPane.showMessageDialog(null, "آزمون نداده اید.", null, JOptionPane.OK_OPTION);
                        } else {
                            bg.add(easy);
                            selectedPanel.add(easy);

                            bg.add(normal);
                            selectedPanel.add(normal);

                            bg.add(hard);
                            selectedPanel.add(hard);

                            selectedPanel.add(save);
                            save.addActionListener((l) -> {

                                String view = null;
                                if (easy.isSelected()) {
                                    view = easy.getText();
                                } else if (normal.isSelected()) {
                                    view = normal.getText();
                                } else if (hard.isSelected()) {
                                    view = hard.getText();
                                }
                                if (view == null) {
                                    JOptionPane.showMessageDialog(null,
                                            "گزینه ای انتخاب نکردید.", null,
                                            JOptionPane.OK_OPTION);
                                } else {
                                    if (!savePoll.savePoll(s.getName() + " "
                                            + s.getOwner().getUserName(), view,
                                            st.getUserName())) {
                                        JOptionPane.showMessageDialog(null,
                                                "قبلا در نظر سنجی این آزمون شرکت کرده اید.",
                                                null, JOptionPane.OK_OPTION);
                                    } else {
                                        JOptionPane.showMessageDialog(null, 
                                                "بازخورد شما ذخیره شد.", null, 
                                                JOptionPane.OK_OPTION);
                                    }
                                }
                            });
                        }
                        break;
                    }
                }
                examSelected = new JScrollPane();
                examSelected.setViewportView(selectedPanel);
                f.add(examSelected);
                examSelected.setBounds(0, 0, f.getWidth() / 3, f.getHeight());
            }
        };

        int n = 0;
        for (Examination e : st.getExaminations()) {
            exButton.add(new JButton(e.getName()));
            exButton.get(n).setName(e.getName());
            exButton.get(n).addActionListener(actionButton);
            examsPanel.add(exButton.get(n));
            n++;
        }

        exams.setViewportView(examsPanel);
    }

    /**
     * @return the exams
     */
    public JScrollPane getExams() {
        return exams;
    }

    /**
     * @return the examSelected
     */
    public JScrollPane getExamSelected() {
        return examSelected;
    }

}
