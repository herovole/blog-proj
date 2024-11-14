package org.herovole.blogproj.domain.time;


import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.helper.AggregateSignatureSplits;

public class Week implements DateRange {
    private static final String SEPARATOR = "-";
    private final Date sunday;

    public Week(Date sunday)  {
        if(sunday.isSunday()) {
            this.sunday = sunday;
        } else {
            throw new DomainInstanceGenerationException();
        }
    }

    @Override
    public boolean isEmpty() {
        return sunday.isEmpty();
    }

    @Override
    public String letterSignature() {
        return new AggregateSignatureSplits.Builder()
                .set(0, sunday.letterSignature())
                .set(1, sunday.shift(6).letterSignature())
                .build().letterSignature(SEPARATOR);
    }

    @Override
    public Date from() {
        return this.sunday;
    }

    @Override
    public Date to() {
        return this.sunday.shift(6);
    }
}
