package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;


public class ManageEvaluations implements Serializable {
    private HashSet<Evaluation> evaluations;

    public ManageEvaluations() {
        evaluations = new HashSet<>();
    }


    public void removedAccommodationAlert(Accommodation accommodation) {
        if (evaluations.isEmpty())
            return;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getAccommodation().equals(accommodation))
                removeEvaluation(evaluation);
        }
    }

    private void updateAvgRatings(Accommodation accommodation, SimpleUser user) {
        accommodation.updateAvgRatingOfAccomodation(evaluations);
        user.updateAvgRatingOfUser(evaluations);
    }
    public boolean userAlreadyEvaluatedThis(SimpleUser user, Accommodation accommodation) {
        if (evaluations.isEmpty())
            return false;
        for (Evaluation evaluation : evaluations) {  // μάλλον μπορεί να γίνει με μια απλή contains αφού η equals της evaluation ελέγχει χρήστη και κατάλυμα
            if (evaluation.getUser().equals(user) && evaluation.getAccommodation().equals(accommodation))
                return true;
        }
        return false;
    }

    public boolean addEvaluation(String text, float grade, SimpleUser user, Accommodation accommodation) {
        Evaluation submittedEvaluation = new Evaluation(text, grade, user, accommodation);
        if (userAlreadyEvaluatedThis(user, accommodation))
            return false;
        evaluations.add(submittedEvaluation);
        updateAvgRatings(accommodation, user); //επανυπολογισμοί μέσων όρων μετά την προσθήκη
        return true;
    }

    public boolean removeEvaluation(Evaluation toDeleteEvaluation) {
        if (evaluations.isEmpty())
            return false;
        if (evaluations.contains(toDeleteEvaluation)) {
            evaluations.remove(toDeleteEvaluation);
            updateAvgRatings(toDeleteEvaluation.getAccommodation(), toDeleteEvaluation.getUser()); //επανυπολογισμοί μέσων όρων μετά τη διαγραφή
            return true;
        }
        return false;
    }

    public ArrayList<Evaluation> getUserEvaluations(SimpleUser user) { //Μπορει να επιστρέφει null ή και κενή λίστα --> πρέπει να ελέγχεται
        ArrayList<Evaluation> userEvaluations = new ArrayList<>();
        if (!evaluations.isEmpty()) {
            for (Evaluation evaluation : evaluations) {
                if (evaluation.getUser().equals(user))
                    userEvaluations.add(evaluation);
            }
        }
        return userEvaluations;
    }

    public ArrayList<Evaluation> getAccommodationEvaluations (Accommodation accommodation) { //Μπορει να επιστρέφει null ή και κενή λίστα --> πρέπει να ελέγχεται
        ArrayList<Evaluation> accommodationEvaluations = new ArrayList<>();
        if (!evaluations.isEmpty()) {
            for (Evaluation evaluation : evaluations) {
                if (evaluation.getAccommodation().equals(accommodation))
                    accommodationEvaluations.add(evaluation);
            }
        }
        return accommodationEvaluations;
    }

    public boolean alterEvaluation(Evaluation oldEvaluation, float nextGrade, String nextText) {
        for (Evaluation evaluation : evaluations) {
            if (oldEvaluation.equals(evaluation)) {
                if (evaluationTextTooLong(nextText))
                    return false;
                if (gradeOutOfBounds(nextGrade))
                    return false;
                evaluations.remove(evaluation); //πρέπει να γίνει αυτή η διαδικασία αλλιώς αλλάζει μόνο η τοπική μεταβλητή στο for loop
                if (evaluation.getGrade() != nextGrade) { // έλεγχος για να μη γίνεται επανυπολογισμός μέσων όρων άδικα
                    evaluation.setGrade(nextGrade);
                    updateAvgRatings(evaluation.getAccommodation(), evaluation.getUser()); //επανυπολογισμός μέσων όρων αν αλλάξει ο βαθμός της αξιολόγησεις
                }
                evaluation.setEvaluationText(nextText);
                evaluations.add(evaluation);
                return true;
            }
        }
        return false;
    }

    public boolean isUsersEvaluation(User user, Evaluation evaluation) {
        return evaluation.getUser().equals(user);
    }

    public boolean evaluationTextTooLong(String text) {
        return text.length() > 500;
    }

    public boolean gradeOutOfBounds(float grade) {
        return grade < 1 || grade > 5;
    }
}
