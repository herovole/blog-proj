export class CreateAdminUserInput {

    private readonly handle: string;
    private readonly role: string;
    private readonly password: string;
    private readonly botDetectionToken: string;

    constructor(handle: string, role:string, password: string, botDetectionToken: string) {
        this.handle = handle;
        this.role = role;
        this.password = password;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "userName": this.handle,
            "role": this.role,
            "password": this.password,
            "botDetectionToken": this.botDetectionToken
        };
    };
}