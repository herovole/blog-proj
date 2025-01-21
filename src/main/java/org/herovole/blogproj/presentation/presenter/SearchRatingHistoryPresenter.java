package org.herovole.blogproj.presentation.presenter;

import org.herovole.blogproj.domain.comment.rating.RatingLogs;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SearchRatingHistoryPresenter extends GenericPresenterProto<RatingLogs> {

    @Override
    public String buildResponseBody() {
        return BasicResponseBody.<RatingLogs.Json>builder()
                .contentJsonModel(this.content.toJsonModel())
                .code(this.controllerErrorType)
                .timestampBannedUntil(this.timestampBannedUntil)
                .message(this.message == null ? this.controllerErrorType.getDefaultBrowserDebugMessage() : this.message)
                .build().toJsonModel().toJsonString();
    }

}
