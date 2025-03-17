package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.image.resourceprefix.GetResourcePrefixOutput;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class GetResourcePrefixPresenter extends GenericPresenterProto<GetResourcePrefixOutput> {

    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<GetResourcePrefixOutput.Json>builder()
                .contentJsonModel(this.content == null ? null : this.content.toJsonModel())
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }

}
