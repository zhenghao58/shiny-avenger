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
 * @author 睿
 */
public class MessageDao {

    static ResultSet rs = null;

    public static boolean post(MessageBean bean) throws SQLException {
        String text = bean.getText();
//        int location_id=bean.getLocation_id();
//        int circle_id =bean.getCicle_id();
        int user_id = bean.getUser_id();
        String privacy = bean.getPrivacy();
        boolean result = false;
        String insertQuery = "insert into Messages(text,user_id,privacy) values('"
                + text + "','"
                + user_id + "','"
                //                +circle_id+"','"
                + privacy + "');";
        //connect to DB 
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(insertQuery);
        MyConnectionManager.closeConnection();
        return result;
    }

    public static ArrayList<MessageBean> search(int user_id) throws SQLException {
        ArrayList<MessageBean> a = new ArrayList<MessageBean>();
        String searchQuery = "select * from Message where user_id='"
                + user_id + "';";

        MyConnectionManager.getConnection();
        boolean result = MyConnectionManager.excute(searchQuery);
        ResultSet rs = MyConnectionManager.getRs();
        while (rs.next()) {
            MessageBean ub = new MessageBean();
            ub.setPrivacy(rs.getString("privacy"));
            ub.setText(rs.getString("text"));
            ub.setMessage_id(Integer.parseInt(rs.getString("message_id")));
            ub.setTime(rs.getTimestamp("time"));
            a.add(ub);
        }

        MyConnectionManager.closeConnection();
        return a;
    }

}
