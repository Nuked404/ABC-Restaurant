package com.abc.model;

public class Branch {
    private int id;
    private String location;
    private int maxSeats;  // New field for maximum seats

    public Branch() {}

    public Branch(int id, String location, int maxSeats) {
        this.id = id;
        this.location = location;
        this.maxSeats = maxSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }
}
