package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

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

	private JLabel lbMaSanPham;
	private JLabel lbTenSanPham;
	private JLabel lbGiaBan;
	private JLabel lbDanhMuc;
	private JLabel lbTrangThai;
	private JLabel lbTimSP;
	private JLabel lbHinhAnhText;
	private JLabel lbHinhAnh;

	private JComboBox<String> cbDanhMuc;
	private JComboBox<String> cbTrangThai;
	private JTextField tfMaSanPham;
	private JTextField tfTenSanPham;
	private JTextField tfGiaBan;
	private JTextField tfTimSP;

	private JTable tbSanPham;
	private DefaultTableModel tmSanPham;

	private JButton btnDM_Them;
	private JButton btnDM_Xoa;
	private JButton btnDM_XoaTrang;
	private JButton btnDM_Sua;
	private JButton btnDM_Luu;
	private JButton btnDM_Tim;

	private JLabel lbDM_Ma;
	private JLabel lbDM_Ten;
	private JLabel lbDM_Tim;

	private JTextField tfDM_Ma;
	private JTextField tfDM_Ten;
	private JTextField tfDM_Tim;

	private Box boxDM_Row1;
	private Box boxDM_Tim;

	private JTable tbDanhMuc;
	private DefaultTableModel tmDanhMuc;

	public DlgHangHoa(JFrame parent) {
		super(parent, "HÀNG HÓA VÀ DANH MỤC", true);
		this.setSize(900, 750);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		ImageIcon iconHangHoa = taoIconThuNho("/images/item.png", 24, 24);
		ImageIcon iconDanhMuc = taoIconThuNho("/images/procurement.png", 24, 24);

		tabbedPane = new JTabbedPane();
		tabHangHoa = khoiTaoTabHangHoa();
		tabDanhMuc = khoiTaoTabDanhMuc();

		tabbedPane.addTab("THÔNG TIN HÀNG HÓA", iconHangHoa, tabHangHoa, "Xem thông tin hàng hóa");
		tabbedPane.addTab("THÔNG TIN DANH MỤC", iconDanhMuc, tabDanhMuc, "Xem thông tin danh mục");

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

	private JPanel khoiTaoTabHangHoa() {
		pnHangHoa = new JPanel();
		pnHangHoa.setLayout(new BorderLayout());
		pnHangHoa.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		lbTimSP = new JLabel("Tìm sản phẩm:");
		lbMaSanPham = new JLabel("Mã sản phẩm:");
		lbTenSanPham = new JLabel("Tên sản phẩm:");
		lbGiaBan = new JLabel("Giá bán:");
		lbTrangThai = new JLabel("Trạng thái:");
		lbDanhMuc = new JLabel("Danh mục:");
		lbHinhAnhText = new JLabel("Hình ảnh:");

		Dimension dLeftLabel = new Dimension(140, 25);
		lbMaSanPham.setPreferredSize(dLeftLabel);
		lbTenSanPham.setPreferredSize(dLeftLabel);
		lbGiaBan.setPreferredSize(dLeftLabel);
		lbTrangThai.setPreferredSize(dLeftLabel);

		Dimension dRightLabel = new Dimension(90, 25);
		lbDanhMuc.setPreferredSize(dRightLabel);
		lbHinhAnhText.setPreferredSize(dRightLabel);
		lbTimSP.setPreferredSize(new Dimension(120, 25));

		Font fontLablel = new Font("Arial", Font.BOLD, 15);
		lbTimSP.setFont(fontLablel);
		lbMaSanPham.setFont(fontLablel);
		lbTenSanPham.setFont(fontLablel);
		lbGiaBan.setFont(fontLablel);
		lbTrangThai.setFont(fontLablel);
		lbDanhMuc.setFont(fontLablel);
		lbHinhAnhText.setFont(fontLablel);

		tfTimSP = new JTextField();
		tfTimSP.setPreferredSize(new Dimension(300, 25));

		tfMaSanPham = new JTextField();
		tfTenSanPham = new JTextField();
		tfGiaBan = new JTextField();

		cbTrangThai = new JComboBox<String>();
		cbTrangThai.addItem("Đang bán");
		cbTrangThai.addItem("Ngừng bán");

		Dimension dInputTrai = new Dimension(250, 25);
		tfMaSanPham.setPreferredSize(dInputTrai);
		tfMaSanPham.setMaximumSize(dInputTrai);
		tfTenSanPham.setPreferredSize(dInputTrai);
		tfTenSanPham.setMaximumSize(dInputTrai);
		tfGiaBan.setPreferredSize(dInputTrai);
		tfGiaBan.setMaximumSize(dInputTrai);
		cbTrangThai.setPreferredSize(dInputTrai);
		cbTrangThai.setMaximumSize(dInputTrai);

		cbDanhMuc = new JComboBox<String>();
		cbDanhMuc.setPreferredSize(new Dimension(170, 25));
		cbDanhMuc.setMaximumSize(new Dimension(170, 25));

		btnThemDanhMuc = new JButton();
		btnThemDanhMuc.setPreferredSize(new Dimension(40, 40));
		btnThemDanhMuc.setMinimumSize(new Dimension(40, 40));
		btnThemDanhMuc.setMaximumSize(new Dimension(40, 40));
		btnThemDanhMuc.setBorderPainted(false);
		btnThemDanhMuc.setContentAreaFilled(false);
		btnThemDanhMuc.setFocusPainted(false);
		btnThemDanhMuc.setCursor(new Cursor(Cursor.HAND_CURSOR));

		ImageIcon iconThemDanhMuc = taoIconThuNho("/images/plus.png", 32, 32);
		if (iconThemDanhMuc != null) {
			btnThemDanhMuc.setIcon(iconThemDanhMuc);
		} else {
			btnThemDanhMuc.setText("+");
			btnThemDanhMuc.setBorderPainted(true);
			btnThemDanhMuc.setContentAreaFilled(true);
		}

		lbHinhAnh = new JLabel();
		lbHinhAnh.setPreferredSize(new Dimension(120, 120));
		lbHinhAnh.setMaximumSize(new Dimension(120, 120));
		lbHinhAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		lbHinhAnh.setOpaque(true);
		lbHinhAnh.setBackground(Color.WHITE);
		lbHinhAnh.setHorizontalAlignment(JLabel.CENTER);

		btnChonAnh = new JButton("Browse");
		btnXoaAnh = new JButton("Xóa");

		btnTim = new JButton();
		btnTim.setPreferredSize(new Dimension(40, 25));
		ImageIcon iconTim = taoIconThuNho("/images/search.png", 16, 16);
		if (iconTim != null) {
			btnTim.setIcon(iconTim);
		} else {
			btnTim.setText("Tìm");
		}

		JPanel pnNhapThongTin = new JPanel();
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.X_AXIS));
		Border brdVienDen = BorderFactory.createLineBorder(Color.black);
		Border brdThutVao = BorderFactory.createEmptyBorder(15, 10, 15, 10);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));
		pnHangHoa.add(pnNhapThongTin, BorderLayout.NORTH);

		JPanel pnTrai = new JPanel();
		pnTrai.setLayout(new BoxLayout(pnTrai, BoxLayout.Y_AXIS));

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
		pnTrai.add(Box.createVerticalStrut(15));
		pnTrai.add(boxTrai2);
		pnTrai.add(Box.createVerticalStrut(15));
		pnTrai.add(boxTrai3);
		pnTrai.add(Box.createVerticalStrut(15));
		pnTrai.add(boxTrai4);

		JPanel pnPhai = new JPanel();
		pnPhai.setLayout(new BoxLayout(pnPhai, BoxLayout.Y_AXIS));

		Box boxPhai1 = Box.createHorizontalBox();
		boxPhai1.add(lbDanhMuc);
		boxPhai1.add(cbDanhMuc);
		boxPhai1.add(Box.createHorizontalStrut(5));
		boxPhai1.add(btnThemDanhMuc);
		boxPhai1.add(Box.createHorizontalGlue());

		Box boxPhai2 = Box.createHorizontalBox();
		boxPhai2.add(lbHinhAnhText);
		boxPhai2.add(lbHinhAnh);
		boxPhai2.add(Box.createHorizontalStrut(10));

		Box boxAnhBtns = Box.createVerticalBox();
		boxAnhBtns.add(btnChonAnh);
		boxAnhBtns.add(Box.createVerticalStrut(10));
		boxAnhBtns.add(btnXoaAnh);
		boxPhai2.add(boxAnhBtns);
		boxPhai2.add(Box.createHorizontalGlue());

		pnPhai.add(boxPhai1);
		pnPhai.add(Box.createVerticalStrut(15));
		pnPhai.add(boxPhai2);
		pnPhai.add(Box.createVerticalGlue());

		pnNhapThongTin.add(pnTrai);
		pnNhapThongTin.add(Box.createHorizontalStrut(40));
		pnNhapThongTin.add(pnPhai);

		JPanel pnBangDuLieu = new JPanel();
		pnBangDuLieu.setLayout(new BorderLayout());
		pnHangHoa.add(pnBangDuLieu, BorderLayout.CENTER);
		pnBangDuLieu
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0), brdVienDen));

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		Box boxTimSP = Box.createHorizontalBox();
		boxTimSP.add(lbTimSP);
		boxTimSP.add(tfTimSP);
		boxTimSP.add(Box.createHorizontalStrut(5));
		boxTimSP.add(btnTim);
		pnTimKiem.add(boxTimSP);

		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "Mã SP", "Tên sản phẩm", "Danh mục", "Giá bán", "Trạng thái" };
		tmSanPham = new DefaultTableModel(colNames, 0);
		tbSanPham = new JTable(tmSanPham);
		tbSanPham.getTableHeader().setFont(fontLablel);
		tbSanPham.setFont(new Font("Arial", Font.PLAIN, 14));
		tbSanPham.setRowHeight(25);

		tbSanPham.getColumnModel().getColumn(0).setPreferredWidth(80);
		tbSanPham.getColumnModel().getColumn(1).setPreferredWidth(200);

		JScrollPane scrollPane = new JScrollPane(tbSanPham);
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);

		JPanel pnNutChucNang = new JPanel();
		pnHangHoa.add(pnNutChucNang, BorderLayout.SOUTH);
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
		btnLuu.setEnabled(false);
		ImageIcon iconLuu = taoIconThuNho("/images/save.png", 20, 20);
		if (iconLuu != null)
			btnLuu.setIcon(iconLuu);

		pnNutChucNang.add(btnThem);
		pnNutChucNang.add(btnXoa);
		pnNutChucNang.add(btnXoaTrang);
		pnNutChucNang.add(btnSua);
		pnNutChucNang.add(btnLuu);

		tbSanPham.getTableHeader().setResizingAllowed(false);
		tbSanPham.getTableHeader().setReorderingAllowed(false);

		return pnHangHoa;
	}

	private JPanel khoiTaoTabDanhMuc() {
		pnDanhMuc = new JPanel();
		pnDanhMuc.setLayout(new BorderLayout());
		pnDanhMuc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		boxDM_Row1 = Box.createHorizontalBox();
		boxDM_Tim = Box.createHorizontalBox();

		lbDM_Tim = new JLabel("Tìm danh mục:");
		lbDM_Ma = new JLabel("Mã danh mục:");
		lbDM_Ten = new JLabel("Tên danh mục:");

		Dimension dLabel = new Dimension(120, 25);
		lbDM_Ma.setPreferredSize(dLabel);
		lbDM_Ten.setPreferredSize(dLabel);
		lbDM_Tim.setPreferredSize(dLabel);

		Font fontLablel = new Font("Arial", Font.BOLD, 15);
		lbDM_Tim.setFont(fontLablel);
		lbDM_Ma.setFont(fontLablel);
		lbDM_Ten.setFont(fontLablel);

		tfDM_Tim = new JTextField();
		tfDM_Tim.setPreferredSize(new Dimension(300, 25));

		tfDM_Ma = new JTextField();
		tfDM_Ten = new JTextField();

		Dimension dInput = new Dimension(250, 25);
		tfDM_Ma.setPreferredSize(dInput);
		tfDM_Ma.setMaximumSize(dInput);
		tfDM_Ten.setPreferredSize(dInput);
		tfDM_Ten.setMaximumSize(dInput);

		btnDM_Tim = new JButton();
		btnDM_Tim.setPreferredSize(new Dimension(40, 25));
		ImageIcon iconTim = taoIconThuNho("/images/search.png", 16, 16);
		if (iconTim != null) {
			btnDM_Tim.setIcon(iconTim);
		} else {
			btnDM_Tim.setText("Tìm");
		}

		JPanel pnNhapThongTin = new JPanel();
		pnDanhMuc.add(pnNhapThongTin, BorderLayout.NORTH);
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.Y_AXIS));

		Border brdVienDen = BorderFactory.createLineBorder(Color.black);
		Border brdThutVao = BorderFactory.createEmptyBorder(15, 10, 15, 10);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));

		boxDM_Row1.add(lbDM_Ma);
		boxDM_Row1.add(tfDM_Ma);
		boxDM_Row1.add(Box.createHorizontalStrut(80));
		boxDM_Row1.add(lbDM_Ten);
		boxDM_Row1.add(tfDM_Ten);
		boxDM_Row1.add(Box.createHorizontalGlue());
		pnNhapThongTin.add(boxDM_Row1);

		JPanel pnBangDuLieu = new JPanel();
		pnBangDuLieu.setLayout(new BorderLayout());
		pnDanhMuc.add(pnBangDuLieu, BorderLayout.CENTER);
		pnBangDuLieu
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0), brdVienDen));

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		boxDM_Tim.add(lbDM_Tim);
		boxDM_Tim.add(tfDM_Tim);
		boxDM_Tim.add(Box.createHorizontalStrut(5));
		boxDM_Tim.add(btnDM_Tim);
		pnTimKiem.add(boxDM_Tim);

		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "Mã danh mục", "Tên danh mục" };
		tmDanhMuc = new DefaultTableModel(colNames, 0);
		tbDanhMuc = new JTable(tmDanhMuc);
		tbDanhMuc.getTableHeader().setFont(fontLablel);
		tbDanhMuc.setFont(new Font("Arial", Font.PLAIN, 14));
		tbDanhMuc.setRowHeight(25);

		JScrollPane scrollPane = new JScrollPane(tbDanhMuc);
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);

		JPanel pnNutChucNang = new JPanel();
		pnDanhMuc.add(pnNutChucNang, BorderLayout.SOUTH);
		pnNutChucNang.setBorder(brdVienDen);
		pnNutChucNang.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

		Font fontBtn = new Font("Arial", Font.BOLD, 14);

		btnDM_Them = new JButton("Thêm");
		btnDM_Them.setFont(fontBtn);
		ImageIcon iconThem = taoIconThuNho("/images/add.png", 20, 20);
		if (iconThem != null)
			btnDM_Them.setIcon(iconThem);

		btnDM_Xoa = new JButton("Xóa");
		btnDM_Xoa.setFont(fontBtn);
		ImageIcon iconXoa = taoIconThuNho("/images/delete.png", 20, 20);
		if (iconXoa != null)
			btnDM_Xoa.setIcon(iconXoa);

		btnDM_XoaTrang = new JButton("Xóa trắng");
		btnDM_XoaTrang.setFont(fontBtn);
		ImageIcon iconXoaTrang = taoIconThuNho("/images/clear.png", 20, 20);
		if (iconXoaTrang != null)
			btnDM_XoaTrang.setIcon(iconXoaTrang);

		btnDM_Sua = new JButton("Sửa");
		btnDM_Sua.setFont(fontBtn);
		ImageIcon iconSua = taoIconThuNho("/images/edit.png", 20, 20);
		if (iconSua != null)
			btnDM_Sua.setIcon(iconSua);

		btnDM_Luu = new JButton("Lưu");
		btnDM_Luu.setFont(fontBtn);
		btnDM_Luu.setEnabled(false);
		ImageIcon iconLuu = taoIconThuNho("/images/save.png", 20, 20);
		if (iconLuu != null)
			btnDM_Luu.setIcon(iconLuu);

		pnNutChucNang.add(btnDM_Them);
		pnNutChucNang.add(btnDM_Xoa);
		pnNutChucNang.add(btnDM_XoaTrang);
		pnNutChucNang.add(btnDM_Sua);
		pnNutChucNang.add(btnDM_Luu);

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

	public void batTatNutSanPham(boolean them, boolean xoa, boolean sua, boolean luu, boolean tfMaState) {
		btnThem.setEnabled(them);
		btnXoa.setEnabled(xoa);
		btnSua.setEnabled(sua);
		btnLuu.setEnabled(luu);
		tfMaSanPham.setEditable(tfMaState);
	}

	public void batTatNutDanhMuc(boolean them, boolean xoa, boolean sua, boolean luu, boolean tfMaState) {
		btnDM_Them.setEnabled(them);
		btnDM_Xoa.setEnabled(xoa);
		btnDM_Sua.setEnabled(sua);
		btnDM_Luu.setEnabled(luu);
		tfDM_Ma.setEditable(tfMaState);
	}
}