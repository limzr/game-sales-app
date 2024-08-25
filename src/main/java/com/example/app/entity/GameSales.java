package com.example.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameSales {

    @Id
    private int id;

    @Column(name = "game_no")
    @NotEmpty(message = "game_no is empty")
    @Min(value = 1, message = "game_no is not between 1 and 100")
    @Max(value = 100, message = "game_no is not between 1 and 100")
    private int gameNo;

    @Column(name = "game_name")
    @NotEmpty(message = "game_name is empty")
    @Size(max = 20, message = "game_name is more than 20 characters")
    private String gameName;

    @Column(name = "game_code")
    @NotEmpty(message = "game_code is empty")
    @Size(max = 5, message = "game_code is more than 5 characters")
    private String gameCode;

    @Column(name = "type")
    @NotEmpty(message = "type is empty")
    @Min(value = 1, message = "The type must be either 1 (Online) or 2 (Offline)")
    @Max(value = 2, message = "The type must be either 1 (Online) or 2 (Offline)")
    private int type;

    @Column(name = "cost_price")
    @Max(value = 100, message = "The cost price not more than 100")
    @Digits(integer = 6, fraction = 2, message = "cost price should be in two decimal point and should not be more than 100.00")
    private float costPrice;

    @Column(name = "tax")
    @NotEmpty(message = "sale_price is empty")
    @Digits(integer = 6, fraction = 2, message = "sale price should be in two decimal point")
    private float tax;

    @Column(name = "sale_price")
    @NotEmpty(message = "sale_price is empty")
    @Digits(integer = 6, fraction = 2, message = "sale price should be in two decimal point")
    private float salePrice;

    @Column(name = "date_of_sale")
    @NotEmpty(message = "date_of_sale is empty")
    private long dateOfSale;

}
