package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.ChiTietDatBan;
import entity.ChiTietHoaDon;
import util.DataBaseConnection;

public class ChiTietDatBanDAO implements BaseDAO<ChiTietDatBan, String> {

	@Override
	public boolean insert(ChiTietDatBan ct) {
		String sql = "INSERT INTO ChiTietDatBan (maDatBan, maSanPham, soLuong, ghiChu) VALUES (?, ?, ?, ?)";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, ct.getMaDatBan());
			pstmt.setString(2, ct.getMaSanPham());
			pstmt.setInt(3, ct.getSoLuong());
			pstmt.setString(4, ct.getGhiChu());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(ChiTietDatBan ct) {
		String sql = "UPDATE ChiTietDatBan SET soLuong = ?, ghiChu = ? WHERE maDatBan = ? AND maSanPham = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setInt(1, ct.getSoLuong());
			pstmt.setString(2, ct.getGhiChu());
			pstmt.setString(3, ct.getMaDatBan());
			pstmt.setString(4, ct.getMaSanPham());
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		String sql = "DELETE FROM ChiTietDatBan WHERE maDatBan = ?";
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
	public ChiTietDatBan findById(String id) {
		String sql = "SELECT * FROM ChiTietDatBan WHERE maDatBan = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new ChiTietDatBan(rs.getString("maDatBan"), rs.getString("maSanPham"), rs.getInt("soLuong"),
							rs.getString("ghiChu"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ChiTietDatBan> findAll() {
		List<ChiTietDatBan> ds = new ArrayList<>();
		String sql = "SELECT * FROM ChiTietDatBan";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				ds.add(new ChiTietDatBan(rs.getString("maDatBan"), rs.getString("maSanPham"), rs.getInt("soLuong"),
						rs.getString("ghiChu")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ds;
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