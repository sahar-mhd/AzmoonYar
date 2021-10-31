package SaveLoad;

import Examination.Examination;
import Examination.Question.*;
import People.Student;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class SaveAnswers {

    /**
     *
     * @param st student who is answering
     * @param exam exam which student is answering
     * @param question question which is being answered
     * @param answer String answer
     * @param mode if question type is descriptive and mode is "img" student is
     * uploading an image
     * @param answ image that is being uploaded if mode is "img"
     */
    public void saveAnswer(Student st, Examination exam, Question question,
            String answer, String mode, BufferedImage answ) {
        File file = new File("Exams\\" + exam.getName() + " "
                + exam.getOwner().getUserName() + "\\Answers");
        file.mkdir();
        
        file = new File(file.getPath() + "\\" + st.getUserName());
        file.mkdir();
        if (mode.equals("img") && answ != null && question instanceof Descriptive) {
            file = new File(file.getPath() + "\\" + question.getName() + " " + question + ".jpg");
        } else {
            file = new File(file.getPath() + "\\" + question.getName() + " " + question + ".txt");
        }
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(SaveAnswers.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Question q : exam.getQuestion()) {
            if (q.equals(question)) {
                if (q instanceof Test) {
                    try {
                        FileWriter fw = new FileWriter(file.getPath());
                        Boolean sw = ((Test) q).getT().equals(answer);
                        fw.write(sw.toString() + "\r\n");
                        fw.write(answer);
                        fw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(SaveAnswers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (q instanceof TrueFalse) {
                    if (answer != null) {
                        try {
                            FileWriter fw = new FileWriter(file.getPath());
                            Boolean sw = (((TrueFalse) q).getAnswer() == Boolean.getBoolean(answer));
                            fw.write(sw.toString() + "\r\n");
                            fw.write(answer);
                            fw.close();
                        } catch (IOException ex) {
                            Logger.getLogger(SaveAnswers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (q instanceof Descriptive) {
                    try {
                        if (mode.equals("img") && answ != null) {
                            ImageIO.write(answ, "jpg", file);
                        } else if (answer != null) {
                            FileWriter fw = new FileWriter(file.getPath());
                            fw.write(answer);
                            fw.close();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(SaveAnswers.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
        }
    }

    /**
     *
     * @param examName
     * @param managerUsername
     * @param studentUsername
     * @return true if student saved answer on this examination
     */
    public boolean searchAnswer(String examName, String managerUsername, String studentUsername) {
        File file = new File("Exams\\" + examName +" "
                + managerUsername + "\\Answers\\" + studentUsername);
        if (file.exists()) {
            return true;
        }
        return false;
    }
    
    public ArrayList<Object> loadAnswers(String studentUsername, String examName,
            String managerUsername, int width, int height) {
        ArrayList<Object> object = new ArrayList<>();
        
        File file = new File("Exams\\" + examName + " " + managerUsername
                + "\\Answers\\" + studentUsername);
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (f.getName().endsWith(" Des.txt") || f.getName().endsWith(" Des.jpg")) {
                    object.add(f.getName().substring(0, f.getName().lastIndexOf(" ")));
                    descriptiveLoad(f, width, height).forEach((o) -> {
                        object.add(o);
                    });
                }
            }
        }
        return object;
    }
    
    public void saveGrade(String studentUsername, ArrayList<Double> grades,
            String examFileName, ArrayList<String> questionName) {
        File file = new File("Exams\\" + examFileName + "\\Answers\\"
                + studentUsername + "\\" + "grade.txt");
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < questionName.size(); i++) {
                bf.append(questionName.get(i) + "\r\n" + grades.get(i) + "\r\n");
            }
            bf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveAnswers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveAnswers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Object> loadAnswers(String examFilename, String studentUsername,
            int width, int height) {
        ArrayList<Object> ans = new ArrayList<>();
        File file = new File("Exams\\" + examFilename + "\\Answers\\" + studentUsername);
        for (File f : file.listFiles()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                if (f.getName().endsWith(" TF.txt")) {
                    ans.add(f.getName().substring(0, f.getName().lastIndexOf(" ")));
                    br.readLine();
                    String line;
                    StringBuffer sb = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    ans.add(sb);
                } else if (f.getName().endsWith(" Test.txt")) {
                    ans.add(f.getName().substring(0, f.getName().lastIndexOf(" ")));
                    br.readLine();
                    String line;
                    StringBuffer sb = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    ans.add(sb);
                } else if (f.getName().endsWith(" Des.txt") || f.getName().endsWith(" Des.jpg")) {
                    ans.add(f.getName().substring(0, f.getName().lastIndexOf(" ")));
                    descriptiveLoad(f, width, height).forEach((o) -> {
                        ans.add(o);
                    });
                }
                br.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SaveAnswers.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SaveAnswers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return ans;
    }
    
    private ArrayList<Object> descriptiveLoad(File f, int width, int height) {
        ArrayList<Object> object = new ArrayList<>();
        if (f.getName().endsWith(" Des.txt")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                StringBuffer s = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    s.append(line);
                }
                object.add(s);
                br.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SaveAnswers.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SaveAnswers.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (f.getName().endsWith(" Des.jpg")) {
            JLabel link = new JLabel("پاسخ به صورت عکس");
            
            ImageIcon img;
            img = new ImageIcon("jpg.png");
            if (width / 32 != 0 && height / 12 != 0) {
                Image temp = img.getImage().getScaledInstance(width / 32,
                        height / 12, Image.SCALE_DEFAULT);
                img.setImage(temp);
            }
            
            link.setIcon(img);
            link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            link.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().open(f);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            object.add(link);
        }
        return object;
    }
}
