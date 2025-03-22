package org.herovole.blogproj.domain.time;


import org.herovole.blogproj.domain.FormContent;

public interface DateRange {

    String API_KEY_DATE_FROM = "dateFrom";
    String API_KEY_DATE_TO = "dateTo";

    static DateRange fromComplementedFormContent(FormContent formContent) {
        return RealDateRange.fromComplementedFormContent(formContent);
    }

    static DateRange fromPostContent(FormContent formContent) {
        return RealDateRange.fromPostContent(formContent);
    }

    boolean isEmpty();

    String letterSignature();

    Date from();

    Date to();

    default DateRange[] splitToIncludingWeeks() {
        throw new UnsupportedOperationException();
    }

    default DateRange[] splitToIncludedWeeks() {
        throw new UnsupportedOperationException();
    }
}
