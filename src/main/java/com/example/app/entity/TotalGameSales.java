package com.example.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class TotalGameSales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "game_no")
    @NotEmpty(message = "game_no is empty")
    @Min(value = 1, message = "game_no is not between 1 and 100")
    @Max(value = 100, message = "game_no is not between 1 and 100")
    private int gameNo;

    @Column(name = "sale_date")
    private LocalDate saleDate;

    @Column(name = "total_games_sold")
    private int totalGamesSold;

    @Column(name = "total_sale_price")
    @Digits(integer = 6, fraction = 2, message = "total sale price should be in two decimal point")
    private float totalSalePrice;

}
