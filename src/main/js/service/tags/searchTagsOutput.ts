import {TagUnit, TagUnitFields} from "../../admin/fragment/atomic/tagselectingform/tagUnit";
import {TagUnits} from "../../admin/fragment/atomic/tagselectingform/tagUnits";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";

export interface SearchTagsOutputFields extends BasicApiResultFields {
    content: {
        tagUnits: ReadonlyArray<TagUnitFields>,
        total: number
    }
}

export class SearchTagsOutput extends BasicApiResult {

    constructor(fields: SearchTagsOutputFields) {
        super(fields);
    }

    getTagUnits(): TagUnits {
        const fields: SearchTagsOutputFields = this.fields as SearchTagsOutputFields;
        return new TagUnits(fields.content.tagUnits.map(tagUnitFields => new TagUnit(tagUnitFields)));
    }
}