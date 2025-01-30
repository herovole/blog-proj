export class BanUserInput {
    userId: number;
    days: number;
    requiresAuth: boolean;
    botDetectionToken: string;

    constructor(userId: number,
                days: number,
                requiresAuth: boolean,
                botDetectionToken: string) {
        this.userId = userId;
        this.days = days;
        this.requiresAuth = requiresAuth;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "userId": this.userId.toString(),
            "days": this.days.toString(),
            "requiresAuth": this.requiresAuth.toString(),
            "botDetectionToken": this.botDetectionToken,
        };
    };

}