package Helper;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash mật khẩu (khi đăng ký / thêm tài khoản)
    public static String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }

    // Kiểm tra mật khẩu khi đăng nhập
    public static boolean verify(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }

}
