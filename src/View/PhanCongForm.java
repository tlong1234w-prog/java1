package View;

import DAO.PhanCongDAO;
import DAO.NhanVienDAO;
import DAO.CaTrucDAO;
import Model.PhanCong;
import Model.NhanVien;
import Model.CaTruc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

public class PhanCongForm extends JPanel {

    private JTextField txtId, txtGhiChu;
    private JComboBox<NhanVien> cboMaNV;
    private JComboBox<String> cboMaCa;
    private JDateChooser dateChooser;
    private JTable table;
    private DefaultTableModel model;

    private PhanCongDAO dao = new PhanCongDAO();
    private NhanVienDAO nvDao = new NhanVienDAO();
    private CaTrucDAO caDao = new CaTrucDAO();

    public PhanCongForm() {
        setLayout(null);
        setBackground(Color.WHITE);

        // ===== INPUT =====
        JPanel pnl = new JPanel(null);
        pnl.setBorder(BorderFactory.createTitledBorder("Phân công"));
        pnl.setBounds(10, 10, 550, 200);
        add(pnl);

        pnl.add(new JLabel("ID:")).setBounds(20, 25, 80, 25);
        txtId = new JTextField();
        txtId.setBounds(150, 25, 300, 25);
        txtId.setEditable(false);
        pnl.add(txtId);

        pnl.add(new JLabel("Nhân viên:")).setBounds(20, 60, 80, 25);
        cboMaNV = new JComboBox<>();
        cboMaNV.setBounds(150, 60, 300, 25);
        pnl.add(cboMaNV);

        pnl.add(new JLabel("Ca trực:")).setBounds(20, 95, 80, 25);
        cboMaCa = new JComboBox<>();
        cboMaCa.setBounds(150, 95, 300, 25);
        pnl.add(cboMaCa);

        pnl.add(new JLabel("Ngày:")).setBounds(20, 130, 80, 25);
        dateChooser = new JDateChooser();
        dateChooser.setBounds(150, 130, 300, 25);
        pnl.add(dateChooser);

        pnl.add(new JLabel("Ghi chú:")).setBounds(20, 165, 80, 25);
        txtGhiChu = new JTextField();
        txtGhiChu.setBounds(150, 165, 300, 25);
        pnl.add(txtGhiChu);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Mã NV", "Mã Ca", "Ngày", "Ghi chú"}, 0
        );
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(10, 220, 696, 300);
        add(sp);

        // ===== BUTTON =====
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");

        btnAdd.setBounds(580, 20, 110, 30);
        btnUpdate.setBounds(580, 70, 110, 30);
        btnDelete.setBounds(580, 120, 110, 30);
        btnClear.setBounds(580, 170, 110, 30);

        add(btnAdd);
        add(btnUpdate);
        add(btnDelete);
        add(btnClear);

        // ===== LOAD DATA =====
        loadMaNV();
        loadMaCa();
        loadData();

        // ===== EVENT =====
        btnAdd.addActionListener(e -> addPC());
        btnUpdate.addActionListener(e -> updatePC());
        btnDelete.addActionListener(e -> deletePC());
        btnClear.addActionListener(e -> clearForm());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                if (r == -1) return;

                txtId.setText(model.getValueAt(r, 0).toString());
                int maNV = Integer.parseInt(model.getValueAt(r, 1).toString());

                for (int i = 0; i < cboMaNV.getItemCount(); i++) {
                    if (cboMaNV.getItemAt(i).getManv() == maNV) {
                        cboMaNV.setSelectedIndex(i);
                        break;
                    }
                }

                cboMaCa.setSelectedItem(model.getValueAt(r, 2).toString());
                dateChooser.setDate(Date.valueOf(model.getValueAt(r, 3).toString()));
                txtGhiChu.setText(model.getValueAt(r, 4).toString());
            }
        });
    }

    // ===== LOAD =====
    void loadMaNV() {
        cboMaNV.removeAllItems();
        for (NhanVien nv : nvDao.getAll()) cboMaNV.addItem(nv);
    }

    void loadMaCa() {
        cboMaCa.removeAllItems();
        for (CaTruc ca : caDao.getAll()) cboMaCa.addItem(ca.getMaca());
    }

    void loadData() {
        model.setRowCount(0);
        for (PhanCong pc : dao.getAll()) {
            model.addRow(new Object[]{
                    pc.getId(), pc.getManv(), pc.getMaca(),
                    pc.getNgay(), pc.getGhiChu()
            });
        }
    }

    // ===== CRUD =====
    void addPC() {
        if (!validateForm()) return;

        PhanCong pc = new PhanCong();
        pc.setManv(((NhanVien) cboMaNV.getSelectedItem()).getManv());
        pc.setMaca(cboMaCa.getSelectedItem().toString());
        pc.setNgay(new Date(dateChooser.getDate().getTime()));
        pc.setGhiChu(txtGhiChu.getText());

        if (dao.insert(pc)) {
            loadData();
            clearForm();
        }
    }

    void updatePC() {
        if (txtId.getText().isEmpty() || !validateForm()) return;

        PhanCong pc = new PhanCong();
        pc.setId(Integer.parseInt(txtId.getText()));
        pc.setManv(((NhanVien) cboMaNV.getSelectedItem()).getManv());
        pc.setMaca(cboMaCa.getSelectedItem().toString());
        pc.setNgay(new Date(dateChooser.getDate().getTime()));
        pc.setGhiChu(txtGhiChu.getText());

        if (dao.update(pc)) loadData();
    }

    void deletePC() {
        if (txtId.getText().isEmpty()) return;
        if (dao.delete(Integer.parseInt(txtId.getText()))) {
            loadData();
            clearForm();
        }
    }

    // ===== UTILS =====
    boolean validateForm() {
        return cboMaNV.getSelectedItem() != null
                && cboMaCa.getSelectedItem() != null
                && dateChooser.getDate() != null;
    }

    void clearForm() {
        txtId.setText("");
        txtGhiChu.setText("");
        cboMaNV.setSelectedIndex(-1);
        cboMaCa.setSelectedIndex(-1);
        dateChooser.setDate(null);
        table.clearSelection();
    }
}
