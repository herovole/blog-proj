package org.herovole.blogproj.domain.comment.rating;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Getter
public class RealRatingLog implements RatingLog {
    private final IntegerId logId;
    @EqualsAndHashCode.Include //Equals is configured as such, for the purpose of combining its array on RatingLogs.
    private final IntegerId commentSerialNumber;
    private final IntegerPublicUserId publicUserId;
    private final IPv4Address ip;
    private final Rating rating;

    @Override
    public IntegerId getId() {
        return this.logId;
    }

    @Override
    public IPv4Address getIPv4Address() {
        return this.getIp();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean hasActualRating() {
        return !this.rating.isNone();
    }
}
