package entity;

public class ChiTietHoaDon {
	private String maHoaDon;
	private String maSanPham;
	private int soLuong;
	private double giaBan;
	private String ghiChu;

	public ChiTietHoaDon() {

	}

	public ChiTietHoaDon(String maHoaDon, String maSanPham, int soLuong, double giaBan, String ghiChu) {
		this.maHoaDon = maHoaDon;
		this.maSanPham = maSanPham;
		this.soLuong = soLuong;
		this.giaBan = giaBan;
		this.ghiChu = ghiChu;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public double tinhTienMon() {
		return getSoLuong() * getGiaBan();
	}
}
