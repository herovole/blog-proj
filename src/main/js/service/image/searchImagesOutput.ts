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

    getTotal = (): number => {
        const fields: SearchImagesOutputFields = this.fields as SearchImagesOutputFields;
        return fields.content.total;
    }

    getFiles = (): ReadonlyArray<{ fileName: string, registrationTimestamp: string }> => {
        const fields: SearchImagesOutputFields = this.fields as SearchImagesOutputFields;
        return fields.content.files;
    }
}