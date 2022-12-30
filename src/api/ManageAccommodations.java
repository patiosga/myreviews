package api;

import java.util.ArrayList;
import java.util.HashSet;

public class ManageAccommodations {
    private ArrayList<Accommodation> accommodations;

    public ManageAccommodations() {
        accommodations = new ArrayList<>();
    }

    public boolean addAccommodation(String name, String description, String stayType, String address, String town, String postCode, Provider provider) {
        Location location = new Location(address, town, postCode);
        Accommodation accommodation = new Accommodation(name, description, stayType, location, provider);
        if (!accommodations.isEmpty()) {
            if (accommodations.contains(accommodation)) {
                return false;
            }
        }
        accommodations.add(accommodation);
        return true;
    }

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
                accommodations.remove(accommodation1);
                accommodation1.setName(name);
                accommodation1.setDescription(description);
                accommodation1.setStayType(stayType);
                accommodation1.setPlace(location);
                accommodations.add(accommodation1);
                return true;
            }
        }
        return false;
    }

    public boolean alterAccommodationUtilities(Accommodation accommodation , ArrayList<Utility> utilities) {
        if (!accommodationExists(accommodation))
            return false;
        accommodation.setTypesOfUtilities(utilities);
        return true;
    }

    public ArrayList<Accommodation> getProvidersAccommodations(Provider provider) {
        ArrayList<Accommodation> providersAccommodations = new ArrayList<>();
        if (!accommodations.isEmpty()) {
            for (Accommodation accommodation : accommodations) {
                if (accommodation.getProvider().equals(provider));
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
}
