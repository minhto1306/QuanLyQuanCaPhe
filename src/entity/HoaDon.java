package entity;

import java.time.LocalDateTime;

public class HoaDon {
	private String maHoaDon;
	private String maNhanVien;
	private String maBan;
	private LocalDateTime thoiGianLap;
	private double tongTien;
	private double thueVAT;
	private double giamGia;
	private double thanhTien;
	private boolean trangThai;

	public HoaDon(String maHoaDon, String maNhanVien, String maBan, LocalDateTime thoiGianLap, double tongTien,
			double thueVAT, double giamGia, double thanhTien, boolean trangThai) {
		this.maHoaDon = maHoaDon;
		this.maNhanVien = maNhanVien;
		this.maBan = maBan;
		this.thoiGianLap = thoiGianLap;
		this.tongTien = tongTien;
		this.thueVAT = thueVAT;
		this.giamGia = giamGia;
		this.thanhTien = thanhTien;
		this.trangThai = trangThai;
	}

	public HoaDon() {

	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaBan() {
		return maBan;
	}

	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}

	public LocalDateTime getThoiGianLap() {
		return thoiGianLap;
	}

	public void setThoiGianLap(LocalDateTime thoiGianLap) {
		this.thoiGianLap = thoiGianLap;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public double getThueVAT() {
		return thueVAT;
	}

	public void setThueVAT(double thueVAT) {
		this.thueVAT = thueVAT;
	}

	public double getGiamGia() {
		return giamGia;
	}

	public void setGiamGia(double giamGia) {
		this.giamGia = giamGia;
	}

	public double getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public double tinhThanhTien() {
		this.thanhTien = this.tongTien + this.thueVAT - this.giamGia;
		return this.thanhTien;
	}

	public double tinhTongTien() {
		return this.tongTien;
	}
}