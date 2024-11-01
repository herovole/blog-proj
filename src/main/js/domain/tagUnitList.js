import {TagUnit} from './tagUnit.js'

export class TagUnitList {

    static fromJsonStringList(arrayJsonStringList) {
        var arrayListOfTagUnits = arrayJsonStringList.map(TagUnit.fromJsonString);
        return new TagUnitList(arrayListOfTagUnits);
    }

    static empty() {
        return new TagUnitList([]);
    }

    constructor(arrayListOfTagUnits) {
        this.tagUnits = arrayListOfTagUnits;
    }

    getTagOptionsJapanese() {
        return this.tagUnits.map(TagUnit.getTagOptionJapanese);
    }

    getTagOptionsEnglish() {
        return this.tagUnits.map(TagUnit.getTagOptionEnglish);
    }

    getJapaneseNamesByIdsForDisplay(ids) {
        var result = [];
        for(const e of this.tagUnits) {
            if(ids.includes(e.id)) {
                result.push(e.nameJp);
            }
        }
        return result.join(",");
    }

    getEnglishNamesByIdsForDisplay(ids) {
        var result = [];
        for(const e of this.tagUnits) {
            if(ids.includes(e.id)) {
                result.push(e.nameEn);
            }
        }
        return result.join(",");
    }
}