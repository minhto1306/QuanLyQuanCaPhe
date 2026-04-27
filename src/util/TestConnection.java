package util;

public class TestConnection {
	public static void main(String[] args) {
		System.out.println("Đang kêt nối!");
		DataBaseConnection dbc = DataBaseConnection.getInstance();

		if (dbc.getConnection() != null) {
			dbc.disconnect();
		}
	}
}
