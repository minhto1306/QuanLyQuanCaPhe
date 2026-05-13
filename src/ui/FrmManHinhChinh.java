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
import java.io.File;
import java.io.InputStream;
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
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.ThanhToanController;
import entity.Ban;
import entity.DanhMuc;
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
	private JButton btnThongKe;

	private ThanhToanController thanhToanController;

	private final Color MAU_NEN_CHINH = new Color(242, 233, 216);
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47);
	private final Color MAU_NAU_NUT = new Color(110, 75, 59);
	private final Color MAU_HEADER = new Color(209, 185, 161);

	private Font fontBungeeBase;

	// CHỨC NĂNG: Khởi tạo giao diện màn hình chính của ứng dụng.
	public FrmManHinhChinh() {
		this.setTitle("Hệ thống quản lý quán cà phê");
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

		loadCustomFont();
		khoiTaoGiaoDien();

		this.thanhToanController = new ThanhToanController(this);
	}

	// CHỨC NĂNG: Tải dữ liệu ban đầu cho danh mục, sản phẩm và sơ đồ bàn.
	public void loadDataInitial() {
		if (this.thanhToanController != null) {
			this.thanhToanController.lamMoiSoDoBanMain();
		}
		dao.DanhMucDAO dmDAO = new dao.DanhMucDAO();
		dao.SanPhamDAO spDAO = new dao.SanPhamDAO();
		hienThiDanhSachHangHoaMain(dmDAO.findAll(), spDAO.findAll());
	}

	// CHỨC NĂNG: Tải font chữ tùy chỉnh từ resource hoặc sử dụng font mặc định nếu
	// xảy ra lỗi.
	private void loadCustomFont() {
		try {
			InputStream is = getClass().getResourceAsStream("/font/Bungee-Regular.ttf");
			if (is != null) {
				fontBungeeBase = Font.createFont(Font.TRUETYPE_FONT, is);
			} else {
				fontBungeeBase = new Font("Impact", Font.PLAIN, 24);
			}
		} catch (Exception e) {
			fontBungeeBase = new Font("Impact", Font.PLAIN, 24);
		}
	}

	// CHỨC NĂNG: Khởi tạo và cấu hình các thành phần giao diện người dùng.
	public void khoiTaoGiaoDien() {
		JPanel pnMainLayout = new JPanel(new BorderLayout());
		pnMainLayout.setBackground(MAU_NAU_VIEN);
		this.setContentPane(pnMainLayout);

		Font fontLabel = fontBungeeBase.deriveFont(Font.PLAIN, 18f);
		Font fontButton = fontBungeeBase.deriveFont(Font.PLAIN, 24f);
		Font fontTitleLg = fontBungeeBase.deriveFont(Font.PLAIN, 28f);
		Font fontThanhToanBtn = fontBungeeBase.deriveFont(Font.PLAIN, 32f);

		Border thickBorder = BorderFactory.createLineBorder(MAU_NAU_VIEN, 3);

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

		JPanel pnButtonsContainer = new JPanel(new GridLayout(8, 1, 0, 8));
		pnButtonsContainer.setBackground(MAU_NAU_VIEN);

		btnHoaDon = new JButton("HÓA ĐƠN");
		btnXemBangGia = new JButton("BẢNG GIÁ");
		btnQLDatBan = new JButton("ĐẶT BÀN");
		btnQLCaLam = new JButton("LỊCH LÀM");
		btnQLSoDo = new JButton("SƠ ĐỒ / BÀN");
		btnQLHangHoa = new JButton("SẢN PHẨM");
		btnQLNhanVien = new JButton("NHÂN VIÊN");
		btnThongKe = new JButton("THỐNG KÊ");
		JButton[] arrBtns = { btnHoaDon, btnXemBangGia, btnQLDatBan, btnQLCaLam, btnQLSoDo, btnQLHangHoa, btnQLNhanVien,
				btnThongKe };
		for (JButton btn : arrBtns) {
			btn.setFont(fontButton);
			btn.setBackground(MAU_NAU_NUT);
			btn.setForeground(Color.WHITE);
			btn.setFocusPainted(false);
			btn.setHorizontalAlignment(SwingConstants.LEFT);
			btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NEN_CHINH, 6),
					BorderFactory.createEmptyBorder(0, 15, 0, 0)));
			pnButtonsContainer.add(btn);
		}

		pnSidebar.add(pnButtonsContainer, BorderLayout.CENTER);
		pnMainLayout.add(pnSidebar, BorderLayout.WEST);

		JPanel pnRightContainer = new JPanel(new BorderLayout(10, 10));
		pnRightContainer.setBackground(MAU_NAU_VIEN);
		pnRightContainer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
		pnMainLayout.add(pnRightContainer, BorderLayout.CENTER);

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
			lb.setFont(fontLabel);
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

		JPanel pnCenterArea = new JPanel(new BorderLayout(10, 0));
		pnCenterArea.setBackground(MAU_NAU_VIEN);
		pnRightContainer.add(pnCenterArea, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(fontButton);
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
		scrollSoDo.getVerticalScrollBar().setUnitIncrement(20);
		pnSoDo.add(scrollSoDo, BorderLayout.CENTER);

		JPanel pnHangHoa = new JPanel(new BorderLayout());
		pnHangHoa.setBackground(MAU_NEN_CHINH);
		pnDanhSachMon = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
		pnDanhSachMon.setBackground(MAU_NEN_CHINH);
		JScrollPane scrollMon = new JScrollPane(pnDanhSachMon);
		scrollMon.setBorder(null);
		scrollMon.getVerticalScrollBar().setUnitIncrement(20);
		pnHangHoa.add(scrollMon, BorderLayout.CENTER);

		tabbedPane.addTab("  SƠ ĐỒ  ", pnSoDo);
		tabbedPane.addTab("  SẢN PHẨM  ", pnHangHoa);
		pnCenterArea.add(tabbedPane, BorderLayout.CENTER);

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
		JPanel pnUser = new JPanel(new BorderLayout(5, 0));
		pnUser.setOpaque(false);
		pnUser.add(lbTitleUser, BorderLayout.WEST);
		pnUser.add(lbValueUSer, BorderLayout.CENTER);

		lbTitleGioVao = new JLabel("Giờ vào:");
		lbValueGioVao = new JLabel("__/__/____");
		JPanel pnGioVao = new JPanel(new BorderLayout(5, 0));
		pnGioVao.setOpaque(false);
		pnGioVao.add(lbTitleGioVao, BorderLayout.WEST);
		pnGioVao.add(lbValueGioVao, BorderLayout.CENTER);

		lbTitleKhuVuc = new JLabel("Khu vực:");
		lbValueKhuVuc = new JLabel("Trống");
		JPanel pnKhuVuc = new JPanel(new BorderLayout(5, 0));
		pnKhuVuc.setOpaque(false);
		pnKhuVuc.add(lbTitleKhuVuc, BorderLayout.WEST);
		pnKhuVuc.add(lbValueKhuVuc, BorderLayout.CENTER);

		lbTitleTienPhaiTra = new JLabel("Phụ thu:");
		lbValueTienPhaiTra = new JLabel("0 VNĐ");
		JPanel pnPhaiTra = new JPanel(new BorderLayout(5, 0));
		pnPhaiTra.setOpaque(false);
		pnPhaiTra.add(lbTitleTienPhaiTra, BorderLayout.WEST);
		pnPhaiTra.add(lbValueTienPhaiTra, BorderLayout.CENTER);

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

		JPanel pnDanhSachHangHoa = new JPanel(new BorderLayout());
		pnDanhSachHangHoa.setOpaque(false);
		JLabel lbTieuDeHoaDon = new JLabel("HÓA ĐƠN", SwingConstants.CENTER);
		lbTieuDeHoaDon.setFont(fontTitleLg);
		lbTieuDeHoaDon.setForeground(MAU_NAU_VIEN);
		lbTieuDeHoaDon.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		pnDanhSachHangHoa.add(lbTieuDeHoaDon, BorderLayout.NORTH);

		String[] columnNames = { "#", "Tên sản phẩm", "S.L", "Giá bán", "T.Tiền" };
		tableModel = new DefaultTableModel(columnNames, 0) {
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

		tbDanhSachHangHoa.getTableHeader().setReorderingAllowed(false);
		tbDanhSachHangHoa.getTableHeader().setResizingAllowed(false);

		tbDanhSachHangHoa.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		tbDanhSachHangHoa.getTableHeader().setBackground(MAU_HEADER);
		tbDanhSachHangHoa.getTableHeader().setForeground(MAU_NAU_VIEN);
		tbDanhSachHangHoa.getTableHeader().setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		tbDanhSachHangHoa.getColumnModel().getColumn(0).setPreferredWidth(35);
		tbDanhSachHangHoa.getColumnModel().getColumn(0).setMaxWidth(35);
		tbDanhSachHangHoa.getColumnModel().getColumn(2).setPreferredWidth(50);
		tbDanhSachHangHoa.getColumnModel().getColumn(2).setMaxWidth(50);

		tbDanhSachHangHoa.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbDanhSachHangHoa.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		JScrollPane scrollPane = new JScrollPane(tbDanhSachHangHoa);
		scrollPane.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		pnDanhSachHangHoa.add(scrollPane, BorderLayout.CENTER);
		pnHoaDon.add(pnDanhSachHangHoa, BorderLayout.CENTER);

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

		JPanel pnTongTien = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnTongTien.setBackground(new Color(193, 71, 71));
		pnTongTien.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3));

		lbTitleTongTien = new JLabel("TỔNG TIỀN:");
		lbTitleTongTien.setFont(fontTitleLg);
		lbTitleTongTien.setForeground(Color.WHITE);

		lbTongTien = new JLabel("0 VNĐ");
		lbTongTien.setFont(fontTitleLg);
		lbTongTien.setForeground(Color.WHITE);

		pnTongTien.add(lbTitleTongTien);
		pnTongTien.add(lbTongTien);
		pnThanhToan.add(pnTongTien, BorderLayout.CENTER);

		btnThanhToan = new JButton("THANH TOÁN");
		btnThanhToan.setFont(fontThanhToanBtn);
		btnThanhToan.setPreferredSize(new Dimension(0, 60));
		btnThanhToan.setBackground(new Color(56, 163, 42));
		btnThanhToan.setForeground(Color.WHITE);
		btnThanhToan.setFocusPainted(false);
		btnThanhToan.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3));
		pnThanhToan.add(btnThanhToan, BorderLayout.SOUTH);

		pnHoaDon.add(pnThanhToan, BorderLayout.SOUTH);
	}

	// CHỨC NĂNG: Hiển thị toàn bộ sơ đồ bàn ra giao diện.
	public void hienThiDanhSachBanToanBo(Map<String, List<Ban>> mapKhuVucBan) {
		pnDanhSachBan.removeAll();
		Font fontLabel = fontBungeeBase.deriveFont(Font.PLAIN, 24f);

		for (Map.Entry<String, List<Ban>> entry : mapKhuVucBan.entrySet()) {
			JPanel pnTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnTitle.setBackground(MAU_NEN_CHINH);

			pnTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
			pnTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

			JLabel lblKhuVuc = new JLabel("[" + entry.getKey().toUpperCase() + "]");
			lblKhuVuc.setFont(fontLabel);
			lblKhuVuc.setForeground(MAU_NAU_VIEN);

			lblKhuVuc.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 0));
			pnTitle.add(lblKhuVuc);

			JPanel pnTables = new JPanel(new WrapLayout(FlowLayout.LEFT, 15, 15));
			pnTables.setBackground(MAU_NEN_CHINH);

			pnTables.setAlignmentX(Component.LEFT_ALIGNMENT);

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

	// CHỨC NĂNG: Cập nhật danh sách hàng hóa và danh mục hiển thị trên giao diện.
	public void hienThiDanhSachHangHoaMain(List<DanhMuc> listDanhMuc, List<SanPham> listSanPham) {
		pnDanhSachMon.removeAll();
		pnDanhSachMon.setLayout(new BoxLayout(pnDanhSachMon, BoxLayout.Y_AXIS));
		Font fontLabel = fontBungeeBase.deriveFont(Font.PLAIN, 24f);

		for (DanhMuc dm : listDanhMuc) {
			JPanel pnGroupDanhMuc = new JPanel(new BorderLayout());
			pnGroupDanhMuc.setBackground(MAU_NEN_CHINH);
			JLabel lblTieuDeDM = new JLabel("[" + dm.getTenDanhMuc().toUpperCase() + "]");
			lblTieuDeDM.setFont(fontLabel);
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

					JLabel lblImage = new JLabel("Đang tải...", SwingConstants.CENTER);
					lblImage.setPreferredSize(new Dimension(120, 80));
					lblImage.setForeground(Color.GRAY);

					new Thread(() -> {
						try {
							String tenHinh = sp.getHinhAnh();
							if (tenHinh != null && !tenHinh.trim().isEmpty()) {
								tenHinh = tenHinh.trim();

								File fileHinh = new File("product-images", tenHinh);

								if (!fileHinh.exists()) {
									fileHinh = new File("src/images", tenHinh);
								}

								if (!fileHinh.exists()) {
									fileHinh = new File(tenHinh);
								}

								if (fileHinh.exists()) {
									ImageIcon iconGoc = new ImageIcon(fileHinh.getAbsolutePath());
									Image imgScale = iconGoc.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
									ImageIcon finalIcon = new ImageIcon(imgScale);

									SwingUtilities.invokeLater(() -> {
										lblImage.setIcon(finalIcon);
										lblImage.setText("");
									});
								} else {
									SwingUtilities.invokeLater(() -> {
										lblImage.setText("No Image");
										lblImage.setForeground(Color.RED);
									});
								}
							} else {
								SwingUtilities.invokeLater(() -> {
									lblImage.setText("Trống");
									lblImage.setForeground(MAU_NAU_VIEN);
								});
							}
						} catch (Exception ex) {
							SwingUtilities.invokeLater(() -> {
								lblImage.setText("Lỗi Ảnh");
								lblImage.setForeground(Color.RED);
							});
						}
					}).start();

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

	// CHỨC NĂNG: Phân quyền hiển thị các chức năng trên giao diện dựa vào vai trò.
	public void phanQuyen(String vaiTro) {
		if (vaiTro != null && vaiTro.toLowerCase().contains("thu")) {
			btnQLNhanVien.setVisible(false);
			btnQLSoDo.setVisible(false);
			btnQLHangHoa.setVisible(false);
			btnQLCaLam.setVisible(true);
			btnThongKe.setVisible(false);
		} else {
			btnQLNhanVien.setVisible(true);
			btnQLSoDo.setVisible(true);
			btnQLHangHoa.setVisible(true);
			btnQLCaLam.setVisible(true);
			btnThongKe.setVisible(true);
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

	// CHỨC NĂNG: Đặt tên nhân viên trên hóa đơn.
	public void setTenNhanVien(String tenHienThi) {
		if (lbValueUSer != null) {
			lbValueUSer.setText(tenHienThi);
		}
	}

	// CHỨC NĂNG: Xóa sạch các dữ liệu tạm trong hóa đơn hiện tại để chuẩn bị cho
	// giao dịch mới.
	public void resetDuLieu() {
		lbValueGioVao.setText("__/__/____");
		lbValueKhuVuc.setText("Trống");
		lbValueTienPhaiTra.setText("0 VNĐ");
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

	public void addThongKeListener(ActionListener l) {
		btnThongKe.addActionListener(l);

	}
}