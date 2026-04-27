package entity;

public class ChiTietDatBan {
	private String maDatBan;
	private String maSanPham;
	private int soLuong;
	private String ghiChu;

	public ChiTietDatBan() {

	}

	public ChiTietDatBan(String maDatBan, String maSanPham, int soLuong, String ghiChu) {
		this.maDatBan = maDatBan;
		this.maSanPham = maSanPham;
		this.soLuong = soLuong;
		this.ghiChu = ghiChu;
	}

	public String getMaDatBan() {
		return maDatBan;
	}

	public void setMaDatBan(String maDatBan) {
		this.maDatBan = maDatBan;
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

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public double tinhTienMon(double giaBanSanPham) {
		return this.soLuong * giaBanSanPham;
	}

}
