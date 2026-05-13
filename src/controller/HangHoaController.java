package controller;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

	// CHỨC NĂNG: Khởi tạo điều khiển quá trình quản lý thông tin sản phẩm và phân
	// loại.
	public HangHoaController(DlgHangHoa view) {
		this.view = view;
		this.danhMucDAO = new DanhMucDAO();
		this.sanPhamDAO = new SanPhamDAO();

		khoiTaoSuKienHangHoa();
		khoiTaoSuKienDanhMuc();

		taiDuLieuDanhMuc();
		taiDuLieuSanPham();
	}

	// CHỨC NĂNG: Lấy dữ liệu toàn bộ danh mục từ cơ sở dữ liệu và hiển thị lên giao
	// diện.
	private void taiDuLieuDanhMuc() {
		view.getTmDanhMuc().setRowCount(0);
		view.getCbDanhMuc().removeAllItems();
		List<DanhMuc> list = danhMucDAO.findAll();
		for (DanhMuc dm : list) {
			view.getTmDanhMuc().addRow(new Object[] { dm.getMaDanhMuc(), dm.getTenDanhMuc() });
			view.getCbDanhMuc().addItem(dm.getMaDanhMuc());
		}
	}

	// CHỨC NĂNG: Lấy dữ liệu toàn bộ sản phẩm từ cơ sở dữ liệu và hiển thị lên giao
	// diện.
	private void taiDuLieuSanPham() {
		view.getTmSanPham().setRowCount(0);
		List<SanPham> list = sanPhamDAO.findAll();
		for (SanPham sp : list) {
			String trangThaiText = sp.isTrangThai() ? "Đang bán" : "Ngừng bán";
			view.getTmSanPham().addRow(new Object[] { sp.getMaSanPham(), sp.getTenSanPham(), sp.getMaDanhMuc(),
					sp.getGiaBan(), trangThaiText });
		}
	}

	// CHỨC NĂNG: Chỉnh sửa và cập nhật tài nguyên đồ họa tương ứng cho sản phẩm
	// được chọn.
	private void hienThiAnhLenLabel(String pathOrName) {
		try {
			File file = new File(pathOrName);
			if (!file.exists() && !pathOrName.isEmpty()) {
				file = new File("product-images", pathOrName);
			}

			if (file.exists()) {
				ImageIcon icon = new ImageIcon(file.getAbsolutePath());
				Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
				view.getLbHinhAnh().setIcon(new ImageIcon(img));
				view.getLbHinhAnh().setText("");
			} else {
				view.getLbHinhAnh().setIcon(null);
				view.getLbHinhAnh().setText("Lỗi ảnh");
			}
		} catch (Exception e) {
			view.getLbHinhAnh().setIcon(null);
			view.getLbHinhAnh().setText("Lỗi ảnh");
		}
	}

	// CHỨC NĂNG: Thực hiện cơ chế sao chép và quản lý lưu trữ tập tin đồ họa của
	// sản phẩm.
	private String luuAnhVaoThuMucProduct(String duongDanGoc, String maSanPham) {
		if (duongDanGoc == null || duongDanGoc.trim().isEmpty()) {
			return null;
		}

		File fileGoc = new File(duongDanGoc);

		if (!fileGoc.exists()) {
			return duongDanGoc;
		}

		try {
			File thuMucLuu = new File("product-images");
			if (!thuMucLuu.exists()) {
				thuMucLuu.mkdirs();
			}

			String tenFileGoc = fileGoc.getName();
			String phanMoRong = tenFileGoc.contains(".") ? tenFileGoc.substring(tenFileGoc.lastIndexOf(".")) : ".png";
			String tenFileMoi = maSanPham + phanMoRong;

			File fileDich = new File(thuMucLuu, tenFileMoi);
			Files.copy(fileGoc.toPath(), fileDich.toPath(), StandardCopyOption.REPLACE_EXISTING);

			return tenFileMoi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// CHỨC NĂNG: Hoàn nguyên trạng thái của biểu mẫu chỉnh sửa sản phẩm về mặc
	// định.
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
		view.getTfTimSP().setText("");
		view.getTfMaSanPham().requestFocus();

		view.batTatNutSanPham(true, true, true, false, true);
	}

	// CHỨC NĂNG: Hoàn nguyên trạng thái của biểu mẫu chỉnh sửa danh mục về mặc
	// định.
	private void xoaTrangDanhMuc() {
		view.getTfDM_Ma().setText("");
		view.getTfDM_Ten().setText("");
		view.getTfDM_Tim().setText("");
		view.getTfDM_Ma().requestFocus();

		view.batTatNutDanhMuc(true, true, true, false, true);
	}

	// CHỨC NĂNG: Cấu hình và kích hoạt các bộ tiếp nhận tín hiệu từ giao diện quản
	// lý Sản phẩm.
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

		view.addBtnXoaTrangSPListener(e -> {
			xoaTrangSanPham();
			taiDuLieuSanPham();
		});

		view.addBtnTimSPListener(e -> {
			String maTim = view.getTfTimSP().getText().trim();
			if (maTim.isEmpty()) {
				taiDuLieuSanPham();
			} else {
				SanPham sp = sanPhamDAO.findById(maTim);
				view.getTmSanPham().setRowCount(0);
				if (sp != null) {
					String trangThaiText = sp.isTrangThai() ? "Đang bán" : "Ngừng bán";
					view.getTmSanPham().addRow(new Object[] { sp.getMaSanPham(), sp.getTenSanPham(), sp.getMaDanhMuc(),
							sp.getGiaBan(), trangThaiText });
				} else {
					JOptionPane.showMessageDialog(view, "Không tìm thấy sản phẩm có mã: " + maTim);
				}
			}
		});

		view.addBtnThemSPListener(e -> {
			try {
				String ma = view.getTfMaSanPham().getText().trim();
				String ten = view.getTfTenSanPham().getText().trim();

				if (ma.isEmpty() || ten.isEmpty()) {
					JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ mã và tên sản phẩm!");
					return;
				}

				if (!ma.matches("^[a-zA-Z0-9_]+$")) {
					JOptionPane.showMessageDialog(view,
							"Mã sản phẩm không hợp lệ! Chỉ dùng chữ cái, số và dấu gạch dưới (_).", "Lỗi mã",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				String maDM = view.getCbDanhMuc().getSelectedItem().toString();
				double gia = Double.parseDouble(view.getTfGiaBan().getText().trim());
				boolean trangThai = view.getCbTrangThai().getSelectedIndex() == 0;

				String tenFileAnToan = luuAnhVaoThuMucProduct(duongDanAnh, ma);

				SanPham sp = new SanPham(ma, maDM, ten, gia, trangThai, tenFileAnToan);
				if (sanPhamDAO.insert(sp)) {
					taiDuLieuSanPham();
					xoaTrangSanPham();
				} else {
					JOptionPane.showMessageDialog(view, "Thêm thất bại! Trùng mã hoặc lỗi CSDL.");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(view, "Giá bán phải là một số hợp lệ!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(view, "Lỗi nhập liệu!");
			}
		});

		view.addBtnXoaSPListener(e -> {
			int row = view.getTbSanPham().getSelectedRow();
			if (row >= 0) {
				String ma = view.getTmSanPham().getValueAt(row, 0).toString();
				int xacNhan = JOptionPane.showConfirmDialog(view, "Xác nhận xóa sản phẩm: " + ma + "?", "Xóa",
						JOptionPane.YES_NO_OPTION);
				if (xacNhan == JOptionPane.YES_OPTION) {
					if (sanPhamDAO.delete(ma)) {
						taiDuLieuSanPham();
						xoaTrangSanPham();
					} else {
						JOptionPane.showMessageDialog(view, "Xóa thất bại!");
					}
				}
			} else {
				JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm cần xóa trên bảng!");
			}
		});

		view.addBtnSuaSPListener(e -> {
			view.batTatNutSanPham(false, false, false, true, false);
		});

		view.addBtnLuuSPListener(e -> {
			try {
				String ma = view.getTfMaSanPham().getText().trim();
				String ten = view.getTfTenSanPham().getText().trim();

				if (!ma.matches("^[a-zA-Z0-9_]+$")) {
					JOptionPane.showMessageDialog(view,
							"Mã sản phẩm không hợp lệ! Chỉ dùng chữ cái, số và dấu gạch dưới (_).", "Lỗi mã",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				String maDM = view.getCbDanhMuc().getSelectedItem().toString();
				double gia = Double.parseDouble(view.getTfGiaBan().getText().trim());
				boolean trangThai = view.getCbTrangThai().getSelectedIndex() == 0;

				String tenFileAnToan = luuAnhVaoThuMucProduct(duongDanAnh, ma);

				SanPham sp = new SanPham(ma, maDM, ten, gia, trangThai, tenFileAnToan);
				if (sanPhamDAO.update(sp)) {
					taiDuLieuSanPham();
					xoaTrangSanPham();
				} else {
					JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(view, "Giá bán phải là một số hợp lệ!");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(view, "Lỗi nhập liệu!");
			}
		});
	}

	// CHỨC NĂNG: Cấu hình và kích hoạt các bộ tiếp nhận tín hiệu từ giao diện quản
	// lý Danh Mục.
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

		view.addBtnXoaTrangDMListener(e -> {
			xoaTrangDanhMuc();
			taiDuLieuDanhMuc();
		});

		view.addBtnTimDMListener(e -> {
			String maTim = view.getTfDM_Tim().getText().trim();
			if (maTim.isEmpty()) {
				taiDuLieuDanhMuc();
			} else {
				DanhMuc dm = danhMucDAO.findById(maTim);
				view.getTmDanhMuc().setRowCount(0);
				if (dm != null) {
					view.getTmDanhMuc().addRow(new Object[] { dm.getMaDanhMuc(), dm.getTenDanhMuc() });
				} else {
					JOptionPane.showMessageDialog(view, "Không tìm thấy danh mục có mã: " + maTim);
				}
			}
		});

		view.addBtnThemDMListener(e -> {
			String ma = view.getTfDM_Ma().getText().trim();
			String ten = view.getTfDM_Ten().getText().trim();

			if (ma.isEmpty() || ten.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ mã và tên danh mục!");
				return;
			}

			if (!ma.matches("^[a-zA-Z0-9_]+$")) {
				JOptionPane.showMessageDialog(view,
						"Mã danh mục không hợp lệ! Chỉ dùng chữ cái, số và dấu gạch dưới (_).", "Lỗi mã",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			DanhMuc dm = new DanhMuc(ma, ten);
			if (danhMucDAO.insert(dm)) {
				taiDuLieuDanhMuc();
				xoaTrangDanhMuc();
			} else {
				JOptionPane.showMessageDialog(view, "Thêm thất bại! Trùng mã hoặc lỗi CSDL.");
			}
		});

		view.addBtnXoaDMListener(e -> {
			int row = view.getTbDanhMuc().getSelectedRow();
			if (row >= 0) {
				String ma = view.getTmDanhMuc().getValueAt(row, 0).toString();
				int xacNhan = JOptionPane.showConfirmDialog(view, "Xác nhận xóa danh mục: " + ma + "?", "Xóa",
						JOptionPane.YES_NO_OPTION);
				if (xacNhan == JOptionPane.YES_OPTION) {
					if (danhMucDAO.delete(ma)) {
						taiDuLieuDanhMuc();
						xoaTrangDanhMuc();
					} else {
						JOptionPane.showMessageDialog(view, "Xóa thất bại! Có thể danh mục này đang chứa sản phẩm.");
					}
				}
			} else {
				JOptionPane.showMessageDialog(view, "Vui lòng chọn danh mục cần xóa trên bảng!");
			}
		});

		view.addBtnSuaDMListener(e -> {
			view.batTatNutDanhMuc(false, false, false, true, false);
		});

		view.addBtnLuuDMListener(e -> {
			String ma = view.getTfDM_Ma().getText().trim();
			String ten = view.getTfDM_Ten().getText().trim();

			if (ten.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Tên danh mục không được để trống!");
				return;
			}

			DanhMuc dm = new DanhMuc(ma, ten);
			if (danhMucDAO.update(dm)) {
				taiDuLieuDanhMuc();
				xoaTrangDanhMuc();
			} else {
				JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
			}
		});
	}
}