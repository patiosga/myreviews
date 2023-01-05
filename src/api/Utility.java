package api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Η κλάση αυτή αφορά τις παροχές που προσφέρουν τα καταλύμματα.
 */

public class Utility implements Serializable {

    private ArrayList<String> specifics;

    public Utility() {
        specifics = new ArrayList<>();
    }

    /**
     * Η μέθοδος αυτή προσθέτει μια καινούρια παροχή.
     * @param somethingNew Η νέα παροχή.
     * @return True ή false.
     */

    public boolean addSpecificUtility(String somethingNew) { //θα προστεθούν στα typesOfUtilities του κάθε καταλύματος ανάλογα με το τι θα δίνεται στα αρχεία
        if (!specifics.isEmpty()) {
            if (specifics.contains(somethingNew)) {
                return false;
            }
        }
        specifics.add(somethingNew);
        return true;
    }

    /**
     * Η μέθοδος αυτή αφαιρεί μια παροχή.
     * @param toRemove Η παροχή που πρόκειται να διαγραφεί.
     * @return True ή false.
     */

    public boolean removeSpecificUtility(String toRemove) {
        if (!specifics.isEmpty()) {
            if (specifics.contains(toRemove)) {
                specifics.remove(toRemove);
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getSpecifics() {
        return specifics;
    }

    public void setSpecifics(ArrayList<String> specifics) {
        this.specifics = specifics;
    }
}
