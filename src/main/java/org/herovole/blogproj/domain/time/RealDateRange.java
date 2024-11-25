package org.herovole.blogproj.domain.time;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.helper.AggregateSignatureSplits;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RealDateRange implements DateRange {

    public static RealDateRange fromComplementedPostContent(PostContent postContent) {
        PostContent postDateFrom = postContent.getChildren(API_KEY_DATE_FROM);
        Date dateFrom = Date.valueOf(postDateFrom.getValue());
        PostContent postDateTo = postContent.getChildren(API_KEY_DATE_TO);
        Date dateTo = Date.valueOf(postDateTo.getValue());
        return complementOf(dateFrom, dateTo);
    }

    public static RealDateRange fromPostContent(PostContent postContent) {
        PostContent postDateFrom = postContent.getChildren(API_KEY_DATE_FROM);
        Date dateFrom = Date.valueOf(postDateFrom.getValue());
        PostContent postDateTo = postContent.getChildren(API_KEY_DATE_TO);
        Date dateTo = Date.valueOf(postDateTo.getValue());
        return of(dateFrom, dateTo);
    }

    public static RealDateRange complementOf(Date dateFrom, Date dateTo) {
        Date dateFrom1 = dateFrom.isEmpty() ? Date.newYear2024() : dateFrom;
        Date dateTo1 = dateTo.isEmpty() ? Date.today() : dateFrom;
        return of(dateFrom1, dateTo1);
    }

    public static RealDateRange of(Date dateFrom, Date dateTo) {
        if (dateFrom.isEmpty() || dateTo.isEmpty() || dateFrom.postcedes(dateTo))
            throw new DomainInstanceGenerationException();
        if (dateFrom.isEmpty() && !dateTo.isEmpty() ||
                !dateFrom.isEmpty() && dateTo.isEmpty()) throw new DomainInstanceGenerationException();
        return new RealDateRange(dateFrom, dateTo);
    }

    private static final String SEPARATOR = "%dr#";
    private final Date date0;
    private final Date date1;

    @Override
    public boolean isEmpty() {
        return date0.isEmpty() && date1.isEmpty();
    }

    @Override
    public String letterSignature() {
        return new AggregateSignatureSplits.Builder()
                .set(0, date0.letterSignature())
                .set(1, date1.letterSignature())
                .build().letterSignature(SEPARATOR);
    }

    @Override
    public Date from() {
        return this.date0;
    }

    @Override
    public Date to() {
        return this.date1;
    }

    @Override
    public DateRange[] splitToIncludingWeeks() {
        List<DateRange> weeks = new ArrayList<>();
        for (Date aDayOfWeek = date0;
             aDayOfWeek.sundayOnSameWeek().precedes(date1.sundayOnSameWeek().shift(1));
             aDayOfWeek = aDayOfWeek.shift(7)) {
            weeks.add(aDayOfWeek.inclusiveWeek());
        }
        return weeks.toArray(DateRange[]::new);
    }

}
