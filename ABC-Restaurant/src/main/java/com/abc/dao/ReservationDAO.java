package com.abc.dao;

import com.abc.enums.ReservationStatus;
import com.abc.model.Reservation;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // Add a reservation to the database
    public void addReservation(Reservation reservation) {
        String query = "INSERT INTO reservation (user_id, branch_id, status, reservation_date, notes) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, reservation.getUserId());
            statement.setInt(2, reservation.getBranchId());
            statement.setString(3, reservation.getStatus().name()); // Convert enum to String
            statement.setTimestamp(4, Timestamp.valueOf(reservation.getReservationDate()));
            statement.setString(5, reservation.getNotes());

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
                    resultSet.getTimestamp("reservation_date").toLocalDateTime(),
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
                    resultSet.getTimestamp("reservation_date").toLocalDateTime(),
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
        String query = "UPDATE reservation SET user_id = ?, branch_id = ?, status = ?, reservation_date = ?, notes = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, reservation.getUserId());
            statement.setInt(2, reservation.getBranchId());
            statement.setString(3, reservation.getStatus().name()); // Convert enum to String
            statement.setTimestamp(4, Timestamp.valueOf(reservation.getReservationDate()));
            statement.setString(5, reservation.getNotes());
            statement.setInt(6, reservation.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cancel a reservation by updating its status
    public void cancelReservation(int id) {
        String query = "UPDATE reservation SET status = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, ReservationStatus.CANCELED.name()); // Convert enum to String
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
