package com.dlizarra.starter.excel;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Radu on 4/12/2018.
 */
@RestController
public class ExpenseController {

    @Autowired
    ApachePOIExcelRead excelCache;

    @RequestMapping(value = "/api/expense", method = RequestMethod.GET)
    public Map<String, List<ExcelFile>> findAll() {
        Map<String, List<ExcelFile>> newMap = excelCache.getParsedFiles().entrySet()
            .stream()
            .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue));

        return newMap;
    }

    @RequestMapping(value = "/api/expense/year", method = RequestMethod.GET)
    public Set<String> getAllYears() {
        return excelCache.getParsedFiles().keySet()
            .stream()
            .map(key -> key.getName())
            .collect(Collectors.toSet());
    }


    //TODO change /year/{year} to /year/{year}/month
    @RequestMapping(value = "/api/expense/year/{year}", method = RequestMethod.GET)
    public List<String> getMonthNames(@PathVariable String year) {
        List<String> collect = excelCache.getParsedFiles().entrySet()
            .stream()
            .filter(entry -> entry.getKey().getName().equals(year))
            .map(Map.Entry::getValue)
            .flatMap(Collection::stream)
            .map(excelFile -> excelFile.getFile().getName())
            .collect(Collectors.toList());

        return collect;
    }

    //TODO change /year/{year}/{month} to /year/{year}/month/{month}
    @RequestMapping(value = "/api/expense/year/{year}/{month}", method = RequestMethod.GET)
    public List<ExcelFile> getMonths(@PathVariable String year, @PathVariable String month) {
        //get months for year
        List<ExcelFile> excelFiles = excelCache.getParsedFiles().entrySet()
            .stream()
            .filter(entry -> entry.getKey().getName().equals(year))
            .map(Map.Entry::getValue)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        //reconstruct Excel file name
        String filename = month + ".xlsx";

        //get specific excel file, there can be duplicates
        List<ExcelFile> collect = excelFiles
            .stream()
            .filter(excelFile -> excelFile.getFile().getName().endsWith(filename))
            .collect(Collectors.toList());

        return collect;
    }
}
