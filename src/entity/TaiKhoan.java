package entity;

public class TaiKhoan {
	private String tenDangNhap;
	private String matKhau;
	private boolean trangThai;
	private String vaiTro;

	public TaiKhoan(String tenDangNhap, String matKhau, boolean trangThai, String vaiTro) {
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.trangThai = trangThai;
		this.vaiTro = vaiTro;
	}

	public TaiKhoan() {

	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public String getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(String vaiTro) {
		this.vaiTro = vaiTro;
	}

	public boolean kiemTraDangNhap(String matKhauNhap) {
		if (this.matKhau == null || matKhauNhap == null) {
			return false;
		}
		return this.matKhau.equals(matKhauNhap);
	}

}
