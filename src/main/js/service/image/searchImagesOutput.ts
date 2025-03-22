import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";

export interface SearchImagesOutputFields extends BasicApiResultFields {
    content: {
        files: ReadonlyArray<{ fileName: string, registrationTimestamp: string }>,
        total: number
    }
}

export class SearchImagesOutput extends BasicApiResult {

    static empty(): SearchImagesOutput {
        return new SearchImagesOutput(null);
    }

    constructor(fields: SearchImagesOutputFields | null) {
        super(fields);
    }

    isEmpty = (): boolean => {
        return !this.fields;
    }

    getTotal = (): number => {
        if (!this.fields) return 0;
        const fields: SearchImagesOutputFields = this.fields as SearchImagesOutputFields;
        return fields.content.total;
    }

    getFiles = (): ReadonlyArray<{ fileName: string, registrationTimestamp: string }> => {
        if (!this.fields) return [];
        const fields: SearchImagesOutputFields = this.fields as SearchImagesOutputFields;
        return fields.content.files;
    }
}