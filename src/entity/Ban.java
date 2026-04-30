package entity;

public class Ban {
	private String maBan;
	private String maKhuVuc;
	private String tenBan;
	private String trangThai; // Đã đổi về String để khớp 100% với nvarchar(100) trong DB

	public Ban() {
	}

	public Ban(String maBan, String maKhuVuc, String tenBan, String trangThai) {
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

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
}