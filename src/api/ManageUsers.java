package api;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

public class ManageUsers implements Serializable {
    private HashSet<SimpleUser> simpleUsers;
    private HashSet<Provider> providers;

    public ManageUsers(){
        simpleUsers = new HashSet<>();
        providers = new HashSet<>();
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

    private boolean checkSynonymity(User user) { // true αν o νέος χρήστης που πάει να δημιουργηθεί έχει ίδιο username με άλλον
        return findUserWithUsername(user.getUserName()) != null;
    }

    public boolean createSimpleUser(String firstName, String lastName ,String userName, String password) {
        SimpleUser user = new SimpleUser(firstName, lastName, userName, password, "simpleUser");
        if (checkSynonymity(user))
            return false; // username already exists
        simpleUsers.add(user);
        return true;
    }

    public boolean createProvider(String firstName, String lastName ,String userName, String password) {
        Provider user = new Provider(firstName, lastName, userName, password, "provider");
        if (checkSynonymity(user))
            return false; // username already exists
        providers.add(user);
        return true;
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
}
