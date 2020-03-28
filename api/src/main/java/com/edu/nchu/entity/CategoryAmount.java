package com.edu.nchu.entity;

import java.io.Serializable;

public class CategoryAmount implements Serializable {
    private String category;

    private String amount;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount == null ? null : amount.trim();
    }
}