package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.CaTruc;
import Model.PhanCong;
import Util.DBConnection;

public class PhanCongDAO {

    // ===== GET ALL =====
    public List<PhanCong> getAll() {
        List<PhanCong> list = new ArrayList<>();
        String sql = "SELECT * FROM phancong";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new PhanCong(
                    rs.getInt("id"),
                    rs.getInt("manv"),
                    rs.getString("maca"),
                    rs.getDate("ngay"),
                    rs.getString("ghi_chu")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== INSERT =====
    public boolean insert(PhanCong pc) {
        String sql = "INSERT INTO phancong(manv, maca, ngay, ghi_chu) VALUES (?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pc.getManv());
            ps.setString(2, pc.getMaca());
            ps.setDate(3, pc.getNgay());
            ps.setString(4, pc.getGhiChu());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== UPDATE =====
    public boolean update(PhanCong pc) {
        String sql = "UPDATE phancong SET manv=?, maca=?, ngay=?, ghi_chu=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pc.getManv());
            ps.setString(2, pc.getMaca());
            ps.setDate(3, pc.getNgay());
            ps.setString(4, pc.getGhiChu());
            ps.setInt(5, pc.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== DELETE =====
    public boolean delete(int id) {
        String sql = "DELETE FROM phancong WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== SEARCH THEO TÊN NHÂN VIÊN =====
    public List<PhanCong> searchByTenNhanVien(String tenNV) {
        List<PhanCong> list = new ArrayList<>();

        String sql = """
            SELECT pc.*
            FROM phancong pc
            JOIN nhanvien nv ON pc.manv = nv.manv
            WHERE nv.hoten LIKE ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + tenNV.trim() + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PhanCong(
                    rs.getInt("id"),
                    rs.getInt("manv"),
                    rs.getString("maca"),
                    rs.getDate("ngay"),
                    rs.getString("ghi_chu")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

public static int countPhanCong() {
    int count = 0;
    String sql = "SELECT COUNT(*) FROM PhanCong";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}

public List<CaTruc> searchByName(String keyword) {
	// TODO Auto-generated method stub
	return null;
}
}