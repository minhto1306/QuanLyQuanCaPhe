package dao;

import java.util.List;

import entity.DanhMuc;

public class DanhMucDAO implements BaseDAO<DanhMuc, String> {

	@Override
	public boolean insert(DanhMuc dm) {
		String sql = "INSERT INTO DanhMuc (maDanhMuc, tenDanhMuc) VALUES (?, ?)";
		try (java.sql.Connection cnnct = util.DataBaseConnection.getInstance().getConnection();
				java.sql.PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, dm.getMaDanhMuc());
			pstmt.setString(2, dm.getTenDanhMuc());
			return pstmt.executeUpdate() > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(DanhMuc dm) {
		String sql = "UPDATE DanhMuc SET tenDanhMuc = ? WHERE maDanhMuc = ?";
		try (java.sql.Connection cnnct = util.DataBaseConnection.getInstance().getConnection();
				java.sql.PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, dm.getTenDanhMuc());
			pstmt.setString(2, dm.getMaDanhMuc());
			return pstmt.executeUpdate() > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		String sql = "DELETE FROM DanhMuc WHERE maDanhMuc = ?";
		try (java.sql.Connection cnnct = util.DataBaseConnection.getInstance().getConnection();
				java.sql.PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, id);
			return pstmt.executeUpdate() > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public DanhMuc findById(String id) {
		String sql = "SELECT * FROM DanhMuc WHERE maDanhMuc = ?";
		try (java.sql.Connection cnnct = util.DataBaseConnection.getInstance().getConnection();
				java.sql.PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try (java.sql.ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new DanhMuc(rs.getString("maDanhMuc"), rs.getString("tenDanhMuc"));
				}
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return null;
	} // <-- CÁI NGOẶC NHỌN BỊ THIẾU NẰM Ở ĐÂY NÌ QUÝ VỊ

	@Override
	public List<DanhMuc> findAll() {
		List<DanhMuc> danhSach = new java.util.ArrayList<DanhMuc>();
		String sql = "SELECT * FROM DanhMuc";

		// RÚT CONNECTION RA NGOÀI TRY:
		java.sql.Connection cnnct = util.DataBaseConnection.getInstance().getConnection();
		try (java.sql.PreparedStatement pstmt = cnnct.prepareStatement(sql);
				java.sql.ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				danhSach.add(new DanhMuc(rs.getString("maDanhMuc"), rs.getString("tenDanhMuc")));
			}

		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}

}