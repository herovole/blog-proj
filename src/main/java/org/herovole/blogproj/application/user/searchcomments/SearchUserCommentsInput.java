package org.herovole.blogproj.application.user.searchcomments;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.comment.UserCommentsSearchOption;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchUserCommentsInput {
    public static SearchUserCommentsInput of(FormContent formContent) {
        return new SearchUserCommentsInput(
                UserCommentsSearchOption.fromFormContent(formContent),
                GenericSwitch.fromFormContentIsDetailed(formContent)
        );
    }

    private final UserCommentsSearchOption searchOption;
    private final GenericSwitch isDetailed;

}

