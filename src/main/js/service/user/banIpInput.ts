export class BanIpInput {
    ip: string;
    days: number;
    botDetectionToken: string;

    constructor(ip: string,
                days: number,
                botDetectionToken: string) {
        this.ip = ip;
        this.days = days;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "ip": this.ip,
            "days": this.days.toString(),
            "botDetectionToken": this.botDetectionToken,
        };
    };

}