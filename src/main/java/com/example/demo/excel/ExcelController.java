package com.example.demo.excel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Excel Controller", description = "Контроллер для работы с Excel файлами")
public class ExcelController {

    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @Operation(
            summary = "Получить N-ное максимальное число из файла",
            description = "Метод принимает путь к файлу и число N, возвращает N-ное максимальное число."
    )
    @ApiResponse(responseCode = "200", description = "Успешный результат")
    @ApiResponse(responseCode = "400", description = "Ошибка ввода")
    @GetMapping("/find-n-max")
    public int getNMax(
            @Parameter(description = "Путь к файлу .xlsx", required = true) @RequestParam String filePath,
            @Parameter(description = "Число N", required = true) @RequestParam int n) {
        validateGetNMax(filePath, n);
        return excelService.getNMax(filePath, n);
    }

    private void validateGetNMax(String filePath, int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n должно быть больше 0");
        if (!StringUtils.hasLength(filePath))
            throw new IllegalArgumentException("filePath не может быть пустым");
    }
}
