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
            "reportId": encodeURIComponent(this.reportId.toString()),
            "isHandled": encodeURIComponent(this.isHandled.toString()),
            "requiresAuth": encodeURIComponent(this.requiresAuth.toString()),
            "botDetectionToken": encodeURIComponent(this.botDetectionToken),
        });
    };

}