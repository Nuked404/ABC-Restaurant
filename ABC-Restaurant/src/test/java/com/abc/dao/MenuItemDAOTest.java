package com.abc.dao;

import com.abc.model.MenuItem;
import com.abc.util.DBConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MenuItemDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private MenuItemDAO menuItemDAO;

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
        menuItemDAO = new MenuItemDAO();

        // Set up the mock connection for each test
        when(DBConnectionFactory.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void testAddMenuItem() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        MenuItem menuItem = new MenuItem(1, "Pizza", "Cheese Pizza", new BigDecimal("12.99"), "/images/pizza.png", "Main Course");
        menuItemDAO.addMenuItem(menuItem);

        verify(mockPreparedStatement).setString(1, "Pizza");
        verify(mockPreparedStatement).setString(2, "Cheese Pizza");
        verify(mockPreparedStatement).setBigDecimal(3, new BigDecimal("12.99"));
        verify(mockPreparedStatement).setString(4, "/images/pizza.png");
        verify(mockPreparedStatement).setString(5, "Main Course");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetMenuItemById() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Pizza");
        when(mockResultSet.getString("description")).thenReturn("Cheese Pizza");
        when(mockResultSet.getBigDecimal("price")).thenReturn(new BigDecimal("12.99"));
        when(mockResultSet.getString("image_path")).thenReturn("/images/pizza.png");
        when(mockResultSet.getString("category")).thenReturn("Main Course");

        MenuItem menuItem = menuItemDAO.getMenuItemById(1);

        assertEquals(1, menuItem.getId());
        assertEquals("Pizza", menuItem.getName());
        assertEquals("Cheese Pizza", menuItem.getDescription());
        assertEquals(new BigDecimal("12.99"), menuItem.getPrice());
        assertEquals("/images/pizza.png", menuItem.getImagePath());
        assertEquals("Main Course", menuItem.getCategory());
    }

    @Test
    void testGetAllMenuItems() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("name")).thenReturn("Pizza").thenReturn("Burger");
        when(mockResultSet.getString("description")).thenReturn("Cheese Pizza").thenReturn("Beef Burger");
        when(mockResultSet.getBigDecimal("price")).thenReturn(new BigDecimal("12.99")).thenReturn(new BigDecimal("8.99"));
        when(mockResultSet.getString("image_path")).thenReturn("/images/pizza.png").thenReturn("/images/burger.png");
        when(mockResultSet.getString("category")).thenReturn("Main Course").thenReturn("Main Course");

        List<MenuItem> menuItems = menuItemDAO.getAllMenuItems();

        assertEquals(2, menuItems.size());
        assertEquals(1, menuItems.get(0).getId());
        assertEquals("Pizza", menuItems.get(0).getName());
        assertEquals("Cheese Pizza", menuItems.get(0).getDescription());
        assertEquals(new BigDecimal("12.99"), menuItems.get(0).getPrice());
        assertEquals("/images/pizza.png", menuItems.get(0).getImagePath());
        assertEquals("Main Course", menuItems.get(0).getCategory());

        assertEquals(2, menuItems.get(1).getId());
        assertEquals("Burger", menuItems.get(1).getName());
        assertEquals("Beef Burger", menuItems.get(1).getDescription());
        assertEquals(new BigDecimal("8.99"), menuItems.get(1).getPrice());
        assertEquals("/images/burger.png", menuItems.get(1).getImagePath());
        assertEquals("Main Course", menuItems.get(1).getCategory());
    }

    @Test
    void testUpdateMenuItem() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        MenuItem menuItem = new MenuItem(1, "Pizza", "Cheese Pizza", new BigDecimal("12.99"), "/images/pizza.png", "Main Course");
        menuItemDAO.updateMenuItem(menuItem);

        verify(mockPreparedStatement).setString(1, "Pizza");
        verify(mockPreparedStatement).setString(2, "Cheese Pizza");
        verify(mockPreparedStatement).setBigDecimal(3, new BigDecimal("12.99"));
        verify(mockPreparedStatement).setString(4, "/images/pizza.png");
        verify(mockPreparedStatement).setString(5, "Main Course");
        verify(mockPreparedStatement).setInt(6, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testSearchMenuItems() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Pizza");
        when(mockResultSet.getString("description")).thenReturn("Cheese Pizza");
        when(mockResultSet.getBigDecimal("price")).thenReturn(new BigDecimal("12.99"));
        when(mockResultSet.getString("image_path")).thenReturn("/images/pizza.png");
        when(mockResultSet.getString("category")).thenReturn("Main Course");

        List<MenuItem> menuItems = menuItemDAO.searchMenuItems("Pizza");

        assertEquals(1, menuItems.size());
        assertEquals(1, menuItems.get(0).getId());
        assertEquals("Pizza", menuItems.get(0).getName());
        assertEquals("Cheese Pizza", menuItems.get(0).getDescription());
        assertEquals(new BigDecimal("12.99"), menuItems.get(0).getPrice());
        assertEquals("/images/pizza.png", menuItems.get(0).getImagePath());
        assertEquals("Main Course", menuItems.get(0).getCategory());
    }

    @Test
    void testDeleteMenuItem() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        menuItemDAO.deleteMenuItem(1);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }
}
