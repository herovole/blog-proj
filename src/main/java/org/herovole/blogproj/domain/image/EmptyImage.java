package org.herovole.blogproj.domain.image;

import org.herovole.blogproj.domain.time.Timestamp;

import java.io.IOException;

public class EmptyImage implements Image {
    @Override
    public ImageName getImageName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Json toJsonModel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Timestamp getTimestamp() throws IOException {
        throw new UnsupportedOperationException();
    }
}
