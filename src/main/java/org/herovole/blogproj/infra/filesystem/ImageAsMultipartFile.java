package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageName;
import org.herovole.blogproj.domain.time.Timestamp;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
    public boolean isEmpty() {
        return file == null;
    }

    @Override
    public Timestamp getTimestamp() throws IOException {
        throw new UnsupportedEncodingException();
    }

    @Override
    public Json toJsonModel() {
        throw new UnsupportedOperationException();
    }


}
