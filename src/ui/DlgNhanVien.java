package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DlgNhanVien extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;
	private JPanel tabNhanVien;
	private JPanel tabTaiKhoan;
	private JPanel pnNhanVien;
	private JPanel pnTaiKhoan;

	private JButton btnThem;
	private JButton btnXoa;
	private JButton btnXoaTrang;
	private JButton btnSua;
	private JButton btnLuu;
	private JButton btnTim;
	private JLabel lbMaNhanVien;
	private JLabel lbHoTenNhanVien;
	private JLabel lbSoDienThoai;
	private JLabel lbCCCD;
	private JLabel lbTenDangNhap;
	private JLabel lbVaiTro;
	private JLabel lbTimNV;
	private JComboBox<String> cbVaiTro;
	private JTextField tfMaNhanVien;
	private JTextField tfHoTenNhanVien;
	private JTextField tfSoDienThoai;
	private JTextField tfCCCD;
	private JTextField tfTenDangNhap;
	private JTextField tfTimNV;
	private Box boxRow1;
	private Box boxRow2;
	private Box boxRow3;
	private Box boxTimNV;
	private JTable tbNhanVien;
	private DefaultTableModel tmNhanVien;

	private JButton btnTK_Them;
	private JButton btnTK_Xoa;
	private JButton btnTK_XoaTrang;
	private JButton btnTK_Sua;
	private JButton btnTK_Luu;
	private JButton btnTK_Tim;
	private JLabel lbTK_TenDangNhap;
	private JLabel lbTK_MatKhau;
	private JLabel lbTK_TrangThai;
	private JLabel lbTK_VaiTro;
	private JLabel lbTK_Tim;
	private JComboBox<String> cbTK_TenDangNhap;
	private JComboBox<String> cbTK_VaiTro;
	private JComboBox<String> cbTK_TrangThai;
	private JTextField tfTK_MatKhau;
	private JTextField tfTK_Tim;
	private Box boxTK_Row1;
	private Box boxTK_Row2;
	private Box boxTK_Tim;
	private JTable tbTaiKhoan;
	private DefaultTableModel tmTaiKhoan;

	// BỘ CHÂN KHÍ MÀU SẮC ĐỒNG BỘ RETRO (Sao y bản chính từ DlgHangHoa)
	private final Color MAU_NEN_CHINH = new Color(242, 233, 216); // Vani
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47); // Nâu đậm
	private final Color MAU_HEADER = new Color(209, 185, 161); // Bạc xỉu
	private Font fontBungeeBase;

	public DlgNhanVien(JFrame parent) {
		super(parent, "NHÂN VIÊN VÀ TÀI KHOẢN", true);
		this.setSize(950, 750);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		loadCustomFont();

		ImageIcon iconNhanVien = taoIconThuNho("/images/barista.png", 24, 24);
		ImageIcon iconTaiKhoan = taoIconThuNho("/images/profile.png", 24, 24);

		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
		tabNhanVien = khoiTaoTabNhanVien();
		tabTaiKhoan = khoiTaoTabTaiKhoan();

		tabbedPane.addTab("THÔNG TIN NHÂN VIÊN", iconNhanVien, tabNhanVien, "Xem thông tin nhân viên");
		tabbedPane.addTab("THÔNG TIN TÀI KHOẢN", iconTaiKhoan, tabTaiKhoan, "Xem thông tin tài khoản");

		// Nền tổng thể Nâu Đậm, lót thêm một lớp panel để tạo viền padding y hệt
		// DlgHangHoa
		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(MAU_NAU_VIEN);
		container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Khoảng cách viền ngoài
		container.add(tabbedPane, BorderLayout.CENTER);
		this.setContentPane(container);
	}

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

	private ImageIcon taoIconThuNho(String duongDan, int width, int height) {
		java.net.URL url = getClass().getResource(duongDan);
		if (url == null)
			return null;
		return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	// CHIÊU THỨC: Trang trí chung cho các TextField / ComboBox
	private void setupRetroInput(Component comp) {
		if (comp instanceof JTextField) {
			JTextField tf = (JTextField) comp;
			tf.setFont(new Font("Arial", Font.BOLD, 15));
			tf.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2),
					BorderFactory.createEmptyBorder(2, 5, 2, 5)));
		} else if (comp instanceof JComboBox) {
			JComboBox<?> cb = (JComboBox<?>) comp;
			cb.setFont(new Font("Arial", Font.BOLD, 15));
			cb.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));
		}
	}

	// CHIÊU THỨC: Trang trí Nút Bấm
	private void setupRetroButton(JButton btn) {
		btn.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 16f));
		btn.setBackground(MAU_NEN_CHINH);
		btn.setForeground(MAU_NAU_VIEN);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
	}

	public JPanel khoiTaoTabNhanVien() {
		pnNhanVien = new JPanel(new BorderLayout());
		pnNhanVien.setBackground(MAU_NEN_CHINH); // Nhuộm màu nền
		pnNhanVien.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		boxRow1 = Box.createHorizontalBox();
		boxRow2 = Box.createHorizontalBox();
		boxRow3 = Box.createHorizontalBox();
		boxTimNV = Box.createHorizontalBox();

		lbTimNV = new JLabel("TÌM NHÂN VIÊN:");
		lbMaNhanVien = new JLabel("MÃ NHÂN VIÊN:");
		lbHoTenNhanVien = new JLabel("HỌ TÊN:");
		lbSoDienThoai = new JLabel("SỐ ĐIỆN THOẠI:");

		lbTenDangNhap = new JLabel("TÊN ĐĂNG NHẬP:");
		lbCCCD = new JLabel("CCCD:");
		lbVaiTro = new JLabel("VAI TRÒ:");

		Dimension dLeftLabel = new Dimension(160, 25);
		lbMaNhanVien.setPreferredSize(dLeftLabel);
		lbHoTenNhanVien.setPreferredSize(dLeftLabel);
		lbSoDienThoai.setPreferredSize(dLeftLabel);

		Dimension dRightLabel = new Dimension(160, 25);
		lbTenDangNhap.setPreferredSize(dRightLabel);
		lbCCCD.setPreferredSize(dRightLabel);
		lbVaiTro.setPreferredSize(dRightLabel);

		lbTimNV.setPreferredSize(new Dimension(160, 25));

		// Đổi Font Bungee cho Labels
		Font fontLabelRetro = fontBungeeBase.deriveFont(Font.PLAIN, 16f);
		JLabel[] labels = { lbTimNV, lbMaNhanVien, lbHoTenNhanVien, lbSoDienThoai, lbTenDangNhap, lbCCCD, lbVaiTro };
		for (JLabel lbl : labels) {
			lbl.setFont(fontLabelRetro);
			lbl.setForeground(MAU_NAU_VIEN);
		}

		tfTimNV = new JTextField();
		tfTimNV.setPreferredSize(new Dimension(300, 30));
		setupRetroInput(tfTimNV);

		tfMaNhanVien = new JTextField();
		tfHoTenNhanVien = new JTextField();
		tfSoDienThoai = new JTextField();
		tfTenDangNhap = new JTextField();
		tfCCCD = new JTextField();
		cbVaiTro = new JComboBox<String>();
		cbVaiTro.addItem("Quản lý");
		cbVaiTro.addItem("Thu ngân");

		Dimension dInput = new Dimension(220, 30);
		JTextField[] tfs = { tfMaNhanVien, tfHoTenNhanVien, tfSoDienThoai, tfTenDangNhap, tfCCCD };
		for (JTextField tf : tfs) {
			tf.setPreferredSize(dInput);
			tf.setMaximumSize(dInput);
			setupRetroInput(tf);
		}

		cbVaiTro.setPreferredSize(dInput);
		cbVaiTro.setMaximumSize(dInput);
		setupRetroInput(cbVaiTro);

		btnTim = new JButton(taoIconThuNho("/images/search.png", 20, 20));
		btnTim.setPreferredSize(new Dimension(40, 30));
		btnTim.setBackground(MAU_NEN_CHINH);
		btnTim.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		JPanel pnNhapThongTin = new JPanel();
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.Y_AXIS));
		pnNhapThongTin.setBackground(MAU_NEN_CHINH);
		Border brdVienDen = BorderFactory.createLineBorder(MAU_NAU_VIEN, 4);
		Border brdThutVao = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));
		pnNhanVien.add(pnNhapThongTin, BorderLayout.NORTH);

		boxRow1.add(lbMaNhanVien);
		boxRow1.add(tfMaNhanVien);
		boxRow1.add(Box.createHorizontalStrut(30));
		boxRow1.add(lbTenDangNhap);
		boxRow1.add(tfTenDangNhap);
		boxRow1.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxRow1);
		pnNhapThongTin.add(Box.createVerticalStrut(20));

		boxRow2.add(lbHoTenNhanVien);
		boxRow2.add(tfHoTenNhanVien);
		boxRow2.add(Box.createHorizontalStrut(30));
		boxRow2.add(lbCCCD);
		boxRow2.add(tfCCCD);
		boxRow2.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxRow2);
		pnNhapThongTin.add(Box.createVerticalStrut(20));

		boxRow3.add(lbSoDienThoai);
		boxRow3.add(tfSoDienThoai);
		boxRow3.add(Box.createHorizontalStrut(30));
		boxRow3.add(lbVaiTro);
		boxRow3.add(cbVaiTro);
		boxRow3.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxRow3);

		// VÙNG BẢNG DỮ LIỆU
		JPanel pnBangDuLieu = new JPanel(new BorderLayout());
		pnBangDuLieu.setBackground(MAU_NEN_CHINH);
		pnBangDuLieu.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), brdVienDen));
		pnNhanVien.add(pnBangDuLieu, BorderLayout.CENTER);

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnTimKiem.setBackground(MAU_HEADER);
		pnTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, MAU_NAU_VIEN));
		boxTimNV.add(lbTimNV);
		boxTimNV.add(tfTimNV);
		boxTimNV.add(Box.createHorizontalStrut(10));
		boxTimNV.add(btnTim);
		pnTimKiem.add(boxTimNV);
		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "#", "Mã NV", "Họ tên", "Tên đăng nhập", "Số ĐT", "CCCD", "Vai trò" };
		tmNhanVien = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbNhanVien = new JTable(tmNhanVien);

		// Style Bảng Retro
		JTableHeader header = tbNhanVien.getTableHeader();
		header.setFont(new Font("Tahoma", Font.BOLD, 16));
		header.setBackground(MAU_HEADER);
		header.setForeground(MAU_NAU_VIEN);
		header.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		tbNhanVien.setFont(new Font("Tahoma", Font.BOLD, 14));
		tbNhanVien.setRowHeight(35);
		tbNhanVien.setBackground(MAU_NEN_CHINH);
		tbNhanVien.setGridColor(MAU_NAU_VIEN);
		tbNhanVien.setShowGrid(true);

		tbNhanVien.getColumnModel().getColumn(0).setMaxWidth(40);
		tbNhanVien.getColumnModel().getColumn(1).setPreferredWidth(80);
		tbNhanVien.getColumnModel().getColumn(2).setPreferredWidth(180);
		tbNhanVien.getColumnModel().getColumn(3).setPreferredWidth(120);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tbNhanVien.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbNhanVien.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

		JScrollPane scrollPane = new JScrollPane(tbNhanVien);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);

		// VÙNG NÚT CHỨC NĂNG
		JPanel pnNutChucNang = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		pnNutChucNang.setBackground(MAU_NEN_CHINH);
		pnNutChucNang.setBorder(brdVienDen);
		pnNhanVien.add(pnNutChucNang, BorderLayout.SOUTH);

		btnThem = new JButton(" THÊM", taoIconThuNho("/images/add.png", 20, 20));
		btnXoa = new JButton(" XÓA", taoIconThuNho("/images/delete.png", 20, 20));
		btnXoaTrang = new JButton(" XÓA TRẮNG", taoIconThuNho("/images/clear.png", 20, 20));
		btnSua = new JButton(" SỬA", taoIconThuNho("/images/edit.png", 20, 20));
		btnLuu = new JButton(" LƯU", taoIconThuNho("/images/save.png", 20, 20));
		btnLuu.setEnabled(false);

		JButton[] btns = { btnThem, btnXoa, btnXoaTrang, btnSua, btnLuu };
		for (JButton b : btns) {
			setupRetroButton(b);
			pnNutChucNang.add(b);
		}

		tbNhanVien.getTableHeader().setResizingAllowed(false);
		tbNhanVien.getTableHeader().setReorderingAllowed(false);

		return pnNhanVien;
	}

	public JPanel khoiTaoTabTaiKhoan() {
		pnTaiKhoan = new JPanel(new BorderLayout());
		pnTaiKhoan.setBackground(MAU_NEN_CHINH);
		pnTaiKhoan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		boxTK_Row1 = Box.createHorizontalBox();
		boxTK_Row2 = Box.createHorizontalBox();
		boxTK_Tim = Box.createHorizontalBox();
		lbTK_Tim = new JLabel("TÌM TÀI KHOẢN:");
		lbTK_TenDangNhap = new JLabel("TÊN ĐĂNG NHẬP:");
		lbTK_VaiTro = new JLabel("VAI TRÒ:");
		lbTK_MatKhau = new JLabel("MẬT KHẨU:");
		lbTK_TrangThai = new JLabel("TRẠNG THÁI:");

		Dimension dLabel = new Dimension(160, 25);
		lbTK_TenDangNhap.setPreferredSize(dLabel);
		lbTK_VaiTro.setPreferredSize(dLabel);
		lbTK_MatKhau.setPreferredSize(dLabel);
		lbTK_TrangThai.setPreferredSize(dLabel);
		lbTK_Tim.setPreferredSize(dLabel);

		Font fontLabelRetro = fontBungeeBase.deriveFont(Font.PLAIN, 16f);
		JLabel[] tkLabels = { lbTK_Tim, lbTK_TenDangNhap, lbTK_VaiTro, lbTK_MatKhau, lbTK_TrangThai };
		for (JLabel lbl : tkLabels) {
			lbl.setFont(fontLabelRetro);
			lbl.setForeground(MAU_NAU_VIEN);
		}

		tfTK_Tim = new JTextField();
		tfTK_Tim.setPreferredSize(new Dimension(300, 30));
		setupRetroInput(tfTK_Tim);

		cbTK_TenDangNhap = new JComboBox<String>();
		tfTK_MatKhau = new JTextField();

		cbTK_VaiTro = new JComboBox<String>();
		cbTK_VaiTro.addItem("Quản lý");
		cbTK_VaiTro.addItem("Thu ngân");

		cbTK_TrangThai = new JComboBox<String>();
		cbTK_TrangThai.addItem("Hoạt động");
		cbTK_TrangThai.addItem("Khóa");

		Dimension dInput = new Dimension(250, 30);
		cbTK_TenDangNhap.setPreferredSize(dInput);
		cbTK_TenDangNhap.setMaximumSize(dInput);
		tfTK_MatKhau.setPreferredSize(dInput);
		tfTK_MatKhau.setMaximumSize(dInput);
		cbTK_VaiTro.setPreferredSize(dInput);
		cbTK_VaiTro.setMaximumSize(dInput);
		cbTK_TrangThai.setPreferredSize(dInput);
		cbTK_TrangThai.setMaximumSize(dInput);

		setupRetroInput(cbTK_TenDangNhap);
		setupRetroInput(tfTK_MatKhau);
		setupRetroInput(cbTK_VaiTro);
		setupRetroInput(cbTK_TrangThai);

		btnTK_Tim = new JButton(taoIconThuNho("/images/search.png", 20, 20));
		btnTK_Tim.setPreferredSize(new Dimension(40, 30));
		btnTK_Tim.setBackground(MAU_NEN_CHINH);
		btnTK_Tim.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		JPanel pnNhapThongTin = new JPanel();
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.Y_AXIS));
		pnNhapThongTin.setBackground(MAU_NEN_CHINH);
		Border brdVienDen = BorderFactory.createLineBorder(MAU_NAU_VIEN, 4);
		Border brdThutVao = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));
		pnTaiKhoan.add(pnNhapThongTin, BorderLayout.NORTH);

		boxTK_Row1.add(lbTK_TenDangNhap);
		boxTK_Row1.add(cbTK_TenDangNhap);
		boxTK_Row1.add(Box.createHorizontalStrut(50));
		boxTK_Row1.add(lbTK_MatKhau);
		boxTK_Row1.add(tfTK_MatKhau);
		boxTK_Row1.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxTK_Row1);
		pnNhapThongTin.add(Box.createVerticalStrut(20));

		boxTK_Row2.add(lbTK_VaiTro);
		boxTK_Row2.add(cbTK_VaiTro);
		boxTK_Row2.add(Box.createHorizontalStrut(50));
		boxTK_Row2.add(lbTK_TrangThai);
		boxTK_Row2.add(cbTK_TrangThai);
		boxTK_Row2.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxTK_Row2);

		JPanel pnBangDuLieu = new JPanel(new BorderLayout());
		pnBangDuLieu.setBackground(MAU_NEN_CHINH);
		pnBangDuLieu.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), brdVienDen));
		pnTaiKhoan.add(pnBangDuLieu, BorderLayout.CENTER);

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnTimKiem.setBackground(MAU_HEADER);
		pnTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, MAU_NAU_VIEN));
		boxTK_Tim.add(lbTK_Tim);
		boxTK_Tim.add(tfTK_Tim);
		boxTK_Tim.add(Box.createHorizontalStrut(10));
		boxTK_Tim.add(btnTK_Tim);
		pnTimKiem.add(boxTK_Tim);
		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "#", "Tên đăng nhập", "Mật khẩu", "Vai trò", "Trạng thái" };
		tmTaiKhoan = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbTaiKhoan = new JTable(tmTaiKhoan);

		JTableHeader header = tbTaiKhoan.getTableHeader();
		header.setFont(new Font("Tahoma", Font.BOLD, 16));
		header.setBackground(MAU_HEADER);
		header.setForeground(MAU_NAU_VIEN);
		header.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		tbTaiKhoan.setFont(new Font("Tahoma", Font.BOLD, 14));
		tbTaiKhoan.setRowHeight(35);
		tbTaiKhoan.setBackground(MAU_NEN_CHINH);
		tbTaiKhoan.setGridColor(MAU_NAU_VIEN);
		tbTaiKhoan.setShowGrid(true);

		tbTaiKhoan.getColumnModel().getColumn(0).setMaxWidth(40);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tbTaiKhoan.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		// Nhuộm màu trạng thái tài khoản
		tbTaiKhoan.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setHorizontalAlignment(SwingConstants.CENTER);
				if (value != null) {
					String status = value.toString();
					if (status.equalsIgnoreCase("Khóa")) {
						c.setForeground(Color.RED);
					} else if (status.equalsIgnoreCase("Hoạt động")) {
						c.setForeground(new Color(0, 153, 0));
					} else {
						c.setForeground(MAU_NAU_VIEN);
					}
				}
				if (isSelected)
					c.setBackground(table.getSelectionBackground());
				else
					c.setBackground(table.getBackground());
				return c;
			}
		});

		JScrollPane scrollPane = new JScrollPane(tbTaiKhoan);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);

		JPanel pnNutChucNang = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		pnNutChucNang.setBackground(MAU_NEN_CHINH);
		pnNutChucNang.setBorder(brdVienDen);
		pnTaiKhoan.add(pnNutChucNang, BorderLayout.SOUTH);

		btnTK_Them = new JButton(" THÊM", taoIconThuNho("/images/add.png", 20, 20));
		btnTK_Xoa = new JButton(" XÓA", taoIconThuNho("/images/delete.png", 20, 20));
		btnTK_XoaTrang = new JButton(" XÓA TRẮNG", taoIconThuNho("/images/clear.png", 20, 20));
		btnTK_Sua = new JButton(" SỬA", taoIconThuNho("/images/edit.png", 20, 20));
		btnTK_Luu = new JButton(" LƯU", taoIconThuNho("/images/save.png", 20, 20));
		btnTK_Luu.setEnabled(false);

		JButton[] btnsDM = { btnTK_Them, btnTK_Xoa, btnTK_XoaTrang, btnTK_Sua, btnTK_Luu };
		for (JButton b : btnsDM) {
			setupRetroButton(b);
			pnNutChucNang.add(b);
		}

		tbTaiKhoan.getTableHeader().setResizingAllowed(false);
		tbTaiKhoan.getTableHeader().setReorderingAllowed(false);

		return pnTaiKhoan;
	}

	public void addThemNVListener(ActionListener l) {
		btnThem.addActionListener(l);
	}

	public void addXoaNVListener(ActionListener l) {
		btnXoa.addActionListener(l);
	}

	public void addSuaNVListener(ActionListener l) {
		btnSua.addActionListener(l);
	}

	public void addLuuNVListener(ActionListener l) {
		btnLuu.addActionListener(l);
	}

	public void addXoaTrangNVListener(ActionListener l) {
		btnXoaTrang.addActionListener(l);
	}

	public void addTimNVListener(ActionListener l) {
		btnTim.addActionListener(l);
	}

	public void addBangNhanVienMouseListener(MouseListener l) {
		tbNhanVien.addMouseListener(l);
	}

	public void addThemTKListener(ActionListener l) {
		btnTK_Them.addActionListener(l);
	}

	public void addXoaTKListener(ActionListener l) {
		btnTK_Xoa.addActionListener(l);
	}

	public void addSuaTKListener(ActionListener l) {
		btnTK_Sua.addActionListener(l);
	}

	public void addLuuTKListener(ActionListener l) {
		btnTK_Luu.addActionListener(l);
	}

	public void addXoaTrangTKListener(ActionListener l) {
		btnTK_XoaTrang.addActionListener(l);
	}

	public void addTimTKListener(ActionListener l) {
		btnTK_Tim.addActionListener(l);
	}

	public void addBangTaiKhoanMouseListener(MouseListener l) {
		tbTaiKhoan.addMouseListener(l);
	}

	public String getMaNVInput() {
		return tfMaNhanVien.getText().trim();
	}

	public String getHoTenNVInput() {
		return tfHoTenNhanVien.getText().trim();
	}

	public String getTenDangNhapNVInput() {
		return tfTenDangNhap.getText().trim();
	}

	public String getSDTInput() {
		return tfSoDienThoai.getText().trim();
	}

	public String getCCCDInput() {
		return tfCCCD.getText().trim();
	}

	public String getTimKiemNVInput() {
		return tfTimNV.getText().trim();
	}

	public String getTK_TimInput() {
		return tfTK_Tim.getText().trim();
	}

	public String getTK_TenDangNhapSelected() {
		return cbTK_TenDangNhap.getSelectedItem() != null ? cbTK_TenDangNhap.getSelectedItem().toString() : "";
	}

	public String getTK_MatKhauInput() {
		return tfTK_MatKhau.getText().trim();
	}

	public String getTK_VaiTroSelected() {
		return cbTK_VaiTro.getSelectedItem().toString();
	}

	public String getTK_TrangThaiSelected() {
		return cbTK_TrangThai.getSelectedItem().toString();
	}

	public DefaultTableModel getTmNhanVien() {
		return tmNhanVien;
	}

	public DefaultTableModel getTmTaiKhoan() {
		return tmTaiKhoan;
	}

	public JTable getTbNhanVien() {
		return tbNhanVien;
	}

	public JTable getTbTaiKhoan() {
		return tbTaiKhoan;
	}

	public JComboBox<String> getCbTK_TenDangNhap() {
		return cbTK_TenDangNhap;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void dienThongTinNhanVienLenForm(String ma, String hoTen, String tenDN, String sdt, String cccd,
			String vaiTro) {
		tfMaNhanVien.setText(ma);
		tfHoTenNhanVien.setText(hoTen);
		tfTenDangNhap.setText(tenDN);
		tfSoDienThoai.setText(sdt);
		tfCCCD.setText(cccd);
		if (vaiTro != null && !vaiTro.equals("Chưa cấp")) {
			cbVaiTro.setSelectedItem(vaiTro);
		}
	}

	public void dienThongTinTaiKhoanLenForm(String tenDN, String matKhau, String vaiTro, String trangThai) {
		cbTK_TenDangNhap.setSelectedItem(tenDN);
		tfTK_MatKhau.setText(matKhau);
		cbTK_VaiTro.setSelectedItem(vaiTro);
		cbTK_TrangThai.setSelectedItem(trangThai);
	}

	public void xoaTrangFormNhanVien() {
		tfMaNhanVien.setText("");
		tfHoTenNhanVien.setText("");
		tfTenDangNhap.setText("");
		tfSoDienThoai.setText("");
		tfCCCD.setText("");
		cbVaiTro.setSelectedIndex(0);
		tfMaNhanVien.requestFocus();
	}

	public void xoaTrangFormTaiKhoan() {
		if (cbTK_TenDangNhap.getItemCount() > 0)
			cbTK_TenDangNhap.setSelectedIndex(0);
		tfTK_MatKhau.setText("");
		cbTK_VaiTro.setSelectedIndex(0);
		cbTK_TrangThai.setSelectedIndex(0);
	}

	public String getVaiTroNVSelected() {
		return cbVaiTro.getSelectedItem() != null ? cbVaiTro.getSelectedItem().toString() : "Thu ngân";
	}

}