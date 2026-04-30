package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.DatBanDAO;
import entity.DatBan;
import ui.DlgDatBan;
import util.DataBaseConnection;

public class DatBanController {
	private DlgDatBan view;
	private DatBanDAO dao;

	public DatBanController(DlgDatBan view) {
		this.view = view;
		this.dao = new DatBanDAO();

		initData();
		setupEvents();
	}

	private void initData() {
		loadKhuVuc();
		if (view.getCbMaKhuVuc().getItemCount() > 0) {
			String selectedKV = view.getCbMaKhuVuc().getSelectedItem().toString();
			loadBanTheoKhuVuc(selectedKV);
		}
		loadDuLieuLenBang();
	}

	private void setupEvents() {
		this.view.addBtnThemListener(e -> handleAction("THEM"));
		this.view.addBtnSuaListener(e -> handleAction("SUA"));
		this.view.addBtnXoaListener(e -> xoaDatBan());
		this.view.addBtnXoaTrangListener(e -> xoaTrang());

		this.view.getCbMaKhuVuc().addActionListener(e -> {
			String selectedKV = (String) view.getCbMaKhuVuc().getSelectedItem();
			if (selectedKV != null) {
				loadBanTheoKhuVuc(selectedKV);
			}
		});

		this.view.getTbDatBan().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && view.getTbDatBan().getSelectedRow() != -1) {
				hienThiLenForm(view.getTbDatBan().getSelectedRow());
			}
		});

		this.view.getTbDatBan().addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int col = view.getTbDatBan().columnAtPoint(e.getPoint());
				int row = view.getTbDatBan().rowAtPoint(e.getPoint());
				if (col == 8) {
					xemChiTiet(row);
				}
			}
		});
	}

	private void loadKhuVuc() {
		view.getCbMaKhuVuc().removeAllItems();
		Connection c = DataBaseConnection.getInstance().getConnection();
		String sql = "SELECT maKhuVuc FROM KhuVuc";
		try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				view.getCbMaKhuVuc().addItem(rs.getString(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadBanTheoKhuVuc(String maKV) {
		view.getCbMaBan().removeAllItems();
		Connection c = DataBaseConnection.getInstance().getConnection();
		String sql = "SELECT maBan FROM Ban WHERE maKhuVuc = ?";
		try (PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, maKV);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					view.getCbMaBan().addItem(rs.getString(1));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void xemChiTiet(int row) {
		DefaultTableModel tm = view.getTmDatBan();
		String maDB = tm.getValueAt(row, 1) != null ? tm.getValueAt(row, 1).toString() : "";
		String khuVuc = tm.getValueAt(row, 2) != null ? tm.getValueAt(row, 2).toString() : "";
		String ban = tm.getValueAt(row, 3) != null ? tm.getValueAt(row, 3).toString() : "";
		String tenKhach = tm.getValueAt(row, 4) != null ? tm.getValueAt(row, 4).toString() : "";
		String sdt = tm.getValueAt(row, 5) != null ? tm.getValueAt(row, 5).toString() : "";
		String thoiGian = tm.getValueAt(row, 6) != null ? tm.getValueAt(row, 6).toString() : "";
		String trangThai = tm.getValueAt(row, 7) != null ? tm.getValueAt(row, 7).toString() : "";

		view.hienThiDialogChiTiet(maDB, khuVuc, ban, tenKhach, sdt, thoiGian, trangThai);
	}

	private void loadDuLieuLenBang() {
		DefaultTableModel tm = view.getTmDatBan();
		tm.setRowCount(0);
		List<DatBan> ds = dao.findAll();
		int stt = 1;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		for (DatBan db : ds) {
			String chuTrangThai = db.isTrangThai() ? "Đã nhận bàn" : "Chờ nhận bàn";
			String chuThoiGianNhan = db.getThoiGianNhanBan() != null ? db.getThoiGianNhanBan().format(df) : "";
			String maKV = db.getMaKhuVuc() != null ? db.getMaKhuVuc() : "";
			String maBan = db.getMaBan() != null ? db.getMaBan() : "";

			tm.addRow(new Object[] { stt++, db.getMaDatBan(), maKV, maBan, db.getTenKhachHang(), db.getSoDienThoai(),
					chuThoiGianNhan, chuTrangThai, "Xem" });
		}
	}

	private void hienThiLenForm(int row) {
		DefaultTableModel tm = view.getTmDatBan();
		view.getTfTenKhach().setText(tm.getValueAt(row, 4).toString());
		view.getTfSoDienThoai().setText(tm.getValueAt(row, 5).toString());

		String maKV = tm.getValueAt(row, 2) != null ? tm.getValueAt(row, 2).toString() : "";
		view.getCbMaKhuVuc().setSelectedItem(maKV);

		String maBan = tm.getValueAt(row, 3) != null ? tm.getValueAt(row, 3).toString() : "";
		view.getCbMaBan().setSelectedItem(maBan);

		String tgStr = tm.getValueAt(row, 6).toString();
		if (!tgStr.isEmpty()) {
			try {
				Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(tgStr);
				view.getSpThoiGianNhan().setValue(date);
			} catch (Exception e) {
				view.getSpThoiGianNhan().setValue(new Date());
			}
		} else {
			view.getSpThoiGianNhan().setValue(new Date());
		}

		String trangThaiBang = tm.getValueAt(row, 7).toString();
		for (int i = 0; i < view.getCbTrangThai().getItemCount(); i++) {
			if (view.getCbTrangThai().getItemAt(i).contains(trangThaiBang)
					|| trangThaiBang.contains(view.getCbTrangThai().getItemAt(i))) {
				view.getCbTrangThai().setSelectedIndex(i);
				break;
			}
		}
	}

	private void xoaTrang() {
		view.getTfTenKhach().setText("");
		view.getTfSoDienThoai().setText("");
		if (view.getCbMaKhuVuc().getItemCount() > 0) {
			view.getCbMaKhuVuc().setSelectedIndex(0);
		}
		view.getSpThoiGianNhan().setValue(new Date());
		view.getTbDatBan().clearSelection();
	}

	private void handleAction(String action) {
		try {
			String ten = view.getTfTenKhach().getText().trim();
			String sdt = view.getTfSoDienThoai().getText().trim();
			String maKhuVuc = view.getCbMaKhuVuc().getSelectedItem() != null
					? view.getCbMaKhuVuc().getSelectedItem().toString()
					: "";
			String maBan = view.getCbMaBan().getSelectedItem() != null ? view.getCbMaBan().getSelectedItem().toString()
					: "";

			Date spDate = (Date) view.getSpThoiGianNhan().getValue();
			LocalDateTime thoiGianNhanBan = spDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

			if (ten.isEmpty() || sdt.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Phải điền tên khách và số điện thoại!");
				return;
			}

			String cbText = view.getCbTrangThai().getSelectedItem().toString().toLowerCase();
			boolean trangThai = cbText.contains("đã") || cbText.contains("xong");

			if (action.equals("THEM")) {
				String maMoi = "DB" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
				// Khởi tạo DatBan đủ 8 tham số
				DatBan db = new DatBan(maMoi, maBan, ten, sdt, null, thoiGianNhanBan, trangThai, maKhuVuc);
				if (dao.insert(db)) {
					JOptionPane.showMessageDialog(view, "Thêm thành công!\nMã: " + maMoi);
					loadDuLieuLenBang();
					xoaTrang();
				} else {
					JOptionPane.showMessageDialog(view, "Thêm thất bại!");
				}
			} else if (action.equals("SUA")) {
				int row = view.getTbDatBan().getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
					return;
				}
				String maCu = view.getTbDatBan().getValueAt(row, 1).toString();
				DatBan db = new DatBan(maCu, maBan, ten, sdt, null, thoiGianNhanBan, trangThai, maKhuVuc);
				if (dao.update(db)) {
					JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
					loadDuLieuLenBang();
				} else {
					JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(view, "Lỗi xử lý ngày giờ!");
			ex.printStackTrace();
		}
	}

	private void xoaDatBan() {
		int row = view.getTbDatBan().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Chọn dòng cần xóa!");
			return;
		}
		String maCu = view.getTbDatBan().getValueAt(row, 1).toString();
		if (JOptionPane.showConfirmDialog(view, "Chắc chắn xóa " + maCu + "?", "Xác nhận",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			if (dao.delete(maCu)) {
				JOptionPane.showMessageDialog(view, "Xóa thành công!");
				loadDuLieuLenBang();
				xoaTrang();
			}
		}
	}
}