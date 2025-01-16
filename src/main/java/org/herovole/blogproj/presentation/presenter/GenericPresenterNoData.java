package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.presentation.ControllerErrorType;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class GenericPresenterNoData extends GenericPresenterProto implements GenericPresenter<Boolean> {

    private Boolean isSuccessful;

    @Override
    public void setContent(Boolean content) {
        this.isSuccessful = content;
    }

    private BasicResponseBody<Boolean> buildPostResponseBody() {
        return BasicResponseBody.<Boolean>builder()
                .contentJsonModel(this.isSuccessful == null ? this.controllerErrorType == ControllerErrorType.NONE : this.isSuccessful)
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build();
    }

    public ResponseEntity<String> buildResponseEntity() {
        return ResponseEntity.status(this.controllerErrorType.getHttpStatus()).body(this.buildPostResponseBody().toJsonModel().toJsonString());
    }

}
