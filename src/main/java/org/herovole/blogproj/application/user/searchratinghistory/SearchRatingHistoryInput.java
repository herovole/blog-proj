package org.herovole.blogproj.application.user.searchratinghistory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.publicuser.IntegerPublicUserId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchRatingHistoryInput {
    public static class Builder {
        private IPv4Address iPv4Address;
        private IntegerPublicUserId userId;
        private FormContent formContent;

        public Builder iPv4Address(IPv4Address iPv4Address) {
            this.iPv4Address = iPv4Address;
            return this;
        }

        public Builder userId(IntegerPublicUserId userId) {
            this.userId = userId;
            return this;
        }

        public Builder formContent(FormContent formContent) {
            this.formContent = formContent;
            return this;
        }

        public SearchRatingHistoryInput build() {
            if (iPv4Address == null || userId == null || formContent == null) {
                throw new IllegalStateException(SearchRatingHistoryInput.class.getSimpleName() + "Invalid building process.");
            }
            return new SearchRatingHistoryInput(
                    iPv4Address,
                    userId,
                    IntegerId.fromFormContentArticleId(formContent)
            );
        }
    }

    private final IPv4Address iPv4Address;
    private final IntegerPublicUserId userId;

    private final IntegerId articleId;

}

