package com.abc.dao;

import com.abc.model.User;
import com.abc.enums.UserRole;
import com.abc.util.DBConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private UserDAO userDAO;

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
        userDAO = new UserDAO();

        // Set up the mock connection for each test
        when(DBConnectionFactory.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void testAddUser() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        User user = new User(1, "John Doe", "john@example.com", "1234567890", 
                "123 Main St", "Apt 4B", "Colombo", 1, UserRole.CUSTOMER, "password");
        
        userDAO.addUser(user);

        verify(mockPreparedStatement).setString(1, "John Doe");
        verify(mockPreparedStatement).setString(2, "john@example.com");
        verify(mockPreparedStatement).setString(3, "1234567890");
        verify(mockPreparedStatement).setString(4, "123 Main St");
        verify(mockPreparedStatement).setString(5, "Apt 4B");
        verify(mockPreparedStatement).setString(6, "Colombo");
        verify(mockPreparedStatement).setInt(7, 1);
        verify(mockPreparedStatement).setString(8, "CUSTOMER");
        verify(mockPreparedStatement).setString(9, userDAO.hashPassword("password"));
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetUserById() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn("john@example.com");
        when(mockResultSet.getString("phone")).thenReturn("1234567890");
        when(mockResultSet.getString("address_line1")).thenReturn("123 Main St");
        when(mockResultSet.getString("address_line2")).thenReturn("Apt 4B");
        when(mockResultSet.getString("city")).thenReturn("Colombo");
        when(mockResultSet.getInt("nearest_location")).thenReturn(1);
        when(mockResultSet.getString("role")).thenReturn("CUSTOMER");

        User user = userDAO.getUserById(1);

        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhone());
        assertEquals("123 Main St", user.getAddressLine1());
        assertEquals("Apt 4B", user.getAddressLine2());
        assertEquals("Colombo", user.getCity());
        assertEquals(1, user.getNearestLocation());
        assertEquals(UserRole.CUSTOMER, user.getRole());
    }

    @Test
    void testAuthenticateUser() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("password")).thenReturn(userDAO.hashPassword("password"));
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn("john@example.com");
        when(mockResultSet.getString("phone")).thenReturn("1234567890");
        when(mockResultSet.getString("address_line1")).thenReturn("123 Main St");
        when(mockResultSet.getString("address_line2")).thenReturn("Apt 4B");
        when(mockResultSet.getString("city")).thenReturn("Colombo");
        when(mockResultSet.getInt("nearest_location")).thenReturn(1);
        when(mockResultSet.getString("role")).thenReturn("CUSTOMER");

        User user = userDAO.authenticateUser("john@example.com", "password");

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhone());
        assertEquals("123 Main St", user.getAddressLine1());
        assertEquals("Apt 4B", user.getAddressLine2());
        assertEquals("Colombo", user.getCity());
        assertEquals(1, user.getNearestLocation());
        assertEquals(UserRole.CUSTOMER, user.getRole());
    }

    @Test
    void testGetUsersByRole() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("name")).thenReturn("John Doe").thenReturn("Jane Smith");
        when(mockResultSet.getString("email")).thenReturn("john@example.com").thenReturn("jane@example.com");
        when(mockResultSet.getString("phone")).thenReturn("1234567890").thenReturn("0987654321");
        when(mockResultSet.getString("address_line1")).thenReturn("123 Main St").thenReturn("456 Elm St");
        when(mockResultSet.getString("address_line2")).thenReturn("Apt 4B").thenReturn("Apt 12A");
        when(mockResultSet.getString("city")).thenReturn("Colombo").thenReturn("Kandy");
        when(mockResultSet.getInt("nearest_location")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("role")).thenReturn("CUSTOMER").thenReturn("STAFF");

        List<User> users = userDAO.getUsersByRole(UserRole.CUSTOMER);

        assertEquals(2, users.size());
        assertEquals(1, users.get(0).getId());
        assertEquals("John Doe", users.get(0).getName());
        assertEquals("john@example.com", users.get(0).getEmail());
        assertEquals("1234567890", users.get(0).getPhone());
        assertEquals("123 Main St", users.get(0).getAddressLine1());
        assertEquals("Apt 4B", users.get(0).getAddressLine2());
        assertEquals("Colombo", users.get(0).getCity());
        assertEquals(1, users.get(0).getNearestLocation());
        assertEquals(UserRole.CUSTOMER, users.get(0).getRole());

        assertEquals(2, users.get(1).getId());
        assertEquals("Jane Smith", users.get(1).getName());
        assertEquals("jane@example.com", users.get(1).getEmail());
        assertEquals("0987654321", users.get(1).getPhone());
        assertEquals("456 Elm St", users.get(1).getAddressLine1());
        assertEquals("Apt 12A", users.get(1).getAddressLine2());
        assertEquals("Kandy", users.get(1).getCity());
        assertEquals(2, users.get(1).getNearestLocation());
        assertEquals(UserRole.STAFF, users.get(1).getRole());
    }

    @Test
    void testUpdateUser() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        User user = new User(1, "John Doe", "john@example.com", "1234567890", 
                "123 Main St", "Apt 4B", "Colombo", 1, UserRole.CUSTOMER, "password");
        
        userDAO.updateUser(user);

        verify(mockPreparedStatement).setString(1, "John Doe");
        verify(mockPreparedStatement).setString(2, "john@example.com");
        verify(mockPreparedStatement).setString(3, "1234567890");
        verify(mockPreparedStatement).setString(4, "123 Main St");
        verify(mockPreparedStatement).setString(5, "Apt 4B");
        verify(mockPreparedStatement).setString(6, "Colombo");
        verify(mockPreparedStatement).setInt(7, 1);
        verify(mockPreparedStatement).setInt(8, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testUpdatePassword() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        userDAO.updatePassword(1, "newpassword");

        verify(mockPreparedStatement).setString(1, userDAO.hashPassword("newpassword"));
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDeleteUser() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        userDAO.deleteUser(1);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }
}

