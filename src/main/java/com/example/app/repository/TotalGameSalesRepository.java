package com.example.app.repository;

import com.example.app.entity.TotalGameSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TotalGameSalesRepository extends JpaRepository<TotalGameSales, Integer> {

    TotalGameSales findByGameNoAndSaleDate(Integer gameNo, LocalDate saleDate);
}
