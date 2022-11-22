package api;

import java.text.*;
import java.util.Calendar;

public class Evaluation {
    private String currentDate;
    private final SimpleUser user;
    private final Accomodation accomodation;
    private String evaluationText;
    private float grade; //1 έως 5 --> μάλλον χρειάζεται κάτι σε exception αλλά θα δούμε μήπως απλά περιορίσουμε τις επιλογές στο gui


    public Evaluation(String evaluationText, float grade, SimpleUser user, Accomodation accomodation) {
        this.evaluationText = evaluationText;
        this.grade = grade;
        this.user = user;
        this.accomodation = accomodation;

        //Ορισμός της ημερομηνίας που προστίθεται η αξιολόγηση
        DateFormat Date = DateFormat.getDateInstance();
        Calendar cals = Calendar.getInstance();
        currentDate = Date.format(cals.getTime());

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

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }


    public void display() {
        //
    }
}
