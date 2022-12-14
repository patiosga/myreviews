package api;

import java.util.HashSet;
import java.util.Objects;

public class ManageUsers {
    HashSet<SimpleUser> simpleUsers;
    HashSet<Provider> providers;

    public ManageUsers(){
        simpleUsers = new HashSet<>();
        providers = new HashSet<>();
    }

    public User findUserWithUsername(String username) {
        if (!simpleUsers.isEmpty()) {
            for (SimpleUser simpleUser : simpleUsers) {
                if (username == simpleUser.getUserName())
                    return simpleUser;
            }
        }
        if (!providers.isEmpty()) {
            for (Provider provider : providers) {
                if (username == provider.getUserName())
                    return provider;
            }
        }
        return null;
    }

    public String getUserType(String username) {
        return Objects.requireNonNull(findUserWithUsername(username)).getType(); // καλείται μόνο όταν υπάρχει σίγουρα το username οπότε δεν χρειάζεται έξτρα έλεγχος
    }

    public boolean isSimpleUser(User user) {
        if (!simpleUsers.isEmpty()) {
            return simpleUsers.contains(user);
        }
        return false;
    }

    public boolean isProvider(User user) {
        if (!simpleUsers.isEmpty()) {
            return providers.contains(user);
        }
        return false;
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
        return user.getPassword() == password;

    }

}
