package org.herovole.blogproj.application;

import org.herovole.blogproj.domain.time.Timestamp;

public interface GenericPresenter<T> {

    void setContent(T content);
    void setUseCaseErrorType(UseCaseErrorType useCaseErrorType);
    void setTimestampBannedUntil(Timestamp timestampBannedUntil);
    void setMessage(String message);
}
