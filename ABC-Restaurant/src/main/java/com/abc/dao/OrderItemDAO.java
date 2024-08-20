package com.abc.dao;

import com.abc.model.OrderItem;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    
    public void addOrderItem(OrderItem orderItem) {
        String query = "INSERT INTO order_item (order_id, menu_item_id, qty) VALUES (?, ?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getMenuItemId());
            statement.setInt(3, orderItem.getQty());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM order_item WHERE order_id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                orderItems.add(new OrderItem(
                    resultSet.getInt("id"),
                    resultSet.getInt("order_id"),
                    resultSet.getInt("menu_item_id"),
                    resultSet.getInt("qty")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderItems;
    }

    public void updateOrderItem(OrderItem orderItem) {
        String query = "UPDATE order_item SET qty = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, orderItem.getQty());
            statement.setInt(2, orderItem.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderItem(int id) {
        String query = "DELETE FROM order_item WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
