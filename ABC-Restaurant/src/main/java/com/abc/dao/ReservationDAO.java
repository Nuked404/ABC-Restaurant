package com.abc.dao;

import com.abc.enums.ReservationStatus;
import com.abc.enums.TimeFrame;
import com.abc.model.Reservation;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
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
			statement.setString(3, ReservationStatus.PENDING.name());
			statement.setDate(4, Date.valueOf(reservation.getReservationDate()));
			statement.setString(5, reservation.getTimeFrame().name());
			statement.setInt(6, reservation.getSeatCount());
			statement.setString(7, reservation.getNotes());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace(); // Consider using a logging framework
		}
	}

	// Retrieve a reservation by its ID
	public Reservation getReservationById(int id) {
		String query = "SELECT * FROM reservation WHERE id = ?";

		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return mapResultSetToReservation(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Retrieve reservations by user ID
    public List<Reservation> getReservationsByUserId(int userId) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation WHERE user_id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                reservations.add(new Reservation(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("branch_id"),
                    ReservationStatus.valueOf(resultSet.getString("status")),
                    resultSet.getDate("reservation_date").toLocalDate(),
                    TimeFrame.valueOf(resultSet.getString("time_frame")),
                    resultSet.getInt("seat_count"),
                    resultSet.getString("notes")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework here
        }

        return reservations;
    }

	// Retrieve all reservations from the database
	public List<Reservation> getAllReservations() {
		List<Reservation> reservations = new ArrayList<>();
		String query = "SELECT * FROM reservation";

		try (Connection connection = DBConnectionFactory.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			while (resultSet.next()) {
				reservations.add(mapResultSetToReservation(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservations;
	}

	// Update an existing reservation in the database
	public void updateReservation(Reservation reservation) {
		String query = "UPDATE reservation SET branch_id = ?, reservation_date = ?, time_frame = ?, seat_count = ?, notes = ? WHERE id = ?";

		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, reservation.getBranchId());
			statement.setDate(2, Date.valueOf(reservation.getReservationDate()));
			statement.setString(3, reservation.getTimeFrame().name());
			statement.setInt(4, reservation.getSeatCount());
			statement.setString(5, reservation.getNotes());
			statement.setInt(6, reservation.getId());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Update reservation status
	public void updateReservationStatus(int id, ReservationStatus status) {
		String query = "UPDATE reservation SET status = ? WHERE id = ?";

		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, status.name());
			statement.setInt(2, id);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Delete a reservation by ID
	public void deleteReservation(int id) {
		String query = "DELETE FROM reservation WHERE id = ?";

		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Helper method to map ResultSet to Reservation object
	private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
		return new Reservation(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("branch_id"),
				ReservationStatus.valueOf(resultSet.getString("status")),
				resultSet.getDate("reservation_date").toLocalDate(),
				TimeFrame.valueOf(resultSet.getString("time_frame")), resultSet.getInt("seat_count"),
				resultSet.getString("notes"));
	}
	
	public int getTotalReservedSeats(int branchId, LocalDate date, TimeFrame timeFrame) {
        String query = "SELECT SUM(seat_count) AS total_reserved FROM reservation WHERE branch_id = ? AND reservation_date = ? AND time_frame = ? AND status IN ('PENDING', 'CONFIRMED')";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, branchId);
            statement.setDate(2, Date.valueOf(date));
            statement.setString(3, timeFrame.name());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total_reserved");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework here
        }

        return 0;
    }
}
