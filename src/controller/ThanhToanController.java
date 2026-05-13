package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import dao.BanDAO;
import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhuVucDAO;
import dao.NhanVienDAO;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhuVuc;
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
	private dao.SanPhamDAO spDAO = new dao.SanPhamDAO();

	private KhuVucDAO kvDAO = new KhuVucDAO();
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	// CHỨC NĂNG: Khởi tạo điều khiển quá trình thanh toán trên màn hình chính.
	public ThanhToanController(FrmManHinhChinh view) {
		this.view = view;
		khoiTaoSuKien();
	}

	// CHỨC NĂNG: Gắn các sự kiện lắng nghe để cập nhật tính toán trên giao diện
	// thanh toán.
	private void khoiTaoSuKien() {
		view.getCbBoxThue().addActionListener(e -> tinhToanLaiTien());
		view.getTfGiamGia().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				tinhToanLaiTien();
			}
		});

		((AbstractDocument) view.getTfGiamGia().getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
					throws BadLocationException {
				if (string == null)
					return;
				if (string.matches("\\d+")) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				if (text == null)
					return;
				if (text.matches("\\d+")) {
					super.replace(fb, offset, length, text, attrs);
				}
			}
		});

		view.getBtnThanhToan().addActionListener(e -> xuLyThanhToan());
	}

	// CHỨC NĂNG: Tải và hiển thị danh sách các bàn theo từng khu vực lên giao diện.
	public void lamMoiSoDoBanMain() {
		List<KhuVuc> listKV = kvDAO.findAll();
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
		view.hienThiDanhSachBanToanBo(mapKhuVucBan);
	}

	// CHỨC NĂNG: Xử lý thông tin hiển thị và trạng thái khi một bàn được chọn.
	public void xuLyChonBan(Ban ban, String tenKhuVuc) {
		this.banHienTai = ban;
		view.getLbValueKhuVuc().setText(tenKhuVuc + " - " + ban.getTenBan());

		HoaDonTam order = danhSachOrder.get(ban.getMaBan());
		if (order == null) {
			order = new HoaDonTam();
			order.gioVao = LocalDateTime.now();

			List<KhuVuc> dsKhuVuc = kvDAO.findAll();
			for (KhuVuc kv : dsKhuVuc) {
				if (kv.getMaKhuVuc() != null && kv.getMaKhuVuc().equals(ban.getMaKhuVuc())) {
					order.phuThu = kv.getPhuThu();
					break;
				}
			}

			danhSachOrder.put(ban.getMaBan(), order);

			ban.setTrangThai("Có khách");
			banDAO.update(ban);
			lamMoiSoDoBanMain();
		}

		view.getLbValueGioVao().setText(order.gioVao.format(dtf));
		hienThiOrderLenBang(order);
		view.getTabbedPane().setSelectedIndex(1);
	}

	// CHỨC NĂNG: Xử lý thêm sản phẩm vào danh sách món của bàn đang chọn.
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

	// CHỨC NĂNG: Cập nhật chi tiết danh sách món và số tiền lên bảng.
	private void hienThiOrderLenBang(HoaDonTam order) {
		DefaultTableModel tm = view.getTableModel();
		tm.setRowCount(0);
		int stt = 1;
		double tongTienChuaThue = 0;

		for (ChiTietHoaDon ct : order.danhSachMon) {
			double thanhTienMon = ct.getSoLuong() * ct.getGiaBan();
			tongTienChuaThue += thanhTienMon;

			entity.SanPham sp = spDAO.findById(ct.getMaSanPham());
			String tenMonHienThi = (sp != null) ? sp.getTenSanPham() : ct.getMaSanPham();

			tm.addRow(new Object[] { stt++, tenMonHienThi, ct.getSoLuong(), ct.getGiaBan(), thanhTienMon });
		}

		order.tongTienBanDau = tongTienChuaThue;
		tinhToanLaiTien();
	}

	// CHỨC NĂNG: Cập nhật lại tổng số tiền cần thanh toán.
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
		double tienThue = (order.tongTienBanDau + order.phuThu) * ptThue / 100.0;

		double tienPhaiTra = order.tongTienBanDau + order.phuThu + tienThue - giamGia;
		if (tienPhaiTra < 0)
			tienPhaiTra = 0;

		view.getLbValueTienPhaiTra().setText(String.format("%,.0f VNĐ", order.phuThu));
		view.getLbTongTien().setText(String.format("%,.0f VNĐ", tienPhaiTra));

		order.tienThue = tienThue;
		order.giamGia = giamGia;
		order.tienPhaiTra = tienPhaiTra;
	}

	// CHỨC NĂNG: Xác nhận thao tác thanh toán, tạo hóa đơn và xử lý cập nhật trạng
	// thái bàn.
	private void xuLyThanhToan() {
		if (banHienTai == null)
			return;
		HoaDonTam order = danhSachOrder.get(banHienTai.getMaBan());
		if (order == null || order.danhSachMon.isEmpty())
			return;

		String maNV = "NV01";
		String tenNVReal = "Nhân viên trực";

		entity.NhanVien nvEntity = nvDAO.findById(maNV);
		if (nvEntity != null) {
			tenNVReal = nvEntity.getHoTenNhanVien();
		}

		String maHD = "HD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
		HoaDon hd = new HoaDon(maHD, maNV, banHienTai.getMaBan(), LocalDateTime.now(),
				order.tongTienBanDau + order.phuThu, order.tienThue, order.giamGia, order.tienPhaiTra, false);

		if (hdDAO.insert(hd)) {
			for (ChiTietHoaDon ct : order.danhSachMon) {
				ct.setMaHoaDon(maHD);
				cthdDAO.insert(ct);
			}

			banHienTai.setTrangThai("Trống");
			banDAO.update(banHienTai);
			danhSachOrder.remove(banHienTai.getMaBan());

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
				DlgInHoaDon dlgIn = new DlgInHoaDon(parentFrame, hd, tenNVReal, banHienTai.getTenBan(), dsMon);
				dlgIn.setVisible(true);
			}

			quayVeTrangChu();
		} else {
			JOptionPane.showMessageDialog(view, "Lỗi lưu Database!");
		}
	}

	// CHỨC NĂNG: Khôi phục lại trạng thái ban đầu sau khi hoàn tất giao dịch.
	private void quayVeTrangChu() {
		view.getTableModel().setRowCount(0);
		view.getLbValueKhuVuc().setText("---");
		view.getLbValueGioVao().setText("--:--");
		view.getLbValueTienPhaiTra().setText("0 VNĐ");
		view.getLbTongTien().setText("0 VNĐ");
		view.getTfGiamGia().setText("");
		view.getCbBoxThue().setSelectedIndex(0);

		banHienTai = null;
		view.getTabbedPane().setSelectedIndex(0);

		lamMoiSoDoBanMain();
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
		double phuThu = 0;
		double tienThue = 0;
		double giamGia = 0;
		double tienPhaiTra = 0;
	}
}