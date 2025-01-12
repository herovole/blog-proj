export interface TagUnitFields {
    id: string;
    tagJapanese: string;
    tagEnglish: string;
}

export class TagUnit {

    static empty(): TagUnit {
        return new TagUnit({["id"]: "", ["tagJapanese"]: "", ["tagEnglish"]: ""})
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