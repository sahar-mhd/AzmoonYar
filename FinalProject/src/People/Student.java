package People;

/**
 *
 * This is the type of person that can attend in tests , send answers , ّand get
 * results
 */
public class Student extends Person implements Comparable<Student> {

    private String studentId;
    private double grade;

    public Student(String userName, String password) {
        super(userName, password);
    }

    public Student(String firstName, String lastName, String userName, String password, String studentId) {
        super(firstName, lastName, userName, password);
        this.studentId = studentId;
    }

    /**
     * @return the studentId
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public int compareTo(Student o) {
        if (this.getGrade() == o.getGrade()) {
            return 0;
        } else if (this.getGrade() < o.getGrade()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * @return the grade
     */
    public double getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

}
