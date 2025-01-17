package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.tag.searchcountrytags.SearchCountryTagsOutput;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class SearchCountryTagsPresenter extends GenericPresenterProto<SearchCountryTagsOutput> {

    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<SearchCountryTagsOutput.Json>builder()
                .contentJsonModel(this.content.toJsonModel()) // Don't return Token to browser.
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }

}
