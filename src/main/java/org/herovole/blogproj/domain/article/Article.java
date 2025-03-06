package org.herovole.blogproj.domain.article;

import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.tag.country.CountryCodes;

public interface Article {

    // post sample
    // key : articleEditingPage.commentEditor.1.text
    // val : This is comment4.

    String API_KEY = "articleEditingPage";

    static Article fromPost(FormContent formContent) {
        return RealArticle.fromPost(formContent);
    }

    static Article empty() {
        return new EmptyArticle();
    }

    boolean isEmpty();
    boolean isPublished();
    IntegerId getArticleId();

    Article append(
            IntegerIds topicTags,
            CountryCodes countries,
            IntegerIds editors,
            CommentUnits sourceComments,
            CommentUnits userComments);

    Article append(
            IntegerIds topicTags,
            CountryCodes countries,
            IntegerIds editors,
            int countSourceComments, int countUserComments);

    Article sortComments();

    Article maskPrivateItems();

    Json toJsonModel();

    interface Json {
    }
}
