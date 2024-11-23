package org.herovole.blogproj.domain.image;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.PostContent;

import java.util.regex.Pattern;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageName {

    private static final Pattern PATTERN_IMG = Pattern.compile("^.{1,63}\\.(jpg|png)$");
    private static final String API_KEY_IMAGE_NAME = "imageName";

    public static ImageName fromPostContentImageName(PostContent postContent) {
        PostContent child = postContent.getChildren(API_KEY_IMAGE_NAME);
        return valueOf(child.getValue());
    }

    public static ImageName valueOf(String field) {
        if (field == null || field.isEmpty()) return empty();
        if (PATTERN_IMG.matcher(field).matches()) return new ImageName(field);
        throw new DomainInstanceGenerationException(field);
    }

    public static ImageName empty() {
        return new ImageName(null);
    }

    private final String name;

    public String memorySignature() {
        return this.name;
    }
}
