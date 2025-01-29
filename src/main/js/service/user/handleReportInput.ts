export class HandleReportInput {
    reportId: number;
    isHandled: boolean;
    botDetectionToken: string;

    constructor(reportId: number,
                isHandled: boolean,
                botDetectionToken: string) {
        this.reportId = reportId;
        this.isHandled = isHandled;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "id": this.reportId.toString(),
            "isHandled": this.isHandled.toString(),
            "botDetectionToken": this.botDetectionToken,
        };
    };

}