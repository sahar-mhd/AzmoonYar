package Examination.Question;

/**
 *
 * complete answer which can be a picture
 */
public class Descriptive extends Question{

    private String answer;
    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public Descriptive(String name, String question) {
        super(name, question);
    }

    @Override
    public String toString() {
        return "Des";
    }

}
