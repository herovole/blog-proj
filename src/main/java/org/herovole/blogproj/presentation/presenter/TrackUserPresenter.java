package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.auth.trackuser.TrackUserOutput;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
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
