package org.herovole.blogproj.presentation.presenter;

import jakarta.servlet.http.HttpServletResponse;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.presentation.ControllerErrorType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public abstract class GenericPresenterProto<T> implements GenericPresenter<T> {

    protected T content;
    protected ControllerErrorType controllerErrorType = ControllerErrorType.NONE;
    protected Timestamp timestampBannedUntil = Timestamp.empty();
    protected String message;

    public GenericPresenter<T> setContent(T content) {
        this.content = content;
        return this;
    }

    public GenericPresenter<T> setUseCaseErrorType(UseCaseErrorType useCaseErrorType) {
        this.controllerErrorType = ControllerErrorType.of(useCaseErrorType);
        return this;
    }

    public GenericPresenter<T> setTimestampBannedUntil(Timestamp timestampBannedUntil) {
        this.timestampBannedUntil = timestampBannedUntil;
        return this;
    }

    public GenericPresenter<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public abstract String buildResponseBody();

    public ResponseEntity<String> buildResponseEntity() {
        return ResponseEntity.status(this.controllerErrorType.getHttpStatus()).body(this.buildResponseBody());
    }

    public void addFilteringErrorInfo(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(this.controllerErrorType.getHttpStatus().value());
        httpServletResponse.getWriter().write(this.buildResponseBody());
        httpServletResponse.getWriter().flush();
    }

    @Override
    public void interruptProcess() throws ApplicationProcessException {
        this.controllerErrorType.getUseCaseErrorType().throwException(this.message);
    }
}
