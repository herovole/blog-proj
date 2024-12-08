package org.herovole.blogproj.domain.source;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.article.ArticleTitle;
import org.herovole.blogproj.domain.time.Date;

@Getter
@ToString
@Builder
public class SourcePage {
    public static SourcePage fromPostContent(FormContent formContent) {
        return builder()
                .url(SourceUrl.fromPostContent(formContent))
                .title(ArticleTitle.fromPostContentSourceTitle(formContent))
                .date(Date.fromPostContentArticleDate(formContent))
                .build();
    }

    private final SourceUrl url;
    private final ArticleTitle title;
    private final Date date;

}
