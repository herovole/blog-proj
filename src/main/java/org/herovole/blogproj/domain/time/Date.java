package org.herovole.blogproj.domain.time;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.PostContent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Date {

    private static final String EMPTY = "-";
    private static final String EMPTY2 = "null";
    private static final Pattern patternYyyyMMDd = Pattern.compile("\\d{8}");
    private static final DateTimeFormatter formatterYyyyMMDd = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Pattern patternYyyySlashMMSlashDd = Pattern.compile("\\d{4}/\\d{2}/\\d{2}");
    private static final DateTimeFormatter formatterYyyySlashMMSlashDd = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private static final String API_KEY_SOURCE_DATE = "sourceDate";

    public static Date fromPostContentArticleDate(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_SOURCE_DATE);
        return valueOf(child.getValue());
    }

    public static Date valueOf(String field) {
        if (field == null || field.isEmpty() || EMPTY2.equalsIgnoreCase(field) || EMPTY.equals(field)) return empty();
        if (patternYyyySlashMMSlashDd.matcher(field).matches())
            return valueOf(LocalDate.parse(field, formatterYyyySlashMMSlashDd));
        if (patternYyyyMMDd.matcher(field).matches())
            return valueOf(LocalDate.parse(field, formatterYyyyMMDd));
        throw new DomainInstanceGenerationException(field);
    }


    public static Date valueOf(LocalDate localDate) {
        return new Date(localDate);
    }

    public static Date empty() {
        return new Date(null);
    }

    public static Date today() {
        return valueOf(LocalDate.now());
    }

    public static Date newYear2024() {
        return valueOf(LocalDate.of(2024, 1, 1));
    }

    private final LocalDate yyyyMMdd;

    public boolean isEmpty() {
        return null == yyyyMMdd;
    }

    public int daysFrom(Date that) {
        if (this.isEmpty() || that.isEmpty()) throw new IllegalStateException();
        return (int) ChronoUnit.DAYS.between(that.toLocalDate(), this.toLocalDate());
    }

    public boolean precedes(Date that) {
        return this.daysFrom(that) < 0;
    }

    public boolean postcedes(Date that) {
        return this.daysFrom(that) > 0;
    }

    public Date shift(int i) {
        return Date.valueOf(this.toLocalDate().plusDays(i));
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : this.toLocalDate().format(formatterYyyyMMDd);
    }

    public LocalDate toLocalDate() {
        return this.yyyyMMdd;
    }

    public int getYearInInt() {
        return this.yyyyMMdd.getYear();
    }

    public int getMonthInInt() {
        return this.yyyyMMdd.getMonthValue();
    }

    public int getDayInInt() {
        return this.yyyyMMdd.getDayOfMonth();
    }

    public boolean isSunday() {
        return this.toLocalDate().getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    public Date sundayOnSameWeek() {
        int dayOfWeek = this.toLocalDate().getDayOfWeek().getValue();
        if (dayOfWeek == 7) return this;
        return this.shift(dayOfWeek * (-1));
    }

    public Week inclusiveWeek() {
        return new Week(this.sundayOnSameWeek());
    }

    public Timestamp beginningTimestampOfDay() {
        return Timestamp.valueOf(this.toLocalDate().atStartOfDay());
    }
}
