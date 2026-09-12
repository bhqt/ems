import cn.dev33.satoken.secure.BCrypt;

public class GenHash {
    public static void main(String[] args) {
        String password = "123456";
        String hash = BCrypt.hashpw(password);
        System.out.println("Generated hash: " + hash);
        System.out.println("Verify: " + BCrypt.checkpw(password, hash));
    }
}