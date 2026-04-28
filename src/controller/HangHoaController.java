package controller;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.DanhMucDAO;
import dao.SanPhamDAO;
import entity.DanhMuc;
import entity.SanPham;
import ui.DlgHangHoa;
import ui.DlgThemDanhMuc;

public class HangHoaController {

	private DlgHangHoa view;
	private DanhMucDAO danhMucDAO;
	private SanPhamDAO sanPhamDAO;
	private String duongDanAnh = "";

	public HangHoaController(DlgHangHoa view) {
		this.view = view;
		this.danhMucDAO = new DanhMucDAO();
		this.sanPhamDAO = new SanPhamDAO();

		khoiTaoSuKienHangHoa();
		khoiTaoSuKienDanhMuc();

		taiDuLieuDanhMuc();
		taiDuLieuSanPham();
	}

	private void taiDuLieuDanhMuc() {
		view.getTmDanhMuc().setRowCount(0);
		view.getCbDanhMuc().removeAllItems();
		List<DanhMuc> list = danhMucDAO.findAll();
		for (DanhMuc dm : list) {
			view.getTmDanhMuc().addRow(new Object[] { dm.getMaDanhMuc(), dm.getTenDanhMuc() });
			view.getCbDanhMuc().addItem(dm.getMaDanhMuc());
		}
	}

	private void taiDuLieuSanPham() {
		view.getTmSanPham().setRowCount(0);
		List<SanPham> list = sanPhamDAO.findAll();
		for (SanPham sp : list) {
			String trangThaiText = sp.isTrangThai() ? "Đang bán" : "Ngừng bán";
			view.getTmSanPham().addRow(new Object[] { sp.getMaSanPham(), sp.getTenSanPham(), sp.getMaDanhMuc(),
					sp.getGiaBan(), trangThaiText });
		}
	}

	private void hienThiAnhLenLabel(String path) {
		try {
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
			view.getLbHinhAnh().setIcon(new ImageIcon(img));
			view.getLbHinhAnh().setText("");
		} catch (Exception e) {
			view.getLbHinhAnh().setIcon(null);
			view.getLbHinhAnh().setText("Lỗi ảnh");
		}
	}

	private void xoaTrangSanPham() {
		view.getTfMaSanPham().setText("");
		view.getTfTenSanPham().setText("");
		view.getTfGiaBan().setText("");
		if (view.getCbDanhMuc().getItemCount() > 0) {
			view.getCbDanhMuc().setSelectedIndex(0);
		}
		view.getCbTrangThai().setSelectedIndex(0);
		duongDanAnh = "";
		view.getLbHinhAnh().setIcon(null);
		view.getLbHinhAnh().setText("");
		view.getTfMaSanPham().requestFocus();

		view.batTatNutSanPham(true, true, true, false, true);
	}

	private void xoaTrangDanhMuc() {
		view.getTfDM_Ma().setText("");
		view.getTfDM_Ten().setText("");
		view.getTfDM_Ma().requestFocus();

		view.batTatNutDanhMuc(true, true, true, false, true);
	}

	private void khoiTaoSuKienHangHoa() {
		view.addBtnThemDanhMucNhanhListener(e -> {
			DlgThemDanhMuc dlgThem = new DlgThemDanhMuc(view);
			new ThemDanhMucController(dlgThem);
			dlgThem.setVisible(true);
			taiDuLieuDanhMuc();
		});

		view.addBtnChonAnhListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
			int xacNhan = fileChooser.showOpenDialog(view);
			if (xacNhan == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				duongDanAnh = file.getAbsolutePath();
				hienThiAnhLenLabel(duongDanAnh);
			}
		});

		view.addBtnXoaAnhListener(e -> {
			duongDanAnh = "";
			view.getLbHinhAnh().setIcon(null);
			view.getLbHinhAnh().setText("");
		});

		view.addHinhAnhDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;

			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						duongDanAnh = file.getAbsolutePath();
						hienThiAnhLenLabel(duongDanAnh);
						break;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		view.addTbSanPhamMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = view.getTbSanPham().getSelectedRow();
				if (row >= 0) {
					String maSP = view.getTmSanPham().getValueAt(row, 0).toString();
					view.getTfMaSanPham().setText(maSP);
					view.getTfTenSanPham().setText(view.getTmSanPham().getValueAt(row, 1).toString());
					view.getCbDanhMuc().setSelectedItem(view.getTmSanPham().getValueAt(row, 2).toString());
					view.getTfGiaBan().setText(view.getTmSanPham().getValueAt(row, 3).toString());
					view.getCbTrangThai().setSelectedItem(view.getTmSanPham().getValueAt(row, 4).toString());

					SanPham sp = sanPhamDAO.findById(maSP);
					if (sp != null) {
						duongDanAnh = sp.getHinhAnh() != null ? sp.getHinhAnh() : "";
						if (!duongDanAnh.isEmpty()) {
							hienThiAnhLenLabel(duongDanAnh);
						} else {
							view.getLbHinhAnh().setIcon(null);
							view.getLbHinhAnh().setText("Chưa có ảnh");
						}
					}
				}
			}
		});

		view.addBtnXoaTrangSPListener(e -> xoaTrangSanPham());

		view.addBtnThemSPListener(e -> {
			try {
				String ma = view.getTfMaSanPham().getText();
				String ten = view.getTfTenSanPham().getText();
				String maDM = view.getCbDanhMuc().getSelectedItem().toString();
				double gia = Double.parseDouble(view.getTfGiaBan().getText());
				boolean trangThai = view.getCbTrangThai().getSelectedIndex() == 0;

				SanPham sp = new SanPham(ma, maDM, ten, gia, trangThai, duongDanAnh);
				if (sanPhamDAO.insert(sp)) {
					taiDuLieuSanPham();
					xoaTrangSanPham();
				} else {
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(view, "Lỗi nhập liệu!");
			}
		});

		view.addBtnXoaSPListener(e -> {
			int row = view.getTbSanPham().getSelectedRow();
			if (row >= 0) {
				String ma = view.getTmSanPham().getValueAt(row, 0).toString();
				if (sanPhamDAO.delete(ma)) {
					taiDuLieuSanPham();
					xoaTrangSanPham();
				}
			}
		});

		view.addBtnSuaSPListener(e -> {
			view.batTatNutSanPham(false, false, false, true, false);
		});

		view.addBtnLuuSPListener(e -> {
			try {
				String ma = view.getTfMaSanPham().getText();
				String ten = view.getTfTenSanPham().getText();
				String maDM = view.getCbDanhMuc().getSelectedItem().toString();
				double gia = Double.parseDouble(view.getTfGiaBan().getText());
				boolean trangThai = view.getCbTrangThai().getSelectedIndex() == 0;

				SanPham sp = new SanPham(ma, maDM, ten, gia, trangThai, duongDanAnh);
				if (sanPhamDAO.update(sp)) {
					taiDuLieuSanPham();
					xoaTrangSanPham();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(view, "Lỗi nhập liệu!");
			}
		});
	}

	private void khoiTaoSuKienDanhMuc() {
		view.addTbDanhMucMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = view.getTbDanhMuc().getSelectedRow();
				if (row >= 0) {
					view.getTfDM_Ma().setText(view.getTmDanhMuc().getValueAt(row, 0).toString());
					view.getTfDM_Ten().setText(view.getTmDanhMuc().getValueAt(row, 1).toString());
				}
			}
		});

		view.addBtnXoaTrangDMListener(e -> xoaTrangDanhMuc());

		view.addBtnThemDMListener(e -> {
			String ma = view.getTfDM_Ma().getText();
			String ten = view.getTfDM_Ten().getText();
			DanhMuc dm = new DanhMuc(ma, ten);
			if (danhMucDAO.insert(dm)) {
				taiDuLieuDanhMuc();
				xoaTrangDanhMuc();
			} else {
				JOptionPane.showMessageDialog(view, "Thêm thất bại!");
			}
		});

		view.addBtnXoaDMListener(e -> {
			int row = view.getTbDanhMuc().getSelectedRow();
			if (row >= 0) {
				String ma = view.getTmDanhMuc().getValueAt(row, 0).toString();
				if (danhMucDAO.delete(ma)) {
					taiDuLieuDanhMuc();
					xoaTrangDanhMuc();
				}
			}
		});

		view.addBtnSuaDMListener(e -> {
			view.batTatNutDanhMuc(false, false, false, true, false);
		});

		view.addBtnLuuDMListener(e -> {
			String ma = view.getTfDM_Ma().getText();
			String ten = view.getTfDM_Ten().getText();
			DanhMuc dm = new DanhMuc(ma, ten);
			if (danhMucDAO.update(dm)) {
				taiDuLieuDanhMuc();
				xoaTrangDanhMuc();
			}
		});
	}
}