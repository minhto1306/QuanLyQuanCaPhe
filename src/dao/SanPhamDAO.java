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
		String sql = "INSERT INTO SanPham (maSanPham, maDanhMuc, tenSanPham, giaBan, trangThai, hinhAnh) VALUES (?, ?, ?, ?, ?, ?)";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, sp.getMaSanPham());
			pstmt.setString(2, sp.getMaDanhMuc());
			pstmt.setString(3, sp.getTenSanPham());
			pstmt.setDouble(4, sp.getGiaBan());
			pstmt.setBoolean(5, sp.isTrangThai());
			pstmt.setString(6, sp.getHinhAnh());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(SanPham sp) {
		String sql = "UPDATE SanPham SET maDanhMuc = ?, tenSanPham = ?, giaBan = ?, trangThai = ?, hinhAnh = ? WHERE maSanPham = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, sp.getMaDanhMuc());
			pstmt.setString(2, sp.getTenSanPham());
			pstmt.setDouble(3, sp.getGiaBan());
			pstmt.setBoolean(4, sp.isTrangThai());
			pstmt.setString(5, sp.getHinhAnh());
			pstmt.setString(6, sp.getMaSanPham());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		String sql = "DELETE FROM SanPham WHERE maSanPham = ?";
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
	public SanPham findById(String id) {
		SanPham sp = null;
		String sql = "SELECT * FROM SanPham WHERE maSanPham = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					sp = new SanPham(rs.getString("maSanPham"), rs.getString("maDanhMuc"), rs.getString("tenSanPham"),
							rs.getDouble("giaBan"), rs.getBoolean("trangThai"), rs.getString("hinhAnh"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sp;
	}

	@Override
	public List<SanPham> findAll() {
		List<SanPham> danhSach = new ArrayList<SanPham>();
		String sql = "SELECT * FROM SanPham";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					danhSach.add(new SanPham(rs.getString("maSanPham"), rs.getString("maDanhMuc"),
							rs.getString("tenSanPham"), rs.getDouble("giaBan"), rs.getBoolean("trangThai"),
							rs.getString("hinhAnh")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}
}