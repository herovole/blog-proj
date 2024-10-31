package org.herovole.blogproj.domain.time;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Timestamp {

    private static final String EMPTY = "-";
    private static final ZoneOffset zoneOffsetTokyo = ZoneOffset.of("+09:00");
    private static final ZoneId zoneIdTokyo = ZoneId.of("Asia/Tokyo");

    private static final DateTimeFormatter formatterYyyyMMddSpaceHHmmss = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
    private static final DateTimeFormatter formatterYyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static Timestamp empty() {
        return new Timestamp(null);
    }

    public static Timestamp valueOf(long unixTimestamp) {
        return new Timestamp(unixTimestamp);
    }

    public static Timestamp fromDateHourMinute(Date date, Hour hour, Minute minute) {
        LocalDateTime localDateTime = LocalDateTime.of(
                date.getYearInInt(),
                date.getMonthInInt(),
                date.getDayInInt(),
                hour.memorySignature(),
                minute.memorySignature()
        );
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneOffsetTokyo); // convert to UTC+9
        long unixTimestamp = zonedDateTime.toInstant().getEpochSecond();
        return Timestamp.valueOf(unixTimestamp);
    }

    private final Long unix;

    boolean isEmpty() {
        return unix == null;
    }

    public String letterSignatureYyyyMMddSpaceHHmmss() {
        if(this.isEmpty()) return EMPTY;
        LocalDateTime date = this.toLocalDateTime();
        return date.format(formatterYyyyMMddSpaceHHmmss);
    }

    public Date toDate() {
        LocalDateTime date = this.toLocalDateTime();
        return Date.valueOf(date.format(formatterYyyyMMdd));
    }

    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unix), zoneIdTokyo);
    }

    public long memorySignature() {
        return this.unix;
    }

    @Override
    public String toString() {
        return this.letterSignatureYyyyMMddSpaceHHmmss();
    }
}
