package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.PhanCongCaLam;
import util.DataBaseConnection;

public class PhanCongCaLamDAO {

	public List<PhanCongCaLam> findByDateRange(LocalDate start, LocalDate end) {
		List<PhanCongCaLam> list = new ArrayList<>();
		String sql = "SELECT * FROM PhanCongCaLam WHERE ngayLam BETWEEN ? AND ?";

		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setDate(1, Date.valueOf(start));
			ps.setDate(2, Date.valueOf(end));

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new PhanCongCaLam(rs.getString("maNhanVien"), rs.getString("maCa"),
							rs.getTimestamp("ngayLam").toLocalDateTime(), rs.getBoolean("trangThaiDiemDanh")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean insert(PhanCongCaLam pc) {
		String sql = "INSERT INTO PhanCongCaLam (maNhanVien, maCa, ngayLam, trangThaiDiemDanh) VALUES (?, ?, ?, ?)";
		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, pc.getMaNhanVien());
			ps.setString(2, pc.getMaCa());
			ps.setTimestamp(3, Timestamp.valueOf(pc.getNgayLam()));
			ps.setBoolean(4, pc.isTrangThaiDiemDanh());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteByDateRange(LocalDate start, LocalDate end) {
		String sql = "DELETE FROM PhanCongCaLam WHERE ngayLam BETWEEN ? AND ?";
		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setDate(1, Date.valueOf(start));
			ps.setDate(2, Date.valueOf(end));
			return ps.executeUpdate() >= 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}