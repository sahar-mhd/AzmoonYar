package SaveLoad;

import Examination.Examination;
import Examination.Question.*;
import People.*;
import java.io.*;
import java.sql.Time;
import java.text.*;
import java.util.*;
import java.util.logging.*;

public class SaveExam {

    /**
     *
     * @param st Array of students to add to exam given
     * @param exam Examination you want to add student to
     */
    public void addStudents(ArrayList<Student> st, Examination exam) {
        if (st != null) {
            File file = new File("Exams\\" + exam.getName() + " "
                    + exam.getOwner().getUserName() + "\\students.txt");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                FileWriter fw = new FileWriter(file.getPath(), true);
                for (Student s : st) {
                    fw.write(s.getUserName() + "\r\n");
                }
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param exam Examination
     * @return Array of question in this exam
     */
    public ArrayList<Question> loadQ(Examination exam) {
        ArrayList<Question> quest = new ArrayList<>();
        File file = new File("Exams\\" + exam.getName() + " "
                + exam.getOwner().getUserName());

        for (File files : file.listFiles()) {
            if (files.isFile()) {
                if (files.getName().endsWith(" Test.txt")) {
                    readTest(files, quest);
                } else if (files.getName().endsWith(" Des.txt")) {
                    readDes(files, quest);
                } else if (files.getName().endsWith(" TF.txt")) {
                    readTF(files, quest);
                }
            }
        }
        if (quest.isEmpty()) {
            return null;
        }
        return quest;
    }

    public Examination loadExam(String name) {
        File file = new File("Exams\\" + name);
        Examination e;
        try {
            if (file.exists()) {
                String managerUser = name.substring(name.lastIndexOf(" ") + 1);

                e = new Examination(new Manager(managerUser),
                        name.subSequence(0, name.lastIndexOf(" ")).toString());
                ArrayList<Question> q = loadQ(e);
                if (q != null) {
                    e.setQuestion(q);
                }

                BufferedReader fr = new BufferedReader(new FileReader(file.getPath() + "\\settings.txt"));

                e.setStart(new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
                        .parse(fr.readLine()));
                e.setStop(new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
                        .parse(fr.readLine()));
                e.setMoroor(Boolean.parseBoolean(fr.readLine()));
                e.setTartibi(Boolean.parseBoolean(fr.readLine()));
                e.setAllQuestionsTogether(Boolean.parseBoolean(fr.readLine()));
                
                fr.close();
                return e;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param m Manager you want to get its exams
     * @return an arrayList consist of exams of this manager
     */
    public ArrayList<Examination> loadExam(Manager m) {
        ArrayList<Examination> exams = new ArrayList<>();
        File file = new File("Exams\\");

        int l = 0;

        for (File files : file.listFiles()) {
            if (files.isDirectory()) {
                if (files.getName().endsWith(" " + m.getUserName())) {
                    int n = files.getName().indexOf(" " + m.getUserName());
                    String name = files.getName().subSequence(0, n).toString();
                    exams.add(new Examination(m, name));
                    File f = new File(files.getPath() + "\\settings.txt");
                    if (f.exists()) {
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(f.getPath()));
                            exams.get(l).setStart(new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
                                    .parse(br.readLine()));
                            exams.get(l).setStop(new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
                                    .parse(br.readLine()));
                            exams.get(l).setMoroor(Boolean.getBoolean(br.readLine()));
                            exams.get(l).setTartibi(Boolean.getBoolean(br.readLine()));
                            br.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    l++;
                }
            }
        }

        return exams;
    }

    /**
     *
     * @param exam
     * @param qname name of question you are looking for
     * @return true if this name exists
     */
    public boolean questionNameExists(Examination exam, String qname) {
        File file = new File("Exams\\" + exam.toString() + " " + exam.getOwner().getUserName());

        for (File f : file.listFiles()) {
            String s = f.getName();
            if (s.contains(" ")) {
                s = s.substring(0, s.lastIndexOf(" "));
                s = s.substring(0, s.lastIndexOf(" "));
                if (s.equals(qname)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param exam
     * @param q question
     * @param managerUsername
     * @return 1 if exam does not exists, 0 if method does not work properly, 2
     * if exam folder created successfully, 3 if question exists,
     */
    public int addQ(Examination exam, Question q, String managerUsername) {
        int check = 0;
        File file = new File("Exams\\" + exam.getName() + " " + managerUsername);

        if (!file.exists()) {
            check = 1;
        } else {
            try { //questinName number type.txt
                File fq = new File("Exams\\" + exam.getName() + " "
                        + managerUsername + "\\" + q.getName() + " "
                        + q.getNumber() + " " + q.toString() + ".txt");
                if (fq.exists()) {
                    check = 3;
                } else {
                    fq.createNewFile();
                    FileWriter writer = new FileWriter(fq.getAbsolutePath());
                    writer.write(q.getQuestion() + "\r\n" + q.getDuration() + "\r\n");
                    if (q instanceof Test) {
                        writer.write(((Test) q).getSw1()
                                + "\r\n" + ((Test) q).getSw2() + "\r\n"
                                + ((Test) q).getSw3() + "\r\n" + ((Test) q).getSw4()
                                + "\r\n" + ((Test) q).getT());
                    } else if (q instanceof TrueFalse) {
                        writer.write(((TrueFalse) q).getAnswer() + "");
                    } else if (q instanceof Descriptive) {
                        writer.write(((Descriptive) q).getAnswer());
                    }
                    writer.close();
                    check = 2;
                }
            } catch (IOException ex) {
                Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }

    /**
     * creates a new folder named : examName managerName
     *
     * @param e examination
     * @param managerUserName
     * @return 1 if method does not work properly, 0 if name exists, 2 if folder
     * created successfully
     */
    public int add(Examination e, String managerUserName) {

        int check = 1;

        String fileName = e.toString() + " " + managerUserName;
        File file = new File("Exams\\" + fileName);

        if (file.exists()) {
            check = 0;
        } else {
            file.mkdir();
            File settings = new File(file.getPath() + "\\settings.txt");
            File students = new File(file.getPath() + "\\students.txt");
            try {
                settings.createNewFile();
                students.createNewFile();
                FileWriter fw = new FileWriter(settings.getPath());
                fw.write(e.getStart() + "\r\n" + e.getStop() + "\r\n"
                        + e.isMoroor() + "\r\n" + e.isTartibi());//false : moroor - tartib soalat
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
            }
            check = 2;
        }
        return check;
    }

    public void changeSettings(Manager m, Examination e) {
        try {
            FileWriter fw = new FileWriter("Exams\\" + e.toString() + " "
                    + m.getUserName() + "\\settings.txt", false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            fw.write(e.getStart() + "\r\n" + e.getStop() + "\r\n"
                    + e.isMoroor() + "\r\n" + e.isTartibi() + "\r\n"
                    + e.isAllQuestionsTogether());
            fw.close();
        } catch (IOException u) {
            System.out.println("Something went horribly wrong: " + u.getMessage());
        }

    }

    private void readTF(File files, ArrayList<Question> quest) {
        try {
            int n = files.getName().indexOf(" TF.txt");
            String name = files.getName().substring(0, n);
            int number = Integer.parseInt(name.substring(name.lastIndexOf(" ") + 1));
            name = name.substring(0, name.lastIndexOf(" "));

            BufferedReader f = new BufferedReader(new FileReader(files.getPath()));
            String line;
            //question
            line = f.readLine();
            TrueFalse t = new TrueFalse(name, line);
            //start time
            line = f.readLine();
            Time time = Time.valueOf(line);
            t.setDuration(time);
            //answer
            line = f.readLine();
            //number
            t.setNumber(number);
            if (line.equals("true")) {
                t.setAnswer(true);
            } else if (line.equals("false")) {
                t.setAnswer(false);
            }
            quest.add(t);
            f.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readDes(File files, ArrayList<Question> quest) {
        try {
            int n = files.getName().indexOf(" Des.txt");
            String name = files.getName().subSequence(0, n).toString();
            int number = Integer.parseInt(name.substring(name.lastIndexOf(" ") + 1));
            name = name.substring(0, name.lastIndexOf(" "));

            BufferedReader f = new BufferedReader(new FileReader(files.getPath()));
            String line;
            //question
            line = f.readLine();
            Descriptive t = new Descriptive(name, line);
            //start time
            line = f.readLine();
            Time time = Time.valueOf(line);
            t.setDuration(time);
            //answer
            line = f.readLine();
            t.setAnswer(line);
            //number
            t.setNumber(number);
            quest.add(t);
            f.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readTest(File files, ArrayList<Question> quest) {
        try {
            int n = files.getName().indexOf(" Test.txt");
            String name = files.getName().subSequence(0, n).toString();
            int number = Integer.parseInt(name.substring(name.lastIndexOf(" ") + 1));
            name = name.substring(0, name.lastIndexOf(" "));

            BufferedReader f = new BufferedReader(new FileReader(files.getPath()));
            String line;
            //question
            line = f.readLine();
            Test t = new Test(name, line);
            //start time
            line = f.readLine();
            Time time = Time.valueOf(line);
            t.setDuration(time);
            //sw1
            line = f.readLine();
            t.setSw1(line);
            //sw2
            line = f.readLine();
            t.setSw2(line);
            //sw3
            line = f.readLine();
            t.setSw3(line);
            //sw4
            line = f.readLine();
            t.setSw4(line);
            //answer
            line = f.readLine();
            t.setT(line);
            //number
            t.setNumber(number);
            quest.add(t);
            f.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveExam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
