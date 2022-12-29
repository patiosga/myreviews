package api;

import java.io.Serializable;
import java.util.ArrayList;

public class Utility implements Serializable {
    private ArrayList<String> specifics;

    public Utility() {
        specifics = new ArrayList<>();
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

    public ArrayList<String> getSpecifics() {
        return specifics;
    }
}
