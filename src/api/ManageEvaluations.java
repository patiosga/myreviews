package api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;


public class ManageEvaluations implements Serializable {

    private HashSet<Evaluation> evaluations;

    public ManageEvaluations() {
        evaluations = new HashSet<>();
    }

    public void saveToOutputFiles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("evaluationsManager.bin"))) {
            out.writeObject(this);
        } catch (IOException e1) {
            System.out.println("Δεν βρέθηκε αρχείο εξόδου");
        }
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
        accommodation.getProvider().updateAvgRatingOfAllAccom(evaluations);
        accommodation.updateAvgRatingOfAccommodation(evaluations);
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

    public ArrayList<Evaluation> getUserEvaluations(SimpleUser user) { //Μπορεί να επιστρέφει κενή λίστα --> πρέπει να ελέγχεται
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
                removeEvaluation(evaluation); //πρέπει να γίνει αυτή η διαδικασία αλλιώς αλλάζει μόνο η τοπική μεταβλητή στο for loop
                evaluation.setGrade(nextGrade);
                evaluation.setEvaluationText(nextText);
                addEvaluation(nextText, nextGrade, oldEvaluation.getUser(), oldEvaluation.getAccommodation());
                return true;
            }
        }
        return false;
    }

    public int getProvidersNumOfEvaluations(Provider provider) {
        int counter = 0;
        if (evaluations.isEmpty())
            return 0;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getAccommodation().getProvider().equals(provider))
                counter++;
        }
        return counter;
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

    public HashSet<Evaluation> getEvaluations() {
        return evaluations;
    }

    public String checkSubmissionInaccuracies(String evaluationText, String grade) {
        try {
            float grade_num = Float.parseFloat(grade);
            if (gradeOutOfBounds(grade_num))
                return "Παρακαλώ εισάγετε βαθμολογία μεταξύ του 1 και 5.";

            else if (evaluationTextTooLong(evaluationText))
                return "Το κείμενο της αξιολόγησης δεν πρέπει να υπερβαίνει τους 500 χαρακτήρες";

            else if (evaluationText.trim().length() == 0) //Η μέθοδος trim() αφαιρεί όλα τα whitespaces ώστε να μην περνάει ως είσοδος το space
                return "Το κείμενο της αξιολόγησης είναι υποχρεωτικό.";
            return null;

        } catch(NumberFormatException e1) {
            return "Παρακαλώ εισάγετε αριθμό στο πεδίο της βαθμολογίας.";
        }
    }
}
