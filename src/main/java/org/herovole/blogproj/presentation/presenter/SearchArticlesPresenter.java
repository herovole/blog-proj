package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.article.searcharticles.SearchArticlesOutput;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SearchArticlesPresenter extends GenericPresenterProto<SearchArticlesOutput> {

    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<SearchArticlesOutput.Json>builder()
                .contentJsonModel(this.content.toJsonModel())
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }

}
