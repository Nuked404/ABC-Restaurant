package com.abc.model;

public class OrderItem {
    private int id;
    private int orderId; // foreign key from Order
    private int menuItemId; // foreign key from MenuItem
    private int qty;

    public OrderItem() {}

    public OrderItem(int id, int orderId, int menuItemId, int qty) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
