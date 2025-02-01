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
            "ip": this.ip,
            "days": this.days.toString(),
            "requiresAuth": this.requiresAuth.toString(),
            "botDetectionToken": this.botDetectionToken,
        });
    };

}