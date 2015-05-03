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
        String privacy = bean.getPrivacy();
        boolean result = false;
        String insertQuery;
        if (circle_id == 0) {
            insertQuery = "insert into Messages(text, user_id, privacy) values('"
                    + text + "',"
                    + user_id + ",'"
                    + privacy + "');";
        } else {
            insertQuery = "insert into Messages(text, user_id, circle_id, privacy) values('"
                    + text + "',"
                    + user_id + ","
                    + circle_id + ",'"
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

        MyConnectionManager.getConnection();
        MyConnectionManager.excute(searchQuery);
        ResultSet rs = MyConnectionManager.getRs();
        while (rs.next()) {
            MessageBean ub = new MessageBean();
            ub.setPrivacy(rs.getString("privacy"));
            ub.setText(rs.getString("text"));
            ub.setMessage_id(rs.getInt("message_id"));
            ub.setCircle_id(rs.getInt("Circle_id"));
            ub.setTime(rs.getTimestamp("time"));
            ub.setUser_id(user_id);
            ub.setUser_name(UserDAO.NameById(user_id));
            a.add(ub);
        }
        
        MyConnectionManager.closeConnection();
        return a;
    }

    public static boolean visible(MessageBean mb, int user_id) throws SQLException {
        String privacy = mb.getPrivacy();
        boolean result = false;
        if (privacy.equals("public") || privacy.equals("friend")) {
            result = true;
        } else if (privacy.equals("private")) {
            result = false;
        } else {
            String searchQuery
                    = "select * from Circle_friend where circle_id="
                    + mb.getCircle_id() + " and user_id="
                    + user_id + ";";
            MyConnectionManager.getConnection();
            MyConnectionManager.excute(searchQuery);
            result = MyConnectionManager.getRs().next();
        }
        return result;
    }

    public static List<MessageBean> searchAll(int user_id) throws SQLException {
        //add user_id's messages
        List<MessageBean> a = new ArrayList<>();
        a = search(user_id);
//        System.out.println("own");
//        for(MessageBean mb:a)
//                System.out.println(mb.getText());
        //search the friendList
        ArrayList<UserBean> uba = FriendDAO.searchAllFrind(user_id);

        for (UserBean ub : uba) {
            //for each friend, search his own messageList
            ArrayList<MessageBean> mba = MessageDAO.search(ub.getUser_id());

            for (MessageBean mb : mba) {
                //for each of this message, check its visibility to the user_id
                if (visible(mb, user_id)) {

                    a.add(mb);
                }
            }
        }
//        System.out.println("own+friend");
//        for(MessageBean mb:a)
//                System.out.println(mb.getText());
        //add all users' messages with public privacy
        String addQuery = "select * from Messages where privacy='public'";
        MyConnectionManager.getConnection();
        MyConnectionManager.excute(addQuery);
        ResultSet rs = MyConnectionManager.getRs();
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
                a.add(ub);
            }
        }
//        System.out.println("all");
//        for(MessageBean mb:a)
//                System.out.println(mb.getText());
        MyConnectionManager.closeConnection();
        a.sort(new Comparator<MessageBean>() {
            @Override
            public int compare(MessageBean mb1, MessageBean mb2) {
                return -mb1.getTime().compareTo(mb2.getTime());
            }
        });

        return a;
    }

}
