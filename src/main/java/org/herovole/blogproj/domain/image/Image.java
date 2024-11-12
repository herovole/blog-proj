package org.herovole.blogproj.domain.image;

import org.springframework.web.multipart.MultipartFile;

public interface Image {
    MultipartFile toMultipartFile();
}
