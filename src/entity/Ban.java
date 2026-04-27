package entity;

public class Ban {
	private String maBan;
	private String maKhuVuc;
	private String tenBan;
	private boolean trangThai;

	public Ban() {

	}

	public Ban(String maBan, String maKhuVuc, String tenBan, boolean trangThai) {
		this.maBan = maBan;
		this.maKhuVuc = maKhuVuc;
		this.tenBan = tenBan;
		this.trangThai = trangThai;
	}

	public String getMaBan() {
		return maBan;
	}

	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}

	public String getMaKhuVuc() {
		return maKhuVuc;
	}

	public void setMaKhuVuc(String maKhuVuc) {
		this.maKhuVuc = maKhuVuc;
	}

	public String getTenBan() {
		return tenBan;
	}

	public void setTenBan(String tenBan) {
		this.tenBan = tenBan;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

}
