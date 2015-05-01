/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author apple
 */
public class UserDAO {

    static ResultSet rs = null;

    public static UserBean login(UserBean bean) {
        String username = bean.getUsername();
        String password = bean.getPassword();
        String searchQuery
                = "select * from Users where username='"
                + username
                + "' AND password='"
                + password
                + "'";
        try {
            
            MyConnectionManager.getConnection();
            boolean more=MyConnectionManager.excute(searchQuery);
            rs=MyConnectionManager.getRs();
            
            rs.next();
            // if user does not exist set the isValid variable to false
            if (!more) {
                System.out.println("Sorry, you are not a registered user! Please sign up first");
                bean.setValid(false);
            } //if user exists set the isValid variable to true
            else if (more) {
                String name = rs.getString("name");
                bean.setName(name);
                bean.setUser_id(rs.getInt("user_id"));
                bean.setValid(true);
            }
            MyConnectionManager.closeConnection();
        } catch (Exception ex) {
            System.out.println("Log In failed: An Exception has occurred! " + ex);
        } //some exception handling
    
        return bean;

    }
    
    public static boolean register(UserBean bean) throws SQLException {

        //preparing some objects for connection 
        Statement stmt = null;

        String username = bean.getUsername();
        String password = bean.getPassword();
        String name = bean.getName();
        boolean result = false;
        String insertQuery
                = "insert into Users(username,password,name) values('"
                + username + "','"
                + password + "','"
                + name + "')";
            //connect to DB 
            MyConnectionManager.getConnection();
            result=MyConnectionManager.update(insertQuery);
            MyConnectionManager.closeConnection();
        return result;
    }
    
    public static ArrayList<UserBean> getAllUser(int user_id) throws SQLException {
        ArrayList<UserBean>a=new ArrayList<UserBean>();
        String searchQuery
                = "select * from Users where user_id !='"
                + user_id+"'and user_id not in(select friend_user_id from Friend where accept=1 and user_id='"
                + user_id+"');";
        MyConnectionManager.getConnection();
        MyConnectionManager.excute(searchQuery);
        ResultSet rs=MyConnectionManager.getRs();
            while(rs.next()){
                UserBean ub=new UserBean();
                ub.setName(rs.getString("name"));
                ub.setUserName(rs.getString("username"));
                ub.setUser_id(rs.getInt("user_id"));
                a.add(ub);
            }
        
        MyConnectionManager.closeConnection();
        return a;
    }
}
