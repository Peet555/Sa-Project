package ku.cs.models;

public class User {
    private String ID;
    private String username;

    public User(String ID, String username) {
        this.ID = ID;
        this.username = username;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
