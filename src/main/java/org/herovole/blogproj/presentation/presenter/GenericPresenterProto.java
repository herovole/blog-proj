package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.UseCaseErrorType;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.presentation.ControllerErrorType;
import org.springframework.http.ResponseEntity;

public abstract class GenericPresenterProto {

    protected ControllerErrorType controllerErrorType;
    protected Timestamp timestampBannedUntil = Timestamp.empty();
    protected String message;

    public void setUseCaseErrorType(UseCaseErrorType useCaseErrorType) {
        this.controllerErrorType = ControllerErrorType.of(useCaseErrorType);
    }

    public void setTimestampBannedUntil(Timestamp timestampBannedUntil) {
        this.timestampBannedUntil = timestampBannedUntil;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public abstract ResponseEntity<String> buildResponseEntity();

}
