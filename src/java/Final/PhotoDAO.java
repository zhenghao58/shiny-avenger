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

    public static int post(String caption, int user_id, int location_id, String privacy, int circle_id) throws SQLException {
        String insertQuery;
        if (circle_id != 0 && location_id != 0) {
            insertQuery = "insert into Photos(user_id,location_id,circle_id,privacy,caption) values("
                    + user_id + ","
                    + location_id + ","
                    + circle_id + ",'"
                    + privacy + "','"
                    + caption + "');";
        } else if (circle_id == 0 && location_id != 0) {
            insertQuery = "insert into Photos(user_id,location_id,privacy,caption) values("
                    + user_id + ","
                    + location_id + ",'"
                    + privacy + "','"
                    + caption + "');";
        } else if (circle_id != 0 && location_id == 0) {
            insertQuery = "insert into Photos(user_id,circle_id,privacy,caption) values("
                    + user_id + ","
                    + circle_id + ",'"
                    + privacy + "','"
                    + caption + "');";
        } else {
            insertQuery = "insert into Photos(user_id,privacy,caption) values("
                    + user_id + ",'"
                    + privacy + "','"
                    + caption + "');";
        }
        int result = 0;
        ConnectionManager cm = new ConnectionManager();
        cm.getConnection();
        try {
            cm.update(insertQuery);
        } catch (SQLException ex) {
            Logger.getLogger(PhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        String searchQuery
                = "select max(photo_id) photo_id from Photos;";
        cm.excute(searchQuery);
        ResultSet rs = cm.getRs();
        rs.next();
        result = rs.getInt("photo_id");
        cm.closeConnection();
        return result;
    }

    private static List<PhotoBean> search(int user_id) throws SQLException {
        List<PhotoBean> a = new ArrayList<>();
        String searchQuery = "select * from Photos where user_id="
                + user_id + ";";

        ConnectionManager cm = new ConnectionManager();
        cm.getConnection();
        cm.excute(searchQuery);
        ResultSet rs = cm.getRs();
        while (rs.next()) {
            PhotoBean pb = new PhotoBean();
            pb.setPhoto_id(rs.getInt("photo_id"));
            pb.setCaption(rs.getString("caption"));
            pb.setTime(rs.getTimestamp("create_at"));
            pb.setUser_id(user_id);
            pb.setUser_name(UserDAO.NameById(user_id));
            pb.setCircle_id(rs.getInt("circle_id"));
            pb.setLocation_id(rs.getInt("location_id"));
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
        } else if (privacy.equals("circle")) {
            String searchQuery
                    = "select * from Circle_friend where circle_id="
                    + pb.getCircle_id() + " and user_id="
                    + user_id + ";";
            if (MyConnectionManager.getCon() == null) {
                MyConnectionManager.getConnection();
            }
            System.out.println(searchQuery);
            result = MyConnectionManager.excute(searchQuery);
//            MyConnectionManager.closeConnection();
        }
        return result;
    }

    public static List<PhotoBean> searchAll(int user_id) throws SQLException {
        //add user_id's photos
        List<PhotoBean> a = new ArrayList<>();
        List<PhotoBean> b = search(user_id);
        if (!b.isEmpty()) a = b;
        

        
        //search the friendList
        ArrayList<UserBean> uba = FriendDAO.searchAllFrind(user_id);
        System.out.println("friend size:" + uba.size());
        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
//        for (UserBean ub : uba) {
//            //for each friend, search his own photoList
//            List<PhotoBean> pba = search(ub.getUser_id());
//            System.out.println(user_id + ":photo size of friend " + ub.getName() + ":" + pba.size());
//            for (PhotoBean pb : pba) {
//                //for each of this photo, check its visibility to the user_id
//                if (visible(pb, user_id)) {
//
//                    a.add(pb);
//                }
//            }
//        }
        String searchQuery=
                "select * from Photos m where m.user_id in (select friend_user_id from Friend  f where f.user_id="
                +user_id+") "
                + "and m.circle_id in(select circle_id from Circle_friend cf where cf.user_id="
                +user_id+"  and privacy='circle');";
        cm.excute(searchQuery);
        ResultSet rs = cm.getRs();
        while (rs.next()) {
                if(rs==null) break;
                    PhotoBean pb = new PhotoBean();
                    pb.setPhoto_id(rs.getInt("photo_id"));
                    pb.setCaption(rs.getString("caption"));
                    pb.setTime(rs.getTimestamp("create_at"));
                    int uid = rs.getInt("user_id");
                    pb.setUser_id(uid);
                    pb.setUser_name(UserDAO.NameById(uid));
                    pb.setCircle_id(rs.getInt("circle_id"));
                    pb.setLocation_id(rs.getInt("location_id"));
                    pb.setPrivacy(rs.getString("privacy"));
                    a.add(pb);
            }
        String searchQuery1=
                "select * from Photos m where m.user_id in (select friend_user_id from Friend  f where f.user_id="
                +user_id+") "
                + "and privacy='friend';";
        cm.excute(searchQuery1);
        rs = cm.getRs();
        while (rs.next()) {
                if(rs==null) break;
                    PhotoBean pb = new PhotoBean();
                    pb.setPhoto_id(rs.getInt("photo_id"));
                    pb.setCaption(rs.getString("caption"));
                    pb.setTime(rs.getTimestamp("create_at"));
                    int uid = rs.getInt("user_id");
                    pb.setUser_id(uid);
                    pb.setUser_name(UserDAO.NameById(uid));
                    pb.setCircle_id(rs.getInt("circle_id"));
                    pb.setLocation_id(rs.getInt("location_id"));
                    pb.setPrivacy(rs.getString("privacy"));
                    a.add(pb);
            }
        HashSet<Integer> set = new HashSet();
        for (PhotoBean pb : a) {
            set.add(pb.getPhoto_id());
        }
        //add all users' photos with public privacy
        String addQuery = "select * from Photos where privacy='public'";
        cm.excute(addQuery);
        rs = cm.getRs();
        if(rs==null) System.out.println("rs is null in PhotoDao search all");
        else {
            while (rs.next()) {
                if(rs==null) break;
                int photoId = rs.getInt("photo_id");
                if (!set.contains(photoId)) {
                    set.add(photoId);
                    PhotoBean pb = new PhotoBean();
                    pb.setPhoto_id(rs.getInt("photo_id"));
                    pb.setCaption(rs.getString("caption"));
                    pb.setTime(rs.getTimestamp("create_at"));
                    int uid = rs.getInt("user_id");
                    pb.setUser_id(uid);
                    pb.setUser_name(UserDAO.NameById(uid));
                    pb.setCircle_id(rs.getInt("circle_id"));
                    pb.setLocation_id(rs.getInt("location_id"));
                    pb.setPrivacy(rs.getString("privacy"));
                    a.add(pb);
                }
            }
        }
        cm.closeConnection();
        a.sort(new Comparator<PhotoBean>() {
            @Override
            public int compare(PhotoBean pb1, PhotoBean pb2) {
                return -pb1.getTime().compareTo(pb2.getTime());
            }
        });

        return a;
    }

    public static List<PhotoBean> search(int user_id,String str) throws SQLException {
        List<PhotoBean> l=searchAll(user_id);
        List<PhotoBean> l1=new ArrayList<>();
        for(PhotoBean pb:l)
            if(pb.getCaption().matches("(.*)"+str+"(.*)"))
                l1.add(pb);
        return l1;
    }

}
