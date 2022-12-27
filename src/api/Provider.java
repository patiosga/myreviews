package api;

import java.io.Serializable;
import java.util.HashSet;

public class Provider extends User implements Serializable {
    private int totalRatings; //πρέπει να ενημερώνεται κάθε φορά που προστίθεται ή αφαιρείται αξιολόγηση
    private float avgRatingOfAllAccom;

    public Provider(String firstName,String lastName,String userName, String password, String type) {
        super(firstName,lastName,userName, password, type);
        totalRatings = 0;
        avgRatingOfAllAccom = 0;
    }

    public void updateAvgRatingOfAllAccom(HashSet<Evaluation> evaluations) { //πρέπει να καλείται μετά τη μέθοδο που ενημερώνει το κατάλυμα μετά από προσθήκη ή αφαίρεση αξιολόγησης
        if (evaluations.size() == 0) {
            avgRatingOfAllAccom = 0;
            return;
        }
        float totalSum = 0;
        int numOfEvaluations = 0;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getAccommodation().getProvider().getUserName() == this.userName) {
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

    public float getAvgRatingOfAllAccom() {
        return avgRatingOfAllAccom;
    }

    public int getTotalRatings() {
        return totalRatings;
    }
    public void display(){
        //provolh katalumatwn-dunatothta epejergasias
    }
}
