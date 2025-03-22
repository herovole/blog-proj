import {ArticleSummaryList} from '../../domain/articlelist/articleSummaryList'
import {ArticleSummary} from "../../domain/articlelist/articleSummary";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";

export interface SearchArticlesOutputFields extends BasicApiResultFields {
    content: {
        articles: ReadonlyArray<ArticleSummary>;
        totalArticles: number;
    }
}


export class SearchArticlesOutput extends BasicApiResult {

    static empty() {
        return new SearchArticlesOutput(null);
    }

    constructor(
        fields: SearchArticlesOutputFields | null
    ) {
        super(fields);
    }

    getArticleSummaryList(): ArticleSummaryList {
        const fields: SearchArticlesOutputFields = this.fields as SearchArticlesOutputFields;
        return fields ? new ArticleSummaryList(fields.content.articles) : new ArticleSummaryList([]);
    }

    getLength() {
        const fields: SearchArticlesOutputFields = this.fields as SearchArticlesOutputFields;
        return fields ? fields.content.totalArticles : 0;
    }
}