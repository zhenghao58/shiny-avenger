/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.SQLException;
import java.util.ArrayList;
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
            insertQuery="insert into Location(location_id,user_id,privacy) values("
                    +location_id+","
                    +user_id+",'"
                    +privacy+"'";
        else
            insertQuery="insert into Location(location_id,user_id,privacy,circle_id) values("
                    +location_id+","
                    +user_id+",'"
                    +privacy+"',"
                    +circle_id+");";
        boolean result=false;
        MyConnectionManager.getConnection();
        try {
            result=MyConnectionManager.excute(insertQuery);
        } catch (SQLException ex) {
            Logger.getLogger(LocationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyConnectionManager.closeConnection();
        return result;
    }
    
    public static List<LocationBean> getAll() throws SQLException{
        List<LocationBean> l=new ArrayList<>();
        String searchQuery=
                "select * from Location;";
        MyConnectionManager.getConnection();
        MyConnectionManager.excute(searchQuery);
        MyConnectionManager.closeConnection();
        return l;
    }
    
    
    
    
    
    
    
    
    
    
}
