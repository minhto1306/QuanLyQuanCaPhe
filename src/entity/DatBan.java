package entity;

import java.time.LocalDateTime;

public class DatBan {
	private String maDatBan;
	private String maBan;
	private String tenKhachHang;
	private String soDienThoai;
	private LocalDateTime thoiGianDat;
	private LocalDateTime thoiGianNhanBan;
	private boolean trangThai;
	private String maKhuVuc;

	public DatBan() {
	}

	public DatBan(String maDatBan, String maBan, String tenKhachHang, String soDienThoai, LocalDateTime thoiGianDat,
			LocalDateTime thoiGianNhanBan, boolean trangThai, String maKhuVuc) {
		this.maDatBan = maDatBan;
		this.maBan = maBan;
		this.tenKhachHang = tenKhachHang;
		this.soDienThoai = soDienThoai;
		this.thoiGianDat = thoiGianDat;
		this.thoiGianNhanBan = thoiGianNhanBan;
		this.trangThai = trangThai;
		this.maKhuVuc = maKhuVuc;
	}

	public String getMaDatBan() {
		return maDatBan;
	}

	public void setMaDatBan(String maDatBan) {
		this.maDatBan = maDatBan;
	}

	public String getMaBan() {
		return maBan;
	}

	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public LocalDateTime getThoiGianDat() {
		return thoiGianDat;
	}

	public void setThoiGianDat(LocalDateTime thoiGianDat) {
		this.thoiGianDat = thoiGianDat;
	}

	public LocalDateTime getThoiGianNhanBan() {
		return thoiGianNhanBan;
	}

	public void setThoiGianNhanBan(LocalDateTime thoiGianNhanBan) {
		this.thoiGianNhanBan = thoiGianNhanBan;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public String getMaKhuVuc() {
		return maKhuVuc;
	}

	public void setMaKhuVuc(String maKhuVuc) {
		this.maKhuVuc = maKhuVuc;
	}
}