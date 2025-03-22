package org.herovole.blogproj.domain.comment.rating;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RatingLogs {
    public static RatingLogs of(RatingLog[] ratingLogs) {
        return new RatingLogs(ratingLogs);
    }

    private final RatingLog[] ratings;

    public RatingLogs combine(RatingLogs those) {
        Set<RatingLog> newLogs = new HashSet<>(Arrays.asList(this.ratings));
        newLogs.addAll(Arrays.asList(those.ratings));
        return RatingLogs.of(newLogs.toArray(new RatingLog[0]));
    }

    public Json toJsonModel() {
        return new Json(Stream.of(this.ratings).map(RatingLog::toJsonModel).toArray(RatingLog.Json[]::new));
    }

    public record Json(RatingLog.Json[] logs) {
        public String toJsonString() {
            return new Gson().toJson(this);
        }
    }
}
