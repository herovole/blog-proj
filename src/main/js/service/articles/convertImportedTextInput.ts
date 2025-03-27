export class ConvertImportedTextInput {
    importedSourceComments: string;

    constructor(importedSourceComments: string) {
        this.importedSourceComments = importedSourceComments;
    }

    toPayloadHash(): { [key: string]: string } {
        return {
            "importedSourceComments": encodeURIComponent(this.importedSourceComments.toString()),
            "requiresAuth": encodeURIComponent("1"),
        };
    };
}