export class GenerateRssInput {
    private readonly version: number;

    constructor(version: number) {
        this.version = version;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "version": encodeURIComponent(this.version.toString()),
            "requiresAuth": encodeURIComponent("true")
        };
    };
}