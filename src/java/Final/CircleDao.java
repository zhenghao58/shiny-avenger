/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.SQLException;

/**
 *
 * @author 睿
 */
public class CircleDao {
    public static boolean add(int circle_id, int user_id) throws SQLException {
        String addQuery="insert into Circle_friend values('"
                +circle_id+"','"
                +user_id+"');";
        boolean result = true;
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(addQuery);
        MyConnectionManager.closeConnection();
        return result;
    }
    
    public static boolean delete(int circle_id, int user_id) throws SQLException {
        String addQuery="delete from Circle_friend values('"
                +circle_id+"','"
                +user_id+"');";
        boolean result = true;
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(addQuery);
        MyConnectionManager.closeConnection();
        return result;
    }
    
    
    
    
    
    
    
}
