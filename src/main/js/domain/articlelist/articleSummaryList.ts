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
        return Array.from(this.list);
    }
}
