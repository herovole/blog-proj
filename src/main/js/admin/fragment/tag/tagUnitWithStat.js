
export class TagUnitWithStat {

    static API_KEY_ID = "id";
    static API_KEY_NAME_JP = "tagJapanese";
    static API_KEY_NAME_EN = "tagEnglish";
    static API_KEY_ARTICLES = "articles";
    static API_KEY_LAST_UPDATE = "lastUpdate";

    static fromHash(hash) {
        return new TagUnitWithStat(
            hash[TagUnitWithStat.API_KEY_ID],
            hash[TagUnitWithStat.API_KEY_NAME_JP],
            hash[TagUnitWithStat.API_KEY_NAME_EN],
            hash[TagUnitWithStat.API_KEY_ARTICLES],
            hash[TagUnitWithStat.API_KEY_LAST_UPDATE]
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