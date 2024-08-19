package com.abc.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int nearestLocation; // foreign key from Branch
    private String role;

    public User() {}

    public User(int id, String name, String email, String phone, int nearestLocation, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.nearestLocation = nearestLocation;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNearestLocation() {
        return nearestLocation;
    }

    public void setNearestLocation(int nearestLocation) {
        this.nearestLocation = nearestLocation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
