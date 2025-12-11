package View;

import DAO.PhanCongDAO;
import Model.PhanCong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.List;

public class PhanCongForm extends JPanel {

    private JTextField txtId, txtMaNV, txtMaCa, txtGhiChu, txtSearch;
    private JDateChooser dateChooser;
    private JTable table;
    private DefaultTableModel model;

    private PhanCongDAO dao = new PhanCongDAO();

    public PhanCongForm() {

        // ❗ QUAN TRỌNG: JPanel KHÔNG dùng setTitle, setSize, setDefaultCloseOperation
        setLayout(null);
        setBackground(Color.WHITE);

        // ===================== PANEL INPUT =====================
        JPanel pnlInput = new JPanel(null);
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin phân công"));
        pnlInput.setBounds(10, 10, 550, 210);
        add(pnlInput);

        // ===== ID =====
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 25, 150, 25);
        pnlInput.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(180, 25, 300, 25);
        txtId.setEditable(false);
        txtId.setFocusable(false);
        pnlInput.add(txtId);

        // ===== Mã nhân viên =====
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setBounds(20, 60, 150, 25);
        pnlInput.add(lblMaNV);

        txtMaNV = new JTextField();
        txtMaNV.setBounds(180, 60, 300, 25);
        pnlInput.add(txtMaNV);

        // ===== Mã ca =====
        JLabel lblMaCa = new JLabel("Mã ca:");
        lblMaCa.setBounds(20, 95, 150, 25);
        pnlInput.add(lblMaCa);

        txtMaCa = new JTextField();
        txtMaCa.setBounds(180, 95, 300, 25);
        pnlInput.add(txtMaCa);

        // ===== Ngày trực =====
        JLabel lblNgayTruc = new JLabel("Ngày trực:");
        lblNgayTruc.setBounds(20, 130, 150, 25);
        pnlInput.add(lblNgayTruc);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setBounds(180, 130, 300, 25);
        pnlInput.add(dateChooser);

        // ===== Ghi chú =====
        JLabel lblGhiChu = new JLabel("Ghi chú:");
        lblGhiChu.setBounds(20, 165, 150, 25);
        pnlInput.add(lblGhiChu);

        txtGhiChu = new JTextField();
        txtGhiChu.setBounds(180, 165, 300, 25);
        pnlInput.add(txtGhiChu);

        // ===================== BUTTON PANEL =====================
        JPanel pnlButtons = new JPanel(null);
        pnlButtons.setBounds(580, 10, 300, 110);
        pnlButtons.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        add(pnlButtons);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setBounds(20, 25, 110, 30);
        pnlButtons.add(btnAdd);

        JButton btnUpdate = new JButton("Sửa");
        btnUpdate.setBounds(160, 25, 110, 30);
        pnlButtons.add(btnUpdate);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setBounds(20, 65, 110, 30);
        pnlButtons.add(btnDelete);

        JButton btnClear = new JButton("Làm mới");
        btnClear.setBounds(160, 65, 110, 30);
        pnlButtons.add(btnClear);

        // ===================== TABLE =====================
        model = new DefaultTableModel(new String[]{"ID", "Mã NV", "Mã Ca", "Ngày", "Ghi Chú"}, 0);
        table = new JTable(model);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(10, 230, 870, 320);
        add(sp);

        // LOAD DATA
        loadData();

        // ===================== EVENT =====================

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtMaNV.setText(model.getValueAt(row, 1).toString());
                txtMaCa.setText(model.getValueAt(row, 2).toString());
                try {
                    dateChooser.setDate(Date.valueOf(model.getValueAt(row, 3).toString()));
                } catch (Exception ex) {}
                txtGhiChu.setText(model.getValueAt(row, 4).toString());
            }
        });

        btnAdd.addActionListener(e -> addPC());
        btnUpdate.addActionListener(e -> updatePC());
        btnDelete.addActionListener(e -> deletePC());
        btnClear.addActionListener(e -> clearForm());
    }

    // ================== FUNCTIONS ==================

    void loadData() {
        model.setRowCount(0);
        List<PhanCong> list = dao.getAll();
        for (PhanCong pc : list) {
            model.addRow(new Object[]{
                    pc.getId(),
                    pc.getManv(),
                    pc.getMaca(),
                    pc.getNgay(),
                    pc.getGhiChu()
            });
        }
    }

    void addPC() {
        try {
            PhanCong pc = new PhanCong();
            pc.setManv(Integer.parseInt(txtMaNV.getText()));
            pc.setMaca(txtMaCa.getText());
            pc.setNgay(new Date(dateChooser.getDate().getTime()));
            pc.setGhiChu(txtGhiChu.getText());

            if (dao.insert(pc)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    void updatePC() {
        try {
            PhanCong pc = new PhanCong();
            pc.setId(Integer.parseInt(txtId.getText()));
            pc.setManv(Integer.parseInt(txtMaNV.getText()));
            pc.setMaca(txtMaCa.getText());
            pc.setNgay(new Date(dateChooser.getDate().getTime()));
            pc.setGhiChu(txtGhiChu.getText());

            if (dao.update(pc)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    void deletePC() {
        int id = Integer.parseInt(txtId.getText());
        if (dao.delete(id)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }

    void clearForm() {
        txtId.setText("");
        txtMaNV.setText("");
        txtMaCa.setText("");
        txtGhiChu.setText("");
        dateChooser.setDate(null);
        table.clearSelection();
    }
}
