package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.TaiKhoan;
import Util.DBConnection;

public class TaiKhoanDAO {

    // Hàm kiểm tra đăng nhập
    public TaiKhoan checkLogin(String user, String pass) {
        TaiKhoan tk = null;

        String sql = "SELECT manv, hoten, chucvu, taikhoan "
                   + "FROM nhanvien "
                   + "WHERE taikhoan = ? AND matkhau = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql)
        ) {
            stm.setString(1, user);
            stm.setString(2, pass);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                tk = new TaiKhoan(
                    rs.getInt("manv"),
                    rs.getString("hoten"),
                    rs.getString("chucvu"),   // QUYỀN
                    rs.getString("taikhoan")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tk;
    }
}
