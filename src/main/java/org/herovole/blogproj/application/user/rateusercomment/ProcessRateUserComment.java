package org.herovole.blogproj.application.user.rateusercomment;

import org.herovole.blogproj.application.user.checkuser.CheckUser;
import org.herovole.blogproj.application.user.checkuser.CheckUserInput;
import org.herovole.blogproj.application.user.checkuser.CheckUserOutput;
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

    private final CheckUser checkUser;
    private final VerifyOrganicity verifyOrganicity;
    private final RateUserComment rateUserComment;

    @Autowired
    public ProcessRateUserComment(CheckUser checkUser, VerifyOrganicity verifyOrganicity, RateUserComment rateUserComment) {
        this.checkUser = checkUser;
        this.verifyOrganicity = verifyOrganicity;
        this.rateUserComment = rateUserComment;
    }

    public ProcessRateUserCommentOutput process(ProcessRateUserCommentInput input) throws Exception {

        // Detect Or Recognize User
        CheckUserInput checkUserInput = input.buildCheckUserInput();
        CheckUserOutput checkUserOutput = this.checkUser.process(checkUserInput);

        if (!checkUserOutput.hasPassed()) {
            return ProcessRateUserCommentOutput.builder()
                    .uuId(checkUserOutput.getUuId())
                    .timestampBannedUntil(checkUserOutput.getTimestampBannedUntil())
                    .isHuman(null)
                    .hasValidOperation(null)
                    .isSuccessful(false)
                    .build();
        }

        // Check if a User is not BOT
        VerifyOrganicityInput verifyOrganicityInput = VerifyOrganicityInput.builder()
                .iPv4Address(input.getIPv4Address())
                .uuId(checkUserOutput.getUuId())
                .verificationToken(input.getVerificationToken())
                .build();
        VerifyOrganicityOutput verifyOrganicityOutput = this.verifyOrganicity.process(verifyOrganicityInput);

        if (!verifyOrganicityOutput.isHuman()) {
            return ProcessRateUserCommentOutput.builder()
                    .uuId(checkUserOutput.getUuId())
                    .timestampBannedUntil(Timestamp.empty())
                    .isHuman(false)
                    .hasValidOperation(null)
                    .isSuccessful(false)
                    .build();
        }

        // Post user comment
        RateUserCommentInput rateUserCommentInput = RateUserCommentInput.builder()
                .iPv4Address(input.getIPv4Address())
                .userId(checkUserOutput.getUserId())
                .commentSerialNumber(input.getCommentSerialNumber())
                .rating(input.getRating())
                .build();
        RateUserCommentOutput rateUserCommentOutput = this.rateUserComment.process(rateUserCommentInput);
        logger.info("job successful. {}", ProcessRateUserComment.class);

        return ProcessRateUserCommentOutput.builder()
                .uuId(checkUserOutput.getUuId())
                .timestampBannedUntil(Timestamp.empty())
                .isHuman(true)
                .hasValidOperation(rateUserCommentOutput.hasValidOperation())
                .isSuccessful(rateUserCommentOutput.hasValidOperation())
                .build();
    }
}
