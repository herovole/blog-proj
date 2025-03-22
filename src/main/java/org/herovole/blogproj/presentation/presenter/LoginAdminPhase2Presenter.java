package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class LoginAdminPhase2Presenter extends GenericPresenterProto<AccessToken> {

    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<Boolean>builder()
                .contentJsonModel(null) // Don't return Token to browser.
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }

}
