package com.example.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class TotalGameSalesDto {

    private Integer totalGamesSold;

    private Float totalSales;

    @CsvBindByName(column = "game_no")
    @Min(value = 1, message = "game_no is not between 1 and 100")
    @Max(value = 100, message = "game_no is not between 1 and 100")
    private Integer gameNo;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate fromDate;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate toDate;
}
