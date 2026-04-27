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
		// --- TAB NHÂN VIÊN ---
		view.addThemNVListener(e -> view.xoaTrangFormNhanVien());
		view.addSuaNVListener(e -> xuLyLuuNV());
		view.addLuuNVListener(e -> xuLyLuuNV());
		view.addXoaNVListener(e -> xuLyXoaNV());
		view.addXoaTrangNVListener(e -> view.xoaTrangFormNhanVien());
		view.addTimNVListener(e -> xuLyTimNV());

		view.addBangNhanVienMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = view.getTbNhanVien().getSelectedRow();
				if (row >= 0) {
					// Đã truyền thêm tham số cột số 6 (Vai trò) vào hàm
					view.dienThongTinNhanVienLenForm(view.getTmNhanVien().getValueAt(row, 1).toString(),
							view.getTmNhanVien().getValueAt(row, 2).toString(),
							view.getTmNhanVien().getValueAt(row, 3).toString(),
							view.getTmNhanVien().getValueAt(row, 4).toString(),
							view.getTmNhanVien().getValueAt(row, 5).toString(),
							view.getTmNhanVien().getValueAt(row, 6).toString());
				}
			}
		});

		// --- TAB TÀI KHOẢN ---
		view.addThemTKListener(e -> view.xoaTrangFormTaiKhoan());
		view.addSuaTKListener(e -> xuLyLuuTK());
		view.addLuuTKListener(e -> xuLyLuuTK());
		view.addXoaTKListener(e -> xuLyXoaTK());
		view.addXoaTrangTKListener(e -> view.xoaTrangFormTaiKhoan());

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

	// ======================== LOGIC NHÂN VIÊN ========================
	private void lamMoiBangNhanVien() {
		view.getTmNhanVien().setRowCount(0);
		List<NhanVien> list = nvDAO.findAll();
		int stt = 1;
		for (NhanVien nv : list) {
			// TUYỆT KỸ: Chạy sang bảng Tài khoản tra cứu Vai trò
			TaiKhoan tk = tkDAO.findById(nv.getTenDangNhap());
			String tenVaiTro = "Chưa cấp";
			if (tk != null) {
				String maDB = tk.getVaiTro() != null ? tk.getVaiTro().trim() : "";
				tenVaiTro = maDB.equals("QuanLy") ? "Quản lý" : "Thu ngân";
			}

			view.getTmNhanVien().addRow(new Object[] { stt++, nv.getMaNhanVien(), nv.getHoTenNhanVien(),
					nv.getTenDangNhap(), nv.getSoDienThoai(), nv.getSoCCCD(), tenVaiTro }); // Hết bị cứng nhắc chữ
																							// "Nhân viên" nhé!
		}

		// Đồng bộ ComboBox tab Tài Khoản
		view.getCbTK_TenDangNhap().removeAllItems();
		for (NhanVien nv : list) {
			view.getCbTK_TenDangNhap().addItem(nv.getTenDangNhap());
		}
	}

	private NhanVien layDuLieuNhanVienTuForm() {
		return new NhanVien(view.getMaNVInput(), view.getTenDangNhapNVInput(), view.getHoTenNVInput(),
				view.getCCCDInput(), view.getSDTInput());
	}

	// Đỉnh cao ở đây: Nút Lưu bên tab Nhân viên giờ tự động cấp/sửa luôn Tài khoản
	private void xuLyLuuNV() {
		String ma = view.getMaNVInput();
		if (ma.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Mã nhân viên không được để trống!");
			return;
		}
		NhanVien nv = layDuLieuNhanVienTuForm();

		// 1. Lưu thông tin Nhân viên
		boolean isNew = (nvDAO.findById(ma) == null);
		boolean thanhCong = isNew ? nvDAO.insert(nv) : nvDAO.update(nv);

		if (thanhCong) {
			// 2. Lấy vai trò từ ComboBox dịch sang mã DB
			String maVaiTroDB = view.getVaiTroNVSelected().equals("Quản lý") ? "QuanLy" : "ThuNgan";

			// 3. Tự động kiểm tra và cấp/cập nhật Tài Khoản
			TaiKhoan tk = tkDAO.findById(nv.getTenDangNhap());
			if (tk == null) {
				// Cấp mới tài khoản, pass mặc định 123456
				tkDAO.insert(new TaiKhoan(nv.getTenDangNhap(), "123456", true, maVaiTroDB));
			} else {
				// Đã có tài khoản thì cập nhật lại vai trò theo ý người dùng
				tk.setVaiTro(maVaiTroDB);
				tkDAO.update(tk);
			}

			JOptionPane.showMessageDialog(view, "Lưu thông tin và cập nhật vai trò thành công!");
			lamMoiBangNhanVien();
			lamMoiBangTaiKhoan(); // Cập nhật lại luôn cả bảng Tài khoản cho đồng bộ
		} else {
			JOptionPane.showMessageDialog(view, "Lưu thất bại! Trùng mã hoặc lỗi dữ liệu.");
		}
	}

	private void xuLyXoaNV() {
		String ma = view.getMaNVInput();
		if (ma.isEmpty())
			return;
		if (JOptionPane.showConfirmDialog(view, "Xóa nhân viên " + ma + " sẽ xóa luôn Tài khoản của kẻ này! Tiếp tục?",
				"Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			// Phải xóa Tài khoản trước vì nó chứa khóa ngoại chiếu tới Nhân Viên (nếu có)
			String tenDN = view.getTenDangNhapNVInput();
			tkDAO.delete(tenDN);

			// Xong mới đuổi việc Nhân viên
			if (nvDAO.delete(ma)) {
				lamMoiBangNhanVien();
				lamMoiBangTaiKhoan();
				view.xoaTrangFormNhanVien();
			} else {
				JOptionPane.showMessageDialog(view, "Xóa thất bại do lỗi ràng buộc (Hắn đã từng tạo hóa đơn)!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void xuLyTimNV() {
		String key = view.getTimKiemNVInput().toLowerCase();
		view.getTmNhanVien().setRowCount(0);
		List<NhanVien> list = nvDAO.findAll();
		int stt = 1;
		for (NhanVien nv : list) {
			if (nv.getHoTenNhanVien().toLowerCase().contains(key) || nv.getMaNhanVien().toLowerCase().contains(key)) {

				// Khi tìm kiếm cũng phải móc vai trò ra
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
	}

	// ======================== LOGIC TÀI KHOẢN ========================
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

	private void xuLyLuuTK() {
		String user = view.getTK_TenDangNhapSelected();
		if (user.isEmpty())
			return;

		TaiKhoan tk = layDuLieuTaiKhoanTuForm();

		boolean ok = (tkDAO.findById(user) == null) ? tkDAO.insert(tk) : tkDAO.update(tk);
		if (ok) {
			JOptionPane.showMessageDialog(view, "Lưu tài khoản thành công!");
			lamMoiBangTaiKhoan();
			lamMoiBangNhanVien(); // Nhỡ đổi vai trò bên này, thì bảng Nhân viên bên kia cũng update theo
		} else {
			JOptionPane.showMessageDialog(view, "Lỗi khi lưu! Kiểm tra lại dữ liệu.");
		}
	}

	private void xuLyXoaTK() {
		String user = view.getTK_TenDangNhapSelected();
		if (!user.isEmpty() && tkDAO.delete(user)) {
			lamMoiBangTaiKhoan();
			lamMoiBangNhanVien(); // Để cập nhật chữ "Chưa cấp" bên bảng Nhân viên
			view.xoaTrangFormTaiKhoan();
		}
	}
}