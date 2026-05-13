package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import entity.HoaDon;

public class DlgInHoaDon extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextArea txtBill;
	private JButton btnLuuDong;

	// CHỨC NĂNG: Khởi tạo hộp thoại hiển thị chi tiết hóa đơn.
	public DlgInHoaDon(JFrame parent, HoaDon hd, String tenNhanVien, String tenBan, List<Object[]> danhSachMon) {
		super(parent, "📄 CHI TIẾT HÓA ĐƠN - MIO COFFEE", true);
		setSize(420, 650);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());

		txtBill = new JTextArea();
		txtBill.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtBill.setEditable(false);
		txtBill.setBorder(new EmptyBorder(10, 10, 10, 10));

		JScrollPane scrollPane = new JScrollPane(txtBill);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);

		JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnLuuDong = new JButton("Lưu & Đóng");
		btnLuuDong.setFont(new Font("Arial", Font.BOLD, 14));
		pnBottom.add(btnLuuDong);
		add(pnBottom, BorderLayout.SOUTH);

		veHoaDon(hd, tenNhanVien, tenBan, danhSachMon);

		btnLuuDong.addActionListener(e -> this.dispose());
	}

	// CHỨC NĂNG: Xuất thông tin hóa đơn ra định dạng văn bản để hiển thị.
	private void veHoaDon(HoaDon hd, String tenNhanVien, String tenBan, List<Object[]> danhSachMon) {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#,###");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		double tongTienMon = 0;
		for (Object[] row : danhSachMon) {
			tongTienMon += Double.parseDouble(row[3].toString());
		}

		double phuThu = hd.getTongTien() - tongTienMon;
		if (phuThu < 0)
			phuThu = 0;

		sb.append("                  MIO COFFEE\n");
		sb.append("      611 Thống Nhất, P16, Q.Gò Vấp, HCM\n");
		sb.append("----------------------------------------------\n");
		sb.append("Mã HĐ   : ").append(hd.getMaHoaDon()).append("\n");
		sb.append("Ngày lập: ").append(hd.getThoiGianLap().format(dtf)).append("\n");
		sb.append("Thu ngân: ").append(tenNhanVien).append("\n");
		sb.append("Bàn     : ").append(tenBan).append("\n");

		sb.append("----------------------------------------------\n");
		sb.append(String.format("%-18s %-4s %-9s %-11s\n", "Tên món", "SL", "Đơn giá", "Thành tiền"));
		sb.append("----------------------------------------------\n");

		for (Object[] row : danhSachMon) {
			String tenMon = row[0].toString();
			if (tenMon.length() > 17)
				tenMon = tenMon.substring(0, 14) + "...";
			sb.append(String.format("%-18s %-4s %-9s %10s\n", tenMon, row[1],
					df.format(Double.parseDouble(row[2].toString())),
					df.format(Double.parseDouble(row[3].toString()))));
		}

		sb.append("----------------------------------------------\n");

		sb.append(String.format("%-28s %15s\n", "Tổng tiền món:", df.format(tongTienMon)));

		if (phuThu > 0) {
			sb.append(String.format("%-28s %15s\n", "Phụ thu :", df.format(phuThu)));
		}

		sb.append(String.format("%-28s %15s\n", "Thuế VAT:", df.format(hd.getThueVAT())));
		sb.append(String.format("%-28s %15s\n", "Giảm giá:", df.format(hd.getGiamGia())));
		sb.append("----------------------------------------------\n");
		sb.append(String.format("%-25s %18s VNĐ\n", "THÀNH TIỀN:", df.format(hd.getThanhTien())));
		sb.append("----------------------------------------------\n");
		sb.append("   Cảm ơn quý khách đã lựa chọn quán chúng tôi,\n");
		sb.append("          xin chào và hẹn gặp lại!\n");

		txtBill.setText(sb.toString());
	}
}