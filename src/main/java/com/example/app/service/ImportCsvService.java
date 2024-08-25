package com.example.app.service;

import com.example.app.dao.GameSalesDao;
import com.example.app.dto.GameSalesDto;
import com.example.app.entity.GameSales;
import com.example.app.util.Convertor;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ImportCsvService {

    private final GameSalesDao gameSalesDao;

    public ImportCsvService(GameSalesDao gameSalesDao) {
        this.gameSalesDao = gameSalesDao;
    }

    public void importTransactionItem(MultipartFile file) throws IOException, SQLException {
        List<GameSalesDto> gameSalesDtos = readCsvFile(file);
        List<GameSales> gameSalesList = gameSalesDtos.parallelStream()
                .map(Convertor::toEntity).collect(toList());
        gameSalesDao.insertSalesAndUpdateSummary(gameSalesList);
    }

    public List<GameSalesDto> readCsvFile(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<GameSalesDto> csvToBean = new CsvToBeanBuilder<GameSalesDto>(reader)
                    .withType(GameSalesDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (Exception e) {
            throw new IOException("Error reading CSV file", e);
        }
    }
}
