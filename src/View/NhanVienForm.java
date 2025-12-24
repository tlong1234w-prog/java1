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

    private JTextField txtManv, txtHoten, txtSdt, txtTaikhoan, txtMatkhau;
    private JComboBox<String> cbChucvu;
    private JTable table;
    private DefaultTableModel model;
    private NhanVienDAO dao = new NhanVienDAO();
    private JDateChooser txtNgaysinh;

    public NhanVienForm() {

        setLayout(null);
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(0, 20, 900, 40);
        add(lblTitle);

        // ====== FORM INPUT ======
        JLabel lblManv = new JLabel("Mã NV:");
        lblManv.setBounds(26, 80, 80, 25);
        add(lblManv);

        txtManv = new JTextField();
        txtManv.setBounds(88, 80, 234, 25);
        txtManv.setEditable(false);
        add(txtManv);

        JLabel lblHoten = new JLabel("Họ tên:");
        lblHoten.setBounds(26, 120, 80, 25);
        add(lblHoten);

        txtHoten = new JTextField();
        txtHoten.setBounds(88, 115, 234, 25);
        add(txtHoten);

        JLabel lblNgaysinh = new JLabel("Ngày sinh:");
        lblNgaysinh.setBounds(26, 160, 100, 25);
        add(lblNgaysinh);

        txtNgaysinh = new JDateChooser();
        txtNgaysinh.setBounds(88, 155, 234, 25);
        txtNgaysinh.setDateFormatString("yyyy-MM-dd");
        add(txtNgaysinh);

        JLabel lblSdt = new JLabel("SĐT:");
        lblSdt.setBounds(26, 195, 80, 25);
        add(lblSdt);

        txtSdt = new JTextField();
        txtSdt.setBounds(88, 195, 234, 25);
        add(txtSdt);

        JLabel lblChucvu = new JLabel("Chức vụ:");
        lblChucvu.setBounds(371, 80, 80, 25);
        add(lblChucvu);

        cbChucvu = new JComboBox<>(new String[]{"Nhân viên", "Quản lý"});
        cbChucvu.setBounds(461, 80, 230, 25);
        add(cbChucvu);

        JLabel lblTaikhoan = new JLabel("Tài khoản:");
        lblTaikhoan.setBounds(371, 120, 100, 25);
        add(lblTaikhoan);

        txtTaikhoan = new JTextField();
        txtTaikhoan.setBounds(461, 120, 230, 25);
        add(txtTaikhoan);

        JLabel lblMatkhau = new JLabel("Mật khẩu:");
        lblMatkhau.setBounds(371, 160, 100, 25);
        add(lblMatkhau);

        txtMatkhau = new JTextField();
        txtMatkhau.setBounds(461, 160, 230, 25);
        add(txtMatkhau);

        // ====== BUTTON ======
        JButton btnAdd = new JButton("Thêm");
        btnAdd.setBounds(65, 260, 100, 30);
        add(btnAdd);

        JButton btnUpdate = new JButton("Sửa");
        btnUpdate.setBounds(222, 260, 100, 30);
        add(btnUpdate);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setBounds(386, 260, 100, 30);
        add(btnDelete);

        JButton btnClear = new JButton("Làm mới");
        btnClear.setBounds(543, 260, 100, 30);
        add(btnClear);

        // ====== TABLE ======
        model = new DefaultTableModel(
                new String[]{"Mã NV", "Họ tên", "Chức vụ", "SĐT", "Ngày sinh", "Tài khoản", "Mật khẩu"}, 0
        );
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(26, 319, 665, 260);
        add(sp);

        loadData();

        // ====== CLICK TABLE ======
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                txtManv.setText(model.getValueAt(i, 0).toString());
                txtHoten.setText(model.getValueAt(i, 1).toString());
                cbChucvu.setSelectedItem(model.getValueAt(i, 2).toString());
                txtSdt.setText(model.getValueAt(i, 3).toString());
                txtNgaysinh.setDate(Date.valueOf(model.getValueAt(i, 4).toString()));
                txtTaikhoan.setText(model.getValueAt(i, 5).toString());
                txtMatkhau.setText(model.getValueAt(i, 6).toString());
            }
        });

        btnAdd.addActionListener(e -> insertNV());
        btnUpdate.addActionListener(e -> updateNV());
        btnDelete.addActionListener(e -> deleteNV());
        btnClear.addActionListener(e -> clearForm());
    }

    // ====== FUNCTIONS ======
    private void loadData() {
        model.setRowCount(0);
        for (NhanVien nv : dao.getAll()) {
            model.addRow(new Object[]{
                    nv.getManv(),
                    nv.getHoten(),
                    nv.getChucvu(),
                    nv.getSdt(),
                    nv.getNgaysinh(),
                    nv.getTaikhoan(),
                    nv.getMatkhau()
            });
        }
    }

    private void clearForm() {
        txtManv.setText("");
        txtHoten.setText("");
        cbChucvu.setSelectedIndex(0);
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
                    cbChucvu.getSelectedItem().toString(),
                    txtSdt.getText(),
                    new Date(txtNgaysinh.getDate().getTime()),
                    txtTaikhoan.getText(),
                    txtMatkhau.getText()
            );
            if (dao.insert(nv)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadData();
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu!");
        }
    }

    private void updateNV() {
        try {
            NhanVien nv = new NhanVien(
                    Integer.parseInt(txtManv.getText()),
                    txtHoten.getText(),
                    cbChucvu.getSelectedItem().toString(),
                    txtSdt.getText(),
                    new Date(txtNgaysinh.getDate().getTime()),
                    txtTaikhoan.getText(),
                    txtMatkhau.getText()
            );
            if (dao.update(nv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật!");
        }
    }

    private void deleteNV() {
        try {
            int id = Integer.parseInt(txtManv.getText());
            if (dao.delete(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa!");
        }
    }
}
