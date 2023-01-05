package api;

import java.io.Serializable;

/**
 * Abstract γενική κλάση χρήστη.
 */
public abstract class User implements Serializable { //βγάζει νόημα να είναι abstract γιατί δεν πρόκειται να δημιουργηθούν αντικείμενα User
    // εδώ ίσως το αλλάξουμε ώστε να έχουμε και τα όνομα, επίθετο σε ξεχωριστές μεταβλητές αλλά μπορεί και να μη χρειαστεί αν υπάρχει καλή μέθοδος parsing για string
    protected final String firstName;
    protected final String lastName;
    protected final String userName;
    protected String password;
    protected final String type; // "simpleUser" or "provider"

    /**
     * Κατασκευαστής της κλάσης User. Αρχικοποιεί το αντικείμενο του απλού χρήστη θέτοντας τα πεδία firstName,
     *      * lastName, username και password ανάλογα με τα ορίσματα που δίνονται.
     * @param firstName μικρό όνομα χρήστη
     * @param lastName επίθετο χρήστη
     * @param userName username χρήστη
     * @param password κωδικός χρήστη
     * @param type τύπος χρήστη
     */
    public User(String firstName, String lastName, String userName, String password, String type) {
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Επιστρέφει το username του χρήστη.
     * @return username του χρήστη
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Επιστρέφει τον κωδικό του χρήστη.
     * @return κωδικός του χρήστη
     */
    public String getPassword() {
        return password;
    }

    /**
     * Αλλάζει τον κωδικό του χρήστη για πιθανή μελλοντική υλοποίηση διαδικασίας αλλαγής κωδικού.
     * @param password νέος κωδικός
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Επιστρέφει τον τύπο του χρήστη.
     * @return τύπος του χρήστη.
     */
    public String getType() {
        return type;
    }

    /**
     * Επιστρέφει το μικρό όνομα του χρήστη
     * @return μικρό όνομα του χρήστη
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Επιστρέφει το επίθετο του χρήστη.
     * @return επίθετο του χρήστη
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Ελέγχεται η ισότητα δύο αντικειμένων υποκλάσεων της User (αφού η ίδια η User είναι abstract). Αν δεν έχουν την
     * ίδια θέση μνήμης και ανήκουν και τα δύο σε υποκλάσεις της User τότε ελέγχεται η ισότητα των username καθώς είναι
     * μοναδικά.
     * @param o το αντικείμενο που θέλουμε να συγκρίνουμε με το this
     * @return true αν ισχύει η ισότητα των δύο αντικειμένων
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getUserName().equals(user.getUserName());
    }

}
