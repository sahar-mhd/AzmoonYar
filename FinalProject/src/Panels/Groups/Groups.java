package Panels.Groups;

import SaveLoad.Loader;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Groups extends JScrollPane {

    private Loader loader;
    private JChat chat;
    private ArrayList<String> exams;

    private JButton[] buttons;
    private JPanel panel;
    private JButton exit;
    private ActionListener listener;

    public void initialize(String username, JButton source, String mode, JFrame f) {
        loader = new Loader();
        panel = new JPanel();
        exit = new JButton("بازگشت");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        exams = new ArrayList<>();
        exams = loader.load(username, mode);
        buttons = new JButton[exams.size()];
        for (int i = 0; i < exams.size(); i++) {
            buttons[i] = new JButton(exams.get(i) + "گروه آزمون");
            panel.add(buttons[i]);
        }
        exit.addActionListener((e) -> {
            getChat().setVisible(false);
            setVisible(false);
            source.setEnabled(true);
        });
        panel.add(exit);

        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(chat!=null){
                        chat.setVisible(false);
                    }
                    JButton button = (JButton) e.getSource();
                    chat = new JChat(username, "224.0.0.7", 9806);
                    chat.setLocation(0, 0);
                    chat.setSize(f.getWidth()/3,f.getHeight()*11/12);
                    chat.setTitle(button.getText());
                    chat.setClosable(true);
                    f.add(chat);
                    f.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(Groups.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(listener);
        }

        setViewportView(panel);
    }

    /**
     * @return the chat
     */
    public JChat getChat() {
        return chat;
    }
}
