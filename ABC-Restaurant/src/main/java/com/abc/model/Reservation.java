package com.abc.model;

import java.util.Date;

public class Reservation {
    private int id;
    private int userId; // foreign key from User
    private int branchId; // foreign key from Branch
    private Date reservationDate;
    private int numOfPeople;
    private String status;

    public Reservation() {}

    public Reservation(int id, int userId, int branchId, Date reservationDate, int numOfPeople, String status) {
        this.id = id;
        this.userId = userId;
        this.branchId = branchId;
        this.reservationDate = reservationDate;
        this.numOfPeople = numOfPeople;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
