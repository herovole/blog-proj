import React from "react";
import {BooleanSelectingForm} from "../atomic/booleanSelectingForm";
import {ReportUnit} from "../../../service/user/searchCommentsOutput";

type AdminReportUnitProps = {
    report: ReportUnit;
}

export const AdminReportUnit: React.FC<AdminReportUnitProps> = ({report}) => {

    return (
        <div key={report.id} className="user-comment-individual">
            <span>{report.id} /</span>
            <span>{report.timestampFiled} </span>
            <span>ID:{report.userId} </span>
            <button type="button" onClick={}>
                報告者とIPをBAN
            </button>
            <div className="user-comment-text">{report.text} </div>
            <span>対応完了</span>
            <BooleanSelectingForm postKey={new RootElementId()}>
                {report.isHandled}
            </BooleanSelectingForm>
            <button type="button" onClick={}>更新</button>
        </div>
    );
}
