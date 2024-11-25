package org.herovole.blogproj.domain.time;


import org.herovole.blogproj.domain.PostContent;

public interface DateRange {

    String API_KEY_DATE_FROM = "dateFrom";
    String API_KEY_DATE_TO = "dateTo";

    static DateRange fromComplementedPostContent(PostContent postContent) {
        return RealDateRange.fromComplementedPostContent(postContent);
    }

    static DateRange fromPostContent(PostContent postContent) {
        return RealDateRange.fromPostContent(postContent);
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
