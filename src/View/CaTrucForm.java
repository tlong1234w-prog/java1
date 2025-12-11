package View;

import DAO.CaTrucDAO;
import Model.CaTruc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CaTrucForm extends JPanel {

    private JTextField txtMaCa, txtTenCa, txtBatDau, txtKetThuc, txtSearch;
    private JComboBox<String> cbSearchType;
    private JTable table;
    private DefaultTableModel model;

    private CaTrucDAO dao = new CaTrucDAO();

    public CaTrucForm() {

        setLayout(null);
        setBackground(Color.WHITE);
        setSize(900, 600);  // cho phù hợp MainForm

        JLabel title = new JLabel("Quản Lý Ca Trực");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(300, 10, 400, 30);
        add(title);

        JLabel lblMa = new JLabel("Mã ca:");
        lblMa.setBounds(20, 60, 120, 25);
        add(lblMa);

        txtMaCa = new JTextField();
        txtMaCa.setBounds(140, 60, 200, 25);
        add(txtMaCa);

        JLabel lblTen = new JLabel("Tên ca:");
        lblTen.setBounds(20, 100, 120, 25);
        add(lblTen);

        txtTenCa = new JTextField();
        txtTenCa.setBounds(140, 100, 200, 25);
        add(txtTenCa);

        JLabel lblBD = new JLabel("Thời gian bắt đầu:");
        lblBD.setBounds(20, 140, 150, 25);
        add(lblBD);

        txtBatDau = new JTextField();
        txtBatDau.setBounds(170, 140, 170, 25);
        add(txtBatDau);

        JLabel lblKT = new JLabel("Thời gian kết thúc:");
        lblKT.setBounds(20, 180, 150, 25);
        add(lblKT);

        txtKetThuc = new JTextField();
        txtKetThuc.setBounds(170, 180, 170, 25);
        add(txtKetThuc);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setBounds(380, 60, 120, 30);
        add(btnAdd);

        JButton btnUpdate = new JButton("Sửa");
        btnUpdate.setBounds(380, 100, 120, 30);
        add(btnUpdate);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setBounds(380, 140, 120, 30);
        add(btnDelete);

        JLabel lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setBounds(20, 240, 100, 25);
        add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(100, 240, 250, 25);
        add(txtSearch);

        cbSearchType = new JComboBox<>(new String[]{
                "Tên ca",
                "Tên nhân viên",
                "Mã nhân viên"
        });
        cbSearchType.setBounds(360, 240, 140, 25);
        add(cbSearchType);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(520, 240, 100, 25);
        add(btnSearch);

        model = new DefaultTableModel(
                new String[]{"Mã ca", "Tên ca", "Bắt đầu", "Kết thúc"}, 0
        );
        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 290, 840, 270);
        add(scroll);

        // Load dữ liệu
        loadData();

        // Event
        btnAdd.addActionListener(e -> insert());
        btnUpdate.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        btnSearch.addActionListener(e -> search());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fillForm();
            }
        });
    }


    private void loadData() {
        model.setRowCount(0);
        List<CaTruc> list = dao.selectAll();
        for (CaTruc ct : list) {
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
        if (row >= 0) {
            txtMaCa.setText(model.getValueAt(row, 0).toString());
            txtTenCa.setText(model.getValueAt(row, 1).toString());
            txtBatDau.setText(model.getValueAt(row, 2).toString());
            txtKetThuc.setText(model.getValueAt(row, 3).toString());
        }
    }

    private void insert() {
        CaTruc ct = new CaTruc(
                txtMaCa.getText(),
                txtTenCa.getText(),
                txtBatDau.getText(),
                txtKetThuc.getText()
        );

        if (dao.insert(ct)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    }

    private void update() {
        CaTruc ct = new CaTruc(
                txtMaCa.getText(),
                txtTenCa.getText(),
                txtBatDau.getText(),
                txtKetThuc.getText()
        );

        if (dao.update(ct)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }

    private void delete() {
        String id = txtMaCa.getText();

        if (dao.delete(id)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }

    private void search() {
        model.setRowCount(0);

        String keyword = txtSearch.getText();
        String type = cbSearchType.getSelectedItem().toString();

        List<CaTruc> list;

        switch (type) {
            case "Tên nhân viên":
                list = dao.searchByEmployeeName(keyword);
                break;

            case "Mã nhân viên":
                list = dao.searchByEmployeeId(keyword);
                break;

            default:
                list = dao.searchByName(keyword);
                break;
        }

        for (CaTruc ct : list) {
            model.addRow(new Object[]{
                    ct.getMaca(),
                    ct.getTenca(),
                    ct.getThoigianbatdau(),
                    ct.getThoigianketthuc()
            });
        }
    }
}
