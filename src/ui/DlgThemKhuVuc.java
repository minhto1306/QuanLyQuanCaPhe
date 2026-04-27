package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DlgThemKhuVuc extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfMaKhuVuc, tfTenKhuVuc, tfPhuThu;
	private JButton btnLuuThemMoi, btnDong;
	private JLabel lbMaKhuVuc, lbTenKhuVuc, lbPhuThu;

	public DlgThemKhuVuc(JDialog parent) {
		super(parent, "THÊM KHU VỰC", true);
		this.setSize(400, 260);
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
		Box box3 = Box.createHorizontalBox();

		lbMaKhuVuc = new JLabel("Mã khu vực:");
		lbTenKhuVuc = new JLabel("Tên khu vực:");
		lbPhuThu = new JLabel("Phụ thu:");

		Dimension dLabel = new Dimension(120, 25);
		lbMaKhuVuc.setPreferredSize(dLabel);
		lbTenKhuVuc.setPreferredSize(dLabel);
		lbPhuThu.setPreferredSize(dLabel);

		Font fontLablel = new Font("Arial", Font.BOLD, 14);
		lbMaKhuVuc.setFont(fontLablel);
		lbTenKhuVuc.setFont(fontLablel);
		lbPhuThu.setFont(fontLablel);

		tfMaKhuVuc = new JTextField();
		tfTenKhuVuc = new JTextField();
		tfPhuThu = new JTextField();

		Dimension dInput = new Dimension(230, 25);
		tfMaKhuVuc.setPreferredSize(dInput);
		tfMaKhuVuc.setMaximumSize(dInput);
		tfTenKhuVuc.setPreferredSize(dInput);
		tfTenKhuVuc.setMaximumSize(dInput);
		tfPhuThu.setPreferredSize(dInput);
		tfPhuThu.setMaximumSize(dInput);

		box1.add(lbMaKhuVuc);
		box1.add(Box.createHorizontalStrut(10));
		box1.add(tfMaKhuVuc);
		box1.add(Box.createHorizontalGlue());

		box2.add(lbTenKhuVuc);
		box2.add(Box.createHorizontalStrut(10));
		box2.add(tfTenKhuVuc);
		box2.add(Box.createHorizontalGlue());

		box3.add(lbPhuThu);
		box3.add(Box.createHorizontalStrut(10));
		box3.add(tfPhuThu);
		box3.add(Box.createHorizontalGlue());

		pnCenter.add(box1);
		pnCenter.add(Box.createVerticalStrut(20));
		pnCenter.add(box2);
		pnCenter.add(Box.createVerticalStrut(20));
		pnCenter.add(box3);

		pnMain.add(pnCenter, BorderLayout.CENTER);

		JPanel pnBottom = new JPanel();
		pnBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 0));
		pnBottom.setBorder(new EmptyBorder(15, 0, 0, 0));

		Font fontBtn = new Font("Arial", Font.BOLD, 13);

		btnLuuThemMoi = new JButton("Lưu + Thêm mới");
		btnLuuThemMoi.setFont(fontBtn);
		ImageIcon iconLuu = taoIconThuNho("/images/save.png", 20, 20);
		if (iconLuu != null)
			btnLuuThemMoi.setIcon(iconLuu);

		btnDong = new JButton("Đóng");
		btnDong.setFont(fontBtn);
		ImageIcon iconDong = taoIconThuNho("/images/close.png", 20, 20);
		if (iconDong != null)
			btnDong.setIcon(iconDong);

		pnBottom.add(btnLuuThemMoi);
		pnBottom.add(btnDong);

		btnDong.addActionListener(e -> this.dispose());

		pnMain.add(pnBottom, BorderLayout.SOUTH);
		this.add(pnMain);
	}
}