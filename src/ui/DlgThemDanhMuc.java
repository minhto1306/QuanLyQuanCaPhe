package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DlgThemDanhMuc extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfMaDanhMuc;
	private JTextField tfTenDanhMuc;
	private JButton btnLuuThemMoi;
	private JButton btnDong;
	private JLabel lbMaDanhMuc;
	private JLabel lbTenDanhMuc;

	public DlgThemDanhMuc(JDialog parent) {
		super(parent, "DANH MỤC", true);
		this.setSize(400, 220);
		this.setLocationRelativeTo(parent);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		khoiTaoGiaoDien();
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

	private void khoiTaoGiaoDien() {
		JPanel pnMain = new JPanel();
		pnMain.setLayout(new BorderLayout());
		pnMain.setBorder(new EmptyBorder(20, 20, 15, 20));

		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));

		Box box1 = Box.createHorizontalBox();
		Box box2 = Box.createHorizontalBox();

		lbMaDanhMuc = new JLabel("Mã danh mục:");
		lbTenDanhMuc = new JLabel("Tên danh mục:");

		Dimension dLabel = new Dimension(120, 25);
		lbMaDanhMuc.setPreferredSize(dLabel);
		lbTenDanhMuc.setPreferredSize(dLabel);

		Font fontLablel = new Font("Arial", Font.BOLD, 14);
		lbMaDanhMuc.setFont(fontLablel);
		lbTenDanhMuc.setFont(fontLablel);

		tfMaDanhMuc = new JTextField();
		tfTenDanhMuc = new JTextField();

		Dimension dInput = new Dimension(230, 25);
		tfMaDanhMuc.setPreferredSize(dInput);
		tfMaDanhMuc.setMaximumSize(dInput);
		tfTenDanhMuc.setPreferredSize(dInput);
		tfTenDanhMuc.setMaximumSize(dInput);

		box1.add(lbMaDanhMuc);
		box1.add(Box.createHorizontalStrut(10));
		box1.add(tfMaDanhMuc);
		box1.add(Box.createHorizontalGlue());

		box2.add(lbTenDanhMuc);
		box2.add(Box.createHorizontalStrut(10));
		box2.add(tfTenDanhMuc);
		box2.add(Box.createHorizontalGlue());

		pnCenter.add(box1);
		pnCenter.add(Box.createVerticalStrut(20));
		pnCenter.add(box2);

		pnMain.add(pnCenter, BorderLayout.CENTER);

		JPanel pnBottom = new JPanel();
		pnBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 0));
		pnBottom.setBorder(new EmptyBorder(15, 0, 0, 0));

		Font fontBtn = new Font("Arial", Font.BOLD, 13);

		btnLuuThemMoi = new JButton("Lưu + Thêm mới");
		btnLuuThemMoi.setFont(fontBtn);
		ImageIcon iconLuu = taoIconThuNho("/images/save.png", 20, 20);
		if (iconLuu != null) {
			btnLuuThemMoi.setIcon(iconLuu);
		}

		btnDong = new JButton("Đóng");
		btnDong.setFont(fontBtn);
		ImageIcon iconDong = taoIconThuNho("/images/close.png", 20, 20);
		if (iconDong != null) {
			btnDong.setIcon(iconDong);
		}

		pnBottom.add(btnLuuThemMoi);
		pnBottom.add(btnDong);

		pnMain.add(pnBottom, BorderLayout.SOUTH);
		this.add(pnMain);
	}

	public String getMaDanhMuc() {
		return tfMaDanhMuc.getText().trim();
	}

	public String getTenDanhMuc() {
		return tfTenDanhMuc.getText().trim();
	}

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