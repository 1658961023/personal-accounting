package com.edu.nchu.entity;

import java.io.Serializable;

public class Category implements Serializable {
    private Integer id;

    private String name;

    private int budgetType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public int getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(Short budgetType) {
        this.budgetType = budgetType;
    }
}