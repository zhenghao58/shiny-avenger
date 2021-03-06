/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Administrator
 */
public class MyConnectionManager {
    private static Connection con;
    private static String url;
    private static ResultSet rs = null;
    private static Statement stmt = null;
    public static void getConnection() {

        try {
            String url = "jdbc:mysql://tourini.czlgpmgnvb64.us-east-1.rds.amazonaws.com:3306/Tourini?zeroDateTimeBehavior=convertToNull";
            // assuming "DataSource" is your DataSource name

            Class.forName("com.mysql.jdbc.Driver");
            try {
                setCon(DriverManager.getConnection(url, "zhenghao", "nyupoly2015"));

                // assuming your SQL Server's	username is "username"               
                // and password is "password"
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        
        try {
            stmt = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(MyConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection() {
         if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                stmt = null;
            }

            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }

                con = null;
            }
    }
    
    public static boolean excute(String sql) throws SQLException {
        rs=stmt.executeQuery(sql);
        boolean result = rs.next();
        if(result) rs.previous();
        return result;
    }
    
    public static boolean update(String sql) throws SQLException {
        int i=0;
        i=stmt.executeUpdate(sql);
        return i==1;
    }
    
    /**
     * @return the con
     */
    public static Connection getCon() {
        return con;
    }

    /**
     * @param aCon the con to set
     */
    public static void setCon(Connection aCon) {
        con = aCon;
    }

    /**
     * @return the url
     */
    public static String getUrl() {
        return url;
    }

    /**
     * @param aUrl the url to set
     */
    public static void setUrl(String aUrl) {
        url = aUrl;
    }

    /**
     * @return the rs
     */
    public static ResultSet getRs() {
        return rs;
    }

    /**
     * @param aRs the rs to set
     */
    public static void setRs(ResultSet aRs) {
        rs = aRs;
    }
}
