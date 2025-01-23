package org.herovole.blogproj.domain.image;

public interface Image {

    ImageName getImageName();


    Json toJsonModel();

    record Json(String fileName, String registrationTimestamp) {
    }
}
