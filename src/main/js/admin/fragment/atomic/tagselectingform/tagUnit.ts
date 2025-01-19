export interface TagUnitFields {
    id: string;
    tagJapanese: string;
    tagEnglish: string;
    articles: number;
    lastUpdate: string;
}

export class TagUnit {

    static empty(): TagUnit {
        return new TagUnit({["id"]: "", ["tagJapanese"]: "", ["tagEnglish"]: "", ["articles"]: 0, ["lastUpdate"]: ""})
    }

    fields: TagUnitFields;

    constructor(fields: TagUnitFields) {
        this.fields = fields;
    }

    getTagOptionJapanese(): { [key: string]: string } {
        return {value: this.fields.id.toString(), label: this.fields.tagJapanese};
    }

    getTagOptionEnglish(): { [key: string]: string } {
        return {value: this.fields.id.toString(), label: this.fields.tagEnglish};
    }

}