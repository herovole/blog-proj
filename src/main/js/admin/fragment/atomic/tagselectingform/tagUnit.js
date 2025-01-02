export class TagUnit {
    static API_KEY_ID = "id";
    static API_KEY_NAME_JP = "tagJapanese";
    static API_KEY_NAME_EN = "tagEnglish";
    static fromHash(hash) {
        return new TagUnit(Number(hash[TagUnit.API_KEY_ID]), hash[TagUnit.API_KEY_NAME_JP], hash[TagUnit.API_KEY_NAME_EN]);
    }
    static empty() {
        return new TagUnit(-1, "", "");
    }
    id;
    nameJp;
    nameEn;
    constructor(id, nameJp, nameEn) {
        this.id = id;
        this.nameJp = nameJp;
        this.nameEn = nameEn;
    }
    getTagOptionJapanese() {
        return { value: this.id.toString(), label: this.nameJp };
    }
    getTagOptionEnglish() {
        return { value: this.id.toString(), label: this.nameEn };
    }
}
