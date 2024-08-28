package com.abc.dao;

import com.abc.enums.OrderStatus;
import com.abc.model.Order;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Add an order to the database
    public int addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (user_id, status, total, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setString(2, order.getStatus().name());
            ps.setBigDecimal(3, order.getTotal());
            ps.setTimestamp(4, order.getCreatedAt());
            ps.setTimestamp(5, order.getUpdatedAt());

            ps.executeUpdate();

            // Retrieve the generated order ID
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return the generated ID
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }

    // Retrieve an order by its ID
    public Order getOrderById(int id) {
        Order order = null;
        String query = "SELECT * FROM `orders` WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                order = new Order(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    OrderStatus.valueOf(resultSet.getString("status")), // Convert String to enum
                    resultSet.getBigDecimal("total"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    // Retrieve all orders from the database
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM `orders`";

        try (Connection connection = DBConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                orders.add(new Order(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    OrderStatus.valueOf(resultSet.getString("status")), // Convert String to enum
                    resultSet.getBigDecimal("total"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getTimestamp("updated_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Update an existing order in the database
    public void updateOrder(Order order) {
        String query = "UPDATE `orders` SET status = ?, total = ?, updated_at = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, order.getStatus().name()); // Convert enum to String
            statement.setBigDecimal(2, order.getTotal());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setInt(4, order.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateOrderStatus(int orderId, OrderStatus status) throws SQLException {
        String sql = "UPDATE orders SET status = ?, updated_at = NOW() WHERE id = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, orderId);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating order status failed, no rows affected.");
            }
        }
    }

    // Cancel an order by updating its status
    public void cancelOrder(int id) {
        String query = "UPDATE `orders` SET status = ?, updated_at = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, OrderStatus.CANCELED.name()); // Convert enum to String
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setInt(3, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    OrderStatus.valueOf(resultSet.getString("status")), // Convert String to enum
                    resultSet.getBigDecimal("total"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getTimestamp("updated_at")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
