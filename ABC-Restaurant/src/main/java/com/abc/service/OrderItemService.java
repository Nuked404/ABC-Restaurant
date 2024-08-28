package com.abc.service;

import com.abc.dao.OrderItemDAO;
import com.abc.model.OrderItem;

import java.util.List;

public class OrderItemService {

    // Singleton instance of OrderItemService
    private static OrderItemService instance;
    private OrderItemDAO orderItemDAO;

    // Private constructor to prevent instantiation
    private OrderItemService() {
        this.orderItemDAO = new OrderItemDAO();
    }

    // Static method to get the singleton instance
    public static OrderItemService getInstance() {
        if (instance == null) {
            synchronized (OrderItemService.class) {
                if (instance == null) {
                    instance = new OrderItemService();
                }
            }
        }
        return instance;
    }

    // Method to add an OrderItem
    public void addOrderItem(OrderItem orderItem) {
        orderItemDAO.addOrderItem(orderItem);
    }

    // Method to get OrderItems by Order ID
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return orderItemDAO.getOrderItemsByOrderId(orderId);
    }

    // Method to update an OrderItem
    public void updateOrderItem(OrderItem orderItem) {
        orderItemDAO.updateOrderItem(orderItem);
    }

    // Method to delete an OrderItem by ID
    public void deleteOrderItem(int id) {
        orderItemDAO.deleteOrderItem(id);
    }
}
