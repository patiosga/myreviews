package api;

import java.io.Serializable;
import java.util.HashSet;

public class Utilities implements Serializable {
    private HashSet<String> specifics;

    public Utilities() {
        specifics = new HashSet<>();
    }

    public boolean addSpecificUtility(String somethingNew) { //θα προστεθούν στα typesOfUtilities του κάθε καταλύματος ανάλογα με το τι θα δίνεται στα αρχεία
        if (!specifics.isEmpty()) {
            if (specifics.contains(somethingNew)) {
                return false;
            }
        }
        specifics.add(somethingNew);
        return true;
    }

    public boolean removeSpecificUtility(String toRemove) {
        if (!specifics.isEmpty()) {
            if (specifics.contains(toRemove)) {
                specifics.remove(toRemove);
                return true;
            }
        }
        return false;
    }

    public HashSet<String> getSpecifics() {
        return specifics;
    }
}
