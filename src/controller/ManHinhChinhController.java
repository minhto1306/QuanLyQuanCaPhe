package controller;

import javax.swing.JOptionPane;

import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.TaiKhoan;
import ui.DlgBangGia;
import ui.DlgCaLamViec;
import ui.DlgDatBan;
import ui.DlgHangHoa;
import ui.DlgHoaDon;
import ui.DlgKhuVucBan;
import ui.DlgNhanVien;
import ui.FrmDangNhap;
import ui.FrmManHinhChinh;

public class ManHinhChinhController {
	private FrmManHinhChinh dashboardView;

	private String tenDangNhapHienTai;

	// CHỨC NĂNG: Khởi tạo điều khiển quản lý các thao tác điều hướng từ màn hình
	// chính.
	public ManHinhChinhController(FrmManHinhChinh dashboardView) {
		this.dashboardView = dashboardView;
		ketNoiKinhMach();
	}

	// CHỨC NĂNG: Thiết lập các sự kiện lắng nghe để liên kết menu chức năng với
	// phương thức tương ứng.
	private void ketNoiKinhMach() {
		dashboardView.addXemBangGiaListener(e -> moCuaSoBangGia());
		dashboardView.addQLNhanVienListener(e -> moCuaSoNhanVien());
		dashboardView.addQLCaLamListener(e -> moCuaSoCaLam());
		dashboardView.addQLSoDoListener(e -> moCuaSoSoDo());
		dashboardView.addQLHangHoaListener(e -> moCuaSoHangHoa());
		dashboardView.addQLDatBanListener(e -> moCuaSoDatBan());
		dashboardView.addDangXuatListener(e -> xuLyDangXuat());
		dashboardView.addHoaDonListener(e -> moCuaSoHoaDon());
		dashboardView.addThongKeListener(e -> moCuaSoThongKe());
	}

	// CHỨC NĂNG: Hiển thị cửa sổ phụ Thống Kê.
	private void moCuaSoThongKe() {
		ui.DlgThongKe dlg = new ui.DlgThongKe(dashboardView);
		new ThongKeController(dlg);
		dlg.setVisible(true);
	}

	// CHỨC NĂNG: Xử lý hiển thị màn hình chính của ứng dụng và nạp dữ liệu mặc
	// định.
	public void hienThiDaiSanh() {
		if (dashboardView != null) {
			dashboardView.loadDataInitial();
			dashboardView.setVisible(true);
		}
	}

	// CHỨC NĂNG: Khởi tạo thông tin người dùng đang trực tiếp thao tác.
	public void setTenNhanVien(String ten) {
		this.tenDangNhapHienTai = ten;
		if (dashboardView != null) {
			NhanVienDAO nvDAO = new NhanVienDAO();
			NhanVien nv = nvDAO.timNhanVienTheoTenDangNhap(ten);
			if (nv != null) {
				dashboardView.setTenNhanVien(nv.getHoTenNhanVien());
			} else {
				dashboardView.setTenNhanVien(ten != null ? ten : "Admin");
			}
		}
	}

	// CHỨC NĂNG: Phân cấp quyền truy cập tính năng.
	public void phanQuyen(String vaiTro) {
		if (dashboardView != null) {
			dashboardView.phanQuyen(vaiTro);
		}
	}

	// CHỨC NĂNG: Hiển thị cửa sổ phụ Bảng Giá.
	private void moCuaSoBangGia() {
		DlgBangGia dlgBangGia = new DlgBangGia(dashboardView);
		new BangGiaController(dlgBangGia);
		dlgBangGia.setVisible(true);
	}

	// CHỨC NĂNG: Hiển thị cửa sổ phụ Nhân Viên.
	private void moCuaSoNhanVien() {
		DlgNhanVien dialog = new DlgNhanVien(dashboardView);
		new NhanVienController(dialog);
		dialog.setVisible(true);
	}

	// CHỨC NĂNG: Hiển thị cửa sổ phụ Ca Làm Việc.
	private void moCuaSoCaLam() {
		DlgCaLamViec dlg = new DlgCaLamViec(dashboardView);
		new CaLamViecController(dlg);

		if (this.tenDangNhapHienTai != null) {
			TaiKhoanDAO tkDAO = new TaiKhoanDAO();
			TaiKhoan tk = tkDAO.findById(this.tenDangNhapHienTai);
			if (tk != null) {
				dlg.phanQuyen(tk.getVaiTro());
			}
		}

		dlg.setVisible(true);
	}

	// CHỨC NĂNG: Hiển thị cửa sổ phụ Sơ Đồ Khu Vực Bàn.
	private void moCuaSoSoDo() {
		DlgKhuVucBan view = new DlgKhuVucBan(dashboardView);
		new KhuVucController(view);
		view.setVisible(true);
	}

	// CHỨC NĂNG: Hiển thị cửa sổ phụ Hàng Hóa.
	private void moCuaSoHangHoa() {
		DlgHangHoa dlgHangHoa = new DlgHangHoa(dashboardView);
		new HangHoaController(dlgHangHoa);
		dlgHangHoa.setVisible(true);

		dashboardView.loadDataInitial();
	}

	// CHỨC NĂNG: Hiển thị cửa sổ phụ Đặt Bàn.
	private void moCuaSoDatBan() {
		DlgDatBan dlg = new DlgDatBan(dashboardView);
		new DatBanController(dlg);
		dlg.setVisible(true);
	}

	// CHỨC NĂNG: Hiển thị cửa sổ phụ Hóa Đơn.
	private void moCuaSoHoaDon() {
		DlgHoaDon dlgHoaDon = new DlgHoaDon(dashboardView);
		new HoaDonController(dlgHoaDon);
		dlgHoaDon.setVisible(true);
	}

	// CHỨC NĂNG: Xử lý thao tác yêu cầu đăng xuất để quay về màn hình Đăng Nhập.
	private void xuLyDangXuat() {
		int xacNhan = JOptionPane.showConfirmDialog(dashboardView, "Xác nhận đăng xuất?", "Đăng xuất",
				JOptionPane.YES_NO_OPTION);
		if (xacNhan == JOptionPane.YES_OPTION) {
			dashboardView.dispose();
			dashboardView.resetDuLieu();

			FrmDangNhap loginView = new FrmDangNhap(null);
			new DangNhapController(loginView, this).batDau();
		}
	}
}