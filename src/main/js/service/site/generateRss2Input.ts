export class GenerateRss2Input {

    toPayloadHash(): { [key: string]: string } {
        return {
            "requiresAuth": encodeURIComponent("true")
        };
    };
}