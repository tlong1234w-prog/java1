package ui;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import DAO.CaTrucDAO;
import DAO.NhanVienDAO;
import DAO.PhanCongDAO;
import View.CaTrucForm;
import View.NhanVienForm;
import View.PhanCongForm;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MainForm extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel menuPanel;
    private JPanel contentPanel;

    public MainForm() {
        ImageIcon icon = new ImageIcon("logo.jpg");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 872, 397);
        getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);
        
        setTitle("Hệ Thống Quản Lý");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // ================= MENU PANEL =================
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 0, 200, 600);
        menuPanel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));

        JLabel lblMenu = new JLabel("MENU", SwingConstants.CENTER);
        lblMenu.setForeground(Color.WHITE);
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblMenu.setBounds(0, 20, 200, 40);
        menuPanel.add(lblMenu);

        JButton btnTrangChu = new JButton("Trang Chủ");
        styleButton(btnTrangChu, 80);
        menuPanel.add(btnTrangChu);

        JButton btnNhanVien = new JButton("Quản Lý Nhân Viên");
        styleButton(btnNhanVien, 140);
        menuPanel.add(btnNhanVien);

        JButton btnCaTruc = new JButton("Quản Lý Ca Trực");
        styleButton(btnCaTruc, 200);
        menuPanel.add(btnCaTruc);

        JButton btnPhanCong = new JButton("Phân Công");
        styleButton(btnPhanCong, 260);
        menuPanel.add(btnPhanCong);

        JButton btnLogout = new JButton("Đăng Xuất");
        styleButton(btnLogout, 500);
        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.setForeground(Color.WHITE);
        menuPanel.add(btnLogout);

        getContentPane().add(menuPanel);

        // ================= CONTENT PANEL =================
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBounds(200, 0, 900, 600);
        getContentPane().add(contentPanel);

        // ================= BUTTON EVENTS =================

        btnTrangChu.addActionListener(e -> setContent(getTrangChuPanel()));
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
new LoginForm().setVisible(true);  // ⬅ QUAY LẠI LOGIN
            }
        });

        
        setContent(getTrangChuPanel());
    }

    // ================ HELPER METHODS =================

    private void styleButton(JButton btn, int y) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBounds(20, y, 160, 40);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setContent(JPanel panel) {
        contentPanel.removeAll();
        panel.setBounds(0, 0, 900, 600);
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ================ TRANG CHỦ =================
    private JPanel getTrangChuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(20, 20, 860, 100);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new MatteBorder(0, 0, 4, 0, new Color(52, 152, 219)));

        JLabel lblWelcome = new JLabel("Xin chào, Quản trị viên!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblWelcome.setForeground(new Color(44, 62, 80));
        lblWelcome.setBounds(20, 15, 400, 40);
        headerPanel.add(lblWelcome);

        JLabel lblTime = new JLabel();
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTime.setForeground(Color.GRAY);
        lblTime.setBounds(20, 55, 400, 30);
        headerPanel.add(lblTime);

        Timer timer = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy HH:mm:ss");
            lblTime.setText(now.format(formatter));
        });
        timer.start();

        panel.add(headerPanel);
        NhanVienDAO nvDAO = new NhanVienDAO();
        CaTrucDAO ctDAO = new CaTrucDAO();
        PhanCongDAO pcDAO = new PhanCongDAO();

        int tongNhanVien = NhanVienDAO.countNhanVien();
        int caHomNay = CaTrucDAO.countCaHomNay();
        int yeuCauPhanCong = PhanCongDAO.countPhanCong();
        
        panel.add(createCard("TỔNG NHÂN VIÊN", String.valueOf(tongNhanVien),
        		new Color(52, 152, 219), 20, 150));
        panel.add(createCard("CA TRỰC HÔM NAY", String.valueOf(caHomNay),
        		new Color(46, 204, 113), 320, 150));
        panel.add(createCard("PHÂN CÔNG", String.valueOf(yeuCauPhanCong),
        		new Color(243, 156, 18), 620, 150));

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.setBackground(Color.WHITE);
        mainContent.setBounds(20, 300, 860, 220);
        mainContent.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
JLabel lblInfo = new JLabel("<html><div style='text-align: center;'>"
                + "<h2>Thông báo hệ thống</h2>"
                + "<p>Hệ thống đang hoạt động ổn định.</p>"
                + "</div></html>", SwingConstants.CENTER);

        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInfo.setForeground(Color.DARK_GRAY);
        mainContent.add(lblInfo, BorderLayout.CENTER);

        panel.add(mainContent);

        return panel;
    }

    private JPanel createCard(String title, String number, Color color, int x, int y) {
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(x, y, 260, 120);
        card.setBackground(color);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(new Color(255, 255, 255, 220));
        lblTitle.setBounds(20, 15, 200, 20);
        card.add(lblTitle);

        JLabel lblNumber = new JLabel(number);
        lblNumber.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblNumber.setForeground(Color.WHITE);
        lblNumber.setBounds(20, 40, 150, 60);
        card.add(lblNumber);

        return card;
    }

}