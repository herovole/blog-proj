package org.herovole.blogproj.application.user.reportusercomment;

import org.herovole.blogproj.application.user.checkuser.CheckUser;
import org.herovole.blogproj.application.user.checkuser.CheckUserInput;
import org.herovole.blogproj.application.user.checkuser.CheckUserOutput;
import org.herovole.blogproj.application.user.reportusercomment.proper.ReportUserComment;
import org.herovole.blogproj.application.user.reportusercomment.proper.ReportUserCommentInput;
import org.herovole.blogproj.application.user.reportusercomment.proper.ReportUserCommentOutput;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicity;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicityInput;
import org.herovole.blogproj.application.user.verifyorganicity.VerifyOrganicityOutput;
import org.herovole.blogproj.domain.time.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessReportUserComment {

    private static final Logger logger = LoggerFactory.getLogger(ProcessReportUserComment.class.getSimpleName());

    private final CheckUser checkUser;
    private final VerifyOrganicity verifyOrganicity;
    private final ReportUserComment reportUserComment;

    @Autowired
    public ProcessReportUserComment(CheckUser checkUser, VerifyOrganicity verifyOrganicity, ReportUserComment reportUserComment) {
        this.checkUser = checkUser;
        this.verifyOrganicity = verifyOrganicity;
        this.reportUserComment = reportUserComment;
    }

    public ProcessReportUserCommentOutput process(ProcessReportUserCommentInput input) throws Exception {

        // Detect Or Recognize User
        CheckUserInput checkUserInput = input.buildCheckUserInput();
        CheckUserOutput checkUserOutput = this.checkUser.process(checkUserInput);

        if (!checkUserOutput.hasPassed()) {
            return ProcessReportUserCommentOutput.builder()
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
            return ProcessReportUserCommentOutput.builder()
                    .uuId(checkUserOutput.getUuId())
                    .timestampBannedUntil(Timestamp.empty())
                    .isHuman(false)
                    .hasValidContent(null)
                    .isSuccessful(false)
                    .build();
        }

        // Post user comment
        ReportUserCommentInput reportUserCommentInput = ReportUserCommentInput.builder()
                .iPv4Address(input.getIPv4Address())
                .userId(checkUserOutput.getUserId())
                .commentSerialNumber(input.getCommentSerialNumber())
                .reportingText(input.getReportingText())
                .build();
        ReportUserCommentOutput reportUserCommentOutput = this.reportUserComment.process(reportUserCommentInput);
        logger.info("job successful. {}", ProcessReportUserComment.class);

        return ProcessReportUserCommentOutput.builder()
                .uuId(checkUserOutput.getUuId())
                .timestampBannedUntil(Timestamp.empty())
                .isHuman(true)
                .hasValidContent(reportUserCommentOutput.hasValidContent())
                .isSuccessful(reportUserCommentOutput.hasValidContent())
                .build();
    }
}
