package api;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ManageEvaluations implements Serializable {
    private HashSet<Evaluation> evaluations;

    public ManageEvaluations() {
        evaluations = new HashSet<>();
    }

    public void removedAccommodationAlert(Accommodation accommodation) {
        if (evaluations.isEmpty())
            return;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getAccomodation().equals(accommodation))
                removeEvaluation(evaluation);
        }
    }

    private void updateAvgRatings(Accommodation accommodation, SimpleUser user) {
        accommodation.updateAvgRatingOfAccomodation(evaluations);
        user.updateAvgRatingOfUser(evaluations);
    }
    private boolean userAlreadyEvaluatedThis(SimpleUser user, Accommodation accommodation) {
        if (evaluations.isEmpty())
            return false;
        for (Evaluation evaluation : evaluations) {  // μάλλον μπορεί να γίνει με μια απλή contains αφού η equals της evaluation ελέγχει χρήστη και κατάλυμα
            if (evaluation.getUser().equals(user) && evaluation.getAccomodation().equals(accommodation))
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
            updateAvgRatings(toDeleteEvaluation.getAccomodation(), toDeleteEvaluation.getUser()); //επανυπολογισμοί μέσων όρων μετά τη διαγραφή
            return true;
        }
        return false;
    }

    public List<Evaluation> getUserEvaluations(SimpleUser user) {
        ArrayList<Evaluation> userEvaluations = new ArrayList<>();
        if (!evaluations.isEmpty()) {
            for (Evaluation evaluation : evaluations) {
                if (evaluation.getUser().equals(user))
                    userEvaluations.add(evaluation);
            }
            return userEvaluations;
        }
        else
            return null;
    }

    public boolean alterEvaluation(Evaluation oldEvaluation, float nextGrade, String nextText) {
        for (Evaluation evaluation : evaluations) {
            if (oldEvaluation.equals(evaluation)) {
                evaluations.remove(evaluation); //πρέπει να γίνει αυτή η διαδικασία αλλιώς αλλάζει μόνο η τοπική μεταβλητή στο for loop
                if (evaluation.getGrade() != nextGrade) { // έλεγχος για να μη γίνεται επανυπολογισμός μέσων όρων άδικα
                    evaluation.setGrade(nextGrade);
                    updateAvgRatings(evaluation.getAccomodation(), evaluation.getUser()); //επανυπολογισμός μέσων όρων αν αλλάξει ο βαθμός της αξιολόγησεις
                }
                evaluation.setEvaluationText(nextText);
                evaluations.add(evaluation);
                return true;
            }
        }
        return false;
    }

    public boolean evaluationTextTooLong(String text) {
        return text.length() > 500;
    }
}
