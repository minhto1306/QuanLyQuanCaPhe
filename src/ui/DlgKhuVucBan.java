package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DlgKhuVucBan extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;
	private JPanel tabSoDoBan;
	private JPanel tabKhuVuc;
	private JPanel pnSoDoBanRight;

	private JComboBox<String> cbKhuVuc;
	private JButton btnThemKhuVucNhanh;
	private JButton btnXoa;
	private JButton btnTaoTuDong;
	private JButton btnSDB_Luu;
	private JButton btnSDB_Thoat;
	private JTextField tfKyHieu;
	private JTextField tfChieuDaiSo;
	private JTextField tfTuSo;
	private JTextField tfDenSo;

	private JButton btnKV_Them;
	private JButton btnKV_Xoa;
	private JButton btnKV_XoaTrang;
	private JButton btnKV_Sua;
	private JButton btnKV_Luu;
	private JButton btnKV_Tim;
	private JLabel lbKV_Ma;
	private JLabel lbKV_Ten;
	private JLabel lbKV_PhuThu;
	private JLabel lbKV_Tim;
	private JTextField tfKV_Ma;
	private JTextField tfKV_Ten;
	private JTextField tfKV_PhuThu;
	private JTextField tfKV_Tim;
	private Box boxKV_Row1;
	private Box boxKV_Row2;
	private Box boxKV_Tim;
	private JTable tbKhuVuc;
	private DefaultTableModel tmKhuVuc;

	// BỘ CHÂN KHÍ MÀU SẮC ĐỒNG BỘ RETRO
	private final Color MAU_NEN_CHINH = new Color(242, 233, 216); // Vani
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47); // Nâu đậm
	private final Color MAU_HEADER = new Color(209, 185, 161); // Bạc xỉu
	private Font fontBungeeBase;

	public DlgKhuVucBan(JFrame parent) {
		super(parent, "SƠ ĐỒ KHU VỰC - BÀN/PHÒNG", true);
		this.setSize(1100, 750);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		loadCustomFont();

		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
		tabSoDoBan = khoiTaoTabSoDoBan();
		tabKhuVuc = khoiTaoTabKhuVuc();

		ImageIcon iconSoDo = taoIconThuNho("/images/floor-plan.png", 24, 24);
		ImageIcon iconKhuVuc = taoIconThuNho("/images/zone.png", 24, 24);

		tabbedPane.addTab("SƠ ĐỒ BÀN/PHÒNG", iconSoDo, tabSoDoBan);
		tabbedPane.addTab("KHU VỰC", iconKhuVuc, tabKhuVuc);

		// Nền tổng thể Nâu Đậm, lót thêm một lớp panel để tạo viền padding
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
		if (url == null) {
			return null;
		}
		ImageIcon iconGoc = new ImageIcon(url);
		Image imgGoc = iconGoc.getImage();
		Image imgThuNho = imgGoc.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(imgThuNho);
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

	// CHIÊU THỨC: Trang trí Nút Bấm Retro chuẩn bài
	private void setupRetroButton(JButton btn) {
		btn.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 16f));
		btn.setBackground(MAU_NEN_CHINH);
		btn.setForeground(MAU_NAU_VIEN);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private JPanel khoiTaoTabSoDoBan() {
		JPanel pnSoDo = new JPanel(new BorderLayout());
		pnSoDo.setBackground(MAU_NEN_CHINH);
		pnSoDo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel pnTopWrapper = new JPanel(new BorderLayout(10, 0));
		pnTopWrapper.setBackground(MAU_NEN_CHINH);

		JPanel pnLeft = new JPanel(new BorderLayout());
		pnLeft.setPreferredSize(new Dimension(380, 0));
		pnLeft.setBackground(MAU_NEN_CHINH);

		Border lineBorder = BorderFactory.createLineBorder(MAU_NAU_VIEN, 4);
		Border paddingBorder = BorderFactory.createEmptyBorder(15, 15, 15, 15);
		pnLeft.setBorder(BorderFactory.createCompoundBorder(lineBorder, paddingBorder));

		JPanel pnLeftInner = new JPanel();
		pnLeftInner.setLayout(new BoxLayout(pnLeftInner, BoxLayout.Y_AXIS));
		pnLeftInner.setBackground(MAU_NEN_CHINH);

		Font fontLabelRetro = fontBungeeBase.deriveFont(Font.PLAIN, 16f);
		Dimension dLabel = new Dimension(130, 25);

		Box boxKhuVuc = Box.createHorizontalBox();
		JLabel lbKhuVuc = new JLabel("KHU VỰC:");
		lbKhuVuc.setPreferredSize(dLabel);
		lbKhuVuc.setFont(fontLabelRetro);
		lbKhuVuc.setForeground(MAU_NAU_VIEN);
		
		cbKhuVuc = new JComboBox<String>();
		cbKhuVuc.setPreferredSize(new Dimension(130, 30));
		cbKhuVuc.setMaximumSize(new Dimension(130, 30));
		setupRetroInput(cbKhuVuc);

		btnThemKhuVucNhanh = new JButton();
		btnThemKhuVucNhanh.setPreferredSize(new Dimension(35, 30));
		btnThemKhuVucNhanh.setMaximumSize(new Dimension(35, 30));
		btnThemKhuVucNhanh.setBackground(MAU_NEN_CHINH);
		btnThemKhuVucNhanh.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));
		btnThemKhuVucNhanh.setFocusPainted(false);
		btnThemKhuVucNhanh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ImageIcon iconThem = taoIconThuNho("/images/plus.png", 20, 20);
		if (iconThem != null) btnThemKhuVucNhanh.setIcon(iconThem);

		boxKhuVuc.add(lbKhuVuc);
		boxKhuVuc.add(Box.createHorizontalStrut(15));
		boxKhuVuc.add(cbKhuVuc);
		boxKhuVuc.add(Box.createHorizontalStrut(5));
		boxKhuVuc.add(btnThemKhuVucNhanh);
		boxKhuVuc.add(Box.createHorizontalGlue());

		Box boxKyHieu = Box.createHorizontalBox();
		JLabel lbKyHieu = new JLabel("KÝ HIỆU:");
		lbKyHieu.setPreferredSize(dLabel);
		lbKyHieu.setFont(fontLabelRetro);
		lbKyHieu.setForeground(MAU_NAU_VIEN);
		tfKyHieu = new JTextField();
		tfKyHieu.setPreferredSize(new Dimension(170, 30));
		tfKyHieu.setMaximumSize(new Dimension(170, 30));
		setupRetroInput(tfKyHieu);
		boxKyHieu.add(lbKyHieu);
		boxKyHieu.add(Box.createHorizontalStrut(15));
		boxKyHieu.add(tfKyHieu);
		boxKyHieu.add(Box.createHorizontalGlue());

		Box boxChieuDaiSo = Box.createHorizontalBox();
		JLabel lbChieuDaiSo = new JLabel("CHIỀU DÀI SỐ:");
		lbChieuDaiSo.setPreferredSize(dLabel);
		lbChieuDaiSo.setFont(fontLabelRetro);
		lbChieuDaiSo.setForeground(MAU_NAU_VIEN);
		tfChieuDaiSo = new JTextField("2");
		tfChieuDaiSo.setPreferredSize(new Dimension(170, 30));
		tfChieuDaiSo.setMaximumSize(new Dimension(170, 30));
		setupRetroInput(tfChieuDaiSo);
		boxChieuDaiSo.add(lbChieuDaiSo);
		boxChieuDaiSo.add(Box.createHorizontalStrut(15));
		boxChieuDaiSo.add(tfChieuDaiSo);
		boxChieuDaiSo.add(Box.createHorizontalGlue());

		Box boxTuSo = Box.createHorizontalBox();
		JLabel lbTuSo = new JLabel("TỪ SỐ:");
		lbTuSo.setPreferredSize(dLabel);
		lbTuSo.setFont(fontLabelRetro);
		lbTuSo.setForeground(MAU_NAU_VIEN);
		tfTuSo = new JTextField("1");
		tfTuSo.setPreferredSize(new Dimension(65, 30));
		tfTuSo.setMaximumSize(new Dimension(65, 30));
		setupRetroInput(tfTuSo);
		
		JLabel lbTilde = new JLabel(" ~ ");
		lbTilde.setFont(fontLabelRetro);
		lbTilde.setForeground(MAU_NAU_VIEN);
		lbTilde.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		
		tfDenSo = new JTextField("20");
		tfDenSo.setPreferredSize(new Dimension(65, 30));
		tfDenSo.setMaximumSize(new Dimension(65, 30));
		setupRetroInput(tfDenSo);
		
		boxTuSo.add(lbTuSo);
		boxTuSo.add(Box.createHorizontalStrut(15));
		boxTuSo.add(tfTuSo);
		boxTuSo.add(lbTilde);
		boxTuSo.add(tfDenSo);
		boxTuSo.add(Box.createHorizontalGlue());

		Box boxButtons = Box.createHorizontalBox();
		btnXoa = new JButton(" XÓA", taoIconThuNho("/images/delete.png", 20, 20));
		setupRetroButton(btnXoa);
		
		btnTaoTuDong = new JButton(" TẠO TỰ ĐỘNG", taoIconThuNho("/images/gear.png", 20, 20));
		setupRetroButton(btnTaoTuDong);

		boxButtons.add(btnXoa);
		boxButtons.add(Box.createHorizontalStrut(15));
		boxButtons.add(btnTaoTuDong);
		boxButtons.add(Box.createHorizontalGlue());

		pnLeftInner.add(boxKhuVuc);
		pnLeftInner.add(Box.createVerticalStrut(20));
		pnLeftInner.add(boxKyHieu);
		pnLeftInner.add(Box.createVerticalStrut(20));
		pnLeftInner.add(boxChieuDaiSo);
		pnLeftInner.add(Box.createVerticalStrut(20));
		pnLeftInner.add(boxTuSo);
		pnLeftInner.add(Box.createVerticalStrut(30));
		pnLeftInner.add(boxButtons);

		pnLeft.add(pnLeftInner, BorderLayout.NORTH);

		pnSoDoBanRight = new JPanel();
		pnSoDoBanRight.setLayout(new BoxLayout(pnSoDoBanRight, BoxLayout.Y_AXIS));
		pnSoDoBanRight.setBackground(MAU_NEN_CHINH); // Nhuộm màu nền

		JScrollPane scrollPane = new JScrollPane(pnSoDoBanRight);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 4), "SƠ ĐỒ TRỰC QUAN",
				TitledBorder.LEFT, TitledBorder.TOP, fontBungeeBase.deriveFont(Font.PLAIN, 16f), MAU_NAU_VIEN));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBackground(MAU_NEN_CHINH);
		scrollPane.getViewport().setBackground(MAU_NEN_CHINH);

		pnTopWrapper.add(pnLeft, BorderLayout.WEST);
		pnTopWrapper.add(scrollPane, BorderLayout.CENTER);

		pnSoDo.add(pnTopWrapper, BorderLayout.CENTER);

		JPanel pnBottomSoDo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		pnBottomSoDo.setBackground(MAU_NEN_CHINH);
		pnBottomSoDo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0),
				BorderFactory.createLineBorder(MAU_NAU_VIEN, 4)));

		btnSDB_Luu = new JButton(" LƯU", taoIconThuNho("/images/save.png", 20, 20));
		setupRetroButton(btnSDB_Luu);

		btnSDB_Thoat = new JButton(" ĐÓNG", taoIconThuNho("/images/close.png", 20, 20));
		setupRetroButton(btnSDB_Thoat);

		pnBottomSoDo.add(btnSDB_Luu);
		pnBottomSoDo.add(btnSDB_Thoat);

		pnSoDo.add(pnBottomSoDo, BorderLayout.SOUTH);

		return pnSoDo;
	}

	private JPanel khoiTaoTabKhuVuc() {
		JPanel pnDanhMuc = new JPanel(new BorderLayout());
		pnDanhMuc.setBackground(MAU_NEN_CHINH);
		pnDanhMuc.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		boxKV_Row1 = Box.createHorizontalBox();
		boxKV_Row2 = Box.createHorizontalBox();
		boxKV_Tim = Box.createHorizontalBox();

		lbKV_Tim = new JLabel("TÌM KHU VỰC:");
		lbKV_Ma = new JLabel("MÃ KHU VỰC:");
		lbKV_Ten = new JLabel("TÊN KHU VỰC:");
		lbKV_PhuThu = new JLabel("PHỤ THU:");

		Dimension dLabel = new Dimension(160, 25);
		Font fontLabelRetro = fontBungeeBase.deriveFont(Font.PLAIN, 18f);

		JLabel[] labels = { lbKV_Ma, lbKV_Ten, lbKV_PhuThu, lbKV_Tim };
		for(JLabel lbl : labels) {
			lbl.setPreferredSize(dLabel);
			lbl.setFont(fontLabelRetro);
			lbl.setForeground(MAU_NAU_VIEN);
		}

		tfKV_Tim = new JTextField();
		tfKV_Tim.setPreferredSize(new Dimension(350, 30));
		setupRetroInput(tfKV_Tim);

		tfKV_Ma = new JTextField();
		tfKV_Ten = new JTextField();
		tfKV_PhuThu = new JTextField();

		Dimension dInput = new Dimension(280, 30);
		JTextField[] tfs = { tfKV_Ma, tfKV_Ten, tfKV_PhuThu };
		for(JTextField tf : tfs) {
			tf.setPreferredSize(dInput);
			tf.setMaximumSize(dInput);
			setupRetroInput(tf);
		}

		btnKV_Tim = new JButton(taoIconThuNho("/images/search.png", 20, 20));
		btnKV_Tim.setPreferredSize(new Dimension(40, 30));
		btnKV_Tim.setBackground(MAU_NEN_CHINH);
		btnKV_Tim.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		JPanel pnNhapThongTin = new JPanel();
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.Y_AXIS));
		pnNhapThongTin.setBackground(MAU_NEN_CHINH);
		Border brdVienDen = BorderFactory.createLineBorder(MAU_NAU_VIEN, 4);
		Border brdThutVao = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));
		pnDanhMuc.add(pnNhapThongTin, BorderLayout.NORTH);

		// Thêm chân khí khoảng cách
		boxKV_Row1.add(lbKV_Ma);
		boxKV_Row1.add(Box.createHorizontalStrut(15));
		boxKV_Row1.add(tfKV_Ma);
		boxKV_Row1.add(Box.createHorizontalStrut(50));
		boxKV_Row1.add(lbKV_Ten);
		boxKV_Row1.add(Box.createHorizontalStrut(15));
		boxKV_Row1.add(tfKV_Ten);
		boxKV_Row1.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxKV_Row1);

		pnNhapThongTin.add(Box.createVerticalStrut(20));

		boxKV_Row2.add(lbKV_PhuThu);
		boxKV_Row2.add(Box.createHorizontalStrut(15));
		boxKV_Row2.add(tfKV_PhuThu);
		boxKV_Row2.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxKV_Row2);

		JPanel pnBangDuLieu = new JPanel(new BorderLayout());
		pnBangDuLieu.setBackground(MAU_NEN_CHINH);
		pnBangDuLieu.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), brdVienDen));
		pnDanhMuc.add(pnBangDuLieu, BorderLayout.CENTER);

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnTimKiem.setBackground(MAU_HEADER);
		pnTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, MAU_NAU_VIEN));
		boxKV_Tim.add(lbKV_Tim);
		boxKV_Tim.add(Box.createHorizontalStrut(15));
		boxKV_Tim.add(tfKV_Tim);
		boxKV_Tim.add(Box.createHorizontalStrut(15));
		boxKV_Tim.add(btnKV_Tim);
		pnTimKiem.add(boxKV_Tim);
		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "#", "Mã khu vực", "Tên khu vực", "Phụ thu" };
		tmKhuVuc = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbKhuVuc = new JTable(tmKhuVuc);
		
		JTableHeader header = tbKhuVuc.getTableHeader();
		header.setFont(new Font("Tahoma", Font.BOLD, 16));
		header.setBackground(MAU_HEADER);
		header.setForeground(MAU_NAU_VIEN);
		header.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		tbKhuVuc.setFont(new Font("Tahoma", Font.BOLD, 14));
		tbKhuVuc.setRowHeight(35);
		tbKhuVuc.setBackground(MAU_NEN_CHINH);
		tbKhuVuc.setGridColor(MAU_NAU_VIEN);
		tbKhuVuc.setShowGrid(true);

		tbKhuVuc.getColumnModel().getColumn(0).setMaxWidth(40);
		tbKhuVuc.getColumnModel().getColumn(1).setPreferredWidth(150);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tbKhuVuc.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		JScrollPane scrollPane = new JScrollPane(tbKhuVuc);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);

		JPanel pnNutChucNang = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		pnNutChucNang.setBackground(MAU_NEN_CHINH);
		pnNutChucNang.setBorder(brdVienDen);
		pnDanhMuc.add(pnNutChucNang, BorderLayout.SOUTH);

		btnKV_Them = new JButton(" THÊM", taoIconThuNho("/images/add.png", 20, 20));
		btnKV_Xoa = new JButton(" XÓA", taoIconThuNho("/images/delete.png", 20, 20));
		btnKV_XoaTrang = new JButton(" XÓA TRẮNG", taoIconThuNho("/images/clear.png", 20, 20));
		btnKV_Sua = new JButton(" SỬA", taoIconThuNho("/images/edit.png", 20, 20));
		btnKV_Luu = new JButton(" LƯU", taoIconThuNho("/images/save.png", 20, 20));
		btnKV_Luu.setEnabled(false);

		JButton[] btnsKV = { btnKV_Them, btnKV_Xoa, btnKV_XoaTrang, btnKV_Sua, btnKV_Luu };
		for (JButton b : btnsKV) {
			setupRetroButton(b);
			pnNutChucNang.add(b);
		}

		tbKhuVuc.getTableHeader().setResizingAllowed(false);
		tbKhuVuc.getTableHeader().setReorderingAllowed(false);

		return pnDanhMuc;
	}

	public void hienThiDanhSachBanToanBo(java.util.Map<String, java.util.List<entity.Ban>> mapKhuVucBan) {
		pnSoDoBanRight.removeAll();
		pnSoDoBanRight.setLayout(new BoxLayout(pnSoDoBanRight, BoxLayout.Y_AXIS));
		pnSoDoBanRight.setBackground(MAU_NEN_CHINH); // Cập nhật nền Retro

		for (java.util.Map.Entry<String, java.util.List<entity.Ban>> entry : mapKhuVucBan.entrySet()) {
			String tenKhuVuc = entry.getKey();
			java.util.List<entity.Ban> danhSachBan = entry.getValue();

			JPanel pnTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnTitle.setBackground(MAU_NEN_CHINH);
			pnTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
			pnTitle.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

			JLabel lblKhuVuc = new JLabel(tenKhuVuc);
			lblKhuVuc.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 22f));
			lblKhuVuc.setForeground(MAU_NAU_VIEN); // Cập nhật màu Retro
			pnTitle.add(lblKhuVuc);

			JPanel pnTables = new JPanel(new WrapLayout(FlowLayout.LEFT, 15, 15));
			pnTables.setBackground(MAU_NEN_CHINH);
			pnTables.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

			for (entity.Ban ban : danhSachBan) {
				JToggleButton btnBan = new JToggleButton();
				btnBan.setText(
						"<html><div style='text-align: center; width: 80px;'>" + ban.getTenBan() + "</div></html>");

				btnBan.setActionCommand(ban.getMaBan());
				btnBan.setPreferredSize(new Dimension(100, 100));
				btnBan.setFont(new Font("Arial", Font.BOLD, 16));
				btnBan.setFocusPainted(false);
				btnBan.setMargin(new java.awt.Insets(2, 2, 2, 2));
				
				// Nhuộm chân khí cho bàn
				btnBan.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3), 
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));

				if (ban.getTrangThai() != null && ban.getTrangThai().equalsIgnoreCase("Có khách")) {
					btnBan.setBackground(new Color(193, 71, 71)); // Đỏ mộc mạc Retro
					btnBan.setForeground(Color.WHITE);
				} else {
					btnBan.setBackground(MAU_HEADER); // Màu Bạc xỉu chờ khách
					btnBan.setForeground(MAU_NAU_VIEN);
				}
				pnTables.add(btnBan);
			}

			pnSoDoBanRight.add(pnTitle);
			pnSoDoBanRight.add(pnTables);
			pnSoDoBanRight.add(Box.createVerticalStrut(20));
		}

		pnSoDoBanRight.revalidate();
		pnSoDoBanRight.repaint();
	}

	public java.util.List<String> getDanhSachMaBanDuocChon() {
		java.util.List<String> listMaBan = new java.util.ArrayList<>();
		for (java.awt.Component compKhuVuc : pnSoDoBanRight.getComponents()) {
			if (compKhuVuc instanceof JPanel) {
				JPanel pn = (JPanel) compKhuVuc;
				if (pn.getLayout() instanceof WrapLayout) {
					for (java.awt.Component compBan : pn.getComponents()) {
						if (compBan instanceof JToggleButton) {
							JToggleButton btn = (JToggleButton) compBan;
							if (btn.isSelected()) {
								listMaBan.add(btn.getActionCommand());
							}
						}
					}
				}
			}
		}
		return listMaBan;
	}

	public void loadDataToComboBoxKhuVuc(java.util.List<entity.KhuVuc> listKhuVuc) {
		cbKhuVuc.removeAllItems();
		cbKhuVuc.addItem("--- Chọn khu vực ---");
		for (entity.KhuVuc kv : listKhuVuc) {
			cbKhuVuc.addItem(kv.getTenKhuVuc());
		}
	}

	public String getKV_TimInput() {
		return tfKV_Tim.getText().trim();
	}

	public void addTimKhuVucListener(java.awt.event.ActionListener listener) {
		btnKV_Tim.addActionListener(listener);
	}

	public String getKhuVucDuocChon() {
		if (cbKhuVuc.getSelectedItem() != null) {
			return cbKhuVuc.getSelectedItem().toString();
		}
		return null;
	}

	public String getTuSo() {
		return tfTuSo.getText().trim();
	}

	public String getDenSo() {
		return tfDenSo.getText().trim();
	}

	public void addKhuVucComboBoxListener(java.awt.event.ActionListener listener) {
		cbKhuVuc.addActionListener(listener);
	}

	public void addXoaBanListener(java.awt.event.ActionListener listener) {
		btnXoa.addActionListener(listener);
	}

	public void addTaoTuDongListener(java.awt.event.ActionListener listener) {
		btnTaoTuDong.addActionListener(listener);
	}

	public void addThoatSoDoListener(java.awt.event.ActionListener listener) {
		btnSDB_Thoat.addActionListener(listener);
	}

	public String getMaKhuVuc() {
		return tfKV_Ma.getText().trim();
	}

	public String getTenKhuVuc() {
		return tfKV_Ten.getText().trim();
	}

	public String getPhuThu() {
		return tfKV_PhuThu.getText().trim();
	}

	public void setMaKhuVuc(String ma) {
		tfKV_Ma.setText(ma);
	}

	public void setTenKhuVuc(String ten) {
		tfKV_Ten.setText(ten);
	}

	public void setPhuThu(String phuThu) {
		tfKV_PhuThu.setText(phuThu);
	}

	public DefaultTableModel getKhuVucTableModel() {
		return tmKhuVuc;
	}

	public JTable getKhuVucTable() {
		return tbKhuVuc;
	}

	public void addThemKhuVucListener(java.awt.event.ActionListener listener) {
		btnKV_Them.addActionListener(listener);
	}

	public void addXoaKhuVucListener(java.awt.event.ActionListener listener) {
		btnKV_Xoa.addActionListener(listener);
	}

	public void addSuaKhuVucListener(java.awt.event.ActionListener listener) {
		btnKV_Sua.addActionListener(listener);
	}

	public void addLuuKhuVucListener(java.awt.event.ActionListener listener) {
		btnKV_Luu.addActionListener(listener);
	}

	public void addXoaTrangKhuVucListener(java.awt.event.ActionListener listener) {
		btnKV_XoaTrang.addActionListener(listener);
	}

	public void addThemKhuVucNhanhListener(java.awt.event.ActionListener listener) {
		btnThemKhuVucNhanh.addActionListener(listener);
	}

	public int getSelectedRow() {
		return tbKhuVuc.getSelectedRow();
	}

	public void addTableSelectionListener(javax.swing.event.ListSelectionListener listener) {
		tbKhuVuc.getSelectionModel().addListSelectionListener(listener);
	}

	public void showMessage(String msg, String title, int type) {
		javax.swing.JOptionPane.showMessageDialog(this, msg, title, type);
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
			while (parent != null && !(parent instanceof javax.swing.JViewport)) {
				parent = parent.getParent();
			}
			if (parent == null)
				return defaultDim;
			int targetWidth = parent.getSize().width;
			if (targetWidth == 0)
				return defaultDim;
			int hgap = getHgap(), vgap = getVgap();
			java.awt.Insets insets = target.getInsets();
			int maxWidth = targetWidth - (insets.left + insets.right + hgap * 2);
			int dimWidth = 0, dimHeight = 0, rowWidth = 0, rowHeight = 0;
			for (int i = 0; i < target.getComponentCount(); i++) {
				java.awt.Component m = target.getComponent(i);
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
			dimWidth += insets.left + insets.right + hgap * 2;
			dimHeight += insets.top + insets.bottom + vgap * 2;
			return new Dimension(dimWidth, dimHeight);
		}
	}
}