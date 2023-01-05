package api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Η κλάση αυτή αφορά τις παροχές που προσφέρουν τα καταλύματα.
 */

public class Utility implements Serializable {

    private ArrayList<String> specifics; //λίστα συμβολοσειρών με τις παροχές

    public Utility() {
        specifics = new ArrayList<>();
    }

    /**
     * Η μέθοδος αυτή προσθέτει μια καινούρια παροχή αν δεν υπάρχει ήδη.
     * @param somethingNew Η νέα παροχή.
     * @return True αν έγινε επιτυχώς η προσθήκη της νέας παροχής, false διαφορετικά.
     */
    public boolean addSpecificUtility(String somethingNew) {
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
     * @return True αν η παροχή υπήρχε και αφαιρέθηκε, false διαφορετικά.
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

    /**
     * Επιστρέφει τις συγκεκριμένες παροχές του αντικειμένου. Δηλαδή, αν το παρών αντικείμενο είναι Utility view, τότε
     * η λίστα specifics θα περιλαμβάνει "Θέα σε θάλασσα", "Θέα στο λιμάνι" κτλ.
     * @return τη λίστα των συγκεκριμένων παροχών
     */
    public ArrayList<String> getSpecifics() {
        return specifics;
    }
}
