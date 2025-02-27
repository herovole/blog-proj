package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.accesskey.AccessKey;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageName;
import org.herovole.blogproj.domain.time.Timestamp;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageAsS3HeadResponse implements Image {
    public static Image of(AccessKey objectName, HeadObjectResponse file) {
        return new ImageAsS3HeadResponse(objectName, file);
    }
    private final AccessKey objectName;
    private final HeadObjectResponse file;

    @Override
    public ImageName getImageName() {
        return ImageName.valueOf(this.objectName.memorySignature());
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
                this.objectName.memorySignature(),
                this.getTimestamp().letterSignatureFrontendDisplay()
        );
    }

}
