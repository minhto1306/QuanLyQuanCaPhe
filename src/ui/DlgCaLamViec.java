package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.formdev.flatlaf.FlatLightLaf;

public class DlgCaLamViec extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel pnMain;
	private JButton btnTroVe, btnTiep, btnHienTai, btnLuu;
	private JLabel lbTuanHienTai;
	private JTable tbCaLamViec;
	private DefaultTableModel tmCaLamViec;

	private LocalDate ngayBatDauTuan;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public DlgCaLamViec(JFrame parent) {
		super(parent, "QUẢN LÝ LỊCH LÀM VIỆC", true);
		this.setSize(1100, 700);
		this.setLocationRelativeTo(parent);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL urllogo = getClass().getResource("/images/logo.png");
		if (urllogo != null) {
			this.setIconImage(new ImageIcon(urllogo).getImage());
		}

		ngayBatDauTuan = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

		khoiTaoGiaoDien();
		capNhatDuLieuTuan();
	}

	private void khoiTaoGiaoDien() {
		pnMain = new JPanel(new BorderLayout(5, 5));
		pnMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel pnNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		pnNorth.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		Font fontBtn = new Font("Arial", Font.BOLD, 14);

		btnHienTai = new JButton("Hiện tại");
		btnHienTai.setFont(fontBtn);

		btnTroVe = new JButton("< Trở về");
		btnTroVe.setFont(fontBtn);

		btnTiep = new JButton("Tiếp >");
		btnTiep.setFont(fontBtn);

		lbTuanHienTai = new JLabel();
		lbTuanHienTai.setFont(new Font("Arial", Font.BOLD, 16));
		lbTuanHienTai.setForeground(Color.BLUE);
		lbTuanHienTai.setPreferredSize(new Dimension(300, 30));
		lbTuanHienTai.setHorizontalAlignment(SwingConstants.CENTER);

		pnNorth.add(btnHienTai);
		pnNorth.add(btnTroVe);
		pnNorth.add(lbTuanHienTai);
		pnNorth.add(btnTiep);

		pnMain.add(pnNorth, BorderLayout.NORTH);

		String[] colNames = { "Ca làm việc", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật" };
		tmCaLamViec = new DefaultTableModel(colNames, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tbCaLamViec = new JTable(tmCaLamViec);
		tbCaLamViec.setRowHeight(150);
		tbCaLamViec.setFont(new Font("Arial", Font.PLAIN, 14));
		tbCaLamViec.setGridColor(Color.LIGHT_GRAY);
		tbCaLamViec.setShowGrid(true);

		tbCaLamViec.setCellSelectionEnabled(true);
		tbCaLamViec.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTableHeader header = tbCaLamViec.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 14));
		header.setPreferredSize(new Dimension(header.getWidth(), 40));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				c.setFont(new Font("Arial", Font.BOLD, 14));
				return c;
			}
		};
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		tbCaLamViec.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbCaLamViec.getColumnModel().getColumn(0).setPreferredWidth(150);
		tbCaLamViec.getColumnModel().getColumn(0).setMaxWidth(200);

		for (int i = 1; i < 8; i++) {
			tbCaLamViec.getColumnModel().getColumn(i).setPreferredWidth(120);
		}

		tbCaLamViec.getTableHeader().setResizingAllowed(false);
		tbCaLamViec.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(tbCaLamViec);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnMain.add(scrollPane, BorderLayout.CENTER);

		JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		pnSouth.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		btnLuu = new JButton("Lưu Phân Công");
		btnLuu.setFont(fontBtn);
		ImageIcon iconLuu = taoIconThuNho("/images/save.png", 20, 20);
		if (iconLuu != null)
			btnLuu.setIcon(iconLuu);

		pnSouth.add(btnLuu);
		pnMain.add(pnSouth, BorderLayout.SOUTH);

		this.add(pnMain);

		btnTiep.addActionListener(e -> {
			ngayBatDauTuan = ngayBatDauTuan.plusDays(7);
			capNhatDuLieuTuan();
		});

		btnTroVe.addActionListener(e -> {
			ngayBatDauTuan = ngayBatDauTuan.minusDays(7);
			capNhatDuLieuTuan();
		});

		btnHienTai.addActionListener(e -> {
			ngayBatDauTuan = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
			capNhatDuLieuTuan();
		});
	}

	private void capNhatDuLieuTuan() {
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

		Object[] caSang = { "Sáng (06:00 - 12:00)", "", "", "", "", "", "", "" };
		Object[] caChieu = { "Chiều (12:00 - 18:00)", "", "", "", "", "", "", "" };
		Object[] caToi = { "Tối (18:00 - 00:00)", "", "", "", "", "", "", "" };

		tmCaLamViec.addRow(caSang);
		tmCaLamViec.addRow(caChieu);
		tmCaLamViec.addRow(caToi);
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

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Failed to initialize LaF");
		}

		new DlgCaLamViec(null).setVisible(true);
	}
}