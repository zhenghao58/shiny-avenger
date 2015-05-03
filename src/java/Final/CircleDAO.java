/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 睿
 */
public class CircleDAO {
    public static boolean add(int circle_id, int user_id) throws SQLException {
        String addQuery="insert into Circle_friend values("
                +circle_id+","
                +user_id+");";
        boolean result = true;
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(addQuery);
        MyConnectionManager.closeConnection();
        return result;
    }
   
    public static boolean delete(int circle_id, int user_id) throws SQLException {
        String addQuery="delete from Circle_friend where circle_id="
                +circle_id+" and user_id="
                +user_id+";";
        boolean result = true;
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(addQuery);
        MyConnectionManager.closeConnection();
        return result;
    }
    
    public static boolean add(String circle_name, int user_id) throws SQLException {
        String addQuery="insert into Circle(circle_name,user_id) values('"
                +circle_name+"',"
                +user_id+");";
        boolean result = true;
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(addQuery);
        MyConnectionManager.closeConnection();
        return result;
    }
    
    public static boolean delete(String circle_name, int user_id) throws SQLException {
        String addQuery="delete from Circle where circle_name='"
                +circle_name+"' and user_id="
                +user_id+";";
        boolean result = true;
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(addQuery);
        MyConnectionManager.closeConnection();
        return result;
    }
    
    public static List<CircleBean> search(int user_id){
            String searchQuery
                    = "select * from Circle where user_id="
                    + user_id + ";";
            List<CircleBean> a = new ArrayList<>();
            try {
            MyConnectionManager.getConnection();
            boolean result = MyConnectionManager.excute(searchQuery);
            ResultSet rs = MyConnectionManager.getRs();
            while (rs.next()) {
                CircleBean cb = new CircleBean();
                cb.setCircle_id(rs.getInt("circle_id"));
                cb.setName(rs.getString("circle_name"));
                cb.setUser_id(user_id);
                a.add(cb);
            }
            
            MyConnectionManager.closeConnection();
            
        } catch (SQLException ex) {
            Logger.getLogger(CircleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }
    
    
    
    
}
