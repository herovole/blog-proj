package org.herovole.blogproj.domain.article;

import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.comment.CommentUnits;
import org.herovole.blogproj.domain.tag.CountryCodes;

public interface ArticleEditingPage {

    // post sample
    // key : articleEditingPage.commentEditor.1.text
    // val : This is comment4.

    String API_KEY = "articleEditingPage";

    static ArticleEditingPage fromPost(PostContent postContent) {
        return RealArticleEditingPage.fromPost(postContent);
    }

    static ArticleEditingPage empty() {
        return new EmptyArticleEditingPage();
    }

    boolean isEmpty();

    ArticleEditingPage append(
            IntegerIds topicTags,
            CountryCodes countries,
            IntegerIds editors,
            CommentUnits sourceComments);
}
