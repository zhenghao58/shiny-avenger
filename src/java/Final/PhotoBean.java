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
public class PhotoBean {
    private int photo_id;
    private Timestamp time;
    private String caption;
    private int location_id;
    private int circle_id;
    private int user_id;
    private String privacy;
    private String user_name;
    
  

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
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
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
     * @return the circle_id
     */
    public int getCircle_id() {
        return circle_id;
    }

    /**
     * @param circle_id the circle_id to set
     */
    public void setCircle_id(int circle_id) {
        this.circle_id = circle_id;
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
     * @return the photo_id
     */
    public int getPhoto_id() {
        return photo_id;
    }

    /**
     * @param photo_id the photo_id to set
     */
    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
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
}
