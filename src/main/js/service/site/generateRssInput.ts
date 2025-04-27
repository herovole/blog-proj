export class GenerateRssInput {
    private readonly rssType: string;

    constructor(rssType: string) {
        this.rssType = rssType;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "rssType": encodeURIComponent(this.rssType.toString()),
            "requiresAuth": encodeURIComponent("true")
        };
    };
}