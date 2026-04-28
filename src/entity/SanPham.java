package entity;

public class SanPham {
	private String maSanPham;
	private String maDanhMuc;
	private String tenSanPham;
	private double giaBan;
	private boolean trangThai;
	private String hinhAnh;

	public SanPham(String maSanPham, String maDanhMuc, String tenSanPham, double giaBan, boolean trangThai,
			String hinhAnh) {
		this.maSanPham = maSanPham;
		this.maDanhMuc = maDanhMuc;
		this.tenSanPham = tenSanPham;
		this.giaBan = giaBan;
		this.trangThai = trangThai;
		this.hinhAnh = hinhAnh;
	}

	public SanPham() {
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getMaDanhMuc() {
		return maDanhMuc;
	}

	public void setMaDanhMuc(String maDanhMuc) {
		this.maDanhMuc = maDanhMuc;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
}