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
     * @return
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
     * Η μέθοδος αυτή ελέγχει εάν η λίστα καταλυμάτων είναι άδεια
     * @param accommodation
     * @return
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
                accommodation.updateSingularId(); //Απαραίτητο σε περίπτωση που αλλάξει το όνομα του καταλύματος για να διατηρηθεί η μοναδικότητα του id
                accommodations.add(accommodation);
                return true;
            }
        }
        return false;
    }

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

    public boolean accommodationExists(Accommodation accommodation) {
        if (accommodations.isEmpty())
            return false;
        return accommodations.contains(accommodation);
    }


    public boolean isProvidersAccommodation(User user, Accommodation accommodation) {
        return user.equals(accommodation.getProvider());
    }

    public void updateAllAvgRatings(ArrayList<Evaluation> evaluations) {
        if (evaluations.isEmpty())
            return;
        for (Accommodation accommodation : accommodations) {
            accommodation.updateAvgRatingOfAccommodation(evaluations);
        }
    }

    public String checkSubmissionInaccuracies(String name, String description, String stayType, String town, String address, String postCode) { //επιστρέφει null αν όλα καλά
        if (name.trim().length() == 0 || stayType.trim().length() == 0 || description.trim().length() == 0 || town.trim().length() == 0 || postCode.trim().length() == 0 || address.trim().length() == 0) //Η μέθοδος trim() αφαιρεί όλα τα whitespaces ώστε να μην περνάει ως είσοδος το space
            return "Τα πεδία κειμένου είναι υποχρεωτικά για να υποβάλετε επιτυχώς το νέο σας κατάλυμα.";
        else if (!stayType.equals("Ξενοδοχείο")  && !stayType.equals("Διαμέρισμα") && !stayType.equals("Μεζονέτα"))
            return "Παρακαλώ δηλώστε τον τύπο του καταλύματος ως Ξενοδοχείο, Διαμέρισμα ή Μεζονέτα.";
        else if (accommodationNameExists(name))
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

    private boolean accommodationNameExists(String name) {
        if (accommodations.isEmpty())
            return false;
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getName().equals(name))
                return true;
        }
        return false;
    }


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
