package com.abc.model;

import com.abc.enums.UserRole;

public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String addressLine1; // New field for address line 1
    private String addressLine2; // New field for address line 2
    private String city; // New field for city
    private int nearestLocation; // foreign key from Branch
    private UserRole role;
    private String password;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(int id, String name, String email, String phone, String addressLine1, String addressLine2, String city, int nearestLocation, UserRole role, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.nearestLocation = nearestLocation;
        this.role = role;
        this.password = password;
    }
    
    public User(String name, String email, String phone, String addressLine1, String addressLine2, String city, int nearestLocation, UserRole role, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.nearestLocation = nearestLocation;
        this.role = role;
        this.password = password;
    }

    // Getters and Setters for all fields

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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNearestLocation() {
        return nearestLocation;
    }

    public void setNearestLocation(int nearestLocation) {
        this.nearestLocation = nearestLocation;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}