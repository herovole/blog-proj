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
    const [hasReportAddressed, setHasReportAddressed] = React.useState<boolean>(report.reporting.isHandled);
    const {executeRecaptcha} = useGoogleReCaptcha();
    const googleReCaptchaActionLabel = "adminReportUnit";

    const handleReportAddressed = async (e: React.MouseEvent<HTMLButtonElement>) => {
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
        const input: HandleReportInput = new HandleReportInput(
            report.reporting.logId,
            !hasReportAddressed,
            true,
            recaptchaToken
        )
        const output = await userService.handleReport(input);
        if (output.isSuccessful()) {
            console.info(output.getMessage("Handle Report"));
            setHasReportAddressed(r => !r);
        } else {
            console.error(output.getMessage("Handle Report"));
        }
    }

    return (
        <div key={report.reporting.logId} className="report-form-interior">
            <span>{report.reporting.logId} /</span>
            <span>{report.reporting.reportTimestamp} </span>
            <span>ID:{report.reporting.userId} </span>
            <AdminBanModal
                label="Ban Report User"
                userId={report.reporting.userId}
                userBannedUntil={report.userBannedUntil}
                hasUserBanned={report.hasUserBanned}
                ip={report.reporting.ip}
                ipBannedUntil={report.ipBannedUntil}
                hasIpBanned={report.hasIpBanned}/>
            <div className="report-form-text">{report.reporting.text} </div>
            <button type="button"
                    onClick={handleReportAddressed}>{hasReportAddressed ? "対応済み" : "未対応"}</button>
        </div>
    );
}
