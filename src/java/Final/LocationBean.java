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
    private Timestamp create_At;
    private int user_id;

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
     * @return the create_At
     */
    public Timestamp getCreate_At() {
        return create_At;
    }

    /**
     * @param create_At the create_At to set
     */
    public void setCreate_At(Timestamp create_At) {
        this.create_At = create_At;
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
}
