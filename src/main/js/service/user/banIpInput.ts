export class BanIpInput {
    ip: string;
    days: number;
    requiresAuth: boolean;
    botDetectionToken: string;

    constructor(ip: string,
                days: number,
                requiresAuth: boolean,
                botDetectionToken: string) {
        this.ip = ip;
        this.days = days;
        this.requiresAuth = requiresAuth;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): string {
        return JSON.stringify({
            "ip": encodeURIComponent(this.ip),
            "days": encodeURIComponent(this.days.toString()),
            "requiresAuth": encodeURIComponent(this.requiresAuth.toString()),
            "botDetectionToken": encodeURIComponent(this.botDetectionToken),
        });
    };

}