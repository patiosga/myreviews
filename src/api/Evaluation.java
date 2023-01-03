package api;

import java.io.Serializable;
import java.text.*;
import java.util.Calendar;

/**
 * Η κλάση αυτή αφόρα την αξιολόγηση κάποιου καταλύματος.
 */

public class Evaluation implements Serializable {
    private final String currentDate;
    private final SimpleUser user;
    private final Accommodation accommodation;
    private String evaluationText;
    private float grade; //1 έως 5
    private final long singularId;

    public Evaluation(String evaluationText, float grade, SimpleUser user, Accommodation accommodation) {
        this.evaluationText = evaluationText;
        this.grade = grade;
        this.user = user;
        this.accommodation = accommodation;
        //Βαθιά αντιγραφή!(?)
//        this.user = new SimpleUser(user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword(), "simpleUser");
//        this.accommodation = new Accommodation(accommodation.getName(), accommodation.getDescription(), accommodation.getStayType(), accommodation.getLocation(), accommodation.getProvider());

        //Ορισμός της ημερομηνίας που προστίθεται η αξιολόγηση
        DateFormat Date = DateFormat.getDateInstance();
        Calendar cals = Calendar.getInstance();
        currentDate = Date.format(cals.getTime());

        singularId = user.hashCode() + accommodation.hashCode() + (long) grade;
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

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public long getSingularId() {
        return singularId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evaluation that)) return false;
        return getSingularId() == that.getSingularId();
    }

    @Override
    public String toString() {
        return user.getFirstName() + ", " + currentDate + ", Βαθμολογία: " + grade;
    }
}
