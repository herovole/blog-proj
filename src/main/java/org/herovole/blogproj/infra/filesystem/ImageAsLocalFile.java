package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageName;

import java.io.IOException;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageAsLocalFile implements Image {
    public static Image of(LocalFile file) {
        return new ImageAsLocalFile(file);
    }

    private final LocalFile file;

    @Override
    public ImageName getImageName() {
        return ImageName.valueOf(this.file.getName());
    }

    @Override
    public Json toJsonModel() {
        try {
            return new Json(this.file.getName(),
                    this.file.getLastModifiedTime().letterSignatureFrontendDisplay()
            );
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
