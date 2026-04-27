package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.SanPham;
import util.DataBaseConnection;

public class SanPhamDAO implements BaseDAO<SanPham, String> {

	@Override
	public boolean insert(SanPham sp) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO SanPham (maSanPham, maDanhMuc, tenSanPham, giaBan, trangThai) VALUES (?, ?, ?, ?, ?)";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, sp.getMaSanPham());
			pstmt.setString(2, sp.getMaDanhMuc());
			pstmt.setString(3, sp.getTenSanPham());
			pstmt.setDouble(4, sp.getGiaBan());
			pstmt.setBoolean(5, sp.isTrangThai());

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(SanPham sp) {
		// TODO Auto-generated method stub
		String sql = "UPDATE SanPham SET maDanhMuc = ?, tenSanPham = ?, giaBan = ?, trangThai = ? WHERE maSanPham = ?";
		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql)) {

			pstmt.setString(1, sp.getMaDanhMuc());
			pstmt.setString(2, sp.getTenSanPham());
			pstmt.setDouble(3, sp.getGiaBan());
			pstmt.setBoolean(4, sp.isTrangThai());
			pstmt.setString(5, sp.getMaSanPham());

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM SanPham WHERE maSanPham = ?";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql)) {

			pstmt.setString(1, id);
			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public SanPham findById(String id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM SanPham WHERE maSanPham = ?";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new SanPham(rs.getString("maSanPham"), rs.getString("maDanhMuc"), rs.getString("tenSanPham"),
							rs.getDouble("giaBan"), rs.getBoolean("trangThai"));
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<SanPham> findAll() {
		List<SanPham> danhSach = new ArrayList<SanPham>();
		String sql = "SELECT * FROM SanPham";

		// RÚT CONNECTION RA NGOÀI TRY:
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				danhSach.add(new SanPham(rs.getString("maSanPham"), rs.getString("maDanhMuc"),
						rs.getString("tenSanPham"), rs.getDouble("giaBan"), rs.getBoolean("trangThai")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return danhSach;
	}

}
