package org.herovole.blogproj.application.user.banuser;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;

@ToString
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BanUserInput {
    public static BanUserInput ofFormContent(int userId, FormContent formContent) {
        // throw error if negative days cause operational issues.
        IntegerPublicUserId formUserId = IntegerPublicUserId.fromFormContentUserId(formContent);
        if (formUserId.intMemorySignature() != userId) throw new IllegalArgumentException(formUserId.letterSignature());

        return BanUserInput.builder()
                .userId(formUserId)
                .days(Integer.parseInt(formContent.getChildren(API_KEY_DAYS_TO_SUSPEND).getValue()))
                .build();
    }

    private static final String API_KEY_DAYS_TO_SUSPEND = "days";

    private final IntegerPublicUserId userId;
    private final int days;
}

