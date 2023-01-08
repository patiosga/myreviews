package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * Η κλάση αυτή αφορά τους παρόχους καταλυμάτων.
 */
public class Provider extends User implements Serializable {
    /**
     * Συνολικές αξιολογήσεις καταλύματος. Πρέπει να ενημερώνεται κάθε φορά που προστίθεται ή αφαιρείται αξιολόγηση
     */
    private int totalRatings;
    private float avgRatingOfAllAccom;

    /**
     * Κατασκευαστής της κλάσης Provider. Αρχικοποιεί το αντικείμενο του παρόχου θέτοντας τα πεδία firstName, lastName,
     * username και password ανάλογα με τα ορίσματα που δίνονται αξιοποιώντας τον κατασκευαστή της abstract
     * κλάσης User. Επίσης, αρχικοποιούνται η μέση βαθμολογία των καταλυμάτων του παρόχου και ο συνολικός αριθμός των
     * αξιολογήσεων τους στο 0.
     * @param firstName μικρό όνομα παρόχου
     * @param lastName επίθετο παρόχου
     * @param userName username παρόχου
     * @param password κωδικός χρήστης
     * @param type τύπος χρήστη --> provider
     */
    public Provider(String firstName,String lastName,String userName, String password, String type) {
        super(firstName,lastName,userName, password, type);
        totalRatings = 0;
        avgRatingOfAllAccom = 0;
    }

    /**
     * Υπολογίζει τη μέση βαθμολογία όλων των καταλυμάτων του παρόχου. Χρησιμοποιείται όταν προστίθεται νέα αξιολόγηση
     * σε κατάλυμα του παρόχου.
     * @param evaluations Η λίστα όλων των αξιολογήσεων για όλα τα καταλύματα
     */
    public void updateAvgRatingOfAllAccom(ArrayList<Evaluation> evaluations) { //πρέπει να καλείται μετά τη μέθοδο που ενημερώνει το κατάλυμα μετά από προσθήκη ή αφαίρεση αξιολόγησης
        if (evaluations.size() == 0) {
            avgRatingOfAllAccom = 0;
            totalRatings = 0;
            return;
        }
        float totalSum = 0;
        int numOfEvaluations = 0;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getAccommodation().getProvider().equals(this)) {
                totalSum += evaluation.getGrade();
                numOfEvaluations++;
            }
        }
        if (numOfEvaluations == 0) {
            avgRatingOfAllAccom = 0;
            totalRatings = 0;
            return;
        }
        totalRatings = numOfEvaluations;
        avgRatingOfAllAccom = totalSum / numOfEvaluations;
    }


    /**
     * Η μέθοδος αυτή επιστρέφει τη μέση βαθμολογία όλων των καταλυμάτων του παρόχου.
     * @return μέση βαθμολογία όλων των καταλυμάτων του παρόχου.
     */
    public float getAvgRatingOfAllAccom() {
        return avgRatingOfAllAccom;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει τον συνολικό βαθμό των αξιολογήσεων των καταλυμάτων του παρόχου.
     * @return συνολικός βαθμός αξιολογήσεων των καταλυμάτων του παρόχου.
     */
    public int getTotalRatings() {
        return totalRatings;
    }
}
