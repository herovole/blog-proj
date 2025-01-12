import {TagUnit, TagUnitFields} from "../../admin/fragment/atomic/tagselectingform/tagUnit";
import {TagUnits} from "../../admin/fragment/atomic/tagselectingform/tagUnits";

export interface SearchTagsOutputFields {
    tagUnits: ReadonlyArray<TagUnitFields>;
}

export class SearchTagsOutput {
    fields: SearchTagsOutputFields;

    constructor(fields: SearchTagsOutputFields) {
        this.fields = fields;
    }

    getTagUnits(): TagUnits {
        return new TagUnits(this.fields.tagUnits.map(tagUnitFields => new TagUnit(tagUnitFields)));
    }
}