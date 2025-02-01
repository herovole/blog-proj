export class AdminLoginInput {

    private readonly handle: string;
    private readonly password: string;
    private readonly botDetectionToken: string;

    constructor(handle: string, password: string, botDetectionToken: string) {
        this.handle = handle;
        this.password = password;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): string {
        return JSON.stringify({
            "userName": this.handle,
            "password": this.password,
            "botDetectionToken": this.botDetectionToken
        });
    };
}