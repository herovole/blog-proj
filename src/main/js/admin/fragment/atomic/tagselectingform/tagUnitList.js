import {TagUnit} from './tagUnit.js'

export class TagUnitList {

    static API_KEY = "tagUnits";

    static fromHash(hash) {
        var arrayListOfTagUnits = hash[TagUnitList.API_KEY].map(hash => TagUnit.fromHash(hash));
        return new TagUnitList(arrayListOfTagUnits);
    }

    static empty() {
        return new TagUnitList([]);
    }

    constructor(arrayListOfTagUnits) {
        this.tagUnits = arrayListOfTagUnits ? arrayListOfTagUnits : [];
    }

    getTagOptionsJapanese() {
        return this.tagUnits.map(e => e.getTagOptionJapanese());
    }

    getTagOptionsJapaneseSelected(arrayIds) {
        return this.tagUnits.filter(e => arrayIds.includes(e.id)).map(e => e.getTagOptionJapanese());
    }

    getTagOptionsEnglish() {
        return this.tagUnits.map(e => e.getTagOptionEnglish());
    }

    getTagOptionsEnglishSelected(arrayIds) {
        return this.tagUnits.filter(e => arrayIds.includes(e.id)).map(e => e.getTagOptionEnglish());
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