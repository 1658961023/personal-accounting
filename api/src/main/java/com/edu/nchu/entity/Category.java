package com.edu.nchu.entity;

public class Category {
    private Integer id;

    private String name;

    private Short budgetType;

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

    public Short getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(Short budgetType) {
        this.budgetType = budgetType;
    }
}