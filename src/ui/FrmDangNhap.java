package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrmDangNhap extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField tfTenDangNhap;
	private JPasswordField pfMatKhau;
	private JButton btnDangNhap, btnThoat;
	private JCheckBox chkHienMatKhau;

	private final Color MAU_NEN_BOX = new Color(250, 235, 215);
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47);
	private final Color MAU_NUT_BAM = new Color(222, 184, 135);
	private Font fontBungeeBase;

	// CHỨC NĂNG: Khởi tạo màn hình đăng nhập của hệ thống.
	public FrmDangNhap(JFrame parent) {
		super(parent, "Đăng Nhập - MIO COFFEE", true);
		this.setSize(750, 420);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		loadCustomFont();
		khoiTaoGiaoDien();
	}

	// CHỨC NĂNG: Tải font chữ tùy chỉnh cho hệ thống đăng nhập.
	private void loadCustomFont() {
		try {
			InputStream is = getClass().getResourceAsStream("/font/Bungee-Regular.ttf");
			if (is != null) {
				fontBungeeBase = Font.createFont(Font.TRUETYPE_FONT, is);
			} else {
				fontBungeeBase = new Font("Impact", Font.PLAIN, 24);
			}
		} catch (Exception e) {
			fontBungeeBase = new Font("Impact", Font.PLAIN, 24);
		}
	}

	// CHỨC NĂNG: Tự động thu nhỏ hình ảnh theo kích thước cung cấp.
	private ImageIcon taoIconThuNho(String path, int w, int h) {
		try {
			java.net.URL url = getClass().getResource(path);
			if (url != null) {
				Image img = new ImageIcon(url).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
				return new ImageIcon(img);
			}
		} catch (Exception e) {
		}
		return null;
	}

	// CHỨC NĂNG: Khởi tạo và thiết lập các thành phần giao diện của bảng đăng nhập.
	public void khoiTaoGiaoDien() {
		JPanel pnBackground = new JPanel() {
			private static final long serialVersionUID = 1L;
			Image bgImage;
			{
				try {
					java.net.URL url = getClass().getResource("/images/bg_login.png");
					if (url == null)
						url = getClass().getResource("/images/bg_login.jpg");
					if (url != null)
						bgImage = new ImageIcon(url).getImage();
				} catch (Exception e) {
				}
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (bgImage != null) {
					g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
				} else {
					g.setColor(new Color(209, 185, 161));
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		pnBackground.setLayout(null);
		this.setContentPane(pnBackground);

		JPanel pnLoginBox = new JPanel();
		pnLoginBox.setLayout(null);
		pnLoginBox.setBounds(380, 50, 320, 280);
		pnLoginBox.setBackground(MAU_NEN_BOX);
		pnLoginBox.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		JLabel lbTitle = new JLabel("MIO COFFEE", SwingConstants.CENTER);
		lbTitle.setBounds(0, 20, 320, 40);
		lbTitle.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 32f));
		lbTitle.setForeground(MAU_NAU_VIEN);
		pnLoginBox.add(lbTitle);

		JLabel lbUserIcon = new JLabel(taoIconThuNho("/images/profile.png", 24, 24));
		if (lbUserIcon.getIcon() == null)
			lbUserIcon.setText("👤");
		lbUserIcon.setBounds(25, 80, 30, 30);
		pnLoginBox.add(lbUserIcon);

		tfTenDangNhap = new JTextField();
		tfTenDangNhap.setBounds(65, 80, 220, 35);
		tfTenDangNhap.setFont(new Font("Arial", Font.BOLD, 14));
		tfTenDangNhap.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		pnLoginBox.add(tfTenDangNhap);

		JLabel lbPassIcon = new JLabel(taoIconThuNho("/images/lock.png", 24, 24));
		if (lbPassIcon.getIcon() == null)
			lbPassIcon.setText("🔒");
		lbPassIcon.setBounds(25, 130, 30, 30);
		pnLoginBox.add(lbPassIcon);

		pfMatKhau = new JPasswordField();
		pfMatKhau.setBounds(65, 130, 220, 35);
		pfMatKhau.setFont(new Font("Arial", Font.BOLD, 14));
		pfMatKhau.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY, 1),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		pnLoginBox.add(pfMatKhau);

		chkHienMatKhau = new JCheckBox("Hiện mật khẩu");
		chkHienMatKhau.setBounds(65, 170, 150, 25);
		chkHienMatKhau.setBackground(MAU_NEN_BOX);
		chkHienMatKhau.setFont(new Font("Arial", Font.PLAIN, 12));
		chkHienMatKhau.setFocusPainted(false);
		chkHienMatKhau.addActionListener(e -> {
			if (chkHienMatKhau.isSelected()) {
				pfMatKhau.setEchoChar((char) 0);
			} else {
				pfMatKhau.setEchoChar('*');
			}
		});
		pnLoginBox.add(chkHienMatKhau);

		btnDangNhap = new JButton("Đăng Nhập", taoIconThuNho("/images/login_arrow.png", 20, 20));
		btnDangNhap.setBounds(20, 210, 140, 40);
		btnDangNhap.setBackground(MAU_NUT_BAM);
		btnDangNhap.setFont(new Font("Arial", Font.BOLD, 13));
		btnDangNhap.setForeground(MAU_NAU_VIEN);
		btnDangNhap.setFocusPainted(false);
		btnDangNhap.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 1, true));
		btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnLoginBox.add(btnDangNhap);

		btnThoat = new JButton("Thoát", taoIconThuNho("/images/close.png", 16, 16));
		btnThoat.setBounds(165, 210, 120, 40);
		btnThoat.setBackground(MAU_NUT_BAM);
		btnThoat.setFont(new Font("Arial", Font.BOLD, 13));
		btnThoat.setForeground(MAU_NAU_VIEN);
		btnThoat.setFocusPainted(false);
		btnThoat.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 1, true));
		btnThoat.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnLoginBox.add(btnThoat);

		pnBackground.add(pnLoginBox);
	}

	// CHỨC NĂNG: Truy xuất tên đăng nhập được nhập vào form.
	public String getUsername() {
		return tfTenDangNhap.getText().trim();
	}

	// CHỨC NĂNG: Truy xuất mật khẩu được nhập vào form.
	public String getPassword() {
		return new String(pfMatKhau.getPassword());
	}

	public void addLoginListener(ActionListener listener) {
		btnDangNhap.addActionListener(listener);
	}

	public void addExitListener(ActionListener listener) {
		btnThoat.addActionListener(listener);
	}

	// CHỨC NĂNG: Hiển thị hộp thoại thông báo.
	public void showMessage(String msg, String title, int type) {
		JOptionPane.showMessageDialog(this, msg, title, type);
	}
}