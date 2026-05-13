package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DlgThemDanhMuc extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfMaDanhMuc;
	private JTextField tfTenDanhMuc;
	private JButton btnLuuThemMoi;
	private JButton btnDong;
	private JLabel lbMaDanhMuc;
	private JLabel lbTenDanhMuc;

	private final Color MAU_NEN_CHINH = new Color(242, 233, 216);
	private final Color MAU_NAU_VIEN = new Color(89, 58, 47);
	private Font fontBungeeBase;

	// CHỨC NĂNG: Khởi tạo giao diện hộp thoại thêm danh mục.
	public DlgThemDanhMuc(JDialog parent) {
		super(parent, "THÊM DANH MỤC", true);
		this.setSize(480, 320);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		loadCustomFont();
		khoiTaoGiaoDien();
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

	// CHỨC NĂNG: Xử lý thay đổi kích thước hình ảnh.
	private ImageIcon taoIconThuNho(String duongDan, int width, int height) {
		java.net.URL url = getClass().getResource(duongDan);
		if (url == null) {
			return null;
		}
		return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	// CHỨC NĂNG: Thiết lập định dạng chuẩn cho các ô nhập liệu.
	private void setupRetroInput(Component comp) {
		if (comp instanceof JTextField) {
			JTextField tf = (JTextField) comp;
			tf.setFont(new Font("Arial", Font.BOLD, 15));
			tf.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		}
	}

	// CHỨC NĂNG: Thiết lập định dạng chuẩn cho các nút bấm.
	private void setupRetroButton(JButton btn) {
		btn.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 16f));
		btn.setBackground(MAU_NEN_CHINH);
		btn.setForeground(MAU_NAU_VIEN);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 2),
				BorderFactory.createEmptyBorder(5, 15, 5, 15)));
	}

	// CHỨC NĂNG: Khởi tạo và bố trí các thành phần giao diện.
	private void khoiTaoGiaoDien() {
		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(MAU_NAU_VIEN);
		container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel pnMainLayout = new JPanel(new BorderLayout());
		pnMainLayout.setBackground(MAU_NEN_CHINH);
		pnMainLayout.setBorder(BorderFactory.createLineBorder(MAU_NAU_VIEN, 3));
		container.add(pnMainLayout, BorderLayout.CENTER);
		this.setContentPane(container);

		JPanel pnTieuDe = new JPanel();
		pnTieuDe.setBackground(MAU_NEN_CHINH);
		pnTieuDe.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
		pnMainLayout.add(pnTieuDe, BorderLayout.NORTH);

		JLabel lbTieuDe = new JLabel("THÊM DANH MỤC", SwingConstants.CENTER);
		lbTieuDe.setFont(fontBungeeBase.deriveFont(Font.PLAIN, 26f));
		lbTieuDe.setForeground(MAU_NAU_VIEN);
		pnTieuDe.add(lbTieuDe);

		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));
		pnCenter.setBackground(MAU_NEN_CHINH);
		pnCenter.setBorder(new EmptyBorder(10, 20, 15, 20));

		Box box1 = Box.createHorizontalBox();
		Box box2 = Box.createHorizontalBox();

		lbMaDanhMuc = new JLabel("MÃ DANH MỤC:");
		lbTenDanhMuc = new JLabel("TÊN DANH MỤC:");

		Dimension dLabel = new Dimension(150, 30);
		lbMaDanhMuc.setPreferredSize(dLabel);
		lbTenDanhMuc.setPreferredSize(dLabel);

		Font fontLabelRetro = fontBungeeBase.deriveFont(Font.PLAIN, 18f);
		lbMaDanhMuc.setFont(fontLabelRetro);
		lbMaDanhMuc.setForeground(MAU_NAU_VIEN);
		lbTenDanhMuc.setFont(fontLabelRetro);
		lbTenDanhMuc.setForeground(MAU_NAU_VIEN);

		tfMaDanhMuc = new JTextField();
		tfTenDanhMuc = new JTextField();

		Dimension dInput = new Dimension(230, 35);
		tfMaDanhMuc.setPreferredSize(dInput);
		tfMaDanhMuc.setMaximumSize(dInput);
		tfTenDanhMuc.setPreferredSize(dInput);
		tfTenDanhMuc.setMaximumSize(dInput);

		setupRetroInput(tfMaDanhMuc);
		setupRetroInput(tfTenDanhMuc);

		box1.add(lbMaDanhMuc);
		box1.add(Box.createHorizontalStrut(10));
		box1.add(tfMaDanhMuc);
		box1.add(Box.createHorizontalGlue());

		box2.add(lbTenDanhMuc);
		box2.add(Box.createHorizontalStrut(10));
		box2.add(tfTenDanhMuc);
		box2.add(Box.createHorizontalGlue());

		pnCenter.add(box1);
		pnCenter.add(Box.createVerticalStrut(25));
		pnCenter.add(box2);

		pnMainLayout.add(pnCenter, BorderLayout.CENTER);

		JPanel pnBottom = new JPanel();
		pnBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
		pnBottom.setBackground(MAU_NEN_CHINH);
		pnBottom.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, MAU_NAU_VIEN));

		btnLuuThemMoi = new JButton("LƯU + THÊM MỚI", taoIconThuNho("/images/save.png", 20, 20));
		btnDong = new JButton("ĐÓNG", taoIconThuNho("/images/close.png", 20, 20));

		setupRetroButton(btnLuuThemMoi);
		setupRetroButton(btnDong);

		pnBottom.add(btnLuuThemMoi);
		pnBottom.add(btnDong);

		pnMainLayout.add(pnBottom, BorderLayout.SOUTH);
	}

	// CHỨC NĂNG: Truy xuất mã danh mục được nhập.
	public String getMaDanhMuc() {
		return tfMaDanhMuc.getText().trim();
	}

	// CHỨC NĂNG: Truy xuất tên danh mục được nhập.
	public String getTenDanhMuc() {
		return tfTenDanhMuc.getText().trim();
	}

	// CHỨC NĂNG: Làm sạch dữ liệu trên các ô nhập liệu.
	public void xoaTrangThongTin() {
		tfMaDanhMuc.setText("");
		tfTenDanhMuc.setText("");
		tfMaDanhMuc.requestFocus();
	}

	public void addLuuThemMoiListener(ActionListener listener) {
		btnLuuThemMoi.addActionListener(listener);
	}

	public void addDongListener(ActionListener listener) {
		btnDong.addActionListener(listener);
	}
}