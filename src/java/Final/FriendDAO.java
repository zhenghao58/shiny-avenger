/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 *
 * @author 睿
 */
public class FriendDAO {
    static Connection currentCon = null;
    static ResultSet rs = null;
    public static boolean request(FriendBean bean) {
        //preparing some objects for connection 
        Statement stmt = null;

        int friend_id = bean.getFriend_id();
        int user_id = bean.getUser_id();
        int friend_user_id = bean.getFriend_user_id();
        bean.setAccept(false);
        boolean result=true;
        String searchQuery
                = "insert into Friend(user_id,friend_user_id) values('"
                + user_id+"','"
                +friend_user_id+"');";
        try {
            //connect to DB 
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            int affectedRow = stmt.executeUpdate(searchQuery);
            

            // if network error makes sql excution fail
            if (affectedRow!=1) result=false;
        } catch (Exception ex) {
            System.out.println("Log In failed: An Exception has occurred! " + ex);
        } //some exception handling
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                stmt = null;
            }

            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                }

                currentCon = null;
            }
        }
        return result;
    }
    
    public static boolean respond(FriendBean bean,boolean accept) {
        //preparing some objects for connection 
        Statement stmt = null;
        int friend_id=bean.getFriend_id();
        String acceptQuery
                = "update Friend set accept=true where friend_id='"
                    +friend_id+"';";
        String refuseQuery
                = "delete from Friend where friend_id='"
                    +friend_id+"';";
        boolean result=false;
            try {
            //connect to DB 
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            int affectedRow = 0;
            if(accept) affectedRow=stmt.executeUpdate(acceptQuery);
            else affectedRow=stmt.executeUpdate(refuseQuery);
            
            // if user does not exist set the isValid variable to false
            if (affectedRow==1)result=true;
        } catch (Exception ex) {
            System.out.println("Log In failed: An Exception has occurred! " + ex);
        } //some exception handling
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                stmt = null;
            }

            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                }

                currentCon = null;
            }
        }

        return result;

    }
    
    public static ArrayList<UserBean> searchAllFrind(int user_id){
        //preparing some objects for connection 
        Statement stmt = null;
        String searchQuery
                = "select * from Users where user_id in(select friend_user_id from Friend where user_id='"
                + user_id+"');";
        ArrayList<UserBean> a=new ArrayList<UserBean>();
        try {
            //connect to DB 
            currentCon = ConnectionManager.getConnection();
            stmt = currentCon.createStatement();
            rs = stmt.executeQuery(searchQuery);
            while(rs.next()){
                UserBean ub=new UserBean();
                ub.setName(rs.getString("name"));
                ub.setUserName(rs.getString("username"));
                ub.setUser_id(rs.getInt("user_id"));
                a.add(ub);
            }
        } catch (Exception ex) {
            System.out.println("Log In failed: An Exception has occurred! " + ex);
        } //some exception handling
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                stmt = null;
            }

            if (currentCon != null) {
                try {
                    currentCon.close();
                } catch (Exception e) {
                }

                currentCon = null;
            }
        }
        return a;
    }
    
}
