package api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;


/**
 *  Η κλάση αυτή περιέχει μεθόδους για την διαχείρηση των αξιολογήσεων.
 */

public class ManageEvaluations implements Serializable {

    private HashSet<Evaluation> evaluations;

    public ManageEvaluations() {
        evaluations = new HashSet<>();
    }

    /**
     * Η μέθοδος αυτή ελέγχει αν υπάρχει κάποιο αρχείο εξόδου.
     */

    public void saveToOutputFiles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("evaluationsManager.bin"))) {
            out.writeObject(this);
        } catch (IOException e1) {
            System.out.println("Δεν βρέθηκε αρχείο εξόδου");
        }
    }

    /**
     * H μέθοδος αυτή αφαιρεί τις αξιολογήσεις εξαιτίας της διαγραφής καταλύμματος.
     * @param accommodation Το κατάλυμα.
     */

    public void removedAccommodationAlert(Accommodation accommodation) {
        if (evaluations.isEmpty())
            return;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getAccommodation().equals(accommodation))
                removeEvaluation(evaluation);
        }
    }

    /**
     * Η μέθοδος αυτή ενημερώνει τον μέσο όρο βαθμολόγησης των καταλύμματων βάση των αξιολογήσεων που έ-
     * χουν λάβει και τον μέσο όρο βαθμολόγησης του χρήστη με βάση τις αξιολογήσεις που έχει κάνει.
     * @param accommodation Το κατάλυμμα.
     * @param user Ο χρήστης.
     */

    private void updateAvgRatings(Accommodation accommodation, SimpleUser user) {
        accommodation.getProvider().updateAvgRatingOfAllAccom(evaluations);
        accommodation.updateAvgRatingOfAccommodation(evaluations);
        user.updateAvgRatingOfUser(evaluations);
    }

    /**
     * Η μέθοδος αυτή ελέγχει έαν ο χρήστης έχει αξιολόγησει ήδη το κατάλυμμα.
     * @param user Ο χρήστης.
     * @param accommodation Το κατάλυμμα.
     * @return true ή false.
     */
    public boolean userAlreadyEvaluatedThis(SimpleUser user, Accommodation accommodation) {
        if (evaluations.isEmpty())
            return false;
        for (Evaluation evaluation : evaluations) {  // μάλλον μπορεί να γίνει με μια απλή contains αφού η equals της evaluation ελέγχει χρήστη και κατάλυμα
            if (evaluation.getUser().equals(user) && evaluation.getAccommodation().equals(accommodation))
                return true;
        }
        return false;
    }

    /**
     * Η μέθοδος αυτή προσθέτει μια αξιολόγηση στην λίστα αξιολογήσεων.
     * @param text Το κείμενο αξιολόγησης.
     * @param grade Ο βαθμός αξιολόγησης.
     * @param user Ο χρήστης που αξιολογεί.
     * @param accommodation Το κατάλυμμα που αξιολογείται.
     * @return True ή false.
     */

    public boolean addEvaluation(String text, float grade, SimpleUser user, Accommodation accommodation) {
        Evaluation submittedEvaluation = new Evaluation(text, grade, user, accommodation);
        if (userAlreadyEvaluatedThis(user, accommodation))
            return false;
        evaluations.add(submittedEvaluation);
        updateAvgRatings(accommodation, user); //επανυπολογισμοί μέσων όρων μετά την προσθήκη
        return true;
    }

    /**
     * Η μέθοδος αυτή αφαιρεί μία αξιολόγηση από την λίστα αξιολογήσεων και επιστρέφει τον μέσο όρο αξι-
     * ολογήσεων μετά την διαγραφή.
     * @param toDeleteEvaluation Η αξιολόγηση που πρόκειται να διαγραφεί
     * @return Ο μέσος όρος αξιολογήσεων μετά την διαγραφή.
     */

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

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την εύρεση αξιολογήσεων ενός χρήστη,και επιστρέφει την λίστα
     * με τις αξιολογήσεις του.
     * @param user Ο χρήστης.
     * @return Λίστα με τις αξιολογήσεις του χρήστη.
     */

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

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την εύρεση των αξιολογήσεων ενός καταλύμματος,και επιστρέφει μια
     * λίστα με τις αξιολογήσεις.
     * @param accommodation Το κατάλλυμα.
     * @return Η λίστα με τις αξιολογήσεις του καταλύμματος.
     */

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

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την αρίθμηση των αξιολογήσεων που κατέχει ένας πάροχος,και ε-
     * πιστρέφει τον αριθμό αυτο.
     * @param provider Ο πάροχος.
     * @return Ο αριθμός των αξιολογήσεων που έχει ο πάροχος.
     */

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

    /**
     * Η μέθοδος αυτή επιστρέφει την αξιολόγηση του χρήστη για ένα κατάλυμμα.
     * @param user Ο χρήστης.
     * @param evaluation Η αξιολόγηση.
     * @return Η αξιολόγηση του χρήστη.
     */

    public boolean isUsersEvaluation(User user, Evaluation evaluation) {
        return evaluation.getUser().equals(user);
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν το κείμενο της αξιόλογησης ενός καταλύμματος είναι  μεγαλύτερο απο το
     * επιτρεπτό όριο.
     * @param text Το κείμενο.
     * @return True ή false.
     */

    public boolean evaluationTextTooLong(String text) {
        return text.length() > 500;
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν ο βαθμός αξιολόγησης ενός καταλύμματος βρίσκεται εντός των επιτρεπτών
     * ορίων.
     * @param grade Ο βαθμός αξιολόγησης.
     * @return True ή false.
     */

    public boolean gradeOutOfBounds(float grade) {
        return grade < 1 || grade > 5;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει τις αξιολογήσεις.
     * @return Οι αξιολογήσεις.
     */

    public HashSet<Evaluation> getEvaluations() {
        return evaluations;
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν καταχώρονται τα πεδία της αξιολόγησης ενός καταλύμματος και εάν καταψω-
     * ρούνται σώστα ως προς τα όρια που έχουν τέθει.Ελέγχεται με την χρήση προηγούμενων μεθόδων εάν η
     * βαθμολογία βρίσκεται εντός των επιτρεπτών ορίων,οι χαρακτήρες του κειμένου είναι περισσότεροι απο το
     * μέγιστο όριο.

     * @param evaluationText Το κέιμενο αξιολόγησης.
     * @param grade Ο βαθμός αξιολόγησης.
     * @return Κατάλληλα μηνύματα ανάλογα το σφάλμα που έχει προκύψει κατά την υποβολή της αξιολόγησης.
     */

    public String checkSubmissionInaccuracies(String evaluationText, String grade) {
        try {
            float grade_num = Float.parseFloat(grade);
            if (gradeOutOfBounds(grade_num))
                return "Παρακαλώ εισάγετε βαθμολογία μεταξύ του 1 και 5.";

            else if (evaluationTextTooLong(evaluationText))
                return "Το κείμενο της αξιολόγησης δεν πρέπει να υπερβαίνει τους 500 χαρακτήρες";

            else if (evaluationText.length() == 0)
                return "Το κείμενο της αξιολόγησης είναι υποχρεωτικό.";
            return null;

        } catch(NumberFormatException e1) {
            return "Παρακαλώ εισάγετε αριθμό στο πεδίο της βαθμολογίας.";
        }
    }
}
