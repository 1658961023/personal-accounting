package com.edu.nchu.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

public class AcctRecord extends BaseRowModel implements Serializable{

    @ExcelProperty(value = "流水号",index = 0)
    private String serialNo;

    @ExcelProperty(value = "收支类型",index = 1)
    private String budgetType;

    @ExcelProperty(value = "分类",index = 2)
    private String category;

    @ExcelProperty(value = "金额",index = 3)
    private Integer amount;

    @ExcelProperty(value = "日期",index = 4)
    private String date;

    @ExcelProperty(value = "备注",index = 5)
    private String summary;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}