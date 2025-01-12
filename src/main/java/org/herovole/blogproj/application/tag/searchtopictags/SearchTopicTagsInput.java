package org.herovole.blogproj.application.tag.searchtopictags;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchTopicTagsInput {


    //?page=...&itemsPerPage=...&isDetailed=...
    public static SearchTopicTagsInput fromFormContent(FormContent formContent) {
        return new SearchTopicTagsInput(
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
