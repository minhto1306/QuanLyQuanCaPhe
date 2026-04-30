package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.ThanhToanController;
import dao.BanDAO;
import dao.KhuVucDAO;
import dao.NhanVienDAO;
import entity.Ban;
import entity.KhuVuc;
import entity.NhanVien;
import entity.SanPham;

public class FrmManHinhChinh extends JFrame {

	private static final long serialVersionUID = 1L;

	public JPanel pnDanhSachBan, pnDanhSachMon;
	private JLabel lbLogo, lbTitleUser, lbValueUSer, lbTitleKhuVuc, lbValueKhuVuc, lbTitleGioVao, lbValueGioVao,
			lbTitleTienPhaiTra, lbValueTienPhaiTra, lbGiamGia, lbThue, lbTongTien, lbTitleTongTien;
	private JButton btnDangXuat, btnThanhToan, btnXemBangGia, btnQLNhanVien, btnQLSoDo, btnQLHangHoa, btnQLCaLam,
			btnQLDatBan, btnHoaDon;
	private JTextField tfGiamGia;
	private JComboBox<Integer> cbBoxThue;
	private DefaultTableModel tableModel;
	private JTabbedPane tabbedPane;

	private ThanhToanController thanhToanController;

	// Hệ màu chuẩn Retro tông đất nung
	private final Color MAU_NEN_CHINH = new Color(242, 233, 216);
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47);
	private final Color MAU_NAU_NUT = new Color(110, 75, 59);
	private final Color MAU_HEADER = new Color(209, 185, 161);

	// Khai báo sẵn biến chứa chân kinh Bungee
	private Font fontBungeeBase;

	public FrmManHinhChinh(String tenNhanVien) {
		this.setTitle("Hệ thống quản lý quán cà phê - Retro Version");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int targetWidth = (int) (screenSize.getWidth() * 0.9);
		int targetHeight = (int) (screenSize.getHeight() * 0.9);
		this.setSize(new Dimension(targetWidth, targetHeight));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		java.net.URL urllogo = getClass().getResource("/images/logo2.0.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		// NẠP TÂM PHÁP FONT CHỮ TRƯỚC KHI KHỞI TẠO GIAO DIỆN
		loadCustomFont();

		khoiTaoGiaoDien();

		NhanVienDAO nvDAO = new NhanVienDAO();
		NhanVien nv = nvDAO.timNhanVienTheoTenDangNhap(tenNhanVien);
		if (nv != null) {
			lbValueUSer.setText(nv.getHoTenNhanVien());
		} else {
			lbValueUSer.setText(tenNhanVien != null ? tenNhanVien : "Admin");
		}

		this.thanhToanController = new ThanhToanController(this);

		loadDuLieuSoDoBanMain();
		loadDuLieuHangHoaMain();
	}

	// Tà thuật hút font chữ từ file .ttf
	private void loadCustomFont() {
		try {
			InputStream is = getClass().getResourceAsStream("/font/Bungee-Regular.ttf");
			if (is != null) {
				fontBungeeBase = Font.createFont(Font.TRUETYPE_FONT, is);
			} else {
				System.err.println("Ây da! Không tìm thấy Bungee-Regular.ttf, chắc đường dẫn sai rồi!");
				fontBungeeBase = new Font("Impact", Font.PLAIN, 24);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fontBungeeBase = new Font("Impact", Font.PLAIN, 24); // Rớt đài thì xài font hệ thống
		}
	}

	public void khoiTaoGiaoDien() {
		JPanel pnMainLayout = new JPanel(new BorderLayout());
		pnMainLayout.setBackground(MAU_NAU_VIEN);
		this.setContentPane(pnMainLayout);

		// Vận công ép size font từ bản gốc
		Font fontLabel = fontBungeeBase.deriveFont(Font.PLAIN, 18f);
		Font fontButton = fontBungeeBase.deriveFont(Font.PLAIN, 24f);
		Font fontTitleLg = fontBungeeBase.deriveFont(Font.PLAIN, 28f);
		Font fontThanhToanBtn = fontBungeeBase.deriveFont(Font.PLAIN, 32f);

		Border thickBorder = BorderFactory.createLineBorder(MAU_NAU_VIEN, 3);

		// =================== TẢ BIÊN: SIDEBAR ===================
		JPanel pnSidebar = new JPanel(new BorderLayout(0, 10));
		pnSidebar.setBackground(MAU_NAU_VIEN);
		pnSidebar.setPreferredSize(new Dimension(250, 0));
		pnSidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel pnLogoArea = new JPanel(new BorderLayout());
		pnLogoArea.setBackground(MAU_NAU_VIEN);
		pnLogoArea.setPreferredSize(new Dimension(230, 200));

		lbLogo = new JLabel("", SwingConstants.CENTER);
		java.net.URL imageUrl = getClass().getResource("/images/logo2.0.png");
		if (imageUrl != null) {
			lbLogo.setIcon(
					new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
		} else {
			lbLogo.setText("[LOGO TRỐNG]");
			lbLogo.setForeground(Color.WHITE);
			lbLogo.setFont(fontLabel);
		}
		pnLogoArea.add(lbLogo, BorderLayout.CENTER);
		pnSidebar.add(pnLogoArea, BorderLayout.NORTH);

		// Khu vực các Nút chức năng
		JPanel pnButtonsContainer = new JPanel(new GridLayout(7, 1, 0, 8));
		pnButtonsContainer.setBackground(MAU_NAU_VIEN);

		btnHoaDon = new JButton("HÓA ĐƠN");
		btnXemBangGia = new JButton("BẢNG GIÁ");
		btnQLDatBan = new JButton("ĐẶT BÀN");
		btnQLNhanVien = new JButton("NHÂN VIÊN");
		btnQLSoDo = new JButton("SƠ ĐỒ / BÀN");
		btnQLHangHoa = new JButton("SẢN PHẨM");
		btnQLCaLam = new JButton("LỊCH LÀM");

		JButton[] arrBtns = { btnHoaDon, btnXemBangGia, btnQLDatBan, btnQLNhanVien, btnQLSoDo, btnQLHangHoa,
				btnQLCaLam };
		for (JButton btn : arrBtns) {
			btn.setFont(fontButton); // Áp dụng Bungee
			btn.setBackground(MAU_NAU_NUT);
			btn.setForeground(Color.WHITE);
			btn.setFocusPainted(false);
			btn.setHorizontalAlignment(SwingConstants.LEFT);

			// --- YÊU CẦU 1: Viền nút to chà bá lửa ---
			btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NEN_CHINH, 6), // Ép lên
																												// hẳn 6
																												// pixel
					BorderFactory.createEmptyBorder(0, 15, 0, 0)));
			pnButtonsContainer.add(btn);
		}

		pnSidebar.add(pnButtonsContainer, BorderLayout.CENTER);
		pnMainLayout.add(pnSidebar, BorderLayout.WEST);

		// =================== KHU VỰC BÊN PHẢI ===================
		JPanel pnRightContainer = new JPanel(new BorderLayout(10, 10));
		pnRightContainer.setBackground(MAU_NAU_VIEN);
		pnRightContainer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
		pnMainLayout.add(pnRightContainer, BorderLayout.CENTER);

		// 1. TOP HEADER
		JPanel pnTopHeader = new JPanel(new BorderLayout());
		pnTopHeader.setBackground(MAU_HEADER);
		pnTopHeader.setBorder(thickBorder);
		pnTopHeader.setPreferredSize(new Dimension(0, 90));

		JPanel pnInfoTruongPhai = new JPanel(new GridLayout(3, 1));
		pnInfoTruongPhai.setOpaque(false);
		pnInfoTruongPhai.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));

		JLabel lbSrc = new JLabel("SRC: NHÓM 3");
		JLabel lbLh = new JLabel("LH: 0349445392");
		JLabel lbDc = new JLabel("ĐC: 611 THỐNG NHẤT, P16, Q.GÒ VẤP, TP.HCM");

		JLabel[] arrLbs = { lbSrc, lbLh, lbDc };
		for (JLabel lb : arrLbs) {
			lb.setFont(fontLabel); // Bungee cỡ nhỏ
			lb.setForeground(MAU_NAU_VIEN);
			pnInfoTruongPhai.add(lb);
		}
		pnTopHeader.add(pnInfoTruongPhai, BorderLayout.WEST);

		JPanel pnĐX = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
		pnĐX.setOpaque(false);
		btnDangXuat = new JButton("ĐĂNG XUẤT");
		btnDangXuat.setFont(fontButton);
		btnDangXuat.setPreferredSize(new Dimension(160, 50));
		btnDangXuat.setBackground(MAU_NAU_NUT);
		btnDangXuat.setForeground(Color.WHITE);
		btnDangXuat.setFocusPainted(false);
		btnDangXuat.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
		pnĐX.add(btnDangXuat);

		pnTopHeader.add(pnĐX, BorderLayout.EAST);
		pnRightContainer.add(pnTopHeader, BorderLayout.NORTH);

		// 2. MAIN CONTENT AREA
		JPanel pnCenterArea = new JPanel(new BorderLayout(10, 0));
		pnCenterArea.setBackground(MAU_NAU_VIEN);
		pnRightContainer.add(pnCenterArea, BorderLayout.CENTER);

		// --- KHU VỰC TRUNG TÂM (TABS) ---
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(fontButton); // Tab xài Bungee luôn cho chiến
		tabbedPane.setBackground(MAU_NEN_CHINH);
		tabbedPane.setForeground(MAU_NAU_VIEN);
		tabbedPane.setBorder(thickBorder);

		JPanel pnSoDo = new JPanel(new BorderLayout());
		pnSoDo.setBackground(MAU_NEN_CHINH);
		pnDanhSachBan = new JPanel();
		pnDanhSachBan.setLayout(new BoxLayout(pnDanhSachBan, BoxLayout.Y_AXIS));
		pnDanhSachBan.setBackground(MAU_NEN_CHINH);
		JScrollPane scrollSoDo = new JScrollPane(pnDanhSachBan);
		scrollSoDo.setBorder(null);
		scrollSoDo.getVerticalScrollBar().setUnitIncrement(16);
		pnSoDo.add(scrollSoDo, BorderLayout.CENTER);

		JPanel pnHangHoa = new JPanel(new BorderLayout());
		pnHangHoa.setBackground(MAU_NEN_CHINH);
		pnDanhSachMon = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
		pnDanhSachMon.setBackground(MAU_NEN_CHINH);
		JScrollPane scrollMon = new JScrollPane(pnDanhSachMon);
		scrollMon.setBorder(null);
		pnHangHoa.add(scrollMon, BorderLayout.CENTER);

		tabbedPane.addTab("  SƠ ĐỒ  ", pnSoDo);
		tabbedPane.addTab("  SẢN PHẨM  ", pnHangHoa);
		pnCenterArea.add(tabbedPane, BorderLayout.CENTER);

		// --- KHU VỰC HỮU BIÊN: HÓA ĐƠN ---
		JPanel pnHoaDon = new JPanel(new BorderLayout(0, 5));
		pnHoaDon.setBackground(MAU_NEN_CHINH);
		pnHoaDon.setBorder(
				BorderFactory.createCompoundBorder(thickBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		int targetWidthHoaDon = Math.max((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.35), 450);
		pnHoaDon.setPreferredSize(new Dimension(targetWidthHoaDon, 0));
		pnCenterArea.add(pnHoaDon, BorderLayout.EAST);

		JPanel pnInfoHoaDon = new JPanel(new GridLayout(2, 2, 5, 15));
		pnInfoHoaDon.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		pnInfoHoaDon.setBackground(MAU_HEADER);

		Font fontDuLieu = new Font("Tahoma", Font.BOLD, 14);

		lbTitleUser = new JLabel("User:");
		lbValueUSer = new JLabel("Chưa có");
		JPanel pnUser = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnUser.setOpaque(false);
		pnUser.add(lbTitleUser);
		pnUser.add(lbValueUSer);

		lbTitleGioVao = new JLabel("Giờ vào:");
		lbValueGioVao = new JLabel("__/__/____");
		JPanel pnGioVao = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnGioVao.setOpaque(false);
		pnGioVao.add(lbTitleGioVao);
		pnGioVao.add(lbValueGioVao);

		lbTitleKhuVuc = new JLabel("Khu vực:");
		lbValueKhuVuc = new JLabel("Trống");
		JPanel pnKhuVuc = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnKhuVuc.setOpaque(false);
		pnKhuVuc.add(lbTitleKhuVuc);
		pnKhuVuc.add(lbValueKhuVuc);

		lbTitleTienPhaiTra = new JLabel("Tạm tính:");
		lbValueTienPhaiTra = new JLabel("0");
		JPanel pnPhaiTra = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnPhaiTra.setOpaque(false);
		pnPhaiTra.add(lbTitleTienPhaiTra);
		pnPhaiTra.add(lbValueTienPhaiTra);

		Component[] allComps = { lbTitleUser, lbValueUSer, lbTitleGioVao, lbValueGioVao, lbTitleKhuVuc, lbValueKhuVuc,
				lbTitleTienPhaiTra, lbValueTienPhaiTra };
		for (Component c : allComps) {
			c.setFont(fontDuLieu);
			c.setForeground(MAU_NAU_VIEN);
		}

		pnInfoHoaDon.add(pnUser);
		pnInfoHoaDon.add(pnGioVao);
		pnInfoHoaDon.add(pnKhuVuc);
		pnInfoHoaDon.add(pnPhaiTra);
		pnHoaDon.add(pnInfoHoaDon, BorderLayout.NORTH);

		// Bảng Hóa Đơn
		JPanel pnDanhSachHangHoa = new JPanel(new BorderLayout());
		pnDanhSachHangHoa.setOpaque(false);
		JLabel lbTieuDeHoaDon = new JLabel("HÓA ĐƠN", SwingConstants.CENTER);
		lbTieuDeHoaDon.setFont(fontTitleLg); // Bungee bự
		lbTieuDeHoaDon.setForeground(MAU_NAU_VIEN);
		lbTieuDeHoaDon.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		pnDanhSachHangHoa.add(lbTieuDeHoaDon, BorderLayout.NORTH);

		// 1. ĐÃ SỬA "Tên hàng" thành "Tên sản phẩm"
		String[] columnNames = { "#", "Tên sản phẩm", "S.L", "Giá bán", "T.Tiền" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			// Hàm này chính là khiên chắn, tuyệt đối KHÔNG cho khách hàng click đúp vào sửa
			// số liệu!
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tbDanhSachHangHoa = new JTable(tableModel);
		tbDanhSachHangHoa.setRowHeight(30);
		tbDanhSachHangHoa.setFont(new Font("Tahoma", Font.BOLD, 14));
		tbDanhSachHangHoa.setBackground(MAU_NEN_CHINH);
		tbDanhSachHangHoa.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		// 2. PHONG ẤN TABLE HEADER CHỐNG KÉO THẢ VÀ CO GIÃN
		tbDanhSachHangHoa.getTableHeader().setReorderingAllowed(false); // Khóa không cho đổi chỗ các cột
		tbDanhSachHangHoa.getTableHeader().setResizingAllowed(false); // Khóa không cho kéo giãn độ rộng cột

		// Trang trí Header
		tbDanhSachHangHoa.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		tbDanhSachHangHoa.getTableHeader().setBackground(MAU_HEADER);
		tbDanhSachHangHoa.getTableHeader().setForeground(MAU_NAU_VIEN);
		tbDanhSachHangHoa.getTableHeader().setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		// Khóa chết độ rộng của cột # và S.L (Tùy chọn thêm để đẹp hơn)
		tbDanhSachHangHoa.getColumnModel().getColumn(0).setPreferredWidth(35);
		tbDanhSachHangHoa.getColumnModel().getColumn(0).setMaxWidth(35); // Khóa cứng cột số thứ tự
		tbDanhSachHangHoa.getColumnModel().getColumn(2).setPreferredWidth(50);
		tbDanhSachHangHoa.getColumnModel().getColumn(2).setMaxWidth(50); // Khóa cứng cột số lượng

		tbDanhSachHangHoa.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbDanhSachHangHoa.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		JScrollPane scrollPane = new JScrollPane(tbDanhSachHangHoa);
		scrollPane.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));
		pnDanhSachHangHoa.add(scrollPane, BorderLayout.CENTER);
		pnHoaDon.add(pnDanhSachHangHoa, BorderLayout.CENTER);

		// Thanh toán
		JPanel pnThanhToan = new JPanel(new BorderLayout(0, 5));
		pnThanhToan.setOpaque(false);

		JPanel pnChietKhau = new JPanel(new BorderLayout());
		pnChietKhau.setBackground(MAU_HEADER);
		pnChietKhau.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		Font fontChietKhau = new Font("Tahoma", Font.BOLD, 18);

		tfGiamGia = new JTextField("0", 6);
		tfGiamGia.setFont(fontChietKhau);
		tfGiamGia.setPreferredSize(new Dimension(80, 35));
		tfGiamGia.setBackground(MAU_NEN_CHINH);
		tfGiamGia.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));
		// --- YÊU CẦU 2: Căn giữa số cho TextField ---
		tfGiamGia.setHorizontalAlignment(JTextField.CENTER);

		JPanel pnGiamGia = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnGiamGia.setOpaque(false);
		lbGiamGia = new JLabel("Giảm giá:");
		lbGiamGia.setFont(fontChietKhau);
		lbGiamGia.setForeground(MAU_NAU_VIEN);
		pnGiamGia.add(lbGiamGia);
		pnGiamGia.add(tfGiamGia);

		cbBoxThue = new JComboBox<>(new Integer[] { 0, 5, 8, 10 });
		cbBoxThue.setFont(fontChietKhau);
		cbBoxThue.setPreferredSize(new Dimension(80, 35));
		// --- YÊU CẦU 2: Căn giữa số cho ComboBox (phải dùng tà thuật Renderer) ---
		DefaultListCellRenderer centerRendererBox = new DefaultListCellRenderer();
		centerRendererBox.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
		cbBoxThue.setRenderer(centerRendererBox);

		JPanel pnThue = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		pnThue.setOpaque(false);
		lbThue = new JLabel("Thuế:");
		lbThue.setFont(fontChietKhau);
		lbThue.setForeground(MAU_NAU_VIEN);
		pnThue.add(lbThue);
		pnThue.add(cbBoxThue);

		pnChietKhau.add(pnGiamGia, BorderLayout.WEST);
		pnChietKhau.add(pnThue, BorderLayout.EAST);
		pnThanhToan.add(pnChietKhau, BorderLayout.NORTH);

		// Box Tổng Tiền
		JPanel pnTongTien = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnTongTien.setBackground(new Color(193, 71, 71));
		pnTongTien.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3));

		lbTitleTongTien = new JLabel("TỔNG TIỀN:");
		lbTitleTongTien.setFont(fontTitleLg); // Bungee bự
		lbTitleTongTien.setForeground(Color.WHITE);

		lbTongTien = new JLabel("0 VNĐ");
		lbTongTien.setFont(fontTitleLg);
		lbTongTien.setForeground(Color.WHITE);

		pnTongTien.add(lbTitleTongTien);
		pnTongTien.add(lbTongTien);
		pnThanhToan.add(pnTongTien, BorderLayout.CENTER);

		btnThanhToan = new JButton("THANH TOÁN");
		btnThanhToan.setFont(fontThanhToanBtn); // Bungee chà bá lửa
		btnThanhToan.setPreferredSize(new Dimension(0, 60));
		btnThanhToan.setBackground(new Color(56, 163, 42));
		btnThanhToan.setForeground(Color.WHITE);
		btnThanhToan.setFocusPainted(false);
		btnThanhToan.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3));
		pnThanhToan.add(btnThanhToan, BorderLayout.SOUTH);

		pnHoaDon.add(pnThanhToan, BorderLayout.SOUTH);
	}

	// =========================================================================
	// PHẦN LOGIC ĐỔ DỮ LIỆU ĐƯỢC GIỮ NGUYÊN HOÀN TOÀN TỪ CODE TRƯỚC
	// =========================================================================

	public void loadDuLieuSoDoBanMain() {
		BanDAO banDAO = new BanDAO();
		KhuVucDAO khuVucDAO = new KhuVucDAO();
		List<Ban> listBanToanBo = banDAO.findAll();
		List<KhuVuc> listKhuVucToanBo = khuVucDAO.findAll();
		Map<String, List<Ban>> mapKhuVucBan = new LinkedHashMap<>();

		for (KhuVuc kv : listKhuVucToanBo) {
			List<Ban> banCuaKv = new ArrayList<>();
			for (Ban b : listBanToanBo) {
				if (b != null && b.getMaKhuVuc() != null && b.getMaKhuVuc().equals(kv.getMaKhuVuc())) {
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
		hienThiDanhSachBanToanBo(mapKhuVucBan);
	}

	public void hienThiDanhSachBanToanBo(Map<String, List<Ban>> mapKhuVucBan) {
		pnDanhSachBan.removeAll();
		Font fontLabel = fontBungeeBase.deriveFont(Font.PLAIN, 24f);

		for (Map.Entry<String, List<Ban>> entry : mapKhuVucBan.entrySet()) {
			JPanel pnTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnTitle.setBackground(MAU_NEN_CHINH);
			JLabel lblKhuVuc = new JLabel("[" + entry.getKey().toUpperCase() + "]");
			lblKhuVuc.setFont(fontLabel); // xài Bungee cho tên khu vực
			lblKhuVuc.setForeground(MAU_NAU_VIEN);

			lblKhuVuc.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 0));
			pnTitle.add(lblKhuVuc);

			JPanel pnTables = new JPanel(new WrapLayout(FlowLayout.LEFT, 15, 15));
			pnTables.setBackground(MAU_NEN_CHINH);

			for (Ban ban : entry.getValue()) {
				JToggleButton btnBan = new JToggleButton();

				String gioVaoStr = thanhToanController.getGioVaoCuaBan(ban.getMaBan());

				if (gioVaoStr != null) {
					btnBan.setText("<html><p align='center'>" + ban.getTenBan()
							+ "<br><br><span style='font-size:12px;'>" + gioVaoStr + "</span></p></html>");
					btnBan.setBackground(MAU_HEADER);
				} else {
					btnBan.setText("<html><p align='center'>" + ban.getTenBan() + "<br><br><br></p></html>");
					btnBan.setBackground(MAU_HEADER);
				}

				btnBan.setPreferredSize(new Dimension(100, 100));
				btnBan.setFont(new Font("Tahoma", Font.BOLD, 14));
				btnBan.setForeground(MAU_NAU_VIEN);
				btnBan.setFocusPainted(false);
				btnBan.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

				btnBan.addActionListener(e -> {
					thanhToanController.xuLyChonBan(ban, entry.getKey());
				});

				pnTables.add(btnBan);
			}
			pnDanhSachBan.add(pnTitle);
			pnDanhSachBan.add(pnTables);
		}
		pnDanhSachBan.revalidate();
		pnDanhSachBan.repaint();
	}

	public void loadDuLieuHangHoaMain() {
		dao.SanPhamDAO sanPhamDAO = new dao.SanPhamDAO();
		dao.DanhMucDAO danhMucDAO = new dao.DanhMucDAO();
		List<SanPham> listSanPham = sanPhamDAO.findAll();
		List<entity.DanhMuc> listDanhMuc = danhMucDAO.findAll();

		pnDanhSachMon.removeAll();
		pnDanhSachMon.setLayout(new BoxLayout(pnDanhSachMon, BoxLayout.Y_AXIS));
		Font fontLabel = fontBungeeBase.deriveFont(Font.PLAIN, 24f);

		for (entity.DanhMuc dm : listDanhMuc) {
			JPanel pnGroupDanhMuc = new JPanel(new BorderLayout());
			pnGroupDanhMuc.setBackground(MAU_NEN_CHINH);
			JLabel lblTieuDeDM = new JLabel("[" + dm.getTenDanhMuc().toUpperCase() + "]");
			lblTieuDeDM.setFont(fontLabel); // xài Bungee luôn
			lblTieuDeDM.setForeground(MAU_NAU_VIEN);

			lblTieuDeDM.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 0));
			pnGroupDanhMuc.add(lblTieuDeDM, BorderLayout.NORTH);

			JPanel pnDanhSachSpCuaDM = new JPanel(new WrapLayout(FlowLayout.LEFT, 15, 15));
			pnDanhSachSpCuaDM.setBackground(MAU_NEN_CHINH);

			for (SanPham sp : listSanPham) {
				if (sp.getMaDanhMuc().equals(dm.getMaDanhMuc()) && sp.isTrangThai()) {
					JButton btnSp = new JButton();
					btnSp.setPreferredSize(new Dimension(120, 140));
					btnSp.setLayout(new BorderLayout());
					btnSp.setBackground(MAU_HEADER);
					btnSp.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

					JLabel lblImage = new JLabel();
					lblImage.setPreferredSize(new Dimension(120, 80));
					try {
						lblImage.setIcon(new ImageIcon(new ImageIcon(sp.getHinhAnh()).getImage().getScaledInstance(120,
								80, Image.SCALE_SMOOTH)));
					} catch (Exception ex) {
						lblImage.setText("Trống");
						lblImage.setHorizontalAlignment(SwingConstants.CENTER);
					}
					btnSp.add(lblImage, BorderLayout.NORTH);

					JPanel pnInfoText = new JPanel(new GridLayout(2, 1));
					pnInfoText.setOpaque(false);
					JLabel lblTenSp = new JLabel(sp.getTenSanPham(), SwingConstants.CENTER);
					lblTenSp.setFont(new Font("Tahoma", Font.BOLD, 12));
					lblTenSp.setForeground(MAU_NAU_VIEN);
					pnInfoText.add(lblTenSp);

					JLabel lblGia = new JLabel(sp.getGiaBan() + "đ", SwingConstants.CENTER);
					lblGia.setFont(new Font("Tahoma", Font.BOLD, 12));
					lblGia.setForeground(new Color(193, 71, 71));
					pnInfoText.add(lblGia);
					btnSp.add(pnInfoText, BorderLayout.CENTER);

					btnSp.addActionListener(e -> {
						thanhToanController.xuLyChonMon(sp);
					});

					pnDanhSachSpCuaDM.add(btnSp);
				}
			}
			pnGroupDanhMuc.add(pnDanhSachSpCuaDM, BorderLayout.CENTER);
			pnDanhSachMon.add(pnGroupDanhMuc);
		}
		pnDanhSachMon.revalidate();
		pnDanhSachMon.repaint();
	}

	class WrapLayout extends FlowLayout {
		private static final long serialVersionUID = 1L;

		public WrapLayout(int align, int hgap, int vgap) {
			super(align, hgap, vgap);
		}

		@Override
		public Dimension preferredLayoutSize(Container target) {
			return layoutSize(target, super.preferredLayoutSize(target));
		}

		@Override
		public Dimension minimumLayoutSize(Container target) {
			return layoutSize(target, super.minimumLayoutSize(target));
		}

		private Dimension layoutSize(Container target, Dimension defaultDim) {
			Container parent = target.getParent();
			while (parent != null && !(parent instanceof JViewport))
				parent = parent.getParent();
			if (parent == null)
				return defaultDim;
			int targetWidth = parent.getSize().width;
			if (targetWidth == 0)
				return defaultDim;
			int hgap = getHgap(), vgap = getVgap();
			Insets insets = target.getInsets();
			int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);
			int dimWidth = 0, dimHeight = 0, rowWidth = 0, rowHeight = 0;
			for (int i = 0; i < target.getComponentCount(); i++) {
				Component m = target.getComponent(i);
				if (m.isVisible()) {
					Dimension d = m.getPreferredSize();
					if (rowWidth + d.width > maxWidth) {
						dimWidth = Math.max(dimWidth, rowWidth);
						dimHeight += rowHeight + vgap;
						rowWidth = 0;
						rowHeight = 0;
					}
					rowWidth += d.width + hgap;
					rowHeight = Math.max(rowHeight, d.height);
				}
			}
			dimWidth = Math.max(dimWidth, rowWidth);
			dimHeight += rowHeight;
			return new Dimension(dimWidth + insets.left + insets.right + hgap * 2,
					dimHeight + insets.top + insets.bottom + vgap * 2);
		}
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public JLabel getLbValueKhuVuc() {
		return lbValueKhuVuc;
	}

	public JLabel getLbValueGioVao() {
		return lbValueGioVao;
	}

	public JLabel getLbTongTien() {
		return lbTongTien;
	}

	public JLabel getLbValueTienPhaiTra() {
		return lbValueTienPhaiTra;
	}

	public JLabel getLbValueUser() {
		return lbValueUSer;
	}

	public JTextField getTfGiamGia() {
		return tfGiamGia;
	}

	public JComboBox<Integer> getCbBoxThue() {
		return cbBoxThue;
	}

	public JButton getBtnThanhToan() {
		return btnThanhToan;
	}

	public void setTenNhanVien(String tenHienThi) {
		if (lbValueUSer != null) {
			lbValueUSer.setText(tenHienThi);
		}
	}

	public void resetDuLieu() {
		lbValueGioVao.setText("__/__/____");
		lbValueKhuVuc.setText("Trống");
		lbValueTienPhaiTra.setText("0");
		lbTongTien.setText("");
		tfGiamGia.setText("0");
		cbBoxThue.setSelectedIndex(0);
		tableModel.setRowCount(0);
	}

	public void addXemBangGiaListener(ActionListener l) {
		btnXemBangGia.addActionListener(l);
	}

	public void addQLNhanVienListener(ActionListener l) {
		btnQLNhanVien.addActionListener(l);
	}

	public void addQLCaLamListener(ActionListener l) {
		btnQLCaLam.addActionListener(l);
	}

	public void addQLSoDoListener(ActionListener l) {
		btnQLSoDo.addActionListener(l);
	}

	public void addQLHangHoaListener(ActionListener l) {
		btnQLHangHoa.addActionListener(l);
	}

	public void addQLDatBanListener(ActionListener l) {
		btnQLDatBan.addActionListener(l);
	}

	public void addDangXuatListener(ActionListener l) {
		btnDangXuat.addActionListener(l);
	}

	public void addHoaDonListener(ActionListener l) {
		btnHoaDon.addActionListener(l);
	}
}