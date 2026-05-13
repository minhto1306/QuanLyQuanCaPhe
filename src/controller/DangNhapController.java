package controller;

import javax.swing.JOptionPane;

import dao.TaiKhoanDAO;
import entity.TaiKhoan;
import ui.FrmDangNhap;
import util.DataBaseConnection;

public class DangNhapController {
	private FrmDangNhap loginView;
	private ManHinhChinhController mainController;
	private TaiKhoanDAO tkDAO;

	// CHỨC NĂNG: Khởi tạo quá trình xử lý đăng nhập cho người dùng hệ thống.
	public DangNhapController(FrmDangNhap loginView, ManHinhChinhController mainController) {
		this.loginView = loginView;
		this.mainController = mainController;
		this.tkDAO = new TaiKhoanDAO();

		this.loginView.addLoginListener(e -> xuLyDangNhap());
		this.loginView.addExitListener(e -> {
			DataBaseConnection.getInstance().disconnect();
			System.exit(0);
		});
	}

	// CHỨC NĂNG: Xác thực các thông tin đăng nhập của người dùng.
	private void xuLyDangNhap() {
		String user = loginView.getUsername();
		String pwd = loginView.getPassword();

		if (user.trim().isEmpty() || pwd.trim().isEmpty()) {
			loginView.showMessage("Tên đăng nhập và mật khẩu không được để trống!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		TaiKhoan tk = tkDAO.kiemTraDangNhap(user, pwd);

		if (tk != null) {
			loginView.showMessage("Đăng nhập thành công! Kính chào: " + tk.getVaiTro(), "Thành công!",
					JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Tài khoản đang đăng nhập " + tk.getTenDangNhap());

			loginView.dispose();
			mainController.setTenNhanVien(tk.getTenDangNhap());

			mainController.phanQuyen(tk.getVaiTro());

			mainController.hienThiDaiSanh();
		} else {
			loginView.showMessage("Sai tên đăng nhập, mật khẩu hoặc bị khóa!", "Thất bại", JOptionPane.ERROR_MESSAGE);
		}
	}

	// CHỨC NĂNG: Hiển thị giao diện màn hình đăng nhập hệ thống.
	public void batDau() {
		loginView.setVisible(true);
	}
}