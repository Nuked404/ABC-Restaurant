package com.abc.service;
import com.abc.dao.BranchDAO;
import com.abc.dao.ReservationDAO;
import com.abc.enums.ReservationStatus;
import com.abc.enums.TimeFrame;
import com.abc.model.Reservation;
import com.abc.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Make sure to uncomment the service public constructor . . .

class ReservationServiceTest {

    @Mock
    private ReservationDAO reservationDAO;

    @Mock
    private BranchDAO branchDAO;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations for mocks and inject mocks
        MockitoAnnotations.openMocks(this);

        // Setting up a sample reservation with user_id = 3 and branch_id = 9
        reservation = new Reservation(1, 3, 9, ReservationStatus.PENDING, LocalDate.now(), TimeFrame.MORNING, 5, "Sample note");
    }

    @Test
    void addReservation_ShouldCallAddReservationOnDAO() {
        // Act: Call the method on the service
        reservationService.addReservation(reservation);

        // Assert: Verify if addReservation was called on the mocked reservationDAO
        verify(reservationDAO, times(1)).addReservation(reservation);
    }

    @Test
    void getReservationById_ShouldReturnReservation_WhenExists() {
        // Arrange: Mock reservationDAO to return the reservation
        when(reservationDAO.getReservationById(1)).thenReturn(reservation);

        // Act: Call the method on the service
        Reservation result = reservationService.getReservationById(1);

        // Assert: Check if the result matches the expected reservation and verify DAO interaction
        assertEquals(reservation, result);
        verify(reservationDAO, times(1)).getReservationById(1);
    }

    @Test
    void getReservationsByUserId_ShouldReturnListOfReservations() {
        // Arrange: Create a list of reservations for the given user
        List<Reservation> expectedReservations = Arrays.asList(
                new Reservation(1, 3, 9, LocalDate.now(), TimeFrame.MORNING, 5, "Sample note"),
                new Reservation(2, 3, 9, LocalDate.now().plusDays(1), TimeFrame.AFTERNOON, 3, "Another note")
        );

        // Mock the DAO to return this list
        when(reservationDAO.getReservationsByUserId(3)).thenReturn(expectedReservations);

        // Act: Call the method on the service
        List<Reservation> result = reservationService.getReservationsByUserId(3);

        // Assert: Check if the result matches the expected list and verify DAO interaction
        assertEquals(expectedReservations.size(), result.size());
        assertEquals(expectedReservations, result);
        verify(reservationDAO, times(1)).getReservationsByUserId(3);
    }

    @Test
    void getAllReservations_ShouldReturnListOfReservations() {
        // Arrange: Create a list of all reservations
        List<Reservation> expectedReservations = Collections.singletonList(reservation);

        // Mock the DAO to return this list
        when(reservationDAO.getAllReservations()).thenReturn(expectedReservations);

        // Act: Call the method on the service
        List<Reservation> result = reservationService.getAllReservations();

        // Assert: Check if the result matches the expected list and verify DAO interaction
        assertEquals(expectedReservations.size(), result.size());
        assertEquals(expectedReservations, result);
        verify(reservationDAO, times(1)).getAllReservations();
    }

    @Test
    void updateReservation_ShouldCallUpdateOnDAO() {
        // Act: Call the method on the service
        reservationService.updateReservation(reservation);

        // Assert: Verify if updateReservation was called on the mocked reservationDAO
        verify(reservationDAO, times(1)).updateReservation(reservation);
    }

    @Test
    void updateReservationStatus_ShouldCallUpdateStatusOnDAO() {
        // Act: Call the method on the service
        reservationService.updateReservationStatus(1, ReservationStatus.CONFIRMED);

        // Assert: Verify if updateReservationStatus was called on the mocked reservationDAO
        verify(reservationDAO, times(1)).updateReservationStatus(1, ReservationStatus.CONFIRMED);
    }

    @Test
    void deleteReservation_ShouldCallDeleteOnDAO() {
        // Act: Call the method on the service
        reservationService.deleteReservation(1);

        // Assert: Verify if deleteReservation was called on the mocked reservationDAO
        verify(reservationDAO, times(1)).deleteReservation(1);
    }

    @Test
    void getAvailableSeats_ShouldReturnCorrectSeatCount() {
        // Arrange: Define the total and reserved seats
        int totalSeats = 20;
        int reservedSeats = 10;

        // Mock the DAO calls to return the defined values
        when(branchDAO.getTotalSeats(9)).thenReturn(totalSeats);
        when(reservationDAO.getTotalReservedSeats(9, LocalDate.now(), TimeFrame.MORNING)).thenReturn(reservedSeats);

        // Act: Call the method on the service
        int availableSeats = reservationService.getAvailableSeats(9, LocalDate.now(), TimeFrame.MORNING);

        // Assert: Check if the available seats calculation is correct and verify DAO interactions
        assertEquals(totalSeats - reservedSeats, availableSeats);
        verify(branchDAO, times(1)).getTotalSeats(9);
        verify(reservationDAO, times(1)).getTotalReservedSeats(9, LocalDate.now(), TimeFrame.MORNING);
    }
}
