/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author 睿
 */
public class FriendDAO {

    static Connection currentCon = null;
    static ResultSet rs = null;

    public static int idByName(String name) throws SQLException {
        
        String searchQuery
                = "select * from Users where name='"
                + name + "';";
        //connect to DB 
        
        MyConnectionManager.getConnection();
        MyConnectionManager.excute(searchQuery);
        ResultSet rs=MyConnectionManager.getRs();
        rs.next();
        int user_id = rs.getInt("user_id");
        System.out.println(user_id);
        MyConnectionManager.closeConnection();
        return user_id;
    }

    public static boolean request(String friend_name, int user_id) throws SQLException {
        FriendBean bean = new FriendBean();
        int friend_user_id = idByName(friend_name);
        bean.setAccept(false);
        boolean result = true;
        String insertQuery
                = "insert into Friend(user_id,friend_user_id) values('"
                + user_id + "','"
                + friend_user_id + "');";
        System.out.println(insertQuery);
        //connect to DB 
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(insertQuery);
        MyConnectionManager.closeConnection();
        return result;
    }

    public static boolean respond(FriendBean bean, boolean accept) throws SQLException {
        int user_id = bean.getUser_id();
        int friend_id = bean.getFriend_id();
        String acceptQuery
                = "update Friend set accept=1 where friend_id='"
                + friend_id + "';insert Friend(user_id,friend_id,accept) values("
                + friend_id + "','"
                + user_id + ",1);";
        String refuseQuery
                = "delete from Friend where friend_id='"
                + friend_id + "';";
        boolean result = false;
        //connect to DB 
        MyConnectionManager.getConnection();
        if (accept) {
            result = MyConnectionManager.excute(acceptQuery);
        } else {
            result = MyConnectionManager.excute(refuseQuery);
        }
        MyConnectionManager.closeConnection();

        return result;

    }

    public static ArrayList<UserBean> searchAllFrind(int user_id) throws SQLException {
        String searchQuery
                = "select * from Users where user_id in(select friend_user_id from Friend where accept=1 and user_id='"
                + user_id + "');";
        ArrayList<UserBean> a = new ArrayList<UserBean>();
        MyConnectionManager.getConnection();
        boolean result = MyConnectionManager.excute(searchQuery);
        ResultSet rs = MyConnectionManager.getRs();
        while (rs.next()) {
            UserBean ub = new UserBean();
            ub.setName(rs.getString("name"));
            ub.setUserName(rs.getString("username"));
            ub.setUser_id(rs.getInt("user_id"));
            a.add(ub);
        }

        MyConnectionManager.closeConnection();
        return a;
    }
}
