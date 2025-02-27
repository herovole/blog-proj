import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";

export interface GetResourcePrefixOutputFields extends BasicApiResultFields {
    content: {
        prefix: string
    }
}

export class GetResourcePrefixOutput extends BasicApiResult {

    static empty(): GetResourcePrefixOutput {
        return new GetResourcePrefixOutput(null);
    }

    constructor(fields: GetResourcePrefixOutputFields | null) {
        super(fields);
    }

    isEmpty = (): boolean => {
        return !this.fields;
    }

    getPrefix = (): string => {
        if (!this.fields) return "";
        const fields: GetResourcePrefixOutputFields = this.fields as GetResourcePrefixOutputFields;
        return fields.content.prefix;
    }
}