package com.abc.model;

import com.abc.enums.ReservationStatus;
import com.abc.enums.TimeFrame;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int userId;
    private int branchId;
    private ReservationStatus status;
    private LocalDate reservationDate; // Store only the date
    private TimeFrame timeFrame; // Enum for time frames
    private int seatCount; // Number of seats reserved
    private String notes;

    // Constructor
    public Reservation(int id, int userId, int branchId, ReservationStatus status, LocalDate reservationDate, TimeFrame timeFrame, int seatCount, String notes) {
        this.id = id;
        this.userId = userId;
        this.branchId = branchId;
        this.status = status;
        this.reservationDate = reservationDate;
        this.timeFrame = timeFrame;
        this.seatCount = seatCount;
        this.notes = notes;
    }



	public Reservation(int id, int userId, int branchId, LocalDate reservationDate, TimeFrame timeFrame, int seatCount,
			String notes) {
		super();
		this.id = id;
		this.userId = userId;
		this.branchId = branchId;
		this.reservationDate = reservationDate;
		this.timeFrame = timeFrame;
		this.seatCount = seatCount;
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

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
