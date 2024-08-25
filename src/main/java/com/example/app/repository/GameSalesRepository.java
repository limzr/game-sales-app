package com.example.app.repository;

import com.example.app.entity.GameSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSalesRepository extends JpaRepository<GameSales, Integer>, JpaSpecificationExecutor<GameSales> {

}
