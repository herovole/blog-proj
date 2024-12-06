
export class TagUnitWithStat {

    static API_KEY_ID = "id";
    static API_KEY_NAME_JP = "tagJapanese";
    static API_KEY_NAME_EN = "tagEnglish";
    static API_KEY_ARTICLES = "articles";
    static API_KEY_LAST_UPDATE = "lastUpdate";

    static fromHash(hash) {
        return new TagUnit(
            hash[TagUnit.API_KEY_ID],
            hash[TagUnit.API_KEY_NAME_JP],
            hash[TagUnit.API_KEY_NAME_EN],
            hash[TagUnit.API_KEY_ARTICLES],
            hash[TagUnit.API_KEY_LAST_UPDATE]
        );
    }

    constructor(id, nameJp, nameEn, articles, lastUpdate) {
        this.id = id;
        this.nameJp = nameJp;
        this.nameEn = nameEn;
        this.articles = articles;
        this.lastUpdate = lastUpdate;
    }

}