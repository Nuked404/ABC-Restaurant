package com.abc.dao;

import com.abc.enums.ReservationStatus;
import com.abc.enums.TimeFrame;
import com.abc.model.Reservation;
import com.abc.util.DBConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private ReservationDAO reservationDAO;

    private static MockedStatic<DBConnectionFactory> mockedDBConnectionFactory;

    @BeforeAll
    static void setUpStatic() {
        // Setup static mocking once for the entire test class
        mockedDBConnectionFactory = mockStatic(DBConnectionFactory.class);
    }

    @AfterAll
    static void tearDownStatic() {
        // Deregister the static mock after all tests are done
        mockedDBConnectionFactory.close();
    }

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        reservationDAO = new ReservationDAO();

        // Set up the mock connection for each test
        when(DBConnectionFactory.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void testAddReservation() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        Reservation reservation = new Reservation(1, 1, 1, ReservationStatus.PENDING, LocalDate.now(), TimeFrame.MORNING, 4, "No notes");
        reservationDAO.addReservation(reservation);

        verify(mockPreparedStatement).setInt(1, reservation.getUserId());
        verify(mockPreparedStatement).setInt(2, reservation.getBranchId());
        verify(mockPreparedStatement).setString(3, reservation.getStatus().name());
        verify(mockPreparedStatement).setDate(4, Date.valueOf(reservation.getReservationDate()));
        verify(mockPreparedStatement).setString(5, reservation.getTimeFrame().name());
        verify(mockPreparedStatement).setInt(6, reservation.getSeatCount());
        verify(mockPreparedStatement).setString(7, reservation.getNotes());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetReservationById() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("user_id")).thenReturn(1);
        when(mockResultSet.getInt("branch_id")).thenReturn(1);
        when(mockResultSet.getString("status")).thenReturn(ReservationStatus.PENDING.name());
        when(mockResultSet.getDate("reservation_date")).thenReturn(Date.valueOf(LocalDate.now()));
        when(mockResultSet.getString("time_frame")).thenReturn(TimeFrame.MORNING.name());
        when(mockResultSet.getInt("seat_count")).thenReturn(4);
        when(mockResultSet.getString("notes")).thenReturn("No notes");

        Reservation reservation = reservationDAO.getReservationById(1);

        assertNotNull(reservation);
        assertEquals(1, reservation.getId());
        assertEquals(1, reservation.getUserId());
        assertEquals(1, reservation.getBranchId());
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
        assertEquals(LocalDate.now(), reservation.getReservationDate());
        assertEquals(TimeFrame.MORNING, reservation.getTimeFrame());
        assertEquals(4, reservation.getSeatCount());
        assertEquals("No notes", reservation.getNotes());
    }

    @Test
    void testGetReservationsByUserId() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getInt("user_id")).thenReturn(1).thenReturn(1);
        when(mockResultSet.getInt("branch_id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("status")).thenReturn(ReservationStatus.PENDING.name()).thenReturn(ReservationStatus.CONFIRMED.name());
        when(mockResultSet.getDate("reservation_date")).thenReturn(Date.valueOf(LocalDate.now())).thenReturn(Date.valueOf(LocalDate.now().plusDays(1)));
        when(mockResultSet.getString("time_frame")).thenReturn(TimeFrame.MORNING.name()).thenReturn(TimeFrame.AFTERNOON.name());
        when(mockResultSet.getInt("seat_count")).thenReturn(4).thenReturn(2);
        when(mockResultSet.getString("notes")).thenReturn("No notes").thenReturn("Some notes");

        List<Reservation> reservations = reservationDAO.getReservationsByUserId(1);

        assertEquals(2, reservations.size());
        assertEquals(1, reservations.get(0).getUserId());
        assertEquals(1, reservations.get(1).getUserId());
    }

    @Test
    void testGetAllReservations() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getInt("user_id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getInt("branch_id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("status")).thenReturn(ReservationStatus.PENDING.name()).thenReturn(ReservationStatus.CONFIRMED.name());
        when(mockResultSet.getDate("reservation_date")).thenReturn(Date.valueOf(LocalDate.now())).thenReturn(Date.valueOf(LocalDate.now().plusDays(1)));
        when(mockResultSet.getString("time_frame")).thenReturn(TimeFrame.MORNING.name()).thenReturn(TimeFrame.AFTERNOON.name());
        when(mockResultSet.getInt("seat_count")).thenReturn(4).thenReturn(2);
        when(mockResultSet.getString("notes")).thenReturn("No notes").thenReturn("Some notes");

        List<Reservation> reservations = reservationDAO.getAllReservations();

        assertEquals(2, reservations.size());
        assertEquals(1, reservations.get(0).getId());
        assertEquals(2, reservations.get(1).getId());
    }

    @Test
    void testUpdateReservation() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        Reservation reservation = new Reservation(1, 1, 1, ReservationStatus.PENDING, LocalDate.now(), TimeFrame.MORNING, 4, "No notes");
        reservationDAO.updateReservation(reservation);

        verify(mockPreparedStatement).setInt(1, reservation.getBranchId());
        verify(mockPreparedStatement).setDate(2, Date.valueOf(reservation.getReservationDate()));
        verify(mockPreparedStatement).setString(3, reservation.getTimeFrame().name());
        verify(mockPreparedStatement).setInt(4, reservation.getSeatCount());
        verify(mockPreparedStatement).setString(5, reservation.getNotes());
        verify(mockPreparedStatement).setInt(6, reservation.getId());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testUpdateReservationStatus() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        reservationDAO.updateReservationStatus(1, ReservationStatus.CONFIRMED);

        verify(mockPreparedStatement).setString(1, ReservationStatus.CONFIRMED.name());
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDeleteReservation() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        reservationDAO.deleteReservation(1);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetTotalReservedSeats() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("total_reserved")).thenReturn(10);

        int totalReservedSeats = reservationDAO.getTotalReservedSeats(1, LocalDate.now(), TimeFrame.MORNING);

        assertEquals(10, totalReservedSeats);
    }
}
