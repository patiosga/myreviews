package api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class ManageAccommodations implements Serializable {
    private ArrayList<Accommodation> accommodations;

    public ManageAccommodations() {
        accommodations = new ArrayList<>();
    }

    public void saveToOutputFiles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("accommodationsManager.bin"))) {
            out.writeObject(this);
            //out.flush();
        } catch (IOException e1) {
            System.out.println("Δεν βρέθηκε αρχείο εξόδου");
        }
    }

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
        saveToOutputFiles();
        return true;
    }

    public boolean removeAccommodation(Accommodation accommodation) {
        if (accommodations.isEmpty())
            return false;
        else if (!accommodations.contains(accommodation))
            return false;
        accommodations.remove(accommodation);
        //Διαχείριση fallout των evaluations με εξτρα συνάρτηση στη ManageEvaluations
        saveToOutputFiles();
        return true;
    }

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
                saveToOutputFiles();
                return true;
            }
        }
        return false;
    }

    public boolean alterAccommodationUtilities(Accommodation accommodation , ArrayList<Utility> utilities) {
        if (!accommodationExists(accommodation))
            return false;
        accommodation.setTypesOfUtilities(utilities);
        saveToOutputFiles();
        return true;
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

    public void updateAllAvgRatings(HashSet<Evaluation> evaluations) {
        if (evaluations.isEmpty())
            return;
        for (Accommodation accommodation : accommodations) {
            accommodation.updateAvgRatingOfAccommodation(evaluations);
        }
        saveToOutputFiles();
    }

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
