package entity;

import java.time.LocalDateTime;

public class CaLamViec {
	private String maCa;
	private String tenCa;
	private LocalDateTime thoiGianBD;
	private LocalDateTime thoiGianKT;
	private double tienMatDauCa;
	private double tongDoanhThuTrongCa;

	public CaLamViec() {

	}

	public CaLamViec(String maCa, String tenCa, LocalDateTime thoiGianBD, LocalDateTime thoiGianKT, double tienMatDauCa,
			double tongDoanhThuTrongCa) {
		this.maCa = maCa;
		this.tenCa = tenCa;
		this.thoiGianBD = thoiGianBD;
		this.thoiGianKT = thoiGianKT;
		this.tienMatDauCa = tienMatDauCa;
		this.tongDoanhThuTrongCa = tongDoanhThuTrongCa;
	}

	public String getMaCa() {
		return maCa;
	}

	public void setMaCa(String maCa) {
		this.maCa = maCa;
	}

	public String getTenCa() {
		return tenCa;
	}

	public void setTenCa(String tenCa) {
		this.tenCa = tenCa;
	}

	public LocalDateTime getThoiGianBD() {
		return thoiGianBD;
	}

	public void setThoiGianBD(LocalDateTime thoiGianBD) {
		this.thoiGianBD = thoiGianBD;
	}

	public LocalDateTime getThoiGianKT() {
		return thoiGianKT;
	}

	public void setThoiGianKT(LocalDateTime thoiGianKT) {
		this.thoiGianKT = thoiGianKT;
	}

	public double getTienMatDauCa() {
		return tienMatDauCa;
	}

	public void setTienMatDauCa(double tienMatDauCa) {
		this.tienMatDauCa = tienMatDauCa;
	}

	public double getTongDoanhThuTrongCa() {
		return tongDoanhThuTrongCa;
	}

	public void setTongDoanhThuTrongCa(double tongDoanhThuTrongCa) {
		this.tongDoanhThuTrongCa = tongDoanhThuTrongCa;
	}

}
