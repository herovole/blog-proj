export class RemoveImageInput {
    private readonly imageName: string;

    constructor(imageName: string) {
        this.imageName = imageName;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "imageName": encodeURIComponent(this.imageName),
            "requiresAuth": encodeURIComponent("true")
        };
    };
}