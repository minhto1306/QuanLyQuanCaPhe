package entity;

public class KhuVuc {
	private String maKhuVuc;
	private String tenKhuVuc;
	private double phuThu;

	public KhuVuc() {

	}

	public KhuVuc(String maKhuVuc, String tenKhuVuc, double phuThu) {
		this.maKhuVuc = maKhuVuc;
		this.tenKhuVuc = tenKhuVuc;
		this.phuThu = phuThu;
	}

	public String getMaKhuVuc() {
		return maKhuVuc;
	}

	public void setMaKhuVuc(String maKhuVuc) {
		this.maKhuVuc = maKhuVuc;
	}

	public String getTenKhuVuc() {
		return tenKhuVuc;
	}

	public void setTenKhuVuc(String tenKhuVuc) {
		this.tenKhuVuc = tenKhuVuc;
	}

	public double getPhuThu() {
		return phuThu;
	}

	public void setPhuThu(double phuThu) {
		this.phuThu = phuThu;
	}

}
