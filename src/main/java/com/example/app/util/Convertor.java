package com.example.app.util;

import com.example.app.dto.GameSalesDto;
import com.example.app.entity.GameSales;

public class Convertor {

    public static GameSales toEntity(GameSalesDto gameSalesDto){
        GameSales gameSales = new GameSales();
        gameSales.setId(gameSalesDto.getId());
        gameSales.setGameNo(gameSalesDto.getGameNo());
        gameSales.setGameCode(gameSalesDto.getGameCode());
        gameSales.setGameName(gameSalesDto.getGameName());
        gameSales.setType(gameSalesDto.getType());
        gameSales.setCostPrice(gameSalesDto.getCostPrice());
        gameSales.setTax(gameSalesDto.getTax());
        gameSales.setSalePrice(gameSalesDto.getSalePrice());
        gameSales.setDateOfSale(gameSalesDto.getDateOfSale());
        return gameSales;
    }
}
