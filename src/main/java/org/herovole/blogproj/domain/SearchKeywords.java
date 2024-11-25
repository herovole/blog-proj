package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchKeywords {

    private static final String API_KEY_KEYWORDS = "keywords";

    public static SearchKeywords fromPostContent(PostContent postContent) {
        PostContent child = postContent.getChildren(SearchKeywords.API_KEY_KEYWORDS);
        PostContents arrayChildren = child.getInArray();
        SearchKeyword[] keywords = arrayChildren.stream().map(p -> SearchKeyword.valueOf(p.getValue())).toArray(SearchKeyword[]::new);
        return of(keywords);
    }

    public static SearchKeywords of(SearchKeyword[] keywords) {
        return new SearchKeywords(keywords);
    }

    public static SearchKeywords empty() {
        return new SearchKeywords(new SearchKeyword[0]);
    }

    private final SearchKeyword[] keywords;

    public SearchKeyword get(int index) {
        if (index < this.keywords.length) {
            return keywords[index];
        } else {
            return SearchKeyword.empty();
        }
    }
}
