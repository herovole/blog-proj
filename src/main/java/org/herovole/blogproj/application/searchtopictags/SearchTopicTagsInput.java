package org.herovole.blogproj.application.searchtopictags;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchTopicTagsInput {


    public static SearchTopicTagsInput fromPostContent(PostContent postContent) {
        return new SearchTopicTagsInput(PagingRequest.fromPostContent(postContent));
    }

    private final PagingRequest pagingRequest;
}
