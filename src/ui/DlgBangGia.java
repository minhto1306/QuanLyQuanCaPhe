package ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DlgBangGia extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tbModel;

	public DlgBangGia(JFrame parent) {
		super(parent, "BẢNG GIÁ", true);
		this.setSize(600, 700);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		java.net.URL imageUrl = getClass().getResource("/images/price-list.png");
		if (imageUrl != null) {
			this.setIconImage(new ImageIcon(imageUrl).getImage());
		}

		khoiTaoGiaoDien();
	}

	public void khoiTaoGiaoDien() {
		JPanel pnTieuDe = new JPanel();
		this.add(pnTieuDe, BorderLayout.NORTH);
		JLabel lbTieuDe = new JLabel("BẢNG GIÁ HÀNG HÓA");
		pnTieuDe.add(lbTieuDe);
		lbTieuDe.setFont(new Font("Arial", Font.BOLD, 18));

		String[] tenCot = { "#", "Mã SP", "Tên SP", "Tên DM", "Giá bán", "Trạng thái" };
		tbModel = new DefaultTableModel(tenCot, 0);
		table = new JTable(tbModel);

		JPanel pnTrai = new JPanel();
		this.add(pnTrai, BorderLayout.WEST);
		JPanel pnPhai = new JPanel();
		this.add(pnPhai, BorderLayout.EAST);
		JPanel pnDuoi = new JPanel();
		this.add(pnDuoi, BorderLayout.SOUTH);

		JScrollPane scrllPane = new JScrollPane(table);
		this.add(scrllPane, BorderLayout.CENTER);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.setRowHeight(25);

		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.getColumnModel().getColumn(5).setPreferredWidth(90);
	}

	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Failed to initialize LaF");
		}
		DlgBangGia run = new DlgBangGia(null);
		run.setVisible(true);
	}
}