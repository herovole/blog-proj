package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageName;
import org.herovole.blogproj.domain.time.Timestamp;
import software.amazon.awssdk.services.s3.model.S3Object;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageAsS3Object implements Image {
    public static Image of(S3Object file) {
        return new ImageAsS3Object(file);
    }

    private final S3Object file;

    @Override
    public ImageName getImageName() {
        return ImageName.valueOf(this.file.key());
    }

    @Override
    public boolean isEmpty() {
        return file == null;
    }

    @Override
    public Timestamp getTimestamp() {
        return Timestamp.valueOf(this.file.lastModified());
    }

    @Override
    public Json toJsonModel() {
        return new Json(
                this.file.key(),
                this.getTimestamp().letterSignatureFrontendDisplay()
        );
    }

}
