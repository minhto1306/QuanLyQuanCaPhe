package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.InputStream;
import java.text.DecimalFormat;

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

public class DlgHangHoa extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;
	private JPanel tabHangHoa;
	private JPanel tabDanhMuc;
	private JPanel pnHangHoa;
	private JPanel pnDanhMuc;

	private JButton btnThem;
	private JButton btnXoa;
	private JButton btnXoaTrang;
	private JButton btnSua;
	private JButton btnLuu;
	private JButton btnTim;
	private JButton btnThemDanhMuc;
	private JButton btnChonAnh;
	private JButton btnXoaAnh;

	private JLabel lbMaSanPham, lbTenSanPham, lbGiaBan, lbDanhMuc, lbTrangThai, lbTimSP, lbHinhAnhText, lbHinhAnh;
	private JComboBox<String> cbDanhMuc, cbTrangThai;
	private JTextField tfMaSanPham, tfTenSanPham, tfGiaBan, tfTimSP;

	private JTable tbSanPham;
	private DefaultTableModel tmSanPham;

	private JButton btnDM_Them, btnDM_Xoa, btnDM_XoaTrang, btnDM_Sua, btnDM_Luu, btnDM_Tim;
	private JLabel lbDM_Ma, lbDM_Ten, lbDM_Tim;
	private JTextField tfDM_Ma, tfDM_Ten, tfDM_Tim;
	private Box boxDM_Row1, boxDM_Tim;

	private JTable tbDanhMuc;
	private DefaultTableModel tmDanhMuc;

	private final Color MAU_NEN_CHINH = new Color(242, 233, 216);
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47);
	private final Color MAU_HEADER = new Color(209, 185, 161);
	private Font fontBungeeBase;

	// CHỨC NĂNG: Khởi tạo giao diện quản lý hàng hóa và danh mục.
	public DlgHangHoa(JFrame parent) {
		super(parent, "HÀNG HÓA VÀ DANH MỤC", true);
		this.setSize(950, 750);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		loadCustomFont();

		ImageIcon iconHangHoa = taoIconThuNho("/images/item.png", 24, 24);
		ImageIcon iconDanhMuc = taoIconThuNho("/images/procurement.png", 24, 24);

		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
		tabHangHoa = khoiTaoTabHangHoa();
		tabDanhMuc = khoiTaoTabDanhMuc();

		tabbedPane.addTab("THÔNG TIN HÀNG HÓA", iconHangHoa, tabHangHoa, "Xem thông tin hàng hóa");
		tabbedPane.addTab("THÔNG TIN DANH MỤC", iconDanhMuc, tabDanhMuc, "Xem thông tin danh mục");

		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(MAU_NAU_VIEN);
		container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		container.add(tabbedPane, BorderLayout.CENTER);
		this.setContentPane(container);
	}

	// CHỨC NĂNG: Tải font chữ tùy chỉnh cho giao diện.
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

	// CHỨC NĂNG: Xử lý thu nhỏ kích thước hình ảnh.
	private ImageIcon taoIconThuNho(String duongDan, int width, int height) {
		java.net.URL url = getClass().getResource(duongDan);
		if (url == null)
			return null;
		return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	// CHỨC NĂNG: Thiết lập định dạng chuẩn cho các thành phần nhập liệu.
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

	// CHỨC NĂNG: Thiết lập định dạng chuẩn cho các nút bấm.
	private void setupRetroButton(JButton btn) {
		btn.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 16f));
		btn.setBackground(MAU_NEN_CHINH);
		btn.setForeground(MAU_NAU_VIEN);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
	}

	// CHỨC NĂNG: Khởi tạo và thiết lập giao diện cho tab Hàng hóa.
	private JPanel khoiTaoTabHangHoa() {
		pnHangHoa = new JPanel(new BorderLayout());
		pnHangHoa.setBackground(MAU_NEN_CHINH);
		pnHangHoa.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		lbTimSP = new JLabel("TÌM SẢN PHẨM:");
		lbMaSanPham = new JLabel("MÃ SẢN PHẨM:");
		lbTenSanPham = new JLabel("TÊN SẢN PHẨM:");
		lbGiaBan = new JLabel("GIÁ BÁN:");
		lbTrangThai = new JLabel("TRẠNG THÁI:");
		lbDanhMuc = new JLabel("DANH MỤC:");
		lbHinhAnhText = new JLabel("HÌNH ẢNH:");

		Dimension dLeftLabel = new Dimension(160, 25);
		lbMaSanPham.setPreferredSize(dLeftLabel);
		lbTenSanPham.setPreferredSize(dLeftLabel);
		lbGiaBan.setPreferredSize(dLeftLabel);
		lbTrangThai.setPreferredSize(dLeftLabel);

		Dimension dRightLabel = new Dimension(110, 25);
		lbDanhMuc.setPreferredSize(dRightLabel);
		lbHinhAnhText.setPreferredSize(dRightLabel);
		lbTimSP.setPreferredSize(new Dimension(180, 25));

		Font fontLabelRetro = fontBungeeBase.deriveFont(Font.PLAIN, 18f);
		JLabel[] labels = { lbTimSP, lbMaSanPham, lbTenSanPham, lbGiaBan, lbTrangThai, lbDanhMuc, lbHinhAnhText };
		for (JLabel lbl : labels) {
			lbl.setFont(fontLabelRetro);
			lbl.setForeground(MAU_NAU_VIEN);
		}

		tfTimSP = new JTextField();
		tfTimSP.setPreferredSize(new Dimension(300, 30));
		setupRetroInput(tfTimSP);

		tfMaSanPham = new JTextField();
		tfTenSanPham = new JTextField();
		tfGiaBan = new JTextField();

		cbTrangThai = new JComboBox<>();
		cbTrangThai.addItem("Đang bán");
		cbTrangThai.addItem("Ngừng bán");

		Dimension dInputTrai = new Dimension(250, 30);
		tfMaSanPham.setPreferredSize(dInputTrai);
		tfMaSanPham.setMaximumSize(dInputTrai);
		tfTenSanPham.setPreferredSize(dInputTrai);
		tfTenSanPham.setMaximumSize(dInputTrai);
		tfGiaBan.setPreferredSize(dInputTrai);
		tfGiaBan.setMaximumSize(dInputTrai);
		cbTrangThai.setPreferredSize(dInputTrai);
		cbTrangThai.setMaximumSize(dInputTrai);

		setupRetroInput(tfMaSanPham);
		setupRetroInput(tfTenSanPham);
		setupRetroInput(tfGiaBan);
		setupRetroInput(cbTrangThai);

		cbDanhMuc = new JComboBox<>();
		cbDanhMuc.setPreferredSize(new Dimension(170, 30));
		cbDanhMuc.setMaximumSize(new Dimension(170, 30));
		setupRetroInput(cbDanhMuc);

		btnThemDanhMuc = new JButton(taoIconThuNho("/images/plus.png", 24, 24));
		btnThemDanhMuc.setPreferredSize(new Dimension(40, 40));
		btnThemDanhMuc.setMaximumSize(new Dimension(40, 40));
		btnThemDanhMuc.setBackground(MAU_NEN_CHINH);
		btnThemDanhMuc.setFocusPainted(false);
		btnThemDanhMuc.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));
		btnThemDanhMuc.setCursor(new Cursor(Cursor.HAND_CURSOR));

		lbHinhAnh = new JLabel();
		lbHinhAnh.setPreferredSize(new Dimension(120, 120));
		lbHinhAnh.setMaximumSize(new Dimension(120, 120));
		lbHinhAnh.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3));
		lbHinhAnh.setOpaque(true);
		lbHinhAnh.setBackground(Color.WHITE);
		lbHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);

		btnChonAnh = new JButton("BROWSE");
		btnXoaAnh = new JButton("XÓA");
		setupRetroButton(btnChonAnh);
		setupRetroButton(btnXoaAnh);

		btnTim = new JButton(taoIconThuNho("/images/search.png", 20, 20));
		btnTim.setPreferredSize(new Dimension(40, 30));
		btnTim.setBackground(MAU_NEN_CHINH);
		btnTim.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		JPanel pnNhapThongTin = new JPanel();
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.X_AXIS));
		pnNhapThongTin.setBackground(MAU_NEN_CHINH);
		Border brdVienDen = BorderFactory.createLineBorder(MAU_NAU_VIEN, 4);
		Border brdThutVao = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));
		pnHangHoa.add(pnNhapThongTin, BorderLayout.NORTH);

		JPanel pnTrai = new JPanel();
		pnTrai.setLayout(new BoxLayout(pnTrai, BoxLayout.Y_AXIS));
		pnTrai.setOpaque(false);

		Box boxTrai1 = Box.createHorizontalBox();
		boxTrai1.add(lbMaSanPham);
		boxTrai1.add(tfMaSanPham);
		boxTrai1.add(Box.createHorizontalGlue());
		Box boxTrai2 = Box.createHorizontalBox();
		boxTrai2.add(lbTenSanPham);
		boxTrai2.add(tfTenSanPham);
		boxTrai2.add(Box.createHorizontalGlue());
		Box boxTrai3 = Box.createHorizontalBox();
		boxTrai3.add(lbGiaBan);
		boxTrai3.add(tfGiaBan);
		boxTrai3.add(Box.createHorizontalGlue());
		Box boxTrai4 = Box.createHorizontalBox();
		boxTrai4.add(lbTrangThai);
		boxTrai4.add(cbTrangThai);
		boxTrai4.add(Box.createHorizontalGlue());

		pnTrai.add(boxTrai1);
		pnTrai.add(Box.createVerticalStrut(20));
		pnTrai.add(boxTrai2);
		pnTrai.add(Box.createVerticalStrut(20));
		pnTrai.add(boxTrai3);
		pnTrai.add(Box.createVerticalStrut(20));
		pnTrai.add(boxTrai4);

		JPanel pnPhai = new JPanel();
		pnPhai.setLayout(new BoxLayout(pnPhai, BoxLayout.Y_AXIS));
		pnPhai.setOpaque(false);

		Box boxPhai1 = Box.createHorizontalBox();
		boxPhai1.add(lbDanhMuc);
		boxPhai1.add(cbDanhMuc);
		boxPhai1.add(Box.createHorizontalStrut(5));
		boxPhai1.add(btnThemDanhMuc);
		boxPhai1.add(Box.createHorizontalGlue());

		Box boxPhai2 = Box.createHorizontalBox();
		boxPhai2.add(lbHinhAnhText);
		boxPhai2.add(lbHinhAnh);
		boxPhai2.add(Box.createHorizontalStrut(15));
		Box boxAnhBtns = Box.createVerticalBox();
		boxAnhBtns.add(btnChonAnh);
		boxAnhBtns.add(Box.createVerticalStrut(10));
		boxAnhBtns.add(btnXoaAnh);
		boxPhai2.add(boxAnhBtns);
		boxPhai2.add(Box.createHorizontalGlue());

		pnPhai.add(boxPhai1);
		pnPhai.add(Box.createVerticalStrut(20));
		pnPhai.add(boxPhai2);
		pnPhai.add(Box.createVerticalGlue());

		pnNhapThongTin.add(pnTrai);
		pnNhapThongTin.add(Box.createHorizontalStrut(50));
		pnNhapThongTin.add(pnPhai);

		JPanel pnBangDuLieu = new JPanel(new BorderLayout());
		pnBangDuLieu.setBackground(MAU_NEN_CHINH);
		pnBangDuLieu.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), brdVienDen));
		pnHangHoa.add(pnBangDuLieu, BorderLayout.CENTER);

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnTimKiem.setBackground(MAU_HEADER);
		pnTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, MAU_NAU_VIEN));
		Box boxTimSP = Box.createHorizontalBox();
		boxTimSP.add(lbTimSP);
		boxTimSP.add(tfTimSP);
		boxTimSP.add(Box.createHorizontalStrut(10));
		boxTimSP.add(btnTim);
		pnTimKiem.add(boxTimSP);
		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "Mã SP", "Tên sản phẩm", "Danh mục", "Giá bán", "Trạng thái" };
		tmSanPham = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbSanPham = new JTable(tmSanPham);

		JTableHeader header = tbSanPham.getTableHeader();
		header.setFont(new Font("Tahoma", Font.BOLD, 16));
		header.setBackground(MAU_HEADER);
		header.setForeground(MAU_NAU_VIEN);
		header.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		tbSanPham.setFont(new Font("Tahoma", Font.BOLD, 14));
		tbSanPham.setRowHeight(35);
		tbSanPham.setBackground(MAU_NEN_CHINH);
		tbSanPham.setGridColor(MAU_NAU_VIEN);
		tbSanPham.setShowGrid(true);

		tbSanPham.getColumnModel().getColumn(0).setPreferredWidth(100);
		tbSanPham.getColumnModel().getColumn(1).setPreferredWidth(250);
		tbSanPham.getColumnModel().getColumn(2).setPreferredWidth(120);
		tbSanPham.getColumnModel().getColumn(3).setPreferredWidth(100);
		tbSanPham.getColumnModel().getColumn(4).setPreferredWidth(100);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tbSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbSanPham.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		tbSanPham.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			private DecimalFormat df = new DecimalFormat("#,### VNĐ");

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof Number) {
					value = df.format(((Number) value).doubleValue());
				} else if (value instanceof String) {
					try {
						value = df.format(Double.parseDouble((String) value));
					} catch (Exception ex) {
					}
				}
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setHorizontalAlignment(SwingConstants.RIGHT);
				return c;
			}
		});

		tbSanPham.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setHorizontalAlignment(SwingConstants.CENTER);
				if (value != null) {
					String status = value.toString();
					if (status.equalsIgnoreCase("Ngừng bán")) {
						c.setForeground(Color.RED);
					} else if (status.equalsIgnoreCase("Đang bán")) {
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

		JScrollPane scrollPane = new JScrollPane(tbSanPham);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);

		JPanel pnNutChucNang = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		pnNutChucNang.setBackground(MAU_NEN_CHINH);
		pnNutChucNang.setBorder(brdVienDen);
		pnHangHoa.add(pnNutChucNang, BorderLayout.SOUTH);

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

		tbSanPham.getTableHeader().setResizingAllowed(false);
		tbSanPham.getTableHeader().setReorderingAllowed(false);

		return pnHangHoa;
	}

	// CHỨC NĂNG: Khởi tạo và thiết lập giao diện cho tab Danh mục.
	private JPanel khoiTaoTabDanhMuc() {
		pnDanhMuc = new JPanel(new BorderLayout());
		pnDanhMuc.setBackground(MAU_NEN_CHINH);
		pnDanhMuc.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		boxDM_Row1 = Box.createHorizontalBox();
		boxDM_Tim = Box.createHorizontalBox();

		lbDM_Tim = new JLabel("TÌM DANH MỤC:");
		lbDM_Ma = new JLabel("MÃ DANH MỤC:");
		lbDM_Ten = new JLabel("TÊN DANH MỤC:");

		Dimension dLabel = new Dimension(150, 25);
		lbDM_Ma.setPreferredSize(dLabel);
		lbDM_Ten.setPreferredSize(dLabel);
		lbDM_Tim.setPreferredSize(dLabel);

		Font fontLabelRetro = fontBungeeBase.deriveFont(Font.PLAIN, 18f);
		lbDM_Tim.setFont(fontLabelRetro);
		lbDM_Tim.setForeground(MAU_NAU_VIEN);
		lbDM_Ma.setFont(fontLabelRetro);
		lbDM_Ma.setForeground(MAU_NAU_VIEN);
		lbDM_Ten.setFont(fontLabelRetro);
		lbDM_Ten.setForeground(MAU_NAU_VIEN);

		tfDM_Tim = new JTextField();
		tfDM_Tim.setPreferredSize(new Dimension(350, 30));
		setupRetroInput(tfDM_Tim);

		tfDM_Ma = new JTextField();
		tfDM_Ten = new JTextField();

		Dimension dInput = new Dimension(280, 30);
		tfDM_Ma.setPreferredSize(dInput);
		tfDM_Ma.setMaximumSize(dInput);
		tfDM_Ten.setPreferredSize(dInput);
		tfDM_Ten.setMaximumSize(dInput);
		setupRetroInput(tfDM_Ma);
		setupRetroInput(tfDM_Ten);

		btnDM_Tim = new JButton(taoIconThuNho("/images/search.png", 20, 20));
		btnDM_Tim.setPreferredSize(new Dimension(40, 30));
		btnDM_Tim.setBackground(MAU_NEN_CHINH);
		btnDM_Tim.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		JPanel pnNhapThongTin = new JPanel();
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.Y_AXIS));
		pnNhapThongTin.setBackground(MAU_NEN_CHINH);
		Border brdVien = BorderFactory.createLineBorder(MAU_NAU_VIEN, 4);
		Border brdThutVao = BorderFactory.createEmptyBorder(30, 30, 30, 30);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVien, brdThutVao));
		pnDanhMuc.add(pnNhapThongTin, BorderLayout.NORTH);

		boxDM_Row1.add(lbDM_Ma);
		boxDM_Row1.add(tfDM_Ma);
		boxDM_Row1.add(Box.createHorizontalStrut(50));
		boxDM_Row1.add(lbDM_Ten);
		boxDM_Row1.add(tfDM_Ten);
		boxDM_Row1.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxDM_Row1);

		JPanel pnBangDuLieu = new JPanel(new BorderLayout());
		pnBangDuLieu.setBackground(MAU_NEN_CHINH);
		pnBangDuLieu
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), brdVien));
		pnDanhMuc.add(pnBangDuLieu, BorderLayout.CENTER);

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnTimKiem.setBackground(MAU_HEADER);
		pnTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, MAU_NAU_VIEN));
		boxDM_Tim.add(lbDM_Tim);
		boxDM_Tim.add(tfDM_Tim);
		boxDM_Tim.add(Box.createHorizontalStrut(10));
		boxDM_Tim.add(btnDM_Tim);
		pnTimKiem.add(boxDM_Tim);
		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "Mã danh mục", "Tên danh mục" };
		tmDanhMuc = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbDanhMuc = new JTable(tmDanhMuc);

		tbDanhMuc.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
		tbDanhMuc.getTableHeader().setBackground(MAU_HEADER);
		tbDanhMuc.getTableHeader().setForeground(MAU_NAU_VIEN);
		tbDanhMuc.getTableHeader().setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		tbDanhMuc.setFont(new Font("Tahoma", Font.BOLD, 14));
		tbDanhMuc.setRowHeight(35);
		tbDanhMuc.setBackground(MAU_NEN_CHINH);
		tbDanhMuc.setGridColor(MAU_NAU_VIEN);
		tbDanhMuc.setShowGrid(true);

		JScrollPane scrollPane = new JScrollPane(tbDanhMuc);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);

		JPanel pnNutChucNang = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		pnNutChucNang.setBorder(brdVien);
		pnNutChucNang.setBackground(MAU_NEN_CHINH);
		pnDanhMuc.add(pnNutChucNang, BorderLayout.SOUTH);

		btnDM_Them = new JButton(" THÊM", taoIconThuNho("/images/add.png", 20, 20));
		btnDM_Xoa = new JButton(" XÓA", taoIconThuNho("/images/delete.png", 20, 20));
		btnDM_XoaTrang = new JButton(" XÓA TRẮNG", taoIconThuNho("/images/clear.png", 20, 20));
		btnDM_Sua = new JButton(" SỬA", taoIconThuNho("/images/edit.png", 20, 20));
		btnDM_Luu = new JButton(" LƯU", taoIconThuNho("/images/save.png", 20, 20));
		btnDM_Luu.setEnabled(false);

		JButton[] btnsDM = { btnDM_Them, btnDM_Xoa, btnDM_XoaTrang, btnDM_Sua, btnDM_Luu };
		for (JButton b : btnsDM) {
			setupRetroButton(b);
			pnNutChucNang.add(b);
		}

		tbDanhMuc.getTableHeader().setResizingAllowed(false);
		tbDanhMuc.getTableHeader().setReorderingAllowed(false);

		return pnDanhMuc;
	}

	public void addBtnThemSPListener(ActionListener listener) {
		btnThem.addActionListener(listener);
	}

	public void addBtnXoaSPListener(ActionListener listener) {
		btnXoa.addActionListener(listener);
	}

	public void addBtnXoaTrangSPListener(ActionListener listener) {
		btnXoaTrang.addActionListener(listener);
	}

	public void addBtnSuaSPListener(ActionListener listener) {
		btnSua.addActionListener(listener);
	}

	public void addBtnLuuSPListener(ActionListener listener) {
		btnLuu.addActionListener(listener);
	}

	public void addBtnTimSPListener(ActionListener listener) {
		btnTim.addActionListener(listener);
	}

	public void addBtnThemDanhMucNhanhListener(ActionListener listener) {
		btnThemDanhMuc.addActionListener(listener);
	}

	public void addBtnChonAnhListener(ActionListener listener) {
		btnChonAnh.addActionListener(listener);
	}

	public void addBtnXoaAnhListener(ActionListener listener) {
		btnXoaAnh.addActionListener(listener);
	}

	public void addTbSanPhamMouseListener(MouseAdapter adapter) {
		tbSanPham.addMouseListener(adapter);
	}

	public void addHinhAnhDropTarget(DropTarget dt) {
		lbHinhAnh.setDropTarget(dt);
	}

	public void addBtnThemDMListener(ActionListener listener) {
		btnDM_Them.addActionListener(listener);
	}

	public void addBtnXoaDMListener(ActionListener listener) {
		btnDM_Xoa.addActionListener(listener);
	}

	public void addBtnXoaTrangDMListener(ActionListener listener) {
		btnDM_XoaTrang.addActionListener(listener);
	}

	public void addBtnSuaDMListener(ActionListener listener) {
		btnDM_Sua.addActionListener(listener);
	}

	public void addBtnLuuDMListener(ActionListener listener) {
		btnDM_Luu.addActionListener(listener);
	}

	public void addBtnTimDMListener(ActionListener listener) {
		btnDM_Tim.addActionListener(listener);
	}

	public void addTbDanhMucMouseListener(MouseAdapter adapter) {
		tbDanhMuc.addMouseListener(adapter);
	}

	public JTextField getTfMaSanPham() {
		return tfMaSanPham;
	}

	public JTextField getTfTenSanPham() {
		return tfTenSanPham;
	}

	public JTextField getTfGiaBan() {
		return tfGiaBan;
	}

	public JTextField getTfTimSP() {
		return tfTimSP;
	}

	public JComboBox<String> getCbDanhMuc() {
		return cbDanhMuc;
	}

	public JComboBox<String> getCbTrangThai() {
		return cbTrangThai;
	}

	public JLabel getLbHinhAnh() {
		return lbHinhAnh;
	}

	public JTable getTbSanPham() {
		return tbSanPham;
	}

	public DefaultTableModel getTmSanPham() {
		return tmSanPham;
	}

	public JTextField getTfDM_Ma() {
		return tfDM_Ma;
	}

	public JTextField getTfDM_Ten() {
		return tfDM_Ten;
	}

	public JTextField getTfDM_Tim() {
		return tfDM_Tim;
	}

	public JTable getTbDanhMuc() {
		return tbDanhMuc;
	}

	public DefaultTableModel getTmDanhMuc() {
		return tmDanhMuc;
	}

	// CHỨC NĂNG: Quản lý trạng thái bật/tắt các nút thao tác sản phẩm.
	public void batTatNutSanPham(boolean them, boolean xoa, boolean sua, boolean luu, boolean tfMaState) {
		btnThem.setEnabled(them);
		btnXoa.setEnabled(xoa);
		btnSua.setEnabled(sua);
		btnLuu.setEnabled(luu);
		tfMaSanPham.setEditable(tfMaState);
	}

	// CHỨC NĂNG: Quản lý trạng thái bật/tắt các nút thao tác danh mục.
	public void batTatNutDanhMuc(boolean them, boolean xoa, boolean sua, boolean luu, boolean tfMaState) {
		btnDM_Them.setEnabled(them);
		btnDM_Xoa.setEnabled(xoa);
		btnDM_Sua.setEnabled(sua);
		btnDM_Luu.setEnabled(luu);
		tfDM_Ma.setEditable(tfMaState);
	}
}