package org.herovole.blogproj.domain.comment.rating;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.FormContent;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Rating {

    public static Rating fromFormRating(FormContent formContent) {
        FormContent child = formContent.getChildren(API_KEY_RATING);
        return valueOf(Integer.parseInt(child.getValue()));
    }

    public static Rating valueOf(Integer value) {
        if (value == null) return new Rating(null);
        if (value < 0) return new Rating(false);
        if (value > 0) return new Rating(true);
        return new Rating(null);
    }

    public static Rating none() {
        return valueOf(null);
    }

    private static final String API_KEY_RATING = "rating";

    private final Boolean value;

    public boolean isNone() {
        return value == null;
    }

    public int memorySignature() {
        if (this.isNone()) return 0;
        if (Boolean.TRUE.equals(this.value)) {
            return 1;
        } else {
            return -1;
        }
    }

}