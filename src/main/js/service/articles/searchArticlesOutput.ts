import {ArticleSummaryList} from '../../domain/articlelist/articleSummaryList'
import {ArticleSummary} from "../../domain/articlelist/articleSummary";

export interface SearchArticlesOutputFields {
    articles: ReadonlyArray<ArticleSummary>;
    totalArticles: number;
}


export class SearchArticlesOutput {

    static empty() {
        return new SearchArticlesOutput(null);
    }

    fields: SearchArticlesOutputFields | null;

    constructor(
        fields: SearchArticlesOutputFields | null
    ) {
        this.fields = fields;
    }

    getArticleSummaryList(): ArticleSummaryList {
        return this.fields ? new ArticleSummaryList(this.fields.articles) : new ArticleSummaryList([]);
    }

    getLength() {
        return this.fields ? this.fields.totalArticles : 0;
    }
}