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

	private boolean isEditingDB = false;

	// CHỨC NĂNG: Khởi tạo luồng điều khiển xử lý việc đặt bàn.
	public DatBanController(DlgDatBan view) {
		this.view = view;
		this.dao = new DatBanDAO();

		initData();
		setupEvents();
	}

	// CHỨC NĂNG: Tải toàn bộ cấu hình dữ liệu mặc định lên giao diện.
	private void initData() {
		loadKhuVuc();
		if (view.getCbMaKhuVuc().getItemCount() > 0) {
			String selectedKV = view.getCbMaKhuVuc().getSelectedItem().toString();
			loadBanTheoKhuVuc(selectedKV);
		}
		loadDuLieuLenBang();
	}

	// CHỨC NĂNG: Đăng ký các bộ lắng nghe sự kiện tương tác của người dùng.
	private void setupEvents() {
		this.view.addBtnThemListener(e -> {
			isEditingDB = false;
			handleAction("THEM");
		});

		this.view.addBtnSuaListener(e -> {
			int row = view.getTbDatBan().getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(view, "Chọn 1 dòng để sửa!");
				return;
			}
			isEditingDB = true;
			view.batTatNutDatBan(false, false, false, true);
		});

		this.view.addBtnXoaListener(e -> xoaDatBan());

		this.view.addBtnXoaTrangListener(e -> {
			xoaTrang();
			isEditingDB = false;
			view.batTatNutDatBan(true, true, true, false);
		});

		this.view.addBtnLuuListener(e -> {
			if (isEditingDB) {
				handleAction("SUA");
			} else {
				JOptionPane.showMessageDialog(view, "Chỉ dùng nút Lưu khi đang Sửa Đặt Bàn!");
			}
		});

		this.view.addTfTimKiemListener(e -> timKiemDatBan());
		this.view.addBtnTimListener(e -> timKiemDatBan());

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

	// CHỨC NĂNG: Truy xuất danh sách mã khu vực từ cơ sở dữ liệu.
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

	// CHỨC NĂNG: Truy xuất danh sách các bàn tương ứng với mã khu vực cung cấp.
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

	// CHỨC NĂNG: Gửi tín hiệu hiển thị chi tiết đặt bàn của một dòng trong bảng dữ
	// liệu.
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

	// CHỨC NĂNG: Nạp toàn bộ thông tin đặt bàn lên giao diện hiển thị.
	private void loadDuLieuLenBang() {
		List<DatBan> ds = dao.findAll();
		hienThiDanhSachDatBan(ds);
	}

	// CHỨC NĂNG: Tạo và hiển thị các bản ghi danh sách đặt bàn được truyền vào.
	private void hienThiDanhSachDatBan(List<DatBan> ds) {
		DefaultTableModel tm = view.getTmDatBan();
		tm.setRowCount(0);
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

	// CHỨC NĂNG: Truy xuất các yêu cầu đặt bàn dựa trên kết quả lọc của chuỗi tìm
	// kiếm.
	private void timKiemDatBan() {
		String keyword = view.getTfTimKiem().getText().trim().toLowerCase();
		if (keyword.isEmpty()) {
			loadDuLieuLenBang();
			return;
		}

		List<DatBan> list = dao.findAll();
		DefaultTableModel model = view.getTmDatBan();
		model.setRowCount(0);
		int stt = 1;
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		for (DatBan db : list) {
			String ma = db.getMaDatBan() != null ? db.getMaDatBan().toLowerCase() : "";
			String sdt = db.getSoDienThoai() != null ? db.getSoDienThoai().toLowerCase() : "";
			String ten = db.getTenKhachHang() != null ? db.getTenKhachHang().toLowerCase() : "";

			if (ma.contains(keyword) || sdt.contains(keyword) || ten.contains(keyword)) {
				String chuTrangThai = db.isTrangThai() ? "Đã nhận bàn" : "Chờ nhận bàn";
				String chuThoiGianNhan = db.getThoiGianNhanBan() != null ? db.getThoiGianNhanBan().format(df) : "";
				String maKV = db.getMaKhuVuc() != null ? db.getMaKhuVuc() : "";
				String maBan = db.getMaBan() != null ? db.getMaBan() : "";

				model.addRow(new Object[] { stt++, db.getMaDatBan(), maKV, maBan, db.getTenKhachHang(),
						db.getSoDienThoai(), chuThoiGianNhan, chuTrangThai, "Xem" });
			}
		}
	}

	// CHỨC NĂNG: Nạp chi tiết bản ghi đặt bàn đã chọn lên khu vực biểu mẫu.
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

	// CHỨC NĂNG: Cấu hình lại các thành phần thông tin trên biểu mẫu để loại bỏ các
	// dữ liệu đã nhập.
	private void xoaTrang() {
		view.getTfTenKhach().setText("");
		view.getTfSoDienThoai().setText("");
		if (view.getCbMaKhuVuc().getItemCount() > 0) {
			view.getCbMaKhuVuc().setSelectedIndex(0);
		}
		view.getSpThoiGianNhan().setValue(new Date());
		view.getTbDatBan().clearSelection();
		view.getTfTimKiem().setText("");
		loadDuLieuLenBang();
	}

	// CHỨC NĂNG: Quản lý luồng xử lý và lưu trữ dữ liệu đối với thao tác tạo mới
	// hoặc chỉnh sửa.
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

					isEditingDB = false;
					view.batTatNutDatBan(true, true, true, false);
				} else {
					JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(view, "Lỗi xử lý ngày giờ!");
			ex.printStackTrace();
		}
	}

	// CHỨC NĂNG: Loại bỏ thông tin ghi nhận đặt bàn tương ứng đã chọn khỏi cơ sở dữ
	// liệu.
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