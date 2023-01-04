package api;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class ManageUsers implements Serializable {
    private HashSet<SimpleUser> simpleUsers;
    private HashSet<Provider> providers;

    public ManageUsers(){
        simpleUsers = new HashSet<>();
        providers = new HashSet<>();
    }

    public void saveToOutputFiles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("usersManager.bin"))) {
            out.writeObject(this);
            //out.flush();
        } catch (IOException e1) {
            System.out.println("Δεν βρέθηκε αρχείο εξόδου");
        }
    }

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

    public boolean isSimpleUser(User user) {
        return user instanceof SimpleUser;
    } //ελέγχει τον χρήστη που επιστρέφει η findUserWithUserName στο login

    public boolean isProvider(User user) {
        return user instanceof Provider;
    } //Δε χρησιμοποιείται γιατί αν ένας χρήστης δεν είναι SimpleUser Θα είναι Provider οπότε δεν απαιτείται έξτρα έλεγχος στο authentication (αν προστεθούν και άλλοι τύποι χρηστών θα είναι απαραίτητο


    public SimpleUser createSimpleUser(String firstName, String lastName ,String userName, String password) {
        SimpleUser user = new SimpleUser(firstName, lastName, userName, password, "simpleUser");
        simpleUsers.add(user);
        return user;
    } //Δεν ελέγχεται αν το username υπάρχει ήδη γιατί ο έλεγχος γίνεται στο checkSignUpInaccuracies

    public Provider createProvider(String firstName, String lastName ,String userName, String password) {
        Provider user = new Provider(firstName, lastName, userName, password, "provider");
        providers.add(user);
        return user;
    } //Δεν ελέγχεται αν το username υπάρχει ήδη γιατί ο έλεγχος γίνεται στο checkSignUpInaccuracies

    public boolean authentication(String username, String password) {
        User user = findUserWithUsername(username);
        if (user == null)
            return false;
        return user.getPassword().equals(password);
    }

    public String checkSignUpInaccuracies(String firstName, String lastName, String username, String password) {
        if (firstName.trim().length() == 0 || lastName.trim().length() == 0 || username.trim().length() == 0 || password.trim().length() == 0) //Η μέθοδος trim() αφαιρεί όλα τα whitespaces ώστε να μην περνάει ως είσοδος το space
            return "Όλα τα πεδία κειμένου είναι υποχρεωτικά για την εγγραφή";
        if (findUserWithUsername(username) != null)
            return "Το όνομα χρήστη " + username + " υπάρχει ήδη";
        return null;
    }
}
