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
import java.util.HashSet;

/**
 *
 * @author 睿
 */
public class FriendDAO {


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
        //connect to DB 
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(insertQuery);
        MyConnectionManager.closeConnection();
        return result;
    }

    public static boolean delete(int friend_user_id, int user_id) throws SQLException {
        FriendBean bean = new FriendBean();
        bean.setAccept(false);
        boolean result = true;
        String deleteQuery
                = "delete from Friend where user_id="
                + user_id + " and friend_user_id="
                + friend_user_id + ";";
        
        String deleteQuery2
                = "delete from Friend where user_id="
                + friend_user_id + " and friend_user_id="
                + user_id + ";";
        String deleteQuery3=
                "delete from Circle_friend where user_id ="
                +user_id+" and circle_id in (select circle_id from Circle where user_id="
                +friend_user_id+");";
        String deleteQuery4=
                "delete from Circle_friend where user_id ="
                +friend_user_id+" and circle_id in (select circle_id from Circle where user_id="
                +user_id+");";
        //connect to DB 
        ConnectionManager cm =new ConnectionManager();
        cm.getConnection();
        result = cm.update(deleteQuery) && cm.update(deleteQuery2);
        cm.closeConnection();
        return result;
    }
    
    public static boolean respond(FriendBean bean, boolean accept) throws SQLException {
        int user_id = bean.getUser_id();
        int friend_user_id = bean.getFriend_user_id();
        String acceptQuery
                = "update Friend set accept=1 where user_id="
                +user_id+" and friend_user_id="
                + friend_user_id + ";";
        String acceptQuery2 = "insert into Friend(user_id,friend_user_id,accept) values("
                + friend_user_id + ","
                + user_id + ",1);";
        String refuseQuery
                = "delete from Friend where user_id="
                +user_id+" and friend_user_id="
                + friend_user_id + ";";
        boolean result = false;
        //connect to DB 
        MyConnectionManager.getConnection();
        if (accept) {
            result = MyConnectionManager.update(acceptQuery) && MyConnectionManager.update(acceptQuery2);
        } else {
            result = MyConnectionManager.update(refuseQuery);
        }
        MyConnectionManager.closeConnection();

        return result;

    }

    public static ArrayList<UserBean> searchAllFrind(int user_id) throws SQLException {
        String searchQuery
                = "select * from Users where user_id in(select friend_user_id from Friend where accept=1 and user_id="
                + user_id + ");";
        ArrayList<UserBean> a = new ArrayList<UserBean>();
        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
        cm.excute(searchQuery);
        ResultSet rs = cm.getRs();

        if(rs!=null){
            while (rs.next()) {
                if(rs==null) break;
                UserBean ub = new UserBean();
                ub.setName(rs.getString("name"));
                //ub.setUserName(rs.getString("username"));
                ub.setUser_id(rs.getInt("user_id"));
                a.add(ub);
            }
        }
        cm.closeConnection();

        return a;
    }
    
    public static ArrayList<UserBean> searchAllRequest(int user_id) throws SQLException {
           String createView=
                 "create view temp as (select user_id,request_at from Friend where accept=0 and friend_user_id="
                + user_id + ");";
           String searchQuery="select u.name,u.username,u.user_id,temp.request_at  from temp left join Users u on u.user_id=temp.user_id;"; 
           String dropView="drop view temp;";
                
        ArrayList<UserBean> a = new ArrayList<UserBean>();
        MyConnectionManager.getConnection();
        MyConnectionManager.update(createView);
        boolean result = MyConnectionManager.excute(searchQuery);
        ResultSet rs = MyConnectionManager.getRs();
        HashSet<Integer> set=new HashSet();
        while (rs.next()) {
            int userId=rs.getInt("user_id");
            if(!set.contains(userId)){
                set.add(userId);
                UserBean ub = new UserBean();
                ub.setName(rs.getString("name"));
                ub.setUserName(rs.getString("username"));
                ub.setUser_id(rs.getInt("user_id"));
                ub.setCreate_time(rs.getTimestamp("request_at"));
                a.add(ub);
            }
        }
        if(!result) System.out.println("No request list yet!");
        MyConnectionManager.update(dropView);
        MyConnectionManager.closeConnection();
        return a;
    }
    
    
    
    
    
    
    
}
