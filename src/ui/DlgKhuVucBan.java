package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

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

	public DlgKhuVucBan(JFrame parent) {
		super(parent, "SƠ ĐỒ KHU VỰC - BÀN/PHÒNG", true);
		this.setSize(1000, 650);
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

		Box boxSDB_Tim = Box.createHorizontalBox();

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

		pnLeftInner.add(boxSDB_Tim);
		pnLeftInner.add(Box.createVerticalStrut(12));
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

		pnSoDoBanRight = new JPanel();
		pnSoDoBanRight.setLayout(new BoxLayout(pnSoDoBanRight, BoxLayout.Y_AXIS));
		pnSoDoBanRight.setBackground(Color.WHITE);

		JScrollPane scrollPane = new JScrollPane(pnSoDoBanRight);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Sơ đồ",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

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

	public void hienThiDanhSachBanToanBo(java.util.Map<String, java.util.List<entity.Ban>> mapKhuVucBan) {
		pnSoDoBanRight.removeAll();
		pnSoDoBanRight.setLayout(new BoxLayout(pnSoDoBanRight, BoxLayout.Y_AXIS));
		pnSoDoBanRight.setBackground(Color.WHITE);

		for (java.util.Map.Entry<String, java.util.List<entity.Ban>> entry : mapKhuVucBan.entrySet()) {
			String tenKhuVuc = entry.getKey();
			java.util.List<entity.Ban> danhSachBan = entry.getValue();

			JPanel pnTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pnTitle.setBackground(Color.WHITE);
			pnTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
			pnTitle.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

			JLabel lblKhuVuc = new JLabel(tenKhuVuc);
			lblKhuVuc.setFont(new Font("Arial", Font.BOLD, 22));
			lblKhuVuc.setForeground(new Color(220, 53, 69));
			pnTitle.add(lblKhuVuc);

			JPanel pnTables = new JPanel(new WrapLayout(FlowLayout.LEFT, 15, 15));
			pnTables.setBackground(Color.WHITE);
			pnTables.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

			for (entity.Ban ban : danhSachBan) {
				JToggleButton btnBan = new JToggleButton();
				btnBan.setText(
						"<html><div style='text-align: center; width: 80px;'>" + ban.getTenBan() + "</div></html>");

				btnBan.setActionCommand(ban.getMaBan());

				btnBan.setPreferredSize(new Dimension(100, 100));
				btnBan.setFont(new Font("Arial", Font.BOLD, 15));
				btnBan.setFocusPainted(false);
				btnBan.setMargin(new java.awt.Insets(2, 2, 2, 2));

				if (ban.isTrangThai()) {
					btnBan.setBackground(Color.RED);
					btnBan.setForeground(Color.WHITE);
				} else {
					btnBan.setBackground(new Color(240, 240, 240));
					btnBan.setForeground(Color.BLACK);
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