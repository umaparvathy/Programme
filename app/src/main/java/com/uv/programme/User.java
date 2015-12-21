package com.uv.programme;

/**
 * Created by venkatsr on 16/12/15.
 */
public class User {

    private int userId;

    private String name;

    private String place;

    public User() {
    }

    public User(int userId, String name, String place) {
        this.userId = userId;
        this.name = name;
        this.place = place;
    }

    public User(String name, String place) {
        this.name = name;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
