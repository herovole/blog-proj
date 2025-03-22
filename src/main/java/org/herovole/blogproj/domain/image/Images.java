package org.herovole.blogproj.domain.image;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.time.Timestamp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Images {
    public static Images of(Image[] items) {
        return new Images(items);
    }

    private final Image[] items;

    public Images sortByTimestampDesc() throws IOException {
        List<ImageWithTimestamp> imagesWithTimestamps = new ArrayList<>();
        for (Image image : items) {
            imagesWithTimestamps.add(new ImageWithTimestamp(image, image.getTimestamp()));
        }
        return new Images(
                imagesWithTimestamps.stream()
                        .sorted((e1, e2) -> e2.timestamp.compareTo(e1.timestamp))
                        .map(e -> e.image)
                        .toArray(Image[]::new));
    }

    public Images get(PagingRequest request) {
        Image[] part = Arrays.copyOfRange(this.items,
                (int) request.getOffset(),
                Math.min((int) request.getLastIndexZeroOrigin() + 1, this.items.length)
        );
        return of(part);
    }

    @RequiredArgsConstructor
    static class ImageWithTimestamp {
        private final Image image;
        final Timestamp timestamp;
    }

    public Image.Json[] toJsonModel() {
        return Stream.of(this.items).map(Image::toJsonModel).toArray(Image.Json[]::new);
    }

}
