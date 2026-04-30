package controller;

import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import dao.BanDAO;
import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.NhanVienDAO;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.SanPham;
import ui.DlgInHoaDon;
import ui.FrmManHinhChinh;

public class ThanhToanController {

	private FrmManHinhChinh view;
	private Ban banHienTai;

	private Map<String, HoaDonTam> danhSachOrder = new HashMap<>();

	private HoaDonDAO hdDAO = new HoaDonDAO();
	private ChiTietHoaDonDAO cthdDAO = new ChiTietHoaDonDAO();
	private BanDAO banDAO = new BanDAO();
	private NhanVienDAO nvDAO = new NhanVienDAO();
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	public ThanhToanController(FrmManHinhChinh view) {
		this.view = view;
		khoiTaoSuKien();
	}

	private void khoiTaoSuKien() {
		view.getCbBoxThue().addActionListener(e -> tinhToanLaiTien());
		view.getTfGiamGia().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				tinhToanLaiTien();
			}
		});

		view.getBtnThanhToan().addActionListener(e -> xuLyThanhToan());
	}

	public void xuLyChonBan(Ban ban, String tenKhuVuc) {
		this.banHienTai = ban;
		view.getLbValueKhuVuc().setText(tenKhuVuc + " - " + ban.getTenBan());

		HoaDonTam order = danhSachOrder.get(ban.getMaBan());
		if (order == null) {
			order = new HoaDonTam();
			order.gioVao = LocalDateTime.now();
			danhSachOrder.put(ban.getMaBan(), order);

			ban.setTrangThai("Có khách");
			banDAO.update(ban);
			view.loadDuLieuSoDoBanMain();
		}

		view.getLbValueGioVao().setText(order.gioVao.format(dtf));
		hienThiOrderLenBang(order);
		view.getTabbedPane().setSelectedIndex(1);
	}

	public void xuLyChonMon(SanPham sp) {
		if (banHienTai == null) {
			JOptionPane.showMessageDialog(view, "Phải chọn Bàn bên Sơ đồ trước khi gọi món!");
			view.getTabbedPane().setSelectedIndex(0);
			return;
		}

		HoaDonTam order = danhSachOrder.get(banHienTai.getMaBan());

		boolean daCoMonNay = false;
		for (ChiTietHoaDon ct : order.danhSachMon) {
			if (ct.getMaSanPham().equals(sp.getMaSanPham())) {
				ct.setSoLuong(ct.getSoLuong() + 1);
				daCoMonNay = true;
				break;
			}
		}

		if (!daCoMonNay) {
			ChiTietHoaDon ctMoi = new ChiTietHoaDon("", sp.getMaSanPham(), 1, sp.getGiaBan(), "");
			order.danhSachMon.add(ctMoi);
		}

		hienThiOrderLenBang(order);
	}

	private void hienThiOrderLenBang(HoaDonTam order) {
		DefaultTableModel tm = view.getTableModel();
		tm.setRowCount(0);
		int stt = 1;
		double tongTienChuaThue = 0;

		for (ChiTietHoaDon ct : order.danhSachMon) {
			double thanhTienMon = ct.getSoLuong() * ct.getGiaBan();
			tongTienChuaThue += thanhTienMon;

			tm.addRow(new Object[] { stt++, ct.getMaSanPham(), ct.getSoLuong(), ct.getGiaBan(), thanhTienMon });
		}

		order.tongTienBanDau = tongTienChuaThue;
		tinhToanLaiTien();
	}

	private void tinhToanLaiTien() {
		if (banHienTai == null)
			return;
		HoaDonTam order = danhSachOrder.get(banHienTai.getMaBan());
		if (order == null)
			return;

		double giamGia = 0;
		try {
			String txtGiamGia = view.getTfGiamGia().getText().trim();
			if (!txtGiamGia.isEmpty()) {
				giamGia = Double.parseDouble(txtGiamGia);
			}
		} catch (Exception ex) {
		}

		int ptThue = (Integer) view.getCbBoxThue().getSelectedItem();
		double tienThue = order.tongTienBanDau * ptThue / 100.0;

		double tienPhaiTra = order.tongTienBanDau + tienThue - giamGia;
		if (tienPhaiTra < 0)
			tienPhaiTra = 0;

		view.getLbValueTienPhaiTra().setText(String.format("%,.0f VNĐ", order.tongTienBanDau));
		view.getLbTongTien().setText(String.format("%,.0f VNĐ", tienPhaiTra));

		order.tienThue = tienThue;
		order.giamGia = giamGia;
		order.tienPhaiTra = tienPhaiTra;
	}

	private void xuLyThanhToan() {
		if (banHienTai == null)
			return;
		HoaDonTam order = danhSachOrder.get(banHienTai.getMaBan());
		if (order == null || order.danhSachMon.isEmpty())
			return;

		// 🛠️ LẤY TÊN NHÂN VIÊN ĐANG TRỰC
		// Giả sử Qẹoooo lưu maNhanVien trong FrmManHinhChinh sau khi Login
		// Nếu chưa có, Qẹoooo tạo hàm public String getMaNhanVienDangNhap() bên
		// FrmManHinhChinh nghen
		String maNV = "NV01"; // Mặc định nếu không tìm thấy
		String tenNVReal = "Nhân viên trực";

		// Tìm thông tin đầy đủ từ Database
		entity.NhanVien nvEntity = nvDAO.findById(maNV);
		if (nvEntity != null) {
			tenNVReal = nvEntity.getHoTenNhanVien();
		}

		// 1. LƯU DATABASE
		String maHD = "HD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
		HoaDon hd = new HoaDon(maHD, maNV, banHienTai.getMaBan(), LocalDateTime.now(), order.tongTienBanDau,
				order.tienThue, order.giamGia, order.tienPhaiTra, "Tiền mặt", true);

		if (hdDAO.insert(hd)) {
			for (ChiTietHoaDon ct : order.danhSachMon) {
				ct.setMaHoaDon(maHD);
				cthdDAO.insert(ct);
			}

			banHienTai.setTrangThai("Trống");
			banDAO.update(banHienTai);
			danhSachOrder.remove(banHienTai.getMaBan());

			// 2. HỎI XEM HÓA ĐƠN
			int chon = JOptionPane.showConfirmDialog(view,
					"Thanh toán thành công! Bạn có muốn xem lại hóa đơn vừa lưu không?", "Xác nhận",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (chon == JOptionPane.YES_OPTION) {
				List<Object[]> dsMon = new ArrayList<>();
				DefaultTableModel tm = view.getTableModel();
				for (int i = 0; i < tm.getRowCount(); i++) {
					dsMon.add(new Object[] { tm.getValueAt(i, 1), tm.getValueAt(i, 2), tm.getValueAt(i, 3),
							tm.getValueAt(i, 4) });
				}

				JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
				// 👇 QUĂNG tenNVReal VỪA TÌM ĐƯỢC VÔ ĐÂY NÈ QẸOOOO
				DlgInHoaDon dlgIn = new DlgInHoaDon(parentFrame, hd, tenNVReal, banHienTai.getTenBan(), dsMon);
				dlgIn.setVisible(true);
			}

			quayVeTrangChu();
		} else {
			JOptionPane.showMessageDialog(view, "Lỗi lưu Database!");
		}
	}

	private void quayVeTrangChu() {
		// Xóa trắng bảng danh sách món
		view.getTableModel().setRowCount(0);

		// Xóa các ô hiển thị tiền bạc, giờ giấc
		view.getLbValueKhuVuc().setText("---");
		view.getLbValueGioVao().setText("--:--");
		view.getLbValueTienPhaiTra().setText("0 VNĐ");
		view.getLbTongTien().setText("0 VNĐ");
		view.getTfGiamGia().setText("");
		view.getCbBoxThue().setSelectedIndex(0);

		// Thả cái bàn đang giữ ra
		banHienTai = null;

		// Nhảy về tab Sơ đồ bàn (Tab 0) và Load lại màu cho các nút Bàn
		view.getTabbedPane().setSelectedIndex(0);
		view.loadDuLieuSoDoBanMain();
	}

	public String getGioVaoCuaBan(String maBan) {
		HoaDonTam order = danhSachOrder.get(maBan);
		if (order != null && order.gioVao != null) {
			return order.gioVao.format(DateTimeFormatter.ofPattern("HH:mm"));
		}
		return null;
	}

	private class HoaDonTam {
		LocalDateTime gioVao;
		List<ChiTietHoaDon> danhSachMon = new ArrayList<>();
		double tongTienBanDau = 0;
		double tienThue = 0;
		double giamGia = 0;
		double tienPhaiTra = 0;
	}
}