package entity;

public class NhanVien {
	private String maNhanVien;
	private String tenDangNhap;
	private String hoTenNhanVien;
	private String soCCCD;
	private String soDienThoai;

	public NhanVien() {

	}

	public NhanVien(String maNhanVien, String tenDangNhap, String hoTenNhanVien, String soCCCD, String soDienThoai) {
		this.maNhanVien = maNhanVien;
		this.tenDangNhap = tenDangNhap;
		this.hoTenNhanVien = hoTenNhanVien;
		this.soCCCD = soCCCD;
		this.soDienThoai = soDienThoai;
	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getHoTenNhanVien() {
		return hoTenNhanVien;
	}

	public void setHoTenNhanVien(String hoTenNhanVien) {
		this.hoTenNhanVien = hoTenNhanVien;
	}

	public String getSoCCCD() {
		return soCCCD;
	}

	public void setSoCCCD(String soCCCD) {
		this.soCCCD = soCCCD;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

}
