package controller;

import javax.swing.JOptionPane;

import ui.DlgBangGia;
import ui.DlgCaLamViec;
import ui.DlgDatBan;
import ui.DlgHangHoa;
import ui.DlgKhuVucBan;
import ui.DlgNhanVien;
import ui.FrmDangNhap;
import ui.FrmManHinhChinh;

public class MainDashboardController {
	private FrmManHinhChinh dashboardView;

	public MainDashboardController(FrmManHinhChinh dashboardView) {
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
	}

	public void hienThiDaiSanh() {
		dashboardView.setVisible(true);
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

	private void xuLyDangXuat() {
		int xacNhan = JOptionPane.showConfirmDialog(dashboardView, "Xác nhận đăng xuất?", "Đăng xuất",
				JOptionPane.YES_NO_OPTION);
		if (xacNhan == JOptionPane.YES_OPTION) {
			dashboardView.dispose();
			dashboardView.resetDuLieu();

			FrmDangNhap loginView = new FrmDangNhap(null);
			new AuthenticationController(loginView, this).batDau();
		}
	}
}