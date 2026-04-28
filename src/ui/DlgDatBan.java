package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class DlgDatBan extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel pnMain;
	private JButton btnThem, btnXoa, btnXoaTrang, btnSua, btnLuu, btnTim;
	private JLabel lbTenKhach, lbSoDienThoai, lbThoiGian, lbTrangThai, lbTimKiem;
	private JTextField tfTenKhach, tfSoDienThoai, tfThoiGian, tfTimKiem;
	private JComboBox<String> cbTrangThai;
	private Box boxRow1, boxRow2, boxTimKiem;
	private JTable tbDatBan;
	private DefaultTableModel tmDatBan;

	public DlgDatBan(JFrame parent) {
		super(parent, "ĐẶT BÀN", true);
		this.setSize(900, 600);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		pnMain = khoiTaoGiaoDien();
		this.add(pnMain);
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

	private JPanel khoiTaoGiaoDien() {
		JPanel pnDatBan = new JPanel(new BorderLayout());

		// --- PHẦN NHẬP LIỆU ---
		boxRow1 = Box.createHorizontalBox();
		boxRow2 = Box.createHorizontalBox();
		boxTimKiem = Box.createHorizontalBox();

		lbTenKhach = new JLabel("Tên khách hàng:");
		lbSoDienThoai = new JLabel("Số điện thoại:");
		lbThoiGian = new JLabel("Thời gian đặt:");
		lbTrangThai = new JLabel("Trạng thái:");
		lbTimKiem = new JLabel("Tìm đặt bàn:");

		Dimension dLabel = new Dimension(120, 25);
		lbTenKhach.setPreferredSize(dLabel);
		lbSoDienThoai.setPreferredSize(dLabel);
		lbThoiGian.setPreferredSize(dLabel);
		lbTrangThai.setPreferredSize(dLabel);
		lbTimKiem.setPreferredSize(dLabel);

		Font fontLabel = new Font("Arial", Font.BOLD, 14);
		lbTenKhach.setFont(fontLabel);
		lbSoDienThoai.setFont(fontLabel);
		lbThoiGian.setFont(fontLabel);
		lbTrangThai.setFont(fontLabel);
		lbTimKiem.setFont(fontLabel);

		tfTenKhach = new JTextField();
		tfSoDienThoai = new JTextField();
		tfThoiGian = new JTextField();
		tfTimKiem = new JTextField();

		cbTrangThai = new JComboBox<String>();
		cbTrangThai.addItem("Chờ nhận bàn");
		cbTrangThai.addItem("Đã nhận bàn");

		Dimension dInput = new Dimension(250, 25);
		tfTenKhach.setPreferredSize(dInput);
		tfTenKhach.setMaximumSize(dInput);
		tfSoDienThoai.setPreferredSize(dInput);
		tfSoDienThoai.setMaximumSize(dInput);
		tfThoiGian.setPreferredSize(dInput);
		tfThoiGian.setMaximumSize(dInput);
		cbTrangThai.setPreferredSize(dInput);
		cbTrangThai.setMaximumSize(dInput);
		tfTimKiem.setPreferredSize(new Dimension(300, 25));

		btnTim = new JButton();
		btnTim.setPreferredSize(new Dimension(40, 25));
		ImageIcon iconTim = taoIconThuNho("/images/search.png", 16, 16);
		if (iconTim != null) {
			btnTim.setIcon(iconTim);
		} else {
			btnTim.setText("Tìm");
		}

		JPanel pnNhapThongTin = new JPanel();
		pnNhapThongTin.setLayout(new BoxLayout(pnNhapThongTin, BoxLayout.Y_AXIS));
		Border brdVienDen = BorderFactory.createLineBorder(Color.gray);
		Border brdThutVao = BorderFactory.createEmptyBorder(15, 10, 15, 10);
		pnNhapThongTin.setBorder(BorderFactory.createCompoundBorder(brdVienDen, brdThutVao));

		boxRow1.add(lbTenKhach);
		boxRow1.add(tfTenKhach);
		boxRow1.add(Box.createHorizontalStrut(50));
		boxRow1.add(lbSoDienThoai);
		boxRow1.add(tfSoDienThoai);
		boxRow1.add(Box.createHorizontalGlue());

		boxRow2.add(lbThoiGian);
		boxRow2.add(tfThoiGian);
		boxRow2.add(Box.createHorizontalStrut(50));
		boxRow2.add(lbTrangThai);
		boxRow2.add(cbTrangThai);
		boxRow2.add(Box.createHorizontalGlue());

		pnNhapThongTin.add(boxRow1);
		pnNhapThongTin.add(Box.createVerticalStrut(15));
		pnNhapThongTin.add(boxRow2);
		pnDatBan.add(pnNhapThongTin, BorderLayout.NORTH);

		// --- PHẦN BẢNG DỮ LIỆU ---
		JPanel pnBangDuLieu = new JPanel(new BorderLayout());
		pnBangDuLieu
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0), brdVienDen));

		JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		boxTimKiem.add(lbTimKiem);
		boxTimKiem.add(tfTimKiem);
		boxTimKiem.add(Box.createHorizontalStrut(5));
		boxTimKiem.add(btnTim);
		pnTimKiem.add(boxTimKiem);
		pnBangDuLieu.add(pnTimKiem, BorderLayout.NORTH);

		String[] colNames = { "#", "Mã đặt bàn", "Tên khách", "Số ĐT", "Thời gian", "Trạng thái", "Chi tiết" };
		tmDatBan = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 6;
			}
		};
		tbDatBan = new JTable(tmDatBan);
		tbDatBan.getTableHeader().setFont(fontLabel);
		tbDatBan.setFont(new Font("Arial", Font.PLAIN, 14));
		tbDatBan.setRowHeight(30);

		tbDatBan.getColumnModel().getColumn(0).setMaxWidth(40);
		tbDatBan.getColumnModel().getColumn(6).setMaxWidth(80);

		// Gắn nút vào cột
		tbDatBan.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
		tbDatBan.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

		JScrollPane scrollPane = new JScrollPane(tbDatBan);
		pnBangDuLieu.add(scrollPane, BorderLayout.CENTER);
		pnDatBan.add(pnBangDuLieu, BorderLayout.CENTER);

		// --- PHẦN NÚT CHỨC NĂNG CÓ THÊM ICON ---
		JPanel pnNutChucNang = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		pnNutChucNang.setBorder(brdVienDen);
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
		pnDatBan.add(pnNutChucNang, BorderLayout.SOUTH);

		tbDatBan.getTableHeader().setResizingAllowed(false);
		tbDatBan.getTableHeader().setReorderingAllowed(false);

		return pnDatBan;
	}

	// =========================================================================
	// CLASS HIỂN THỊ NÚT TRONG BẢNG
	// =========================================================================
	class ButtonRenderer extends JButton implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		public ButtonRenderer() {
			setOpaque(true);
			setFont(new Font("Arial", Font.BOLD, 14));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText((value == null) ? "..." : value.toString());
			return this;
		}
	}

	// =========================================================================
	// CLASS BẮT SỰ KIỆN KHI BẤM NÚT TRONG BẢNG
	// =========================================================================
	class ButtonEditor extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;
		protected JButton button;
		private String label;
		private boolean isPushed;
		private int selectedRow;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.setFont(new Font("Arial", Font.BOLD, 14));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			label = (value == null) ? "..." : value.toString();
			button.setText(label);
			isPushed = true;
			selectedRow = row;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (isPushed) {
				String maDB = tbDatBan.getValueAt(selectedRow, 1) != null
						? tbDatBan.getValueAt(selectedRow, 1).toString()
						: "";
				String tenKhach = tbDatBan.getValueAt(selectedRow, 2) != null
						? tbDatBan.getValueAt(selectedRow, 2).toString()
						: "";
				String trangThai = tbDatBan.getValueAt(selectedRow, 5) != null
						? tbDatBan.getValueAt(selectedRow, 5).toString()
						: "";

				new DlgChiTietDatBan(DlgDatBan.this, maDB, tenKhach, trangThai).setVisible(true);
			}
			isPushed = false;
			return label;
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}
	}

	// =========================================================================
	// DIALOG CHI TIẾT (ĐÃ CHUYỂN SANG CHIỀU DỌC)
	// =========================================================================
	class DlgChiTietDatBan extends JDialog {
		private static final long serialVersionUID = 1L;

		public DlgChiTietDatBan(Dialog parent, String maDB, String tenKhach, String trangThai) {
			super(parent, "CHI TIẾT ĐẶT BÀN", true);
			this.setSize(400, 250);
			this.setLocationRelativeTo(parent);

			JPanel pnNoiDung = new JPanel();
			pnNoiDung.setLayout(new BoxLayout(pnNoiDung, BoxLayout.Y_AXIS));
			pnNoiDung.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

			Font fLabel = new Font("Arial", Font.BOLD, 14);
			Font fValue = new Font("Arial", Font.PLAIN, 14);

			pnNoiDung.add(createDetailBox("Mã Đặt Bàn:", maDB, fLabel, fValue));
			pnNoiDung.add(Box.createVerticalStrut(15));
			pnNoiDung.add(createDetailBox("Khách Hàng:", tenKhach, fLabel, fValue));
			pnNoiDung.add(Box.createVerticalStrut(15));
			pnNoiDung.add(createDetailBox("Trạng Thái:", trangThai, fLabel, fValue));

			this.add(pnNoiDung);
		}

		private JPanel createDetailBox(String title, String value, Font fTitle, Font fVal) {
			JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
			p.setMaximumSize(new Dimension(400, 30));

			JLabel lblTitle = new JLabel(title);
			lblTitle.setFont(fTitle);
			lblTitle.setPreferredSize(new Dimension(100, 25));

			JLabel lblValue = new JLabel(value);
			lblValue.setFont(fVal);
			lblValue.setForeground(Color.BLUE);

			p.add(lblTitle);
			p.add(lblValue);
			return p;
		}
	}

	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Lỗi: " + ex.getMessage());
		}
		DlgDatBan dialog = new DlgDatBan(null);
		new controller.DatBanController(dialog);
		dialog.setVisible(true);
	}

	public JTextField getTfTenKhach() {
		return tfTenKhach;
	}

	public JTextField getTfSoDienThoai() {
		return tfSoDienThoai;
	}

	public JTextField getTfThoiGianDat() {
		return tfThoiGian;
	}

	public JComboBox<String> getCbTrangThai() {
		return cbTrangThai;
	}

	public JTextField getTfTimKiem() {
		return tfTimKiem;
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

	public void addBtnTimListener(ActionListener l) {
		btnTim.addActionListener(l);
	}

	public void addBtnLuuListener(ActionListener l) {
		btnLuu.addActionListener(l);
	}
}