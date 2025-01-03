import {ArticleSummaryList} from './articleSummaryList'

export class SearchArticlesOutput {

    static readonly API_KEY_ARTICLES: string = "articles";
    static readonly API_KEY_TOTAL_ARTICLES: string = "totalArticles";

    static fromHash(hash: { [key: string]: string }) {
        return new SearchArticlesOutput(
            ArticleSummaryList.fromHash(hash[SearchArticlesOutput.API_KEY_ARTICLES]),
            Number(hash[SearchArticlesOutput.API_KEY_TOTAL_ARTICLES])
        )
    }

    static empty() {
        return new SearchArticlesOutput(ArticleSummaryList.empty(), 0);
    }

    articleSummaries: ArticleSummaryList;
    totalArticles: number;

    constructor(
        articleSummaries: ArticleSummaryList,
        totalArticles: number
    ) {
        this.articleSummaries = articleSummaries;
        this.totalArticles = totalArticles;
    }
}