
class TagUnit {

    static fromJsonString(String jsonString) {
        parsedData = JSON.parse(jsonString);
        tagUnit = Object.assign(new TagUnit(), parsedData);
        return tagUnit;
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