package api;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;

/**
 * Η κλάση αυτή περιέχει μεθόδους για την διαχείρηση των χρηστών.
 */


public class ManageUsers implements Serializable {
    private HashSet<SimpleUser> simpleUsers;
    private HashSet<Provider> providers;

    public ManageUsers(){
        simpleUsers = new HashSet<>();
        providers = new HashSet<>();
    }

    /**
     * Η μέθοδος αυτή ελέγχει αν υπάρχει κάποιο αρχείο εξόδου.
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
     * Η μέθοδος αυτή χρησιμοποιείται για την εύρεση κάποιου χρήστη με βάση το username του.Εάν η λίστα
     * του απλού χρήστη ή του πάροχο αντίστοιχα δεν είναι κενή γίνεται αναζήτηση στην λίστα για να βρε-
     * θεί ο χρήστης με βάση το username του.Εάν βρεθεί επιστρέφεται ο χρήστης διαφορετικά η μέθοδος επι-
     * στρέφει null.
     * @param username Το είδικο όνομα χρήστη.
     * @return Ο χρήστης που αναζητείται ή null.
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
     * Η μέθοδος αυτη επιστρέφει τον τύπο χρήστη.
     * @param username Το ειδικό όνομα χρήστη.
     * @return Τον τύπο του χρήστη.
     */

    public String getUserType(String username) {
        return Objects.requireNonNull(findUserWithUsername(username)).getType(); // καλείται μόνο όταν υπάρχει σίγουρα το username οπότε δεν χρειάζεται έξτρα έλεγχος
    }

    /**
     * Η μέθοδος αυτή
     * @param user Ο χρήστης.
     * @return
     */

    public boolean isSimpleUser(User user) {
        return user instanceof SimpleUser;
    }

    public boolean isProvider(User user) {
        return user instanceof Provider;
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την δημιουργία νέου απλού χρήστη και προστίθεται στην λίστα
     * των απλών χρηστών.
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
    }

    /**
     * Η μέθοδος αυτή χρησιμοποιείται για την δημιουργία νέου παρόχου χρήστη και προστίθεται στην λίστα
     *των παρόχων.
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
    }

    /**
     * H μέθοδος αυτή χρησιμοποιείται για την αυθεντικοποιήση του χρήστη κατα την εισοδό του.Δηλαδή την
     * εύρεση του ονόματος με την χρήση προηγούμενης μεθόδου και έπειτα τον έλεγχο για την ορθότητα του
     * πληκτρολογημένου κωδικού.
     * @param username Το ειδικό όνομα.
     * @param password Ο κωδικός.
     * @return False ή true.
     */

    public boolean authentication(String username, String password) {
        User user = findUserWithUsername(username);
        if (user == null)
            return false;
        return user.getPassword().equals(password);
    }

    /**
     * Η μέθοδος αυτή ενημερώνει τον μέσο όρο βαθμολόγησεων που έχει κάνει κάποιος χρήστης και τον μέσο
     * όρο αξιολογήσεων που έχει δεχθεί ένα κατάλυμμα.
     * @param evaluations Η λίστα των αξιολογήσεων.
     */

    public void updateAllAvgRatings(HashSet<Evaluation> evaluations) {
        if (evaluations.isEmpty())
            return;
        for (SimpleUser simpleUser : simpleUsers)
            simpleUser.updateAvgRatingOfUser(evaluations);
        for (Provider provider : providers)
            provider.updateAvgRatingOfAllAccom(evaluations);
    }

    /**
     * Η μέθοδος αυτή ελέγχει
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @return
     */

    public String checkSignUpInaccuracies(String firstName, String lastName, String username, String password) {
        if (firstName.length() == 0 || lastName.length() == 0 || username.length() == 0 || password.length() == 0)
            return "Όλα τα πεδία κειμένου είναι υποχρεωτικά για την εγγραφή";
        if (findUserWithUsername(username) != null)
            return "Το όνομα χρήστη " + username + " υπάρχει ήδη";
        return null;
    }
}
