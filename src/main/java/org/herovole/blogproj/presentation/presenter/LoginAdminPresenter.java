package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.domain.adminuser.AccessToken;
import org.herovole.blogproj.presentation.ControllerErrorType;
import org.springframework.http.ResponseEntity;

public class LoginAdminPresenter extends GenericPresenterProto implements GenericPresenter<AccessToken> {

    private AccessToken accessToken;

    @Override
    public void setContent(AccessToken content) {
        this.accessToken = content;
    }

    private BasicResponseBody<Boolean> buildPostResponseBody() {
        return BasicResponseBody.<Boolean>builder()
                .contentJsonModel(this.controllerErrorType.equals(ControllerErrorType.NONE)) // Don't return Token to browser.
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build();
    }

    @Override
    public ResponseEntity<String> buildResponseEntity() {
        return ResponseEntity.status(this.controllerErrorType.getHttpStatus()).body(this.buildPostResponseBody().toJsonModel().toJsonString());
    }
}
