
export class TagUnit {

    static API_KEY_ID = "id";
    static API_KEY_NAME_JP = "tagJapanese";
    static API_KEY_NAME_EN = "tagEnglish";

    static fromJsonString(jsonString) {
        //var tagUnit = Object.assign(new TagUnit(), JSON.parse(jsonString));
        var parsedHash = JSON.parse(jsonString);
        return new TagUnit(
            parsedHash[TagUnit.API_KEY_ID],
            parsedHash[TagUnit.API_KEY_NAME_JP],
            parsedHash[TagUnit.API_KEY_NAME_EN]
        );
    }

    constructor(id, nameJp, nameEn) {
        this.id = id;
        this.nameJp = nameJp;
        this.nameEn = nameEn;
    }

    getTagOptionJapanese() {
        return { value: this.id, label: this.nameJp};
    }

    getTagOptionEnglish() {
        return { value: this.id, label: this.nameEn};
    }
}