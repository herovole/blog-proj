import axios from "axios";
import {FindArticleInput} from "./findArticleInput";
import {FindArticleOutput, FindArticleOutputFields} from "./findArticleOutput";
import {SearchArticlesOutput, SearchArticlesOutputFields} from "./searchArticlesOutput";
import {SearchArticlesInput} from "./searchArticlesInput";

export class ArticleService {

    async findArticle(input: FindArticleInput): Promise<FindArticleOutput> {
        const response = await axios.get(
            "/api/v1/articles/" + input.articleId, {
                headers: {Accept: 'application/json',},
            }
        );
        return new FindArticleOutput(response.data as FindArticleOutputFields);
    }

    async searchArticles(input: SearchArticlesInput): Promise<SearchArticlesOutput> {
        const response = await axios.get("/api/v1/articles", {
            params: input.toPayloadHash(),
            headers: {Accept: "application/json"},
        });
        return new SearchArticlesOutput(response.data as SearchArticlesOutputFields);
    }
}