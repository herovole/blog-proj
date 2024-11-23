package org.herovole.blogproj.domain.article;

import lombok.ToString;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.tag.CountryCodes;

@ToString
public class EmptyArticle implements Article {
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public IntegerId getArticleId() {
        return IntegerId.empty();
    }

    @Override
    public Article append(IntegerIds topicTags, CountryCodes countries, IntegerIds editors, CommentUnits sourceComments) {
        return this;
    }
}
