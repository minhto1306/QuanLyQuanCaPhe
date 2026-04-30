package controller;

import javax.swing.JOptionPane;

import dao.DanhMucDAO;
import entity.DanhMuc;
import ui.DlgThemDanhMuc;

public class ThemDanhMucController {

	private DlgThemDanhMuc view;
	private DanhMucDAO danhMucDAO;

	public ThemDanhMucController(DlgThemDanhMuc view) {
		this.view = view;
		this.danhMucDAO = new DanhMucDAO();
		khoiTaoSuKien();
	}

	private void khoiTaoSuKien() {
		view.addDongListener(e -> view.dispose());

		view.addLuuThemMoiListener(e -> {
			String ma = view.getMaDanhMuc().trim();
			String ten = view.getTenDanhMuc().trim();

			if (ma.isEmpty() || ten.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Không được để trống thông tin!");
				return;
			}

			if (!ma.matches("^[a-zA-Z0-9_]+$")) {
				JOptionPane.showMessageDialog(view,
						"Mã danh mục không hợp lệ! Chỉ dùng chữ cái, số và dấu gạch dưới (_).", "Lỗi mã",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			DanhMuc dm = new DanhMuc(ma, ten);
			if (danhMucDAO.insert(dm)) {
				JOptionPane.showMessageDialog(view, "Thêm thành công!");
				view.xoaTrangThongTin();
			} else {
				JOptionPane.showMessageDialog(view, "Thêm thất bại. Trùng mã hoặc lỗi!");
			}
		});
	}
}