package application;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import controller.AuthenticationController;
import controller.MainDashboardController;
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

		MainDashboardController dashboardController = new MainDashboardController(mainView);
		AuthenticationController authController = new AuthenticationController(loginView, dashboardController);

		authController.batDau();
	}
}