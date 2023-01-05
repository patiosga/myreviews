package api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Κλάση απλού χρήστη.
 */
public class SimpleUser extends User implements Serializable {
    private float avgRating;
    //Μέση βαθμολογία που έχει δώσει ο χρήστης σε όλες τις αξιολογήσεις του.
    //Πρέπει να ενημερώνεται κάθε φορά που κάνει ο χρήστης υποβάλλει νέα αξιολόγηση ή αφαιρείται μια παλιά του.

    /**
     * Κατασκευαστής της κλάσης SimpleUser. Αρχικοποιεί το αντικείμενο του απλού χρήστη θέτοντας τα πεδία firstName,
     * lastName, username και password ανάλογα με τα ορίσματα που δίνονται αξιοποιώντας τον κατασκευαστή της abstract
     * κλάσης User. Επίσης, αρχικοποιεί τη μέση βαθμολογία
     * των αξιολογήσεων του χρήστη στο 0.
     * @param firstName μικρό όνομα απλού χρήστη
     * @param lastName επίθετο απλού χρήστη
     * @param userName username απλού χρήστη
     * @param password κωδικός χρήστης
     * @param type τύπος χρήστη --> simpleUser
     */
    public SimpleUser(String firstName, String lastName ,String userName, String password, String type) {
        super(firstName,lastName, userName, password, type);
        avgRating = 0;
    }

    /**
     * Υπολογίζει τη μέση βαθμολογία όλων των αξιολογήσεων του χρήστη. Χρησιμοποιείται όταν προστίθεται νέα αξιολόγηση
     * από τον ίδιο χρήστη.
     * @param evaluations Η λίστα όλων των αξιολογήσεων για όλα τα καταλύματα
     */
    public void updateAvgRatingOfUser(ArrayList<Evaluation> evaluations) {
        if (evaluations.size() == 0) {
            avgRating = 0;
            return;
        }
        float totalSum = 0;
        int numOfEvaluations = 0;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getUser().getUserName().equals(this.getUserName())) {
                totalSum += evaluation.getGrade();
                numOfEvaluations++;
            }
        }
        if (numOfEvaluations == 0) {
            avgRating = 0;
            return;
        }
        avgRating = totalSum / numOfEvaluations;
    }

    /**
     * Επιστρέφει τη μέση βαθμολογία που έχει δώσει ο χρήστης σε όλες τις αξιολογήσεις του
     * @return μέση βαθμολογία που έχει δώσει ο χρήστης σε όλες τις αξιολογήσεις του
     */
    public float getAvgRating() {
        return avgRating;
    }
}
