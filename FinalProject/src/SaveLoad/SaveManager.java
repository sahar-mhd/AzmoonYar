package SaveLoad;

import Examination.Examination;
import People.*;
import java.io.*;

/**
 *
 * saves and loads manager's information
 */
public class SaveManager {

    /**
     *
     * @param exam
     * @param e
     * @param m
     */
    public boolean addExam(Examination exam, Manager m) throws IOException {
        File file = new File("ManagersList\\" + m.toString() + ".txt");
        if (file.exists()) {
            try {
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(file.getAbsolutePath(), true) //Set true for append mode
                );
                writer.newLine();   //Add new line
                writer.write(exam.getName());
                writer.close();
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                System.out.println("Error initializing");
            }
        }
        return false;
    }

    /**
     *
     * @param manager
     * @param mode
     * @return 0 if username and password are correct, 1 if password is
     * incorrect 2 if username is not registered, 3 if function couldn't work
     * properly, 4 if register failed because username already exists, 5 if
     * register succeed
     */
    public int add(Manager manager, String mode) throws ClassNotFoundException {

        int check = 3;

        String fileName = manager.toString() + ".txt";
        File filem = new File("ManagersList\\" + fileName);

        //write to file
        if (mode.equals("register")) {
            if (filem.exists()) {
                check = 4;
            } else {
                try {
                    FileWriter file = new FileWriter(filem.getPath());
                    file.write(manager.getPassword() + "\r\n"
                            + manager.getFirstName() + "\r\n"
                            + manager.getLastName());
                    file.close();
                    check = 5;
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                } catch (IOException e) {
                    System.out.println("Error initializing");
                }
            }
        } else {
            if (!filem.exists()) {
                check = 2;
            } else {
                //read from file
                try {
                    BufferedReader file = new BufferedReader(new FileReader(filem.getPath()));
                    String line;
                    line = file.readLine();
                    if (line.equals(manager.getPassword())) {
                        manager.setFirstName(file.readLine());
                        manager.setLastName(file.readLine());

                        SaveExam saveExam = new SaveExam();
                        manager.setExamination(saveExam.loadExam(manager));
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
}
