package api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Η κλάση αυτή περιέχει μεθόδους για την διαχείρηση των καταλυμάτων.
 */

public class ManageAccommodations implements Serializable {
    private ArrayList<Accommodation> accommodations;

    public ManageAccommodations() {
        accommodations = new ArrayList<>();
    }

    /**
     *Η μέθοδος αυτή ελέγχει αν υπάρχει κάποιο αρχείο εξόδου.
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
     * Η μέθοδος αυτη ελέγχει εάν η λίστα των καταλυμμάτων είναι άδεια και αν περιέχεται το κατάλυμμα σε
     * αυτήν.Εάν ισχύουν οι δύο αυτές προυποθέσεις η μέθοδος επιστρέφει false διαφορετικά προσθέτει το
     * κατάλυμα με τις παροχές που προσφέρει.
     * @param name Το όνομα του καταλύματος.
     * @param description Η περίγραφη του καταλύματος.
     * @param stayType Ο τύπος του καταλύματος.
     * @param address Η διεύθυνση του καταλύματος.
     * @param town Η πόλη στην οποία βρίσκεται το κατάλυμα.
     * @param postCode Ο ταχυδρομικός κώδικας της περιοχής που βρίσκεται το κατάλυμα.
     * @param utilities Οι παροχές που προσφέρει το κατάλυμα.
     * @param provider Ο πάροχος του καταλύματος.
     * @return Επιστρέφει true ή false.
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
     * Η μέθοδος αυτή  αφαιρεί ένα κατάλυμμα απο την λίστα καταλυμμάτων εάν η λίστα δεν είναι αδεία ή εάν αυτό περιεχέται στην λίστα καταλυμ-
     * μάτων, σε διαφορετική περίπτωση επιστρέφει false.
     * @param accommodation Το κατάλυμμα.
     * @return False ή true
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
     * Η μέθοδος αυτή χρησιμοποιείται για την ανανέωση των πληροφορίων/χαρακτηριστικών ενός καταλύμματος.Εάν η λίστα είναι άδεια επιστρέφει false
     * διαφορετικά γίνεται μία αναζήτη στην λίστα των καταλύμματων και όταν βρεθεί το κατάλυμμα που αναζητείται γίνονται οι αλλαγές στις πληροφο-
     * ρίες/χαρακτηριστικά του.
     *
     * @param accommodation Το Κατάλυμμα.
     * @param name Το όνομα καταλύμματος.
     * @param description Η περιγραφή του καταλύμματος.
     * @param stayType Ο τύπος καταλύμματος.
     * @param address Η διεύθυνση καταλύμματος.
     * @param town Η πόλη του καταλύμματος.
     * @param postCode Ο ταχυδρομικός κώδικας του καταλύμματος.
     * @return True ή False.
     */

    public boolean alterAccommodationDetails(Accommodation accommodation, String name, String description, String stayType, String address, String town, String postCode) {
        Location location = new Location(address, town, postCode);
        if (accommodations.isEmpty())
            return false;
        for (Accommodation accommodation1 : accommodations) {
            if (accommodation.equals(accommodation1)) {
                accommodations.remove(accommodation1);
                accommodation1.setName(name);
                accommodation1.updateSingularId(); //απαραίτητο για να αποφευχθούν συγχύσεις
                accommodation1.setDescription(description);
                accommodation1.setStayType(stayType);
                accommodation1.setPlace(location);
                accommodations.add(accommodation1);
                return true;
            }
        }
        return false;
    }

    /**
     * η Μέθοδος αυτή χρησιμοποιείται για την ανανέωση των παροχών ενός καταλύμματος.
     * @param accommodation Το κατάλυμμα.
     * @param utilities Οι παροχές του καταλύμματος.
     * @return True ή False.
     */

    public boolean alterAccommodationUtilities(Accommodation accommodation , ArrayList<Utility> utilities) {
        if (!accommodationExists(accommodation))
            return false;
        accommodation.setTypesOfUtilities(utilities);
        return true;
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την δημιουργία μιας λίστας για τα καταλύμματα ενός παρόχου.
     * @param provider Ο πάροχος
     * @return Τα καταλύμματα ενός παρόχου.
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
     * Η μέθοδος αυτή ελέγχει εάν ένα κατάλυμμα υπάρχει στην λίστα.
     * @param accommodation
     * @return
     */

    public boolean accommodationExists(Accommodation accommodation) {
        if (accommodations.isEmpty())
            return false;
        return accommodations.contains(accommodation);
    }

    /**
     * Η μέθοδος αυτή
     * @param user
     * @param accommodation
     * @return
     */


    public boolean isProvidersAccommodation(User user, Accommodation accommodation) {
        return user.equals(accommodation.getProvider());
    }

    /**
     *Η μέθοδος αυτή ενημερώνει τον μέσο όρο αξιολογήσεων των καταλυμμάτων.
     * @param evaluations Οι αξιολογήσεις.
     */

    public void updateAllAvgRatings(HashSet<Evaluation> evaluations) {
        if (evaluations.isEmpty())
            return;
        for (Accommodation accommodation : accommodations) {
            accommodation.updateAvgRatingOfAccommodation(evaluations);
        }
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν τα πέδια για την καταχώρηση κάποιου καταλύμματος έχουν συμπληρωθεί ορθά,και επιστρέφει κατάλληλο μήνυμα.Η ο-
     * ρθότητα καταχώρησης κρίνεται από τις πληροφορίες του καταλύμματος,των οποίων το μήκος της γραμματοσειράς πρέπει να υπερβαίνει του μηδενός,
     * του τύπου καταλύμματος το οποίο πρεπει να αντιστοιχείται σε κάποιο από τα τρία διαθέσιμα και του ταχυδρόμικου κώδικα ο οποίος πρέπει να
     * είναι μεγαλύτερος του μηδενός.Εάν πληρούνται αυτές οι προυποθέσεις η μέθοδος επιστρέφει null,σε διαφορετική περίπτωση επιστρέφεται κατάλληλο
     * μήνυμα.
     * @param name Το όνομα καταλύμματος.
     * @param description Η περιγραφή του καταλύμματος.
     * @param stayType Ο τύπος καταλύμματος.
     * @param town Η πόλη του καταλύμματος.
     * @param address  Η διεύθυνση καταλύμματος.
     * @param postCode Ο ταχυδρομικός κώδικας του καταλύμματος.
     * @return Κατάλληλο μήνυμα σε πέριπτωση λανθασμένης καταχώρησης,διαφορετικά null.
     */

    public String checkSubmissionInaccuracies(String name, String description, String stayType, String town, String address, String postCode) { //επιστρέφει null αν όλα καλά
        if (name.length() == 0 || stayType.length() == 0 || description.length() == 0 || town.length() == 0 || postCode.length() == 0 || address.length() == 0)
            return "Τα πεδία κειμένου είναι υποχρεωτικά για να υποβάλετε επιτυχώς το νέο σας κατάλυμα.";
        else if (!stayType.equals("Ξενοδοχείο")  && !stayType.equals("Διαμέρισμα") && !stayType.equals("Μεζονέτα"))
            return "Παρακαλώ δηλώστε τον τύπο του καταλύματος ως Ξενοδοχείο, Διαμέρισμα ή Μεζονέτα." + stayType + ".";
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
     * Η μέθοδος αυτή χρησιμοποιείται για την αναζήτηση καταλυμμάτων
     * @param name Tο όνομα του καταλύμματος.
     * @param stayType Ο τύπος του καταλύμματος.
     * @param town Η πόλη του καταλύμματος.
     * @param utilities Οι παροχές του καταλύμματος.
     * @return Λίστα με τα καταλύμματα.
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
            // Αν οι συμβολοσειρές είναι κενές δεν οδηγείται στην απόρριψη του καταλύματος κατά την αναζήτηση
            fittingName = name.length() == 0 || name.equals(accommodation.getName());
            fittingStayType = stayType.length() == 0 || stayType.equals(accommodation.getStayType());
            fittingTown = town.length() ==0 || town.equals(accommodation.getLocation().getTown());

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
