import {TagUnit, TagUnitFields} from "../../admin/fragment/atomic/tagselectingform/tagUnit";
import {TagUnits} from "../../admin/fragment/atomic/tagselectingform/tagUnits";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";

export interface SearchTagsOutputFields extends BasicApiResultFields {
    content: ReadonlyArray<TagUnitFields>;
}

export class SearchTagsOutput extends BasicApiResult {

    constructor(fields: SearchTagsOutputFields) {
        super(fields);
    }

    getTagUnits(): TagUnits {
        const fields: SearchTagsOutputFields = this.fields as SearchTagsOutputFields;
        return new TagUnits(fields.content.map(tagUnitFields => new TagUnit(tagUnitFields)));
    }
}