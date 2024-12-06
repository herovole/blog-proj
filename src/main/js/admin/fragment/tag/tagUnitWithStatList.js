import {TagUnit} from './tagUnit.js'

export class TagUnitWithStatList {

    static API_KEY = "tagUnits";

    static fromHash(hash) {
        var arrayListOfTagUnits = hash[TagUnitWithStatList.API_KEY].map(hash => TagUnitWithStat.fromHash(hash));
        return new TagUnitWithStatList(arrayListOfTagUnits);
    }

    static empty() {
        return new TagUnitWithStatList([]);
    }

    constructor(arrayListOfTagUnits) {
        this.tagUnits = arrayListOfTagUnits ? arrayListOfTagUnits : [];
    }
}