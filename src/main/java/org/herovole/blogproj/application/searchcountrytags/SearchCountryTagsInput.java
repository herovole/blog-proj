package org.herovole.blogproj.application.searchcountrytags;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchCountryTagsInput {

    //?page=...&itemsPerPage=...&isDetailed=...
    public static SearchCountryTagsInput fromFormContent(FormContent formContent) {
        return new SearchCountryTagsInput(
                PagingRequest.fromFormContent(formContent),
                GenericSwitch.fromFormContentIsDetailed(formContent)
        );
    }

    private final PagingRequest pagingRequest;
    private final GenericSwitch withStat;

    public boolean isDetailed() {
        return withStat.isTrue();
    }
}
