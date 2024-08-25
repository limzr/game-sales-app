package com.example.app.dto;

import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Getter
public class GameSalesDto {

    @CsvBindByName(column = "id")
    @NotEmpty(message = "id is empty")
    private int id;

    @CsvBindByName(column = "game_no")
    @NotEmpty(message = "game_no is empty")
    @Min(value = 1, message = "game_no is not between 1 and 100")
    @Max(value = 100, message = "game_no is not between 1 and 100")
    private int gameNo;

    @CsvBindByName(column = "game_name")
    @NotEmpty(message = "game_name is empty")
    @Size(max = 20, message = "game_name is more than 20 characters")
    private String gameName;

    @CsvBindByName(column = "game_code")
    @NotEmpty(message = "game_code is empty")
    @Size(max = 5, message = "game_code is more than 5 characters")
    private String gameCode;

    @CsvBindByName(column = "type")
    @NotEmpty(message = "type is empty")
    @Min(value = 1, message = "The type must be either 1 (Online) or 2 (Offline)")
    @Max(value = 2, message = "The type must be either 1 (Online) or 2 (Offline)")
    private int type;

    @CsvBindByName(column = "cost_price")
    @NotEmpty(message = "cost_price is empty")
    @Max(value = 100, message = "The cost price not more than 100")
    @Digits(integer = 6, fraction = 2, message = "cost price should be in two decimal point and should not be more than 100.00")
    private float costPrice;

    @CsvBindByName(column = "tax")
    @NotEmpty(message = "tax is empty")
    @Digits(integer = 6, fraction = 2, message = "tax price should be in two decimal point")
    private float tax;

    @CsvBindByName(column = "sale_price")
    @NotEmpty(message = "sale_price is empty")
    @Digits(integer = 6, fraction = 2, message = "sale price should be in two decimal point")
    private float salePrice;

    @CsvBindByName(column = "date_of_sale")
    @NotEmpty(message = "date_of_sale is empty")
    private long dateOfSale;
}
