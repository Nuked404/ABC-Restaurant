package com.abc.model;

import java.math.BigDecimal;

public class Order {
    private int id;
    private int userId; // foreign key from User
    private String status;
    private BigDecimal total;

    public Order() {}

    public Order(int id, int userId, String status, BigDecimal total) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
