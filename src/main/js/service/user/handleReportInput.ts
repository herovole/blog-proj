export class HandleReportInput {
    reportId: number;
    isHandled: boolean;
    requiresAuth: boolean;
    botDetectionToken: string;

    constructor(reportId: number,
                isHandled: boolean,
                requiresAuth: boolean,
                botDetectionToken: string) {
        this.reportId = reportId;
        this.isHandled = isHandled;
        this.requiresAuth = requiresAuth;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): string {
        return JSON.stringify({
            "reportId": this.reportId.toString(),
            "isHandled": this.isHandled.toString(),
            "requiresAuth": this.requiresAuth.toString(),
            "botDetectionToken": this.botDetectionToken,
        });
    };

}