import {TagUnit} from './tagUnit'

export class TagUnits {

    static empty(): TagUnits {
        return new TagUnits([]);
    }

    tagUnits: ReadonlyArray<TagUnit>;

    constructor(arrayListOfTagUnits: ReadonlyArray<TagUnit>) {
        this.tagUnits = arrayListOfTagUnits || [];
    }

    isEmpty(): boolean {
        return this.tagUnits.length === 0;
    }

    getTagUnit(id: string): TagUnit {
        const candidate: TagUnit = this.tagUnits.filter(e => e.fields.id === id)[0];
        return candidate || TagUnit.empty();
    }

    getTagOptionsJapanese(): ReadonlyArray<{ [key: string]: string }> {
        return this.tagUnits.map(e => e.getTagOptionJapanese());
    }

    getTagOptionsJapaneseSelected(arrayIds: ReadonlyArray<string>): ReadonlyArray<{ [key: string]: string }> {
        return this.tagUnits.filter(e => arrayIds.includes(e.fields.id)).map(e => e.getTagOptionJapanese());
    }

    getTagOptionsEnglish(): ReadonlyArray<{ [key: string]: string }> {
        return this.tagUnits.map(e => e.getTagOptionEnglish());
    }

    getTagOptionsEnglishSelected(arrayIds: ReadonlyArray<string>): ReadonlyArray<{ [key: string]: string }> {
        return this.tagUnits.filter(e => arrayIds.includes(e.fields.id)).map(e => e.getTagOptionEnglish());
    }

    getJapaneseNamesByIdsForDisplay(ids: ReadonlyArray<string>): string {
        const result: Array<string> = [];
        for (const e of this.tagUnits) {
            if (ids.includes(e.fields.id)) {
                result.push(e.fields.tagJapanese);
            }
        }
        return result.join(",");
    }

    getEnglishNamesByIdsForDisplay(ids: ReadonlyArray<string>): string {
        const result: Array<string> = [];
        for (const e of this.tagUnits) {
            if (ids.includes(e.fields.id)) {
                result.push(e.fields.tagEnglish);
            }
        }
        return result.join(",");
    }
}