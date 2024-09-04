package com.abc.dao;


import com.abc.model.OrderItem;
import com.abc.util.DBConnectionFactory;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private OrderItemDAO orderItemDAO;

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
        orderItemDAO = new OrderItemDAO();

        // Mock the DBConnectionFactory to return the mock connection
        when(DBConnectionFactory.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void testAddOrderItem() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        OrderItem orderItem = new OrderItem(1, 101, 5, 10);
        orderItemDAO.addOrderItem(orderItem);

        verify(mockPreparedStatement).setInt(1, orderItem.getOrderId()); // 1
        verify(mockPreparedStatement).setInt(2, orderItem.getMenuItemId()); // 101
        verify(mockPreparedStatement).setInt(3, orderItem.getQty()); // 10
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetOrderItemsByOrderId() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getInt("order_id")).thenReturn(1);
        when(mockResultSet.getInt("menu_item_id")).thenReturn(101);
        when(mockResultSet.getInt("qty")).thenReturn(5);

        List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(1);

        assertEquals(1, orderItems.size());
        OrderItem item = orderItems.get(0);
        assertEquals(1, item.getId());
        assertEquals(1, item.getOrderId());
        assertEquals(101, item.getMenuItemId());
        assertEquals(5, item.getQty());

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    void testUpdateOrderItem() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        OrderItem orderItem = new OrderItem(1, 101, 5, 10);
        orderItemDAO.updateOrderItem(orderItem);

        verify(mockPreparedStatement).setInt(1, 10);
        verify(mockPreparedStatement).setInt(2, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testDeleteOrderItem() throws SQLException {
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        orderItemDAO.deleteOrderItem(1);

        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }
}
