import { TagUnit } from './tagUnit';
export class TagUnitList {
    static API_KEY = "tagUnits";
    static fromHash(hash) {
        const arrayListOfTagUnits = hash[TagUnitList.API_KEY].map(hash => TagUnit.fromHash(hash));
        return new TagUnitList(arrayListOfTagUnits);
    }
    static empty() {
        return new TagUnitList([]);
    }
    tagUnits;
    constructor(arrayListOfTagUnits) {
        this.tagUnits = arrayListOfTagUnits || [];
    }
    getTagUnit(id) {
        const candidate = this.tagUnits.filter(e => e.id === id)[0];
        return candidate || TagUnit.empty();
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
        const result = [];
        for (const e of this.tagUnits) {
            if (ids.includes(e.id)) {
                result.push(e.nameJp);
            }
        }
        return result.join(",");
    }
    getEnglishNamesByIdsForDisplay(ids) {
        const result = [];
        for (const e of this.tagUnits) {
            if (ids.includes(e.id)) {
                result.push(e.nameEn);
            }
        }
        return result.join(",");
    }
}
