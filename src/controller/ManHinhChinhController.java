package controller;

import javax.swing.JOptionPane;

import ui.DlgBangGia;
import ui.DlgCaLamViec;
import ui.DlgDatBan;
import ui.DlgHangHoa;
import ui.DlgHoaDon; // Nhớ nạp bùa chú import này vào!
import ui.DlgKhuVucBan;
import ui.DlgNhanVien;
import ui.FrmDangNhap;
import ui.FrmManHinhChinh;

public class ManHinhChinhController {
	private FrmManHinhChinh dashboardView;

	public ManHinhChinhController(FrmManHinhChinh dashboardView) {
		this.dashboardView = dashboardView;
		ketNoiKinhMach();
	}

	private void ketNoiKinhMach() {
		dashboardView.addXemBangGiaListener(e -> moCuaSoBangGia());
		dashboardView.addQLNhanVienListener(e -> moCuaSoNhanVien());
		dashboardView.addQLCaLamListener(e -> moCuaSoCaLam());
		dashboardView.addQLSoDoListener(e -> moCuaSoSoDo());
		dashboardView.addQLHangHoaListener(e -> moCuaSoHangHoa());
		dashboardView.addQLDatBanListener(e -> moCuaSoDatBan());
		dashboardView.addDangXuatListener(e -> xuLyDangXuat());

		// ĐÂY! Đã nối thành công kinh mạch Hóa Đơn!
		dashboardView.addHoaDonListener(e -> moCuaSoHoaDon());
	}

	public void hienThiDaiSanh() {
		dashboardView.setVisible(true);
	}

	// Hàm mới: Đẩy tên nhân viên từ lúc đăng nhập lên Màn Hình Chính
	public void setTenNhanVien(String ten) {
		if (dashboardView != null) {
			dashboardView.setTenNhanVien(ten);
		}
	}

	private void moCuaSoBangGia() {
		DlgBangGia dlgBangGia = new DlgBangGia(dashboardView);
		new BangGiaController(dlgBangGia);
		dlgBangGia.setVisible(true);
	}

	private void moCuaSoNhanVien() {
		DlgNhanVien dialog = new DlgNhanVien(dashboardView);
		new NhanVienController(dialog);
		dialog.setVisible(true);
	}

	private void moCuaSoCaLam() {
		DlgCaLamViec dlg = new DlgCaLamViec(dashboardView);
		new CaLamViecController(dlg);
		dlg.setVisible(true);
	}

	private void moCuaSoSoDo() {
		DlgKhuVucBan view = new DlgKhuVucBan(dashboardView);
		new KhuVucController(view);
		view.setVisible(true);
	}

	private void moCuaSoHangHoa() {
		DlgHangHoa dlgHangHoa = new DlgHangHoa(dashboardView);
		new HangHoaController(dlgHangHoa);
		dlgHangHoa.setVisible(true);
		dashboardView.loadDuLieuHangHoaMain();
	}

	private void moCuaSoDatBan() {
		DlgDatBan dlg = new DlgDatBan(dashboardView);
		new DatBanController(dlg);
		dlg.setVisible(true);
	}

	// CHIÊU THỨC MỚI: Triệu hồi cửa sổ Hóa Đơn
	private void moCuaSoHoaDon() {
		DlgHoaDon dlgHoaDon = new DlgHoaDon(dashboardView);

		// Lời khuyên của bậc tiền bối: Lát nữa rảnh rỗi thì nhớ tạo HoaDonController
		// Rồi tháo phong ấn dòng code bên dưới ra để nó quản lý logic nhé!
		// new HoaDonController(dlgHoaDon);

		dlgHoaDon.setVisible(true);
	}

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