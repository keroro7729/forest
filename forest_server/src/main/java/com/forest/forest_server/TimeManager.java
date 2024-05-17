package com.forest.forest_server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeManager {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH/mm/ss");

    public static String timeStamp(){
        return LocalDateTime.now().format(formatter);
    }

    public static LocalDateTime convertToLocalDateTime(String timestamp){
        return LocalDateTime.parse(timestamp, formatter);
    }
}
