package com.example.app.controller;

import com.example.app.dto.TotalGameSalesDto;
import com.example.app.entity.GameSales;
import com.example.app.service.GameSalesService;
import com.example.app.service.ImportCsvService;
import com.example.app.util.QueryType;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@RestController
@Log4j2
public class GameController {

    private final ImportCsvService importCsvService;
    private final GameSalesService gameSalesService;

    public GameController(ImportCsvService importCsvService, GameSalesService gameSalesService) {
        this.importCsvService = importCsvService;
        this.gameSalesService = gameSalesService;
    }

    @PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> importGameSales(@RequestParam(name = "file") MultipartFile file) {
        try {
            importCsvService.importTransactionItem(file);
            return ResponseEntity.ok().build();
        } catch (SQLException | IOException e) {
            log.error(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/getGameSales")
    public ResponseEntity<Page<GameSales>> getGameSales(
            @RequestParam(name = "fromDate", required = false) LocalDate fromDate,
            @RequestParam(name = "toDate", required = false) LocalDate toDate,
            @RequestParam(name = "salePrice", required = false) Double salePrice,
            @RequestParam(name = "salePriceQueryType", required = false) QueryType salePriceQueryType,
            Pageable pageable) {
        try {
            Page<GameSales> results = gameSalesService.getGameSales(fromDate, toDate, salePrice, salePriceQueryType, pageable);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            log.error(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/getTotalSales")
    public ResponseEntity<TotalGameSalesDto> getTotalSales(
            @RequestParam(name = "fromDate") LocalDate fromDate,
            @RequestParam(name = "toDate") LocalDate toDate,
            @RequestParam(name = "gameNo", required = false) Integer gameNo) {
        try {
            return ResponseEntity.ok(gameSalesService.getTotalSales(fromDate, toDate, gameNo));
        } catch (Exception e) {
            log.error(e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
