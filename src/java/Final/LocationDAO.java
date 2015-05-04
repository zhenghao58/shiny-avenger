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
public class LocationDAO {
    public static boolean post(int location_id,int user_id,String privacy,int circle_id){
        String insertQuery;
        if(circle_id==0)
            insertQuery="insert into Current_Location(location_id,user_id,privacy) values("
                    +location_id+","
                    +user_id+",'"
                    +privacy+"'";
        else
            insertQuery="insert into Current_Location(location_id,user_id,privacy,circle_id) values("
                    +location_id+","
                    +user_id+",'"
                    +privacy+"',"
                    +circle_id+");";
        boolean result=false;
        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
        try {
            result=cm.update(insertQuery);
        } catch (SQLException ex) {
            Logger.getLogger(LocationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        cm.closeConnection();
        return result;
    }
    
    public static List<LocationBean> search(int user_id) throws SQLException {
        List<LocationBean> a = new ArrayList<>();
        String searchQuery = "select * from Current_Location cl natural join Locations l where user_id="
                + user_id + ";";

        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
        cm.excute(searchQuery);
        ResultSet rs = cm.getRs();
        while (rs.next()) {
            LocationBean lb = new LocationBean();
            lb.setLocation_id(rs.getInt("location_id"));
            lb.setTime(rs.getTimestamp("create_at"));
            lb.setCity_name(rs.getString("city_name"));
            lb.setAttraction(rs.getString("attraction"));
            lb.setLatitude(rs.getFloat("latitude"));
            lb.setLongtitude(rs.getFloat("longtitude"));
            lb.setUser_id(rs.getInt("user_id"));
            lb.setCircle_id(rs.getInt("circle_id"));
            lb.setPrivacy(rs.getString("privacy"));
            a.add(lb);
        }
        cm.closeConnection();
        return a;
    }
    
        public static boolean visible(LocationBean lb, int user_id) throws SQLException {
        String privacy = lb.getPrivacy();
        boolean result = false;
        if (privacy.equals("public") || privacy.equals("friend")) {
            result = true;
        } else if (privacy.equals("private")) {
            result = false;
        } else if(privacy.equals("circle")){
            String searchQuery
                    = "select * from Circle_friend where circle_id="
                    + lb.getCircle_id() + " and user_id="
                    + user_id + ";";
//            MyConnectionManager.getConnection();
            System.out.println(searchQuery);
            result=MyConnectionManager.excute(searchQuery);
//            MyConnectionManager.closeConnection();
        }
        return result;
    }
    
    
    public static List<LocationBean> searchAll(int user_id) throws SQLException {
         //add user_id's locations
        List<LocationBean> a = new ArrayList<>();
        List<LocationBean> b= search(user_id);
        if(b.size()!=0)a=b;
        //search the friendList
        ArrayList<UserBean> uba = FriendDAO.searchAllFrind(user_id);
        System.out.println("friend size:"+uba.size());
        MyConnectionManager.getConnection();
        for (UserBean ub : uba) {
            //for each friend, search his own locationList
            List<LocationBean> lba = search(ub.getUser_id());
            System.out.println(user_id+":location size of friend "+ub.getName()+":"+lba.size());
            for (LocationBean lb : lba) {
                //for each of this location, check its visibility to the user_id
                if (visible(lb, user_id)) {

                    a.add(lb);
                }
            }
        }
        MyConnectionManager.closeConnection();
        
        //add all users' locations with public privacy
        String addQuery = "select * from Current_Location where privacy='public'";
        MyConnectionManager.getConnection();
        MyConnectionManager.excute(addQuery);
        ResultSet rs = MyConnectionManager.getRs();
        HashSet<Integer> set = new HashSet();
        for (LocationBean lb : a) {
            set.add(lb.getLocation_id());
        }
        while (rs.next()) {
            int locationId = rs.getInt("location_id");
            if (!set.contains(locationId)) {
                set.add(locationId);
                LocationBean lb = new LocationBean();
                lb.setLocation_id(rs.getInt("location_id"));
                lb.setTime(rs.getTimestamp("create_at"));
                lb.setCity_name(rs.getString("city_name"));
                lb.setAttraction(rs.getString("attraction"));
                lb.setLatitude(rs.getFloat("latitude"));
                lb.setLongtitude(rs.getFloat("longtitude"));
                lb.setUser_id(rs.getInt("user_id"));
                lb.setCircle_id(rs.getInt("circle_id"));
                lb.setPrivacy(rs.getString("privacy"));
                a.add(lb);
            }
        }
        MyConnectionManager.closeConnection();
        a.sort(new Comparator<LocationBean>() {
            @Override
            public int compare(LocationBean lb1, LocationBean lb2) {
                return -lb1.getTime().compareTo(lb2.getTime());
            }
        });

        return a;
    }
    
    
    
    
    
    
    
    
    
    
}
