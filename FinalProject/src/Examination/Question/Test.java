package Examination.Question;

/**
 *
 * 4 choices to choose
 */
public class Test extends Question {

    private String T; //true
    private String sw1;
    private String sw2;
    private String sw3;
    private String sw4;

    public Test(String name, String question) {
        super(name, question);
    }

    /**
     * @return the sw1
     */
    public String getSw1() {
        return sw1;
    }

    /**
     * @param sw1 the sw1 to set
     */
    public void setSw1(String sw1) {
        this.sw1 = sw1;
    }

    /**
     * @return the sw2
     */
    public String getSw2() {
        return sw2;
    }

    /**
     * @param sw2 the sw2 to set
     */
    public void setSw2(String sw2) {
        this.sw2 = sw2;
    }

    /**
     * @return the sw3
     */
    public String getSw3() {
        return sw3;
    }

    /**
     * @param sw3 the sw3 to set
     */
    public void setSw3(String sw3) {
        this.sw3 = sw3;
    }

    /**
     * @return the sw4
     */
    public String getSw4() {
        return sw4;
    }

    /**
     * @param sw4 the sw4 to set
     */
    public void setSw4(String sw4) {
        this.sw4 = sw4;
    }

    @Override
    public String toString() {
        return "Test";
    }

    /**
     * @return the T
     */
    public String getT() {
        return T;
    }

    /**
     * @param T the T to set
     */
    public void setT(String T) {
        if (T.equals(sw1) || T.equals(sw2) || T.equals(sw3) || T.equals(sw4)) {
            this.T = T;
        } else {
            throw new RuntimeException("no choice like input");
        }
    }

}
