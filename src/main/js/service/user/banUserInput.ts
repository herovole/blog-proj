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

    toPayloadHash(): string {
        return JSON.stringify({
            "userId": encodeURIComponent(this.userId.toString()),
            "days": encodeURIComponent(this.days.toString()),
            "requiresAuth": encodeURIComponent(this.requiresAuth.toString()),
            "botDetectionToken": encodeURIComponent(this.botDetectionToken),
        });
    };

}