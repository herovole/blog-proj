package org.herovole.blogproj.domain.time;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Timestamp implements Comparable<Timestamp> {

    private static final String EMPTY = "-";
    private static final ZoneOffset zoneOffsetTokyo = ZoneOffset.of("+09:00");
    private static final ZoneId zoneIdTokyo = ZoneId.of("Asia/Tokyo");


    private static final DateTimeFormatter formatterFrontendDisplay = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter formatterYyyyMMddSpaceHHmmss = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
    private static final DateTimeFormatter formatterYyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    // RSS 2.0 pubDate (RFC 1123 format)
    private static final DateTimeFormatter formatterRss20 = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    public static Timestamp empty() {
        return new Timestamp(null);
    }

    public static Timestamp now() {
        return valueOf(LocalDateTime.now(zoneIdTokyo));
    }

    public static Timestamp valueOf(Instant instant) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneIdTokyo);
        return new Timestamp(localDateTime);
    }

    public static Timestamp valueOf(LocalDateTime localDateTime) {
        return new Timestamp(localDateTime);
    }

    public static Timestamp valueOf(java.util.Date deprecatedDateInstance) {
        LocalDateTime localDateTime = deprecatedDateInstance.toInstant().atZone(zoneIdTokyo).toLocalDateTime();
        return valueOf(localDateTime);
    }

    public static Timestamp valueOf(long unixTimestamp) {
        return valueOf(LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp), zoneIdTokyo));
    }

    public static Timestamp fromDateHourMinute(Date date, Hour hour, Minute minute) {
        LocalDateTime localDateTime = LocalDateTime.of(
                date.getYearInInt(),
                date.getMonthInInt(),
                date.getDayInInt(),
                hour.memorySignature(),
                minute.memorySignature()
        );
        return valueOf(localDateTime);
    }


    private final LocalDateTime localDateTime;

    public boolean isEmpty() {
        return this.localDateTime == null;
    }

    public boolean precedes(Timestamp that) {
        return this.isEmpty() || that.isEmpty() || this.localDateTime.isBefore(that.localDateTime);
    }

    public boolean precedesOrEquals(Timestamp that) {
        return this.isEmpty() || that.isEmpty() || !this.localDateTime.isAfter(that.localDateTime);
    }

    public Timestamp shiftHours(int hours) {
        if (this.isEmpty()) throw new IllegalStateException("Empty Timestamp");
        return Timestamp.valueOf(this.localDateTime.plusHours(hours));
    }

    public Timestamp shiftMinutes(int minutes) {
        if (this.isEmpty()) throw new IllegalStateException("Empty Timestamp");
        return Timestamp.valueOf(this.localDateTime.plusMinutes(minutes));
    }

    public long minusInSeconds(Timestamp that) {
        // Suppose that "that" precedes "this" normally.
        if (this.isEmpty() || that.isEmpty()) throw new IllegalStateException();
        return ChronoUnit.SECONDS.between(that.localDateTime, this.localDateTime);
    }

    public String letterSignatureYyyyMMddSpaceHHmmss() {
        if (this.isEmpty()) return EMPTY;
        return this.localDateTime.format(formatterYyyyMMddSpaceHHmmss);
    }

    public String letterSignatureFrontendDisplay() {
        if (this.isEmpty()) return EMPTY;
        return this.localDateTime.format(formatterFrontendDisplay);
    }

    public String rss20PubDate() {
        ZonedDateTime zonedDateTime = this.localDateTime.atZone(zoneIdTokyo);
        return zonedDateTime.format(formatterRss20);
    }

    public Date toDate() {
        return Date.valueOf(this.localDateTime.format(formatterYyyyMMdd));
    }

    public LocalDateTime toLocalDateTime() {
        return this.localDateTime;
    }

    public long longMemorySignature() {
        return this.localDateTime.atZone(zoneIdTokyo).toEpochSecond();
    }

    @Override
    public String toString() {
        return this.letterSignatureYyyyMMddSpaceHHmmss();
    }

    @Override
    public int compareTo(Timestamp o) {
        if (this.isEmpty() && o.isEmpty()) return 0;
        if (this.isEmpty() && !o.isEmpty()) return -1;
        if (!this.isEmpty() && o.isEmpty()) return 1;
        return this.localDateTime.compareTo(o.localDateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timestamp that = (Timestamp) o;
        return Objects.equals(this.longMemorySignature(), that.longMemorySignature());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.localDateTime);
    }
}
