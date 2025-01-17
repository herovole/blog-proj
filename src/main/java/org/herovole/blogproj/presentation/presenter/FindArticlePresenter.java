package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.tag.searchcountrytags.SearchCountryTagsOutput;
import org.herovole.blogproj.domain.article.Article;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class FindArticlePresenter extends GenericPresenterProto<Article> {

    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<Article.Json>builder()
                .contentJsonModel(this.content.toJsonModel())
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }

}
