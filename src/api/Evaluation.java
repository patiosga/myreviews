package api;

import java.io.Serializable;
import java.text.*;
import java.util.Calendar;

/**
 * Η κλάση αυτή αφορά την αξιολόγηση κάποιου καταλύματος.
 */

public class Evaluation implements Serializable {
    private final String currentDate;
    private final SimpleUser user;
    private final Accommodation accommodation;
    private String evaluationText;
    private float grade; //1 έως 5
    private final long singularId;

    /**
     * Κατασκευαστής της κλάσης των αξιολογήσεων. Αρχικοποιεί τα πεδία evaluationText, grade, user, και Accommodation
     * σύμφωνα με τα ορίσματα που δίνονται. Επίσης, ορίζεται η ημερομηνία που δημιουργήθηκε το αντικείμενο της
     * αξιολόγησης ως η ημερομηνία της ίδιας της αξιολόγησης. Τέλος, υπολογίζεται το id της αξιολόγησης ως το άθροισμα
     * του hashCode() του username του χρήστη που έκανε την αξιολόγηση και του hashCode() του καταλύματος υπό
     * αξιολόγηση. Είναι μοναδικό καθώς δεν επιτρέπεται στον ίδιο χρήστη να αξιολογήσει ένα κατάλυμα πάνω απο μία φορά.
     * @param evaluationText Κείμενο αξιολόγησης του εκάστοτε καταλύματος
     * @param grade Βαθμός αξιολόγησης
     * @param user Αντικείμενο απλού χρήστη που έκανε την αξιολόγηση
     * @param accommodation Το κατάλυμα που αξιολογείται
     */
    public Evaluation(String evaluationText, float grade, SimpleUser user, Accommodation accommodation) {
        this.evaluationText = evaluationText;
        this.grade = grade;
        this.user = user;
        this.accommodation = accommodation;

        //Ορισμός της ημερομηνίας που προστίθεται η αξιολόγηση
        DateFormat Date = DateFormat.getDateInstance();
        Calendar cals = Calendar.getInstance();
        currentDate = Date.format(cals.getTime());

        singularId = user.getUserName().hashCode() + accommodation.hashCode();
    }

    /**
     * Επιστρέφει το κείμενο της αξιολόγησης
     * @return κείμενο της αξιολόγησης
     */
    public String getEvaluationText() {
        return evaluationText;
    }

    /**
     * Αλλάζει το κείμενο της αξιολόγησης σε περίπτωση μεταγενέστερης επεξεργασίας της από τον χρήστη που την έκανε.
     * @param evaluationText νέο κείμενο της αξιολόγησης
     */
    public void setEvaluationText(String evaluationText) {
        this.evaluationText = evaluationText;
    }

    /**
     * Επιστρέφει τον βαθμό της αξιολόγησης
     * @return βαθμός της αξιολόγησης
     */
    public float getGrade() {
        return grade;
    }

    /**
     * Αλλάζει τον βαθμό της αξιολόγησης σε περίπτωση μεταγενέστερης επεξεργασίας της από τον χρήστη που την έκανε.
     * @param grade νέος βαθμός αξιολόγησης
     */
    public void setGrade(float grade) {
        this.grade = grade;
    }

    /**
     * Επιστρέφει το αντικείμενο του απλού χρήστη που έκανε την αξιολόγηση
     * @return αντικείμενο του απλού χρήστη που έκανε την αξιολόγηση
     */
    public SimpleUser getUser() {
        return user;
    }

    /**
     * Επιστρέφει το αντικείμενο του καταλύματος που αξιολογείται
     * @return κατάλυμα που αξιολογείται
     */
    public Accommodation getAccommodation() {
        return accommodation;
    }

    /**
     * Επιστρέφει τη συμβολοσειρά της ημερομηνίας που έγινε η αξιολόγηση.
     * @return συμβολοσειρά ημερομηνίας
     */
    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * Επιστρέφει το id της αξιολόγησης
     * @return id αξιολόγησης
     */
    public long getSingularId() {
        return singularId;
    }

    /**
     * Ελέγχει την ισότητα δύο αντικειμένων Evaluation. Η ισότητα τους αν οι θέσεις μνήμης διαφέρουν και πρόκειται για
     * αντικείμενο τύπου Evaluation έγκειται στην ισότητα των id των δύο αξιολογήσεων.
     * @param o το αντικείμενο που θέλουμε να συγκρίνουμε με το this
     * @return true αν ισχύει η ισότητα των δύο αντικειμένων
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evaluation that)) return false;
        return getSingularId() == that.getSingularId();
    }

    /**
     * Επιστρέφει τη συμβολοσειρά που αντιστοιχεί στην αξιολόγηση ως "[μικρό όνομα χρήστη], [ημερομηνία αξιολόγησης],
     * Βαθμολογία: [βαθμός αξιολόγησης]"
     * @return συμβολοσειρά που αντιστοιχεί στην αξιολόγηση
     */
    @Override
    public String toString() {
        return user.getFirstName() + ", " + currentDate + ", Βαθμολογία: " + grade;
    }
}
