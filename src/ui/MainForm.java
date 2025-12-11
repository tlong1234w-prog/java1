package ui;

import javax.swing.*;

import View.CaTrucForm;
import View.NhanVienForm;
import View.PhanCongForm;

import java.awt.*;

public class MainForm extends JFrame {

    private JPanel menuPanel;     // Panel menu bên trái
    private JPanel contentPanel;  // Panel hiển thị nội dung

    public MainForm() {

        setTitle("Hệ Thống Quản Lý");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // ================= MENU PANEL =================
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 0, 200, 600);
        menuPanel.setBackground(new Color(248, 200, 220));

        JLabel lblMenu = new JLabel("MENU", SwingConstants.CENTER);
        lblMenu.setForeground(Color.WHITE);
        lblMenu.setFont(new Font("Arial", Font.BOLD, 20));
        lblMenu.setBounds(0, 20, 200, 40);
        menuPanel.add(lblMenu);

        JButton btnTrangChu = new JButton("Trang Chủ");
        btnTrangChu.setBounds(20, 80, 160, 40);
        menuPanel.add(btnTrangChu);

        JButton btnNhanVien = new JButton("Quản Lý Nhân Viên");
        btnNhanVien.setBounds(20, 140, 160, 40);
        menuPanel.add(btnNhanVien);

        JButton btnCaTruc = new JButton("Quản Lý Ca Trực");
        btnCaTruc.setBounds(20, 200, 160, 40);
        menuPanel.add(btnCaTruc);

        JButton btnPhanCong = new JButton("Phân Công");
        btnPhanCong.setBounds(20, 260, 160, 40);
        menuPanel.add(btnPhanCong);

        JButton btnLogout = new JButton("Đăng Xuất");
        btnLogout.setBounds(20, 500, 160, 40);
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);
        menuPanel.add(btnLogout);

        add(menuPanel);

        // ================= CONTENT PANEL =================
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBounds(200, 0, 900, 600);
        add(contentPanel);



        // ================= BUTTON EVENTS =================

        btnNhanVien.addActionListener(e -> setContent(new NhanVienForm()));
        btnCaTruc.addActionListener(e -> setContent(new CaTrucForm()));
        btnPhanCong.addActionListener(e -> setContent(new PhanCongForm()));

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn đăng xuất không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginForm().setVisible(true);
            }
        });
    }

    // Hàm chuyển Panel
    public void setContent(JPanel panel) {
        contentPanel.removeAll();
        panel.setBounds(0, 0, 900, 600);
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

//    public static void main(String[] args) {
//        new MainForm().setVisible(true);
//    }
}
