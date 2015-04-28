/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author apple
 */
public class ConnectionManager {

    static Connection con;
    static String url;

    public static Connection getConnection() {

        try {
            String url = "jdbc:mysql://tourini.czlgpmgnvb64.us-east-1.rds.amazonaws.com:3306/Tourini?zeroDateTimeBehavior=convertToNull";
            // assuming "DataSource" is your DataSource name

            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = DriverManager.getConnection(url, "zhenghao", "nyupoly2015");

                // assuming your SQL Server's	username is "username"               
                // and password is "password"
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return con;
    }
}
