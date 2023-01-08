package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * Η κλάση αυτή διαχειρίζεται τις πληροφορίες/χαρακτηριστικά ενός καταλύματος.
 */

public class Accommodation implements Serializable {

    private float avgRating;
    private int totalEvaluations; //avgRating και totalEvaluations αλλάζουν κάθε φορά που προστίθεται αξιολόγηση στο κατάλυμα

    private long singularId; //Για να αποφευχθούν προβλήματα συνωνυμίας  !!!!!!
    private final Provider provider;
    private String name;
    private String description;
    private String stayType; //ξενοδοχείο, διαμέρισμα, μεζονέτα --> hotel, apartment, maisonette
    private Location place;
    private ArrayList<Utility> typesOfUtilities;

    /**
     * Κατασκευαστής της κλάσης των καταλυμάτων. Αρχικοποιεί τα πεδία name, description, stayType και provider σύμφωνα
     * με τα ορίσματα που δίνονται. Τα πεδία avgRating και totalEvaluations αρχικοποιούνται στο μηδέν. Επίσης, υπολογίζεται
     * ο κωδικός του καταλύματος ως το άθροισμα του hashCode() του username του παρόχου του καταλύματος και του ονόματος
     * του ίδιου καταλύματος. Τέλος, αρχικοποιείται η λίστα των παροχών με 9 αντικείμενα για να αποφευχθούν exceptions
     * του τύπου ArrayIndexOutOfBoundsException σε διάφορα σημεία του προγράμματος.
     * @param name Το όνομα του καταλύματος
     * @param description Περιγραφή του καταλύματος
     * @param stayType Τύπος καταλύματος: 1. Ξενοδοχείο, 2. Διαμέρισμα, 3. Μεζονέτα
     * @param location Αντικείμενο της κλάσης Location για την αποθήκευση της τοποθεσίας του καταλύματος (πόλη, διεύθυνση
     *                 και Τ.Κ.)
     * @param provider Ο πάροχος του καταλύματος (αντικείμενο της κλάσης Provider)
     */
    public Accommodation(String name, String description, String stayType, Location location, Provider provider) {
        this.name = name;
        this.description = description;
        this.stayType = stayType;
        totalEvaluations = 0;
        place = location;
        this.provider = provider;
        singularId = provider.getUserName().hashCode() + name.hashCode(); //Μοναδικό id καταλύματος --> Απαγορεύω στον provider να κάνει δύο καταλύματα με το ίδιο όνομα
        //Μοναδικός κωδικός καταλύματος ακόμα και αν ο ίδιος πάροχος έχει δύο καταλύματα με το ίδιο όνομα
        avgRating = 0;

        typesOfUtilities = new ArrayList<>(); //Για λόγους debugging
        Utility view = new Utility();
        typesOfUtilities.add(view);
        Utility bath = new Utility();
        typesOfUtilities.add(bath);
        Utility washingClothes = new Utility();
        typesOfUtilities.add(washingClothes);
        Utility entertainment = new Utility();
        typesOfUtilities.add(entertainment);
        Utility temperatureControl = new Utility();
        typesOfUtilities.add(temperatureControl);
        Utility internet = new Utility();
        typesOfUtilities.add(internet);
        Utility foodArea = new Utility();
        typesOfUtilities.add(foodArea);
        Utility outsideSpace = new Utility();
        typesOfUtilities.add(outsideSpace);
        Utility parkingSpace = new Utility();
        typesOfUtilities.add(parkingSpace);
    }

    /**
     * Επιστρέφει το όνομα του καταλύματος
     * @return το όνομα του καταλύματος
     */
    public String getName() {
        return name;
    }

    /**
     * Αλλάζει το όνομα του καταλύματος σε ό,τι δίνεται ως όρισμα
     * @param name το νέο όνομα του καταλύματος
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Επιστρέφει την περιγραφή του καταλύματος
     * @return η περιγραφή του καταλύματος
     */
    public String getDescription() {
        return description;
    }

    /**
     * Αλλάζει την περιγραφή του καταλύματος σε ό,τι δίνεται ως όρισμα
     * @param description η νέεα περιγραφή του καταλύματος
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Επιστρέφει τον τύπο του καταλύματος που για την ώρα πρέπει να είναι 1. Ξενοδοχείο, 2. Διαμέρισμα, 3. Μεζονέτα
     * @return τύπος καταλύματος
     */
    public String getStayType() {
        return stayType;
    }

    /**
     * Αλλάζει την περιγραφή του καταλύματος σε ό,τι δίνεται ως όρισμα
     * @param stayType ο νέος τύπος καταλύματος
     */
    public void setStayType(String stayType) {
        this.stayType = stayType;
    }

    /**
     * Επιστρέφει το αντικείμενο της τοποθεσίας του καταλύματος
     * @return τοποθεσία καταλύματος (πόλη, διεύθυνση και Τ.Κ.)
     */
    public Location getLocation() { return place; }

    /**
     * Αλλάζει την τοποθεσία του καταλύματος σε ό,τι δίνεται ως όρισμα
     * @param place το νέο αντικείμενο τοποθεσίας του καταλύματος
     */
    public void setPlace(Location place) {
        this.place = place;
    }

    /**
     * Επιστρέφει το αντικείμενο του παρόχου του καταλύματος
     * @return τον πάροχο του καταλύματος
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Επιστρέφει το μοναδικό id του καταλύματος που αρχικοποιείται στον κατασκευαστή
     * @return μοναδικό id του καταλύματος
     */
    public long getSingularId() {
        return singularId;
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την ανανέωση του μοναδικού κωδικού καταλύματος σε περίπτωση
     * μετονομασίας του. Έτσι, διατηρείται η μοναδικότητα του id αν ο πάροχος μετονομάσει αυτό το κατάλυμα και μετά
     * δημιουργήσει άλλο με το αρχικό όνομα του πρώτου.
     */
    public void updateSingularId() {
        singularId = provider.getUserName().hashCode() + name.hashCode();
    }

    /**
     * Επιστρέφει τη μέση βαθμολογία του καταλύματος
     * @return μέση βαθμολογία του καταλύματος
     */
    public float getAvgRating() {
        return avgRating;
    }

    /**
     * Επιστρέφει τον αριθμό των αξιολογήσεων του καταλύματος
     * @return αριθμός των αξιολογήσεων του καταλύματος
     */
    public int getTotalEvaluations() {
        return totalEvaluations;
    }

    /**
     * Επιστρέφει τη λίστα με τα αντικείμενα παροχών του καταλύματος
     * @return λίστα με τα αντικείμενα παροχών του καταλύματος
     */
    public ArrayList<Utility> getTypesOfUtilities() {
        return typesOfUtilities;
    }

    /**
     * Αλλάζει τη λίστα των παροχών σε περίπτωση μεταγενέστερης επεξεργασίας τους από τον πάροχο.
     * @param typesOfUtilities η νέα λίστα παροχών
     */
    public void setTypesOfUtilities(ArrayList<Utility> typesOfUtilities) {
        this.typesOfUtilities = typesOfUtilities;
    }

    /**
     * Η μέθοδος αυτή ενημερώνει τον μέσο όρο βαθμολογίας ενός καταλύματος και τον αριθμό των αξιολογήσεων για αυτό.
     * @param evaluations Όλες οι βαθμολογίες για όλα τα καταλύματα
     */

    public void updateAvgRatingOfAccommodation(ArrayList<Evaluation> evaluations) {
        if (evaluations.size() == 0) { //μηδενισμός των μεταβλητών αν δεν έχουν προστεθεί αξιολογήσεις ή αν διαγραφούν αργότερα όλες
            avgRating = 0;
            totalEvaluations = 0;
            return;
        }
        float totalSum = 0;
        int numOfEvaluations = 0;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getAccommodation().equals(this)) { //αν η αξιολόγηση απευθύνεται στο συγκεκριμένο κατάλυμα
                totalSum += evaluation.getGrade();
                numOfEvaluations++;
            }
        }
        if (numOfEvaluations == 0) {
            avgRating = 0;
            totalEvaluations = 0;
            return;
        }
        totalEvaluations = numOfEvaluations;
        avgRating = totalSum / numOfEvaluations;
    }

    /**
     * Ελέγχει την ισότητα δύο αντικειμένων Accommodation. Η ισότητα τους αν οι θέσεις μνήμης διαφέρουν και πρόκειται για
     * αντικείμενο τύπου Accommodation έγκειται στην ισότητα των id των δύο καταλυμάτων.
     * @param o το αντικείμενο που θέλουμε να συγκρίνουμε με το this
     * @return true αν ισχύει η ισότητα των δύο αντικειμένων
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accommodation that)) return false;
        return getSingularId() == that.getSingularId();
    }

    /**
     * Επιστρέφει τη συμβολοσειρά που αντιστοιχεί στο κατάλυμα ως "[όνομα], [πόλη], [τύπος καταλύματος],
     * [μέση βαθμολογία] ([αριθμός αξιολογήσεων])".
     * @return συμβολοσειρά που αντιστοιχεί στο κατάλυμα
     */
    @Override
    public String toString() {
        return name + ", " + getLocation().getTown() + ", " + stayType + ", " + avgRating +"(" + totalEvaluations+")";
    }
}
