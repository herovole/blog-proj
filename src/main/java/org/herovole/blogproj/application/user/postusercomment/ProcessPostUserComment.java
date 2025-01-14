package org.herovole.blogproj.application.user.postusercomment;

import org.herovole.blogproj.application.user.checkuserban.CheckUserBan;
import org.herovole.blogproj.application.user.checkuserban.CheckUserBanInput;
import org.herovole.blogproj.application.user.checkuserban.CheckUserBanOutput;
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

    private final CheckUserBan checkUserBan;
    private final VerifyOrganicity verifyOrganicity;
    private final PostUserComment postUserComment;

    @Autowired
    public ProcessPostUserComment(CheckUserBan checkUserBan, VerifyOrganicity verifyOrganicity, PostUserComment postUserComment) {
        this.checkUserBan = checkUserBan;
        this.verifyOrganicity = verifyOrganicity;
        this.postUserComment = postUserComment;
    }

    public ProcessPostUserCommentOutput process(ProcessPostUserCommentInput input) throws Exception {

        // Detect Or Recognize User
        CheckUserBanInput checkUserBanInput = input.buildCheckUserInput();
        CheckUserBanOutput checkUserBanOutput = this.checkUserBan.process(checkUserBanInput);

        if (!checkUserBanOutput.hasPassed()) {
            return ProcessPostUserCommentOutput.builder()
                    .uuId(checkUserBanOutput.getUuId())
                    .timestampBannedUntil(checkUserBanOutput.getTimestampBannedUntil())
                    .isHuman(null)
                    .hasValidContent(null)
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
            return ProcessPostUserCommentOutput.builder()
                    .uuId(checkUserBanOutput.getUuId())
                    .timestampBannedUntil(Timestamp.empty())
                    .isHuman(false)
                    .hasValidContent(null)
                    .isSuccessful(false)
                    .build();
        }

        // Post user comment
        PostUserCommentInput postUserCommentInput = PostUserCommentInput.builder()
                .iPv4Address(input.getIPv4Address())
                .userId(checkUserBanOutput.getUserId())
                .articleId(input.getArticleId())
                .handleName(input.getHandleName())
                .commentText(input.getCommentText())
                .build();
        PostUserCommentOutput postUserCommentOutput = this.postUserComment.process(postUserCommentInput);
        logger.info("job successful. {}", ProcessPostUserComment.class);

        return ProcessPostUserCommentOutput.builder()
                .uuId(checkUserBanOutput.getUuId())
                .timestampBannedUntil(Timestamp.empty())
                .isHuman(true)
                .hasValidContent(postUserCommentOutput.hasValidContent())
                .isSuccessful(postUserCommentOutput.hasValidContent())
                .build();
    }
}
