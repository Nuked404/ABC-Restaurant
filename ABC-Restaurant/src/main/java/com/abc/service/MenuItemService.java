package com.abc.service;

import java.sql.SQLException;
import java.util.List;

import com.abc.dao.MenuItemDAO;
import com.abc.model.MenuItem;

public class MenuItemService {

    // Singleton instance of MenuItemService
    private static MenuItemService instance;
    private MenuItemDAO menuItemDAO;

    // Private constructor to prevent instantiation
    private MenuItemService() {
        this.menuItemDAO = new MenuItemDAO();
    }

    // Static method to get the singleton instance
    public static MenuItemService getInstance() {
        if (instance == null) {
            synchronized (MenuItemService.class) {
                if (instance == null) {
                    instance = new MenuItemService();
                }
            }
        }
        return instance;
    }

    // Method to add a MenuItem
    public void addMenuItem(MenuItem menuItem) {
        menuItemDAO.addMenuItem(menuItem);
    }

    // Method to update a MenuItem
    public void updateMenuItem(MenuItem menuItem) {
        menuItemDAO.updateMenuItem(menuItem);
    }

    // Method to delete a MenuItem by ID
    public void deleteMenuItem(int id) {
        menuItemDAO.deleteMenuItem(id);
    }

    // Method to get a list of all MenuItems
    public List<MenuItem> getAllMenuItems() throws SQLException {
        return menuItemDAO.getAllMenuItems();
    }

    // Method to get a MenuItem by ID
    public MenuItem getMenuItemById(int id) throws SQLException {
        return menuItemDAO.getMenuItemById(id);
    }
}
