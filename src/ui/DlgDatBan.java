package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class DlgDatBan extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel pnMain;
	private JButton btnThem, btnXoa, btnXoaTrang, btnSua, btnLuu, btnTim;
	private JLabel lbTenKhach, lbSoDienThoai, lbMaKhuVuc, lbMaBan, lbThoiGianNhan, lbTrangThai, lbTimKiem;
	private JTextField tfTenKhach, tfSoDienThoai, tfTimKiem;
	private JComboBox<String> cbTrangThai, cbMaKhuVuc, cbMaBan;
	private JSpinner spThoiGianNhan;
	private Box boxRow1, boxRow2, boxRow3, boxTimKiem;
	private JTable tbDatBan;
	private DefaultTableModel tmDatBan;

	private final Color MAU_NEN_CHINH = new Color(242, 233, 216);
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47);
	private final Color MAU_HEADER = new Color(209, 185, 161);
	private Font fontBungeeBase;

	// CHỨC NĂNG: Khởi tạo hộp thoại quản lý quá trình đặt bàn.
	public DlgDatBan(JFrame parent) {
		super(parent, "QUẢN LÝ ĐẶT BÀN", true);
		this.setSize(1400, 700);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		loadCustomFont();

		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(MAU_NAU_VIEN);
		container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		pnMain = khoiTaoGiaoDien();
		container.add(pnMain, BorderLayout.CENTER);

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

	// CHỨC NĂNG: Xử lý thay đổi kích thước của hình ảnh.
	private ImageIcon taoIconThuNho(String duongDan, int width, int height) {
		java.net.URL url = getClass().getResource(duongDan);
		if (url == null)
			return null;
		return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	// CHỨC NĂNG: Thiết lập định dạng chuẩn cho các ô nhập liệu.
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

	// CHỨC NĂNG: Khởi tạo và thiết lập các thành phần giao diện.
	private JPanel khoiTaoGiaoDien() {
		JPanel pnDatBan = new JPanel(new BorderLayout());
		pnDatBan.setBackground(MAU_NEN_CHINH);
		pnDatBan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		Font fontLabelRetro = fontBungeeBase.deriveFont(Font.PLAIN, 18f);

		boxRow1 = Box.createHorizontalBox();
		boxRow2 = Box.createHorizontalBox();
		boxRow3 = Box.createHorizontalBox();
		boxTimKiem = Box.createHorizontalBox();

		lbTenKhach = new JLabel("TÊN KHÁCH HÀNG:");
		lbSoDienThoai = new JLabel("SỐ ĐIỆN THOẠI:");
		lbMaKhuVuc = new JLabel("MÃ KHU VỰC:");
		lbMaBan = new JLabel("MÃ BÀN:");
		lbThoiGianNhan = new JLabel("THỜI GIAN NHẬN:");
		lbTrangThai = new JLabel("TRẠNG THÁI:");
		lbTimKiem = new JLabel("TÌM KIẾM:");

		Dimension dLabel = new Dimension(180, 25);
		JLabel[] labels = { lbTenKhach, lbSoDienThoai, lbMaKhuVuc, lbMaBan, lbThoiGianNhan, lbTrangThai, lbTimKiem };
		for (JLabel lb : labels) {
			lb.setPreferredSize(dLabel);
			lb.setFont(fontLabelRetro);
			lb.setForeground(MAU_NAU_VIEN);
		}
		lbTimKiem.setPreferredSize(new Dimension(120, 25));

		tfTenKhach = new JTextField();
		tfSoDienThoai = new JTextField();
		tfTimKiem = new JTextField();

		cbMaKhuVuc = new JComboBox<>();
		cbMaBan = new JComboBox<>();
		cbTrangThai = new JComboBox<>(new String[] { "Chờ nhận bàn", "Đã nhận bàn" });

		SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
		spThoiGianNhan = new JSpinner(dateModel);
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spThoiGianNhan, "dd/MM/yyyy HH:mm");
		spThoiGianNhan.setEditor(timeEditor);

		spThoiGianNhan.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));
		JTextField tfSpinner = ((JSpinner.DefaultEditor) spThoiGianNhan.getEditor()).getTextField();
		tfSpinner.setFont(new Font("Arial", Font.BOLD, 15));
		tfSpinner.setForeground(MAU_NAU_VIEN);

		Dimension dInput = new Dimension(250, 30);
		Component[] inputs = { tfTenKhach, tfSoDienThoai, cbMaKhuVuc, cbMaBan, cbTrangThai };
		for (Component cp : inputs) {
			cp.setPreferredSize(dInput);
			cp.setMaximumSize(dInput);
			if (cp instanceof JTextField)
				setupRetroInput((JTextField) cp);
			if (cp instanceof JComboBox)
				setupRetroInput((JComboBox<?>) cp);
		}
		spThoiGianNhan.setPreferredSize(dInput);
		spThoiGianNhan.setMaximumSize(dInput);

		btnTim = new JButton(taoIconThuNho("/images/search.png", 20, 20));
		btnTim.setPreferredSize(new Dimension(40, 30));
		btnTim.setBackground(MAU_NEN_CHINH);
		btnTim.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		JPanel pnNhap = new JPanel();
		pnNhap.setLayout(new BoxLayout(pnNhap, BoxLayout.Y_AXIS));
		pnNhap.setBackground(MAU_NEN_CHINH);
		Border brdVienDen = BorderFactory.createLineBorder(MAU_NAU_VIEN, 4);
		Border brdThutVao = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		pnNhap.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));

		boxRow1.add(lbTenKhach);
		boxRow1.add(Box.createHorizontalStrut(15));
		boxRow1.add(tfTenKhach);
		boxRow1.add(Box.createHorizontalStrut(40));
		boxRow1.add(lbSoDienThoai);
		boxRow1.add(Box.createHorizontalStrut(15));
		boxRow1.add(tfSoDienThoai);
		boxRow1.add(Box.createHorizontalGlue());

		boxRow2.add(lbMaKhuVuc);
		boxRow2.add(Box.createHorizontalStrut(15));
		boxRow2.add(cbMaKhuVuc);
		boxRow2.add(Box.createHorizontalStrut(40));
		boxRow2.add(lbMaBan);
		boxRow2.add(Box.createHorizontalStrut(15));
		boxRow2.add(cbMaBan);
		boxRow2.add(Box.createHorizontalGlue());

		boxRow3.add(lbThoiGianNhan);
		boxRow3.add(Box.createHorizontalStrut(15));
		boxRow3.add(spThoiGianNhan);
		boxRow3.add(Box.createHorizontalStrut(40));
		boxRow3.add(lbTrangThai);
		boxRow3.add(Box.createHorizontalStrut(15));
		boxRow3.add(cbTrangThai);
		boxRow3.add(Box.createHorizontalGlue());

		pnNhap.add(boxRow1);
		pnNhap.add(Box.createVerticalStrut(20));
		pnNhap.add(boxRow2);
		pnNhap.add(Box.createVerticalStrut(20));
		pnNhap.add(boxRow3);
		pnDatBan.add(pnNhap, BorderLayout.NORTH);

		JPanel pnBangDuLieu = new JPanel(new BorderLayout());
		pnBangDuLieu.setBackground(MAU_NEN_CHINH);
		pnBangDuLieu.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0), brdVienDen));

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
		pnTimKiem.setBackground(MAU_HEADER);
		pnTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, MAU_NAU_VIEN));

		boxTimKiem.add(lbTimKiem);
		boxTimKiem.add(Box.createHorizontalStrut(15));
		tfTimKiem.setPreferredSize(new Dimension(300, 30));
		tfTimKiem.setMaximumSize(new Dimension(300, 30));
		setupRetroInput(tfTimKiem);
		boxTimKiem.add(tfTimKiem);
		boxTimKiem.add(Box.createHorizontalStrut(15));
		boxTimKiem.add(btnTim);
		pnTimKiem.add(boxTimKiem);
		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "#", "Mã ĐB", "Khu Vực", "Bàn", "Tên Khách", "Số ĐT", "Thời Gian", "Trạng Thái",
				"Chi Tiết" };
		tmDatBan = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return col == 8;
			}
		};
		tbDatBan = new JTable(tmDatBan);

		JTableHeader header = tbDatBan.getTableHeader();
		header.setFont(new Font("Tahoma", Font.BOLD, 16));
		header.setBackground(MAU_HEADER);
		header.setForeground(MAU_NAU_VIEN);
		header.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		tbDatBan.setRowHeight(35);
		tbDatBan.setFont(new Font("Tahoma", Font.BOLD, 14));
		tbDatBan.setBackground(MAU_NEN_CHINH);
		tbDatBan.setGridColor(MAU_NAU_VIEN);
		tbDatBan.setShowGrid(true);

		tbDatBan.getColumnModel().getColumn(0).setMaxWidth(40);
		tbDatBan.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
		tbDatBan.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox()));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i <= 3; i++)
			tbDatBan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		tbDatBan.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setHorizontalAlignment(SwingConstants.CENTER);
				c.setFont(c.getFont().deriveFont(Font.BOLD));
				if (value != null) {
					String status = value.toString();
					if (status.equalsIgnoreCase("Chờ nhận bàn")) {
						c.setForeground(Color.RED);
					} else if (status.equalsIgnoreCase("Đã nhận bàn")) {
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

		JScrollPane scrollPane = new JScrollPane(tbDatBan);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);
		pnDatBan.add(pnBangDuLieu, BorderLayout.CENTER);

		JPanel pnNutChucNang = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		pnNutChucNang.setBackground(MAU_NEN_CHINH);
		pnNutChucNang.setBorder(brdVienDen);

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
		pnDatBan.add(pnNutChucNang, BorderLayout.SOUTH);

		tbDatBan.getTableHeader().setResizingAllowed(false);
		tbDatBan.getTableHeader().setReorderingAllowed(false);

		return pnDatBan;
	}

	// Lớp phụ trợ phục vụ hiển thị nút bấm trong bảng
	class ButtonRenderer extends JButton implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		public ButtonRenderer() {
			setOpaque(true);
			setFont(new Font("Tahoma", Font.BOLD, 12));
			setBackground(MAU_HEADER);
			setForeground(MAU_NAU_VIEN);
			setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 1));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int col) {
			setText("XEM");
			return this;
		}
	}

	// Lớp phụ trợ phục vụ thao tác của nút bấm trong bảng
	class ButtonEditor extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;
		protected JButton button;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton("XEM");
			button.setBackground(MAU_HEADER);
			button.setForeground(MAU_NAU_VIEN);
			button.setFont(new Font("Tahoma", Font.BOLD, 12));
			button.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 1));
			button.addActionListener(e -> fireEditingStopped());
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			return "XEM";
		}
	}

	public JTextField getTfTenKhach() {
		return tfTenKhach;
	}

	public JTextField getTfSoDienThoai() {
		return tfSoDienThoai;
	}

	public JTextField getTfTimKiem() {
		return tfTimKiem;
	}

	public JComboBox<String> getCbMaKhuVuc() {
		return cbMaKhuVuc;
	}

	public JComboBox<String> getCbMaBan() {
		return cbMaBan;
	}

	public JSpinner getSpThoiGianNhan() {
		return spThoiGianNhan;
	}

	public JComboBox<String> getCbTrangThai() {
		return cbTrangThai;
	}

	public JTable getTbDatBan() {
		return tbDatBan;
	}

	public DefaultTableModel getTmDatBan() {
		return tmDatBan;
	}

	public void addBtnThemListener(ActionListener l) {
		btnThem.addActionListener(l);
	}

	public void addBtnXoaListener(ActionListener l) {
		btnXoa.addActionListener(l);
	}

	public void addBtnSuaListener(ActionListener l) {
		btnSua.addActionListener(l);
	}

	public void addBtnXoaTrangListener(ActionListener l) {
		btnXoaTrang.addActionListener(l);
	}

	public void addBtnLuuListener(ActionListener l) {
		btnLuu.addActionListener(l);
	}

	public void addBtnTimListener(ActionListener l) {
		btnTim.addActionListener(l);
	}

	public void addTfTimKiemListener(ActionListener l) {
		tfTimKiem.addActionListener(l);
	}

	// CHỨC NĂNG: Khởi tạo và hiển thị hộp thoại chi tiết cho thông tin đặt bàn đã
	// được chọn.
	public void hienThiDialogChiTiet(String maDB, String khuVuc, String ban, String tenKhach, String sdt,
			String thoiGian, String trangThai) {
		new DlgChiTietDatBan(this, maDB, khuVuc, ban, tenKhach, sdt, thoiGian, trangThai).setVisible(true);
	}

	// Lớp phụ trợ phục vụ hiển thị chi tiết thông tin đặt bàn
	class DlgChiTietDatBan extends JDialog {
		private static final long serialVersionUID = 1L;

		public DlgChiTietDatBan(Dialog parent, String maDB, String khuVuc, String ban, String tenKhach, String sdt,
				String thoiGian, String trangThai) {
			super(parent, "CHI TIẾT ĐẶT BÀN", true);
			this.setSize(450, 450);
			this.setLocationRelativeTo(parent);
			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			p.setBackground(MAU_NEN_CHINH);
			p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 4),
					BorderFactory.createEmptyBorder(20, 30, 20, 30)));

			p.add(createRow("MÃ ĐẶT BÀN :", maDB));
			p.add(Box.createVerticalStrut(10));
			p.add(createRow("KHU VỰC: ", khuVuc));
			p.add(Box.createVerticalStrut(10));
			p.add(createRow("BÀN: ", ban));
			p.add(Box.createVerticalStrut(10));

			String tienPhuThu = "0";
			dao.KhuVucDAO kvDAO = new dao.KhuVucDAO();
			java.util.List<entity.KhuVuc> dsKV = kvDAO.findAll();
			for (entity.KhuVuc kv : dsKV) {
				if (kv.getMaKhuVuc().equals(khuVuc) || kv.getTenKhuVuc().equals(khuVuc)) {
					tienPhuThu = String.format("%,.0f VNĐ", kv.getPhuThu());
					break;
				}
			}
			p.add(createRow("PHỤ THU: ", tienPhuThu));
			p.add(Box.createVerticalStrut(10));

			p.add(createRow("KHÁCH HÀNG :", tenKhach));
			p.add(Box.createVerticalStrut(10));
			p.add(createRow("SỐ ĐIỆN THOẠI :", sdt));
			p.add(Box.createVerticalStrut(10));
			p.add(createRow("THỜI GIAN :", thoiGian.isEmpty() ? "N/A" : thoiGian));
			p.add(Box.createVerticalStrut(10));
			p.add(createRow("TRẠNG THÁI: ", trangThai));

			this.setContentPane(p);
		}

		private JPanel createRow(String t, String v) {
			JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
			row.setOpaque(false);
			JLabel l1 = new JLabel(t);
			l1.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 16f));
			l1.setForeground(MAU_NAU_VIEN);
			l1.setPreferredSize(new Dimension(170, 25));
			JLabel l2 = new JLabel(v);
			l2.setFont(new Font("Tahoma", Font.BOLD, 15));
			l2.setForeground(new Color(193, 71, 71));
			row.add(l1);
			row.add(l2);
			return row;
		}
	}

	// CHỨC NĂNG: Quản lý trạng thái bật/tắt các nút chức năng trong quá trình đặt
	// bàn.
	public void batTatNutDatBan(boolean them, boolean xoa, boolean sua, boolean luu) {
		btnThem.setEnabled(them);
		btnXoa.setEnabled(xoa);
		btnSua.setEnabled(sua);
		btnLuu.setEnabled(luu);
	}
}