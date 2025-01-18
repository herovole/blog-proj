package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.tag.searchtopictags.SearchTopicTagsOutput;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SearchTopicTagsPresenter extends GenericPresenterProto<SearchTopicTagsOutput> {

    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<SearchTopicTagsOutput.Json>builder()
                .contentJsonModel(this.content.toJsonModel())
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }

}
