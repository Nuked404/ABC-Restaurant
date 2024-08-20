package com.abc.model;

import com.abc.enums.ReservationStatus;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private int userId;
    private int branchId;
    private ReservationStatus status;
    private LocalDateTime reservationDate;
    private String notes;

    // Constructor
    public Reservation(int id, int userId, int branchId, ReservationStatus status, LocalDateTime reservationDate, String notes) {
        this.id = id;
        this.userId = userId;
        this.branchId = branchId;
        this.status = status;
        this.reservationDate = reservationDate;
        this.notes = notes;
    }

    // Getters and Setters
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

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
