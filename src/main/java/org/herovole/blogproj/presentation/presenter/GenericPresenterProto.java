package org.herovole.blogproj.presentation.presenter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.presentation.ControllerErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public abstract class GenericPresenterProto<T> implements GenericPresenter<T> {

    private static final Logger logger = LoggerFactory.getLogger(GenericPresenterProto.class.getSimpleName());

    @Getter
    protected T content;
    protected ControllerErrorType controllerErrorType = ControllerErrorType.NONE;
    protected Timestamp timestampBannedUntil = Timestamp.empty();
    protected String message;

    public GenericPresenter<T> setContent(T content) {
        this.content = content;
        return this;
    }

    public GenericPresenter<T> setUseCaseErrorType(UseCaseErrorType useCaseErrorType) {
        if (this.controllerErrorType.equals(ControllerErrorType.NONE)) {
            this.controllerErrorType = ControllerErrorType.of(useCaseErrorType);
            logger.info("Assigned {}", useCaseErrorType);
        } else {
            logger.error("Reassigning {} to {}", useCaseErrorType, this.controllerErrorType);
            throw new IllegalStateException("Cannot change use case error type after it's been set");
        }
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
