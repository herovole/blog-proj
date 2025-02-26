package org.herovole.blogproj.domain.accesskey;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageName;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessKeyAsPath implements AccessKey {

    private static final String NULL_EXPRESSION = "-";

    public static AccessKeyAsPath valueOf(String path) {
        return new AccessKeyAsPath(path);
    }

    public static AccessKeyAsPath valueOf(ImageName imageName) {
        return new AccessKeyAsPath(imageName.memorySignature());
    }

    public static AccessKeyAsPath nameOf(Image image) {
        return valueOf(image.getImageName().memorySignature());
    }

    public static AccessKeyAsPath empty() {
        return new AccessKeyAsPath(NULL_EXPRESSION);
    }

    private final String path;

    @Override
    public boolean isEmpty() {
        return path == null || path.isEmpty() || path.equals(NULL_EXPRESSION) || path.equalsIgnoreCase("null");
    }

    @Override
    public String memorySignature() {
        return isEmpty() ? "" : this.path;
    }

    @Override
    public boolean correspondsWith(String expression) {
        return this.path.equals(expression);
    }

    @Override
    public AccessKey appendWithSlash(AccessKey accessKey) {
        return AccessKeyAsPath.valueOf(this.path + "/" + accessKey.memorySignature());
    }
}
