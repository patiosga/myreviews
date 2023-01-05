package api;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;


/**
 * Η κλάση αυτή περιέχει μεθόδους για τη διαχείριση των χρηστών.
 */
public class ManageUsers implements Serializable {
    private HashSet<SimpleUser> simpleUsers;
    private HashSet<Provider> providers;

    /**
     * Κατασκευαστής της κλάσης ManageUsers. Αρχικοποιεί τα HashSets των απλών χρηστών και των παρόχων.
     */
    public ManageUsers(){
        simpleUsers = new HashSet<>();
        providers = new HashSet<>();
    }

    /**
     * Αποθηκεύει το παρόν αντικείμενο στο αντίστοιχο αρχείο εξόδου.
     */
    public void saveToOutputFiles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("usersManager.bin"))) {
            out.writeObject(this);
            //out.flush();
        } catch (IOException e1) {
            System.out.println("Δεν βρέθηκε αρχείο εξόδου");
        }
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την εύρεση κάποιου χρήστη με βάση το username του. Εάν βρεθεί επιστρέφεται το
     * αντικείμενο του αντίστοιχου χρήστη, διαφορετικά η μέθοδος επιστρέφει null.
     * @param username Το username του χρήστη.
     * @return Ο χρήστης που αναζητείται ή null αν δε βρεθεί.
     */
    public User findUserWithUsername(String username) {
        if (!simpleUsers.isEmpty()) {
            for (SimpleUser simpleUser : simpleUsers) {
                if (Objects.equals(username, simpleUser.getUserName()))
                    return simpleUser;
            }
        }
        if (!providers.isEmpty()) {
            for (Provider provider : providers) {
                if (Objects.equals(username, provider.getUserName()))
                    return provider;
            }
        }
        return null;
    }

    /**
     * Η μέθοδος ελέγχει αν το αντικείμενο του χρήστη που δίνεται ως όρισμα είναι απλός χρήστης.
     * @param user Το αντικείμενο του χρήστη.
     * @return True αν το αντικείμενο του χρήστη που δίνεται ως όρισμα είναι απλός χρήστης, false διαφορετικά.
     */
    public boolean isSimpleUser(User user) {
        return user instanceof SimpleUser;
    } //ελέγχει τον χρήστη που επιστρέφει η findUserWithUserName στο login

    /**
     * Η μέθοδος ελέγχει αν το αντικείμενο του χρήστη που δίνεται ως όρισμα είναι πάροχος.
     * @param user Το αντικείμενο του χρήστη.
     * @return True αν το αντικείμενο του χρήστη που δίνεται ως όρισμα είναι πάροχος, false διαφορετικά.
     */
    public boolean isProvider(User user) {
        return user instanceof Provider;
    } //Δε χρησιμοποιείται γιατί αν ένας χρήστης δεν είναι SimpleUser Θα είναι Provider οπότε δεν απαιτείται έξτρα έλεγχος στο authentication (αν προστεθούν και άλλοι τύποι χρηστών θα είναι απαραίτητο

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για τη δημιουργία νέου απλού χρήστη.
     * @param firstName Το μικρό όνομα χρήστη.
     * @param lastName Το επώνυμο όνομα χρήστη.
     * @param userName Το ειδικό όνομα χρήστη.
     * @param password Ο κωδικός.
     * @return Χρήστη.
     */
    public SimpleUser createSimpleUser(String firstName, String lastName ,String userName, String password) {
        SimpleUser user = new SimpleUser(firstName, lastName, userName, password, "simpleUser");
        simpleUsers.add(user);
        return user;
    } //Δεν ελέγχεται αν το username υπάρχει ήδη γιατί ο έλεγχος γίνεται στο checkSignUpInaccuracies

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για τη δημιουργία νέου παρόχου.
     * @param firstName Το μικρό όνομα χρήστη.
     * @param lastName Το επώνυμο όνομα χρήστη.
     * @param userName Το ειδικό όνομα χρήστη.
     * @param password Ο κωδικός.
     * @return Χρήστη.
     */
    public Provider createProvider(String firstName, String lastName ,String userName, String password) {
        Provider user = new Provider(firstName, lastName, userName, password, "provider");
        providers.add(user);
        return user;
    } //Δεν ελέγχεται αν το username υπάρχει ήδη γιατί ο έλεγχος γίνεται στο checkSignUpInaccuracies

    /**
     * H μέθοδος αυτή χρησιμοποιείται για την επαλήθευση των στοιχείων του χρήστη κατά την είσοδό του.
     * Γίνεται η εύρεση του username με τη χρήση της findUserWithUsername() και έπειτα τον ελέγχεται η ορθότητα του
     * πληκτρολογημένου κωδικού.
     * @param username Το username που δόθηκε από τον χρήστη.
     * @param password Ο κωδικός.
     * @return True αν υπάρχει χρήστης με τέτοιο username και έχει δοθεί ο σωστός κωδικός, false διαφορετικά.
     */
    public boolean authentication(String username, String password) {
        User user = findUserWithUsername(username);
        if (user == null)
            return false;
        return user.getPassword().equals(password);
    }

    /**
     * Η μέθοδος αυτή ελέγχει εάν τα πεδία για την εγγραφή κάποιου χρήστη έχουν συμπληρωθεί ορθά και επιστρέφει
     * κατάλληλο μήνυμα. Η ορθότητα εγγραφής κρίνεται από το αν τα στοιχεία του χρήστη έχουν μήκος συμβολοσειράς
     * μεγαλύτερο του μηδενός (χωρίς τους κενούς χαρακτήρες) και από το αν το username που δίνεται είναι
     * μοναδικό. Σε διαφορετική περίπτωση να επιστρέφει κατάλληλο μήνυμα.
     * @param firstName Μικρό όνομα νέου χρήστη
     * @param lastName Επίθετο νέου χρήστη
     * @param username username νέου χρήστη
     * @param password κωδικός νέου χρήστη
     * @return null αν πέρασε όλους τους ελέγχους, διαφορετικά κατάλληλο μήνυμα.
     */
    public String checkSignUpInaccuracies(String firstName, String lastName, String username, String password) {
        if (firstName.trim().length() == 0 || lastName.trim().length() == 0 || username.trim().length() == 0 || password.trim().length() == 0) //Η μέθοδος trim() αφαιρεί όλα τα whitespaces ώστε να μην περνάει ως είσοδος το space
            return "Όλα τα πεδία κειμένου είναι υποχρεωτικά για την εγγραφή";
        if (findUserWithUsername(username) != null)
            return "Το όνομα χρήστη " + username + " υπάρχει ήδη";
        return null;
    }
}
