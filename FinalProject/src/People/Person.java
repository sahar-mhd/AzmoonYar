package People;

import Examination.Examination;
import java.util.ArrayList;

public abstract class Person {

    /**
     * @param examination the examination to set
     */
    public void setExamination(ArrayList<Examination> examination) {
        this.examination = examination;
    }

    public Person(String username){
        this.userName = username;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    private String firstName;
    private String lastName;
    private final String userName;
    private String password;
    
    private ArrayList<Examination> examination = new ArrayList<>();

    public Person(String userName,String password){
        this.userName = userName;
        setPassword(password);
    }
    /**
     *
     * @param firstName the firstName to set for the first time
     * @param lastName the larstName to set for the first time
     * @param password the password to set for the first time
     */
    public Person(String firstName, String lastName, String userName, String password) {
        this.userName = userName;
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
    }
    
    /**
     * 
     * @return "firstName lastName"
     */
    @Override
    public String toString(){
        return getUserName();
    }
    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public void addExamination(Examination exam){
        examination.add(exam);
    }
    
    public ArrayList<Examination> getExaminations(){
        return examination;
    }
}
