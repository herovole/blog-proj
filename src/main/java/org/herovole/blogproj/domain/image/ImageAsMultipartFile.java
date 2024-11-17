package org.herovole.blogproj.domain.image;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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
    public String getFileName() {
        return this.file.getOriginalFilename();
    }

}
