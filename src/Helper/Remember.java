package Helper;

import java.io.*;

public class Remember {

    private static final String FILE_NAME = "remember.dat";

    // Lưu username + password
    public static void save(String username, String password) {
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            fw.write(username + "\n" + password);
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu remember.dat: " + e.getMessage());
        }
    }

    // Xóa file lưu
    public static void clear() {
        File f = new File(FILE_NAME);
        if (f.exists()) {
            f.delete();
        }
    }

    // Tải dữ liệu đã lưu
    public static String[] load() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String user = br.readLine();
            String pass = br.readLine();
            return new String[]{user, pass};
        } catch (Exception e) {
            return null;
        }
    }
}
