package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entity.DatBan;
import util.DataBaseConnection;

public class DatBanDAO {

	public List<DatBan> findAll() {
		List<DatBan> ds = new ArrayList<>();
		String sql = "SELECT * FROM DatBan";

		Connection cnt = DataBaseConnection.getInstance().getConnection();

		try (PreparedStatement ps = cnt.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				ds.add(new DatBan(rs.getString("maDatBan"), rs.getString("maBan"), rs.getString("tenKhachHang"),
						rs.getString("soDienThoai"),
						rs.getTimestamp("thoiGianDat") != null ? rs.getTimestamp("thoiGianDat").toLocalDateTime()
								: null,
						rs.getTimestamp("thoiGianNhanBan") != null
								? rs.getTimestamp("thoiGianNhanBan").toLocalDateTime()
								: null,
						rs.getBoolean("trangThai")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
	}

	public boolean insert(DatBan db) {
		String sql = "INSERT INTO DatBan (maDatBan, maBan, tenKhachHang, soDienThoai, thoiGianDat, thoiGianNhanBan, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, db.getMaDatBan());
			if (db.getMaBan() == null || db.getMaBan().trim().isEmpty()) {
				ps.setNull(2, java.sql.Types.VARCHAR);
			} else {
				ps.setString(2, db.getMaBan());
			}

			ps.setString(3, db.getTenKhachHang());
			ps.setString(4, db.getSoDienThoai());
			ps.setTimestamp(5, db.getThoiGianDat() != null ? Timestamp.valueOf(db.getThoiGianDat()) : null);
			ps.setTimestamp(6, db.getThoiGianNhanBan() != null ? Timestamp.valueOf(db.getThoiGianNhanBan()) : null);
			ps.setBoolean(7, db.isTrangThai());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean update(DatBan db) {
		String sql = "UPDATE DatBan SET maBan=?, tenKhachHang=?, soDienThoai=?, thoiGianDat=?, thoiGianNhanBan=?, trangThai=? WHERE maDatBan=?";
		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, db.getMaBan());
			ps.setString(2, db.getTenKhachHang());
			ps.setString(3, db.getSoDienThoai());
			ps.setTimestamp(4, db.getThoiGianDat() != null ? Timestamp.valueOf(db.getThoiGianDat()) : null);
			ps.setTimestamp(5, db.getThoiGianNhanBan() != null ? Timestamp.valueOf(db.getThoiGianNhanBan()) : null);
			ps.setBoolean(6, db.isTrangThai());
			ps.setString(7, db.getMaDatBan());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(String maDatBan) {
		String sql = "DELETE FROM DatBan WHERE maDatBan=?";
		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, maDatBan);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}