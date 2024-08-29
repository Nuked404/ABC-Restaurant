package com.abc.service;

import com.abc.dao.BranchDAO;
import com.abc.dao.ReservationDAO;
import com.abc.enums.ReservationStatus;
import com.abc.enums.TimeFrame;
import com.abc.model.Reservation;

import java.time.LocalDate;
import java.util.List;

public class ReservationService {
    private final ReservationDAO reservationDAO;
    private final BranchDAO branchDAO;

    // Singleton pattern implementation
    private static ReservationService instance;

    private ReservationService() {
        this.reservationDAO = new ReservationDAO();
        this.branchDAO = new BranchDAO();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            synchronized (ReservationService.class) {
                if (instance == null) {
                    instance = new ReservationService();
                }
            }
        }
        return instance;
    }

    public void addReservation(Reservation reservation) {
        reservationDAO.addReservation(reservation);
    }

    public Reservation getReservationById(int id) {
        return reservationDAO.getReservationById(id);
    }
    
    public List<Reservation> getReservationsByUserId(int userId) {
        return reservationDAO.getReservationsByUserId(userId);
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAllReservations();
    }

    public void updateReservation(Reservation reservation) {
        reservationDAO.updateReservation(reservation);
    }

    public void updateReservationStatus(int id, ReservationStatus status) {
        reservationDAO.updateReservationStatus(id, status);
    }

    public void deleteReservation(int id) {
        reservationDAO.deleteReservation(id);
    }
    
    public int getAvailableSeats(int branchId, LocalDate date, TimeFrame timeFrame) {
        int totalSeats = branchDAO.getTotalSeats(branchId);
        int totalReservedSeats = reservationDAO.getTotalReservedSeats(branchId, date, timeFrame);

        return totalSeats - totalReservedSeats;
    }
    
}
