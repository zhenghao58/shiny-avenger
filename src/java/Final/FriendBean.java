/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.Timestamp;

public class FriendBean implements java.io.Serializable{
    private int friend_id;
    private int user_id;
    private int friend_user_id;
    private boolean accept;
    private Timestamp request_at;

    /**
     * @return the friend_id
     */
    public int getFriend_id() {
        return friend_id;
    }

    /**
     * @param friend_id the friend_id to set
     */
    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the friend_user_id
     */
    public int getFriend_user_id() {
        return friend_user_id;
    }

    /**
     * @param friend_user_id the friend_user_id to set
     */
    public void setFriend_user_id(int friend_user_id) {
        this.friend_user_id = friend_user_id;
    }

    /**
     * @return the accept
     */
    public boolean isAccept() {
        return accept;
    }

    /**
     * @param accept the accept to set
     */
    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    /**
     * @return the request_at
     */
    public Timestamp getRequest_at() {
        return request_at;
    }

    /**
     * @param request_at the request_at to set
     */
    public void setRequest_at(Timestamp request_at) {
        this.request_at = request_at;
    }
    
}
