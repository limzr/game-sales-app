package com.example.app.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Util {

    public static long convertFromDateToUnixTimestamp(LocalDate localDate) {
        LocalDateTime fromLocalDateTime = localDate.atTime(0, 0, 0);
        return convertLocalDateTimeToEpochMilli(fromLocalDateTime);
    }

    public static long convertToDateToUnixTimestamp(LocalDate localDate) {
        LocalDateTime toLocalDateTime = localDate.atTime(23, 59, 59);
        return convertLocalDateTimeToEpochMilli(toLocalDateTime);
    }

    public static long convertLocalDateTimeToEpochMilli(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static String convertLocalDateToString(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public static LocalDate convertUnixTimestampToLocalDate(long unixTimestamp){
        return Instant.ofEpochMilli(unixTimestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
