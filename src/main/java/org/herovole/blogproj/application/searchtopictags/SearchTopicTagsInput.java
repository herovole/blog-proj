package org.herovole.blogproj.application.searchtopictags;

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

    private static final String API_KEY = "topicTags";

    //?page=...&itemsPerPage=...&isDetailed=...
    public static SearchTopicTagsInput fromFormContent(FormContent formContent) {
        FormContent contents = formContent.getChildren(API_KEY);
        return new SearchTopicTagsInput(
                PagingRequest.fromFormContent(contents),
                GenericSwitch.fromFormContentIsDetailed(contents)
        );
    }

    private final PagingRequest pagingRequest;
    private final GenericSwitch withStat;

    public boolean isDetailed() {
        return withStat.isTrue();
    }
}
