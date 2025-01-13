package org.herovole.blogproj.application.user.rateusercomment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.herovole.blogproj.application.user.checkuser.CheckUserInput;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.IPv4Address;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.comment.rating.Rating;
import org.herovole.blogproj.domain.publicuser.UniversallyUniqueId;

@ToString
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessRateUserCommentInput {

    public static class Builder {
        private IPv4Address iPv4Address;
        private UniversallyUniqueId uuId;
        private String verificationToken;
        private FormContent formContent;

        public Builder setiPv4Address(IPv4Address iPv4Address) {
            this.iPv4Address = iPv4Address;
            return this;
        }

        public Builder setUuId(UniversallyUniqueId uuId) {
            this.uuId = uuId;
            return this;
        }

        public Builder setVerificationToken(String verificationToken) {
            this.verificationToken = verificationToken;
            return this;
        }

        public Builder setFormContent(FormContent formContent) {
            this.formContent = formContent;
            return this;
        }

        public ProcessRateUserCommentInput build() {
            if (iPv4Address == null || uuId == null || verificationToken == null || formContent == null) {
                throw new IllegalStateException(ProcessRateUserCommentInput.class.getSimpleName() + "Invalid building process.");
            }
            FormContent children = formContent.getGrandchildren(API_KEY_PARENT_PREFIX, API_KEY_PREFIX);
            children.println("comment post (parse 2)");
            return new ProcessRateUserCommentInput(
                    iPv4Address,
                    uuId,
                    verificationToken,
                    IntegerId.fromFormContentCommentSerialNumber(children),
                    Rating.fromFormRating(children)
            );
        }
    }

    private static final String API_KEY_PARENT_PREFIX = "article";
    private static final String API_KEY_PREFIX = "userCommentRating";

    private final IPv4Address iPv4Address;
    private final UniversallyUniqueId uuId;
    private final String verificationToken;

    private final IntegerId commentSerialNumber;
    private final Rating rating;

    CheckUserInput buildCheckUserInput() {
        return CheckUserInput.builder()
                .iPv4Address(this.iPv4Address)
                .uuId(this.uuId)
                .build();
    }

}

