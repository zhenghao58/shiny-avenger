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
public class LocationBean {
    private int location_id;
    private float longtitude;
    private float latitude;
    private String city_name;
    private String attraction;
    private Timestamp time;
    private int user_id;
    private String name;
    private int circle_id;
    private String privacy;
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
     * @return the longtitude
     */
    public float getLongtitude() {
        return longtitude;
    }

    /**
     * @param longtitude the longtitude to set
     */
    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }

    /**
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the city_name
     */
    public String getCity_name() {
        return city_name;
    }

    /**
     * @param city_name the city_name to set
     */
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    /**
     * @return the attraction
     */
    public String getAttraction() {
        return attraction;
    }

    /**
     * @param attraction the attraction to set
     */
    public void setAttraction(String attraction) {
        this.attraction = attraction;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
