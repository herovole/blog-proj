package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.domain.adminuser.AdminUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class ValidateAccessTokenPresenter extends GenericPresenterProto<AdminUser> {

    public String buildResponseBody() {
        return BasicResponseBody.<Boolean>builder()
                .contentJsonModel(null)
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }
}
