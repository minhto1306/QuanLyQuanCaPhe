package controller;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import ui.DlgThongKe;

public class ThongKeController {
	private DlgThongKe view;
	private HoaDonDAO hdDAO;
	private ChiTietHoaDonDAO cthdDAO;
	private LocalDate ngayBatDau;

	// CHỨC NĂNG: Khởi tạo luồng điều khiển xử lý dữ liệu thống kê.
	public ThongKeController(DlgThongKe view) {
		this.view = view;
		this.hdDAO = new HoaDonDAO();
		this.cthdDAO = new ChiTietHoaDonDAO();
		this.ngayBatDau = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

		khoiTaoSuKien();
		xuLyThongKe();
	}

	// CHỨC NĂNG: Lắng nghe và điều hướng thao tác chuyển tuần thống kê.
	private void khoiTaoSuKien() {
		view.addTuanTruocListener(e -> {
			ngayBatDau = ngayBatDau.minusDays(7);
			xuLyThongKe();
		});
		view.addTuanNayListener(e -> {
			ngayBatDau = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
			xuLyThongKe();
		});
	}

	// CHỨC NĂNG: Tổng hợp và tính toán số liệu kinh doanh theo khung thời gian.
	private void xuLyThongKe() {
		List<HoaDon> tatCaHoaDon = hdDAO.findAll();
		List<Double> doanhThu7Ngay = new ArrayList<>();
		List<String> ten7Ngay = new ArrayList<>();

		double tongDoanhThuTuan = 0;
		int tongSanPhamTuan = 0;
		int tongKhachHang = 0;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM");

		for (int i = 0; i < 7; i++) {
			LocalDate ngayDangXet = ngayBatDau.plusDays(i);
			ten7Ngay.add(ngayDangXet.format(dtf));

			double doanhThuNgayNay = 0;
			for (HoaDon hd : tatCaHoaDon) {
				if (hd.isTrangThai() && hd.getThoiGianLap().toLocalDate().isEqual(ngayDangXet)) {
					doanhThuNgayNay += hd.getThanhTien();
					tongKhachHang++;

					List<ChiTietHoaDon> dsChiTiet = cthdDAO.findByMaHoaDon(hd.getMaHoaDon());
					for (ChiTietHoaDon ct : dsChiTiet) {
						tongSanPhamTuan += ct.getSoLuong();
					}
				}
			}
			doanhThu7Ngay.add(doanhThuNgayNay);
			tongDoanhThuTuan += doanhThuNgayNay;
		}

		DecimalFormat df = new DecimalFormat("#,### VNĐ");

		view.capNhatDuLieu(df.format(tongDoanhThuTuan), String.valueOf(tongSanPhamTuan), String.valueOf(tongKhachHang),
				doanhThu7Ngay, ten7Ngay);
	}
}