package api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Η κλάση αυτή περιέχει μεθόδους για τη διαχείριση των καταλυμάτων και την κεντρική λίστα που συγκεντρώνει όλα τα
 * καταλύματα.
 */
public class ManageAccommodations implements Serializable {
    private ArrayList<Accommodation> accommodations;

    /**
     * Κατασκευαστής της κλάσης ManageAccommodations. Αρχικοποιεί τη λίστα των καταλυμάτων.
     */
    public ManageAccommodations() {
        accommodations = new ArrayList<>();
    }

    /**
     *Η μέθοδος αυτή αποθηκεύει το αντικείμενο τύπου ManageAccommodations στο αντίστοιχο αρχείο εξόδου.
     */
    public void saveToOutputFiles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("accommodationsManager.bin"))) {
            out.writeObject(this);
            //out.flush();
        } catch (IOException e1) {
            System.out.println("Δεν βρέθηκε αρχείο εξόδου");
        }
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν η λίστα των καταλυμάτων είναι άδεια και αν περιέχεται το κατάλυμα σε
     * αυτήν. Εάν ισχύουν οι δύο αυτές προϋποθέσεις η μέθοδος επιστρέφει false διαφορετικά προσθέτει το
     * κατάλυμα με τις παροχές που προσφέρει.
     * @param name Το όνομα του καταλύματος.
     * @param description Η περιγραφή του καταλύματος.
     * @param stayType Ο τύπος του καταλύματος.
     * @param address Η διεύθυνση του καταλύματος.
     * @param town Η πόλη στην οποία βρίσκεται το κατάλυμα.
     * @param postCode Ο ταχυδρομικός κώδικας της περιοχής που βρίσκεται το κατάλυμα.
     * @param utilities Η λίστα με τις παροχές που προσφέρει το κατάλυμα.
     * @param provider Ο πάροχος του καταλύματος.
     * @return true αν γίνει επιτυχώς η προσθήκη του νέου καταλύματος και false διαφορετικά
     */

    public boolean addAccommodation(String name, String description, String stayType, String address, String town, String postCode, ArrayList<Utility> utilities, Provider provider) {
        Location location = new Location(address, town, postCode);
        Accommodation accommodation = new Accommodation(name, description, stayType, location, provider);
        if (!accommodations.isEmpty()) {
            if (accommodations.contains(accommodation)) {
                return false;
            }
        }
        accommodation.setTypesOfUtilities(utilities);
        accommodations.add(accommodation);
        return true;
    }

    /**
     * Η μέθοδος αυτή αφαιρεί ένα κατάλυμα απο τη λίστα καταλυμάτων εάν η λίστα δεν είναι αδεία και εάν αυτό περιέχεται
     * στη λίστα καταλυμάτων, σε διαφορετική περίπτωση επιστρέφει false.
     * @param accommodation Το κατάλυμα.
     * @return true αν γίνει επιτυχώς η αφαίρεση του νέου καταλύματος και false διαφορετικά
     */
    public boolean removeAccommodation(Accommodation accommodation) {
        if (accommodations.isEmpty())
            return false;
        else if (!accommodations.contains(accommodation))
            return false;
        accommodations.remove(accommodation);
        //Διαχείριση fallout των evaluations με εξτρα συνάρτηση στη ManageEvaluations
        return true;
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την ανανέωση των πληροφοριών/χαρακτηριστικών ενός καταλύματος. Εάν η λίστα
     * είναι άδεια επιστρέφει false διαφορετικά γίνεται μία αναζήτηση στη λίστα των καταλύμματων και όταν βρεθεί το
     * κατάλυμα που αναζητείται γίνονται οι αλλαγές στις πληροφορίες/χαρακτηριστικά του.
     *
     * @param accommodation Το Κατάλυμα.
     * @param name Το όνομα καταλύματος.
     * @param description Η περιγραφή του καταλύματος.
     * @param stayType Ο τύπος καταλύματος.
     * @param address Η διεύθυνση καταλύματος.
     * @param town Η πόλη του καταλύματος.
     * @param postCode Ο ταχυδρομικός κώδικας του καταλύματος.
     * @return True αν γίνει επιτυχώς η επεξεργασία του νέου καταλύματος και false διαφορετικά
     */
    public boolean alterAccommodationDetails(Accommodation accommodation, String name, String description, String stayType, String address, String town, String postCode) {
        Location location = new Location(address, town, postCode);
        if (accommodations.isEmpty())
            return false;
        for (Accommodation accommodation1 : accommodations) {
            if (accommodation.equals(accommodation1)) {
                accommodations.remove(accommodation); // το accommodation υπάρχει οπότε η remove δεν επιστρέφει false
                accommodation.setName(name);
                accommodation.updateSingularId(); //απαραίτητο για να αποφευχθούν συγχύσεις
                accommodation.setDescription(description);
                accommodation.setStayType(stayType);
                accommodation.setPlace(location);
                accommodations.add(accommodation);
                return true;
            }
        }
        return false;
    }

    /**
     * η Μέθοδος αυτή χρησιμοποιείται για την ανανέωση των παροχών ενός καταλύματος.
     * @param accommodation Το κατάλυμα.
     * @param utilities Οι νέες παροχές του καταλύμματος.
     * @return True αν γίνει επιτυχώς η επεξεργασία των παροχών του καταλύματος και false διαφορετικά
     */
    public boolean alterAccommodationUtilities(Accommodation accommodation , ArrayList<Utility> utilities) {
        if (!accommodationExists(accommodation))
            return false;
        for (Accommodation accommodation1 : accommodations) {
            if (accommodation.equals(accommodation1)) {
                accommodations.remove(accommodation); // το accommodation υπάρχει οπότε η remove δεν επιστρέφει false
                accommodation.setTypesOfUtilities(utilities);
                accommodations.add(accommodation);
                return true;
            }
        }
        accommodation.setTypesOfUtilities(utilities);
        return false;
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για τη δημιουργία μιας λίστας για τα καταλύματα ενός παρόχου.
     * @param provider Ο πάροχος
     * @return Τα καταλύματα του παρόχου που δίνεται ως όρισμα.
     */
    public ArrayList<Accommodation> getProvidersAccommodations(Provider provider) {
        ArrayList<Accommodation> providersAccommodations = new ArrayList<>();
        if (!accommodations.isEmpty()) {
            for (Accommodation accommodation : accommodations) {
                if (accommodation.getProvider().equals(provider))
                    providersAccommodations.add(accommodation);
            }
        }
        return providersAccommodations;
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν ένα κατάλυμα υπάρχει στη λίστα όλων των καταλυμάτων.
     * @param accommodation το κατάλυμα που θέλουμε να ελέγξουμε αν υπάρχει
     * @return True αν υπάρχε, false διαφορετικά
     */
    public boolean accommodationExists(Accommodation accommodation) {
        if (accommodations.isEmpty())
            return false;
        return accommodations.contains(accommodation);
    }

    /**
     * Ελέγχει αν ένα κατάλυμα έχει προστεθεί απο τον χρήστη που δίνεται ως όρισμα
     * @param user Πάροχος
     * @param accommodation κατάλυμα που θέλουμε να δούμε αν έχει προστεθεί από τον πάροχο
     * @return True αν το κατάλυμα έχει προστεθεί από τον πάροχο, false διαφορετικά
     */
    public boolean isProvidersAccommodation(User user, Accommodation accommodation) {
        return user.equals(accommodation.getProvider());
    }

    /**
     *Η μέθοδος αυτή ενημερώνει τον μέσο όρο αξιολογήσεων όλων των καταλυμάτων.
     * @param evaluations Οι αξιολογήσεις για όλα τα καταλύματα.
     */
    public void updateAllAvgRatings(ArrayList<Evaluation> evaluations) {
        if (evaluations.isEmpty())
            return;
        for (Accommodation accommodation : accommodations) {
            accommodation.updateAvgRatingOfAccommodation(evaluations);
        }
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν τα πεδία για την καταχώρηση κάποιου καταλύματος έχουν συμπληρωθεί ορθά και επιστρέφει
     * κατάλληλο μήνυμα. Η ορθότητα καταχώρησης κρίνεται από τις πληροφορίες του καταλύματος, των οποίων το μήκος της
     * συμβολοσειράς πρέπει να υπερβαίνει του μηδενός χωρίς τους κενούς χαρακτήρες, του τύπου καταλύματος, το οποίο
     * πρέπει να αντιστοιχείται σε κάποιο από τα τρία διαθέσιμα (Διαμέρισμα, Ξενοδοχείο, Μεζονέτα) και του ταχυδρομικού
     * κώδικα ο οποίος πρέπει να είναι αριθμός μεγαλύτερος του μηδενός. Εάν πληρούνται αυτές οι προϋποθέσεις η μέθοδος
     * επιστρέφει null, ενώ σε διαφορετική περίπτωση επιστρέφεται κατάλληλο μήνυμα λάθους.
     * @param name Το όνομα καταλύματος.
     * @param description Η περιγραφή του καταλύματος.
     * @param stayType Ο τύπος καταλύματος.
     * @param town Η πόλη του καταλύματος.
     * @param address  Η διεύθυνση καταλύματος.
     * @param postCode Ο ταχυδρομικός κώδικας του καταλύματος.
     * @return Κατάλληλο μήνυμα σε περίπτωση λανθασμένης καταχώρησης, διαφορετικά null.
     * @param editing False αν πρόκειται για επεξεργασία καταλύματος για να μη χτυπήσει στη συνωνυμία με τον εαυτό του
     */
    public String checkSubmissionInaccuracies(String name, String description, String stayType, String town, String address, String postCode, boolean editing) { //επιστρέφει null αν όλα καλά
        if (name.trim().length() == 0 || stayType.trim().length() == 0 || description.trim().length() == 0 || town.trim().length() == 0 || postCode.trim().length() == 0 || address.trim().length() == 0) //Η μέθοδος trim() αφαιρεί όλα τα whitespaces ώστε να μην περνάει ως είσοδος το space
            return "Τα πεδία κειμένου είναι υποχρεωτικά για να υποβάλετε επιτυχώς το νέο σας κατάλυμα.";
        else if (!stayType.equals("Ξενοδοχείο")  && !stayType.equals("Διαμέρισμα") && !stayType.equals("Μεζονέτα"))
            return "Παρακαλώ δηλώστε τον τύπο του καταλύματος ως Ξενοδοχείο, Διαμέρισμα ή Μεζονέτα.";
        else if (accommodationNameExists(name) && !editing)
            return "Έχετε ήδη καταχωρήσει κατάλυμα με αυτό το όνομα παρακαλώ επιλέξτε άλλο";
        float postC;
        try {
            postC = Float.parseFloat(postCode);
            if (postC <= 0)
                return "Εισάγετε έγκυρο ταχυδρομικό κώδικα";
        } catch(NumberFormatException e1) {
            return "Παρακαλώ εισάγετε αριθμό στο πεδίο του ταχυδρομικού κώδικα.";
        }
        return null; //περίπτωση του όλα καλά
    }

    /**
     * Ελέγχει αν υπάρχει ήδη κατάλυμα με το όνομα που δίνεται ως όρισμα
     * @param name όνομα προς αναζήτηση στη λίστα των καταλυμάτων
     * @return True αν υπάρχει κατάλυμα με όνομα name, null διαφορετικά
     */
    private boolean accommodationNameExists(String name) {
        if (accommodations.isEmpty())
            return false;
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getName().equals(name))
                return true;
        }
        return false;
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την αναζήτηση καταλυμάτων από τον χρήστη. Είναι έτσι υλοποιημένη ώστε αν
     * κάποια πεδία είναι κενά να λειτουργεί κανονικά η αναζήτηση. Στο τέλος επιστρέφονται όσα καταλύματα ταυτίζονται
     * με τις πληροφορίες των ορισμάτων.
     * @param name Tο όνομα του καταλύματος.
     * @param stayType Ο τύπος του καταλύματος.
     * @param town Η πόλη του καταλύματος.
     * @param utilities Οι παροχές του καταλύματος.
     * @return Λίστα με τα καταλύματα.
     */
    public ArrayList<Accommodation> searchAccommodations(String name, String stayType, String town, ArrayList<Utility> utilities) {
        ArrayList<Accommodation> resultingAccommodations = new ArrayList<>();
        if (accommodations.isEmpty())
            return resultingAccommodations;
        boolean fittingName;
        boolean fittingStayType;
        boolean fittingTown;
        boolean fittingUtilities;
        for (Accommodation accommodation : accommodations) {
            fittingUtilities = true;
            // Αν οι συμβολοσειρές είναι κενές ή έχουν μόνο whitespaces δεν οδηγείται στην απόρριψη του καταλύματος κατά την αναζήτηση
            fittingName = name.trim().length() == 0 || name.equals(accommodation.getName());
            fittingStayType = stayType.trim().length() == 0 || stayType.equals(accommodation.getStayType());
            fittingTown = town.trim().length() ==0 || town.equals(accommodation.getLocation().getTown());

            for (int i=0; i<utilities.size() && fittingUtilities; i++) {
                if (!utilities.get(i).getSpecifics().isEmpty())
                    for (String specificUtility : utilities.get(i).getSpecifics()) {
                        if (!accommodation.getTypesOfUtilities().get(i).getSpecifics().contains(specificUtility)) {
                            fittingUtilities = false;
                            break;
                        }
                    }
            }

            if (fittingTown && fittingName && fittingUtilities && fittingStayType)
                resultingAccommodations.add(accommodation);
        }

        return resultingAccommodations;
    }
}
