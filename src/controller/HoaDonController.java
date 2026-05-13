package controller;

import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import dao.BanDAO;
import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhuVucDAO;
import dao.NhanVienDAO;
import dao.SanPhamDAO;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhuVuc;
import entity.NhanVien;
import entity.SanPham;
import ui.DlgHoaDon;
import ui.DlgInHoaDon;

public class HoaDonController {

	private DlgHoaDon view;
	private HoaDonDAO hdDAO;
	private ChiTietHoaDonDAO cthdDAO;
	private BanDAO banDAO;
	private NhanVienDAO nvDAO;
	private SanPhamDAO spDAO;
	private KhuVucDAO kvDAO;

	private DecimalFormat df = new DecimalFormat("#,### VNĐ");
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	// CHỨC NĂNG: Khởi tạo điều khiển quản lý và tra cứu thông tin hóa đơn.
	public HoaDonController(DlgHoaDon view) {
		this.view = view;
		this.hdDAO = new HoaDonDAO();
		this.cthdDAO = new ChiTietHoaDonDAO();
		this.banDAO = new BanDAO();
		this.nvDAO = new NhanVienDAO();
		this.spDAO = new SanPhamDAO();
		this.kvDAO = new KhuVucDAO();

		view.getTfGiamGia().setEditable(false);
		view.getCbThue().setEnabled(false);

		loadFilterData();
		dangKySuKien();
		loadDanhSachHoaDon("Tất cả");
	}

	// CHỨC NĂNG: Nạp cấu hình lọc cơ bản để phân loại hóa đơn trên giao diện tra
	// cứu.
	private void loadFilterData() {
		view.getCbTimKiemHD().addItem("Tất cả");

		Set<String> filterItems = new HashSet<>();
		List<NhanVien> listNV = nvDAO.findAll();
		if (listNV != null) {
			for (NhanVien nv : listNV) {
				filterItems.add("Nhân viên: " + nv.getHoTenNhanVien());
			}
		}

		List<KhuVuc> listKV = kvDAO.findAll();
		if (listKV != null) {
			for (KhuVuc kv : listKV) {
				filterItems.add("Khu vực: " + kv.getTenKhuVuc());
			}
		}

		for (String item : filterItems) {
			view.getCbTimKiemHD().addItem(item);
		}
	}

	// CHỨC NĂNG: Liên kết các sự kiện tương tác để phản hồi thao tác của người
	// dùng.
	private void dangKySuKien() {
		view.getCbTimKiemHD().addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				loadDanhSachHoaDon(e.getItem().toString());
			}
		});

		view.addTbHoaDonMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = view.getTbHoaDon().getSelectedRow();
				if (row >= 0) {
					String maHD = view.getTmHoaDon().getValueAt(row, 0).toString();
					loadChiTietHoaDon(maHD);
				}
			}
		});

		view.addBtnInHoaDonListener(e -> inLaiHoaDon());
	}

	// CHỨC NĂNG: Truy vấn và kết xuất danh sách hóa đơn theo tiêu chí được chỉ
	// định.
	private void loadDanhSachHoaDon(String dieuKienLoc) {
		DefaultTableModel tm = view.getTmHoaDon();
		tm.setRowCount(0);

		List<HoaDon> dsHoaDon = hdDAO.findAll();
		if (dsHoaDon == null)
			return;

		for (HoaDon hd : dsHoaDon) {
			Ban ban = banDAO.findById(hd.getMaBan());
			String tenBan = (ban != null) ? ban.getTenBan() : hd.getMaBan();

			String tenKV = "Không rõ";
			if (ban != null) {
				KhuVuc kv = kvDAO.findById(ban.getMaKhuVuc());
				if (kv != null)
					tenKV = kv.getTenKhuVuc();
			}

			NhanVien nv = nvDAO.findById(hd.getMaNhanVien());
			String tenNV = (nv != null) ? nv.getHoTenNhanVien() : hd.getMaNhanVien();

			boolean hopLe = false;
			if (dieuKienLoc.equals("Tất cả")) {
				hopLe = true;
			} else if (dieuKienLoc.startsWith("Nhân viên: ") && dieuKienLoc.replace("Nhân viên: ", "").equals(tenNV)) {
				hopLe = true;
			} else if (dieuKienLoc.startsWith("Khu vực: ") && dieuKienLoc.replace("Khu vực: ", "").equals(tenKV)) {
				hopLe = true;
			}

			if (hopLe) {
				String thoiGian = hd.getThoiGianLap().format(dtf);
				String thanhTien = df.format(hd.getThanhTien());
				String trangThai = hd.isTrangThai() ? "Đã thanh toán" : "Hủy";

				tm.addRow(new Object[] { hd.getMaHoaDon(), tenKV, tenBan, tenNV, thoiGian, thanhTien, trangThai });
			}
		}
	}

	// CHỨC NĂNG: Truy xuất các món đã bán tương ứng với mã hóa đơn và hiển thị chi
	// tiết.
	private void loadChiTietHoaDon(String maHD) {
		DefaultTableModel tm = view.getTmChiTiet();
		tm.setRowCount(0);

		HoaDon hd = hdDAO.findById(maHD);
		if (hd != null) {
			view.getTfGiamGia().setText(String.format("%,.0f", hd.getGiamGia()));

			int phanTramThue = 0;
			if (hd.getTongTien() > 0) {
				phanTramThue = (int) Math.round((hd.getThueVAT() / hd.getTongTien()) * 100);
			}

			boolean foundThue = false;
			for (int i = 0; i < view.getCbThue().getItemCount(); i++) {
				if (view.getCbThue().getItemAt(i).equals(String.valueOf(phanTramThue))) {
					view.getCbThue().setSelectedIndex(i);
					foundThue = true;
					break;
				}
			}
			if (!foundThue && view.getCbThue().getItemCount() > 0)
				view.getCbThue().setSelectedIndex(0);
		}

		int stt = 1;
		List<ChiTietHoaDon> dsChiTietToanBo = cthdDAO.findAll();

		for (ChiTietHoaDon ct : dsChiTietToanBo) {
			if (ct.getMaHoaDon().equals(maHD)) {
				SanPham sp = spDAO.findById(ct.getMaSanPham());
				String tenMon = (sp != null) ? sp.getTenSanPham() : ct.getMaSanPham();

				double thanhTienMon = ct.getSoLuong() * ct.getGiaBan();

				tm.addRow(new Object[] { stt++, tenMon, ct.getSoLuong(), String.format("%,.0f", ct.getGiaBan()),
						String.format("%,.0f", thanhTienMon) });
			}
		}
	}

	// CHỨC NĂNG: Kích hoạt tiến trình tái xuất bản hóa đơn thông qua hộp thoại hiển
	// thị.
	private void inLaiHoaDon() {
		int row = view.getTbHoaDon().getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn một hóa đơn bên trái để in lại!");
			return;
		}

		String maHD = view.getTmHoaDon().getValueAt(row, 0).toString();
		HoaDon hd = hdDAO.findById(maHD);
		if (hd == null)
			return;

		String tenBan = view.getTmHoaDon().getValueAt(row, 2).toString();
		String tenNV = view.getTmHoaDon().getValueAt(row, 3).toString();

		List<Object[]> dsMon = new ArrayList<>();
		for (int i = 0; i < view.getTmChiTiet().getRowCount(); i++) {
			String tenMon = view.getTmChiTiet().getValueAt(i, 1).toString();
			String soLuong = view.getTmChiTiet().getValueAt(i, 2).toString();
			String giaBanStr = view.getTmChiTiet().getValueAt(i, 3).toString().replaceAll("[^\\d]", "");
			String thanhTienStr = view.getTmChiTiet().getValueAt(i, 4).toString().replaceAll("[^\\d]", "");

			dsMon.add(new Object[] { tenMon, soLuong, giaBanStr, thanhTienStr });
		}

		JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
		DlgInHoaDon dlgIn = new DlgInHoaDon(parentFrame, hd, tenNV, tenBan, dsMon);
		dlgIn.setVisible(true);
	}
}