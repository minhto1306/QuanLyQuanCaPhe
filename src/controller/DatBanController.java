package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.DatBanDAO;
import entity.DatBan;
import ui.DlgDatBan;

public class DatBanController {
	private DlgDatBan view;
	private DatBanDAO dao;

	public DatBanController(DlgDatBan view) {
		this.view = view;
		this.dao = new DatBanDAO();

		loadDuLieuLenBang();
		this.view.addBtnXoaTrangListener(e -> xoaTrangForm());
		this.view.addBtnThemListener(e -> themDatBan());
		this.view.addBtnLuuListener(e -> themDatBan());
		this.view.addBtnSuaListener(e -> suaDatBan());
		this.view.addBtnXoaListener(e -> xoaDatBan());
		this.view.getTbDatBan().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && view.getTbDatBan().getSelectedRow() != -1) {
				hienThiChiTiet(view.getTbDatBan().getSelectedRow());
			}
		});
	}

	private void loadDuLieuLenBang() {
		DefaultTableModel tm = view.getTmDatBan();
		tm.setRowCount(0);
		List<DatBan> ds = dao.findAll();
		int stt = 1;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		for (DatBan db : ds) {
			String chuTrangThai = db.isTrangThai() ? "Đã nhận bàn" : "Chờ nhận bàn";
			String chuThoiGian = db.getThoiGianDat() != null ? db.getThoiGianDat().format(df) : "";

			tm.addRow(new Object[] { stt++, db.getMaDatBan(), db.getTenKhachHang(), db.getSoDienThoai(), chuThoiGian,
					chuTrangThai, "Xem" });
		}
	}

	private void xoaTrangForm() {
		view.getTfTenKhach().setText("");
		view.getTfSoDienThoai().setText("");
		view.getTfThoiGianDat().setText("");
		view.getCbTrangThai().setSelectedIndex(0);
		view.getTbDatBan().clearSelection();
	}

	private void hienThiChiTiet(int row) {
		DefaultTableModel tm = view.getTmDatBan();
		view.getTfTenKhach().setText(tm.getValueAt(row, 2).toString());
		view.getTfSoDienThoai().setText(tm.getValueAt(row, 3).toString());
		view.getTfThoiGianDat().setText(tm.getValueAt(row, 4).toString());

		String trangThaiBang = tm.getValueAt(row, 5).toString();
		for (int i = 0; i < view.getCbTrangThai().getItemCount(); i++) {
			if (view.getCbTrangThai().getItemAt(i).contains(trangThaiBang)
					|| trangThaiBang.contains(view.getCbTrangThai().getItemAt(i))) {
				view.getCbTrangThai().setSelectedIndex(i);
				break;
			}
		}
	}

	private void themDatBan() {
		try {
			String ten = view.getTfTenKhach().getText().trim();
			String sdt = view.getTfSoDienThoai().getText().trim();
			String tgStr = view.getTfThoiGianDat().getText().trim();

			if (ten.isEmpty() || sdt.isEmpty() || tgStr.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Bạn điền thiêú tên , SDT hoặc thời gian !");
				return;
			}

			String maMoi = "DB" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));

			LocalDateTime thoiGian;
			if (tgStr.contains(":")) {
				thoiGian = LocalDateTime.parse(tgStr, DateTimeFormatter.ofPattern("d/M/yyyy H:m"));
			} else {
				thoiGian = LocalDate.parse(tgStr, DateTimeFormatter.ofPattern("d/M/yyyy")).atStartOfDay();
			}

			String cbText = view.getCbTrangThai().getSelectedItem().toString().toLowerCase();
			boolean trangThai = cbText.contains("đã") || cbText.contains("xong") || cbText.contains("thanh toán");

			DatBan db = new DatBan(maMoi, "", ten, sdt, thoiGian, null, trangThai);

			if (dao.insert(db)) {
				JOptionPane.showMessageDialog(view, "Thêm Đặt bàn thành công!\nMã tự sinh là: " + maMoi);
				loadDuLieuLenBang();
				xoaTrangForm();
			} else {
				JOptionPane.showMessageDialog(view, "Lưu xuống thất bại!");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(view, "Gõ ngày tháng sai cú pháp rồi!");
		}
	}

	private void suaDatBan() {
		int row = view.getTbDatBan().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Click chọn 1 dòng dưới bảng ");
			return;
		}
		try {
			String maCu = view.getTbDatBan().getValueAt(row, 1).toString();
			String ten = view.getTfTenKhach().getText().trim();
			String sdt = view.getTfSoDienThoai().getText().trim();
			String tgStr = view.getTfThoiGianDat().getText().trim();

			LocalDateTime thoiGian;
			if (tgStr.contains(":")) {
				thoiGian = LocalDateTime.parse(tgStr, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			} else {
				thoiGian = LocalDate.parse(tgStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
			}

			String cbText = view.getCbTrangThai().getSelectedItem().toString().toLowerCase();
			boolean trangThai = cbText.contains("đã") || cbText.contains("xong");

			DatBan db = new DatBan(maCu, "", ten, sdt, thoiGian, null, trangThai);
			if (dao.update(db)) {
				JOptionPane.showMessageDialog(view, "Cập nhật dữ liệu thành công!");
				loadDuLieuLenBang();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(view, "Ngày giờ sai định dạng ! ");
		}
	}

	private void xoaDatBan() {
		int row = view.getTbDatBan().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Chọn 1 dòng dưới bảng để xóa!");
			return;
		}
		String maCu = view.getTbDatBan().getValueAt(row, 1).toString();
		if (JOptionPane.showConfirmDialog(view, "Chắc chắn xóa " + maCu + " chưa?", "Hỏi nhẹ",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			if (dao.delete(maCu)) {
				JOptionPane.showMessageDialog(view, "Xóa thành công!");
				loadDuLieuLenBang();
				xoaTrangForm();
			}
		}
	}
}