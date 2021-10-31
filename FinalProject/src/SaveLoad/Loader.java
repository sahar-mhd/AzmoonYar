package SaveLoad;

import People.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.*;

public class Loader {

    private ArrayList<String> exams;
    private ArrayList<Double> grades;

    public ArrayList load(String username, String mode) {
        exams = new ArrayList<>();
        File file;
        if (mode.equals("m")) {
            file = new File("managersList\\" + username + ".txt");
        } else {
            file = new File("studentsList\\" + username + ".txt");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st = new String();
            br.readLine();
            br.readLine();
            br.readLine();
            if (mode.equals("s")) {
                br.readLine();
            }
            while ((st = br.readLine()) != null) {
                exams.add(st);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exams;
    }

    public ArrayList studentsInExam(String name) {
        ArrayList<String> students = new ArrayList<>();
        File f = new File("Exams\\" + name + "\\students.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String str = br.readLine();
            while (str != null) {
                students.add(str);
                str = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return students;
    }

    /**
     *
     * @param name
     * @param mode
     * @return grades if mode is "return",else null
     */
    public ArrayList WholeGrades(String name, String mode) {
        ArrayList<String> susernames = studentsInExam(name);
        grades = new ArrayList<>();
        for (int j = 0; j < susernames.size(); j++) {
            File f = new File("Exams\\" + name + "\\Answers\\" + susernames.get(j) + "\\");
            File file = new File("Exams\\" + name);
            int count = 0;
            for (File fi : file.listFiles()) {
                if (fi.getName().endsWith("Des.txt") || fi.getName().endsWith("Test.txt") || fi.getName().endsWith("TF.txt")) {
                    count++;
                }
            }
            double sum = 0;
            File[] files = f.listFiles();
            BufferedReader br;
            try {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().contains("TF.txt") || files[i].getName().contains("Test.txt")) {
                        br = new BufferedReader(new FileReader(files[i]));
                        if (br.readLine().equals("true")) {
                            sum += 20;
                        }
                    } else if (files[i].getName().equals("grade.txt")) {
                        Scanner scanner = new Scanner(files[i]);
                        while (scanner.hasNext()) {
                            if (scanner.hasNextDouble()) {
                                sum += scanner.nextDouble();
                            } else {
                                scanner.next();
                            }
                        }
                    }
                }
                grades.add(sum / count);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        File file = new File("Exams\\" + name + "\\" + "wholeGrades.txt");
        try {
            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < susernames.size(); i++) {
                fw.write(susernames.get(i) + "\r\n" + grades.get(i) + "\r\n");
            }
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (mode.equals("return")) {
            return grades;
        } else {
            return null;
        }
    }

    public ArrayList AvarageGrades(ArrayList<String> examName, String musername) {
        for (int i = 0; i < examName.size(); i++) {
            String name = examName.get(i) + " " + musername;
            WholeGrades(name, "");
        }
        ArrayList<Double> avarages = new ArrayList<>();
        for (int i = 0; i < examName.size(); i++) {
            String name = examName.get(i) + musername;
            ArrayList<String> susernames = studentsInExam(name);
            int count = susernames.size();
            double sum = 0;
            File f = new File("Exams\\" + examName.get(i) + " " + musername + "\\" + "wholeGrades.txt");
            try {
                Scanner scanner = new Scanner(f);
                for (int k = 0; scanner.hasNext(); k++) {
                    if (scanner.hasNextDouble() && k % 2 != 0) {
                        sum += scanner.nextDouble();
                    } else {
                        scanner.next();
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            }
            avarages.add(sum / count);
        }
        return avarages;
    }

    public ArrayList deletedstudents(String examName, String musername) {
        File file = new File("Exams\\" + examName + " " + musername + "\\" + "deleted.txt");
        ArrayList<String> deleted = new ArrayList<>();
        try {
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String str;
                str = br.readLine();
                while (str != null) {
                    deleted.add(str);
                    str = br.readLine();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleted;
    }

    public int[] pollResults(String examName, String musername) {
        File file = new File("Exams\\" + examName + " " + musername + "\\" + "poll.txt");
        int[] amount = new int[3];
        try {
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String str;
                str = br.readLine();
                while (str != null) {
                    if (str.equals("ساده")) {
                        amount[0]++;
                    } else if (str.equals("متوسط")) {
                        amount[1]++;
                    } else {
                        amount[2]++;
                    }
                    str = br.readLine();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return amount;
    }

    public boolean graded(String examName, String susername) {
        File file = new File("Exams\\" + examName + "\\Answers\\" + susername + "\\grade.txt");
        return file.exists();
    }

    public String getgrade(String examName, String susername) {
        File file = new File("Exams\\" + examName + "\\" + "wholeGrades.txt");
        if (!(file.exists())) {
            WholeGrades(examName, "");
        }
        String str = new String();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            str = br.readLine();
            while (str != null) {
                if (str.equals(susername)) {
                    str = br.readLine();
                    break;
                }
                str = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    public ArrayList gradeofeachQ(String examName, String susername) {
        ArrayList<String> st = new ArrayList<>();
        File f = new File("Exams\\" + examName + "\\Answers\\" + susername + "\\");
        File[] files = f.listFiles();
        BufferedReader br;
        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains("TF.txt")) {
                    br = new BufferedReader(new FileReader(files[i]));
                    if (br.readLine().equals("true")) {
                        st.add(files[i].getName().substring(0, files[i].getName().length() - 6) + ": 20");
                    } else {
                        st.add(files[i].getName().substring(0, files[i].getName().length() - 6) + ": 0");
                    }
                } else if (files[i].getName().contains("Test.txt")) {
                    br = new BufferedReader(new FileReader(files[i]));
                    if (br.readLine().equals("true")) {
                        st.add(files[i].getName().substring(0, files[i].getName().length() - 8) + ": 20");
                    } else {
                        st.add(files[i].getName().substring(0, files[i].getName().length() - 8) + ": 0");
                    }
                } else if (files[i].getName().equals("grade.txt")) {
                    br = new BufferedReader(new FileReader(files[i]));
                    String str = br.readLine();
                    while (str != null) {
                        st.add(str + " : " + br.readLine());
                        str = br.readLine();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return st;
    }

    /**
     *
     * @param examName
     * @param username
     * @return object containing int,double
     */
    public Object[] getDegree(String examName, String username) {
        ArrayList<String> susernames = studentsInExam(examName);
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < susernames.size(); i++) {
            students.add(new Student(susernames.get(i), susernames.get(i)));
        }
        File file = new File("Exams\\" + examName + "\\" + "wholeGrades.txt");
        try {
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNext()) {
                if (scanner.hasNextDouble()) {
                    students.get(i).setGrade(scanner.nextDouble());
                    i++;
                } else {
                    scanner.next();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        students.sort((d1, d2) -> d1.compareTo(d2));
        int i;
        for (i = 0; i < students.size(); i++) {
            if (students.get(i).getUserName().equals(username)) {
                break;
            }
        }
        double a = 0;
        a = students.stream().map((s) -> s.getGrade()).reduce(a,
                (accumulator, _item) -> accumulator + _item);
        Object[] o = {i + 1, a / students.size()};
        return o;
    }
}
