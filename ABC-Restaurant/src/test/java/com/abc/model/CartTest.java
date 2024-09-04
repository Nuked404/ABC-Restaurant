package com.abc.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class CartTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
    }

    @Test
    void testAddItem_NewItem() {
        OrderItem item = new OrderItem(1, 1, 101, 2);
        cart.addItem(item);

        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getItems().get(0).getQty());
    }

    @Test
    void testAddItem_ExistingItem() {
        OrderItem item1 = new OrderItem(1, 1, 101, 2);
        OrderItem item2 = new OrderItem(2, 1, 101, 3);  // Same menuItemId as item1

        cart.addItem(item1);
        cart.addItem(item2);

        assertEquals(1, cart.getItems().size()); // Should be 1 item with updated qty
        assertEquals(5, cart.getItems().get(0).getQty());
    }

    @Test
    void testRemoveItem() {
        OrderItem item = new OrderItem(1, 1, 101, 2);
        cart.addItem(item);

        cart.removeItem(101);

        assertEquals(0, cart.getItems().size());
    }

    @Test
    void testClearCart() {
        OrderItem item1 = new OrderItem(1, 1, 101, 2);
        OrderItem item2 = new OrderItem(2, 1, 102, 3);

        cart.addItem(item1);
        cart.addItem(item2);

        cart.clearCart();

        assertEquals(0, cart.getItems().size());
    }

    @Test
    void testCalculateTotal() {
        OrderItem item1 = new OrderItem(1, 1, 101, 2); // Qty: 2
        OrderItem item2 = new OrderItem(2, 1, 102, 1); // Qty: 1

        cart.addItem(item1);
        cart.addItem(item2);

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(101, "Burger", "Tasty", new BigDecimal("5.00"), "burger.png", "Food"));
        menuItems.add(new MenuItem(102, "Fries", "Crispy", new BigDecimal("3.00"), "fries.png", "Food"));

        BigDecimal total = cart.calculateTotal(menuItems);

        assertEquals(new BigDecimal("13.00"), total); // 2 * 5.00 + 1 * 3.00 = 13.00
    }

    @Test
    void testGetTotalQuantity() {
        OrderItem item1 = new OrderItem(1, 1, 101, 2);
        OrderItem item2 = new OrderItem(2, 1, 102, 3);

        cart.addItem(item1);
        cart.addItem(item2);

        int totalQuantity = cart.getTotalQuantity();

        assertEquals(5, totalQuantity); // 2 + 3 = 5
    }
}

