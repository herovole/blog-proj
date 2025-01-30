import React from "react";
import {CommentAndReport} from "../../../service/user/searchCommentsOutput";
import {PublicUserCommentViewUnit} from "../../../public/fragment/article/usercomment/publicUserCommentViewUnit";
import {AdminReportUnit} from "./adminReportUnit";
import {UserService} from "../../../service/user/userService";
import {HideCommentInput} from "../../../service/user/hideCommentInput";
import {useGoogleReCaptcha} from "react-google-recaptcha-v3";
import {BasicApiResult} from "../../../domain/basicApiResult";
import {AdminBanModal} from "./adminBanModal";
import {useNavigate} from "react-router-dom";

type AdminCommentUnitProps = {
    content: CommentAndReport;
    directoryToIndividualPage: string;
}

export const AdminCommentUnit: React.FC<AdminCommentUnitProps> = ({content, directoryToIndividualPage}) => {
    const userService: UserService = new UserService();
    const [hidesComment, setHidesComment] = React.useState(content.commentUnit.body.isHidden);
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "hidesComment";
    const navigate = useNavigate();

    const navigateToArticle = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();
        navigate(directoryToIndividualPage + "/" + content.commentUnit.body.articleId);
    }

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
            content.commentUnit.body.commentSerialNumber,
            hidesComment,
            true,
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
            <button className="comment-form-header"
                    onClick={navigateToArticle}>
                article-{content.commentUnit.body.articleId} : {content.title}</button>
            <br/>
            <span>コメントを非表示</span>
            <input className="admin-editable-text-activated"
                   type="checkbox"
                   checked={hidesComment}
                   onChange={handleHideComment}
            />
            <br/>
            <AdminBanModal
                label="Ban Comment User"
                userId={content.commentUnit.body.publicUserId}
                userBannedUntil={content.userBannedUntil}
                hasUserBanned={content.hasUserBanned}
                ip={content.commentUnit.body.ip}
                ipBannedUntil={content.ipBannedUntil}
                hasIpBanned={content.hasIpBanned}/>

            <PublicUserCommentViewUnit content={content.commentUnit} modeAdmin/>
            {content.reportingUnits.map(report => (
                <div key={report.reporting.logId}>
                    <AdminReportUnit report={report}/>
                </div>
            ))}
        </div>
    );

}