package api;

import java.util.ArrayList;

public class SimpleUser extends User{
    private ArrayList<Evaluation> personalEvaluations;


    public SimpleUser(String userName, String password, String type) {

        super(userName, password, type);
    }

    public float getAverageOfRatings() {
        if (personalEvaluations.size() == 0)
            return 0;
        float totalGrades = 0;
        for (Evaluation evaluation : personalEvaluations) {
            totalGrades += evaluation.getGrade();
        }
        return totalGrades/personalEvaluations.size();
    }

    public boolean addPersonalEvaluation(Evaluation rating) {

    }

    public boolean removePersonalEvaluation(Evaluation rating) {
        //πρέπει να διαγράφεται ταυτόχρονα και από τη λίστα με τις αξιολογήσεις του αντίστοιχου καταλύματος
        //καλύτερα να γίνεται αυτόματα από εδώ και το δεύτερο καλώντας την αντίστοιχη μέθοδο
        //!!! περιπλέκεται αρκετά αν ο πάροχος αφαιρέσει το κατάλυμα αλλα βλέπουμε
    }

    //μέθοδοι επεξεργασίας της αξιολόγησης και του βαθμού
    //ΜΑΛΛΟΝ ΘΑ ΕΧΟΥΜΕ ΜΙΑ ΛΙΣΤΑ ΑΠΟ ΑΞΙΟΛΟΓΗΣΕΙΣ ΚΑΠΟΥ ΑΛΛΟΥ ΜΑΛΛΟΝ γιατι τώρα ότι αλλαγές γίνονται πρεπει να γίνονται δύο φορές

    public void display() {
        //προβολή προσωπικών αξιολογήσεων στο dashboard
    }
}
