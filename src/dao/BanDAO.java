package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Ban;
import util.DataBaseConnection;

public class BanDAO implements BaseDAO<Ban, String> {

	@Override
	public boolean insert(Ban ban) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO Ban (maBan, maKhuVuc, tenBan, trangThai) VALUES (?,?,?,?)";
		try (Connection cnt = DataBaseConnection.getInstance().getConnection();
				PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, ban.getMaBan());
			ps.setString(2, ban.getMaKhuVuc());
			ps.setString(3, ban.getTenBan());
			ps.setBoolean(4, ban.isTrangThai());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Ban ban) {
		String sql = "UPDATE Ban SET tenBan = ? WHERE maBan = ? ";
		try (Connection cnt = DataBaseConnection.getInstance().getConnection();
				PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, ban.getMaBan());
			ps.setString(2, ban.getMaKhuVuc());
			ps.setString(3, ban.getTenBan());
			ps.setBoolean(4, ban.isTrangThai());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String id) {
		String sql = "DELETE FROM Ban WHERE maBan = ? ";
		try (Connection cnt = DataBaseConnection.getInstance().getConnection();
				PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ban findById(String id) {
		String sql = "SELECT * FROM Ban WHERE maBan = ?";
		try (Connection cnt = DataBaseConnection.getInstance().getConnection();
				PreparedStatement ps = cnt.prepareStatement(sql)) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new Ban(rs.getString("maBan"), rs.getString("maKhuVuc"), rs.getString("tenBan"),
							rs.getBoolean("trangThai"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ban> findAll() {
		List<Ban> danhSach = new ArrayList<Ban>();
		String sql = "SELECT * FROM DanhMuc";
		try (Connection cnt = DataBaseConnection.getInstance().getConnection();
				PreparedStatement ps = cnt.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				danhSach.add(new Ban(rs.getString("maBan"), rs.getString("maKhuVuc"), rs.getString("tenBan"),
						rs.getBoolean("trangThai")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return danhSach;
	}

}
