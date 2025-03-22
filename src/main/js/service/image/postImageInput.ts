export class PostImageInput {
    image: File;

    constructor(image: File) {
        this.image = image;
    }

    toPayloadHash(): { [key: string]: File } {
        return {
            "image": this.image
        };
    };
}