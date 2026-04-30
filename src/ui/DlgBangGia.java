package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DlgBangGia extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tbModel;

	private final Color MAU_NEN_CHINH = new Color(242, 233, 216);
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47);
	private final Color MAU_HEADER = new Color(209, 185, 161);
	private Font fontBungeeBase;

	public DlgBangGia(JFrame parent) {
		super(parent, "BẢNG GIÁ", true);
		this.setSize(800, 700);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL imageUrl = getClass().getResource("/images/price-list.png");
		if (imageUrl != null) {
			this.setIconImage(new ImageIcon(imageUrl).getImage());
		}

		loadCustomFont();
		khoiTaoGiaoDien();
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

	public void khoiTaoGiaoDien() {
		JPanel pnMainLayout = new JPanel(new BorderLayout());
		pnMainLayout.setBackground(MAU_NEN_CHINH);
		pnMainLayout.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 4));
		this.setContentPane(pnMainLayout);

		JPanel pnTieuDe = new JPanel();
		pnTieuDe.setBackground(MAU_NEN_CHINH);
		pnTieuDe.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		pnMainLayout.add(pnTieuDe, BorderLayout.NORTH);

		JLabel lbTieuDe = new JLabel("BẢNG GIÁ HÀNG HÓA", SwingConstants.CENTER);
		lbTieuDe.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 28f));
		lbTieuDe.setForeground(MAU_NAU_VIEN);
		pnTieuDe.add(lbTieuDe);

		String[] tenCot = { "#", "Mã SP", "Tên SP", "Tên DM", "Giá bán", "Trạng thái" };
		tbModel = new DefaultTableModel(tenCot, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(tbModel);

		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		table.getTableHeader().setBackground(MAU_HEADER);
		table.getTableHeader().setForeground(MAU_NAU_VIEN);
		table.getTableHeader().setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		table.setRowHeight(30);
		table.setBackground(MAU_NEN_CHINH);
		table.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2));

		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.getColumnModel().getColumn(5).setPreferredWidth(90);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

		// CHIÊU THỨC CẤP MÀU SẮC CHO TRẠNG THÁI
		table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setHorizontalAlignment(JLabel.CENTER);

				if (value != null) {
					String status = value.toString();
					// Nếu "Ngừng bán" thì nhuộm Đỏ, "Đang bán" thì nhuộm Xanh
					if (status.equalsIgnoreCase("Ngừng bán")) {
						c.setForeground(Color.RED);
					} else if (status.equalsIgnoreCase("Đang bán")) {
						c.setForeground(new Color(0, 153, 0)); // Màu xanh lá đậm để không chói lóa
					} else {
						c.setForeground(Color.BLACK); // Phòng hờ lỗi
					}
				}

				// Khi người dùng click chọn dòng thì chữ vẫn phải giữ nguyên màu hoặc theo màu
				// hệ thống
				if (isSelected) {
					c.setBackground(table.getSelectionBackground());
				} else {
					c.setBackground(table.getBackground());
				}

				return c;
			}
		});

		JScrollPane scrllPane = new JScrollPane(table);
		scrllPane.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3));
		pnMainLayout.add(scrllPane, BorderLayout.CENTER);

		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
	}

	public DefaultTableModel getTbModel() {
		return tbModel;
	}
}