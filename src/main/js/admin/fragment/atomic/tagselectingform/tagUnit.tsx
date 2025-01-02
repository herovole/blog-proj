export class TagUnit {

    static readonly API_KEY_ID: string = "id";
    static readonly API_KEY_NAME_JP: string = "tagJapanese";
    static readonly API_KEY_NAME_EN: string = "tagEnglish";

    static fromHash(hash: { [key: string]: string }) {
        return new TagUnit(
            Number(hash[TagUnit.API_KEY_ID]),
            hash[TagUnit.API_KEY_NAME_JP],
            hash[TagUnit.API_KEY_NAME_EN]
        );
    }

    static empty() {
        return new TagUnit(-1, "", "");
    }

    id: number;
    nameJp: string;
    nameEn: string;

    constructor(id: number, nameJp: string, nameEn: string) {
        this.id = id;
        this.nameJp = nameJp;
        this.nameEn = nameEn;
    }

    getTagOptionJapanese(): { [key: string]: string } {
        return {value: this.id.toString(), label: this.nameJp};
    }

    getTagOptionEnglish(): { [key: string]: string } {
        return {value: this.id.toString(), label: this.nameEn};
    }
}