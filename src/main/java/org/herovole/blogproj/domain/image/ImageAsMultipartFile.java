package org.herovole.blogproj.domain.image;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageAsMultipartFile implements Image {

    public static Image fromMultipartFile(MultipartFile file) {
        return new ImageAsMultipartFile(file);
    }

    private final MultipartFile file;

    public MultipartFile toMultipartFile() {
        return this.file;
    }

}
