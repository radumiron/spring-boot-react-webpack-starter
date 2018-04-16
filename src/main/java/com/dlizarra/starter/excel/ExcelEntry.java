package com.dlizarra.starter.excel;

/**
 * Created by mironr on 2/22/2018.
 */
public class ExcelEntry {
    private String expenseName;
    private Double expenseValue;

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public void setExpenseValue(Double expenseValue) {
        this.expenseValue = expenseValue;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public Double getExpenseValue() {
        return expenseValue;
    }

    @Override
    public String toString() {
        return "ExcelEntry{" +
                "expenseName='" + expenseName + '\'' +
                ", expenseValue=" + expenseValue +
                '}';
    }
}
