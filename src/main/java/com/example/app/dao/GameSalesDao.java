package com.example.app.dao;

import com.example.app.entity.GameSales;
import com.example.app.entity.GameSalesDateKey;
import com.example.app.entity.TotalGameSales;
import com.example.app.entity.TotalSaleSummary;
import com.example.app.repository.TotalGameSalesRepository;
import com.example.app.util.Util;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class GameSalesDao {

    private static final int BATCH_SIZE = 1000;
    private final JdbcTemplate jdbcTemplate;
    private final TotalGameSalesRepository totalGameSalesRepository;

    public GameSalesDao(JdbcTemplate jdbcTemplate, TotalGameSalesRepository totalGameSalesRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.totalGameSalesRepository = totalGameSalesRepository;
    }

    @Async
    @Transactional
    public void insertSalesAndUpdateSummary(List<GameSales> gameSalesList) {
        insertGameSales(gameSalesList);
        CompletableFuture.runAsync(() -> updateTransactionSummaries(gameSalesList));
    }

    private void insertGameSales(List<GameSales> gameSalesList) {
        List<GameSales> gameSalesBatch = new ArrayList<>();
        for (int i = 0; i < gameSalesList.size(); i += BATCH_SIZE) {
            gameSalesBatch.addAll(gameSalesList.subList(i, Math.min(i + BATCH_SIZE, gameSalesList.size())));
            batchInsert(gameSalesBatch);
            gameSalesBatch.clear();
        }
    }

    private void batchInsert(List<GameSales> gameSales) {
        String sql = "INSERT INTO game_sales (id, game_no, game_name, game_code, `type`, cost_price, tax, sale_price, date_of_sale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, gameSales, BATCH_SIZE, (preparedStatement, gameSale) -> {
            preparedStatement.setInt(1, gameSale.getId());
            preparedStatement.setInt(2, gameSale.getGameNo());
            preparedStatement.setString(3, gameSale.getGameName());
            preparedStatement.setString(4, gameSale.getGameCode());
            preparedStatement.setInt(5, gameSale.getType());
            preparedStatement.setDouble(6, gameSale.getCostPrice());
            preparedStatement.setDouble(7, gameSale.getTax());
            preparedStatement.setDouble(8, gameSale.getSalePrice());
            preparedStatement.setLong(9, gameSale.getDateOfSale());
        });
    }

    private void updateTransactionSummaries(List<GameSales> gameSalesList) {
        Map<GameSalesDateKey, TotalSaleSummary> summaryMap = new HashMap<>();

        for (GameSales gameSales : gameSalesList) {
            GameSalesDateKey key = new GameSalesDateKey(
                    gameSales.getGameNo(),
                    Util.convertUnixTimestampToLocalDate(gameSales.getDateOfSale())
            );

            TotalSaleSummary data = summaryMap
                    .computeIfAbsent(key, k -> new TotalSaleSummary(0, 0.00F));
            data.addAmount(gameSales.getSalePrice());
            data.incrementCount(1);
        }

        // Update or create daily summaries in a batch
        for (Map.Entry<GameSalesDateKey, TotalSaleSummary> entry : summaryMap.entrySet()) {
            GameSalesDateKey key = entry.getKey();
            TotalSaleSummary data = entry.getValue();

            TotalGameSales summary = totalGameSalesRepository.findByGameNoAndSaleDate(
                    key.getGameNo(), key.getSaleDate());

            if (summary == null) {
                summary = new TotalGameSales();
                summary.setGameNo(key.getGameNo());
                summary.setSaleDate(key.getSaleDate());
                summary.setTotalGamesSold(data.getTotalGamesSold());
                summary.setTotalSalePrice(data.getTotalSalePrice());
            } else {
                summary.setTotalGamesSold(summary.getTotalGamesSold() + data.getTotalGamesSold());
                summary.setTotalSalePrice(summary.getTotalSalePrice() + data.getTotalSalePrice());
            }
            totalGameSalesRepository.save(summary);
        }
    }

    public TotalSaleSummary getTotalSalesSummary(LocalDate fromDate, LocalDate toDate, Integer gameNo) {
        final var fromDateString = Util.convertLocalDateToString(fromDate);
        final var toDateString = Util.convertLocalDateToString(toDate);
        String sql;
        if (gameNo == null) {
            sql = "SELECT COALESCE (SUM(total_games_sold),0) as total_games_sold, COALESCE (SUM(total_sale_price),0) as total_sale_price from total_game_sales WHERE DATE(sale_date) BETWEEN ? AND ?";
        } else {
            sql = "SELECT COALESCE (SUM(total_games_sold),0) as total_games_sold, COALESCE (SUM(total_sale_price),0) as total_sale_price from total_game_sales WHERE DATE(sale_date) BETWEEN ? AND ?  AND game_no = ?";
        }
        TotalSaleSummary totalSaleSummary;
        if (gameNo == null) {
            totalSaleSummary = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TotalSaleSummary.class), fromDateString, toDateString);
        } else {
            totalSaleSummary = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TotalSaleSummary.class), fromDateString, toDateString, gameNo);
        }
        return totalSaleSummary;
    }
}