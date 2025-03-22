package org.herovole.blogproj.application.image.searchimages;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchImagesInput {

    public static SearchImagesInput of(FormContent formContent) {
        return new SearchImagesInput(
                PagingRequest.fromFormContent(formContent),
                GenericSwitch.fromFormContentRequiresAuth(formContent)
        );
    }

    private final PagingRequest pagingRequest;
    private final GenericSwitch isDetailed;
}
