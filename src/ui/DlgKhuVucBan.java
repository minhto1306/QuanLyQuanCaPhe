package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

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
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;

import entity.Ban;

public class DlgKhuVucBan extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;
	private JPanel tabSoDoBan, tabKhuVuc, pnSoDoBanRight;

	private JComboBox<String> cbKhuVuc;
	private JButton btnThemKhuVucNhanh, btnXoa, btnTaoTuDong, btnSDB_Luu, btnSDB_Thoat;
	private JTextField tfKyHieu, tfChieuDaiSo, tfTuSo, tfDenSo;

	private JButton btnKV_Them, btnKV_Xoa, btnKV_XoaTrang, btnKV_Sua, btnKV_Luu, btnKV_Tim;
	private JLabel lbKV_Ma, lbKV_Ten, lbKV_PhuThu, lbKV_Tim;
	private JTextField tfKV_Ma, tfKV_Ten, tfKV_PhuThu, tfKV_Tim;
	private Box boxKV_Row1, boxKV_Row2, boxKV_Tim;
	private JTable tbKhuVuc;
	private DefaultTableModel tmKhuVuc;

	public DlgKhuVucBan(JFrame parent) {
		super(parent, "SƠ ĐỒ KHU VỰC - BÀN/PHÒNG", true);
		this.setSize(950, 650);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		tabbedPane = new JTabbedPane();
		tabSoDoBan = khoiTaoTabSoDoBan();
		tabKhuVuc = khoiTaoTabKhuVuc();

		ImageIcon iconSoDo = taoIconThuNho("/images/floor-plan.png", 24, 24);
		ImageIcon iconKhuVuc = taoIconThuNho("/images/zone.png", 24, 24);

		tabbedPane.addTab("Sơ đồ bàn/phòng", iconSoDo, tabSoDoBan);
		tabbedPane.addTab("Khu vực", iconKhuVuc, tabKhuVuc);

		this.add(tabbedPane);
		khoiTaoSuKien();
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

	private JPanel khoiTaoTabSoDoBan() {
		JPanel pnSoDo = new JPanel(new BorderLayout());
		pnSoDo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel pnTopWrapper = new JPanel(new BorderLayout());

		JPanel pnLeft = new JPanel(new BorderLayout());
		pnLeft.setPreferredSize(new Dimension(300, 0));

		Border marginBorder = BorderFactory.createEmptyBorder(0, 0, 0, 10);
		Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
		Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		pnLeft.setBorder(BorderFactory.createCompoundBorder(marginBorder,
				BorderFactory.createCompoundBorder(lineBorder, paddingBorder)));

		JPanel pnLeftInner = new JPanel();
		pnLeftInner.setLayout(new BoxLayout(pnLeftInner, BoxLayout.Y_AXIS));

		Dimension dLabel = new Dimension(110, 25);
		Dimension dInput = new Dimension(150, 25);
		Font fontLabel = new Font("Arial", Font.BOLD, 15);

		Box boxKhuVuc = Box.createHorizontalBox();
		JLabel lbKhuVuc = new JLabel("Khu vực");
		lbKhuVuc.setPreferredSize(dLabel);
		lbKhuVuc.setFont(fontLabel);
		cbKhuVuc = new JComboBox<String>();
		cbKhuVuc.setPreferredSize(new Dimension(110, 25));
		cbKhuVuc.setMaximumSize(new Dimension(110, 25));

		btnThemKhuVucNhanh = new JButton();
		btnThemKhuVucNhanh.setPreferredSize(new Dimension(35, 35));
		btnThemKhuVucNhanh.setMaximumSize(new Dimension(40, 40));
		btnThemKhuVucNhanh.setBorderPainted(false);
		btnThemKhuVucNhanh.setContentAreaFilled(false);
		btnThemKhuVucNhanh.setFocusPainted(false);
		btnThemKhuVucNhanh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ImageIcon iconThem = taoIconThuNho("/images/plus.png", 32, 32);
		if (iconThem != null) {
			btnThemKhuVucNhanh.setIcon(iconThem);
		} else {
			btnThemKhuVucNhanh.setText("+");
			btnThemKhuVucNhanh.setBorderPainted(true);
			btnThemKhuVucNhanh.setContentAreaFilled(true);
		}

		boxKhuVuc.add(lbKhuVuc);
		boxKhuVuc.add(cbKhuVuc);
		boxKhuVuc.add(Box.createHorizontalStrut(5));
		boxKhuVuc.add(btnThemKhuVucNhanh);
		boxKhuVuc.add(Box.createHorizontalGlue());

		Box boxKyHieu = Box.createHorizontalBox();
		JLabel lbKyHieu = new JLabel("Ký hiệu");
		lbKyHieu.setPreferredSize(dLabel);
		lbKyHieu.setFont(fontLabel);
		tfKyHieu = new JTextField();
		tfKyHieu.setPreferredSize(dInput);
		tfKyHieu.setMaximumSize(dInput);
		boxKyHieu.add(lbKyHieu);
		boxKyHieu.add(tfKyHieu);
		boxKyHieu.add(Box.createHorizontalGlue());

		Box boxChieuDaiSo = Box.createHorizontalBox();
		JLabel lbChieuDaiSo = new JLabel("Chiều dài số");
		lbChieuDaiSo.setPreferredSize(dLabel);
		lbChieuDaiSo.setFont(fontLabel);
		tfChieuDaiSo = new JTextField("2");
		tfChieuDaiSo.setPreferredSize(dInput);
		tfChieuDaiSo.setMaximumSize(dInput);
		boxChieuDaiSo.add(lbChieuDaiSo);
		boxChieuDaiSo.add(tfChieuDaiSo);
		boxChieuDaiSo.add(Box.createHorizontalGlue());

		Box boxTuSo = Box.createHorizontalBox();
		JLabel lbTuSo = new JLabel("Từ số");
		lbTuSo.setPreferredSize(dLabel);
		lbTuSo.setFont(fontLabel);
		tfTuSo = new JTextField("1");
		tfTuSo.setPreferredSize(new Dimension(60, 25));
		tfTuSo.setMaximumSize(new Dimension(60, 25));
		JLabel lbTilde = new JLabel(" ~ ");
		lbTilde.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 6));
		tfDenSo = new JTextField("20");
		tfDenSo.setPreferredSize(new Dimension(60, 25));
		tfDenSo.setMaximumSize(new Dimension(60, 25));
		boxTuSo.add(lbTuSo);
		boxTuSo.add(tfTuSo);
		boxTuSo.add(lbTilde);
		boxTuSo.add(tfDenSo);
		boxTuSo.add(Box.createHorizontalGlue());

		Box boxButtons = Box.createHorizontalBox();

		Font fontBtn = new Font("Arial", Font.BOLD, 14);
		btnXoa = new JButton("Xóa");
		btnXoa.setFont(fontBtn);
		ImageIcon iconXoa = taoIconThuNho("/images/delete.png", 20, 20);
		if (iconXoa != null)
			btnXoa.setIcon(iconXoa);

		btnTaoTuDong = new JButton("Tạo tự động");
		btnTaoTuDong.setFont(fontBtn);
		ImageIcon iconTao = taoIconThuNho("/images/gear.png", 20, 20);
		if (iconTao != null)
			btnTaoTuDong.setIcon(iconTao);

		boxButtons.add(btnXoa);
		boxButtons.add(Box.createHorizontalStrut(10));
		boxButtons.add(btnTaoTuDong);
		boxButtons.add(Box.createHorizontalGlue());

		pnLeftInner.add(boxKhuVuc);
		pnLeftInner.add(Box.createVerticalStrut(12));
		pnLeftInner.add(boxKyHieu);
		pnLeftInner.add(Box.createVerticalStrut(12));
		pnLeftInner.add(boxChieuDaiSo);
		pnLeftInner.add(Box.createVerticalStrut(12));
		pnLeftInner.add(boxTuSo);
		pnLeftInner.add(Box.createVerticalStrut(20));
		pnLeftInner.add(boxButtons);

		pnLeft.add(pnLeftInner, BorderLayout.NORTH);

		pnSoDoBanRight = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnSoDoBanRight.setBackground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane(pnSoDoBanRight);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Sơ đồ",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

		pnTopWrapper.add(pnLeft, BorderLayout.WEST);
		pnTopWrapper.add(scrollPane, BorderLayout.CENTER);

		pnSoDo.add(pnTopWrapper, BorderLayout.CENTER);

		JPanel pnBottomSoDo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		pnBottomSoDo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0),
				BorderFactory.createLineBorder(Color.GRAY)));

		btnSDB_Luu = new JButton("Lưu");
		btnSDB_Luu.setFont(fontBtn);
		ImageIcon iconLuu = taoIconThuNho("/images/save.png", 20, 20);
		if (iconLuu != null)
			btnSDB_Luu.setIcon(iconLuu);

		btnSDB_Thoat = new JButton("Đóng");
		btnSDB_Thoat.setFont(fontBtn);
		ImageIcon iconDong = taoIconThuNho("/images/close.png", 20, 20);
		if (iconDong != null)
			btnSDB_Thoat.setIcon(iconDong);

		pnBottomSoDo.add(btnSDB_Luu);
		pnBottomSoDo.add(btnSDB_Thoat);

		pnSoDo.add(pnBottomSoDo, BorderLayout.SOUTH);

		return pnSoDo;
	}

	private JPanel khoiTaoTabKhuVuc() {
		JPanel pnDanhMuc = new JPanel();
		pnDanhMuc.setLayout(new BorderLayout());
		pnDanhMuc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		boxKV_Row1 = Box.createHorizontalBox();
		boxKV_Row2 = Box.createHorizontalBox();
		boxKV_Tim = Box.createHorizontalBox();

		lbKV_Tim = new JLabel("Tìm khu vực:");
		lbKV_Ma = new JLabel("Mã khu vực:");
		lbKV_Ten = new JLabel("Tên khu vực:");
		lbKV_PhuThu = new JLabel("Phụ thu:");

		Dimension dLabel = new Dimension(120, 25);
		lbKV_Ma.setPreferredSize(dLabel);
		lbKV_Ten.setPreferredSize(dLabel);
		lbKV_PhuThu.setPreferredSize(dLabel);
		lbKV_Tim.setPreferredSize(dLabel);

		Font fontLablel = new Font("Arial", Font.BOLD, 15);
		lbKV_Tim.setFont(fontLablel);
		lbKV_Ma.setFont(fontLablel);
		lbKV_Ten.setFont(fontLablel);
		lbKV_PhuThu.setFont(fontLablel);

		tfKV_Tim = new JTextField();
		tfKV_Tim.setPreferredSize(new Dimension(300, 25));

		tfKV_Ma = new JTextField();
		tfKV_Ten = new JTextField();
		tfKV_PhuThu = new JTextField();

		Dimension dInput = new Dimension(250, 25);
		tfKV_Ma.setPreferredSize(dInput);
		tfKV_Ma.setMaximumSize(dInput);
		tfKV_Ten.setPreferredSize(dInput);
		tfKV_Ten.setMaximumSize(dInput);
		tfKV_PhuThu.setPreferredSize(dInput);
		tfKV_PhuThu.setMaximumSize(dInput);

		btnKV_Tim = new JButton();
		btnKV_Tim.setPreferredSize(new Dimension(40, 25));
		ImageIcon iconTim = taoIconThuNho("/images/search.png", 16, 16);
		if (iconTim != null) {
			btnKV_Tim.setIcon(iconTim);
		} else {
			btnKV_Tim.setText("Tìm");
		}

		JPanel pnNhapThongTin = new JPanel();
		pnDanhMuc.add(pnNhapThongTin, BorderLayout.NORTH);
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.Y_AXIS));

		Border brdVienDen = BorderFactory.createLineBorder(Color.black);
		Border brdThutVao = BorderFactory.createEmptyBorder(15, 10, 15, 10);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));

		boxKV_Row1.add(lbKV_Ma);
		boxKV_Row1.add(tfKV_Ma);
		boxKV_Row1.add(Box.createHorizontalStrut(80));
		boxKV_Row1.add(lbKV_Ten);
		boxKV_Row1.add(tfKV_Ten);
		boxKV_Row1.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxKV_Row1);

		pnNhapThongTin.add(Box.createVerticalStrut(15));

		boxKV_Row2.add(lbKV_PhuThu);
		boxKV_Row2.add(tfKV_PhuThu);
		boxKV_Row2.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxKV_Row2);

		JPanel pnBangDuLieu = new JPanel();
		pnBangDuLieu.setLayout(new BorderLayout());
		pnDanhMuc.add(pnBangDuLieu, BorderLayout.CENTER);
		pnBangDuLieu
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0), brdVienDen));

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		boxKV_Tim.add(lbKV_Tim);
		boxKV_Tim.add(tfKV_Tim);
		boxKV_Tim.add(Box.createHorizontalStrut(5));
		boxKV_Tim.add(btnKV_Tim);
		pnTimKiem.add(boxKV_Tim);

		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "#", "Mã khu vực", "Tên khu vực", "Phụ thu" };
		tmKhuVuc = new DefaultTableModel(colNames, 0);
		tbKhuVuc = new JTable(tmKhuVuc);
		tbKhuVuc.getTableHeader().setFont(fontLablel);
		tbKhuVuc.setFont(new Font("Arial", Font.PLAIN, 14));
		tbKhuVuc.setRowHeight(25);

		tbKhuVuc.getColumnModel().getColumn(0).setMaxWidth(40);
		tbKhuVuc.getColumnModel().getColumn(1).setPreferredWidth(150);

		JScrollPane scrollPane = new JScrollPane(tbKhuVuc);
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);

		JPanel pnNutChucNang = new JPanel();
		pnDanhMuc.add(pnNutChucNang, BorderLayout.SOUTH);
		pnNutChucNang.setBorder(brdVienDen);
		pnNutChucNang.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

		Font fontBtn = new Font("Arial", Font.BOLD, 14);

		btnKV_Them = new JButton("Thêm");
		btnKV_Them.setFont(fontBtn);
		ImageIcon iconThem = taoIconThuNho("/images/add.png", 20, 20);
		if (iconThem != null)
			btnKV_Them.setIcon(iconThem);

		btnKV_Xoa = new JButton("Xóa");
		btnKV_Xoa.setFont(fontBtn);
		ImageIcon iconXoa = taoIconThuNho("/images/delete.png", 20, 20);
		if (iconXoa != null)
			btnKV_Xoa.setIcon(iconXoa);

		btnKV_XoaTrang = new JButton("Xóa trắng");
		btnKV_XoaTrang.setFont(fontBtn);
		ImageIcon iconXoaTrang = taoIconThuNho("/images/clear.png", 20, 20);
		if (iconXoaTrang != null)
			btnKV_XoaTrang.setIcon(iconXoaTrang);

		btnKV_Sua = new JButton("Sửa");
		btnKV_Sua.setFont(fontBtn);
		ImageIcon iconSua = taoIconThuNho("/images/edit.png", 20, 20);
		if (iconSua != null)
			btnKV_Sua.setIcon(iconSua);

		btnKV_Luu = new JButton("Lưu");
		btnKV_Luu.setFont(fontBtn);
		ImageIcon iconLuu = taoIconThuNho("/images/save.png", 20, 20);
		if (iconLuu != null)
			btnKV_Luu.setIcon(iconLuu);

		pnNutChucNang.add(btnKV_Them);
		pnNutChucNang.add(btnKV_Xoa);
		pnNutChucNang.add(btnKV_XoaTrang);
		pnNutChucNang.add(btnKV_Sua);
		pnNutChucNang.add(btnKV_Luu);

		tbKhuVuc.getTableHeader().setResizingAllowed(false);
		tbKhuVuc.getTableHeader().setReorderingAllowed(false);

		return pnDanhMuc;
	}

	public void hienThiDanhSachBan(List<Ban> danhSachBan) {
		pnSoDoBanRight.removeAll();
		for (Ban ban : danhSachBan) {
			JToggleButton btnBan = new JToggleButton(ban.getTenBan());
			btnBan.setPreferredSize(new Dimension(80, 80));
			btnBan.setFont(new Font("Arial", Font.BOLD, 16));
			btnBan.setFocusPainted(false);

			if (ban.isTrangThai()) {
				btnBan.setBackground(Color.RED);
			} else {
				btnBan.setBackground(Color.WHITE);
			}

			pnSoDoBanRight.add(btnBan);
		}
		pnSoDoBanRight.revalidate();
		pnSoDoBanRight.repaint();
	}

	private void khoiTaoSuKien() {
		btnThemKhuVucNhanh.addActionListener(e -> {
			DlgThemKhuVuc dlgThem = new DlgThemKhuVuc(this);
			dlgThem.setVisible(true);
		});

		btnSDB_Thoat.addActionListener(e -> this.dispose());
	}

	public static void main(String[] args) {
		try {
			FlatLightLaf.setup();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new DlgKhuVucBan(null).setVisible(true);
	}
}