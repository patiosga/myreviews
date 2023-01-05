package api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Η κλάση αυτή περιέχει μεθόδους για τη διαχείριση των αξιολογήσεων των καταλυμάτων.
 */
public class ManageEvaluations implements Serializable {

    private ArrayList<Evaluation> evaluations;

    /**
     * Κατασκευαστής της κλάσης ManageEvaluations. Αρχικοποιεί τη λίστα των αξιολογήσεων.
     */
    public ManageEvaluations() {
        evaluations = new ArrayList<>();
    }

    /**
     * Η μέθοδος αυτή αποθηκεύει το παρόν αντικείμενο στο αντίστοιχο αρχείο εξόδου.
     */
    public void saveToOutputFiles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("evaluationsManager.bin"))) {
            out.writeObject(this);
        } catch (IOException e1) {
            System.out.println("Δεν βρέθηκε αρχείο εξόδου");
        }
    }

    /**
     * H μέθοδος αυτή αφαιρεί τις αξιολογήσεις που αντιστοιχεούν σε κατάλυμα που έχει διαγραφεί.
     * @param accommodation Το κατάλυμα που έχει διαγραφεί
     */
    public void removedAccommodationAlert(Accommodation accommodation) {
        if (evaluations.isEmpty())
            return;
        for (Iterator<Evaluation> iterator = evaluations.iterator(); iterator.hasNext();) {
            Evaluation evaluation = iterator.next();
            if (evaluation.getAccommodation().equals(accommodation))
                iterator.remove();
        }
    }

    /**
     * Η μέθοδος αυτή καλεί τις μεθόδους που ενημερώνουν τον μέσο όρο βαθμολογίας του καταλύματος που δίνεται ως όρισμα
     * και τον μέσο όρο βαθμολόγησης του χρήστη με βάση τις αξιολογήσεις που έχει κάνει.
     * @param accommodation Το κατάλυμμα.
     * @param user Ο χρήστης.
     */
    private void updateAvgRatings(Accommodation accommodation, SimpleUser user) {
        accommodation.getProvider().updateAvgRatingOfAllAccom(evaluations);
        accommodation.updateAvgRatingOfAccommodation(evaluations);
        user.updateAvgRatingOfUser(evaluations);
    }

    /**
     * Η μέθοδος αυτή ελέγχει έαν ο χρήστης έχει αξιολογήσει ήδη το κατάλυμα.
     * @param user Ο χρήστης.
     * @param accommodation Το κατάλυμα.
     * @return True αν ο χρήστης έχει αξιολογήσει ήδη το κατάλυμα, false διαφορετικά.
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
     * Η μέθοδος αυτή προσθέτει μια αξιολόγηση στη λίστα αξιολογήσεων.
     * @param text Το κείμενο της νέας αξιολόγησης.
     * @param grade Ο βαθμός της νέας αξιολόγησης.
     * @param user Ο χρήστης που αξιολογεί.
     * @param accommodation Το κατάλυμα που αξιολογείται.
     * @return True ή false ανάλογα με το αν ο χρήστης έχει αξιολογήσει ήδη το κατάλυμα ή όχι.
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
     * Η μέθοδος αυτή αφαιρεί μία αξιολόγηση από τη λίστα αξιολογήσεων εφόσον αυτή υπάρχει.
     * @param toDeleteEvaluation Η αξιολόγηση που πρόκειται να διαγραφεί
     * @return True αν βρέθηκε και αφαιρέθηκε η αξιολόγηση, false διαφορετικά
     */
    public boolean removeEvaluation(Evaluation toDeleteEvaluation) {
        if (evaluations.isEmpty())
            return false;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.equals(toDeleteEvaluation)) {
                if (!evaluations.remove(toDeleteEvaluation))
                    return false;
                updateAvgRatings(toDeleteEvaluation.getAccommodation(), toDeleteEvaluation.getUser()); //επανυπολογισμοί μέσων όρων μετά τη διαγραφή
                return true;
            }
        }
        return false;
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την εύρεση αξιολογήσεων του χρήστη που δίνεται ως όρισμα και τις επιστρέφει
     * σε μορφή λίστας.
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
     * Η μέθοδος αυτή χρησιμοποιείται για την εύρεση των αξιολογήσεων ενός καταλύματος και τις επιστρέφει σε μορφή
     * λίστας
     * @param accommodation Το κατάλυμα.
     * @return Η λίστα με τις αξιολογήσεις του καταλύματος.
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

    /**
     * Μέθοδος για την επεξεργασία μιας παλιάς αξιολόγησης. Αλλάζει το κείμενο και ο βαθμός της αξιολόγησης και γίνονται
     * οι αντίστοιχοι έλεγχοι για το αν τα νέα δεδομένα πληρούν τις προϋποθέσεις μιας ολοκληρωμένης αξιολόγησης.
     * @param oldEvaluation Παλιά αξιολόγηση
     * @param nextGrade Νέος βαθμός
     * @param nextText Νέα κείμενο
     * @return True τα νέα δεδομένα πληρούν τις προϋποθέσεις μιας ολοκληρωμένης αξιολόγησης, false διαφορετικά
     */
    public boolean alterEvaluation(Evaluation oldEvaluation, float nextGrade, String nextText) {
        if (evaluations.isEmpty())
            return false;
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
     * Η μέθοδος αυτή επιστρέφει τον αριθμό των αξιολογήσεων που έχουν γίνει στα καταλύματα ενός παρόχου και επιστρέφει
     * τον αριθμό αυτό.
     * @param provider Ο πάροχος.
     * @return Ο αριθμός των αξιολογήσεων που έχουν τα καταλύματα του παρόχου.
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
     * Η μέθοδος ελέγχει αν η αξιολόγηση που δίνεται ως όρισμα έχει γίνει από τον χρήστη που δίνεται ως όρισμα.
     * @param user Ο χρήστης.
     * @param evaluation Η αξιολόγηση.
     * @return Η αξιολόγηση του χρήστη.
     */
    public boolean isUsersEvaluation(User user, Evaluation evaluation) {
        return evaluation.getUser().equals(user);
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν το κείμενο της αξιολόγησης ενός καταλύματος είναι μεγαλύτερο από το
     * επιτρεπτό όριο των 500 χαρακτήρων.
     * @param text Το κείμενο.
     * @return True αν το κείμενο υπερβαίνει τους 500 χαρακτήρες, false διαφορετικά.
     */
    public boolean evaluationTextTooLong(String text) {
        return text.length() > 500;
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν ο βαθμός αξιολόγησης ενός καταλύματος βρίσκεται εντός των επιτρεπτών
     * ορίων, δηλαδή εντός του [1,5].
     * @param grade Ο βαθμός αξιολόγησης.
     * @return True εάν ο βαθμός αξιολόγησης ενός καταλύματος βρίσκεται του [1,5], false διαφορετικά.
     */
    public boolean gradeOutOfBounds(float grade) {
        return grade < 1 || grade > 5;
    }

    /**
     * Η μέθοδος αυτή επιστρέφει τις όλες τις αξιολογήσεις που έχουν γίνει.
     * @return Οι αξιολογήσεις.
     */
    public ArrayList<Evaluation> getEvaluations() {
        return evaluations;
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν καταχωρούνται τα πεδία της αξιολόγησης ενός καταλύματος και εάν καταχωρούνται
     * σωστά ως προς τα όρια που έχουν τεθεί. Ελέγχεται με τη χρήση προηγούμενων μεθόδων εάν η
     * βαθμολογία βρίσκεται εντός των επιτρεπτών ορίων ([1,5]) και αν οι χαρακτήρες του κειμένου είναι περισσότεροι απο το
     * μέγιστο όριο (500).

     * @param evaluationText Το κείμενο αξιολόγησης.
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

            else if (evaluationText.trim().length() == 0) //Η μέθοδος trim() αφαιρεί όλα τα whitespaces ώστε να μην περνάει ως είσοδος το space
                return "Το κείμενο της αξιολόγησης είναι υποχρεωτικό.";
            return null;

        } catch(NumberFormatException e1) {
            return "Παρακαλώ εισάγετε αριθμό στο πεδίο της βαθμολογίας.";
        }
    }
}
