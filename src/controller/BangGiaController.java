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

	public BangGiaController(DlgBangGia view) {
		this.bangGiaView = view;
		// Vừa gọi Controller là nó tự động lấy dữ liệu đắp lên View luôn
		loadDuLieuLenBang();
	}

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

		// Lấy cái bảng từ bên View qua để nhét dữ liệu vô
		DefaultTableModel tbModel = bangGiaView.getTbModel();
		tbModel.setRowCount(0); // Xóa trắng trước khi thêm
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