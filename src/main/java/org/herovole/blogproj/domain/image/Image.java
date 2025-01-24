package org.herovole.blogproj.domain.image;

public interface Image {

    ImageName getImageName();

    boolean isEmpty();

    Json toJsonModel();

    record Json(String fileName, String registrationTimestamp) {
    }
}
