package com.studentmanager.config;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeConfig {
    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm").withZone(ZoneId.systemDefault());
}
