package controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.NhanVienDAO;
import dao.PhanCongCaLamDAO;
import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.PhanCongCaLam;
import entity.TaiKhoan;
import ui.DlgCaLamViec;

public class CaLamViecController {
	private DlgCaLamViec view;
	private PhanCongCaLamDAO pcDAO;
	private NhanVienDAO nvDAO;
	private TaiKhoanDAO tkDAO;

	public CaLamViecController(DlgCaLamViec view) {
		this.view = view;
		this.pcDAO = new PhanCongCaLamDAO();
		this.nvDAO = new NhanVienDAO();
		this.tkDAO = new TaiKhoanDAO(); // Khởi tạo mạch máu mới

		caiDatDanhSachNhanVien();
		loadLichLamViec();

		this.view.addBtnTroVeListener(e -> chuyenTuan(-7));
		this.view.addBtnTiepListener(e -> chuyenTuan(7));
		this.view.addBtnHienTaiListener(e -> {
			view.setNgayBatDauTuan(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));
			view.capNhatDuLieuTuan();
			loadLichLamViec();
		});

		this.view.addBtnLuuListener(e -> luuPhanCong());
	}

	private void chuyenTuan(int days) {
		view.setNgayBatDauTuan(view.getNgayBatDauTuan().plusDays(days));
		view.capNhatDuLieuTuan();
		loadLichLamViec();
	}

	private void caiDatDanhSachNhanVien() {
		List<NhanVien> dsNV = nvDAO.findAll();
		List<String> listTenNV = new ArrayList<>();
		if (dsNV != null) {
			for (NhanVien nv : dsNV) {
				// TUYỆT CHIÊU KẾT LIỄU: Kiểm tra xem tài khoản của kẻ này có đang bị khóa hay
				// không!
				TaiKhoan tk = tkDAO.findById(nv.getTenDangNhap());
				if (tk != null && tk.isTrangThai()) {
					listTenNV.add(nv.getMaNhanVien() + " - " + nv.getHoTenNhanVien());
				}
			}
		}
		view.setDanhSachNhanVien(listTenNV);
	}

	private void loadLichLamViec() {
		DefaultTableModel model = view.getTmCaLamViec();
		LocalDate start = view.getNgayBatDauTuan();
		LocalDate end = start.plusDays(6);

		for (int row = 0; row < 3; row++) {
			for (int col = 1; col <= 7; col++) {
				model.setValueAt("", row, col);
			}
		}

		List<PhanCongCaLam> dsPhanCong = pcDAO.findByDateRange(start, end);
		if (dsPhanCong != null) {
			for (PhanCongCaLam pc : dsPhanCong) {
				int row = pc.getMaCa().equals("CA01") ? 0 : (pc.getMaCa().equals("CA02") ? 1 : 2);
				int col = pc.getNgayLam().toLocalDate().getDayOfWeek().getValue();

				NhanVien nv = nvDAO.findById(pc.getMaNhanVien());
				if (nv != null) {
					String tenMoi = nv.getMaNhanVien() + " - " + nv.getHoTenNhanVien();
					String tenHienTai = (String) model.getValueAt(row, col);
					if (tenHienTai == null || tenHienTai.isEmpty()) {
						model.setValueAt(tenMoi, row, col);
					} else {
						if (!tenHienTai.contains(tenMoi)) {
							model.setValueAt(tenHienTai + "\n" + tenMoi, row, col);
						}
					}
				}
			}
		}
	}

	private void luuPhanCong() {
		LocalDate start = view.getNgayBatDauTuan();

		pcDAO.deleteByDateRange(start, start.plusDays(6));

		DefaultTableModel tm = view.getTmCaLamViec();
		int count = 0;
		for (int row = 0; row < 3; row++) {
			String maCa = row == 0 ? "CA01" : (row == 1 ? "CA02" : "CA03");
			for (int col = 1; col <= 7; col++) {
				Object val = tm.getValueAt(row, col);

				if (val != null && !val.toString().trim().isEmpty()) {
					String[] mangNhanVien = val.toString().split("\n");
					LocalDate ngay = start.plusDays(col - 1);

					for (String chuoiNV : mangNhanVien) {
						String maNV = chuoiNV.split(" - ")[0].trim();
						PhanCongCaLam pcMoi = new PhanCongCaLam(maNV, maCa, ngay.atStartOfDay(), false);
						if (pcDAO.insert(pcMoi))
							count++;
					}
				}
			}
		}
		JOptionPane.showMessageDialog(view, "Đã lưu " + count + " ca làm thành công!");
		loadLichLamViec();
	}
}