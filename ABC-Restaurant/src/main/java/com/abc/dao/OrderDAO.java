package com.abc.dao;

import com.abc.enums.OrderStatus;
import com.abc.model.Order;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Add an order to the database
    public void addOrder(Order order) {
        String query = "INSERT INTO `order` (user_id, status, total) VALUES (?, ?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, order.getUserId());
            statement.setString(2, order.getStatus().name()); // Convert enum to String
            statement.setBigDecimal(3, order.getTotal());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve an order by its ID
    public Order getOrderById(int id) {
        Order order = null;
        String query = "SELECT * FROM `order` WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                order = new Order(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    OrderStatus.valueOf(resultSet.getString("status")), // Convert String to enum
                    resultSet.getBigDecimal("total")
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
        String query = "SELECT * FROM `order`";

        try (Connection connection = DBConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                orders.add(new Order(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    OrderStatus.valueOf(resultSet.getString("status")), // Convert String to enum
                    resultSet.getBigDecimal("total")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Update an existing order in the database
    public void updateOrder(Order order) {
        String query = "UPDATE `order` SET status = ?, total = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, order.getStatus().name()); // Convert enum to String
            statement.setBigDecimal(2, order.getTotal());
            statement.setInt(3, order.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cancel an order by updating its status
    public void cancelOrder(int id) {
        String query = "UPDATE `order` SET status = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, OrderStatus.CANCELED.name()); // Convert enum to String
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
