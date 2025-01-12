import {ArticleSummary} from './articleSummary'

export class ArticleSummaryList {

    static empty() {
        return new ArticleSummaryList([]);
    }

    list: ReadonlyArray<ArticleSummary>;

    constructor(
        list: ReadonlyArray<ArticleSummary>
    ) {
        this.list = list;
    }

    getElements(): ReadonlyArray<ArticleSummary> {
        console.log(this.list.length);
        console.log(this.list[0]);
        console.log(this.list[1]);
        return Array.from(this.list);
    }
}
