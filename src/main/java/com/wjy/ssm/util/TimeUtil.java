package com.wjy.ssm.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author WangJinYi
 *         2018/10/30
 */
public class TimeUtil {

    public static final DateTimeFormatter DATE_FORMATTER_14 = DateTimeFormatter
            .ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter DATE_FORMATTER_19 = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER_25 = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'+08:00'");
    public static final DateTimeFormatter DATE_FORMATTER_29 = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'+08:00'");

    /**
     * 把毫秒数转成LocalDateTime
     *
     * @param milliseconds 毫秒数
     * @return LocalDateTime
     */
    public static LocalDateTime millisecondsToDateTime(Long milliseconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds),
                Clock.systemDefaultZone().getZone());
    }

    /**
     * 字符串转LocalDateTime
     *
     * @param stringDateTime 字符串日期时间
     * @return LocalDateTime
     */
    public static LocalDateTime stringToDateTime(String stringDateTime) {
        LocalDateTime result;
        if (stringDateTime.length() == 25) {
            result = LocalDateTime.parse(stringDateTime, DATE_FORMATTER_25);
        } else if (stringDateTime.length() == 29) {
            result = LocalDateTime.parse(stringDateTime, DATE_FORMATTER_29);
        } else {
            throw new BusinessException("时间格式异常，时间长度应为25或29，实际为："
                    + stringDateTime.length() + ", " + stringDateTime);
        }
        return result;
    }

    /**
     * LocalDateTime转时间戳
     *
     * @param dateTime LocalDateTime
     * @return 时间戳
     */
    public static long getMilliSeconds(LocalDateTime dateTime) {
        return getInstant(dateTime).toEpochMilli();
    }

    /**
     * LocalDateTime转Instant
     *
     * @param dateTime LocalDateTime
     * @return Instant
     */
    public static Instant getInstant(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.of("+8"));
    }

    /**
     * 日期字符串转时间戳
     *
     * @param stringDateTime 日期字符串
     * @return 时间戳
     */
    public static long getMilliSeconds(String stringDateTime) {
        return getMilliSeconds(stringToDateTime(stringDateTime));
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

}
