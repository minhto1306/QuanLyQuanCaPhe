package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
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

import dao.TaiKhoanDAO;
import entity.TaiKhoan;
import util.DataBaseConnection;

public class FrmDangNhap extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel lbTenDangNhap, lbMatKhau;
	JTextField tfTenDangNhap;
	JPasswordField pfMatKhau;
	JButton btnDangNhap, btnThoat;
	Box b1, b2, b3;
	TaiKhoanDAO tkDAO;

	private boolean dangNhapThanhCong = false;

	public FrmDangNhap(JFrame parent) {
		super(parent, "🔒 Đăng nhập", true);
		this.setSize(350, 200);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		khoiTaoGiaoDien();

		// THOAT DATABASE KHI NHAN DAU X
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				DataBaseConnection.getInstance().disconnect();
//			}
//		});

		// NUT THOAT
		btnThoat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DataBaseConnection.getInstance().disconnect();
				System.exit(0);
			}
		});

		// NUT DANG NHAP
		btnDangNhap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dangNhapTaiKhoan();
			}
		});
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
		pnSouth.add(btnDangNhap = new JButton("Đăng nhập"));
		pnSouth.add(btnThoat = new JButton("Thoát"));

		pnSouth.setPreferredSize(new Dimension(300, 40));
		btnDangNhap.setBackground(Color.white);
		btnThoat.setBackground(Color.white);
	}

	private void dangNhapTaiKhoan() {
		String user = tfTenDangNhap.getText();
		String pwd = new String(pfMatKhau.getPassword());

		// kiem tra du lieu nhap vao
		if (user.trim().isEmpty() || pwd.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Tên đăng nhập và mật khẩu không được để trống!");
			return;
		}

		tkDAO = new TaiKhoanDAO();
		TaiKhoan tk = tkDAO.kiemTraDangNhap(user, pwd);

		if (tk != null) {
			JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Kính chào: " + tk.getVaiTro(), "Thành công!",
					JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Tài khoản đang đăng nhập " + tk.getTenDangNhap());

			this.dangNhapThanhCong = true;

			this.dispose();

		} else {
			JOptionPane.showMessageDialog(this, "Sai tên đăng nhập, mật khẩu hoặc bị khóa!");
		}
	}

	public boolean isDangNhapThanhCong() {
		return this.dangNhapThanhCong;
	}

	public static void main(String[] args) {
		FrmManHinhChinh frmMain = new FrmManHinhChinh();
		frmMain.setVisible(true);
		FrmDangNhap frmLogin = new FrmDangNhap(frmMain);

		frmLogin.setVisible(true);

		if (!frmLogin.isDangNhapThanhCong()) {
			System.exit(0);
		}

	}

}
