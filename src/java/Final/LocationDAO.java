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
    public static boolean post(int location_id,int user_id,String privacy,int circle_id) throws SQLException{
        String insertQuery;
        if(circle_id==0)
            insertQuery="insert into Current_Location(location_id,user_id,privacy) values("
                    +location_id+","
                    +user_id+",'"
                    +privacy+"');";
        else
            insertQuery="insert into Current_Location(location_id,user_id,privacy,circle_id) values("
                    +location_id+","
                    +user_id+",'"
                    +privacy+"',"
                    +circle_id+");";
        boolean result=false;
        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
        System.out.println(insertQuery);
        try {
            result=cm.update(insertQuery);
        } catch (SQLException ex) {
            Logger.getLogger(LocationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        cm.closeConnection();
        return result;
    }
    
    public static boolean isInDistance(float originLatitude,float originLongtitude,float desLatitude,float desLongtitude,float distance){
        double r=6371000;
        double a=Math.toRadians(originLatitude);
        double b=Math.toRadians(desLatitude);
        double c=Math.toRadians(desLatitude-originLatitude);
        double d=Math.toRadians(desLongtitude-originLongtitude);
        double e=Math.sin(c/2) * Math.sin(c/2) +
        Math.cos(a) * Math.cos(b) *
        Math.sin(d/2) * Math.sin(d/2);
        return r*e<distance;
    }
    
    
    public static List<LocationBean> search(int user_id,float distance) throws SQLException{
        List<LocationBean>l=new ArrayList<>();
        LocationBean lb=new LocationBean();
        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
        String searchQuery1= 
                "select * from Current_Location natural join Locations where user_id ="
                +user_id+" order by create_at  desc limit 1;";
        cm.excute(searchQuery1);
        ResultSet rs=cm.getRs();
        rs.next();
        if(rs==null)return l;
        float latitude=rs.getFloat("latitude");
        float longtitude=rs.getFloat("longtitude");
        
        String searchQuery2= 
                "select * from Locations where location_id !="
                +lb.getLocation_id()+";";
        cm.excute(searchQuery2);
        rs=cm.getRs();
        if(rs!=null)
            while(rs.next()){
                float DesLongtitude=rs.getFloat("longtitude");
                float DesLatitude=rs.getFloat("latitude");
                if(isInDistance(latitude,longtitude,DesLatitude,DesLongtitude,distance)){
                    //search all users who are in the lb
                    int location_id=rs.getInt("location_id");
                    String searhQuery3=
                            "select * from Current_Location natural join Locations where location_id="
                            +location_id+"; ";
                    ConnectionManager cm1=new ConnectionManager();
                    cm1.getConnection();
                    cm1.excute(searhQuery3);
                    ResultSet rs1=cm1.getRs();
                    while(rs1.next()){
                        LocationBean lb1 = new LocationBean();
                        int uid = rs1.getInt("user_id");
                        lb1.setLocation_id(rs1.getInt("location_id"));
                        lb1.setTime(rs1.getTimestamp("create_at"));
                        lb1.setCity_name(rs1.getString("city_name"));
                        lb1.setAttraction(rs1.getString("attraction"));
                        lb1.setLatitude(rs1.getFloat("latitude"));
                        lb1.setLongtitude(rs1.getFloat("longtitude"));
                        lb1.setUser_id(uid);
                        lb1.setCircle_id(rs1.getInt("circle_id"));
                        lb1.setPrivacy(rs1.getString("privacy"));
                        lb1.setName(UserDAO.NameById(uid));
                        l.add(lb1);
                    }
                    cm1.closeConnection();
                }
            }
        
        cm.closeConnection();
        return l;
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
            lb.setUser_id(user_id);
            lb.setName(UserDAO.NameById(user_id));
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
        } else if (privacy.equals("circle")) {
            String searchQuery
                    = "select * from Circle_friend where circle_id="
                    + lb.getCircle_id() + " and user_id="
                    + user_id + ";";
//            MyConnectionManager.getConnection();
            System.out.println(searchQuery);
            if (MyConnectionManager.getCon() == null) {
                MyConnectionManager.getConnection();
            }
            result = MyConnectionManager.excute(searchQuery);
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
        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
//        for (UserBean ub : uba) {
//            //for each friend, search his own locationList
//            List<LocationBean> lba = search(ub.getUser_id());
//            System.out.println(user_id+":location size of friend "+ub.getName()+":"+lba.size());
//            for (LocationBean lb : lba) {
//                //for each of this location, check its visibility to the user_id
//                if (visible(lb, user_id)) {
//
//                    a.add(lb);
//                }
//            }
//        }
        
        String searchQuery=
                "select * from Current_Location m natural join Locations where m.user_id in (select friend_user_id from Friend  f where f.user_id="
                +user_id+") "
                + "and m.circle_id in(select circle_id from Circle_friend cf where cf.user_id="
                +user_id+") and privacy='circle';";
        cm.excute(searchQuery);
        ResultSet rs = cm.getRs();
        while (rs.next()) {
                int uid = rs.getInt("user_id");
                LocationBean lb = new LocationBean();
                lb.setLocation_id(rs.getInt("location_id"));
                lb.setTime(rs.getTimestamp("create_at"));
                lb.setCity_name(rs.getString("city_name"));
                lb.setAttraction(rs.getString("attraction"));
                lb.setLatitude(rs.getFloat("latitude"));
                lb.setLongtitude(rs.getFloat("longtitude"));
                lb.setUser_id(uid);
                lb.setName(UserDAO.NameById(uid));
                lb.setCircle_id(rs.getInt("circle_id"));
                lb.setPrivacy(rs.getString("privacy"));
                a.add(lb);
        }
        
        String searchQuery1=
                "select * from Current_Location m natural join Locations where m.user_id in (select friend_user_id from Friend  f where f.user_id="
                +user_id+") "
                + "and privacy='friend';";
        cm.excute(searchQuery1);
        rs = cm.getRs();
        while (rs.next()) {
                LocationBean lb = new LocationBean();
                int uid = rs.getInt("user_id");
                lb.setLocation_id(rs.getInt("location_id"));
                lb.setTime(rs.getTimestamp("create_at"));
                lb.setCity_name(rs.getString("city_name"));
                lb.setAttraction(rs.getString("attraction"));
                lb.setLatitude(rs.getFloat("latitude"));
                lb.setLongtitude(rs.getFloat("longtitude"));
                lb.setUser_id(uid);
                lb.setName(UserDAO.NameById(uid));
                lb.setCircle_id(rs.getInt("circle_id"));
                lb.setPrivacy(rs.getString("privacy"));
                a.add(lb);
        }
        
        //add all users' locations with public privacy
        String addQuery = "select * from Current_Location natural join Locations where privacy='public'";
        cm.getConnection();
        cm.excute(addQuery);
        rs = cm.getRs();
        HashSet<Integer> set = new HashSet();
        for (LocationBean lb : a) {
            set.add(lb.getLocation_id());
        }
        while (rs.next()) {
            int locationId = rs.getInt("location_id");
            if (!set.contains(locationId)) {
                set.add(locationId);
                int uid = rs.getInt("user_id");
                LocationBean lb = new LocationBean();
                lb.setLocation_id(rs.getInt("location_id"));
                lb.setTime(rs.getTimestamp("create_at"));
                lb.setCity_name(rs.getString("city_name"));
                lb.setAttraction(rs.getString("attraction"));
                lb.setLatitude(rs.getFloat("latitude"));
                lb.setLongtitude(rs.getFloat("longtitude"));
                lb.setUser_id(uid);
                lb.setName(UserDAO.NameById(uid));
                lb.setCircle_id(rs.getInt("circle_id"));
                lb.setPrivacy(rs.getString("privacy"));
                a.add(lb);
            }
        }
        cm.closeConnection();
        a.sort(new Comparator<LocationBean>() {
            @Override
            public int compare(LocationBean lb1, LocationBean lb2) {
                return -lb1.getTime().compareTo(lb2.getTime());
            }
        });

        return a;
    }
    
    public static List<LocationBean> getAll() throws SQLException{
        List<LocationBean>l=new ArrayList<>();
        ConnectionManager cm=new ConnectionManager();
        cm.getConnection();
        String searchQuery=
                "select * from Locations;";
        cm.excute(searchQuery);
        ResultSet rs= cm.getRs();
        while(rs.next()){
            LocationBean lb = new LocationBean();
                lb.setLocation_id(rs.getInt("location_id"));
                lb.setCity_name(rs.getString("city_name"));
                lb.setAttraction(rs.getString("attraction"));
                lb.setLatitude(rs.getFloat("latitude"));
                lb.setLongtitude(rs.getFloat("longtitude"));
                l.add(lb);
        }
        cm.closeConnection();
        return l;
    }
    
    
    
    
    
    
    
    
}
