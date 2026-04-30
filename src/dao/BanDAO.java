package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Ban;
import util.DataBaseConnection;

public class BanDAO implements BaseDAO<Ban, String> {

	@Override
	public List<Ban> findAll() {
		List<Ban> ds = new ArrayList<>();
		String sql = "SELECT * FROM Ban";
		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				ds.add(new Ban(rs.getString("maBan"), rs.getString("maKhuVuc"), rs.getString("tenBan"),
						rs.getString("trangThai")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	@Override
	public boolean insert(Ban entity) {
		String sql = "INSERT INTO Ban(maBan, maKhuVuc, tenBan, trangThai) VALUES(?, ?, ?, ?)";
		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, entity.getMaBan());
			ps.setString(2, entity.getMaKhuVuc());
			ps.setString(3, entity.getTenBan());
			ps.setString(4, entity.getTrangThai());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Ban entity) {
		String sql = "UPDATE Ban SET maKhuVuc=?, tenBan=?, trangThai=? WHERE maBan=?";
		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, entity.getMaKhuVuc());
			ps.setString(2, entity.getTenBan());
			ps.setString(3, entity.getTrangThai());
			ps.setString(4, entity.getMaBan());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		String sql = "DELETE FROM Ban WHERE maBan=?";
		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, id);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Ban findById(String id) {
		String sql = "SELECT * FROM Ban WHERE maBan=?";
		Connection cnt = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Ban(rs.getString("maBan"), rs.getString("maKhuVuc"), rs.getString("tenBan"),
							rs.getString("trangThai"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}