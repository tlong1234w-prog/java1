package View;

import DAO.CaTrucDAO;
import Model.CaTruc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class CaTrucForm extends JPanel {

    private JTextField txtMaCa, txtTenCa;
    private JSpinner spBatDau, spKetThuc;
    private JTable table;
    private DefaultTableModel model;

    private CaTrucDAO dao = new CaTrucDAO();

    public CaTrucForm() {

        setLayout(null);
        setBackground(Color.WHITE);
        setSize(900, 600);

        JLabel title = new JLabel("QUẢN LÝ CA TRỰC");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(300, 10, 400, 30);
        add(title);

        // ===== FORM (MỖI MỤC = 1 JPANEL) =====
        txtMaCa = new JTextField();
        add(formRow("Mã ca:", txtMaCa, 20, 60));

        txtTenCa = new JTextField();
        add(formRow("Tên ca:", txtTenCa, 20, 110));

        spBatDau = timeSpinner();
        add(formRow("Thời gian bắt đầu:", spBatDau, 20, 160));

        spKetThuc = timeSpinner();
        add(formRow("Thời gian kết thúc:", spKetThuc, 20, 210));

        // ===== BUTTON =====
        JButton btnAdd = button("Thêm", 460, 60);
        JButton btnUpdate = button("Sửa", 460, 110);
        JButton btnDelete = button("Xóa", 460, 160);
        JButton btnClear = button("Làm mới", 460, 210);

        add(btnAdd);
        add(btnUpdate);
        add(btnDelete);
        add(btnClear);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"Mã ca", "Tên ca", "Bắt đầu", "Kết thúc"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 270, 640, 280);
        add(scroll);

        loadData();

        // ===== EVENT =====
        btnAdd.addActionListener(e -> insert());
        btnUpdate.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        btnClear.addActionListener(e -> clearForm());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fillForm();
            }
        });
    }

    // ================= FORM ROW =================
    private JPanel formRow(String text, JComponent field, int x, int y) {
        JPanel row = new JPanel(null);
        row.setBounds(x, y, 430, 35);
        row.setBackground(Color.WHITE);

        JLabel lb = new JLabel(text);
        lb.setBounds(0, 5, 150, 25);

        field.setBounds(160, 5, 250, 25);

        row.add(lb);
        row.add(field);
        return row;
    }

    // ================= LOAD =================
    private void loadData() {
        model.setRowCount(0);
        for (CaTruc ct : dao.getAll()) {
            model.addRow(new Object[]{
                    ct.getMaca(),
                    ct.getTenca(),
                    ct.getThoigianbatdau(),
                    ct.getThoigianketthuc()
            });
        }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        txtMaCa.setText(model.getValueAt(row, 0).toString());
        txtTenCa.setText(model.getValueAt(row, 1).toString());
        txtMaCa.setEnabled(false);

        LocalTime bd = LocalTime.parse(model.getValueAt(row, 2).toString());
        LocalTime kt = LocalTime.parse(model.getValueAt(row, 3).toString());

        spBatDau.setValue(Date.from(bd.atDate(java.time.LocalDate.now())
                .atZone(ZoneId.systemDefault()).toInstant()));
        spKetThuc.setValue(Date.from(kt.atDate(java.time.LocalDate.now())
                .atZone(ZoneId.systemDefault()).toInstant()));
    }

    // ================= CRUD =================
    private void insert() {
        if (!validateForm()) return;

        if (dao.exists(txtMaCa.getText())) {
            JOptionPane.showMessageDialog(this, "Mã ca đã tồn tại!");
            return;
        }

        LocalTime start = getTime(spBatDau);
        LocalTime end = getTime(spKetThuc);

        if (!end.isAfter(start)) {
            JOptionPane.showMessageDialog(this, "Giờ kết thúc phải sau giờ bắt đầu!");
            return;
        }

        CaTruc ct = new CaTruc(
                txtMaCa.getText(),
                txtTenCa.getText(),
                start.toString(),
                end.toString()
        );

        if (dao.insert(ct)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadData();
            clearForm();
        }
    }

    private void update() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Chọn ca cần sửa!");
            return;
        }

        if (!validateForm()) return;

        LocalTime start = getTime(spBatDau);
        LocalTime end = getTime(spKetThuc);

        if (!end.isAfter(start)) {
            JOptionPane.showMessageDialog(this, "Giờ kết thúc phải sau giờ bắt đầu!");
            return;
        }

        CaTruc ct = new CaTruc(
                txtMaCa.getText(),
                txtTenCa.getText(),
                start.toString(),
                end.toString()
        );

        if (dao.update(ct)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadData();
            clearForm();
        }
    }

    private void delete() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn ca cần xóa!");
            return;
        }

        if (JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa ca này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {

            if (dao.delete(txtMaCa.getText())) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
                clearForm();
            }
        }
    }

    // ================= UTILS =================
    private boolean validateForm() {
        if (txtMaCa.getText().isEmpty() || txtTenCa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống dữ liệu!");
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtMaCa.setText("");
        txtTenCa.setText("");
        txtMaCa.setEnabled(true);
        spBatDau.setValue(new Date());
        spKetThuc.setValue(new Date());
        table.clearSelection();
    }

    private LocalTime getTime(JSpinner spinner) {
        Date date = (Date) spinner.getValue();
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .withSecond(0);
    }

    private JButton button(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 120, 30);
        return btn;
    }

    private JSpinner timeSpinner() {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner sp = new JSpinner(model);
        sp.setEditor(new JSpinner.DateEditor(sp, "HH:mm"));
        return sp;
    }
}
