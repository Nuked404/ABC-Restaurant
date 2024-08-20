package com.abc.model;

import java.math.BigDecimal;

import com.abc.enums.OrderStatus;

public class Order {
    private int id;
    private int userId; // foreign key from User
    private OrderStatus status;
    private BigDecimal total;

    public Order() {}

    public Order(int id, int userId, OrderStatus status, BigDecimal total) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
