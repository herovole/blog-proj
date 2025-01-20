package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class BasicPresenter extends GenericPresenterProto<Object> {

    @Override
    public GenericPresenter<Object> setContent(Object content) {
        throw new UnsupportedOperationException(BasicPresenter.class.getSimpleName() + " doesn't carry a content.");
    }

    @Override
    public void interruptProcess() throws ApplicationProcessException {
        this.controllerErrorType.getUseCaseErrorType().throwException(this.message);
    }

    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<Boolean>builder()
                .contentJsonModel(null)
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }
}
