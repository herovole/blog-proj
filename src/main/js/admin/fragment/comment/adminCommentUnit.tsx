import React from "react";
import {CommentAndReport} from "../../../service/user/searchCommentsOutput";
import {PublicUserCommentViewUnit} from "../../../public/fragment/article/usercomment/publicUserCommentViewUnit";
import {AdminReportUnit} from "./adminReportUnit";

type AdminCommentUnitProps = {
    content: CommentAndReport;
}

export const AdminCommentUnit: React.FC<AdminCommentUnitProps> = ({content}) => {
    const [hidesComment, setHidesComment] = React.useState<boolean>(content.comment.body.isHidden);

    const handleHidesComment = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();
        setHidesComment(e.currentTarget.checked);
    }

    return (
        <div className="user-comment-individual">
            <div className="comment-form-header">article-{content.article.id} : {content.article.title}</div>
            <span>コメントを非表示</span>
            <input className="admin-editable-text-activated"
                   type="checkbox"
                   checked={hidesComment}
                   onChange={handleHidesComment}
            />
            <button className="report-button" type="button" onClick={}>
                コメントユーザーとIPをBAN
            </button>

            <PublicUserCommentViewUnit content={content.comment}/>
            {content.reports.map(report => (
                <div key={report.id}>
                    <AdminReportUnit report={report}/>
                </div>
            ))}
        </div>
    );

}