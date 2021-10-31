package SaveLoad;

import Examination.Examination;
import People.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.*;

/**
 *
 * saves and loads student's information
 */
public class SaveStudent {

    /**
     *
     * @param exam
     * @param username
     * @return true if student with this username exists false if not
     */
    public boolean search(Examination exam, String username) {
        File file = new File("Exams\\" + exam.getName() + " "
                + exam.getOwner().getUserName() + "\\students.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file.getPath()));

            String line;

            while ((line = br.readLine()) != null) {
                if (line.equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param exam
     * @param username of student manager wants to remove
     * @return true if student removed from exam
     */
    public boolean removeStudentOfExam(Examination exam, ArrayList<String> username) {
        String filename = "Exams\\" + exam.getName() + " "
                + exam.getOwner().getUserName() + "\\students.txt";

        File file = new File("Exams\\" + exam.getName() + " "
                + exam.getOwner().getUserName() + "\\deleted.txt");

        for (String use : username) {
            File student = new File("StudentsList\\" + use + ".txt");

            if (student.exists()) {
                try {
                    StringBuffer sb = new StringBuffer();
                    BufferedReader br = new BufferedReader(new FileReader(student));
                    for (int i = 0; i < 4; i++) {
                        sb.append(br.readLine());
                        sb.append("\r\n");
                    }
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.equals(exam.getName() + " " + exam.getOwner().getUserName())) {
                            sb.append(line);
                            sb.append("\r\n");
                        }
                    }

                    br.close();

                    BufferedWriter bw = new BufferedWriter(new FileWriter(student));
                    bw.write(sb.toString());
                    bw.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SaveStudent.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SaveStudent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(SaveStudent.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuffer removed = new StringBuffer("");

            for (String s : username) {
                removed.append(s);
                removed.append("\r\n");
            }

            //String buffer to store contents of the file
            StringBuffer sb = new StringBuffer("");

            String line;
            boolean sw = false;

            while ((line = br.readLine()) != null) {
                //Store each valid line in the string buffer
                for (String str : username) {
                    if (line.equals(str)) {
                        sw = true;
                        break;
                    }
                }
                if (!sw) {
                    sb.append(line + "\r\n");
                }
                sw = false;
            }
            br.close();

            FileWriter fw = new FileWriter(new File(filename));
            //Write entire string buffer into the file
            fw.write(sb.toString());
            fw.close();

            BufferedWriter fw1 = new BufferedWriter(new FileWriter(file));
            fw1.write(removed.toString());
            fw1.close();
            return true;
        } catch (Exception e) {
            System.out.println("Something went horribly wrong: " + e.getMessage());
        }
        return false;
    }

    /**
     *
     * @param st
     * @param mode
     * @param exam
     * @return 0 if username and password are correct, 1 if password is
     * incorrect, 2 if username is not registered, 3 if function couldn't work
     * properly, 4 if register failed because username already exists, 5 if
     * register succeed
     */
    public int add(Student st, String mode, Examination exam) {

        int check = 3;

        String fileName = st.toString() + ".txt";
        File files = new File("StudentsList\\" + fileName);

        //write to file
        if (mode.equals("register")) {
            if (files.exists()) {
                check = 4;
            } else {
                try {
                    FileWriter file = new FileWriter(files.getPath());
                    file.write(st.getPassword() + "\r\n"
                            + st.getFirstName() + "\r\n"
                            + st.getLastName() + "\r\n"
                            + st.getStudentId());
                    if (exam != null) {
                        file.write("\r\n" + exam.getName() + " " + exam.getOwner().getUserName());
                    }
                    file.close();
                    check = 5;
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                } catch (IOException e) {
                    System.out.println("Error initializing");
                }
            }
        } else {
            if (!files.exists()) {
                check = 2;
            } else {
                //read from file
                try {
                    BufferedReader file = new BufferedReader(new FileReader(files.getPath()));
                    String line;
                    line = file.readLine();
                    if (line.equals(st.getPassword())) {
                        st.setFirstName(file.readLine());
                        st.setLastName(file.readLine());
                        st.setStudentId(file.readLine());
                        while ((line = file.readLine()) != null) {
                            st.addExamination(new SaveExam().loadExam(line));
                        }
                        check = 0;
                    } else {
                        check = 1;
                    }
                    file.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                } catch (IOException e) {
                    System.out.println("Error initializing stream");
                }
            }
        }
        return check;
    }

    /**
     *
     * @param addressofexamfile Exams\\nameOfExam managerUsername
     * @return students username of this exam
     */
    public ArrayList<String> StudentsOfExam(String addressofexamfile) {
        ArrayList<String> student = new ArrayList<>();
        File file = new File(addressofexamfile + "\\students.txt");
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line;
                while ((line = br.readLine()) != null) {
                    student.add(line);
                }
                br.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SaveStudent.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SaveStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return null;
        }
        return student;
    }

    /**
     *
     * @param st Student
     * @param exam
     * @return 0 if student exists and information is true, 1 if student exists
     * and information is not true, 2 if student does not exist and added
     * successfully, 3 if function does not work properly, 4 if this student had
     * this exam
     */
    public int addStudentToExam(Student st, Examination exam) {
        int check = 3;
        File file = new File("StudentsList\\" + st.getUserName() + ".txt");
        if (file.exists()) {
            try {
                Scanner read = new Scanner(file);
                read.nextLine();
                if (st.getFirstName().equals(read.nextLine())
                        && st.getLastName().equals(read.nextLine())
                        && st.getStudentId().equals(read.nextLine())) {

                    while(read.hasNextLine()){
                        if(read.nextLine().equals(exam.getName()+" "+exam.getOwner().getUserName())){
                            return 4;
                        }
                    }
                    FileWriter fw = new FileWriter(file, true);
                    fw.write("\r\n" + exam.getName() + " " + exam.getOwner().getUserName());
                    fw.close();

                    check = 0;
                } else {
                    check = 1;
                }
                read.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SaveStudent.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SaveStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(file, false);

                fw.write(st.getPassword() + "\r\n");
                fw.write(st.getFirstName() + "\r\n");
                fw.write(st.getLastName() + "\r\n");
                fw.write(st.getStudentId() + "\r\n");
                fw.write(exam.getName() + " " + exam.getOwner().getUserName());

                fw.close();
                check = 2;
            } catch (IOException ex) {
                Logger.getLogger(SaveStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }
}
