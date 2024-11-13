package org.herovole.blogproj.domain.time;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.helper.AggregateSignatureSplits;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Date {

    private static final String EMPTY = "----";
    private static final Pattern yyyyDotMMDotDd = Pattern.compile("\\d{4}\\.\\d{2}\\.\\d{2}");
    private static final String DOT = ".";
    private static final String HYPHEN = "-";

    private static final String API_KEY_ARTICLE_DATE = "date";

    private static final DateTimeFormatter FROM_LOCAL_DATE_TO_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static Date fromPostContentArticleDate(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_ARTICLE_DATE);
        return valueOf(child.getValue());
    }

    public static Date valueOf(String field) {
        return new Date(field);
    }

    public static Date fromDottedEightDigits(String field) throws DomainInstanceGenerationException {
        if (!yyyyDotMMDotDd.matcher(field).matches()) {
            throw new DomainInstanceGenerationException();
        }
        return Date.valueOf(field.replace(DOT, ""));
    }

    public static Date valueOf(LocalDate localDate) {
        return Date.valueOf(localDate.format(FROM_LOCAL_DATE_TO_YYYYMMDD));
    }

    public static Date empty() {
        return new Date(EMPTY);
    }

    private final String yyyyMMdd;

    public boolean isEmpty() {
        return null == yyyyMMdd || EMPTY.equals(yyyyMMdd) || "".equals(yyyyMMdd);
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
        return this.yyyyMMdd;
    }

    public int intMemorySignature() {
        if (this.isEmpty()) throw new IllegalStateException();
        return Integer.parseInt(this.yyyyMMdd);
    }

    public String toHyphenatedYyyyMMDd() {
        return new AggregateSignatureSplits.Builder()
                .set(0, this.getYear())
                .set(1, this.getMonth())
                .set(2, this.getDayOfMonth())
                .build().letterSignature(HYPHEN);
    }

    public LocalDate toLocalDate() {
        return LocalDate.parse(this.toHyphenatedYyyyMMDd());
    }

    public String getYear() {
        return this.yyyyMMdd.substring(0, 4);
    }

    public int getYearInInt() {
        return Integer.parseInt(this.getYear());
    }

    public String getMonth() {
        return this.yyyyMMdd.substring(4, 6);
    }

    public int getMonthInInt() {
        return Integer.parseInt(this.getMonth());
    }

    public String getDayOfMonth() {
        return this.yyyyMMdd.substring(6, 8);
    }

    public int getDayInInt() {
        return Integer.parseInt(this.getDayOfMonth());
    }

    public boolean isSunday() {
        return this.toLocalDate().getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    public Date sundayOnSameWeek() {
        int dayOfWeek = this.toLocalDate().getDayOfWeek().getValue();
        if(dayOfWeek == 7) return this;
        return this.shift(dayOfWeek * (-1));
    }
    public Week inclusiveWeek() throws DomainInstanceGenerationException {
        return new Week(this.sundayOnSameWeek());
    }
}
