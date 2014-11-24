package lecture3.example2;

public class SecurityManager {

    private String username;

    public boolean login(String username, String password) {
        if ("john".equals(username) && "P@ssw0rd".equals(password) ||
            "mary".equals(username) && "1q2w3e4r".equals(password)) {
            this.username = username;
            return true;
        }
        return false;
    }

    public void logout() {
        username = null;
    }

    public String getUsername() {
        return username;
    }
}
