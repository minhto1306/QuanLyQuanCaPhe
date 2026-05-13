package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class DlgCaLamViec extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel pnMain;
	private JButton btnTroVe, btnTiep, btnHienTai, btnLuu;
	private JLabel lbTuanHienTai;
	private JTable tbCaLamViec;
	private DefaultTableModel tmCaLamViec;

	private LocalDate ngayBatDauTuan;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private List<String> danhSachNhanVien = new ArrayList<>();

	private boolean quyenChinhSua = true;

	private final Color MAU_NEN_CHINH = new Color(242, 233, 216);
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47);
	private final Color MAU_NAU_NUT = new Color(110, 75, 59);
	private final Color MAU_HEADER = new Color(209, 185, 161);
	private Font fontBungeeBase;

	// CHỨC NĂNG: Khởi tạo hộp thoại giao diện quản lý lịch làm việc.
	public DlgCaLamViec(JFrame parent) {
		super(parent, "QUẢN LÝ LỊCH LÀM VIỆC", true);
		this.setSize(1450, 700);
		this.setLocationRelativeTo(parent);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		ngayBatDauTuan = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		loadCustomFont();
		khoiTaoGiaoDien();
		capNhatDuLieuTuan();
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

	// CHỨC NĂNG: Thiết lập định dạng chuẩn cho các nút tương tác.
	private void setupRetroButton(JButton btn) {
		btn.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 18f));
		btn.setBackground(MAU_NAU_NUT);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3));
		btn.setPreferredSize(new Dimension(140, 40));
	}

	// CHỨC NĂNG: Khởi tạo và thiết lập các thành phần giao diện.
	private void khoiTaoGiaoDien() {
		pnMain = new JPanel(new BorderLayout(5, 5));
		pnMain.setBackground(MAU_NAU_VIEN);
		pnMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel pnNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		pnNorth.setBackground(MAU_HEADER);
		pnNorth.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 4));

		btnHienTai = new JButton("HIỆN TẠI");
		setupRetroButton(btnHienTai);
		btnTroVe = new JButton("< TRỞ VỀ");
		setupRetroButton(btnTroVe);
		btnTiep = new JButton("TIẾP >");
		setupRetroButton(btnTiep);

		lbTuanHienTai = new JLabel();
		lbTuanHienTai.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbTuanHienTai.setForeground(MAU_NAU_VIEN);
		lbTuanHienTai.setPreferredSize(new Dimension(350, 30));
		lbTuanHienTai.setHorizontalAlignment(SwingConstants.CENTER);

		pnNorth.add(btnHienTai);
		pnNorth.add(btnTroVe);
		pnNorth.add(lbTuanHienTai);
		pnNorth.add(btnTiep);
		pnMain.add(pnNorth, BorderLayout.NORTH);

		String[] colNames = { "Ca làm việc", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật" };
		tmCaLamViec = new DefaultTableModel(colNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tbCaLamViec = new JTable(tmCaLamViec);
		tbCaLamViec.setRowHeight(150);
		tbCaLamViec.setFont(new Font("Tahoma", Font.BOLD, 14));
		tbCaLamViec.setBackground(MAU_NEN_CHINH);
		tbCaLamViec.setGridColor(MAU_NAU_VIEN);
		tbCaLamViec.setShowGrid(true);
		tbCaLamViec.setCellSelectionEnabled(true);
		tbCaLamViec.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = tbCaLamViec.getTableHeader();
		header.setFont(new Font("Tahoma", Font.BOLD, 15));
		header.setBackground(MAU_HEADER);
		header.setForeground(MAU_NAU_VIEN);
		header.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));
		header.setPreferredSize(new Dimension(header.getWidth(), 50));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				c.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 14f));
				c.setForeground(MAU_NAU_VIEN);
				return c;
			}
		};
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		tbCaLamViec.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbCaLamViec.getColumnModel().getColumn(0).setPreferredWidth(160);
		tbCaLamViec.getColumnModel().getColumn(0).setMaxWidth(200);

		MultiLineCellRenderer multiRenderer = new MultiLineCellRenderer();
		for (int i = 1; i < 8; i++) {
			tbCaLamViec.getColumnModel().getColumn(i).setPreferredWidth(120);
			tbCaLamViec.getColumnModel().getColumn(i).setCellRenderer(multiRenderer);
		}

		tbCaLamViec.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbCaLamViec.rowAtPoint(e.getPoint());
				int col = tbCaLamViec.columnAtPoint(e.getPoint());
				if (col <= 0 || row < 0)
					return;

				if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
					if (quyenChinhSua) {
						JPopupMenu popup = taoMenuChonNhanVien(row, col);
						if (popup != null)
							popup.show(tbCaLamViec, e.getX(), e.getY());
					}
				} else if (e.getClickCount() == 2) {
					Object val = tmCaLamViec.getValueAt(row, col);
					String text = (val == null || val.toString().trim().isEmpty()) ? "Trống!" : val.toString();
					JOptionPane.showMessageDialog(pnMain, "Nhân viên trực:\n" + text, "Chi tiết",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		tbCaLamViec.getTableHeader().setResizingAllowed(false);
		tbCaLamViec.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(tbCaLamViec);
		scrollPane.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 4));
		pnMain.add(scrollPane, BorderLayout.CENTER);

		JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		pnSouth.setBackground(MAU_NEN_CHINH);
		pnSouth.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 4));

		btnLuu = new JButton("LƯU PHÂN CÔNG");
		btnLuu.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 20f));
		btnLuu.setBackground(new Color(56, 163, 42));
		btnLuu.setForeground(Color.WHITE);
		btnLuu.setFocusPainted(false);
		btnLuu.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3));
		btnLuu.setPreferredSize(new Dimension(250, 50));

		pnSouth.add(btnLuu);
		pnMain.add(pnSouth, BorderLayout.SOUTH);

		this.setContentPane(pnMain);
	}

	// CHỨC NĂNG: Khởi tạo danh sách thao tác chọn nhân viên cho từng ô.
	private JPopupMenu taoMenuChonNhanVien(int row, int col) {
		if (danhSachNhanVien == null || danhSachNhanVien.isEmpty())
			return null;
		JPopupMenu popup = new JPopupMenu();
		JMenuItem itemXoa = new JMenuItem("Xóa trống ô này");
		itemXoa.setForeground(Color.RED);
		itemXoa.addActionListener(e -> tmCaLamViec.setValueAt("", row, col));
		popup.add(itemXoa);
		popup.addSeparator();

		for (String ten : danhSachNhanVien) {
			JMenuItem item = new JMenuItem("Thêm: " + ten);
			item.addActionListener(e -> {
				String hienTai = (String) tmCaLamViec.getValueAt(row, col);
				if (hienTai == null || hienTai.trim().isEmpty()) {
					tmCaLamViec.setValueAt(ten, row, col);
				} else {
					if (!hienTai.contains(ten)) {
						tmCaLamViec.setValueAt(hienTai + "\n" + ten, row, col);
					}
				}
			});
			popup.add(item);
		}
		return popup;
	}

	// CHỨC NĂNG: Cập nhật hiển thị và dữ liệu cho tuần làm việc mới.
	public void capNhatDuLieuTuan() {
		LocalDate ngayKetThucTuan = ngayBatDauTuan.plusDays(6);
		lbTuanHienTai.setText("Tuần: " + ngayBatDauTuan.format(formatter) + " - " + ngayKetThucTuan.format(formatter));

		JTableHeader header = tbCaLamViec.getTableHeader();
		String[] daysOfWeek = { "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật" };

		for (int i = 0; i < 7; i++) {
			LocalDate currentDay = ngayBatDauTuan.plusDays(i);
			tbCaLamViec.getColumnModel().getColumn(i + 1).setHeaderValue(
					"<html><center>" + daysOfWeek[i] + "<br>" + currentDay.format(formatter) + "</center></html>");
		}
		header.repaint();
		tmCaLamViec.setRowCount(0);

		Object[] caSang = { "SÁNG (06:00 - 12:00)", "", "", "", "", "", "", "" };
		Object[] caChieu = { "CHIỀU (12:00 - 18:00)", "", "", "", "", "", "", "" };
		Object[] caToi = { "TỐI (18:00 - 00:00)", "", "", "", "", "", "", "" };

		tmCaLamViec.addRow(caSang);
		tmCaLamViec.addRow(caChieu);
		tmCaLamViec.addRow(caToi);
	}

	// CHỨC NĂNG: Phân quyền sử dụng các tính năng dựa trên vai trò của nhân viên.
	public void phanQuyen(String vaiTro) {
		if (vaiTro != null && vaiTro.toLowerCase().contains("thu")) {
			quyenChinhSua = false;
			btnLuu.setVisible(false);
		} else {
			quyenChinhSua = true;
			btnLuu.setVisible(true);
		}
	}

	public void setDanhSachNhanVien(List<String> ds) {
		this.danhSachNhanVien = ds;
	}

	public DefaultTableModel getTmCaLamViec() {
		return tmCaLamViec;
	}

	public JTable getTbCaLamViec() {
		return tbCaLamViec;
	}

	public LocalDate getNgayBatDauTuan() {
		return ngayBatDauTuan;
	}

	public void setNgayBatDauTuan(LocalDate d) {
		this.ngayBatDauTuan = d;
	}

	public void addBtnTroVeListener(java.awt.event.ActionListener l) {
		btnTroVe.addActionListener(l);
	}

	public void addBtnTiepListener(java.awt.event.ActionListener l) {
		btnTiep.addActionListener(l);
	}

	public void addBtnHienTaiListener(java.awt.event.ActionListener l) {
		btnHienTai.addActionListener(l);
	}

	public void addBtnLuuListener(java.awt.event.ActionListener l) {
		btnLuu.addActionListener(l);
	}

	// Lớp phụ trợ phục vụ hiển thị nội dung nhiều dòng trong một ô bảng
	class MultiLineCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		private final Insets PADDING = new Insets(10, 10, 10, 10);

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setFont(new Font("Tahoma", Font.BOLD, 13));
			if (value != null && !value.toString().trim().isEmpty()) {
				String[] nhanViens = value.toString().split("\n");
				StringBuilder html = new StringBuilder("<html><div style='text-align: center; line-height: 1.8;'>");
				for (String nv : nhanViens) {
					html.append("<span style='color: #593a2f;'>").append(nv.trim()).append("</span><br>");
				}
				html.append("</div></html>");
				setText(html.toString());
			} else {
				setText("");
			}
			setHorizontalAlignment(SwingConstants.CENTER);
			setBorder(BorderFactory.createEmptyBorder(PADDING.top, PADDING.left, PADDING.bottom, PADDING.right));
			return this;
		}
	}
}