package api;

public class User {
    //εδώ ίσως το αλλάξουμε ώστε να έχουμε και τα όνομα, επίθετο σε ξεχωριστές μεταβλητές αλλά μπορεί και να μη χρειαστεί αν υπάρχει καλή μέθοδος parsing για string
    protected String userName;
    protected String password;
    protected String type;

    public User(String userName, String password, String type) {
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setType(String type) {
        this.type = type;
    }
}
