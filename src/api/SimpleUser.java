package api;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Η κλάση αυτή αφορά τους απλούς χρήστες.
 */

public class SimpleUser extends User implements Serializable {
    private float avgRating; //πρέπει να ενημερώνεται κάθε φορά που κάνει ο χρήστης νέα αξιολόγηση ή αφαιρείται μια παλιά του

    public SimpleUser(String firstName, String lastName ,String userName, String password, String type) {
        super(firstName,lastName, userName, password, type);
        avgRating = 0;
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την ενημέρωση του μέσου όρου των βαθμολογήσεων ενός χρήστη.
     * @param evaluations Η λίστα των αξιολογήσεων.
     */

    public void updateAvgRatingOfUser(HashSet<Evaluation> evaluations) {
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
     *
     * @return Τον μέσο όρο βαθμολογιών.
     */
    public float getAvgRating() {
        return avgRating;
    }
}
