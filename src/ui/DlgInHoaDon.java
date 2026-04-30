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

		// Chỉ giữ 1 nút Lưu & Đóng như Qẹoooo muốn hỉ
		JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnLuuDong = new JButton("Lưu & Đóng");
		btnLuuDong.setFont(new Font("Arial", Font.BOLD, 14));
		pnBottom.add(btnLuuDong);
		add(pnBottom, BorderLayout.SOUTH);

		veHoaDon(hd, tenNhanVien, tenBan, danhSachMon);

		// Bấm là đóng thôi vì dữ liệu đã lưu ở Controller rồi
		btnLuuDong.addActionListener(e -> this.dispose());
	}

	private void veHoaDon(HoaDon hd, String tenNhanVien, String tenBan, List<Object[]> danhSachMon) {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#,###");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		sb.append("                  MIO COFFEE\n");
		sb.append("      611 Thống Nhất, P16, Q.Gò Vấp, HCM\n");
		sb.append("----------------------------------------------\n");
		sb.append("Mã HĐ   : ").append(hd.getMaHoaDon()).append("\n");
		sb.append("Ngày lập: ").append(hd.getThoiGianLap().format(dtf)).append("\n");
		sb.append("Thu ngân: ").append(tenNhanVien).append("\n"); // Đổ đúng tên người trực vô đây
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
		sb.append(String.format("%-28s %15s\n", "Tổng cộng:", df.format(hd.getTongTien())));
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