package com.abc.dao;

import com.abc.model.MenuItem;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {
    
    public void addMenuItem(MenuItem menuItem) {
        String query = "INSERT INTO menu_item (name, description, price, image_path,category) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, menuItem.getName());
            statement.setString(2, menuItem.getDescription());
            statement.setBigDecimal(3, menuItem.getPrice());
            statement.setString(4, menuItem.getImagePath());
            statement.setString(5, menuItem.getCategory());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MenuItem getMenuItemById(int id) {
        MenuItem menuItem = null;
        String query = "SELECT * FROM menu_item WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                menuItem = new MenuItem(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getString("image_path"),
                    resultSet.getString("category")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return menuItem;
    }

    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        String query = "SELECT * FROM menu_item";

        try (Connection connection = DBConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
             
            while (resultSet.next()) {
                menuItems.add(new MenuItem(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getString("image_path"),
                    resultSet.getString("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return menuItems;
    }

    public void updateMenuItem(MenuItem menuItem) {
        String query = "UPDATE menu_item SET name = ?, description = ?, price = ?, image_path = ?, category = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, menuItem.getName());
            statement.setString(2, menuItem.getDescription());
            statement.setBigDecimal(3, menuItem.getPrice());
            statement.setString(4, menuItem.getImagePath());
            statement.setString(5, menuItem.getCategory());
            statement.setInt(6, menuItem.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<MenuItem> searchMenuItems(String searchTerm) {
        List<MenuItem> menuItems = new ArrayList<>();
        String query = "SELECT * FROM menu_item WHERE name LIKE ? OR description LIKE ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                menuItems.add(new MenuItem(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getString("image_path"),
                    resultSet.getString("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    public void deleteMenuItem(int id) {
        String query = "DELETE FROM menu_item WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
