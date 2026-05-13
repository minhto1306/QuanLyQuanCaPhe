package controller;

import java.awt.Window;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import dao.BanDAO;
import dao.KhuVucDAO;
import entity.Ban;
import entity.KhuVuc;
import ui.DlgKhuVucBan;
import ui.DlgThemKhuVuc;

public class KhuVucController {
	private DlgKhuVucBan view;
	private KhuVucDAO khuVucDAO;
	private BanDAO banDAO;

	private boolean isEditingKV = false;

	// CHỨC NĂNG: Khởi tạo điều khiển quản lý khu vực và sơ đồ bàn.
	public KhuVucController(DlgKhuVucBan view) {
		this.view = view;
		this.khuVucDAO = new KhuVucDAO();
		this.banDAO = new BanDAO();

		initEvents();
		loadDataToTableAndComboBox();
	}

	// CHỨC NĂNG: Khởi tạo và liên kết các sự kiện tương tác của người dùng.
	private void initEvents() {
		this.view.addThemKhuVucListener(e -> {
			isEditingKV = false;
			handleAddKhuVuc();
		});

		this.view.addXoaKhuVucListener(e -> handleDeleteKhuVuc());

		this.view.addSuaKhuVucListener(e -> {
			int row = view.getSelectedRow();
			if (row < 0) {
				view.showMessage("Vui lòng chọn khu vực cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
				return;
			}
			isEditingKV = true;
			view.batTatNutKhuVuc(false, false, false, true, false);
		});

		this.view.addXoaTrangKhuVucListener(e -> {
			clearForm();
			isEditingKV = false;
			view.batTatNutKhuVuc(true, true, true, false, true);
		});

		this.view.addLuuKhuVucListener(e -> {
			if (isEditingKV) {
				handleUpdateKhuVuc();
			} else {
				JOptionPane.showMessageDialog(view, "Chỉ dùng nút Lưu khi đang Sửa Khu Vực!");
			}
		});

		this.view.addTimKhuVucListener(e -> handleTimKiemKhuVuc());

		this.view.addTableSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				handleTableClick();
			}
		});

		this.view.addThemKhuVucNhanhListener(e -> handleShowQuickAdd());
		this.view.addTaoTuDongListener(e -> handleTaoTuDong());
		this.view.addKhuVucComboBoxListener(e -> handleFilterBanByKhuVuc());
		this.view.addXoaBanListener(e -> handleDeleteBan());

		this.view.addThoatSoDoListener(e -> {
			this.view.dispose();
			Window parent = SwingUtilities.getWindowAncestor(this.view);
			if (parent instanceof ui.FrmManHinhChinh) {
				List<KhuVuc> listKV = khuVucDAO.findAll();
				List<Ban> listBan = banDAO.findAll();
				Map<String, List<Ban>> mapKhuVucBan = new LinkedHashMap<>();
				for (KhuVuc kv : listKV) {
					List<Ban> banCuaKv = new ArrayList<>();
					for (Ban b : listBan) {
						if (b.getMaKhuVuc() != null && b.getMaKhuVuc().equals(kv.getMaKhuVuc())) {
							banCuaKv.add(b);
						}
					}
					if (!banCuaKv.isEmpty()) {
						banCuaKv.sort((b1, b2) -> {
							int n1 = Integer.parseInt(b1.getTenBan().replaceAll("\\D", "").isEmpty() ? "0"
									: b1.getTenBan().replaceAll("\\D", ""));
							int n2 = Integer.parseInt(b2.getTenBan().replaceAll("\\D", "").isEmpty() ? "0"
									: b2.getTenBan().replaceAll("\\D", ""));
							if (n1 == n2)
								return b1.getTenBan().compareTo(b2.getTenBan());
							return Integer.compare(n1, n2);
						});
						mapKhuVucBan.put(kv.getTenKhuVuc(), banCuaKv);
					}
				}
				((ui.FrmManHinhChinh) parent).hienThiDanhSachBanToanBo(mapKhuVucBan);
			}
		});
	}

	// CHỨC NĂNG: Nạp dữ liệu khu vực từ cơ sở dữ liệu lên bảng và danh sách thả
	// xuống.
	private void loadDataToTableAndComboBox() {
		List<KhuVuc> list = khuVucDAO.findAll();

		DefaultTableModel model = view.getKhuVucTableModel();
		model.setRowCount(0);
		int stt = 1;
		for (KhuVuc kv : list) {
			model.addRow(new Object[] { stt++, kv.getMaKhuVuc(), kv.getTenKhuVuc(), kv.getPhuThu() });
		}
		view.loadDataToComboBoxKhuVuc(list);
	}

	// CHỨC NĂNG: Xử lý tìm kiếm thông tin khu vực dựa trên mã.
	private void handleTimKiemKhuVuc() {
		String keyword = view.getKV_TimInput().toLowerCase();
		if (keyword.isEmpty()) {
			loadDataToTableAndComboBox();
			return;
		}

		List<KhuVuc> list = khuVucDAO.findAll();
		DefaultTableModel model = view.getKhuVucTableModel();
		model.setRowCount(0);
		int stt = 1;

		for (KhuVuc kv : list) {
			if (kv.getMaKhuVuc().toLowerCase().contains(keyword)) {
				model.addRow(new Object[] { stt++, kv.getMaKhuVuc(), kv.getTenKhuVuc(), kv.getPhuThu() });
			}
		}
	}

	// CHỨC NĂNG: Cập nhật thông tin chi tiết khu vực lên form khi chọn một dòng
	// trên bảng.
	private void handleTableClick() {
		int row = view.getSelectedRow();
		if (row >= 0) {
			view.setMaKhuVuc(view.getKhuVucTable().getValueAt(row, 1).toString());
			view.setTenKhuVuc(view.getKhuVucTable().getValueAt(row, 2).toString());
			view.setPhuThu(view.getKhuVucTable().getValueAt(row, 3).toString());
		}
	}

	// CHỨC NĂNG: Xác thực và lưu thông tin khu vực mới vào hệ thống.
	private void handleAddKhuVuc() {
		KhuVuc kv = getKhuVucFromForm(view.getMaKhuVuc(), view.getTenKhuVuc(), view.getPhuThu(), view);
		if (kv == null)
			return;

		if (khuVucDAO.findById(kv.getMaKhuVuc()) != null) {
			view.showMessage("Mã khu vực này đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (khuVucDAO.insert(kv)) {
			view.showMessage("Thêm khu vực thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			loadDataToTableAndComboBox();
			clearForm();
		}
	}

	// CHỨC NĂNG: Xử lý việc loại bỏ khu vực khỏi cơ sở dữ liệu.
	private void handleDeleteKhuVuc() {
		int row = view.getSelectedRow();
		if (row < 0) {
			view.showMessage("Vui lòng chọn khu vực cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String ma = view.getKhuVucTable().getValueAt(row, 1).toString();
		if (JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa khu vực " + ma + "?", "Xác nhận",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			if (khuVucDAO.delete(ma)) {
				view.showMessage("Xóa khu vực thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				loadDataToTableAndComboBox();
				clearForm();
			} else {
				view.showMessage("Không thể xóa khu vực này vì đang chứa bàn bên trong!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// CHỨC NĂNG: Xác thực và cập nhật thông tin chỉnh sửa của khu vực hiện có.
	private void handleUpdateKhuVuc() {
		String maTrenBang = view.getMaKhuVuc();
		KhuVuc kv = getKhuVucFromForm(maTrenBang, view.getTenKhuVuc(), view.getPhuThu(), view);
		if (kv == null)
			return;

		if (khuVucDAO.update(kv)) {
			view.showMessage("Cập nhật khu vực thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			loadDataToTableAndComboBox();
			clearForm();

			isEditingKV = false;
			view.batTatNutKhuVuc(true, true, true, false, true);
		}
	}

	// CHỨC NĂNG: Hiển thị hộp thoại hỗ trợ thao tác thêm khu vực nhanh.
	private void handleShowQuickAdd() {
		DlgThemKhuVuc dlgThemNhanh = new DlgThemKhuVuc(view);

		dlgThemNhanh.addLuuThemMoiListener(e -> {
			KhuVuc kvMoi = getKhuVucFromForm(dlgThemNhanh.getMaKhuVuc(), dlgThemNhanh.getTenKhuVuc(),
					dlgThemNhanh.getPhuThu(), dlgThemNhanh);
			if (kvMoi == null)
				return;

			if (khuVucDAO.findById(kvMoi.getMaKhuVuc()) != null) {
				dlgThemNhanh.showMessage("Mã khu vực này đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (khuVucDAO.insert(kvMoi)) {
				dlgThemNhanh.showMessage("Thêm khu vực thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				dlgThemNhanh.clearForm();
				loadDataToTableAndComboBox();
			} else {
				dlgThemNhanh.showMessage("Lỗi hệ thống khi thêm khu vực!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		});

		dlgThemNhanh.addDongListener(e -> dlgThemNhanh.dispose());
		dlgThemNhanh.setVisible(true);
	}

	// CHỨC NĂNG: Xóa dữ liệu tạm thời trên các trường thông tin khu vực.
	private void clearForm() {
		view.setMaKhuVuc("");
		view.setTenKhuVuc("");
		view.setPhuThu("");
		view.getKhuVucTable().clearSelection();
	}

	// CHỨC NĂNG: Rút xuất và kiểm tra tính hợp lệ của dữ liệu nhập để tạo đối tượng
	// KhuVuc.
	private KhuVuc getKhuVucFromForm(String ma, String ten, String phuThuStr, java.awt.Component parentView) {
		if (ma.isEmpty() || ten.isEmpty()) {
			JOptionPane.showMessageDialog(parentView, "Vui lòng nhập đầy đủ Mã và Tên khu vực!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		if (!ma.matches("^[a-zA-Z0-9_]+$")) {
			JOptionPane.showMessageDialog(parentView, "Mã khu vực không được chứa khoảng trắng hoặc ký tự đặc biệt!",
					"Cảnh báo", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		try {
			double pt = phuThuStr.isEmpty() ? 0 : Double.parseDouble(phuThuStr);
			if (pt < 0) {
				JOptionPane.showMessageDialog(parentView, "Phụ thu không được nhập số âm!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
			return new KhuVuc(ma, ten, pt);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(parentView, "Phụ thu phải là số hợp lệ (>= 0)!", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	// CHỨC NĂNG: Trích xuất giá trị số từ chuỗi định danh để phục vụ việc sắp xếp.
	private int extractNumber(String s) {
		String num = s.replaceAll("\\D", "");
		return num.isEmpty() ? 0 : Integer.parseInt(num);
	}

	// CHỨC NĂNG: Lọc và hiển thị danh sách các bàn tương ứng với khu vực được chọn.
	private void handleFilterBanByKhuVuc() {
		String tenKhuVuc = view.getKhuVucDuocChon();

		List<Ban> listBanToanBo = banDAO.findAll();
		List<KhuVuc> listKhuVucToanBo = khuVucDAO.findAll();

		Map<String, List<Ban>> mapKhuVucBan = new LinkedHashMap<>();

		if (tenKhuVuc == null || tenKhuVuc.equals("--- Chọn khu vực ---")) {
			for (KhuVuc kv : listKhuVucToanBo) {
				List<Ban> banCuaKv = new ArrayList<>();
				for (Ban b : listBanToanBo) {
					if (b != null && b.getMaKhuVuc() != null && b.getMaKhuVuc().equals(kv.getMaKhuVuc())) {
						banCuaKv.add(b);
					}
				}
				if (!banCuaKv.isEmpty()) {
					banCuaKv.sort((b1, b2) -> {
						int n1 = extractNumber(b1.getTenBan());
						int n2 = extractNumber(b2.getTenBan());
						if (n1 == n2)
							return b1.getTenBan().compareTo(b2.getTenBan());
						return Integer.compare(n1, n2);
					});
					mapKhuVucBan.put(kv.getTenKhuVuc(), banCuaKv);
				}
			}
		} else {
			KhuVuc kvSelected = null;
			for (KhuVuc kv : listKhuVucToanBo) {
				if (kv.getTenKhuVuc().equals(tenKhuVuc)) {
					kvSelected = kv;
					break;
				}
			}
			if (kvSelected != null) {
				List<Ban> banCuaKv = new ArrayList<>();
				for (Ban b : listBanToanBo) {
					if (b != null && b.getMaKhuVuc() != null && b.getMaKhuVuc().equals(kvSelected.getMaKhuVuc())) {
						banCuaKv.add(b);
					}
				}
				banCuaKv.sort((b1, b2) -> {
					int n1 = extractNumber(b1.getTenBan());
					int n2 = extractNumber(b2.getTenBan());
					if (n1 == n2)
						return b1.getTenBan().compareTo(b2.getTenBan());
					return Integer.compare(n1, n2);
				});
				mapKhuVucBan.put(kvSelected.getTenKhuVuc(), banCuaKv);
			}
		}

		view.hienThiDanhSachBanToanBo(mapKhuVucBan);
	}

	// CHỨC NĂNG: Khởi tạo hàng loạt các bàn mới dựa trên các tham số cấu hình tự
	// động.
	private void handleTaoTuDong() {
		String tenKhuVuc = view.getKhuVucDuocChon();

		if (tenKhuVuc == null || tenKhuVuc.equals("--- Chọn khu vực ---")) {
			view.showMessage("Vui lòng chọn một khu vực trong danh sách trước khi tạo bàn!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String maKhuVuc = "";
		for (KhuVuc kv : khuVucDAO.findAll()) {
			if (kv.getTenKhuVuc().equals(tenKhuVuc)) {
				maKhuVuc = kv.getMaKhuVuc();
				break;
			}
		}

		try {
			int tuBan = Integer.parseInt(view.getTuSo());
			int denBan = Integer.parseInt(view.getDenSo());

			if (tuBan > denBan || tuBan <= 0) {
				view.showMessage("Số bàn không hợp lệ! (Từ số phải > 0 và <= Đến số)", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			int xacNhan = JOptionPane.showConfirmDialog(null,
					"Bạn có chắc muốn tạo bàn từ số " + tuBan + " đến số " + denBan + " cho " + tenKhuVuc + " không?",
					"Xác nhận tạo tự động", JOptionPane.YES_NO_OPTION);

			if (xacNhan == JOptionPane.YES_OPTION) {
				List<Ban> dsBanHienTai = banDAO.findAll();
				int soBanTaoMoi = 0;
				int soBanBoQua = 0;

				for (int i = tuBan; i <= denBan; i++) {
					String maBan = maKhuVuc + "_B" + i;
					boolean daTonTai = false;

					for (Ban b : dsBanHienTai) {
						if (b.getMaBan().equals(maBan)) {
							daTonTai = true;
							break;
						}
					}

					if (!daTonTai) {
						String tenBan = "Bàn " + i;
						Ban banMoi = new Ban(maBan, maKhuVuc, tenBan, "Trống");
						if (banDAO.insert(banMoi)) {
							soBanTaoMoi++;
						}
					} else {
						soBanBoQua++;
					}
				}

				String thongBao = "Tạo tự động hoàn tất!\n- Đã thêm mới: " + soBanTaoMoi + " bàn.";
				if (soBanBoQua > 0) {
					thongBao += "\n- Đã bỏ qua: " + soBanBoQua + " bàn (do đã tồn tại trước đó).";
				}
				view.showMessage(thongBao, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				handleFilterBanByKhuVuc();
			}

		} catch (NumberFormatException ex) {
			view.showMessage("Vui lòng nhập số nguyên hợp lệ vào ô 'Từ số' và 'Đến số'!", "Lỗi nhập liệu",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// CHỨC NĂNG: Xử lý thao tác loại bỏ các bàn được chọn khỏi hệ thống.
	private void handleDeleteBan() {
		List<String> listMaBan = view.getDanhSachMaBanDuocChon();

		if (listMaBan.isEmpty()) {
			view.showMessage("Vui lòng chọn ít nhất một bàn trên sơ đồ để xóa!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int xacNhan = JOptionPane.showConfirmDialog(view,
				"Bạn có chắc chắn muốn xóa " + listMaBan.size() + " bàn đã chọn không?", "Xác nhận xóa",
				JOptionPane.YES_NO_OPTION);

		if (xacNhan == JOptionPane.YES_OPTION) {
			int count = 0;
			for (String maBan : listMaBan) {
				if (banDAO.delete(maBan)) {
					count++;
				}
			}
			view.showMessage("Đã xóa thành công " + count + " bàn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			handleFilterBanByKhuVuc();
		}
	}
}