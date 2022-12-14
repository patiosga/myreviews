package api;

public abstract class User { //βγάζει νόημα να είναι abstract γιατί δεν πρόκειται να δημιουργηθούν αντικείμενα User
    // εδώ ίσως το αλλάξουμε ώστε να έχουμε και τα όνομα, επίθετο σε ξεχωριστές μεταβλητές αλλά μπορεί και να μη χρειαστεί αν υπάρχει καλή μέθοδος parsing για string
    protected final String firstName;
    protected final String lastName;
    protected final String userName;
    protected String password;
    protected final String type; // "simpleUser" or "provider"

    public User(String firstName, String lastName, String userName, String password, String type) {
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }



}
