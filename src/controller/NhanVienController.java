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

	private boolean isEditingNV = false;
	private boolean isEditingTK = false;

	// CHỨC NĂNG: Khởi tạo luồng điều khiển xử lý chức năng quản lý nhân viên và tài
	// khoản.
	public NhanVienController(DlgNhanVien view) {
		this.view = view;
		this.nvDAO = new NhanVienDAO();
		this.tkDAO = new TaiKhoanDAO();

		dangKySuKien();
		lamMoiBangNhanVien();
		lamMoiBangTaiKhoan();
	}

	// CHỨC NĂNG: Lắng nghe và điều hướng các thao tác trên giao diện.
	private void dangKySuKien() {
		view.addThemNVListener(e -> {
			isEditingNV = false;
			xuLyThemNV();
		});

		view.addSuaNVListener(e -> {
			String ma = view.getMaNVInput().trim();
			if (ma.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Chưa chọn nhân viên cần sửa!");
				return;
			}
			isEditingNV = true;
			view.batTatNutNhanVien(false, false, false, true, false);
		});

		view.addXoaNVListener(e -> xuLyXoaNV());

		view.addXoaTrangNVListener(e -> {
			view.xoaTrangFormNhanVien();
			isEditingNV = false;
			view.batTatNutNhanVien(true, true, true, false, true);
		});

		view.addLuuNVListener(e -> {
			if (isEditingNV) {
				xuLySuaNV();
			} else {
				JOptionPane.showMessageDialog(view, "Chỉ dùng nút Lưu khi đang Sửa!");
			}
		});

		view.addTimNVListener(e -> xuLyTimNV());

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

		view.addThemTKListener(e -> {
			isEditingTK = false;
			xuLyThemTK();
		});

		view.addSuaTKListener(e -> {
			String user = view.getTK_TenDangNhapSelected();
			if (user.isEmpty() || tkDAO.findById(user) == null) {
				JOptionPane.showMessageDialog(view, "Chưa chọn tài khoản cần sửa!");
				return;
			}
			isEditingTK = true;
			view.batTatNutTaiKhoan(false, false, false, true, false);
		});

		view.addXoaTKListener(e -> xuLyXoaTK());

		view.addXoaTrangTKListener(e -> {
			view.xoaTrangFormTaiKhoan();
			isEditingTK = false;
			view.batTatNutTaiKhoan(true, true, true, false, true);
		});

		view.addLuuTKListener(e -> {
			if (isEditingTK) {
				xuLySuaTK();
			} else {
				JOptionPane.showMessageDialog(view, "Chỉ dùng nút Lưu khi đang Sửa!");
			}
		});

		view.addTimTKListener(e -> xuLyTimTK());

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

	// CHỨC NĂNG: Cập nhật lại dữ liệu hiển thị trên bảng danh sách nhân viên.
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

	// CHỨC NĂNG: Khởi tạo một đối tượng Nhân viên từ dữ liệu nhập trên form.
	private NhanVien layDuLieuNhanVienTuForm() {
		return new NhanVien(view.getMaNVInput(), view.getTenDangNhapNVInput(), view.getHoTenNVInput(),
				view.getCCCDInput(), view.getSDTInput(), true);
	}

	// CHỨC NĂNG: Xác thực và xử lý việc lưu thông tin nhân viên mới vào cơ sở dữ
	// liệu.
	private void xuLyThemNV() {
		String ma = view.getMaNVInput().trim();
		String tenDN = view.getTenDangNhapNVInput().trim();
		String hoTen = view.getHoTenNVInput().trim();
		String cccd = view.getCCCDInput().trim();
		String sdt = view.getSDTInput().trim();

		if (ma.isEmpty() || tenDN.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Mã nhân viên và Tên đăng nhập không được để trống!");
			return;
		}

		if (!ma.matches("^[a-zA-Z0-9_]+$")) {
			JOptionPane.showMessageDialog(view,
					"Mã nhân viên không hợp lệ! Không chứa khoảng trắng hay ký tự đặc biệt.");
			return;
		}
		if (!hoTen.matches("^[\\p{L}\\s]+$")) {
			JOptionPane.showMessageDialog(view, "Họ tên không hợp lệ! Chỉ được chứa chữ cái và khoảng trắng.");
			return;
		}
		if (!sdt.matches("^0\\d{9}$")) {
			JOptionPane.showMessageDialog(view, "Số điện thoại không hợp lệ! Phải gồm 10 chữ số và bắt đầu bằng số 0.");
			return;
		}
		if (!cccd.matches("^\\d{12}$")) {
			JOptionPane.showMessageDialog(view, "Căn cước công dân không hợp lệ! Phải chứa đúng 12 chữ số.");
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

	// CHỨC NĂNG: Xác thực và xử lý việc cập nhật thông tin nhân viên đã tồn tại.
	private void xuLySuaNV() {
		String hoTen = view.getHoTenNVInput().trim();
		String cccd = view.getCCCDInput().trim();
		String sdt = view.getSDTInput().trim();

		if (!hoTen.matches("^[\\p{L}\\s]+$")) {
			JOptionPane.showMessageDialog(view, "Họ tên không hợp lệ! Chỉ được chứa chữ cái và khoảng trắng.");
			return;
		}
		if (!sdt.matches("^0\\d{9}$")) {
			JOptionPane.showMessageDialog(view, "Số điện thoại không hợp lệ! Phải gồm 10 chữ số và bắt đầu bằng số 0.");
			return;
		}
		if (!cccd.matches("^\\d{12}$")) {
			JOptionPane.showMessageDialog(view, "Căn cước công dân không hợp lệ! Phải chứa đúng 12 chữ số.");
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
			isEditingNV = false;
			view.batTatNutNhanVien(true, true, true, false, true);
		} else {
			JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
		}
	}

	// CHỨC NĂNG: Xử lý thao tác loại bỏ nhân viên khỏi cơ sở dữ liệu.
	private void xuLyXoaNV() {
		String ma = view.getMaNVInput();
		if (ma.isEmpty())
			return;

		NhanVien nv = nvDAO.findById(ma);
		if (nv == null)
			return;

		if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa hoàn toàn nhân viên " + ma + " và tài khoản của họ?",
				"Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			String tenDN = nv.getTenDangNhap();

			if (nvDAO.delete(ma)) {
				if (tenDN != null && !tenDN.isEmpty()) {
					tkDAO.delete(tenDN);
				}
				lamMoiBangNhanVien();
				lamMoiBangTaiKhoan();
				view.xoaTrangFormNhanVien();
				JOptionPane.showMessageDialog(view, "Đã xóa thành công nhân viên và tài khoản!");
			} else {
				JOptionPane.showMessageDialog(view,
						"Lỗi khi xóa! Chắc chắn nhân viên này không còn tồn tại trong Hóa Đơn hoặc Lịch Làm Việc.",
						"Lỗi Ràng Buộc", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// CHỨC NĂNG: Xử lý chức năng tìm kiếm thông tin nhân viên trong bảng.
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

	// CHỨC NĂNG: Cập nhật lại dữ liệu hiển thị trên bảng danh sách tài khoản.
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

	// CHỨC NĂNG: Khởi tạo một đối tượng Tài Khoản từ dữ liệu nhập trên form.
	private TaiKhoan layDuLieuTaiKhoanTuForm() {
		String user = view.getTK_TenDangNhapSelected();
		String pass = view.getTK_MatKhauInput();
		boolean trangThai = view.getTK_TrangThaiSelected().equals("Hoạt động");
		String tenUI = view.getTK_VaiTroSelected();
		String maDB = tenUI.equals("Quản lý") ? "QuanLy" : "ThuNgan";
		return new TaiKhoan(user, pass, trangThai, maDB);
	}

	// CHỨC NĂNG: Xác thực và xử lý việc lưu thông tin tài khoản mới.
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

	// CHỨC NĂNG: Xác thực và xử lý việc cập nhật thông tin tài khoản đã tồn tại.
	private void xuLySuaTK() {
		TaiKhoan tk = layDuLieuTaiKhoanTuForm();
		if (tkDAO.update(tk)) {
			JOptionPane.showMessageDialog(view, "Cập nhật tài khoản thành công!");
			lamMoiBangTaiKhoan();
			lamMoiBangNhanVien();

			view.xoaTrangFormTaiKhoan();
			isEditingTK = false;
			view.batTatNutTaiKhoan(true, true, true, false, true);
		}
	}

	// CHỨC NĂNG: Xử lý thao tác loại bỏ tài khoản ra khỏi cơ sở dữ liệu.
	private void xuLyXoaTK() {
		String user = view.getTK_TenDangNhapSelected();
		if (user.isEmpty())
			return;

		NhanVien nvCheck = nvDAO.timNhanVienTheoTenDangNhap(user);
		if (nvCheck != null) {
			JOptionPane.showMessageDialog(view, "Tài khoản đang được nhân viên " + nvCheck.getMaNhanVien()
					+ " sử dụng. Phải xóa nhân viên trước khi xóa tài khoản!");
			return;
		}

		if (JOptionPane.showConfirmDialog(view, "Xác nhận xóa hoàn toàn tài khoản " + user + "?", "Xóa",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			if (tkDAO.delete(user)) {
				lamMoiBangTaiKhoan();
				view.xoaTrangFormTaiKhoan();
				JOptionPane.showMessageDialog(view, "Đã xóa!");
			}
		}
	}

	// CHỨC NĂNG: Xử lý chức năng tìm kiếm thông tin tài khoản trong bảng.
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