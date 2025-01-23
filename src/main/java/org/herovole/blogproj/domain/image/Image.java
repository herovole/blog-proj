package org.herovole.blogproj.domain.image;

import java.io.IOException;

public interface Image {

    ImageName getImageName();


    Json toJsonModel() throws IOException;

    record Json(String fileName, String registrationTimestamp) {
    }
}
