package org.herovole.blogproj.application;

import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.time.Timestamp;

public interface GenericPresenter<T> {

    GenericPresenter<T> setContent(T content);

    GenericPresenter<T> setUseCaseErrorType(UseCaseErrorType useCaseErrorType);

    GenericPresenter<T> setTimestampBannedUntil(Timestamp timestampBannedUntil);

    GenericPresenter<T> setMessage(String message);

    void interruptProcess() throws ApplicationProcessException;

}
