package com.abc.model;

import java.math.BigDecimal;

public class Offer {
    private int id;
    private String name;
    private String description;
    private BigDecimal discount;

    public Offer() {}

    public Offer(int id, String name, String description, BigDecimal discount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
