package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.TaiKhoan;
import util.DataBaseConnection;

public class TaiKhoanDAO implements BaseDAO<TaiKhoan, String> {

	public TaiKhoan kiemTraDangNhap(String tenDangNhap, String matKhau) {
		TaiKhoan taiKhoan = null;
		String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ? AND matKhau = ? AND trangThai = 1";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();

		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {

			pstmt.setString(1, tenDangNhap);
			pstmt.setString(2, matKhau);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					taiKhoan = new TaiKhoan(rs.getString("tenDangNhap"), rs.getString("matKhau"),
							rs.getBoolean("trangThai"), rs.getString("maVaiTro"));
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("Lỗi lúc đăng nhập! " + e.getMessage());
		}
		return taiKhoan;
	}

	@Override
	public boolean insert(TaiKhoan entity) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO TaiKhoan (tenDangNhap, matKhau, trangThai, maVaiTro) VALUES (?, ?, ?, ?)";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql);) {

			pstmt.setString(1, entity.getTenDangNhap());
			pstmt.setString(2, entity.getMatKhau());
			pstmt.setBoolean(3, entity.isTrangThai());
			pstmt.setString(4, entity.getVaiTro());

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(TaiKhoan entity) {
		// TODO Auto-generated method stub
		String sql = "UPDATE TaiKhoan SET matKhau = ?, trangThai = ?, maVaiTro = ? WHERE tenDangNhap = ?";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql);) {

			pstmt.setString(1, entity.getMatKhau());
			pstmt.setBoolean(2, entity.isTrangThai());
			pstmt.setString(3, entity.getVaiTro());
			pstmt.setString(4, entity.getTenDangNhap());

			return pstmt.executeUpdate() > 0;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM TaiKhoan WHERE tenDangNhap = ?";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql);) {

			pstmt.setString(1, id);

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public TaiKhoan findById(String id) {
		TaiKhoan tk = null;
		String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ?";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql);) {

			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					tk = new TaiKhoan(rs.getString("tenDangNhap"), rs.getString("matKhau"), rs.getBoolean("trangThai"),
							rs.getString("maVaiTro"));
				}

			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return tk;
	}

	@Override
	public List<TaiKhoan> findAll() {
		List<TaiKhoan> danhSach = new ArrayList<TaiKhoan>();
		String sql = "SELECT * FROM TaiKhoan";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql);) {

			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					TaiKhoan tk = new TaiKhoan(rs.getString("tenDangNhap"), rs.getString("matKhau"),
							rs.getBoolean("trangThai"), rs.getString("maVaiTro"));
					danhSach.add(tk);
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return danhSach;
	}

}
