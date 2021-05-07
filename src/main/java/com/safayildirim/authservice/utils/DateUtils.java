package com.safayildirim.authservice.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateUtils {
    public static long calculateDifferenceInMinutes(LocalDateTime date) {
        LocalDateTime currentDate = LocalDateTime.now();
        return ((Timestamp.valueOf(currentDate).getTime() - Timestamp.valueOf(date).getTime()) / (1000 * 60)) % 60;
    }
}
