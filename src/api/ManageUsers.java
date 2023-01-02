package api;

import java.io.*;
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

    public String getUserType(String username) {
        return Objects.requireNonNull(findUserWithUsername(username)).getType(); // καλείται μόνο όταν υπάρχει σίγουρα το username οπότε δεν χρειάζεται έξτρα έλεγχος
    }

    public boolean isSimpleUser(User user) {
        return user instanceof SimpleUser;
    }

    public boolean isProvider(User user) {
        return user instanceof Provider;
    }


    public SimpleUser createSimpleUser(String firstName, String lastName ,String userName, String password) {
        SimpleUser user = new SimpleUser(firstName, lastName, userName, password, "simpleUser");
        simpleUsers.add(user);
        return user;
    }

    public Provider createProvider(String firstName, String lastName ,String userName, String password) {
        Provider user = new Provider(firstName, lastName, userName, password, "provider");
        providers.add(user);
        return user;
    }

    public boolean authentication(String username, String password) {
        User user = findUserWithUsername(username);
        if (user == null)
            return false;
        return user.getPassword().equals(password);
    }

    public void updateAllAvgRatings(HashSet<Evaluation> evaluations) {
        if (evaluations.isEmpty())
            return;
        for (SimpleUser simpleUser : simpleUsers)
            simpleUser.updateAvgRatingOfUser(evaluations);
        for (Provider provider : providers)
            provider.updateAvgRatingOfAllAccom(evaluations);
    }

    public String checkSignUpInaccuracies(String firstName, String lastName, String username, String password) {
        if (firstName.length() == 0 || lastName.length() == 0 || username.length() == 0 || password.length() == 0)
            return "Όλα τα πεδία κειμένου είναι υποχρεωτικά για την εγγραφή";
        if (findUserWithUsername(username) != null)
            return "Το όνομα χρήστη " + username + " υπάρχει ήδη";
        return null;
    }
}
