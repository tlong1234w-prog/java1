package View;

import DAO.NhanVienDAO;
import Model.NhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.List;

public class NhanVienForm extends JPanel {

    private JTextField txtManv, txtHoten, txtChucvu, txtSdt, txtTaikhoan, txtMatkhau;
    private JTable table;
    private DefaultTableModel model;
    private NhanVienDAO dao = new NhanVienDAO();
    private JDateChooser txtNgaysinh;

    public NhanVienForm() {

        // ❗ JPanel KHÔNG dùng setTitle, setDefaultCloseOperation...
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(0, 20, 900, 40);
        add(lblTitle);

        // ====== FORM INPUT ======
        JLabel lblManv = new JLabel("Mã NV:");
        lblManv.setBounds(90, 80, 80, 25);
        add(lblManv);

        txtManv = new JTextField();
        txtManv.setBounds(150, 80, 200, 25);
        txtManv.setEditable(false);
        txtManv.setFocusable(false);
        add(txtManv);

        JLabel lblHoten = new JLabel("Họ Tên:");
        lblHoten.setBounds(90, 120, 60, 25);
        add(lblHoten);

        txtHoten = new JTextField();
        txtHoten.setBounds(150, 120, 200, 25);
        add(txtHoten);

        JLabel lblChucvu = new JLabel("Chức Vụ:");
        lblChucvu.setBounds(90, 160, 60, 25);
        add(lblChucvu);

        txtChucvu = new JTextField();
        txtChucvu.setBounds(150, 160, 200, 25);
        add(txtChucvu);

        JLabel lblSdt = new JLabel("SĐT:");
        lblSdt.setBounds(450, 80, 60, 25);
        add(lblSdt);

        txtSdt = new JTextField();
        txtSdt.setBounds(600, 80, 150, 25);
        add(txtSdt);

        JLabel lblNgaysinh = new JLabel("Ngày sinh:");
        lblNgaysinh.setBounds(450, 120, 100, 25);
        add(lblNgaysinh);

        txtNgaysinh = new JDateChooser();
        txtNgaysinh.setBounds(600, 120, 150, 25);
        txtNgaysinh.setDateFormatString("yyyy-MM-dd");
        add(txtNgaysinh);

        JLabel lblTaikhoan = new JLabel("Tài khoản:");
        lblTaikhoan.setBounds(450, 160, 100, 25);
        add(lblTaikhoan);

        txtTaikhoan = new JTextField();
        txtTaikhoan.setBounds(600, 160, 150, 25);
        add(txtTaikhoan);

        JLabel lblMatkhau = new JLabel("Mật khẩu:");
        lblMatkhau.setBounds(450, 200, 80, 25);
        add(lblMatkhau);

        txtMatkhau = new JTextField();
        txtMatkhau.setBounds(600, 200, 150, 25);
        add(txtMatkhau);

        // ====== BUTTON ======
        JButton btnAdd = new JButton("Thêm");
        btnAdd.setBounds(150, 260, 100, 30);
        add(btnAdd);

        JButton btnUpdate = new JButton("Sửa");
        btnUpdate.setBounds(300, 260, 100, 30);
        add(btnUpdate);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setBounds(450, 260, 100, 30);
        add(btnDelete);

        JButton btnClear = new JButton("Làm mới");
        btnClear.setBounds(600, 260, 100, 30);
        add(btnClear);

        // ====== BẢNG ======
        model = new DefaultTableModel(
                new String[]{"Mã NV", "Họ tên", "Chức vụ", "SĐT", "Ngày sinh", "Tài khoản", "Mật khẩu"}, 0
        );

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(30, 320, 820, 260);
        add(sp);

        loadData();

        // ====== CLICK BẢNG ======
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();

                txtManv.setText(model.getValueAt(i, 0).toString());
                txtHoten.setText(model.getValueAt(i, 1).toString());
                txtChucvu.setText(model.getValueAt(i, 2).toString());
                txtSdt.setText(model.getValueAt(i, 3).toString());

                txtNgaysinh.setDate(Date.valueOf(model.getValueAt(i, 4).toString()));

                txtTaikhoan.setText(model.getValueAt(i, 5).toString());
                txtMatkhau.setText(model.getValueAt(i, 6).toString());
            }
        });

        // BUTTON ACTIONS
        btnAdd.addActionListener(e -> insertNV());
        btnUpdate.addActionListener(e -> updateNV());
        btnDelete.addActionListener(e -> deleteNV());
        btnClear.addActionListener(e -> clearForm());
    }

    // Load bảng
    private void loadData() {
        model.setRowCount(0);
        List<NhanVien> list = dao.getAll();
        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                    nv.getManv(), nv.getHoten(), nv.getChucvu(),
                    nv.getSdt(), nv.getNgaysinh(),
                    nv.getTaikhoan(), nv.getMatkhau()
            });
        }
    }

    private void clearForm() {
        txtManv.setText("");
        txtHoten.setText("");
        txtChucvu.setText("");
        txtSdt.setText("");
        txtNgaysinh.setDate(null);
        txtTaikhoan.setText("");
        txtMatkhau.setText("");
    }

    private void insertNV() {
        try {
            NhanVien nv = new NhanVien(
                    0,
                    txtHoten.getText(),
                    txtChucvu.getText(),
                    txtSdt.getText(),
                    new java.sql.Date(txtNgaysinh.getDate().getTime()),
                    txtTaikhoan.getText(),
                    txtMatkhau.getText()
            );

            if (dao.insert(nv)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu: " + ex.getMessage());
        }
    }

    private void updateNV() {
        try {
            NhanVien nv = new NhanVien(
                    Integer.parseInt(txtManv.getText()),
                    txtHoten.getText(),
                    txtChucvu.getText(),
                    txtSdt.getText(),
                    new java.sql.Date(txtNgaysinh.getDate().getTime()),
                    txtTaikhoan.getText(),
                    txtMatkhau.getText()
            );

            if (dao.update(nv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi sửa: " + ex.getMessage());
        }
    }

    private void deleteNV() {
        try {
            int id = Integer.parseInt(txtManv.getText());

            if (dao.delete(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa: " + ex.getMessage());
        }
    }
}
