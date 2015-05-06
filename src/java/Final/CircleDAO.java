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
        String addQuery = "insert into Circle_friend values("
                + circle_id + ","
                + user_id + ");";
        boolean result = true;
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(addQuery);
        MyConnectionManager.closeConnection();
        return result;
    }

    public static boolean quit(int circle_id, int user_id) throws SQLException {
        String addQuery = "delete from Circle_friend where circle_id="
                + circle_id + " and user_id="
                + user_id + ";";
        boolean result = true;
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(addQuery);
        MyConnectionManager.closeConnection();
        return result;
    }

    public static boolean add(String circle_name, int user_id) throws SQLException {
        String addQuery = "insert into Circle(circle_name,user_id) values('"
                + circle_name + "',"
                + user_id + ");";
        System.out.println(addQuery);
        String searchQuery = "select circle_id from Circle where circle_name='"
                + circle_name + "' and user_id="
                + user_id + ";";
        System.out.println(searchQuery);
        boolean result = true;
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(addQuery);

        MyConnectionManager.excute(searchQuery);
        ResultSet rs = MyConnectionManager.getRs();
        rs.next();
        int circle_id = rs.getInt("circle_id");
        add(circle_id, user_id);
        MyConnectionManager.closeConnection();
        return result;
    }

    public static boolean delete(String circle_name, int user_id) throws SQLException {
        String addQuery = "delete from Circle where circle_name='"
                + circle_name + "' and user_id="
                + user_id + ";";
        boolean result = true;
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(addQuery);
        MyConnectionManager.closeConnection();
        return result;
    }

    //return the circles created by user_id

    public static List<CircleBean> search(int user_id) {
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

    //return the users of circle_id

    public static List<UserBean> searchUsersByCircleId(int circle_id, int user_id) {
        String searchQuery
                = "select * from Users where user_id!=" + user_id + " and user_id in (select user_id from Circle_friend where circle_id="
                + circle_id + ");";
        List<UserBean> a = new ArrayList<>();
        try {
            if(MyConnectionManager.getCon()==null) MyConnectionManager.getConnection();
            boolean result = MyConnectionManager.excute(searchQuery);
            ResultSet rs = MyConnectionManager.getRs();
            if (rs != null && result) {
                while (rs.next()) {
                    if(rs==null) break;
                    UserBean ub = new UserBean();
                    ub.setName(rs.getString("name"));
                    ub.setUserName(rs.getString("username"));
                    ub.setUser_id(rs.getInt("user_id"));
                    a.add(ub);
                }
            }

            MyConnectionManager.closeConnection();

        } catch (SQLException ex) {
            Logger.getLogger(CircleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

}
