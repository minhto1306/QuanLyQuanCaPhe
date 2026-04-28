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
			String ma = view.getMaDanhMuc();
			String ten = view.getTenDanhMuc();

			if (ma.isEmpty() || ten.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Không được để trống thông tin!");
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