package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;

public class FrmManHinhChinh extends JFrame {

	private static final long serialVersionUID = 1L;

	public JPanel pnDanhSachBan, pnDanhSachMon, pnDanhMuc;
	JLabel lbLogo, lbTenCuaHang, lbTitleUser, lbValueUSer, lbTitleKhuVuc, lbValueKhuVuc, lbTitleGioVao, lbValueGioVao,
			lbTitleTienPhaiTra, lbValueTienPhaiTra, lbGiamGia, lbThue, lbTongTien, lbTenKhuVuc, lbTitleTongTien;
	JButton btnDangXuat, btnThanhToan, btnThemTaiKhoan, btnXemBangGia, btnXemKhuVuc, btnXemHangHoa, btnQLNhanVien,
			btnQLSoDo, btnQLHangHoa, btnQLBangGia, btnQLCaLam, btnQLDatBan;
	JTextField tfGiamGia;
	JComboBox<Integer> cbBoxThue;
	Box boxThue, boxGiamGia;
	DefaultTableModel tableModel;

	public FrmManHinhChinh() {
		this.setTitle("Hệ thống quản lý quán cà phê");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int targetWidth = (int) (screenSize.getWidth() * 0.9);
		int targetHeight = (int) (screenSize.getHeight() * 0.9);
		this.setSize(new Dimension(targetWidth, targetHeight));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		khoiTaoGiaoDien();
	}

	private void applyIconToButton(JButton targetButton, String iconFileName, int width, int height) {
		try {
			java.net.URL resourceUrl = getClass().getResource("/images/" + iconFileName);
			if (resourceUrl != null) {
				ImageIcon originalIcon = new ImageIcon(resourceUrl);
				Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
				targetButton.setIcon(new ImageIcon(scaledImage));
				targetButton.setIconTextGap(8);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void khoiTaoGiaoDien() {
		JPanel pnHeader = new JPanel();
		pnHeader.setLayout(new BorderLayout());
		pnHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(180, 180, 180)));
		pnHeader.setBackground(Color.WHITE);
		this.add(pnHeader, BorderLayout.NORTH);

		JPanel pnHeaderTrai = new JPanel();
		pnHeaderTrai.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnHeaderTrai.setOpaque(false);

		java.net.URL imageUrl = getClass().getResource("/images/logo.png");
		lbLogo = new JLabel();
		if (imageUrl == null) {
			lbLogo.setText("[LOGO]");
		} else {
			ImageIcon iconGoc = new ImageIcon(imageUrl);
			Image imgLoi = iconGoc.getImage();
			Image imgDaEp = imgLoi.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			lbLogo.setIcon(new ImageIcon(imgDaEp));
		}
		pnHeaderTrai.add(lbLogo);

		lbTenCuaHang = new JLabel("Mio Coffee");
		lbTenCuaHang.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lbTenCuaHang.setForeground(new Color(50, 50, 50));
		pnHeaderTrai.add(lbTenCuaHang);

		pnHeader.add(pnHeaderTrai, BorderLayout.WEST);

		btnDangXuat = new JButton("Đăng Xuất");
		btnDangXuat.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnDangXuat.setPreferredSize(new Dimension(140, 40));
		btnDangXuat.setBackground(new Color(220, 53, 69));
		btnDangXuat.setForeground(Color.WHITE);
		btnDangXuat.setFocusPainted(false);
		applyIconToButton(btnDangXuat, "logout.png", 20, 20);

		JPanel pnBocNut = new JPanel();
		pnBocNut.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		pnBocNut.setOpaque(false);
		pnBocNut.add(btnDangXuat);
		pnHeader.add(pnBocNut, BorderLayout.EAST);

		JPanel pnMain = new JPanel();
		this.add(pnMain, BorderLayout.CENTER);
		pnMain.setLayout(new BorderLayout(10, 10));
		pnMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel pnHoaDon = new JPanel();
		pnMain.add(pnHoaDon, BorderLayout.WEST);
		pnHoaDon.setLayout(new BorderLayout(0, 10));
		pnHoaDon.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
						BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		pnHoaDon.setBackground(Color.WHITE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int targetWidth = (int) (screenSize.getWidth() * 0.33);
		if (targetWidth < 450) {
			targetWidth = 450;
		}
		pnHoaDon.setPreferredSize(new Dimension(targetWidth, 0));

		JPanel pnInfoHoaDon = new JPanel(new GridLayout(2, 2, 10, 15));
		pnInfoHoaDon.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(66, 133, 244), 1, true),
						BorderFactory.createEmptyBorder(15, 10, 15, 10)));
		pnInfoHoaDon.setBackground(new Color(232, 240, 254));

		Font fontTieuDe = new Font("Segoe UI", Font.BOLD, 14);
		Font fontDuLieu = new Font("Segoe UI", Font.PLAIN, 15);
		Color colorText = new Color(30, 30, 30);

		JPanel pnUser = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnUser.setOpaque(false);
		lbTitleUser = new JLabel("User:");
		lbTitleUser.setFont(fontTieuDe);
		lbTitleUser.setPreferredSize(new Dimension(40, 30));
		lbTitleUser.setForeground(new Color(100, 100, 100));
		lbValueUSer = new JLabel("Chưa có");
		lbValueUSer.setFont(fontDuLieu);
		lbValueUSer.setForeground(colorText);
		pnUser.add(lbTitleUser);
		pnUser.add(lbValueUSer);

		JPanel pnGioVao = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnGioVao.setOpaque(false);
		lbTitleGioVao = new JLabel("Giờ vào:");
		lbTitleGioVao.setFont(fontTieuDe);
		lbTitleGioVao.setForeground(new Color(100, 100, 100));
		lbValueGioVao = new JLabel("__/__/____, __:__");
		lbValueGioVao.setFont(fontDuLieu);
		lbValueGioVao.setForeground(colorText);
		pnGioVao.add(lbTitleGioVao);
		pnGioVao.add(lbValueGioVao);

		JPanel pnKhuVuc = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnKhuVuc.setOpaque(false);
		lbTitleKhuVuc = new JLabel("Khu vực:");
		lbTitleKhuVuc.setFont(fontTieuDe);
		lbTitleKhuVuc.setForeground(new Color(100, 100, 100));
		lbValueKhuVuc = new JLabel("Trống");
		lbValueKhuVuc.setFont(fontDuLieu);
		lbValueKhuVuc.setForeground(colorText);
		pnKhuVuc.add(lbTitleKhuVuc);
		pnKhuVuc.add(lbValueKhuVuc);

		JPanel pnPhaiTra = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnPhaiTra.setOpaque(false);
		lbTitleTienPhaiTra = new JLabel("Phải trả:");
		lbTitleTienPhaiTra.setFont(fontTieuDe);
		lbTitleTienPhaiTra.setForeground(new Color(220, 53, 69));
		lbValueTienPhaiTra = new JLabel("0 VNĐ");
		lbValueTienPhaiTra.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lbValueTienPhaiTra.setForeground(new Color(220, 53, 69));
		pnPhaiTra.add(lbTitleTienPhaiTra);
		pnPhaiTra.add(lbValueTienPhaiTra);

		pnInfoHoaDon.add(pnUser);
		pnInfoHoaDon.add(pnGioVao);
		pnInfoHoaDon.add(pnKhuVuc);
		pnInfoHoaDon.add(pnPhaiTra);

		pnHoaDon.add(pnInfoHoaDon, BorderLayout.NORTH);

		JPanel pnDanhSachHangHoa = new JPanel();
		pnDanhSachHangHoa.setLayout(new BorderLayout());
		pnDanhSachHangHoa.setOpaque(false);

		JLabel lbTieuDeHoaDon = new JLabel("HÓA ĐƠN", SwingConstants.CENTER);
		lbTieuDeHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lbTieuDeHoaDon.setForeground(new Color(50, 50, 50));
		lbTieuDeHoaDon.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
		pnDanhSachHangHoa.add(lbTieuDeHoaDon, BorderLayout.NORTH);

		String[] columnNames = { "#", "Tên hàng", "S.L", "Giá bán", "T.Tiền" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 2;
			}
		};

		JTable tbDanhSachHangHoa = new JTable(tableModel);
		tbDanhSachHangHoa.getTableHeader().setReorderingAllowed(false);
		tbDanhSachHangHoa.getTableHeader().setResizingAllowed(false);
		tbDanhSachHangHoa.setRowHeight(35);
		tbDanhSachHangHoa.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		tbDanhSachHangHoa.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		tbDanhSachHangHoa.getTableHeader().setBackground(new Color(66, 133, 244));
		tbDanhSachHangHoa.getTableHeader().setForeground(Color.WHITE);
		tbDanhSachHangHoa.setShowVerticalLines(false);
		tbDanhSachHangHoa.setSelectionBackground(new Color(232, 240, 254));
		tbDanhSachHangHoa.setSelectionForeground(Color.BLACK);

		tbDanhSachHangHoa.getColumnModel().getColumn(0).setPreferredWidth(40);
		tbDanhSachHangHoa.getColumnModel().getColumn(1).setPreferredWidth(160);
		tbDanhSachHangHoa.getColumnModel().getColumn(2).setPreferredWidth(50);
		tbDanhSachHangHoa.getColumnModel().getColumn(3).setPreferredWidth(100);
		tbDanhSachHangHoa.getColumnModel().getColumn(4).setPreferredWidth(100);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		tbDanhSachHangHoa.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbDanhSachHangHoa.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tbDanhSachHangHoa.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tbDanhSachHangHoa.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

		JScrollPane scrollPane = new JScrollPane(tbDanhSachHangHoa);
		scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		pnDanhSachHangHoa.add(scrollPane, BorderLayout.CENTER);
		pnHoaDon.add(pnDanhSachHangHoa, BorderLayout.CENTER);

		JPanel pnThanhToan = new JPanel();
		pnThanhToan.setLayout(new BorderLayout(0, 10));
		pnThanhToan.setOpaque(false);
		pnHoaDon.add(pnThanhToan, BorderLayout.SOUTH);

		Font fontThanhToan = new Font("Segoe UI", Font.BOLD, 16);
		JPanel pnChietKhau = new JPanel(new BorderLayout());
		pnChietKhau.setOpaque(false);
		pnThanhToan.add(pnChietKhau, BorderLayout.NORTH);

		JPanel pnGiamGia = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnGiamGia.setOpaque(false);
		lbGiamGia = new JLabel("Giảm giá:");
		lbGiamGia.setFont(fontThanhToan);
		lbGiamGia.setPreferredSize(new Dimension(80, 30));

		tfGiamGia = new JTextField(10);
		tfGiamGia.setPreferredSize(new Dimension(100, 30));
		tfGiamGia.setFont(fontThanhToan);
		tfGiamGia.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel iconVND = new JLabel(".000 VNĐ");
		iconVND.setFont(fontThanhToan);

		pnGiamGia.add(lbGiamGia);
		pnGiamGia.add(tfGiamGia);
		pnGiamGia.add(iconVND);

		JPanel pnThue = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		pnThue.setOpaque(false);
		lbThue = new JLabel("Thuế:");
		lbThue.setFont(fontThanhToan);

		Integer[] phanTramThue = { 0, 5, 8, 10 };
		cbBoxThue = new JComboBox<>(phanTramThue);
		cbBoxThue.setPreferredSize(new Dimension(70, 30));
		cbBoxThue.setFont(fontThanhToan);
		JLabel iconPhanTram = new JLabel("%");
		iconPhanTram.setFont(fontThanhToan);

		pnThue.add(lbThue);
		pnThue.add(cbBoxThue);
		pnThue.add(iconPhanTram);

		pnChietKhau.add(pnGiamGia, BorderLayout.WEST);
		pnChietKhau.add(pnThue, BorderLayout.EAST);

		JPanel pnTongTien = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnTongTien.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 1, true),
						BorderFactory.createEmptyBorder(5, 0, 5, 0)));
		pnTongTien.setBackground(new Color(253, 237, 237));

		lbTitleTongTien = new JLabel("Tổng tiền:");
		lbTitleTongTien.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lbTitleTongTien.setForeground(new Color(220, 53, 69));

		lbTongTien = new JLabel("0 VNĐ");
		lbTongTien.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lbTongTien.setForeground(new Color(220, 53, 69));

		pnTongTien.add(lbTitleTongTien);
		pnTongTien.add(lbTongTien);

		pnThanhToan.add(pnTongTien, BorderLayout.CENTER);

		btnThanhToan = new JButton("Thanh Toán");
		btnThanhToan.setFont(new Font("Segoe UI", Font.BOLD, 22));
		btnThanhToan.setPreferredSize(new Dimension(0, 60));
		btnThanhToan.setBackground(new Color(40, 167, 69));
		btnThanhToan.setForeground(Color.WHITE);
		btnThanhToan.setFocusPainted(false);
		// applyIconToButton(btnThanhToan, "procurement.png", 28, 28);
		pnThanhToan.add(btnThanhToan, BorderLayout.SOUTH);

		JPanel pnMainWorkSpace = new JPanel();
		pnMainWorkSpace.setLayout(new BorderLayout());
		pnMainWorkSpace.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
		pnMainWorkSpace.setBackground(Color.WHITE);
		pnMain.add(pnMainWorkSpace, BorderLayout.CENTER);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 16));
		tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel pnSoDo = new JPanel(new BorderLayout(10, 10));
		pnSoDo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnSoDo.setBackground(Color.WHITE);

		lbTenKhuVuc = new JLabel("");
		lbTenKhuVuc.setFont(new Font("Segoe UI", Font.BOLD, 18));
		pnSoDo.add(lbTenKhuVuc, BorderLayout.NORTH);

		pnDanhSachBan = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
		pnDanhSachBan.setBackground(Color.WHITE);
		JScrollPane scrollSoDo = new JScrollPane(pnDanhSachBan);
		scrollSoDo.setBorder(null);
		pnSoDo.add(scrollSoDo, BorderLayout.CENTER);

		JPanel pnHangHoa = new JPanel(new BorderLayout(10, 10));
		pnHangHoa.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pnHangHoa.setBackground(Color.WHITE);

		JPanel pnMainHangHoa = new JPanel(new BorderLayout(15, 15));
		pnMainHangHoa.setOpaque(false);

		pnDanhSachMon = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
		pnDanhSachMon.setBackground(Color.WHITE);
		JScrollPane scrollMon = new JScrollPane(pnDanhSachMon);
		scrollMon.setBorder(null);

		pnDanhMuc = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		pnDanhMuc.setPreferredSize(new Dimension(220, 0));
		pnDanhMuc.setBackground(new Color(250, 250, 250));
		pnDanhMuc.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(220, 220, 220)));

		pnMainHangHoa.add(scrollMon, BorderLayout.CENTER);
		pnMainHangHoa.add(pnDanhMuc, BorderLayout.EAST);

		pnHangHoa.add(pnMainHangHoa, BorderLayout.CENTER);

		tabbedPane.addTab("SƠ ĐỒ", pnSoDo);
		tabbedPane.addTab("HÀNG HÓA", pnHangHoa);

		pnMainWorkSpace.add(tabbedPane, BorderLayout.CENTER);

		JPanel pnChucNangQuanLy = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		pnChucNangQuanLy.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
		pnChucNangQuanLy.setBackground(new Color(245, 245, 245));

		btnQLNhanVien = new JButton("Nhân Viên");
		btnXemBangGia = new JButton("Bảng Giá");
		btnQLSoDo = new JButton("Sơ Đồ/Bàn");
		btnQLHangHoa = new JButton("Hàng Hóa");
		btnQLCaLam = new JButton("Ca làm việc");
		btnQLDatBan = new JButton("Đặt bàn");

		Font fontBtn = new Font("Segoe UI", Font.BOLD, 14);

		applyIconToButton(btnQLNhanVien, "barista.png", 24, 24);
		applyIconToButton(btnQLCaLam, "calendar.png", 24, 24);
		applyIconToButton(btnQLSoDo, "floor-plan.png", 24, 24);
		applyIconToButton(btnQLHangHoa, "item.png", 24, 24);
		applyIconToButton(btnXemBangGia, "price-list.png", 24, 24);
		applyIconToButton(btnQLDatBan, "booking.png", 24, 24);

		JButton[] arrBtns = { btnQLNhanVien, btnQLCaLam, btnQLSoDo, btnQLHangHoa, btnQLDatBan, btnXemBangGia };
		for (JButton btn : arrBtns) {
			btn.setFont(fontBtn);
			btn.setPreferredSize(new Dimension(150, 45));
			btn.setBackground(Color.WHITE);
			btn.setFocusPainted(false);
			pnChucNangQuanLy.add(btn);
		}

		btnXemBangGia.addActionListener(e -> {
			DlgBangGia dlg = new DlgBangGia(FrmManHinhChinh.this);
			dlg.setVisible(true);
		});

		btnQLNhanVien.addActionListener(e -> {
			DlgNhanVien dlg = new DlgNhanVien(FrmManHinhChinh.this);
			dlg.setVisible(true);
		});

		btnQLCaLam.addActionListener(e -> {
			DlgCaLamViec dlg = new DlgCaLamViec(FrmManHinhChinh.this);
			dlg.setVisible(true);
		});

		btnQLSoDo.addActionListener(e -> {
			DlgKhuVucBan dlg = new DlgKhuVucBan(FrmManHinhChinh.this);
			dlg.setVisible(true);
		});

		btnQLHangHoa.addActionListener(e -> {
			DlgHangHoa dlg = new DlgHangHoa(FrmManHinhChinh.this);
			dlg.setVisible(true);
		});

		btnQLDatBan.addActionListener(e -> {
			DlgDatBan dlg = new DlgDatBan(FrmManHinhChinh.this);
			dlg.setVisible(true);
		});

		btnDangXuat.addActionListener(e -> {
			int xacNhan = JOptionPane.showConfirmDialog(FrmManHinhChinh.this, "Xác nhận đăng xuất?", "Đăng xuất",
					JOptionPane.YES_NO_OPTION);

			if (xacNhan == JOptionPane.YES_OPTION) {
				FrmManHinhChinh.this.setVisible(false);
				resetDuLieu();

				FrmManHinhChinh.this.setVisible(true);
				FrmDangNhap frmLogin = new FrmDangNhap(FrmManHinhChinh.this);
				frmLogin.setVisible(true);

				if (frmLogin.isDangNhapThanhCong()) {
					FrmManHinhChinh.this.setVisible(true);
				} else {
					System.exit(0);
				}
			}
		});

		pnMainWorkSpace.add(pnChucNangQuanLy, BorderLayout.SOUTH);
	}

	public void resetDuLieu() {
		lbValueUSer.setText("Chưa có");
		lbValueGioVao.setText("__/__/____, __:__");
		lbValueKhuVuc.setText("Trống");
		lbValueTienPhaiTra.setText("0 VNĐ");
		lbTongTien.setText("0 VNĐ");

		if (tfGiamGia != null) {
			tfGiamGia.setText("");
		}
		if (cbBoxThue != null) {
			cbBoxThue.setSelectedIndex(0);
		}
		if (tableModel != null) {
			tableModel.setRowCount(0);
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Failed to initialize LaF");
		}

		FrmManHinhChinh frmMain = new FrmManHinhChinh();
		frmMain.setVisible(true);
		FrmDangNhap frmLogin = new FrmDangNhap(frmMain);

		frmLogin.setVisible(true);

		if (!frmLogin.isDangNhapThanhCong()) {
			System.exit(0);
		}
	}
}