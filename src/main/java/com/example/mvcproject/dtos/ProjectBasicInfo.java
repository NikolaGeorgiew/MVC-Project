package com.example.mvcproject.dtos;

import java.math.BigDecimal;

public class ProjectBasicInfo {

    private String name;

    private String description;

    private BigDecimal payment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Project Name :" + this.name + "\n" +
                "   Description: " + this.description + "\n" +
                "   " + this.payment;
    }
}
