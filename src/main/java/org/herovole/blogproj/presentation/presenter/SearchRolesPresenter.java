package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.tag.searchroles.SearchRolesOutput;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SearchRolesPresenter extends GenericPresenterProto<SearchRolesOutput> {

    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<SearchRolesOutput.Json>builder()
                .contentJsonModel(this.content == null ? null : this.content.toJsonModel())
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }

}
