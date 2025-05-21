import {TagUnit} from './tagUnit'

export class TagUnits {

    static ofJson(json: string | null): TagUnits {
        if (json == null) return new TagUnits(new Array<TagUnit>());
        const field: ReadonlyArray<TagUnit> = Object.assign(new Array<TagUnit>(), JSON.parse(json));
        return new TagUnits(field);
    }

    static empty(): TagUnits {
        return new TagUnits([]);
    }

    tagUnits: ReadonlyArray<TagUnit>;

    constructor(arrayListOfTagUnits: ReadonlyArray<TagUnit>) {
        this.tagUnits = arrayListOfTagUnits || [];
    }

    stringify(): string {
        return JSON.stringify(this.tagUnits);
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

    getJapaneseNameByIdForDisplay(id: string): string | null {
        for (const e of this.tagUnits) {
            if (id.includes(e.fields.id)) {
                return e.fields.tagJapanese;
            }
        }
        return null;
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