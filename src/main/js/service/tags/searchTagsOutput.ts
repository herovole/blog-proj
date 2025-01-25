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

    static empty(): SearchTagsOutput {
        return new SearchTagsOutput(null);
    }

    constructor(fields: SearchTagsOutputFields | null) {
        super(fields);
    }

    getTagUnits(): TagUnits {
        if (!this.fields) return TagUnits.empty();
        const fields: SearchTagsOutputFields = this.fields as SearchTagsOutputFields;
        return new TagUnits(fields.content.tagUnits.map(tagUnitFields => new TagUnit(tagUnitFields)));
    }

    getTotalNumber(): number {
        if (!this.fields) return 0;
        const fields: SearchTagsOutputFields = this.fields as SearchTagsOutputFields;
        return fields.content.total;
    }
}