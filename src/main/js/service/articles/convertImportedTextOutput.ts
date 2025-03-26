import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {SourceCommentUnit} from "../../domain/comment/sourceCommentUnit";

export interface ConvertImportedTextOutputFields extends BasicApiResultFields {
    content: ReadonlyArray<SourceCommentUnit>;
}

export class ConvertImportedTextOutput extends BasicApiResult {

    static empty(): ConvertImportedTextOutput {
        return new ConvertImportedTextOutput(null);
    }

    constructor(fields: ConvertImportedTextOutputFields | null) {
        super(fields);
    }

    public getSourceComments(): ReadonlyArray<SourceCommentUnit> {
        return (this.fields as ConvertImportedTextOutputFields).content;
    }
}
