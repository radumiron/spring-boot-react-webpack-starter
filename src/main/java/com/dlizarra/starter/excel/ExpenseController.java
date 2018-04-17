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

/*    @RequestMapping(value = "/api/expense/year/{year}", method = RequestMethod.GET)
    public List<ExcelFile> getMonths(@PathVariable String year) {
        List<ExcelFile> collect = excelCache.getParsedFiles().entrySet()
            .stream()
            .filter(entry -> entry.getKey().getName().equals(year))
            .map(Map.Entry::getValue)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        return collect;
    }*/
}
