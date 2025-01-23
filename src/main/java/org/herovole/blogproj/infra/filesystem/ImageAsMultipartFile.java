package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageName;
import org.springframework.web.multipart.MultipartFile;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageAsMultipartFile implements Image {

    public static Image of(MultipartFile file) {
        return new ImageAsMultipartFile(file);
    }

    private final MultipartFile file;

    public MultipartFile toMultipartFile() {
        return this.file;
    }

    @Override
    public ImageName getImageName() {
        return ImageName.valueOf(this.file.getOriginalFilename());
    }

    @Override
    public Json toJsonModel() {
        throw new UnsupportedOperationException();
    }

}
