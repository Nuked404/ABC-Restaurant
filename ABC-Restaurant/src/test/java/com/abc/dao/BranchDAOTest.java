package com.abc.dao;

import com.abc.model.Branch;
import com.abc.util.DBConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BranchDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private BranchDAO branchDAO;

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
        branchDAO = new BranchDAO();

        // Set up the mock connection for each test
        when(DBConnectionFactory.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void testAddBranch() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        Branch branch = new Branch(1, "Colombo", 50);
        branchDAO.addBranch(branch);

        verify(mockPreparedStatement).setString(1, "Colombo");
        verify(mockPreparedStatement).setInt(2, 50);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetBranchById() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("location")).thenReturn("Colombo");
        when(mockResultSet.getInt("max_seats")).thenReturn(50);

        Branch branch = branchDAO.getBranchById(1);

        assertEquals(1, branch.getId());
        assertEquals("Colombo", branch.getLocation());
        assertEquals(50, branch.getMaxSeats());
    }

    @Test
    void testGetAllBranches() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("location")).thenReturn("Colombo").thenReturn("Kandy");
        when(mockResultSet.getInt("max_seats")).thenReturn(50).thenReturn(100);

        List<Branch> branches = branchDAO.getAllBranches();

        assertEquals(2, branches.size());
        assertEquals(1, branches.get(0).getId());
        assertEquals("Colombo", branches.get(0).getLocation());
        assertEquals(50, branches.get(0).getMaxSeats());
        assertEquals(2, branches.get(1).getId());
        assertEquals("Kandy", branches.get(1).getLocation());
        assertEquals(100, branches.get(1).getMaxSeats());
    }

    @Test
    void testUpdateBranch() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        Branch branch = new Branch(1, "Colombo", 50);
        branchDAO.updateBranch(branch);

        verify(mockPreparedStatement).setString(1, "Colombo");
        verify(mockPreparedStatement).setInt(2, 50);
        verify(mockPreparedStatement).setInt(3, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDeleteBranch() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        branchDAO.deleteBranch(1);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetTotalSeats() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("max_seats")).thenReturn(50);

        int totalSeats = branchDAO.getTotalSeats(1);

        assertEquals(50, totalSeats);
    }
}