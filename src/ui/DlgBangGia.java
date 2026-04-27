
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DlgBangGia extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tbModel;

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

		khoiTaoGiaoDien();
//		loadDuLieu(); // Gọi hàm châm dữ liệu vô bảng ngay khi mở Form
	}

	public void khoiTaoGiaoDien() {
		JPanel pnTieuDe = new JPanel();
		this.add(pnTieuDe, BorderLayout.NORTH);
		JLabel lbTieuDe = new JLabel("BẢNG GIÁ HÀNG HÓA");
		pnTieuDe.add(lbTieuDe);
		lbTieuDe.setFont(new Font("Arial", Font.BOLD, 18));

		String[] tenCot = { "#", "Mã SP", "Tên SP", "Tên DM", "Giá bán", "Trạng thái" };
		tbModel = new DefaultTableModel(tenCot, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Khóa bảng không cho người ta click đúp vô sửa bậy
			}
		};
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
		table.setRowHeight(30); // Nới dòng ra xí cho dễ dòm

		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.getColumnModel().getColumn(5).setPreferredWidth(90);

		// Căn giữa cho mấy cột STT, Mã, Trạng thái
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		// Căn lề phải cho cột Tiền
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
	}

	// ===============================================
	// HÀM CHÂM DỮ LIỆU TỪ DATABASE LÊN BẢNG
	// ===============================================
//	private void loadDuLieu() {
//		SanPhamDAO spDAO = new SanPhamDAO();
//		DanhMucDAO dmDAO = new DanhMucDAO();
//
//		// Bợ nguyên kho dữ liệu lên
//		List<SanPham> danhSachSP = spDAO.findAll();
//		List<DanhMuc> danhSachDM = dmDAO.findAll();
//
//		// Tạo Map để tra cứu Tên Danh Mục cho lẹ (ví dụ DM01 -> Cà phê)
//		Map<String, String> mapDM = new HashMap<>();
//		if (danhSachDM != null) {
//			for (DanhMuc dm : danhSachDM) {
//				mapDM.put(dm.getMaDanhMuc(), dm.getTenDanhMuc());
//			}
//		}
//
//		tbModel.setRowCount(0); // Xóa trắng bảng cũ trước khi đắp dữ liệu mới
//		int stt = 1;
//		DecimalFormat df = new DecimalFormat("#,### VNĐ");
//
//		if (danhSachSP != null) {
//			for (SanPham sp : danhSachSP) {
//				// Lấy Tên danh mục từ Map
//				String tenDM = mapDM.getOrDefault(sp.getMaDanhMuc(), "Không rõ");
//
//				// Định dạng lại tiền cho có dấu phẩy đồ
//				String giaBan = df.format(sp.getGiaBan());
//
//				// Đổi True/False thành chữ Đang bán/Ngừng bán
//				String trangThai = sp.isTrangThai() ? "Đang bán" : "Ngừng bán";
//
//				// Quăng nguyên dòng vô bảng
//				tbModel.addRow(new Object[] { stt++, sp.getMaSanPham(), sp.getTenSanPham(), tenDM, giaBan, trangThai });
//			}
//		}
//	}

	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Failed to initialize LaF");
		}
		DlgBangGia run = new DlgBangGia(null);
		run.setVisible(true);
	}

	public DefaultTableModel getTbModel() {
		return tbModel;
	}
}