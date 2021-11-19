package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOManager {
    public static String DB_URL = "jdbc:sqlite:dict_hh.db";
    public Connection conn = null;

    public DAOManager() throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Created DB Connection...");
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public void open() throws SQLException {
        try {
            if (this.conn == null) {
                this.conn = DriverManager.getConnection(DB_URL);
                System.out.println("Database Connected!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (this.conn != null) {
                this.conn.close();
                System.out.println("Connection has been close!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    static DAOManager getInstance() {
        return DAOManagerSingleton.instance.get();
    }

    public static void main(String[] args) throws Exception {
        DAOManager dao = DAOManager.getInstance();
        dao.open();
        //Các thao tác
        dao.close();
    }
}
