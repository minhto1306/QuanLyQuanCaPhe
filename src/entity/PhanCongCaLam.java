package entity;

import java.time.LocalDateTime;

public class PhanCongCaLam {
	private String maNhanVien;
	private String maCa;
	private LocalDateTime ngayLam;
	private boolean trangThaiDiemDanh;

	public PhanCongCaLam(String maNhanVien, String maCa, LocalDateTime ngayLam, boolean trangThaiDiemDanh) {
		this.maNhanVien = maNhanVien;
		this.maCa = maCa;
		this.ngayLam = ngayLam;
		this.trangThaiDiemDanh = trangThaiDiemDanh;
	}

	public PhanCongCaLam() {

	}

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMaCa() {
		return maCa;
	}

	public void setMaCa(String maCa) {
		this.maCa = maCa;
	}

	public LocalDateTime getNgayLam() {
		return ngayLam;
	}

	public void setNgayLam(LocalDateTime ngayLam) {
		this.ngayLam = ngayLam;
	}

	public boolean isTrangThaiDiemDanh() {
		return trangThaiDiemDanh;
	}

	public void setTrangThaiDiemDanh(boolean trangThaiDiemDanh) {
		this.trangThaiDiemDanh = trangThaiDiemDanh;
	}

}
