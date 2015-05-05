/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 睿
 */
public class PhotoDAO {
    public static boolean post(String caption,int user_id,int location_id, String privacy,int circle_id) throws SQLException{
        String insertQuery;
        if(circle_id!=0 && location_id!=0)
            insertQuery="insert into Photos(user_id,location_id,circle_id,privacy,caption) values("
                    +user_id+","
                    +location_id+","
                    +circle_id+",'"
                    +privacy+"','"
                    +caption+"');";
        else if(circle_id==0 && location_id!=0)
            insertQuery="insert into Photos(user_id,location_id,privacy,caption) values("
                    +user_id+","
                    +location_id+",'"
                    +privacy+"','"
                    +caption+"');";
        else if(circle_id==0 && location_id!=0)
            insertQuery="insert into Photos(user_id,circle_id,privacy,caption) values("
                    +user_id+","
                    +circle_id+",'"
                    +privacy+"','"
                    +caption+"');";
        else
            insertQuery="insert into Photos(user_id,privacy,caption) values("
                    +user_id+",'"
                    +privacy+"','"
                    +caption+"');";
        boolean result=false;
        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
        try {
            result=cm.update(insertQuery);
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        cm.closeConnection();
        return result;
    }
    
    public static List<PhotoBean> search(int user_id) throws SQLException {
        List<PhotoBean> a = new ArrayList<>();
        String searchQuery = "select * from Photos where user_id="
                + user_id + ";";

        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
        cm.excute(searchQuery);
        ResultSet rs = cm.getRs();
        while (rs.next()) {
            PhotoBean pb = new PhotoBean();
            pb.setPhoto_id(rs.getInt("photo_id"));
            pb.setCaption(rs.getString("caption"));
            pb.setTime(rs.getTimestamp("create_at"));
            pb.setUser_id(rs.getInt("user_id"));
            pb.setCircle_id(rs.getInt("circle_id"));
            pb.setPhoto_id(rs.getInt("location_id"));
            pb.setPrivacy(rs.getString("privacy"));
            a.add(pb);
        }
        cm.closeConnection();
        return a;
    }
    
        public static boolean visible(PhotoBean pb, int user_id) throws SQLException {
        String privacy = pb.getPrivacy();
        boolean result = false;
        if (privacy.equals("public") || privacy.equals("friend")) {
            result = true;
        } else if (privacy.equals("private")) {
            result = false;
        } else if(privacy.equals("circle")){
            String searchQuery
                    = "select * from Circle_friend where circle_id="
                    + pb.getCircle_id() + " and user_id="
                    + user_id + ";";
//            MyConnectionManager.getConnection();
            System.out.println(searchQuery);
            result=MyConnectionManager.excute(searchQuery);
//            MyConnectionManager.closeConnection();
        }
        return result;
    }
    
    
    public static List<PhotoBean> searchAll(int user_id) throws SQLException {
         //add user_id's photos
        List<PhotoBean> a = new ArrayList<>();
        List<PhotoBean> b= search(user_id);
        if(b.size()!=0)a=b;
        //search the friendList
        ArrayList<UserBean> uba = FriendDAO.searchAllFrind(user_id);
        System.out.println("friend size:"+uba.size());
        MyConnectionManager.getConnection();
        for (UserBean ub : uba) {
            //for each friend, search his own photoList
            List<PhotoBean> pba = search(ub.getUser_id());
            System.out.println(user_id+":photo size of friend "+ub.getName()+":"+pba.size());
            for (PhotoBean pb : pba) {
                //for each of this photo, check its visibility to the user_id
                if (visible(pb, user_id)) {

                    a.add(pb);
                }
            }
        }
        MyConnectionManager.closeConnection();
        
        //add all users' photos with public privacy
        String addQuery = "select * from Photos where privacy='public'";
        MyConnectionManager.getConnection();
        MyConnectionManager.excute(addQuery);
        ResultSet rs = MyConnectionManager.getRs();
        HashSet<Integer> set = new HashSet();
        for (PhotoBean pb : a) {
            set.add(pb.getPhoto_id());
        }
        while (rs.next()) {
            int photoId = rs.getInt("photo_id");
            if (!set.contains(photoId)) {
                set.add(photoId);
                PhotoBean pb = new PhotoBean();
            pb.setPhoto_id(rs.getInt("photo_id"));
            pb.setCaption(rs.getString("caption"));
            pb.setTime(rs.getTimestamp("create_at"));
            pb.setUser_id(rs.getInt("user_id"));
            pb.setCircle_id(rs.getInt("circle_id"));
            pb.setPhoto_id(rs.getInt("location_id"));
            pb.setPrivacy(rs.getString("privacy"));
                a.add(pb);
            }
        }
        MyConnectionManager.closeConnection();
        a.sort(new Comparator<PhotoBean>() {
            @Override
            public int compare(PhotoBean pb1, PhotoBean pb2) {
                return -pb1.getTime().compareTo(pb2.getTime());
            }
        });

        return a;
    }
    
    
    
    
    
    
    
    
    
    
}

