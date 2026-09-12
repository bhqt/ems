import cn.dev33.satoken.secure.BCrypt;

public class TestBCrypt {
    public static void main(String[] args) {
        String hash = "$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2";
        System.out.println("Testing BCrypt.checkpw('123456', hash):");
        System.out.println(BCrypt.checkpw("123456", hash));
    }
}