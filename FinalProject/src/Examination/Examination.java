package Examination;

import Examination.Question.*;
import People.Manager;
import People.Student;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * Manager can make Examinations including questions in 3 types : test,
 * descriptive, and true-false
 */
public class Examination {
    
    private String name;
    private boolean tartibi;
    private boolean moroor;
    private boolean allQuestionsTogether;
    
    private Date start;
    private Date stop;
    
    private Manager owner;
    private ArrayList<Question> question = new ArrayList<>();
    private ArrayList<Student> student = new ArrayList<>();
    
    public Examination(Manager owner, String name) {
        this.owner = owner;
        this.name = name;
    }
    
    public boolean removeStudent(Student stu) {
        for (Student st : student) {
            if (st.getUserName().equals(stu.getUserName())) {
                student.remove(st);
                return true;
            }
        }
        return false;
    }
    
    public String toString() {
        return name;
    }

    /**
     * @return the student
     */
    public ArrayList<Student> getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(ArrayList<Student> student) {
        this.student = student;
    }
    
    public void addStudent(Student s) {
        student.add(s);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the owner
     */
    public Manager getOwner() {
        return owner;
    }

    /**
     * @return the question
     */
    public ArrayList<Question> getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void addQuestion(Question question) {
        this.question.add(question);
    }
    
    public void setQuestion(ArrayList<Question> questions) {
        for (Question q : questions) {
            this.question.add(q);
        }
    }

    /**
     * @return the moroor
     */
    public boolean isMoroor() {
        return moroor;
    }

    /**
     * @param moroor the moroor to set
     */
    public void setMoroor(boolean moroor) {
        this.moroor = moroor;
    }

    /**
     * @return the tartib
     */
    public boolean isTartibi() {
        return tartibi;
    }

    /**
     * @param tartib the tartib to set
     */
    public void setTartibi(boolean tartib) {
        this.tartibi = tartib;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Manager owner) {
        this.owner = owner;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the stop
     */
    public Date getStop() {
        return stop;
    }

    /**
     * @param stop the stop to set
     */
    public void setStop(Date stop) {
        this.stop = stop;
    }

    /**
     * @return the allQuestionsTogether
     */
    public boolean isAllQuestionsTogether() {
        return allQuestionsTogether;
    }

    /**
     * @param allQuestionsTogether the allQuestionsTogether to set
     */
    public void setAllQuestionsTogether(boolean allQuestionsTogether) {
        this.allQuestionsTogether = allQuestionsTogether;
    }
}
