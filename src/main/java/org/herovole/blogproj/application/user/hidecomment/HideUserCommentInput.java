package org.herovole.blogproj.application.user.hidecomment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.IntegerId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HideUserCommentInput {

    public static HideUserCommentInput ofFormContent(long commentSerialNumberConfirmation, FormContent formContent) {
        IntegerId commentSerialNumber = IntegerId.fromFormContentCommentSerialNumber(formContent);
        if (commentSerialNumber.longMemorySignature() != commentSerialNumberConfirmation) {
            throw new IllegalArgumentException("Comment Serial Number does not match");
        }
        GenericSwitch hides = GenericSwitch.fromFormContentIsHidden(formContent);
        return new HideUserCommentInput(commentSerialNumber, hides);
    }

    private final IntegerId commentSerialNumber;
    private final GenericSwitch hides;

}

