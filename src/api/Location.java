package api;

import java.io.Serializable;

/**
 * Η κλάση αυτή αφορά την τοποθεσία κάποιου καταλύματος και περιλαμβάνει την πόλη, τη διεύθυνση και τον Τ.Κ.
 */

public class Location implements Serializable {
    private String address;
    private String town;
    private String postCode;

    /**
     * Κατασκευαστής της κλάσης της τοποθεσίας. Αρχικοποιεί τα πεδία address, town και postCode σύμφωνα με το ορίσματα
     * που δίνονται. Πριν την κλήση του κατασκευαστή ελέγχεται αν ο Τ.Κ. που δίνεται ως συμβολοσειρά είναι όντως
     * αριθμός μεγαλύτερος του μηδενός
     * @param address Διεύθυνση
     * @param town Πόλη
     * @param postCode Ταχυδρομικός κώδικας
     */
    public Location(String address, String town, String postCode) {
        this.address = address;
        this.town = town;
        this.postCode = postCode;
    }

    /**
     * Επιστρέφει τη διεύθυνση της τοποθεσίας
     * @return διεύθυνση
     */
    public String getAddress() {
        return address;
    }

    /**
     * Αλλάζει τη διεύθυνση σε όποια συμβολοσειρά δίνεται ως όρισμα
     * @param address νέα διεύθυνση
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Επιστρέφει το όνομα της πόλης της τοποθεσίας
     * @return όνομα πόλης
     */
    public String getTown() {
        return town;
    }

    /**
     * Αλλάζει το όνομα της πόλης σε όποια συμβολοσειρά δίνεται ως όρισμα.
     * @param town νέο όνομα πόλης
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     * Επιστρέφει τον ταχυδρομικό κώδικα της τοποθεσίας
     * @return Τ.Κ.
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Αλλάζει τον Τ.Κ. σε όποια συμβολοσειρά δίνεται ως όρισμα.
     * @param postCode νέος Τ.Κ.
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
