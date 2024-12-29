package org.herovole.blogproj.domain.comment.rating;

import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.user.IntegerPublicUserId;

public class EmptyRatingLog implements RatingLog {
    @Override
    public IntegerId getId() {
        return IntegerId.empty();
    }

    @Override
    public IntegerId getCommentSerialNumber() {
        return IntegerId.empty();
    }

    @Override
    public IntegerPublicUserId getPublicUserId() {
        return IntegerPublicUserId.empty();
    }

    @Override
    public IPv4Address getIPv4Address() {
        return IPv4Address.empty();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean hasActualRating() {
        return false;
    }
}
