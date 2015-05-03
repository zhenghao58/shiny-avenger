/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.sql.Timestamp;

/**
 *
 * @author 睿
 */
public class MessageBean {
    public int message_id;
    public String text;
    public Timestamp time;
    public int location_id;
    public int circle_id;
    public int user_id;
    public String privacy;
    public String user_name;
    /**
     * @return the privacy
     */
    public String getPrivacy() {
        return privacy;
    }

    /**
     * @param privacy the privacy to set
     */
    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
    
    
    
    
    /**
     * @return the message_id
     */
    public int getMessage_id() {
        return message_id;
    }

    /**
     * @param message_id the message_id to set
     */
    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the time
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    /**
     * @return the location_id
     */
    public int getLocation_id() {
        return location_id;
    }

    /**
     * @param location_id the location_id to set
     */
    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    /**
     * @return the cicle_id
     */
    public int getCircle_id() {
        return circle_id;
    }

    /**
     * @param cicle_id the cicle_id to set
     */
    public void setCircle_id(int cicle_id) {
        this.circle_id = cicle_id;
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
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * @return the privacy
     */

    
}
