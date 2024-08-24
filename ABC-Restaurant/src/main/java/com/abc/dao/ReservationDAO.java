package com.abc.dao;

import com.abc.enums.ReservationStatus;
import com.abc.enums.TimeFrame;
import com.abc.model.Reservation;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // Add a reservation to the database
    public void addReservation(Reservation reservation) {
        String query = "INSERT INTO reservation (user_id, branch_id, status, reservation_date, time_frame, seat_count, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, reservation.getUserId());
            statement.setInt(2, reservation.getBranchId());
            statement.setString(3, reservation.getStatus().name()); // Convert enum to String
            statement.setDate(4, Date.valueOf(reservation.getReservationDate())); // Store only the date
            statement.setString(5, reservation.getTimeFrame().name()); // Convert enum to String
            statement.setInt(6, reservation.getSeatCount());
            statement.setString(7, reservation.getNotes());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a reservation by its ID
    public Reservation getReservationById(int id) {
        Reservation reservation = null;
        String query = "SELECT * FROM reservation WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reservation = new Reservation(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("branch_id"),
                    ReservationStatus.valueOf(resultSet.getString("status")), // Convert String to enum
                    resultSet.getDate("reservation_date").toLocalDate(), // Convert SQL date to LocalDate
                    TimeFrame.valueOf(resultSet.getString("time_frame")), // Convert String to enum
                    resultSet.getInt("seat_count"),
                    resultSet.getString("notes")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservation;
    }

    // Retrieve all reservations from the database
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation";

        try (Connection connection = DBConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                reservations.add(new Reservation(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("branch_id"),
                    ReservationStatus.valueOf(resultSet.getString("status")), // Convert String to enum
                    resultSet.getDate("reservation_date").toLocalDate(), // Convert SQL date to LocalDate
                    TimeFrame.valueOf(resultSet.getString("time_frame")), // Convert String to enum
                    resultSet.getInt("seat_count"),
                    resultSet.getString("notes")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    // Update an existing reservation in the database
    public void updateReservation(Reservation reservation) {
        String query = "UPDATE reservation SET user_id = ?, branch_id = ?, status = ?, reservation_date = ?, time_frame = ?, seat_count = ?, notes = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, reservation.getUserId());
            statement.setInt(2, reservation.getBranchId());
            statement.setString(3, reservation.getStatus().name()); // Convert enum to String
            statement.setDate(4, Date.valueOf(reservation.getReservationDate())); // Store only the date
            statement.setString(5, reservation.getTimeFrame().name()); // Convert enum to String
            statement.setInt(6, reservation.getSeatCount());
            statement.setString(7, reservation.getNotes());
            statement.setInt(8, reservation.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cancel a reservation by updating its status
    public void changeReservationStatus(int id, ReservationStatus status) {
        String query = "UPDATE reservation SET status = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, status.name()); // Convert enum to String
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
