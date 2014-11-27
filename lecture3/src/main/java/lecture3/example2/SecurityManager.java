package lecture3.example2;

public class SecurityManager {

    private ThreadLocal<String> user =
            new ThreadLocal<>();

    public boolean login(String user, String pass) {
        if ("john".equals(user) && "P@ssw0rd".equals(pass) ||
            "mary".equals(user) && "1q2w3e4r".equals(pass)) {
            this.user.set(user);
            return true;
        }
        return false;
    }

    public void logout() {
        user.set(null);
    }

    public String getUser() {
        return user.get();
    }
}
