package org.herovole.blogproj.domain.image;

import org.herovole.blogproj.domain.time.Timestamp;

import java.io.IOException;

public interface Image {

    static Image empty() {
        return new EmptyImage();
    }

    ImageName getImageName();

    boolean isEmpty();

    Json toJsonModel();

    Timestamp getTimestamp() throws IOException;

    record Json(String fileName, String registrationTimestamp) {
    }
}
