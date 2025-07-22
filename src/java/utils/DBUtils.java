package utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.pkcs11.Secmod;

public class DBUtils {

    private static final String DB_NAME = "PRJ301_TungLongDB_TEST_3";
    private static final String DB_USER_NAME = "SA";
    private static final String DB_PASSWORD = "12345";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        conn = DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
        return conn;
    }

    public static void main(String[] args) {
        try {
            System.out.println(DBUtils.getConnection());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing resourse " + e.getMessage());
        }

        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing resourse " + e.getMessage());
        }

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing resourse " + e.getMessage());
        }
    }

    public static void closeResources(Connection conn, PreparedStatement ps) {
        closeResources(conn, ps, null);
    }
}
