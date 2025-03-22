package org.herovole.blogproj.domain.comment;

import lombok.Builder;
import lombok.Getter;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.SearchKeywords;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.time.DateRange;

@Getter
@Builder
public class UserCommentsSearchOption {


    public static UserCommentsSearchOption fromFormContent(FormContent formContent) {
        return UserCommentsSearchOption.builder()
                .pagingRequest(PagingRequest.fromFormContent(formContent))
                .dateRange(DateRange.fromComplementedFormContent(formContent))
                .keywords(SearchKeywords.fromFormContent(formContent))
                .hasReports(GenericSwitch.fromFormContentHasReports(formContent))
                .hasUnhandledReports(GenericSwitch.fromFormContentHasUnhandledReports(formContent))
                .build();
    }

    private final PagingRequest pagingRequest;
    private final DateRange dateRange;
    private final SearchKeywords keywords;
    private final GenericSwitch hasReports;
    private final GenericSwitch hasUnhandledReports;

}
