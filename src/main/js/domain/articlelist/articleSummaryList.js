import {ArticleSummary} from './articleSummary'

export class ArticleSummaryList {

    static API_KEY = "articles";

    static fromHash(hash) {
        const list = [];
        for (let e of hash[ArticleSummaryList.API_KEY]) {
            console.log(JSON.stringify(e));
            list.push(ArticleSummary.fromHash(e));
        }
        return new ArticleSummaryList(list);
    }

    static empty() {
        return new ArticleSummaryList([]);
    }

    constructor(
        list
    ) {
        this.list = list;
    }
}
