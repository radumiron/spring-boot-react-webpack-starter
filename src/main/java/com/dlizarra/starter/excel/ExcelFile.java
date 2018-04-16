package com.dlizarra.starter.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mironr on 2/22/2018.
 */
public class ExcelFile {
    private File file;
    private List<ExcelSheet> sheetList = new ArrayList<>();
    private ExcelSheet totalsSheet;

    public ExcelFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<ExcelSheet> getSheetList() {
        return sheetList;
    }

    public void setSheetList(List<ExcelSheet> sheetList) {
        this.sheetList = sheetList;
    }

    public ExcelSheet getTotalsSheet() {
        return totalsSheet;
    }

    public void setTotalsSheet(ExcelSheet totalsSheet) {
        this.totalsSheet = totalsSheet;
    }

    public void addExcelSheet(ExcelSheet currentSheet) {
        sheetList.add(currentSheet);
    }
}
