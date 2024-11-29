package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchKeywords {

    private static final String API_KEY_KEYWORDS = "keywords";

    public static SearchKeywords fromPostContent(PostContent postContent) {
        PostContent child = postContent.getChildren(SearchKeywords.API_KEY_KEYWORDS);
        String[] keywords = child.getValue().split(" ");
        SearchKeyword[] searchKeywords = Arrays.stream(keywords).filter(e -> !e.isEmpty()).map(SearchKeyword::valueOf).toArray(SearchKeyword[]::new);
        return of(searchKeywords);
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
