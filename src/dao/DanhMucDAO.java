package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.DanhMuc;
import util.DataBaseConnection;

public class DanhMucDAO implements BaseDAO<DanhMuc, String> {

	@Override
	public boolean insert(DanhMuc dm) {
		String sql = "INSERT INTO DanhMuc (maDanhMuc, tenDanhMuc) VALUES (?, ?)";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, dm.getMaDanhMuc());
			pstmt.setString(2, dm.getTenDanhMuc());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(DanhMuc dm) {
		String sql = "UPDATE DanhMuc SET tenDanhMuc = ? WHERE maDanhMuc = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, dm.getTenDanhMuc());
			pstmt.setString(2, dm.getMaDanhMuc());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		String sql = "DELETE FROM DanhMuc WHERE maDanhMuc = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, id);
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public DanhMuc findById(String id) {
		DanhMuc dm = null;
		String sql = "SELECT * FROM DanhMuc WHERE maDanhMuc = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					dm = new DanhMuc(rs.getString("maDanhMuc"), rs.getString("tenDanhMuc"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dm;
	}

	@Override
	public List<DanhMuc> findAll() {
		List<DanhMuc> danhSach = new ArrayList<DanhMuc>();
		String sql = "SELECT * FROM DanhMuc";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					danhSach.add(new DanhMuc(rs.getString("maDanhMuc"), rs.getString("tenDanhMuc")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}
}