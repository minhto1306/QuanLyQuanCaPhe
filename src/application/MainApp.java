package application;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import controller.DangNhapController;
import controller.ManHinhChinhController;
import ui.FrmDangNhap;
import ui.FrmManHinhChinh;

public class MainApp {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Khởi tạo FlatLaf thất bại!");
		}

		FrmManHinhChinh mainView = new FrmManHinhChinh();
		FrmDangNhap loginView = new FrmDangNhap(mainView);

		ManHinhChinhController dashboardController = new ManHinhChinhController(mainView);
		DangNhapController authController = new DangNhapController(loginView, dashboardController);

		authController.batDau();
	}
}