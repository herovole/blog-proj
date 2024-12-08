import {TagUnitWithStat} from './tagUnitWithStat.js'

export class TagUnitWithStatList {

    static fromHash(hash) {
        var arrayListOfTagUnits = hash.map(hash => TagUnitWithStat.fromHash(hash));
        return new TagUnitWithStatList(arrayListOfTagUnits);
    }

    static empty() {
        return new TagUnitWithStatList([]);
    }

    constructor(arrayListOfTagUnits) {
        this.tagUnits = arrayListOfTagUnits ? arrayListOfTagUnits : [];
    }
}