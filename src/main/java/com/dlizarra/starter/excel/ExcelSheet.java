package com.dlizarra.starter.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mironr on 2/22/2018.
 */
public class ExcelSheet {
    private String name;
    private ExcelEntry totalSumEntry;

    private List<ExcelEntry> sheetEntries = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExcelEntry getTotalSumEntry() {
        return totalSumEntry;
    }

    public void setTotalSumEntry(ExcelEntry totalSumEntry) {
        this.totalSumEntry = totalSumEntry;
    }

    public List<ExcelEntry> getSheetEntries() {
        return sheetEntries;
    }

    public void setSheetEntries(List<ExcelEntry> sheetEntries) {
        this.sheetEntries = sheetEntries;
    }

    public void addExcelEntry(ExcelEntry entry) {
        sheetEntries.add(entry);
    }
}
