package com.example.app.service;

import com.example.app.dao.GameSalesDao;
import com.example.app.dto.TotalGameSalesDto;
import com.example.app.entity.GameSales;
import com.example.app.entity.TotalSaleSummary;
import com.example.app.repository.GameSalesRepository;
import com.example.app.util.GameSalesSpecification;
import com.example.app.util.QueryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GameSalesService {

    private final GameSalesRepository gameSalesRepository;
    private final GameSalesDao gameSalesDao;

    public GameSalesService(GameSalesRepository gameSalesRepository, GameSalesDao gameSalesDao) {
        this.gameSalesRepository = gameSalesRepository;
        this.gameSalesDao = gameSalesDao;
    }

    public Page<GameSales> getGameSales(LocalDate fromDate, LocalDate toDate, Double salePrice, QueryType salePriceQueryType, Pageable pageable) {
        Specification<GameSales> filters = Specification.where(GameSalesSpecification.getGameSales(fromDate, toDate, salePrice, salePriceQueryType));
        return gameSalesRepository.findAll(filters, pageable);
    }

    public TotalGameSalesDto getTotalSales(LocalDate fromDate, LocalDate toDate, Integer gameNo) {
        TotalSaleSummary totalSaleSummary = gameSalesDao.getTotalSalesSummary(fromDate, toDate, gameNo);
        return TotalGameSalesDto.builder()
                .totalGamesSold(totalSaleSummary.getTotalGamesSold())
                .totalSales(totalSaleSummary.getTotalSalePrice())
                .gameNo(gameNo)
                .fromDate(fromDate)
                .toDate(toDate)
                .build();
    }
}
