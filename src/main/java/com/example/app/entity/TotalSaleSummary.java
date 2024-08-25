package com.example.app.entity;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalSaleSummary {

    @Column(name = "total_games_sold")
    private Integer totalGamesSold;

    @Column(name = "total_sale_price")
    private Float totalSalePrice;

    public void addAmount(Float amount) {
        this.totalSalePrice = this.totalGamesSold + amount;
    }

    public void incrementCount(Integer count) {
        this.totalGamesSold += count;
    }
}
