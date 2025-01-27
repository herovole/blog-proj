export class BanUserInput {
    userId: number;
    days: number;
    botDetectionToken: string;

    constructor(userId: number,
                days: number,
                botDetectionToken: string) {
        this.userId = userId;
        this.days = days;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "userId": this.userId.toString(),
            "days": this.days.toString(),
            "botDetectionToken": this.botDetectionToken,
        };
    };

}