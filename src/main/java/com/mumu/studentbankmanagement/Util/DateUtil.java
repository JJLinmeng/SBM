package com.mumu.studentbankmanagement.Util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
    public static Date LocalDateToDate(LocalDate localDate){
        LocalDateTime localDateTime = localDate.atStartOfDay();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(defaultZoneId).toInstant();
        Date date = Date.from(instant);
        return date;
    }

}
