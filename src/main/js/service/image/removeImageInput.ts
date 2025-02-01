export class RemoveImageInput {
    private readonly imageName: string;

    constructor(imageName: string) {
        this.imageName = imageName;
    }

    toPayloadHash(): string {
        return JSON.stringify({
            "imageName": this.imageName
        });
    };
}