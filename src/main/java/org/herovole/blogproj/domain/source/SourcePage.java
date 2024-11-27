package org.herovole.blogproj.domain.source;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.article.ArticleTitle;
import org.herovole.blogproj.domain.time.Date;

@Getter
@ToString
@Builder
public class SourcePage {
    public static SourcePage fromPostContent(PostContent postContent) {
        return builder()
                .url(SourceUrl.fromPostContent(postContent))
                .title(ArticleTitle.fromPostContentSourceTitle(postContent))
                .date(Date.fromPostContentArticleDate(postContent))
                .build();
    }

    private final SourceUrl url;
    private final ArticleTitle title;
    private final Date date;

    public Json toJsonRecord() {
        return Json.builder()
                .sourceUrl(this.url.memorySignature())
                .sourceTitle(this.title.memorySignature())
                .sourceDate(this.date.letterSignature())
                .build();
    }

    @Builder
    public record Json(String sourceUrl, String sourceTitle, String sourceDate) { }

}
