package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.ChiTietHoaDon;
import util.DataBaseConnection;

public class ChiTietHoaDonDAO implements BaseDAO<ChiTietHoaDon, String> {

	@Override
	public boolean insert(ChiTietHoaDon cthd) {
		String sql = "INSERT INTO ChiTietHoaDon (maHoaDon, maSanPham, soLuong, giaBan, ghiChu) VALUES (?, ?, ?, ?, ?)";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, cthd.getMaHoaDon());
			pstmt.setString(2, cthd.getMaSanPham());
			pstmt.setInt(3, cthd.getSoLuong());
			pstmt.setDouble(4, cthd.getGiaBan());
			pstmt.setString(5, cthd.getGhiChu());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(ChiTietHoaDon cthd) {
		String sql = "UPDATE ChiTietHoaDon SET soLuong = ?, giaBan = ?, ghiChu = ? WHERE maHoaDon = ? AND maSanPham = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setInt(1, cthd.getSoLuong());
			pstmt.setDouble(2, cthd.getGiaBan());
			pstmt.setString(3, cthd.getGhiChu());
			pstmt.setString(4, cthd.getMaHoaDon());
			pstmt.setString(5, cthd.getMaSanPham());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		String sql = "DELETE FROM ChiTietHoaDon WHERE maHoaDon = ?";
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
	public ChiTietHoaDon findById(String id) {
		String sql = "SELECT * FROM ChiTietHoaDon WHERE maHoaDon = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new ChiTietHoaDon(rs.getString("maHoaDon"), rs.getString("maSanPham"), rs.getInt("soLuong"),
							rs.getDouble("giaBan"), rs.getString("ghiChu"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ChiTietHoaDon> findAll() {
		List<ChiTietHoaDon> danhSach = new ArrayList<ChiTietHoaDon>();
		String sql = "SELECT * FROM ChiTietHoaDon";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				danhSach.add(new ChiTietHoaDon(rs.getString("maHoaDon"), rs.getString("maSanPham"),
						rs.getInt("soLuong"), rs.getDouble("giaBan"), rs.getString("ghiChu")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}

	// Thêm vào trong ChiTietHoaDonDAO.java
	public List<ChiTietHoaDon> findByMaHoaDon(String maHD) {
		List<ChiTietHoaDon> danhSach = new ArrayList<>();
		String sql = "SELECT * FROM ChiTietHoaDon WHERE maHoaDon = ?";
		Connection cnnct = util.DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, maHD);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					danhSach.add(new ChiTietHoaDon(rs.getString("maHoaDon"), rs.getString("maSanPham"),
							rs.getInt("soLuong"), rs.getDouble("giaBan"), rs.getString("ghiChu")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}
}