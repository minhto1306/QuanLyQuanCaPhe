package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

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
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class DlgNhanVien extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;
	private JPanel tabNhanVien, tabTaiKhoan, pnNhanVien, pnTaiKhoan;

	private JButton btnThem, btnXoa, btnXoaTrang, btnSua, btnLuu, btnTim;
	private JLabel lbMaNhanVien, lbHoTenNhanVien, lbSoDienThoai, lbCCCD, lbTenDangNhap, lbVaiTro, lbTimNV;
	private JComboBox<String> cbVaiTro;
	private JTextField tfMaNhanVien, tfHoTenNhanVien, tfSoDienThoai, tfCCCD, tfTenDangNhap, tfTimNV;
	private Box boxRow1, boxRow2, boxRow3, boxTimNV;
	private JTable tbNhanVien;
	private DefaultTableModel tmNhanVien;

	private JButton btnTK_Them, btnTK_Xoa, btnTK_XoaTrang, btnTK_Sua, btnTK_Luu, btnTK_Tim;
	private JLabel lbTK_TenDangNhap, lbTK_MatKhau, lbTK_TrangThai, lbTK_VaiTro, lbTK_Tim;
	private JComboBox<String> cbTK_TenDangNhap, cbTK_VaiTro, cbTK_TrangThai;
	private JTextField tfTK_MatKhau, tfTK_Tim;
	private Box boxTK_Row1, boxTK_Row2, boxTK_Tim;
	private JTable tbTaiKhoan;
	private DefaultTableModel tmTaiKhoan;

	public DlgNhanVien(JFrame parent) {
		super(parent, "NHÂN VIÊN", true);
		this.setSize(900, 700);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		ImageIcon iconNhanVien = taoIconThuNho("/images/barista.png", 24, 24);
		ImageIcon iconTaiKhoan = taoIconThuNho("/images/profile.png", 24, 24);

		tabbedPane = new JTabbedPane();
		tabNhanVien = khoiTaoTabNhanVien();
		tabTaiKhoan = khoiTaoTabTaiKhoan();
		tabbedPane.addTab("THÔNG TIN NHÂN VIÊN", iconNhanVien, tabNhanVien, "Xem thông tin nhân viên");
		tabbedPane.addTab("THÔNG TIN TÀI KHOẢN", iconTaiKhoan, tabTaiKhoan, "Xem thông tin tài khoản");

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

	public JPanel khoiTaoTabNhanVien() {
		pnNhanVien = new JPanel();
		pnNhanVien.setLayout(new BorderLayout());

		boxRow1 = Box.createHorizontalBox();
		boxRow2 = Box.createHorizontalBox();
		boxRow3 = Box.createHorizontalBox();
		boxTimNV = Box.createHorizontalBox();

		lbTimNV = new JLabel("Tìm nhân viên:");
		lbMaNhanVien = new JLabel("Mã nhân viên:");
		lbHoTenNhanVien = new JLabel("Họ & tên:");
		lbSoDienThoai = new JLabel("Số điện thoại:");

		lbTenDangNhap = new JLabel("Tên đăng nhập:");
		lbCCCD = new JLabel("CCCD:");
		lbVaiTro = new JLabel("Vai trò:");

		Dimension dLeftLabel = new Dimension(110, 25);
		lbMaNhanVien.setPreferredSize(dLeftLabel);
		lbHoTenNhanVien.setPreferredSize(dLeftLabel);
		lbSoDienThoai.setPreferredSize(dLeftLabel);
		Dimension dRightLabel = new Dimension(130, 25);
		lbTenDangNhap.setPreferredSize(dRightLabel);
		lbCCCD.setPreferredSize(dRightLabel);
		lbVaiTro.setPreferredSize(dRightLabel);

		lbTimNV.setPreferredSize(new Dimension(130, 25));

		Font fontLablel = new Font("Arial", Font.BOLD, 15);
		lbTimNV.setFont(fontLablel);
		lbMaNhanVien.setFont(fontLablel);
		lbHoTenNhanVien.setFont(fontLablel);
		lbTenDangNhap.setFont(fontLablel);
		lbSoDienThoai.setFont(fontLablel);
		lbCCCD.setFont(fontLablel);
		lbVaiTro.setFont(fontLablel);

		tfTimNV = new JTextField();
		tfTimNV.setPreferredSize(new Dimension(300, 25));

		tfMaNhanVien = new JTextField();
		tfHoTenNhanVien = new JTextField();
		tfSoDienThoai = new JTextField();
		tfTenDangNhap = new JTextField();
		tfCCCD = new JTextField();
		cbVaiTro = new JComboBox<String>();
		cbVaiTro.addItem("Quản lý");
		cbVaiTro.addItem("Thu ngân");

		Dimension dInput = new Dimension(250, 25);
		tfMaNhanVien.setPreferredSize(dInput);
		tfMaNhanVien.setMaximumSize(dInput);
		tfHoTenNhanVien.setPreferredSize(dInput);
		tfHoTenNhanVien.setMaximumSize(dInput);
		tfSoDienThoai.setPreferredSize(dInput);
		tfSoDienThoai.setMaximumSize(dInput);

		tfTenDangNhap.setPreferredSize(dInput);
		tfTenDangNhap.setMaximumSize(dInput);
		tfCCCD.setPreferredSize(dInput);
		tfCCCD.setMaximumSize(dInput);
		cbVaiTro.setPreferredSize(dInput);
		cbVaiTro.setMaximumSize(dInput);

		btnTim = new JButton();
		btnTim.setPreferredSize(new Dimension(40, 25));
		ImageIcon iconTim = taoIconThuNho("/images/search.png", 16, 16);
		if (iconTim != null) {
			btnTim.setIcon(iconTim);
		} else {
			btnTim.setText("Tìm");
		}

		JPanel pnNhapThongTin = new JPanel();
		pnNhanVien.add(pnNhapThongTin, BorderLayout.NORTH);
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.Y_AXIS));

		Border brdVienDen = BorderFactory.createLineBorder(Color.black);
		Border brdThutVao = BorderFactory.createEmptyBorder(15, 10, 15, 10);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));

		boxRow1.add(lbMaNhanVien);
		boxRow1.add(tfMaNhanVien);
		boxRow1.add(Box.createHorizontalStrut(50));
		boxRow1.add(lbTenDangNhap);
		boxRow1.add(tfTenDangNhap);
		boxRow1.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxRow1);
		pnNhapThongTin.add(Box.createVerticalStrut(15));

		boxRow2.add(lbHoTenNhanVien);
		boxRow2.add(tfHoTenNhanVien);
		boxRow2.add(Box.createHorizontalStrut(50));
		boxRow2.add(lbCCCD);
		boxRow2.add(tfCCCD);
		boxRow2.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxRow2);
		pnNhapThongTin.add(Box.createVerticalStrut(15));

		boxRow3.add(lbSoDienThoai);
		boxRow3.add(tfSoDienThoai);
		boxRow3.add(Box.createHorizontalStrut(50));
		boxRow3.add(lbVaiTro);
		boxRow3.add(cbVaiTro);
		boxRow3.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxRow3);

		JPanel pnBangDuLieu = new JPanel();
		pnBangDuLieu.setLayout(new BorderLayout());
		pnNhanVien.add(pnBangDuLieu, BorderLayout.CENTER);
		pnBangDuLieu
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0), brdVienDen));

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		boxTimNV.add(lbTimNV);
		boxTimNV.add(tfTimNV);
		boxTimNV.add(Box.createHorizontalStrut(5));
		boxTimNV.add(btnTim);
		pnTimKiem.add(boxTimNV);
		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "#", "Mã nhân viên", "Họ & tên", "Tên đăng nhập", "Số điện thoại", "CCCD", "Vai trò" };
		tmNhanVien = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbNhanVien = new JTable(tmNhanVien);
		tbNhanVien.getTableHeader().setFont(fontLablel);
		tbNhanVien.setFont(new Font("Arial", Font.PLAIN, 14));
		tbNhanVien.setRowHeight(25);

		tbNhanVien.getColumnModel().getColumn(0).setMaxWidth(40);
		tbNhanVien.getColumnModel().getColumn(1).setPreferredWidth(100);
		tbNhanVien.getColumnModel().getColumn(3).setPreferredWidth(120);
		JScrollPane scrollPane = new JScrollPane(tbNhanVien);
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);

		JPanel pnNutChucNang = new JPanel();
		pnNhanVien.add(pnNutChucNang, BorderLayout.SOUTH);
		pnNutChucNang.setBorder(brdVienDen);
		pnNutChucNang.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		Font fontBtn = new Font("Arial", Font.BOLD, 14);

		btnThem = new JButton("Thêm");
		btnThem.setFont(fontBtn);
		ImageIcon iconThem = taoIconThuNho("/images/add.png", 20, 20);
		if (iconThem != null)
			btnThem.setIcon(iconThem);

		btnXoa = new JButton("Xóa");
		btnXoa.setFont(fontBtn);
		ImageIcon iconXoa = taoIconThuNho("/images/delete.png", 20, 20);
		if (iconXoa != null)
			btnXoa.setIcon(iconXoa);

		btnXoaTrang = new JButton("Xóa trắng");
		btnXoaTrang.setFont(fontBtn);
		ImageIcon iconXoaTrang = taoIconThuNho("/images/clear.png", 20, 20);
		if (iconXoaTrang != null)
			btnXoaTrang.setIcon(iconXoaTrang);

		btnSua = new JButton("Sửa");
		btnSua.setFont(fontBtn);
		ImageIcon iconSua = taoIconThuNho("/images/edit.png", 20, 20);
		if (iconSua != null)
			btnSua.setIcon(iconSua);

		btnLuu = new JButton("Lưu");
		btnLuu.setFont(fontBtn);
		ImageIcon iconLuu = taoIconThuNho("/images/save.png", 20, 20);
		if (iconLuu != null)
			btnLuu.setIcon(iconLuu);

		pnNutChucNang.add(btnThem);
		pnNutChucNang.add(btnXoa);
		pnNutChucNang.add(btnXoaTrang);
		pnNutChucNang.add(btnSua);
		pnNutChucNang.add(btnLuu);

		tbNhanVien.getTableHeader().setResizingAllowed(false);
		tbNhanVien.getTableHeader().setReorderingAllowed(false);

		return pnNhanVien;
	}

	public JPanel khoiTaoTabTaiKhoan() {
		pnTaiKhoan = new JPanel();
		pnTaiKhoan.setLayout(new BorderLayout());

		boxTK_Row1 = Box.createHorizontalBox();
		boxTK_Row2 = Box.createHorizontalBox();
		boxTK_Tim = Box.createHorizontalBox();
		lbTK_Tim = new JLabel("Tìm tài khoản:");
		lbTK_TenDangNhap = new JLabel("Tên đăng nhập:");
		lbTK_VaiTro = new JLabel("Vai trò:");
		lbTK_MatKhau = new JLabel("Mật khẩu:");
		lbTK_TrangThai = new JLabel("Trạng thái:");

		Dimension dLabel = new Dimension(130, 25);
		lbTK_TenDangNhap.setPreferredSize(dLabel);
		lbTK_VaiTro.setPreferredSize(dLabel);
		lbTK_MatKhau.setPreferredSize(dLabel);
		lbTK_TrangThai.setPreferredSize(dLabel);
		lbTK_Tim.setPreferredSize(dLabel);

		Font fontLablel = new Font("Arial", Font.BOLD, 15);
		lbTK_Tim.setFont(fontLablel);
		lbTK_TenDangNhap.setFont(fontLablel);
		lbTK_MatKhau.setFont(fontLablel);
		lbTK_VaiTro.setFont(fontLablel);
		lbTK_TrangThai.setFont(fontLablel);

		tfTK_Tim = new JTextField();
		tfTK_Tim.setPreferredSize(new Dimension(300, 25));
		cbTK_TenDangNhap = new JComboBox<String>();

		tfTK_MatKhau = new JTextField();

		cbTK_VaiTro = new JComboBox<String>();
		cbTK_VaiTro.addItem("Quản lý");
		cbTK_VaiTro.addItem("Thu ngân");

		cbTK_TrangThai = new JComboBox<String>();
		cbTK_TrangThai.addItem("Hoạt động");
		cbTK_TrangThai.addItem("Khóa");

		Dimension dInput = new Dimension(250, 25);
		cbTK_TenDangNhap.setPreferredSize(dInput);
		cbTK_TenDangNhap.setMaximumSize(dInput);
		tfTK_MatKhau.setPreferredSize(dInput);
		tfTK_MatKhau.setMaximumSize(dInput);
		cbTK_VaiTro.setPreferredSize(dInput);
		cbTK_VaiTro.setMaximumSize(dInput);
		cbTK_TrangThai.setPreferredSize(dInput);
		cbTK_TrangThai.setMaximumSize(dInput);
		btnTK_Tim = new JButton();
		btnTK_Tim.setPreferredSize(new Dimension(40, 25));
		ImageIcon iconTim = taoIconThuNho("/images/search.png", 16, 16);
		if (iconTim != null) {
			btnTK_Tim.setIcon(iconTim);
		} else {
			btnTK_Tim.setText("Tìm");
		}

		JPanel pnNhapThongTin = new JPanel();
		pnTaiKhoan.add(pnNhapThongTin, BorderLayout.NORTH);
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.Y_AXIS));

		Border brdVienDen = BorderFactory.createLineBorder(Color.black);
		Border brdThutVao = BorderFactory.createEmptyBorder(15, 10, 15, 10);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));

		boxTK_Row1.add(lbTK_TenDangNhap);
		boxTK_Row1.add(cbTK_TenDangNhap);
		boxTK_Row1.add(Box.createHorizontalStrut(80));
		boxTK_Row1.add(lbTK_MatKhau);
		boxTK_Row1.add(tfTK_MatKhau);
		boxTK_Row1.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxTK_Row1);
		pnNhapThongTin.add(Box.createVerticalStrut(15));

		boxTK_Row2.add(lbTK_VaiTro);
		boxTK_Row2.add(cbTK_VaiTro);
		boxTK_Row2.add(Box.createHorizontalStrut(80));
		boxTK_Row2.add(lbTK_TrangThai);
		boxTK_Row2.add(cbTK_TrangThai);
		boxTK_Row2.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxTK_Row2);

		JPanel pnBangDuLieu = new JPanel();
		pnBangDuLieu.setLayout(new BorderLayout());
		pnTaiKhoan.add(pnBangDuLieu, BorderLayout.CENTER);
		pnBangDuLieu
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0), brdVienDen));
		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		boxTK_Tim.add(lbTK_Tim);
		boxTK_Tim.add(tfTK_Tim);
		boxTK_Tim.add(Box.createHorizontalStrut(5));
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
		tbTaiKhoan.getTableHeader().setFont(fontLablel);
		tbTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 14));
		tbTaiKhoan.setRowHeight(25);

		tbTaiKhoan.getColumnModel().getColumn(0).setMaxWidth(40);

		JScrollPane scrollPane = new JScrollPane(tbTaiKhoan);
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);
		JPanel pnNutChucNang = new JPanel();
		pnTaiKhoan.add(pnNutChucNang, BorderLayout.SOUTH);
		pnNutChucNang.setBorder(brdVienDen);
		pnNutChucNang.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

		Font fontBtn = new Font("Arial", Font.BOLD, 14);
		btnTK_Them = new JButton("Thêm");
		btnTK_Them.setFont(fontBtn);
		ImageIcon iconThem = taoIconThuNho("/images/add.png", 20, 20);
		if (iconThem != null)
			btnTK_Them.setIcon(iconThem);

		btnTK_Xoa = new JButton("Xóa");
		btnTK_Xoa.setFont(fontBtn);
		ImageIcon iconXoa = taoIconThuNho("/images/delete.png", 20, 20);
		if (iconXoa != null)
			btnTK_Xoa.setIcon(iconXoa);

		btnTK_XoaTrang = new JButton("Xóa trắng");
		btnTK_XoaTrang.setFont(fontBtn);
		ImageIcon iconXoaTrang = taoIconThuNho("/images/clear.png", 20, 20);
		if (iconXoaTrang != null)
			btnTK_XoaTrang.setIcon(iconXoaTrang);

		btnTK_Sua = new JButton("Sửa");
		btnTK_Sua.setFont(fontBtn);
		ImageIcon iconSua = taoIconThuNho("/images/edit.png", 20, 20);
		if (iconSua != null)
			btnTK_Sua.setIcon(iconSua);

		btnTK_Luu = new JButton("Lưu");
		btnTK_Luu.setFont(fontBtn);
		ImageIcon iconLuu = taoIconThuNho("/images/save.png", 20, 20);
		if (iconLuu != null)
			btnTK_Luu.setIcon(iconLuu);

		pnNutChucNang.add(btnTK_Them);
		pnNutChucNang.add(btnTK_Xoa);
		pnNutChucNang.add(btnTK_XoaTrang);
		pnNutChucNang.add(btnTK_Sua);
		pnNutChucNang.add(btnTK_Luu);

		tbTaiKhoan.getTableHeader().setResizingAllowed(false);
		tbTaiKhoan.getTableHeader().setReorderingAllowed(false);

		return pnTaiKhoan;
	}

	// =========================================================================
	// PHẦN MỞ RỘNG: ĐĂNG KÝ SỰ KIỆN & LẤY DỮ LIỆU DÀNH CHO CONTROLLER
	// =========================================================================

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
	} // THÊM NÚT LƯU

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
	} // THÊM NÚT LƯU

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
	} // Trả về TabPane để Controller nhảy tab

	// Cập nhật hàm này: Thêm tham số String vaiTro ở cuối
	public void dienThongTinNhanVienLenForm(String ma, String hoTen, String tenDN, String sdt, String cccd,
			String vaiTro) {
		tfMaNhanVien.setText(ma);
		tfHoTenNhanVien.setText(hoTen);
		tfTenDangNhap.setText(tenDN);
		tfSoDienThoai.setText(sdt);
		tfCCCD.setText(cccd);
		// Cập nhật luôn ComboBox
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
		cbVaiTro.setSelectedIndex(0); // Reset ComboBox
		tfMaNhanVien.requestFocus();
	}

	public void xoaTrangFormTaiKhoan() {
		if (cbTK_TenDangNhap.getItemCount() > 0)
			cbTK_TenDangNhap.setSelectedIndex(0);
		tfTK_MatKhau.setText("");
		cbTK_VaiTro.setSelectedIndex(0);
		cbTK_TrangThai.setSelectedIndex(0);
	}

	// Thêm hàm lấy dữ liệu từ ComboBox Vai trò bên tab Nhân Viên
	public String getVaiTroNVSelected() {
		return cbVaiTro.getSelectedItem() != null ? cbVaiTro.getSelectedItem().toString() : "Thu ngân";
	}

}