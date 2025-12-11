package DAO;

import Model.CaTruc;
import Util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CaTrucDAO {

    // ================================
    //  LẤY TẤT CẢ CA TRỰC
    // ================================
    public List<CaTruc> selectAll() {
        List<CaTruc> list = new ArrayList<>();
        String sql = "SELECT * FROM catruc";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new CaTruc(
                        rs.getString("maca"),
                        rs.getString("tenca"),
                        rs.getString("thoigianbatdau"),
                        rs.getString("thoigianketthuc")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // ================================
    //  THÊM CA TRỰC
    // ================================
    public boolean insert(CaTruc ct) {
        String sql = "INSERT INTO catruc (maca, tenca, thoigianbatdau, thoigianketthuc) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ct.getMaca());
            ps.setString(2, ct.getTenca());
            ps.setString(3, ct.getThoigianbatdau());
            ps.setString(4, ct.getThoigianketthuc());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // ================================
    //  CẬP NHẬT CA TRỰC
    // ================================
    public boolean update(CaTruc ct) {
        String sql = """
            UPDATE catruc
            SET tenca = ?, thoigianbatdau = ?, thoigianketthuc = ?
            WHERE maca = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ct.getTenca());
            ps.setString(2, ct.getThoigianbatdau());
            ps.setString(3, ct.getThoigianketthuc());
            ps.setString(4, ct.getMaca());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // ================================
    //  XÓA CA TRỰC
    // ================================
    public boolean delete(String maca) {
        String sql = "DELETE FROM catruc WHERE maca = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maca);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // ================================
    //  TÌM THEO TÊN CA
    // ================================
    public List<CaTruc> searchByName(String keyword) {
        List<CaTruc> list = new ArrayList<>();

        String sql = "SELECT * FROM catruc WHERE tenca LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CaTruc(
                        rs.getString("maca"),
                        rs.getString("tenca"),
                        rs.getString("thoigianbatdau"),
                        rs.getString("thoigianketthuc")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // ================================
    //  TÌM THEO TÊN NHÂN VIÊN
    // ================================
    public List<CaTruc> searchByEmployeeName(String keyword) {
        List<CaTruc> list = new ArrayList<>();

        String sql = """
            SELECT c.maca, c.tenca, c.thoigianbatdau, c.thoigianketthuc
            FROM phancong p
            JOIN nhanvien n ON p.manv = n.manv
            JOIN catruc c ON p.maca = c.maca
            WHERE n.hoten LIKE ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CaTruc(
                        rs.getString("maca"),
                        rs.getString("tenca"),
                        rs.getString("thoigianbatdau"),
                        rs.getString("thoigianketthuc")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // ================================
    //  TÌM THEO MÃ NHÂN VIÊN
    // ================================
    public List<CaTruc> searchByEmployeeId(String id) {
        List<CaTruc> list = new ArrayList<>();

        String sql = """
            SELECT c.maca, c.tenca, c.thoigianbatdau, c.thoigianketthuc
            FROM phancong p
            JOIN nhanvien n ON p.manv = n.manv
            JOIN catruc c ON p.maca = c.maca
            WHERE n.manv = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CaTruc(
                        rs.getString("maca"),
                        rs.getString("tenca"),
                        rs.getString("thoigianbatdau"),
                        rs.getString("thoigianketthuc")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
