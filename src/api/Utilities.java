package api;

import java.util.HashSet;

public class Utilities {
    private HashSet<String> specifics;

    public Utilities() {
        specifics = new HashSet<>();
    }

    public void addSpecificUtility(String somethingNew) { //θα προστεθούν στα typesOfUtilities του κάθε καταλύματος ανάλογα με το τι θα δίνεται στα αρχεία
        specifics.add(somethingNew);
    }

    public boolean removeSpecificUtility(String toRemove) {
        if (specifics.contains(toRemove)) {
            specifics.remove(toRemove);
            return true;
        }
        return false;
    }




}
