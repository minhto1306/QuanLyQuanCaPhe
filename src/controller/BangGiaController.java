package controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import dao.DanhMucDAO;
import dao.SanPhamDAO;
import entity.DanhMuc;
import entity.SanPham;
import ui.DlgBangGia;

public class BangGiaController {
	private DlgBangGia bangGiaView;

	// CHỨC NĂNG: Khởi tạo tiến trình điều khiển để kết xuất thông tin bảng giá sản
	// phẩm.
	public BangGiaController(DlgBangGia view) {
		this.bangGiaView = view;
		loadDuLieuLenBang();
	}

	// CHỨC NĂNG: Tải thông tin từ cơ sở dữ liệu để hiển thị chi tiết giá cả của
	// toàn bộ sản phẩm.
	public void loadDuLieuLenBang() {
		SanPhamDAO spDAO = new SanPhamDAO();
		DanhMucDAO dmDAO = new DanhMucDAO();

		List<SanPham> danhSachSP = spDAO.findAll();
		List<DanhMuc> danhSachDM = dmDAO.findAll();

		Map<String, String> mapDM = new HashMap<>();
		if (danhSachDM != null) {
			for (DanhMuc dm : danhSachDM) {
				mapDM.put(dm.getMaDanhMuc(), dm.getTenDanhMuc());
			}
		}

		DefaultTableModel tbModel = bangGiaView.getTbModel();
		tbModel.setRowCount(0);
		int stt = 1;
		DecimalFormat df = new DecimalFormat("#,### VNĐ");

		if (danhSachSP != null) {
			for (SanPham sp : danhSachSP) {
				String tenDM = mapDM.getOrDefault(sp.getMaDanhMuc(), "Không rõ");
				String giaBan = df.format(sp.getGiaBan());
				String trangThai = sp.isTrangThai() ? "Đang bán" : "Ngừng bán";

				tbModel.addRow(new Object[] { stt++, sp.getMaSanPham(), sp.getTenSanPham(), tenDM, giaBan, trangThai });
			}
		}
	}

}