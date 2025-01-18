package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.auth.trackuser.TrackUserOutput;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class TrackUserPresenter extends GenericPresenterProto<TrackUserOutput> {
    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<Boolean>builder()
                .contentJsonModel(null) // Don't return user info to the browser.
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }

}
