package Examination.Question;

import java.sql.Time;

/**
 *
 * it is in an examination and includes 3 types : test, descriptive, and
 * true-false
 */
public abstract class Question implements Comparable<Question> {

    private String question;
    private String name;
    private int number = 0;
    private Time duration;

    public Question(String name, String question) {
        this.name = name;
        this.question = question;
    }

    public abstract String toString();

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
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

    @Override
    public int compareTo(Question o) {
        if (this.getNumber() == o.getNumber()) {
            return 0;
        } else if (this.getNumber() > o.getNumber()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * @return the duration
     */
    public Time getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Time duration) {
        this.duration = duration;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

}
