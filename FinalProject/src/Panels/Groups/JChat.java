package Panels.Groups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JChat extends JInternalFrame implements ActionListener {

    String name;
    InetAddress iadr;
    int port;
    MulticastSocket so;
    JTextArea txt = new JTextArea();
    JScrollPane sp = new JScrollPane(txt);
    JTextField write = new JTextField();

    public JChat(String username, String groupAdr, int portNr) throws IOException {
        name = username;
        iadr = InetAddress.getByName(groupAdr);
        port = portNr;
        so = new MulticastSocket(port);
        so.joinGroup(iadr);
        new Receiver(so, txt);
        txt.setEditable(false);
        add(sp, BorderLayout.CENTER);
        add(write, BorderLayout.SOUTH);
        write.addActionListener(this);
        setSize(400, 250);
        setVisible(true);
    }

    public void sendMess(String s) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        byte[] data = (name + ": " + s+"\n"+dtf.format(now)+"\n").getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, iadr, port);
        try {
            so.send(packet);
        } catch (IOException ie) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Data overflow !");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (write.getText()!="") {
            sendMess(write.getText());
            write.setText("");
        }
    }
}
