import {FilteringResultOutput, FilteringResultOutputFields} from "../../../domain/filteringResultOutput";

export interface AdminLoginOutputFields {
    isSuccessful: boolean;
    postResponseBody: FilteringResultOutputFields;
}

export class AdminLoginOutput {

    private readonly fields: AdminLoginOutputFields;

    constructor(fields: AdminLoginOutputFields) {
        this.fields = fields;
    }

    isSuccessful(): boolean {
        return this.fields.isSuccessful;
    }

    getMessage(prefix: string): string {
        return new FilteringResultOutput(this.fields.postResponseBody).getMessage(prefix);
    }

}