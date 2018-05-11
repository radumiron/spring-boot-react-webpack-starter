package com.github.bitcharts.excel;

import com.github.bitcharts.model.excel.ExcelEntry;
import com.github.bitcharts.model.excel.ExcelFile;
import com.github.bitcharts.model.excel.ExcelSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mironr on 2/22/2018.
 */
public class ApachePOIExcelRead {

    private static final String FILE_LOCATION_FOLDER = "C:\\Users\\mironr\\Dropbox\\Expenses";

    private static final String TOTALS_SHEET = "Total";
    private static final String TOTALS_PER_SHEET = "Suma totala:";

    public static void main(String[] args) {
        new ApachePOIExcelRead().readExcelFiles();
    }

    private void readExcelFiles() {
        Map<File, List<File>> excelFilesMap = new ApachePOIExcelRead().readYearsFolder();

        Map<File, ExcelFile> parsedFiles = new TreeMap<>();
        for (Map.Entry<File, List<File>> entry : excelFilesMap.entrySet()) {
            System.out.println(entry);
            for (File excelFile : entry.getValue()) {
                ExcelFile parsedFile = readSingleExcelFile(excelFile);
                parsedFiles.put(entry.getKey(), parsedFile);
            }
        }
        System.out.println("finish");
    }

    private Map<File, List<File>> readYearsFolder() {
        File excelFilesFolder = new File(FILE_LOCATION_FOLDER);
        Map<File, List<File>> excelFilesMap = new TreeMap<>();

        if (excelFilesFolder.exists() && excelFilesFolder.isDirectory()) {
            List<File> yearFolders = Arrays.stream(excelFilesFolder.listFiles())
                    .filter(folder -> folder.isDirectory() && isYear(folder.getName()))
                    .collect(Collectors.toList());

            for (File yearFolder : yearFolders) {
                List<File> excelFiles = Arrays.stream(yearFolder.listFiles())
                        .filter(file -> file.isFile() && (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")))
                        .collect(Collectors.toList());
                excelFilesMap.put(yearFolder, excelFiles);
            }
        }

        return excelFilesMap;
    }

    private ExcelFile readSingleExcelFile(File file) {
        ExcelFile currentFile = new ExcelFile(file);
        try {
            System.out.println("Parsing file: " + file.getAbsolutePath());

            FileInputStream excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                ExcelSheet currentSheet = new ExcelSheet();
                Sheet excelSheet = workbook.getSheetAt(i);
                currentSheet.setName(excelSheet.getSheetName());
                System.out.println("Parsing sheet: " + excelSheet.getSheetName());

                Iterator<Row> iterator = excelSheet.iterator();

                if (TOTALS_SHEET.equals(currentSheet.getName())) {
                    currentFile.setTotalsSheet(currentSheet);
                } else {
                    //skip header row
                    iterator.next();
                    currentFile.addExcelSheet(currentSheet);
                }

                while (iterator.hasNext()) {
                    Row currentRow = iterator.next();

                    if (currentRow.getPhysicalNumberOfCells() == 0) { //if empty row, keep going
                        continue;
                    }

                    if (currentRow.getPhysicalNumberOfCells() <= 3) { //if regular row
                        //get expense name
                        ExcelEntry currentEntry = readIndividualEntry(currentRow);

                        if (TOTALS_PER_SHEET.equals(currentEntry.getExpenseName())) {
                            currentSheet.setTotalSumEntry(currentEntry);
                        } else {
                            currentSheet.addExcelEntry(currentEntry);
                        }
                        System.out.println(currentEntry);
                    } else if (TOTALS_PER_SHEET.equals(currentRow.getCell(5).getStringCellValue())) {    //row also has the sheet totals entry
                        ExcelEntry currentEntry = readIndividualEntry(currentRow);
                        ExcelEntry totalSumEntry = new ExcelEntry();
                        totalSumEntry.setExpenseName(currentRow.getCell(5).getStringCellValue());
                        totalSumEntry.setExpenseValue(currentRow.getCell(6).getNumericCellValue());

                        currentSheet.addExcelEntry(currentEntry);
                        currentSheet.setTotalSumEntry(totalSumEntry);

                        System.out.println(currentEntry);
                        System.out.println("Total sum per sheet: " + totalSumEntry);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentFile;
    }

    private ExcelEntry readIndividualEntry(Row currentRow) {
        ExcelEntry currentEntry = new ExcelEntry();
        Iterator<Cell> cellIterator = currentRow.iterator();
        //get expense name
        Cell currentCell = cellIterator.next();

        String expenseName = currentCell.getStringCellValue();

        if (cellIterator.hasNext()) {
            //get expense value
            currentCell = cellIterator.next();


            currentEntry.setExpenseName(expenseName);

            Double expenseValue = 0.0;
            try {
                expenseValue = currentCell.getNumericCellValue();
            } catch (IllegalStateException e) {

            } finally {
                currentEntry.setExpenseValue(expenseValue);
            }
        }

        return currentEntry;
    }

    private boolean isYear(String filename) {
        return filename.matches("^[1-9][0-9]{3}");
    }
}

