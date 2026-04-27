package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entity.HoaDon;
import util.DataBaseConnection;

public class HoaDonDAO implements BaseDAO<HoaDon, String> {

	@Override
	public boolean insert(HoaDon hd) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO HoaDon (maHoaDon, maNhanVien, maBan, thoiGianLap, tongTien, thueVAT, giamGia, thanhTien, phuongThucThanhToan, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql)) {
			pstmt.setString(1, hd.getMaHoaDon());
			pstmt.setString(2, hd.getMaNhanVien());
			pstmt.setString(3, hd.getMaBan());
			pstmt.setTimestamp(4, Timestamp.valueOf(hd.getThoiGianLap()));
			pstmt.setDouble(5, hd.getTongTien());
			pstmt.setDouble(6, hd.getThueVAT());
			pstmt.setDouble(7, hd.getGiamGia());
			pstmt.setDouble(8, hd.getThanhTien());
			pstmt.setString(9, hd.getPhuongThucThanhToan());
			pstmt.setBoolean(10, hd.isTrangThai());

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(HoaDon hd) {
		// TODO Auto-generated method stub
		String sql = "UPDATE HoaDon SET maNhanVien = ?, maBan = ?, thoiGianLap = ?, tongTien = ?, thueVAT = ?, giamGia = ?, thanhTien = ?, phuongThucThanhToan = ?, trangThai = ? WHERE maHoaDon = ?";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql)) {

			pstmt.setString(1, hd.getMaNhanVien());
			pstmt.setString(2, hd.getMaBan());
			pstmt.setTimestamp(3, Timestamp.valueOf(hd.getThoiGianLap()));
			pstmt.setDouble(4, hd.getTongTien());
			pstmt.setDouble(5, hd.getThueVAT());
			pstmt.setDouble(6, hd.getGiamGia());
			pstmt.setDouble(7, hd.getThanhTien());
			pstmt.setString(8, hd.getPhuongThucThanhToan());
			pstmt.setBoolean(9, hd.isTrangThai());
			pstmt.setString(10, hd.getMaHoaDon());

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
		String sql = "DELETE FROM HoaDon WHERE maHoaDon = ?";

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
	public HoaDon findById(String id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql)) {

			pstmt.setString(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new HoaDon(rs.getString("maHoaDon"), rs.getString("maNhanVien"), rs.getString("maBan"),
							rs.getTimestamp("thoiGianLap").toLocalDateTime(), rs.getDouble("tongTien"),
							rs.getDouble("thueVAT"), rs.getDouble("giamGia"), rs.getDouble("thanhTien"),
							rs.getString("phuongThucThanhToan"), rs.getBoolean("trangThai"));
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<HoaDon> findAll() {
		// TODO Auto-generated method stub
		List<HoaDon> danhSach = new ArrayList<HoaDon>();
		String sql = "SELECT * FROM HoaDon";

		try (Connection cnnct = DataBaseConnection.getInstance().getConnection();
				PreparedStatement pstmt = cnnct.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				danhSach.add(new HoaDon(rs.getString("maHoaDon"), rs.getString("maNhanVien"), rs.getString("maBan"),
						rs.getTimestamp("thoiGianLap").toLocalDateTime(), rs.getDouble("tongTien"),
						rs.getDouble("thueVAT"), rs.getDouble("giamGia"), rs.getDouble("thanhTien"),
						rs.getString("phuongThucThanhToan"), rs.getBoolean("trangThai")));
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return danhSach;
	}
}