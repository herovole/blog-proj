import {Article} from "../../domain/article";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";

export interface FindArticleOutputFields extends BasicApiResultFields {
    content: Article;
}

export class FindArticleOutput extends BasicApiResult {

    static empty(): FindArticleOutput {
        return new FindArticleOutput(null);
    }

    constructor(fields: FindArticleOutputFields | null) {
        super(fields);
    }

    public getArticle(): Article {
        return (this.fields as FindArticleOutputFields).content;
    }
}
