package org.herovole.blogproj.application.user.rateusercomment;

import org.herovole.blogproj.application.user.checkuserban.CheckUserBan;
import org.herovole.blogproj.application.user.checkuserban.CheckUserBanInput;
import org.herovole.blogproj.application.user.checkuserban.CheckUserBanOutput;
import org.herovole.blogproj.application.user.rateusercomment.proper.RateUserComment;
import org.herovole.blogproj.application.user.rateusercomment.proper.RateUserCommentInput;
import org.herovole.blogproj.application.user.rateusercomment.proper.RateUserCommentOutput;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicity;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicityInput;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicityOutput;
import org.herovole.blogproj.domain.time.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessRateUserComment {

    private static final Logger logger = LoggerFactory.getLogger(ProcessRateUserComment.class.getSimpleName());

    private final CheckUserBan checkUserBan;
    private final VerifyOrganicity verifyOrganicity;
    private final RateUserComment rateUserComment;

    @Autowired
    public ProcessRateUserComment(CheckUserBan checkUserBan, VerifyOrganicity verifyOrganicity, RateUserComment rateUserComment) {
        this.checkUserBan = checkUserBan;
        this.verifyOrganicity = verifyOrganicity;
        this.rateUserComment = rateUserComment;
    }

    public ProcessRateUserCommentOutput process(ProcessRateUserCommentInput input) throws Exception {

        // Detect Or Recognize User
        CheckUserBanInput checkUserBanInput = input.buildCheckUserInput();
        CheckUserBanOutput checkUserBanOutput = this.checkUserBan.process(checkUserBanInput);

        if (!checkUserBanOutput.hasPassed()) {
            return ProcessRateUserCommentOutput.builder()
                    .uuId(checkUserBanOutput.getUuId())
                    .timestampBannedUntil(checkUserBanOutput.getTimestampBannedUntil())
                    .isHuman(null)
                    .hasValidOperation(null)
                    .isSuccessful(false)
                    .build();
        }

        // Check if a User is not BOT
        VerifyOrganicityInput verifyOrganicityInput = VerifyOrganicityInput.builder()
                .iPv4Address(input.getIPv4Address())
                .uuId(checkUserBanOutput.getUuId())
                .verificationToken(input.getVerificationToken())
                .build();
        VerifyOrganicityOutput verifyOrganicityOutput = this.verifyOrganicity.process(verifyOrganicityInput);

        if (!verifyOrganicityOutput.isHuman()) {
            return ProcessRateUserCommentOutput.builder()
                    .uuId(checkUserBanOutput.getUuId())
                    .timestampBannedUntil(Timestamp.empty())
                    .isHuman(false)
                    .hasValidOperation(null)
                    .isSuccessful(false)
                    .build();
        }

        // Post user comment
        RateUserCommentInput rateUserCommentInput = RateUserCommentInput.builder()
                .iPv4Address(input.getIPv4Address())
                .userId(checkUserBanOutput.getUserId())
                .commentSerialNumber(input.getCommentSerialNumber())
                .rating(input.getRating())
                .build();
        RateUserCommentOutput rateUserCommentOutput = this.rateUserComment.process(rateUserCommentInput);
        logger.info("job successful. {}", ProcessRateUserComment.class);

        return ProcessRateUserCommentOutput.builder()
                .uuId(checkUserBanOutput.getUuId())
                .timestampBannedUntil(Timestamp.empty())
                .isHuman(true)
                .hasValidOperation(rateUserCommentOutput.hasValidOperation())
                .isSuccessful(rateUserCommentOutput.hasValidOperation())
                .build();
    }
}
