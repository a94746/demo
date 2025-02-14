package com.example.demo.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

@Service
public class ExcelService {

    public int getNMax(String filePath, int n) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        try(FileInputStream file = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        int value = (int) cell.getNumericCellValue();
                        if (minHeap.size() < n) {
                            minHeap.offer(value);
                        } else if (value > minHeap.peek()) {
                            minHeap.poll();
                            minHeap.offer(value);
                        }
                    }
                }
            }

            if (minHeap.size() < n) {
                throw new IllegalArgumentException("В файле меньше чисел, чем N");
            }

            return minHeap.peek();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
