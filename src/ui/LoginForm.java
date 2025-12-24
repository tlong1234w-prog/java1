package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import DAO.TaiKhoanDAO;
import Model.TaiKhoan;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Helper.Remember;

public class LoginForm extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtUser;
    private JPasswordField txtPass;
	protected boolean[] show;
	private boolean showPass = false;
    public LoginForm() {
    	

    	setTitle("Đăng Nhập");

        ImageIcon icon = new ImageIcon("logo.jpg");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 872, 397);
        getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("FORM LOGIN QUẢN LÍ CA TRỰC");
        lblTitle.setForeground(SystemColor.activeCaption);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblTitle.setBounds(178, 15, 460, 69);
        panel.add(lblTitle);

        JLabel lblUser = new JLabel("USERNAME");
        lblUser.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblUser.setHorizontalAlignment(SwingConstants.CENTER);
        lblUser.setForeground(Color.BLACK);
        lblUser.setBounds(208, 94, 69, 29);
        panel.add(lblUser);

        txtUser = new JTextField();
        txtUser.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
        txtUser.setBounds(212, 122, 377, 29);
        panel.add(txtUser);

        JLabel lblPass = new JLabel("PASSWORD");
        lblPass.setForeground(Color.BLACK);
        lblPass.setFont(new Font("Tahoma", Font.PLAIN, 10));
        lblPass.setHorizontalAlignment(SwingConstants.CENTER);
        lblPass.setBounds(208, 161, 69, 29);
        panel.add(lblPass);
     // NÚT SHOW / HIDE PASSWORD
        JPanel passPanel = new JPanel();
        passPanel.setLayout(null);
        passPanel.setBounds(214, 180, 420, 29);   // rộng hơn mật khẩu 40px
        panel.add(passPanel);

        // Ô MẬT KHẨU
        txtPass = new JPasswordField();
        txtPass.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
        txtPass.setBounds(0, 0, 380, 29);     // chừa 40px bên phải cho icon
        txtPass.setEchoChar('•');
        passPanel.add(txtPass);

        // ICON TRONG Ô – 🔒 mặc định
     // ICON TRONG Ô – 🔒 mặc định
        JLabel btnShowPass1 = new JLabel("\uD83D\uDD12"); // 🔒
        btnShowPass1.setBounds(385, 0, 35, 29);    // 👈 sửa Y từ -2 thành 0
        btnShowPass1.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        btnShowPass1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        passPanel.add(btnShowPass1);
        // CLICK ĐỂ ĐỔI ICON + HIỆN/ẨN MẬT KHẨU
        btnShowPass1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showPass = !showPass;

                if (showPass) {
                    txtPass.setEchoChar((char) 0);          // hiện mật khẩu
                    btnShowPass1.setText("\uD83D\uDD13");    // 🔓 mở
                } else {
                    txtPass.setEchoChar('•');               // ẩn
                    btnShowPass1.setText("\uD83D\uDD12");    // 🔒 khóa
                }
            }
        });



        JButton btnDangNhap = new JButton("ĐĂNG NHẬP");
        btnDangNhap.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnDangNhap.setBackground(new Color(255, 0, 0));
        btnDangNhap.setForeground(Color.BLACK);
        btnDangNhap.setBounds(212, 252, 377, 35);
        panel.add(btnDangNhap);

        //ENTER PHÍM ĐĂNG NHẬP
        txtUser.addActionListener(e -> txtPass.requestFocus());
        txtPass.addActionListener(e -> btnDangNhap.doClick());
        // ==========================

        


        JCheckBox chkRemember = new JCheckBox("Lưu mật khẩu");
        chkRemember.setFont(new Font("Tahoma", Font.PLAIN, 10));
        chkRemember.setBounds(212, 221, 120, 14);
        panel.add(chkRemember);

        //TẢI FILE REMEMBER
        String[] data = Remember.load();
        if (data != null) {
            txtUser.setText(data[0]);
            txtPass.setText(data[1]);
            chkRemember.setSelected(true);
        }

        //SỰ KIỆN ĐĂNG NHẬP
        btnDangNhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String user = txtUser.getText();
                String pass = new String(txtPass.getPassword());

                TaiKhoanDAO dao = new TaiKhoanDAO();
                TaiKhoan tk = dao.checkLogin(user, pass);

                if (tk != null) {

                    if (chkRemember.isSelected()) {
                        Remember.save(user, pass);
                    } else {
                        Remember.clear();
                    }
                    new MainForm().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu!");
                }
            }
        });
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}

