import {TagUnitWithStatList} from './tagUnitWithStatList';


export class SearchTopicTagsOutput {

    static API_KEY_UNITS = "tagUnits";
    static API_KEY_TOTAL = "total";

    static fromHash(hash) {
        return new SearchTopicTagsOutput(
            TagUnitWithStatList.fromHash(hash[SearchTopicTagsOutput.API_KEY_UNITS]),
            hash[SearchTopicTagsOutput.API_KEY_TOTAL]
        )
    }

    static empty() {
        return new SearchTopicTagsOutput(TagUnitWithStatList.empty(), 0);
    }

    constructor(
        tagUnits,
        total
    ) {
        this.tagUnits = tagUnits;
        this.total = total;
    }
}