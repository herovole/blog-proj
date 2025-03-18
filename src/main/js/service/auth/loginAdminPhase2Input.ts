export class LoginAdminPhase2Input {

    private readonly handle: string;
    private readonly password: string;
    private readonly verificationCode: string;
    private readonly botDetectionToken: string;

    constructor(handle: string, password: string, verificationCode: string, botDetectionToken: string) {
        this.handle = handle;
        this.password = password;
        this.verificationCode = verificationCode;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): string {
        return JSON.stringify({
            "userName": encodeURIComponent(this.handle),
            "password": encodeURIComponent(this.password),
            "verificationCode": encodeURIComponent(this.verificationCode),
            "botDetectionToken": encodeURIComponent(this.botDetectionToken)
        });
    };
}