package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * Η κλάσση αυτή αφορά τους παρόχους καταλυμμάτων.
 */
public class Provider extends User implements Serializable {
    private int totalRatings; //πρέπει να ενημερώνεται κάθε φορά που προστίθεται ή αφαιρείται αξιολόγηση
    private float avgRatingOfAllAccom;

    public Provider(String firstName,String lastName,String userName, String password, String type) {
        super(firstName,lastName,userName, password, type);
        totalRatings = 0;
        avgRatingOfAllAccom = 0;
    }

    /**
     * Η μέση βαθμολογία του παρόχου δεν επηρεάζεται από καταλύματα με μηδενικές αξιολογήσεις, γιατί βγαίνει από τη λίστα των αξιολογήσεων
     * @param evaluations Η λίστα όλων των αξιολογήσεων για όλα τα καταλύματα (βελτιστοποιήσιμο)
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
     * Η μέθοδος αυτή επιστρέφει τον μέσο όρο βαθμολόγησης όλων των καταλυμμάτων.
     * @return Τον μέσο όρο βαθμολόγησης όλων των καταλυμμάτων.
     */

    public float getAvgRatingOfAllAccom() {
        return avgRatingOfAllAccom;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει τον συνολικό βαθμό των αξιολογήσεων.
     * @return Τον επιστρέφει τον συνολικό βαθμό των αξιολογήσεων.
     */

    public int getTotalRatings() {
        return totalRatings;
    }

}
