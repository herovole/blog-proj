

class TagUnitList {

    static fromJsonStringList(arrayJsonStringList) {
        arrayListOfTagUnits = arrayJsonStringList.map(TagUnit.fromJsonString);
        return new TagUnitList(arrayListOfTagUnits);
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
}