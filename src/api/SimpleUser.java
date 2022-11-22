package api;

import java.util.ArrayList;
import java.util.List;

public class SimpleUser extends User{
    private float avgRating; //πρέπει να ενημερώνεται κάθε φορά που κάνει ο χρήστης νέα αξιολόγηση ή αφαιρείται μια παλιά του

    public SimpleUser(String userName, String password, String type) {
        super(userName, password, type);
        avgRating = 0;
    }

    public void updateAvgRatingOfUser(List<Evaluation> evaluations) {
        if (evaluations.size() == 0) {
            avgRating = 0;
            return;
        }
        float totalSum = 0;
        int numOfEvaluations = 0;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getUser().getUserName() == this.getUserName()) {
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
    public float getAvgRating() {
        return avgRating;
    }






    public void display() {
        //προβολή προσωπικών αξιολογήσεων στο dashboard
    }
}
