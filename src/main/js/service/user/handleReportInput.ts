export class HandleReportInput {
    id: number;
    isHandled: boolean;
    botDetectionToken: string;

    constructor(id: number,
                isHandled: boolean,
                botDetectionToken: string) {
        this.id = id;
        this.isHandled = isHandled;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "id": this.id.toString(),
            "isHandled": this.isHandled.toString(),
            "botDetectionToken": this.botDetectionToken,
        };
    };

}