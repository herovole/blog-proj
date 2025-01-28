import React from "react";
import {CommentAndReport} from "../../../service/user/searchCommentsOutput";
import {PublicUserCommentViewUnit} from "../../../public/fragment/article/usercomment/publicUserCommentViewUnit";
import {AdminReportUnit} from "./adminReportUnit";
import {UserService} from "../../../service/user/userService";
import {HideCommentInput} from "../../../service/user/hideCommentInput";
import {useGoogleReCaptcha} from "react-google-recaptcha-v3";
import {BasicApiResult} from "../../../domain/basicApiResult";
import {AdminBanModal} from "./adminBanModal";

type AdminCommentUnitProps = {
    content: CommentAndReport;
}

export const AdminCommentUnit: React.FC<AdminCommentUnitProps> = ({content}) => {
    const userService: UserService = new UserService();
    const [hidesComment, setHidesComment] = React.useState(content.comment.body.isHidden);
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "hidesComment";


    const handleHideComment = async (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();
        if (!executeRecaptcha) {
            console.error('reCAPTCHA not yet available');
            return;
        }
        const recaptchaToken: string = await executeRecaptcha(googleReCaptchaActionLabel);
        if (!recaptchaToken) {
            console.error('verification failed');
            return;
        }
        setHidesComment(e.currentTarget.checked);
        const input: HideCommentInput = new HideCommentInput(
            content.comment.body.commentSerialNumber,
            hidesComment,
            recaptchaToken
        );
        const output: BasicApiResult = await userService.hideComment(input);
        if (output.isSuccessful()) {
            console.info(output.getMessage("Hide Comment"));
        } else {
            console.error(output.getMessage("Hide Comment"));
        }
    }

    return (
        <div className="user-comment-individual">
            <div className="comment-form-header">article-{content.comment.body.articleId} : {content.title}</div>
            <span>コメントを非表示</span>
            <input className="admin-editable-text-activated"
                   type="checkbox"
                   checked={hidesComment}
                   onChange={handleHideComment}
            />
            <AdminBanModal
                label="Ban Comment User"
                userId={content.comment.body.publicUserId}
                userBannedUntil={content.userBannedUntil}
                hasUserBanned={content.hasUserBanned}
                ip={content.comment.body.ip}
                ipBannedUntil={content.ipBannedUntil}
                hasIpBanned={content.hasIpBanned}/>

            <PublicUserCommentViewUnit content={content.comment}/>
            {content.reportingUnits.map(report => (
                <div key={report.reporting.logId}>
                    <AdminReportUnit report={report}/>
                </div>
            ))}
        </div>
    );

}