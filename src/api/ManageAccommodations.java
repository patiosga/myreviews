package api;

import java.util.HashSet;

public class ManageAccommodations {
    private HashSet<Accommodation> accommodations;

    public ManageAccommodations() {
        accommodations = new HashSet<>();
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

    public boolean alterAccommodationUtilities(Accommodation accommodation) {
        return true;
    }



}
