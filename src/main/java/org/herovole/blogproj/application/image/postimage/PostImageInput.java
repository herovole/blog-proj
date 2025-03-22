package org.herovole.blogproj.application.image.postimage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.image.Image;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostImageInput {

    public static PostImageInput of(Image image) {
        return new PostImageInput(image);
    }

    private final Image image;
}
