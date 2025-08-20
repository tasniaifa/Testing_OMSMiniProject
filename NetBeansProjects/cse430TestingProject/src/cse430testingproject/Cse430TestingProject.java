import java.sql.Connection;
import java.sql.DriverManager;

public class Cse430TestingProject {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/order_management_db";
        String user = "root";
        String password = "Mahibe@961";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
