import React from "react";
import {ReportUnit} from "../../../service/user/searchCommentsOutput";
import {AdminBanModal} from "./adminBanModal";
import {UserService} from "../../../service/user/userService";
import {HandleReportInput} from "../../../service/user/handleReportInput";
import {useGoogleReCaptcha} from "react-google-recaptcha-v3";

type AdminReportUnitProps = {
    report: ReportUnit;
}

export const AdminReportUnit: React.FC<AdminReportUnitProps> = ({report}) => {
    const userService: UserService = new UserService();
    const [reportAddressed, setReportAddressed] = React.useState<boolean>(report.isHandled);
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "adminReportUnit";

    const handleReportAddressed = async (e: React.ChangeEvent<HTMLInputElement>) => {
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
        const checked = e.currentTarget.checked;
        setReportAddressed(checked);
        const input: HandleReportInput = new HandleReportInput(
            report.id,
            reportAddressed,
            recaptchaToken
        )
        const output = await userService.handleReport(input);
        if (output.isSuccessful()) {
            console.info(output.getMessage("Handle Report"));
        } else {
            console.error(output.getMessage("Handle Report"));
        }
    }

    return (
        <div key={report.id} className="user-comment-individual">
            <span>{report.id} /</span>
            <span>{report.timestampFiled} </span>
            <span>ID:{report.userId} </span>
            <AdminBanModal
                label="Ban Report User"
                userId={report.userId}
                userBannedUntil={report.userBannedUntil}
                hasUserBanned={report.hasUserBanned}
                ip={report.ip}
                ipBannedUntil={report.ipBannedUntil}
                hasIpBanned={report.hasIpBanned}/>
            <div className="user-comment-text">{report.text} </div>
            <span>対応完了</span>
            <input className="admin-editable-text-activated"
                   type="checkbox"
                   checked={reportAddressed}
                   onChange={handleReportAddressed}
            />
        </div>
    );
}
