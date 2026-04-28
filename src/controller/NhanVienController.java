package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;

import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.TaiKhoan;
import ui.DlgNhanVien;

public class NhanVienController {
	private DlgNhanVien view;
	private NhanVienDAO nvDAO;
	private TaiKhoanDAO tkDAO;

	public NhanVienController(DlgNhanVien view) {
		this.view = view;
		this.nvDAO = new NhanVienDAO();
		this.tkDAO = new TaiKhoanDAO();

		dangKySuKien();
		lamMoiBangNhanVien();
		lamMoiBangTaiKhoan();
	}

	private void dangKySuKien() {
		view.addThemNVListener(e -> xuLyThemNV());
		view.addSuaNVListener(e -> xuLySuaNV());
		view.addXoaNVListener(e -> xuLyXoaNV());
		view.addXoaTrangNVListener(e -> view.xoaTrangFormNhanVien());
		view.addTimNVListener(e -> xuLyTimNV());
		view.addLuuNVListener(e -> JOptionPane.showMessageDialog(view, "Vui lòng dùng nút Thêm hoặc Sửa!"));

		view.addBangNhanVienMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = view.getTbNhanVien().getSelectedRow();
				if (row >= 0) {
					view.dienThongTinNhanVienLenForm(view.getTmNhanVien().getValueAt(row, 1).toString(),
							view.getTmNhanVien().getValueAt(row, 2).toString(),
							view.getTmNhanVien().getValueAt(row, 3).toString(),
							view.getTmNhanVien().getValueAt(row, 4).toString(),
							view.getTmNhanVien().getValueAt(row, 5).toString(),
							view.getTmNhanVien().getValueAt(row, 6).toString());
				}
			}
		});

		view.addThemTKListener(e -> xuLyThemTK());
		view.addSuaTKListener(e -> xuLySuaTK());
		view.addXoaTKListener(e -> xuLyXoaTK());
		view.addXoaTrangTKListener(e -> view.xoaTrangFormTaiKhoan());
		view.addTimTKListener(e -> xuLyTimTK());
		view.addLuuTKListener(e -> JOptionPane.showMessageDialog(view, "Vui lòng dùng nút Thêm hoặc Sửa!"));

		view.addBangTaiKhoanMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = view.getTbTaiKhoan().getSelectedRow();
				if (row >= 0) {
					view.dienThongTinTaiKhoanLenForm(view.getTmTaiKhoan().getValueAt(row, 1).toString(),
							view.getTmTaiKhoan().getValueAt(row, 2).toString(),
							view.getTmTaiKhoan().getValueAt(row, 3).toString(),
							view.getTmTaiKhoan().getValueAt(row, 4).toString());
				}
			}
		});
	}

	private void lamMoiBangNhanVien() {
		view.getTmNhanVien().setRowCount(0);
		List<NhanVien> list = nvDAO.findAll();
		int stt = 1;
		for (NhanVien nv : list) {
			TaiKhoan tk = tkDAO.findById(nv.getTenDangNhap());
			String tenVaiTro = "Chưa cấp";
			if (tk != null) {
				String maDB = tk.getVaiTro() != null ? tk.getVaiTro().trim() : "";
				tenVaiTro = maDB.equals("QuanLy") ? "Quản lý" : "Thu ngân";
			}
			view.getTmNhanVien().addRow(new Object[] { stt++, nv.getMaNhanVien(), nv.getHoTenNhanVien(),
					nv.getTenDangNhap(), nv.getSoDienThoai(), nv.getSoCCCD(), tenVaiTro });
		}
		view.getCbTK_TenDangNhap().removeAllItems();
		for (NhanVien nv : list) {
			view.getCbTK_TenDangNhap().addItem(nv.getTenDangNhap());
		}
	}

	private NhanVien layDuLieuNhanVienTuForm() {
		return new NhanVien(view.getMaNVInput(), view.getTenDangNhapNVInput(), view.getHoTenNVInput(),
				view.getCCCDInput(), view.getSDTInput(), true);
	}

	private void xuLyThemNV() {
		String ma = view.getMaNVInput();
		String tenDN = view.getTenDangNhapNVInput();

		if (ma.isEmpty() || tenDN.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Mã nhân viên và Tên đăng nhập không được để trống!");
			return;
		}

		if (nvDAO.findById(ma) != null) {
			JOptionPane.showMessageDialog(view, "Mã nhân viên đã tồn tại!");
			return;
		}

		NhanVien nvCheck = nvDAO.timNhanVienTheoTenDangNhap(tenDN);
		if (nvCheck != null) {
			JOptionPane.showMessageDialog(view,
					"Tên đăng nhập này đã được cấp cho nhân viên khác (" + nvCheck.getMaNhanVien() + ")!");
			return;
		}

		TaiKhoan tk = tkDAO.findById(tenDN);
		String maVaiTroDB = view.getVaiTroNVSelected().equals("Quản lý") ? "QuanLy" : "ThuNgan";

		if (tk == null) {
			tk = new TaiKhoan(tenDN, "123456", true, maVaiTroDB);
			if (!tkDAO.insert(tk)) {
				JOptionPane.showMessageDialog(view, "Lỗi tạo tài khoản hệ thống!");
				return;
			}
		} else {
			tk.setVaiTro(maVaiTroDB);
			tkDAO.update(tk);
		}

		NhanVien nv = layDuLieuNhanVienTuForm();
		if (nvDAO.insert(nv)) {
			JOptionPane.showMessageDialog(view, "Thêm Nhân viên thành công!");
			lamMoiBangNhanVien();
			lamMoiBangTaiKhoan();
			view.xoaTrangFormNhanVien();
		} else {
			JOptionPane.showMessageDialog(view, "Lỗi thêm nhân viên!");
		}
	}

	private void xuLySuaNV() {
		String ma = view.getMaNVInput();
		if (ma.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Chưa chọn nhân viên cần sửa!");
			return;
		}

		NhanVien nv = layDuLieuNhanVienTuForm();
		if (nvDAO.update(nv)) {
			String maVaiTroDB = view.getVaiTroNVSelected().equals("Quản lý") ? "QuanLy" : "ThuNgan";
			TaiKhoan tk = tkDAO.findById(nv.getTenDangNhap());
			if (tk != null) {
				tk.setVaiTro(maVaiTroDB);
				tkDAO.update(tk);
			}
			JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
			lamMoiBangNhanVien();
			lamMoiBangTaiKhoan();
			view.xoaTrangFormNhanVien();
		} else {
			JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
		}
	}

	private void xuLyXoaNV() {
		String ma = view.getMaNVInput();
		if (ma.isEmpty())
			return;

		NhanVien nv = nvDAO.findById(ma);
		if (nv == null)
			return;

		if (JOptionPane.showConfirmDialog(view, "Xác nhận vô hiệu hóa nhân viên " + ma + " và khóa tài khoản của họ?",
				"Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			String tenDN = nv.getTenDangNhap();

			if (nvDAO.delete(ma)) {
				TaiKhoan tk = tkDAO.findById(tenDN);
				if (tk != null) {
					tk.setTrangThai(false);
					tkDAO.update(tk);
				}
				lamMoiBangNhanVien();
				lamMoiBangTaiKhoan();
				view.xoaTrangFormNhanVien();
				JOptionPane.showMessageDialog(view, "Đã vô hiệu hóa nhân viên!");
			} else {
				JOptionPane.showMessageDialog(view, "Lỗi khi vô hiệu hóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void xuLyTimNV() {
		String key = view.getTimKiemNVInput().toLowerCase().trim();
		if (key.isEmpty()) {
			lamMoiBangNhanVien();
			return;
		}
		view.getTmNhanVien().setRowCount(0);
		List<NhanVien> list = nvDAO.findAll();
		int stt = 1;
		for (NhanVien nv : list) {
			if (nv.getHoTenNhanVien().toLowerCase().contains(key) || nv.getMaNhanVien().toLowerCase().contains(key)) {
				TaiKhoan tk = tkDAO.findById(nv.getTenDangNhap());
				String tenVaiTro = "Chưa cấp";
				if (tk != null) {
					String maDB = tk.getVaiTro() != null ? tk.getVaiTro().trim() : "";
					tenVaiTro = maDB.equals("QuanLy") ? "Quản lý" : "Thu ngân";
				}
				view.getTmNhanVien().addRow(new Object[] { stt++, nv.getMaNhanVien(), nv.getHoTenNhanVien(),
						nv.getTenDangNhap(), nv.getSoDienThoai(), nv.getSoCCCD(), tenVaiTro });
			}
		}
		if (view.getTmNhanVien().getRowCount() == 0) {
			JOptionPane.showMessageDialog(view, "Không tìm thấy!");
			lamMoiBangNhanVien();
		}
	}

	private void lamMoiBangTaiKhoan() {
		view.getTmTaiKhoan().setRowCount(0);
		List<TaiKhoan> list = tkDAO.findAll();
		int stt = 1;
		for (TaiKhoan tk : list) {
			String maDB = tk.getVaiTro() != null ? tk.getVaiTro().trim() : "";
			String hienThi = maDB.equals("QuanLy") ? "Quản lý" : "Thu ngân";
			view.getTmTaiKhoan().addRow(new Object[] { stt++, tk.getTenDangNhap(), tk.getMatKhau(), hienThi,
					tk.isTrangThai() ? "Hoạt động" : "Khóa" });
		}
	}

	private TaiKhoan layDuLieuTaiKhoanTuForm() {
		String user = view.getTK_TenDangNhapSelected();
		String pass = view.getTK_MatKhauInput();
		boolean trangThai = view.getTK_TrangThaiSelected().equals("Hoạt động");
		String tenUI = view.getTK_VaiTroSelected();
		String maDB = tenUI.equals("Quản lý") ? "QuanLy" : "ThuNgan";
		return new TaiKhoan(user, pass, trangThai, maDB);
	}

	private void xuLyThemTK() {
		String user = view.getTK_TenDangNhapSelected();
		if (user.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Chưa có tên đăng nhập!");
			return;
		}
		if (tkDAO.findById(user) != null) {
			JOptionPane.showMessageDialog(view, "Tài khoản này đã tồn tại!");
			return;
		}
		TaiKhoan tk = layDuLieuTaiKhoanTuForm();
		if (tkDAO.insert(tk)) {
			JOptionPane.showMessageDialog(view, "Thêm tài khoản thành công!");
			lamMoiBangTaiKhoan();
		}
	}

	private void xuLySuaTK() {
		String user = view.getTK_TenDangNhapSelected();
		if (user.isEmpty() || tkDAO.findById(user) == null) {
			JOptionPane.showMessageDialog(view, "Không tìm thấy tài khoản để sửa!");
			return;
		}
		TaiKhoan tk = layDuLieuTaiKhoanTuForm();
		if (tkDAO.update(tk)) {
			JOptionPane.showMessageDialog(view, "Cập nhật tài khoản thành công!");
			lamMoiBangTaiKhoan();
			lamMoiBangNhanVien();
		}
	}

	private void xuLyXoaTK() {
		String user = view.getTK_TenDangNhapSelected();
		if (user.isEmpty())
			return;

		NhanVien nvCheck = nvDAO.timNhanVienTheoTenDangNhap(user);
		if (nvCheck != null && nvCheck.isTrangThai()) {
			JOptionPane.showMessageDialog(view, "Tài khoản đang được nhân viên " + nvCheck.getMaNhanVien()
					+ " sử dụng. Sẽ tiến hành khóa tài khoản thay vì xóa!");
			TaiKhoan tk = tkDAO.findById(user);
			if (tk != null) {
				tk.setTrangThai(false);
				tkDAO.update(tk);
				lamMoiBangTaiKhoan();
				JOptionPane.showMessageDialog(view, "Đã khóa tài khoản!");
			}
			return;
		}

		if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa tài khoản " + user + "?", "Xóa",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			if (tkDAO.delete(user)) {
				lamMoiBangTaiKhoan();
				view.xoaTrangFormTaiKhoan();
				JOptionPane.showMessageDialog(view, "Đã xóa!");
			}
		}
	}

	private void xuLyTimTK() {
		String key = view.getTK_TimInput().toLowerCase().trim();
		if (key.isEmpty()) {
			lamMoiBangTaiKhoan();
			return;
		}
		view.getTmTaiKhoan().setRowCount(0);
		List<TaiKhoan> list = tkDAO.findAll();
		int stt = 1;
		for (TaiKhoan tk : list) {
			if (tk.getTenDangNhap().toLowerCase().contains(key)) {
				String maDB = tk.getVaiTro() != null ? tk.getVaiTro().trim() : "";
				String hienThi = maDB.equals("QuanLy") ? "Quản lý" : "Thu ngân";
				view.getTmTaiKhoan().addRow(new Object[] { stt++, tk.getTenDangNhap(), tk.getMatKhau(), hienThi,
						tk.isTrangThai() ? "Hoạt động" : "Khóa" });
			}
		}
		if (view.getTmTaiKhoan().getRowCount() == 0) {
			JOptionPane.showMessageDialog(view, "Không tìm thấy tài khoản nào!");
			lamMoiBangTaiKhoan();
		}
	}
}