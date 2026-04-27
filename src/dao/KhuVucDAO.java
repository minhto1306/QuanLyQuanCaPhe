package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.KhuVuc;
import util.DataBaseConnection;

public class KhuVucDAO implements BaseDAO<KhuVuc, String> {

	@Override
	public boolean insert(KhuVuc kv) {
		String sql = "INSERT INTO KhuVuc (maKhuVuc, tenKhuVuc, phuThu) VALUES (?, ?, ?)";

		// Lấy connection ra ngoài block try-with-resources để tránh bị auto-close
		Connection cnnct = DataBaseConnection.getInstance().getConnection();

		// Chỉ đưa PreparedStatement vào trong try() để nó tự giải phóng bộ nhớ khi chạy
		// xong
		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {

			pstmt.setString(1, kv.getMaKhuVuc());
			pstmt.setString(2, kv.getTenKhuVuc());
			pstmt.setDouble(3, kv.getPhuThu());

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(KhuVuc kv) {
		String sql = "UPDATE KhuVuc SET tenKhuVuc = ?, phuThu = ? WHERE maKhuVuc = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();

		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {

			pstmt.setString(1, kv.getTenKhuVuc());
			pstmt.setDouble(2, kv.getPhuThu());
			pstmt.setString(3, kv.getMaKhuVuc());

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String id) {
		String sql = "DELETE FROM KhuVuc WHERE maKhuVuc = ?";
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
	public KhuVuc findById(String id) {
		String sql = "SELECT * FROM KhuVuc WHERE maKhuVuc = ?";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();

		try (PreparedStatement pstmt = cnnct.prepareStatement(sql)) {

			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new KhuVuc(rs.getString("maKhuVuc"), rs.getString("tenKhuVuc"), rs.getDouble("phuThu"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<KhuVuc> findAll() {
		List<KhuVuc> danhSach = new ArrayList<>();
		String sql = "SELECT * FROM KhuVuc";
		Connection cnnct = DataBaseConnection.getInstance().getConnection();

		try (PreparedStatement pstmt = cnnct.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				danhSach.add(new KhuVuc(rs.getString("maKhuVuc"), rs.getString("tenKhuVuc"), rs.getDouble("phuThu")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return danhSach;
	}
}