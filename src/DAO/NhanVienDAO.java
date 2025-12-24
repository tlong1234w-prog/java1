package DAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.NhanVien;
import Util.DBConnection;

public class NhanVienDAO {

    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhanvien";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getInt("manv"),
                    rs.getString("hoten"),
                    rs.getString("chucvu"),
                    rs.getString("sdt"),
                    rs.getDate("ngaysinh"),
                    rs.getString("taikhoan"),
                    rs.getString("matkhau")
                );
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public NhanVien findById(int id) {
        String sql = "SELECT * FROM nhanvien WHERE manv = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new NhanVien(
                    rs.getInt("manv"),
                    rs.getString("hoten"),
                    rs.getString("chucvu"),
                    rs.getString("sdt"),
                    rs.getDate("ngaysinh"),
                    rs.getString("taikhoan"),
                    rs.getString("matkhau")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insert(NhanVien nv) {
        String sql = "INSERT INTO nhanvien(hoten,chucvu,sdt,ngaysinh,taikhoan,matkhau) VALUES (?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getHoten());
            ps.setString(2, nv.getChucvu());
            ps.setString(3, nv.getSdt());
            ps.setDate(4, nv.getNgaysinh());
            ps.setString(5, nv.getTaikhoan());
            ps.setString(6, nv.getMatkhau());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(NhanVien nv) {
        String sql = "UPDATE nhanvien SET hoten=?, chucvu=?, sdt=?, ngaysinh=?, taikhoan=?, matkhau=? WHERE manv=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getHoten());
            ps.setString(2, nv.getChucvu());
            ps.setString(3, nv.getSdt());
            ps.setDate(4, nv.getNgaysinh());
            ps.setString(5, nv.getTaikhoan());
            ps.setString(6, nv.getMatkhau());
            ps.setInt(7, nv.getManv());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM nhanvien WHERE manv=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public static int countNhanVien() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM NhanVien";

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
}



