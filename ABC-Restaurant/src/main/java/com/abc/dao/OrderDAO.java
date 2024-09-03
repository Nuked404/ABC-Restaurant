package com.abc.dao;

import com.abc.enums.OrderStatus;
import com.abc.model.Order;
import com.abc.model.OrderItem;
import com.abc.util.DBConnectionFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {

	// Add an order to the database
	public int addOrder(Order order) throws SQLException {
		String sql = "INSERT INTO orders (user_id, status, total, branch_id) VALUES (?, ?, ?, ?)"; // Added branch_id
		try (Connection conn = DBConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, order.getUserId());
			ps.setString(2, order.getStatus().name());
			ps.setBigDecimal(3, order.getTotal());
			ps.setInt(4, order.getBranchId());

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
				order = new Order(resultSet.getInt("id"), resultSet.getInt("user_id"),
						OrderStatus.valueOf(resultSet.getString("status")), resultSet.getBigDecimal("total"),
						resultSet.getTimestamp("created_at"), resultSet.getTimestamp("updated_at"),
						resultSet.getInt("branch_id"));
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
				orders.add(new Order(resultSet.getInt("id"), resultSet.getInt("user_id"),
						OrderStatus.valueOf(resultSet.getString("status")), resultSet.getBigDecimal("total"),
						resultSet.getTimestamp("created_at"), resultSet.getTimestamp("updated_at"),
						resultSet.getInt("branch_id")));
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

		try (Connection conn = DBConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
				Order order = new Order(resultSet.getInt("id"), resultSet.getInt("user_id"),
						OrderStatus.valueOf(resultSet.getString("status")), resultSet.getBigDecimal("total"),
						resultSet.getTimestamp("created_at"), resultSet.getTimestamp("updated_at"),
						resultSet.getInt("branch_id"));
				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orders;
	}

	// Dashboard functions, Using 7 days as a template to scale it later

	public Map<LocalDate, BigDecimal> getIncomeForPast7Days() throws SQLException {
		Map<LocalDate, BigDecimal> incomeMap = new LinkedHashMap<>(); // LinkedHashMap to maintain order
		String sql = "SELECT DATE(created_at) AS date, SUM(total) AS total_income " + "FROM orders "
				+ "WHERE created_at >= NOW() - INTERVAL 7 DAY " + "AND status = 'DELIVERED' "
				+ "GROUP BY DATE(created_at) " + "ORDER BY DATE(created_at)";

		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				LocalDate date = resultSet.getDate("date").toLocalDate();
				BigDecimal totalIncome = resultSet.getBigDecimal("total_income");
				incomeMap.put(date, totalIncome);
			}
		}
		return incomeMap;
	}

	public Map<String, Map<String, Object>> getSoldItemsForPast7Days() throws SQLException {
		Map<String, Map<String, Object>> itemsData = new LinkedHashMap<>(); // LinkedHashMap to maintain order
		String sql = "SELECT mi.name AS item_name, SUM(oi.qty) AS total_quantity, "
				+ "SUM(oi.qty * mi.price) AS total_price " + "FROM order_item oi "
				+ "JOIN orders o ON oi.order_id = o.id " + "JOIN menu_item mi ON oi.menu_item_id = mi.id "
				+ "WHERE o.status = 'DELIVERED' " + "AND o.created_at >= NOW() - INTERVAL 7 DAY " + "GROUP BY mi.name "
				+ "ORDER BY total_quantity DESC"; // Order by total_quantity in descending order

		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				String itemName = resultSet.getString("item_name");
				int totalQuantity = resultSet.getInt("total_quantity");
				BigDecimal totalPrice = resultSet.getBigDecimal("total_price");

				Map<String, Object> data = new HashMap<>();
				data.put("totalQuantity", totalQuantity);
				data.put("totalPrice", totalPrice);
				itemsData.put(itemName, data);
			}
		}
		return itemsData;
	}

	public Map<Integer, Map<String, Object>> getBranchProfitAndCanceledCountForPast7Days() throws SQLException {
		Map<Integer, Map<String, Object>> branchDataMap = new HashMap<>();
		String sql = "SELECT o.branch_id, b.location, "
				+ "SUM(CASE WHEN o.status = 'DELIVERED' THEN o.total ELSE 0 END) AS total_profit, "
				+ "SUM(CASE WHEN o.status = 'CANCELED' THEN 1 ELSE 0 END) AS canceled_orders " + "FROM orders o "
				+ "JOIN branch b ON o.branch_id = b.id " + "WHERE o.created_at >= NOW() - INTERVAL 7 DAY "
				+ "GROUP BY o.branch_id " + "ORDER BY total_profit DESC";

		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int branchId = resultSet.getInt("branch_id");
				Map<String, Object> branchInfo = new HashMap<>();
				branchInfo.put("location", resultSet.getString("location"));
				branchInfo.put("total_profit", resultSet.getBigDecimal("total_profit"));
				branchInfo.put("canceled_orders", resultSet.getInt("canceled_orders"));
				branchDataMap.put(branchId, branchInfo);
			}
		}
		return branchDataMap;
	}

	
	public List<Map<String, Object>> getTop10CustomersForPast7Days() throws SQLException {
		List<Map<String, Object>> customerPurchases = new ArrayList<>();
		String sql = "SELECT u.name AS customer_name, "
				+ "(SELECT location FROM branch WHERE id = u.nearest_location) AS branch_name, "
				+ "SUM(o.total) AS total_purchases " + "FROM orders o " + "JOIN user u ON o.user_id = u.id "
				+ "WHERE o.status = 'DELIVERED' AND o.created_at >= NOW() - INTERVAL 7 DAY " + "GROUP BY u.id, u.name "
				+ // Group by user id and name to combine totals across branches
				"ORDER BY total_purchases DESC " + "LIMIT 10";

		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Map<String, Object> row = new HashMap<>();
				row.put("customerName", resultSet.getString("customer_name"));
				row.put("branchName", resultSet.getString("branch_name"));
				row.put("totalPurchases", resultSet.getBigDecimal("total_purchases"));
				customerPurchases.add(row);
			}
		}

		return customerPurchases;
	}

}
