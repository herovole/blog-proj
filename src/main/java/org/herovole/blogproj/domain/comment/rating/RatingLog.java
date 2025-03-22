package org.herovole.blogproj.domain.comment.rating;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;

public interface RatingLog {
    static RatingLog empty() {
        return new EmptyRatingLog();
    }

    IntegerId getId();

    IntegerId getCommentSerialNumber();

    IntegerPublicUserId getPublicUserId();

    IPv4Address getIPv4Address();

    Rating getRating();

    boolean isEmpty();

    boolean hasActualRating();

    default Json toJsonModel() {
        return new Json(
                this.getCommentSerialNumber().longMemorySignature(),
                this.getRating().memorySignature());
    }

    record Json(long commentSerialNumber, int rating) {
    }
}
