package api;

public class Evaluation {
    //πρέπει να προστεθεί ημερομηνία!!!
    private final SimpleUser user;
    private final Accomodation accomodation;
    private String evaluationText;
    private float grade;


    public Evaluation(String evaluationText, float grade, SimpleUser user, Accomodation accomodation) {
        this.evaluationText = evaluationText;
        this.grade = grade;
        this.user = user;
        this.accomodation = accomodation;
    }

    public String getEvaluationText() {
        return evaluationText;
    }

    public void setEvaluationText(String evaluationText) {
        this.evaluationText = evaluationText;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public SimpleUser getUser() {
        return user;
    }

    public Accomodation getAccomodation() {
        return accomodation;
    }

    public void display() {
        //
    }
}
