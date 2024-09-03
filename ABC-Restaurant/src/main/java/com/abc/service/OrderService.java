package com.abc.service;

import com.abc.dao.OrderDAO;
import com.abc.dao.OrderItemDAO;
import com.abc.model.Order;
import com.abc.enums.OrderStatus;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OrderService {

    // Singleton instance of OrderService
    private static OrderService instance;
    private OrderDAO orderDAO;

    // Private constructor to prevent instantiation
    private OrderService() {
        this.orderDAO = new OrderDAO();
    }

    // Static method to get the singleton instance
    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderService.class) {
                if (instance == null) {
                    instance = new OrderService();
                }
            }
        }
        return instance;
    }

    // Method to add an Order
    public int addOrder(Order order) throws SQLException {
        return orderDAO.addOrder(order);
    }

    // Method to get an Order by ID
    public Order getOrderById(int id) {
        return orderDAO.getOrderById(id);
    }

    // Method to get all Orders
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    // Method to update an Order
    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    // Method to cancel an Order by ID
    public void cancelOrder(int id) {
        orderDAO.cancelOrder(id);
    }
    
    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }
    
    public void updateOrderStatus(int orderId, OrderStatus status) throws SQLException {
    	orderDAO.updateOrderStatus(orderId, status);
    }
    
    public Map<LocalDate, BigDecimal> getIncomeForPast7Days() throws SQLException {
        return orderDAO.getIncomeForPast7Days();
    }
    public Map<String, Map<String, Object>> getSoldItemsForPast7Days() throws SQLException {
        return orderDAO.getSoldItemsForPast7Days();
    }
    
    public Map<Integer, Map<String, Object>> getBranchProfitAndCanceledCountForPast7Days() throws SQLException {
        return orderDAO.getBranchProfitAndCanceledCountForPast7Days();
    }
    
    public List<Map<String, Object>> getTop10CustomersForPast7Days() throws SQLException {
        return orderDAO.getTop10CustomersForPast7Days();
    }
}
