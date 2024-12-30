package org.herovole.blogproj.application.user.postusercomment;

import org.herovole.blogproj.application.user.checkuser.CheckUser;
import org.herovole.blogproj.application.user.checkuser.CheckUserInput;
import org.herovole.blogproj.application.user.checkuser.CheckUserOutput;
import org.herovole.blogproj.application.user.postusercomment.proper.PostUserComment;
import org.herovole.blogproj.application.user.postusercomment.proper.PostUserCommentInput;
import org.herovole.blogproj.application.user.postusercomment.proper.PostUserCommentOutput;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicity;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicityInput;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicityOutput;
import org.herovole.blogproj.domain.time.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessPostUserComment {

    private static final Logger logger = LoggerFactory.getLogger(ProcessPostUserComment.class.getSimpleName());

    private final CheckUser checkUser;
    private final VerifyOrganicity verifyOrganicity;
    private final PostUserComment postUserComment;

    @Autowired
    public ProcessPostUserComment(CheckUser checkUser, VerifyOrganicity verifyOrganicity, PostUserComment postUserComment) {
        this.checkUser = checkUser;
        this.verifyOrganicity = verifyOrganicity;
        this.postUserComment = postUserComment;
    }

    public ProcessPostUserCommentOutput process(ProcessPostUserCommentInput input) throws Exception {

        // Detect Or Recognize User
        CheckUserInput checkUserInput = input.buildCheckUserInput();
        CheckUserOutput checkUserOutput = this.checkUser.process(checkUserInput);

        if (!checkUserOutput.hasPassed()) {
            return ProcessPostUserCommentOutput.builder()
                    .uuId(checkUserOutput.getUuId())
                    .timestampBannedUntil(checkUserOutput.getTimestampBannedUntil())
                    .isHuman(null)
                    .hasValidContent(null)
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
            return ProcessPostUserCommentOutput.builder()
                    .uuId(checkUserOutput.getUuId())
                    .timestampBannedUntil(Timestamp.empty())
                    .isHuman(false)
                    .hasValidContent(null)
                    .isSuccessful(false)
                    .build();
        }

        // Post user comment
        PostUserCommentInput postUserCommentInput = PostUserCommentInput.builder()
                .iPv4Address(input.getIPv4Address())
                .userId(checkUserOutput.getUserId())
                .articleId(input.getArticleId())
                .handleName(input.getHandleName())
                .commentText(input.getCommentText())
                .build();
        PostUserCommentOutput postUserCommentOutput = this.postUserComment.process(postUserCommentInput);
        logger.info("job successful. {}", ProcessPostUserComment.class);

        return ProcessPostUserCommentOutput.builder()
                .uuId(checkUserOutput.getUuId())
                .timestampBannedUntil(Timestamp.empty())
                .isHuman(true)
                .hasValidContent(postUserCommentOutput.hasValidContent())
                .isSuccessful(postUserCommentOutput.hasValidContent())
                .build();
    }
}
