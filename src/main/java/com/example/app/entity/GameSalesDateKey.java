package com.example.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class GameSalesDateKey {
    private Integer gameNo;
    private LocalDate saleDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSalesDateKey that = (GameSalesDateKey) o;
        return Objects.equals(gameNo, that.gameNo) &&
                Objects.equals(saleDate, that.saleDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameNo, saleDate);
    }
}