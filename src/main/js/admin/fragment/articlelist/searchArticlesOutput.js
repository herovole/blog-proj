import {ArticleSummaryList} from './articleSummaryList'

export class SearchArticlesOutput {

    static API_KEY_ARTICLES = "articles";
    static API_KEY_TOTAL_ARTICLES = "totalArticles";

    static fromHash(hash) {
        return new SearchArticlesOutput(
            ArticleSummaryList.fromHash(hash[SearchArticlesOutput.API_KEY_ARTICLES]),
            hash[SearchArticlesOutput.API_KEY_TOTAL_ARTICLES]
        )
    }

    static empty() {
        return new SearchArticlesOutput(ArticleSummaryList.empty(), 0);
    }

    constructor(
        articleSummaries,
        totalArticles
    ) {
        this.articleSummaries = articleSummaries;
        this.totalArticles = totalArticles;
    }
}