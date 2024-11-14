package org.herovole.blogproj.domain.time;


public interface DateRange {
    boolean isEmpty();

    String letterSignature();

    Date from();

    Date to();

    default DateRange[] splitToIncludingWeeks()  {
        throw new UnsupportedOperationException();
    }

    default DateRange[] splitToIncludedWeeks() {
        throw new UnsupportedOperationException();
    }
}
