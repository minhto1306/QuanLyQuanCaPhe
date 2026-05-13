package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DlgHoaDon extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Color MAU_NEN_CHINH = new Color(242, 233, 216);
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47);
	private final Color MAU_HEADER = new Color(209, 185, 161);
	private Font fontBungeeBase;

	private JComboBox<String> cbTimKiemHD;
	private JTable tbHoaDon;
	private DefaultTableModel tmHoaDon;

	private JTable tbChiTiet;
	private DefaultTableModel tmChiTiet;
	private JTextField tfGiamGia;
	private JComboBox<String> cbThue;
	private JButton btnInHoaDon;

	// CHỨC NĂNG: Khởi tạo giao diện quản lý hóa đơn.
	public DlgHoaDon(JFrame parent) {
		super(parent, "QUẢN LÝ HÓA ĐƠN", true);
		this.setSize(1450, 750);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		loadCustomFont();

		JPanel container = new JPanel(new BorderLayout(15, 0));
		container.setBackground(MAU_NAU_VIEN);
		container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		container.add(khoiTaoVungDanhSach(), BorderLayout.CENTER);
		container.add(khoiTaoVungChiTiet(), BorderLayout.EAST);

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

	// CHỨC NĂNG: Thay đổi kích thước hình ảnh hiển thị.
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

	// CHỨC NĂNG: Thiết lập định dạng chuẩn cho các nút tương tác.
	private void setupRetroButton(JButton btn) {
		btn.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 16f));
		btn.setBackground(MAU_NEN_CHINH);
		btn.setForeground(MAU_NAU_VIEN);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	// CHỨC NĂNG: Khởi tạo vùng hiển thị danh sách hóa đơn.
	private JPanel khoiTaoVungDanhSach() {
		JPanel pnTrai = new JPanel(new BorderLayout());
		pnTrai.setBackground(MAU_NEN_CHINH);
		pnTrai.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 4),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		pnTimKiem.setBackground(MAU_NEN_CHINH);

		JLabel lbTimKiem = new JLabel("LỌC THEO:");
		lbTimKiem.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 18f));
		lbTimKiem.setForeground(MAU_NAU_VIEN);

		cbTimKiemHD = new JComboBox<>();
		cbTimKiemHD.setPreferredSize(new Dimension(300, 30));
		setupRetroInput(cbTimKiemHD);

		pnTimKiem.add(lbTimKiem);
		pnTimKiem.add(cbTimKiemHD);

		pnTrai.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "Mã HĐ", "Khu Vực", "Bàn", "Thu ngân", "Thời gian lập", "Thành tiền", "Trạng thái" };
		tmHoaDon = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbHoaDon = new JTable(tmHoaDon);

		JTableHeader header = tbHoaDon.getTableHeader();
		header.setFont(new Font("Tahoma", Font.BOLD, 15));
		header.setBackground(MAU_HEADER);
		header.setForeground(MAU_NAU_VIEN);
		header.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		tbHoaDon.setFont(new Font("Tahoma", Font.BOLD, 14));
		tbHoaDon.setRowHeight(35);
		tbHoaDon.setBackground(MAU_NEN_CHINH);
		tbHoaDon.setGridColor(MAU_NAU_VIEN);
		tbHoaDon.setShowGrid(true);

		tbHoaDon.getColumnModel().getColumn(0).setPreferredWidth(80);
		tbHoaDon.getColumnModel().getColumn(1).setPreferredWidth(100);
		tbHoaDon.getColumnModel().getColumn(2).setPreferredWidth(60);
		tbHoaDon.getColumnModel().getColumn(5).setPreferredWidth(100);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tbHoaDon.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbHoaDon.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tbHoaDon.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tbHoaDon.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

		JScrollPane scrollPane = new JScrollPane(tbHoaDon);
		scrollPane.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));
		pnTrai.add(scrollPane, BorderLayout.CENTER);

		return pnTrai;
	}

	// CHỨC NĂNG: Khởi tạo vùng hiển thị chi tiết hóa đơn.
	private JPanel khoiTaoVungChiTiet() {
		JPanel pnPhai = new JPanel(new BorderLayout());
		pnPhai.setPreferredSize(new Dimension(500, 0));
		pnPhai.setBackground(MAU_NEN_CHINH);

		Border brdVienDen = BorderFactory.createLineBorder(MAU_NAU_VIEN, 4);
		Border brdThutVao = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		pnPhai.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));

		JLabel lbTitle = new JLabel("HÓA ĐƠN", SwingConstants.CENTER);
		lbTitle.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 32f));
		lbTitle.setForeground(MAU_NAU_VIEN);
		lbTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		pnPhai.add(lbTitle, BorderLayout.NORTH);

		String[] colNames = { "#", "Tên sản phẩm", "S.L", "Giá bán", "T.Tiền" };
		tmChiTiet = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbChiTiet = new JTable(tmChiTiet);

		JTableHeader header = tbChiTiet.getTableHeader();
		header.setFont(new Font("Tahoma", Font.BOLD, 15));
		header.setBackground(MAU_HEADER);
		header.setForeground(MAU_NAU_VIEN);
		header.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		tbChiTiet.setFont(new Font("Tahoma", Font.BOLD, 14));
		tbChiTiet.setRowHeight(35);
		tbChiTiet.setBackground(MAU_NEN_CHINH);
		tbChiTiet.setGridColor(MAU_NAU_VIEN);
		tbChiTiet.setShowGrid(true);

		tbChiTiet.getColumnModel().getColumn(0).setMaxWidth(40);
		tbChiTiet.getColumnModel().getColumn(1).setPreferredWidth(160);
		tbChiTiet.getColumnModel().getColumn(2).setMaxWidth(50);
		tbChiTiet.getColumnModel().getColumn(3).setPreferredWidth(90);
		tbChiTiet.getColumnModel().getColumn(4).setPreferredWidth(100);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		tbChiTiet.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbChiTiet.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		tbChiTiet.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tbChiTiet.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

		JScrollPane scrollPane = new JScrollPane(tbChiTiet);
		scrollPane.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));
		pnPhai.add(scrollPane, BorderLayout.CENTER);

		JPanel pnBottomWrap = new JPanel();
		pnBottomWrap.setLayout(new BoxLayout(pnBottomWrap, BoxLayout.Y_AXIS));
		pnBottomWrap.setBackground(MAU_NEN_CHINH);

		JPanel pnControls = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		pnControls.setBackground(MAU_HEADER);
		pnControls.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0),
				BorderFactory.createLineBorder(MAU_NAU_VIEN, 2)));

		Font fontLabelRetro = fontBungeeBase.deriveFont(Font.PLAIN, 18f);

		JLabel lbGiamGia = new JLabel("GIẢM GIÁ:");
		lbGiamGia.setFont(fontLabelRetro);
		lbGiamGia.setForeground(MAU_NAU_VIEN);

		tfGiamGia = new JTextField();
		tfGiamGia.setPreferredSize(new Dimension(100, 30));
		setupRetroInput(tfGiamGia);
		tfGiamGia.setHorizontalAlignment(JTextField.RIGHT);

		JLabel lbThue = new JLabel("THUẾ:");
		lbThue.setFont(fontLabelRetro);
		lbThue.setForeground(MAU_NAU_VIEN);

		cbThue = new JComboBox<>(new String[] { "0", "5", "8", "10" });
		cbThue.setPreferredSize(new Dimension(70, 30));
		setupRetroInput(cbThue);

		pnControls.add(lbGiamGia);
		pnControls.add(tfGiamGia);
		pnControls.add(Box.createHorizontalStrut(30));
		pnControls.add(lbThue);
		pnControls.add(cbThue);

		JPanel pnAction = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnAction.setBackground(MAU_NEN_CHINH);
		pnAction.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		btnInHoaDon = new JButton(" IN HÓA ĐƠN ĐÃ CHỌN", taoIconThuNho("/images/print.png", 20, 20));
		setupRetroButton(btnInHoaDon);
		pnAction.add(btnInHoaDon);

		pnBottomWrap.add(pnControls);
		pnBottomWrap.add(pnAction);

		pnPhai.add(pnBottomWrap, BorderLayout.SOUTH);

		return pnPhai;
	}

	public JTable getTbHoaDon() {
		return tbHoaDon;
	}

	public DefaultTableModel getTmHoaDon() {
		return tmHoaDon;
	}

	public JTable getTbChiTiet() {
		return tbChiTiet;
	}

	public DefaultTableModel getTmChiTiet() {
		return tmChiTiet;
	}

	public JComboBox<String> getCbTimKiemHD() {
		return cbTimKiemHD;
	}

	public JTextField getTfGiamGia() {
		return tfGiamGia;
	}

	public JComboBox<String> getCbThue() {
		return cbThue;
	}

	public void addTbHoaDonMouseListener(java.awt.event.MouseAdapter adapter) {
		tbHoaDon.addMouseListener(adapter);
	}

	public void addBtnInHoaDonListener(java.awt.event.ActionListener l) {
		btnInHoaDon.addActionListener(l);
	}
}