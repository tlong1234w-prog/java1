package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:sqlserver://DESKTOP-9SP5GH3:1433;"
          + "databaseName=Quanlycatruc;"
          + "encrypt=false;"
          + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy driver SQL Server!", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
