package org.herovole.blogproj.domain.time;


import org.herovole.blogproj.domain.helper.AggregateSignatureSplits;

import java.util.ArrayList;
import java.util.List;

public class RealDateRange implements DateRange {

    private static final String SEPARATOR = "%dr#";
    private final Date date0;
    private final Date date1;

    public RealDateRange(Date date0, Date date1) throws DomainInstanceGenerationException {
        this.date0 = date0;
        this.date1 = date1;
        if (date0.isEmpty() || date1.isEmpty() || date0.postcedes(date1)) throw new DomainInstanceGenerationException();
        if (date0.isEmpty() && !date1.isEmpty() ||
                !date0.isEmpty() && date1.isEmpty()) throw new DomainInstanceGenerationException();
    }

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
    public DateRange[] splitToIncludingWeeks() throws DomainInstanceGenerationException {
        List<DateRange> weeks = new ArrayList<>();
        for (Date aDayOfWeek = date0;
             aDayOfWeek.sundayOnSameWeek().precedes(date1.sundayOnSameWeek().shift(1));
             aDayOfWeek = aDayOfWeek.shift(7)) {
            weeks.add(aDayOfWeek.inclusiveWeek());
        }
        return weeks.toArray(DateRange[]::new);
    }

}
