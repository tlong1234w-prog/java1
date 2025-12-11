package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.PhanCong;
import Util.DBConnection;

public class PhanCongDAO {

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
    public List<PhanCong> search(String keyword) {
        List<PhanCong> list = new ArrayList<>();

        String sql = "SELECT * FROM phancong "
                   + "WHERE CAST(manv AS NVARCHAR) LIKE ? "
                   + "OR maca LIKE ? "
                   + "OR ghi_chu LIKE ? "
                   + "OR CONVERT(VARCHAR, ngay, 23) LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String key = "%" + keyword + "%";

            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);

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
}

