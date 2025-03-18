export class CreateAdminUserInput {

    private readonly handle: string;
    private readonly email: string;
    private readonly role: string;
    private readonly password: string;
    private readonly requiresAuth: boolean;
    private readonly botDetectionToken: string;

    constructor(handle: string, email: string, role: string, password: string, requiresAuth: boolean, botDetectionToken: string) {
        this.handle = handle;
        this.email = email;
        this.role = role;
        this.password = password;
        this.requiresAuth = requiresAuth;
        this.botDetectionToken = botDetectionToken;
    }

    toPayloadHash(): string {
        return JSON.stringify({
            "userName": encodeURIComponent(this.handle),
            "email": encodeURIComponent(this.email),
            "role": encodeURIComponent(this.role),
            "password": encodeURIComponent(this.password),
            "requiresAuth": encodeURIComponent(this.requiresAuth.toString()),
            "botDetectionToken": encodeURIComponent(this.botDetectionToken)
        });
    };
}