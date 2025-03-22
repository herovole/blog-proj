package org.herovole.blogproj.application.image.deleteimage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.image.ImageName;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RemoveImageInput {

    public static RemoveImageInput of(FormContent formContent) {
        return new RemoveImageInput(ImageName.fromPostContentImageName(formContent));
    }

    private final ImageName imageName;
}
