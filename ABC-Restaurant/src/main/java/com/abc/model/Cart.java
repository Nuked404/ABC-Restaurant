package com.abc.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<OrderItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        for (OrderItem orderItem : items) {
            if (orderItem.getMenuItemId() == item.getMenuItemId()) {
                orderItem.setQty(orderItem.getQty() + item.getQty());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(int menuItemId) {
        items.removeIf(orderItem -> orderItem.getMenuItemId() == menuItemId);
    }

    public void clearCart() {
        items.clear();
    }

    public BigDecimal calculateTotal(List<MenuItem> menuItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : items) {
            for (MenuItem menuItem : menuItems) {
                if (menuItem.getId() == orderItem.getMenuItemId()) {
                    total = total.add(menuItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQty())));
                    break;
                }
            }
        }
        return total;
    }
    
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (OrderItem item : items) {
            totalQuantity += item.getQty();
        }
        return totalQuantity;
    }
}