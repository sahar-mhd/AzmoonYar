package Examination.Question;

/**
 *
 * choose if it's true or false
 */
public class TrueFalse extends Question {

    private boolean answer;

    public TrueFalse(String name, String question) {
        super(name, question);
    }

    @Override
    public String toString() {
        return "TF";
    }

    /**
     * @return the answer
     */
    public boolean getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

}
