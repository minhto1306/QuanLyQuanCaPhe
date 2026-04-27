package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.NhanVien;
import util.DataBaseConnection;

public class NhanVienDAO implements BaseDAO<NhanVien, String> {

	@Override
	public boolean insert(NhanVien nv) {
		String sql = "INSERT INTO NhanVien (maNhanVien, tenDangNhap, hoTenNhanVien, soCCCD, soDienThoai) VALUES (?, ?, ?, ?, ?)";
		// Đưa Connection ra ngoài try-with-resources
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, nv.getMaNhanVien());
			pstmt.setString(2, nv.getTenDangNhap());
			pstmt.setString(3, nv.getHoTenNhanVien());
			pstmt.setString(4, nv.getSoCCCD());
			pstmt.setString(5, nv.getSoDienThoai());

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(NhanVien nv) {
		String sql = "UPDATE NhanVien SET hoTenNhanVien = ?, soCCCD = ?, soDienThoai = ? WHERE maNhanVien = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, nv.getHoTenNhanVien());
			pstmt.setString(2, nv.getSoCCCD());
			pstmt.setString(3, nv.getSoDienThoai());
			pstmt.setString(4, nv.getMaNhanVien());

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
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
	public NhanVien findById(String id) {
		String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new NhanVien(rs.getString("maNhanVien"), rs.getString("tenDangNhap"),
							rs.getString("hoTenNhanVien"), rs.getString("soCCCD"), rs.getString("soDienThoai"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<NhanVien> findAll() {
		List<NhanVien> danhSach = new ArrayList<NhanVien>();
		String sql = "SELECT * FROM NhanVien";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				danhSach.add(new NhanVien(rs.getString("maNhanVien"), rs.getString("tenDangNhap"),
						rs.getString("hoTenNhanVien"), rs.getString("soCCCD"), rs.getString("soDienThoai")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}

	public NhanVien timNhanVienTheoTenDangNhap(String tenDangNhap) {
		String sql = "SELECT * FROM NhanVien WHERE tenDangNhap = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, tenDangNhap);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new NhanVien(rs.getString("maNhanVien"), rs.getString("tenDangNhap"),
							rs.getString("hoTenNhanVien"), rs.getString("soCCCD"), rs.getString("soDienThoai"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}