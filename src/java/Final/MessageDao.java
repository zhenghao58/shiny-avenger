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

    //static ResultSet rs = null;

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
        ArrayList<MessageBean> a = new ArrayList<>();
        String searchQuery = "select * from Messages where user_id='"
                + user_id + "';";

        MyConnectionManager.getConnection();
        MyConnectionManager.excute(searchQuery);
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
    
    public static boolean visible(MessageBean mb,int user_id) throws SQLException{
        String privacy=mb.getPrivacy();
        boolean result=false;
        if(privacy=="PUBLIC" || privacy=="FRIEND")
            result=true;
        else if(privacy=="PRIVATE")
            result=false;
        else {
            String saerchQuery=
                    "select * from Circle_friend where circle_id='"
                    +mb.getCicle_id()+"' and user_id='"
                    +user_id+"';";
            MyConnectionManager.getConnection();
            MyConnectionManager.excute(saerchQuery);
            result=MyConnectionManager.getRs().next();
        }
        return result;
    }
    
    
    public static ArrayList<MessageBean> searchAll(int user_id) throws SQLException {
        ArrayList<MessageBean> a = new ArrayList<MessageBean>();
        
        //search the friendList
        ArrayList<UserBean> uba = FriendDAO.searchAllFrind(user_id);
        for(UserBean ub:uba){
            //for each friend, search his own messageList
            ArrayList<MessageBean>mba=MessageDao.search(ub.getUser_id());
            for(MessageBean mb:mba){
                //for each of this message, check its visibility to the user_id
                if(visible(mb,user_id))a.add(mb);
            }
        }
        return a;
    }

}
