import {TagUnit} from './tagUnit'

export class TagUnits {

    static readonly API_KEY: string = "tagUnits";

    static fromHash(hash: { [key: string]: ReadonlyArray<{ [key: string]: string }> }): TagUnits {
        const arrayListOfTagUnits: ReadonlyArray<TagUnit> = hash[TagUnits.API_KEY].map(hash => TagUnit.fromHash(hash));
        return new TagUnits(arrayListOfTagUnits);
    }

    static empty(): TagUnits {
        return new TagUnits([]);
    }

    tagUnits: ReadonlyArray<TagUnit>;

    constructor(arrayListOfTagUnits: ReadonlyArray<TagUnit>) {
        this.tagUnits = arrayListOfTagUnits || [];
    }

    getTagUnit(id: string): TagUnit {
        const candidate: TagUnit = this.tagUnits.filter(e => e.id === id)[0];
        return candidate || TagUnit.empty();
    }

    getTagOptionsJapanese(): ReadonlyArray<{ [key: string]: string }> {
        return this.tagUnits.map(e => e.getTagOptionJapanese());
    }

    getTagOptionsJapaneseSelected(arrayIds: ReadonlyArray<string>): ReadonlyArray<{ [key: string]: string }> {
        return this.tagUnits.filter(e => arrayIds.includes(e.id)).map(e => e.getTagOptionJapanese());
    }

    getTagOptionsEnglish(): ReadonlyArray<{ [key: string]: string }> {
        return this.tagUnits.map(e => e.getTagOptionEnglish());
    }

    getTagOptionsEnglishSelected(arrayIds: ReadonlyArray<string>): ReadonlyArray<{ [key: string]: string }> {
        return this.tagUnits.filter(e => arrayIds.includes(e.id)).map(e => e.getTagOptionEnglish());
    }

    getJapaneseNamesByIdsForDisplay(ids: ReadonlyArray<string>): string {
        const result: Array<string> = [];
        for (const e of this.tagUnits) {
            if (ids.includes(e.id)) {
                result.push(e.nameJp);
            }
        }
        return result.join(",");
    }

    getEnglishNamesByIdsForDisplay(ids: ReadonlyArray<string>): string {
        const result: Array<string> = [];
        for (const e of this.tagUnits) {
            if (ids.includes(e.id)) {
                result.push(e.nameEn);
            }
        }
        return result.join(",");
    }
}