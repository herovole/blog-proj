package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.tag.searchtopictags.SearchTopicTagsOutput;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class SearchTopicTagsPresenter extends GenericPresenterProto<SearchTopicTagsOutput> {

    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<SearchTopicTagsOutput.Json>builder()
                .contentJsonModel(this.content.toJsonModel()) // Don't return Token to browser.
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }

}
