/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author 睿
 */
public class MessageDAO {

    //static ResultSet rs = null;
public static boolean post(MessageBean bean) throws SQLException {
        String text = bean.getText();
//        int location_id=bean.getLocation_id();
        int circle_id = bean.getCircle_id();
        int user_id = bean.getUser_id();
        int location_id=bean.getLocation_id();
        String privacy = bean.getPrivacy();
        boolean result = false;
        String insertQuery;
        if (circle_id == 0 && location_id == 0) {
            insertQuery = "insert into Messages(text, user_id, privacy) values('"
                    + text + "',"
                    + user_id + ",'"
                    + privacy + "');";
        } else if (circle_id != 0 && location_id == 0){
            insertQuery = "insert into Messages(text, user_id, circle_id, privacy) values('"
                    + text + "',"
                    + user_id + ","
                    + circle_id + ",'"
                    + privacy + "');";
        }else if (circle_id == 0 && location_id != 0){
            insertQuery = "insert into Messages(text, user_id, location_id, privacy) values('"
                    + text + "',"
                    + user_id + ","
                    + location_id + ",'"
                    + privacy + "');";
        }else {
            insertQuery = "insert into Messages(text, user_id, circle_id, location_id, privacy) values('"
                    + text + "',"
                    + user_id + ","
                    + circle_id+ ","
                    + location_id + ",'"
                    + privacy + "');";
        }
        //connect to DB 
        MyConnectionManager.getConnection();
        result = MyConnectionManager.update(insertQuery);
        MyConnectionManager.closeConnection();
        return result;
    }

    public static ArrayList<MessageBean> search(int user_id) throws SQLException {
        ArrayList<MessageBean> a = new ArrayList<>();
        String searchQuery = "select * from Messages where user_id="
                + user_id + ";";
        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
        boolean result = cm.excute(searchQuery);
        ResultSet rs = cm.getRs();
        while (rs.next()&&result) {
            MessageBean ub = new MessageBean();
            ub.setPrivacy(rs.getString("privacy"));
            ub.setText(rs.getString("text"));
            ub.setMessage_id(rs.getInt("message_id"));
            ub.setCircle_id(rs.getInt("circle_id"));
            ub.setTime(rs.getTimestamp("time"));
            ub.setUser_id(user_id);
            ub.setUser_name(UserDAO.NameById(user_id));
            ub.setLocation_id(rs.getInt("location_id"));
            a.add(ub);
        }
        
        cm.closeConnection();
        return a;
    }


    public static List<MessageBean> searchAll(int user_id) throws SQLException {
        //add user_id's messages
        List<MessageBean> a = new ArrayList<>();
        a = search(user_id);

        ArrayList<UserBean> uba = FriendDAO.searchAllFrind(user_id);
        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();

        String searchQuery=
                "select * from Messages m where m.user_id in (select friend_user_id from Friend  f where f.user_id="
                +user_id+") "
                + "and m.circle_id in(select circle_id from Circle_friend cf where cf.user_id="
                +user_id+") and privacy='circle';";
        cm.excute(searchQuery);
        ResultSet rs = cm.getRs();
        while (rs.next()) {
                MessageBean ub = new MessageBean();
                ub.setPrivacy(rs.getString("privacy"));
                ub.setText(rs.getString("text"));
                ub.setMessage_id(Integer.parseInt(rs.getString("message_id")));
                ub.setTime(rs.getTimestamp("time"));
                int message_user_id = rs.getInt("user_id");
                ub.setUser_id(message_user_id);
                ub.setUser_name(UserDAO.NameById(message_user_id));
                ub.setLocation_id(rs.getInt("location_id"));
                a.add(ub);
        }
        
        String searchQuery1=
                "select * from Messages m where m.user_id in (select friend_user_id from Friend  f where f.user_id="
                +user_id+") "
                + "and privacy='friend';";
        cm.excute(searchQuery1);
        rs = cm.getRs();
        while (rs.next()) {
                MessageBean ub = new MessageBean();
                ub.setPrivacy(rs.getString("privacy"));
                ub.setText(rs.getString("text"));
                ub.setMessage_id(Integer.parseInt(rs.getString("message_id")));
                ub.setTime(rs.getTimestamp("time"));
                int message_user_id = rs.getInt("user_id");
                ub.setUser_id(message_user_id);
                ub.setUser_name(UserDAO.NameById(message_user_id));
                ub.setLocation_id(rs.getInt("location_id"));
                a.add(ub);
        }
        
        String addQuery = "select * from Messages where privacy='public'";
        cm.excute(addQuery);
        rs = cm.getRs();
        HashSet<Integer> set = new HashSet();
        for (MessageBean mb : a) {
            set.add(mb.getMessage_id());
        }
        
        while (rs.next()) {
            int messageId = rs.getInt("message_id");
            if (!set.contains(messageId)) {
                set.add(messageId);
                MessageBean ub = new MessageBean();
                ub.setPrivacy(rs.getString("privacy"));
                ub.setText(rs.getString("text"));
                ub.setMessage_id(Integer.parseInt(rs.getString("message_id")));
                ub.setTime(rs.getTimestamp("time"));
                int message_user_id = rs.getInt("user_id");
                ub.setUser_id(message_user_id);
                ub.setUser_name(UserDAO.NameById(message_user_id));
                ub.setLocation_id(rs.getInt("location_id"));
                a.add(ub);
            }
        }

        cm.closeConnection();
        a.sort(new Comparator<MessageBean>() {
            @Override
            public int compare(MessageBean mb1, MessageBean mb2) {
                return -mb1.getTime().compareTo(mb2.getTime());
            }
        });

        return a;
    }

    public static List<MessageBean> search(int user_id,String str) throws SQLException {
        List<MessageBean> l=searchAll(user_id);
        List<MessageBean> l1=new ArrayList<>();
        for(MessageBean mb:l)
            if(mb.getText().matches("(.*)"+str+"(.*)"))
                l1.add(mb);
        return l1;
    }

}
