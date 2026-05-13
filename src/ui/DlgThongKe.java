package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.io.InputStream;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DlgThongKe extends JDialog {

	private static final long serialVersionUID = 1L;
	private final Color MAU_NEN_CHINH = new Color(242, 233, 216);
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47);
	private final Color MAU_HEADER = new Color(209, 185, 161);
	private Font fontBungeeBase;

	// Khai báo các biến giao diện cho các nhãn thống kê
	private JLabel lbTongDoanhThu, lbTongSanPham, lbTongKhach;
	private BieuDoDuong bieuDoPanel;
	private JButton btnTuanTruoc, btnTuanNay;

	// CHỨC NĂNG: Khởi tạo giao diện hộp thoại thống kê
	public DlgThongKe(JFrame parent) {
		super(parent, "THỐNG KÊ DOANH THU THEO TUẦN", true);
		this.setSize(1200, 750);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);

		loadCustomFont();
		khoiTaoGiaoDien();
	}

	// CHỨC NĂNG: Tải font chữ tùy chỉnh
	private void loadCustomFont() {
		try {
			InputStream is = getClass().getResourceAsStream("/font/Bungee-Regular.ttf");
			fontBungeeBase = (is != null) ? Font.createFont(Font.TRUETYPE_FONT, is)
					: new Font("Impact", Font.PLAIN, 24);
		} catch (Exception e) {
			fontBungeeBase = new Font("Impact", Font.PLAIN, 24);
		}
	}

	// CHỨC NĂNG: Xây dựng và bố trí các thành phần trên giao diện
	private void khoiTaoGiaoDien() {
		JPanel pnMain = new JPanel(new BorderLayout(10, 10));
		pnMain.setBackground(MAU_NEN_CHINH);
		pnMain.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 6),
				BorderFactory.createEmptyBorder(15, 15, 15, 15)));

		// Phân chia cấu trúc lưới thành 3 cột để hiển thị các thẻ thống kê
		JPanel pnTop = new JPanel(new GridLayout(1, 3, 20, 0));
		pnTop.setOpaque(false);
		pnTop.setPreferredSize(new Dimension(0, 100));

		lbTongDoanhThu = taoCardThongKe("TỔNG DOANH THU", "0 VNĐ", new Color(193, 71, 71));
		lbTongSanPham = taoCardThongKe("SẢN PHẨM BÁN RA", "0", MAU_NAU_VIEN);
		lbTongKhach = taoCardThongKe("LƯỢT KHÁCH", "0", new Color(52, 138, 167));

		pnTop.add((JComponent) lbTongDoanhThu.getParent());
		pnTop.add((JComponent) lbTongSanPham.getParent());
		pnTop.add((JComponent) lbTongKhach.getParent());
		pnMain.add(pnTop, BorderLayout.NORTH);

		// Khu vực trung tâm: Hiển thị biểu đồ
		bieuDoPanel = new BieuDoDuong();
		bieuDoPanel.setBackground(Color.WHITE);
		bieuDoPanel.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3));
		pnMain.add(bieuDoPanel, BorderLayout.CENTER);

		// Khu vực phía dưới: Chứa các nút thao tác chức năng
		JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		pnBottom.setOpaque(false);

		btnTuanTruoc = new JButton("< TUẦN TRƯỚC");
		btnTuanNay = new JButton("TUẦN NÀY");

		setupRetroButton(btnTuanTruoc);
		setupRetroButton(btnTuanNay);

		pnBottom.add(btnTuanTruoc);
		pnBottom.add(btnTuanNay);
		pnMain.add(pnBottom, BorderLayout.SOUTH);

		this.setContentPane(pnMain);
	}

	// CHỨC NĂNG: Khởi tạo và định dạng thành phần thẻ thống kê
	private JLabel taoCardThongKe(String title, String value, Color valueColor) {
		JPanel card = new JPanel(new GridLayout(2, 1));
		card.setBackground(MAU_HEADER);
		card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3),
				BorderFactory.createEmptyBorder(10, 15, 10, 15)));

		JLabel lbTitle = new JLabel(title);
		lbTitle.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 20f));
		lbTitle.setForeground(MAU_NAU_VIEN);

		JLabel lbValue = new JLabel(value);
		lbValue.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 26f));
		lbValue.setForeground(valueColor);

		card.add(lbTitle);
		card.add(lbValue);
		return lbValue;
	}

	// CHỨC NĂNG: Thiết lập thuộc tính giao diện chuẩn cho các nút bấm
	private void setupRetroButton(JButton btn) {
		btn.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 18f));
		btn.setBackground(MAU_NAU_VIEN);
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setPreferredSize(new Dimension(180, 45));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	// CHỨC NĂNG: Cập nhật dữ liệu vào các nhãn hiển thị và biểu đồ
	public void capNhatDuLieu(String tongDoanhThu, String tongSanPham, String tongKhach, List<Double> doanhThuCacNgay,
			List<String> tenCacNgay) {
		lbTongDoanhThu.setText(tongDoanhThu);
		lbTongSanPham.setText(tongSanPham);
		lbTongKhach.setText(tongKhach);
		bieuDoPanel.setDuLieu(doanhThuCacNgay, tenCacNgay);
	}

	public void addTuanTruocListener(java.awt.event.ActionListener l) {
		btnTuanTruoc.addActionListener(l);
	}

	public void addTuanNayListener(java.awt.event.ActionListener l) {
		btnTuanNay.addActionListener(l);
	}

	// CHỨC NĂNG: Thành phần vẽ biểu đồ đường hỗ trợ hiển thị chi tiết điểm dữ liệu
	class BieuDoDuong extends JPanel {
		private static final long serialVersionUID = 1L;
		private java.util.List<Double> scores;
		private java.util.List<String> labels;

		// Danh sách lưu trữ tọa độ các điểm mốc trên biểu đồ để xử lý sự kiện
		private java.util.List<Point> diemCham = new java.util.ArrayList<>();
		private int clickedIndex = -1; // Biểu thị chưa có điểm nào được chọn

		private final int paddingLeft = 130;
		private final int paddingRight = 40;
		private final int paddingTop = 40;
		private final int paddingBottom = 70;

		public BieuDoDuong() {
			// Đăng ký bộ lắng nghe sự kiện nhấp chuột
			this.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (diemCham == null || diemCham.isEmpty())
						return;

					clickedIndex = -1;
					for (int i = 0; i < diemCham.size(); i++) {
						Point p = diemCham.get(i);
						// Kiểm tra xem vị trí nhấp chuột có nằm trong phạm vi 15 pixel so với điểm mốc
						// không
						if (p.distance(e.getPoint()) <= 15) {
							clickedIndex = i;
							break;
						}
					}
					repaint(); // Vẽ lại giao diện để cập nhật thông tin hiển thị
				}
			});
		}

		public void setDuLieu(java.util.List<Double> scores, java.util.List<String> labels) {
			this.scores = scores;
			this.labels = labels;
			this.clickedIndex = -1; // Đặt lại trạng thái lựa chọn khi cập nhật dữ liệu mới
			repaint();
		}

		private String formatTienFull(double tien) {
			java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
			return df.format(tien);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			if (scores == null || scores.isEmpty())
				return;

			// Xóa danh sách tọa độ cũ trước khi tính toán lại
			diemCham.clear();

			double yMin = 0;
			double yMax = 40000000.0;
			int numYDivisions = 20;

			int width = getWidth();
			int height = getHeight();

			int chartWidth = width - paddingLeft - paddingRight;
			int chartHeight = height - paddingTop - paddingBottom;

			double xScale = (double) chartWidth / (scores.size() - 1);
			double yScale = (double) chartHeight / (yMax - yMin);

			// Bước 1: Vẽ khung biểu đồ
			g2.setColor(MAU_NAU_VIEN);
			g2.setStroke(new BasicStroke(1.5f));
			g2.drawRect(paddingLeft, paddingTop, chartWidth, chartHeight);

			// Bước 2: Hiển thị lưới ngang
			g2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			for (int i = 0; i <= numYDivisions; i++) {
				int y = paddingTop + chartHeight - (i * chartHeight / numYDivisions);
				if (i > 0 && i < numYDivisions) {
					g2.setColor(new Color(210, 210, 210));
					g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
							new float[] { 5f }, 0.0f));
					g2.drawLine(paddingLeft, y, width - paddingRight, y);
				}

				g2.setColor(MAU_NAU_VIEN);
				double currentVal = yMin + (yMax - yMin) * ((double) i / numYDivisions);
				String yLabel = formatTienFull(currentVal);
				FontMetrics fm = g2.getFontMetrics();
				int labelWidth = fm.stringWidth(yLabel);
				g2.drawString(yLabel, paddingLeft - labelWidth - 10, y + (fm.getAscent() / 2) - 2);
			}

			// Bước 3: Định cấu hình hiển thị tiêu đề trục Y theo chiều dọc
			String yTitle = "SỐ TIỀN (VNĐ)";
			g2.setFont(new Font("Tahoma", Font.BOLD, 14));
			java.awt.geom.AffineTransform orig = g2.getTransform();
			g2.rotate(-Math.PI / 2);
			FontMetrics fmTitle = g2.getFontMetrics();
			int yTitleWidth = fmTitle.stringWidth(yTitle);
			g2.drawString(yTitle, -(paddingTop + chartHeight / 2 + yTitleWidth / 2), 35);
			g2.setTransform(orig);

			// Bước 4: Hiển thị trục X
			g2.setStroke(new BasicStroke(1f));
			g2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			for (int i = 0; i < scores.size(); i++) {
				int x = paddingLeft + (int) (i * xScale);

				g2.setColor(MAU_NAU_VIEN);
				g2.drawLine(x, height - paddingBottom, x, height - paddingBottom + 5);

				if (i > 0 && i < scores.size() - 1) {
					g2.setColor(new Color(230, 230, 230));
					g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
							new float[] { 5f }, 0.0f));
					g2.drawLine(x, paddingTop, x, height - paddingBottom);
					g2.setStroke(new BasicStroke(1f));
				}

				g2.setColor(MAU_NAU_VIEN);
				String xLabel = labels.get(i);
				FontMetrics fm = g2.getFontMetrics();
				int labelWidth = fm.stringWidth(xLabel);
				g2.drawString(xLabel, x - labelWidth / 2, height - paddingBottom + 25);
			}

			String xTitle = "NGÀY";
			g2.setFont(new Font("Tahoma", Font.BOLD, 14));
			int xTitleWidth = g2.getFontMetrics().stringWidth(xTitle);
			g2.drawString(xTitle, paddingLeft + chartWidth / 2 - xTitleWidth / 2, height - 15);

			// Bước 5: Vẽ đường biểu diễn và các điểm mốc
			g2.setStroke(new BasicStroke(2.5f));
			Color lineColor = new Color(193, 71, 71);
			g2.setColor(lineColor);

			for (int i = 0; i < scores.size(); i++) {
				int x = paddingLeft + (int) (i * xScale);
				double score = scores.get(i);
				if (score > yMax)
					score = yMax;
				if (score < yMin)
					score = yMin;

				int y = paddingTop + (int) ((yMax - score) * yScale);

				// Lưu trữ tọa độ X, Y để xử lý các sự kiện liên quan
				diemCham.add(new Point(x, y));

				if (i > 0) {
					int xPrev = paddingLeft + (int) ((i - 1) * xScale);
					double scorePrev = scores.get(i - 1);
					if (scorePrev > yMax)
						scorePrev = yMax;
					if (scorePrev < yMin)
						scorePrev = yMin;

					int yPrev = paddingTop + (int) ((yMax - scorePrev) * yScale);
					g2.drawLine(xPrev, yPrev, x, y);
				}
			}

			// Vẽ các điểm mốc trên đường biểu diễn
			for (int i = 0; i < diemCham.size(); i++) {
				Point p = diemCham.get(i);

				// Tăng kích thước đối với điểm đang được người dùng lựa chọn
				int r = (i == clickedIndex) ? 14 : 10;
				int offset = r / 2;

				g2.setColor(Color.WHITE);
				g2.fillOval(p.x - offset, p.y - offset, r, r);
				g2.setColor(lineColor);
				g2.drawOval(p.x - offset, p.y - offset, r, r);
			}

			// Bước 6: Hiển thị bảng thông tin chi tiết (tooltip) nếu có điểm được chọn
			if (clickedIndex != -1 && clickedIndex < diemCham.size()) {
				Point p = diemCham.get(clickedIndex);
				String val = formatTienFull(scores.get(clickedIndex)) + " VNĐ";

				g2.setFont(new Font("Tahoma", Font.BOLD, 14));
				FontMetrics fmPopup = g2.getFontMetrics();

				int boxW = fmPopup.stringWidth(val) + 20;
				int boxH = 30;
				int boxX = p.x - boxW / 2;
				int boxY = p.y - boxH - 12;

				// Ràng buộc vị trí hiển thị để không vượt khỏi khu vực biểu đồ
				if (boxX < paddingLeft)
					boxX = paddingLeft;
				if (boxX + boxW > width - paddingRight)
					boxX = width - paddingRight - boxW;

				// Vẽ bóng đổ (Shadow)
				g2.setColor(new Color(0, 0, 0, 40));
				g2.fillRoundRect(boxX + 3, boxY + 3, boxW, boxH, 8, 8);

				// Vẽ khung hiển thị bảng thông tin
				g2.setColor(MAU_HEADER);
				g2.fillRoundRect(boxX, boxY, boxW, boxH, 8, 8);
				g2.setColor(MAU_NAU_VIEN);
				g2.setStroke(new BasicStroke(1.5f));
				g2.drawRoundRect(boxX, boxY, boxW, boxH, 8, 8);

				// Hiển thị giá trị cụ thể
				g2.setColor(lineColor);
				g2.drawString(val, boxX + 10, boxY + 20);
			}
		}
	}
}