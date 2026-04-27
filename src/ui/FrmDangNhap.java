package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FrmDangNhap extends JDialog {

	private static final long serialVersionUID = 1L;

	private JLabel lbTenDangNhap, lbMatKhau;
	private JTextField tfTenDangNhap;
	private JPasswordField pfMatKhau;
	private JButton btnDangNhap, btnThoat;
	private Box b1, b2;

	public FrmDangNhap(JFrame parent) {
		super(parent, "🔒 Đăng nhập", true);
		this.setSize(350, 200);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		// Chú ý: Đóng form đăng nhập sẽ không thoát chương trình ngay,
		// Controller sẽ lo việc này nếu cần thiết.
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		khoiTaoGiaoDien();
	}

	public void khoiTaoGiaoDien() {
		// CONTAINER
		JPanel pnContainer = new JPanel();
		this.add(pnContainer, BorderLayout.CENTER);
		pnContainer.setLayout(new FlowLayout(FlowLayout.CENTER));

		// CENTER
		JPanel pnMain = new JPanel();
		pnContainer.add(pnMain);
		pnMain.setLayout(new BorderLayout());

		JPanel pnCenter = new JPanel();
		pnMain.add(pnCenter, BorderLayout.CENTER);
		pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));
		b1 = Box.createHorizontalBox();
		b2 = Box.createHorizontalBox();

		b1.add(lbTenDangNhap = new JLabel("Đăng nhập:"));
		b1.add(tfTenDangNhap = new JTextField());

		b2.add(lbMatKhau = new JLabel("Mật khẩu:"));
		b2.add(pfMatKhau = new JPasswordField());

		pnCenter.add(Box.createVerticalStrut(10));
		pnCenter.add(b1);
		pnCenter.add(Box.createVerticalStrut(10));
		pnCenter.add(b2);
		pnCenter.add(Box.createVerticalStrut(10));

		pnCenter.setPreferredSize(new Dimension(300, 80));

		lbTenDangNhap.setPreferredSize(new Dimension(80, 20));
		lbMatKhau.setPreferredSize(new Dimension(80, 20));
		tfTenDangNhap.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		pfMatKhau.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

		// SOUTH
		JPanel pnSouth = new JPanel();
		pnMain.add(pnSouth, BorderLayout.SOUTH);
		pnSouth.setLayout(new GridLayout(1, 2, 15, 10));

		btnDangNhap = new JButton("Đăng nhập");
		btnThoat = new JButton("Thoát");
		pnSouth.add(btnDangNhap);
		pnSouth.add(btnThoat);

		pnSouth.setPreferredSize(new Dimension(300, 40));
		btnDangNhap.setBackground(Color.white);
		btnThoat.setBackground(Color.white);
	}

	public String getUsername() {
		return tfTenDangNhap.getText();
	}

	public String getPassword() {
		return new String(pfMatKhau.getPassword());
	}

	public void addLoginListener(ActionListener listener) {
		btnDangNhap.addActionListener(listener);
	}

	public void addExitListener(ActionListener listener) {
		btnThoat.addActionListener(listener);
	}

	public void showMessage(String msg, String title, int type) {
		JOptionPane.showMessageDialog(this, msg, title, type);
	}

}