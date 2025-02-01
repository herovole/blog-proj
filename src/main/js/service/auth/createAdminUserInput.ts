export class CreateAdminUserInput {

    private readonly handle: string;
    private readonly role: string;
    private readonly password: string;
    private readonly requiresAuth: boolean;
    private readonly botDetectionToken: string;

    constructor(handle: string, role: string, password: string, requiresAuth: boolean, botDetectionToken: string) {
        this.handle = handle;
        this.role = role;
        this.password = password;
        this.requiresAuth = requiresAuth;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): string {
        return JSON.stringify({
            "userName": this.handle,
            "role": this.role,
            "password": this.password,
            "requiresAuth": this.requiresAuth.toString(),
            "botDetectionToken": this.botDetectionToken
        });
    };
}