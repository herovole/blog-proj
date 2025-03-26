import axios, {AxiosResponse} from "axios";
import {FindArticleInput} from "./findArticleInput";
import {FindArticleOutput, FindArticleOutputFields} from "./findArticleOutput";
import {SearchArticlesOutput, SearchArticlesOutputFields} from "./searchArticlesOutput";
import {SearchArticlesInput} from "./searchArticlesInput";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {VisitArticleInput} from "./visitArticleInput";
import {ConvertImportedTextOutput, ConvertImportedTextOutputFields} from "./convertImportedTextOutput";
import {ConvertImportedTextInput} from "./convertImportedTextInput";

export class ArticleService {

    async findArticle(input: FindArticleInput): Promise<FindArticleOutput> {
        try {
            const response: AxiosResponse<FindArticleOutputFields> = await axios.get(
                "/api/v1/articles/" + input.articleId, {
                    params: input.toPayloadHash(),
                    headers: {Accept: 'application/json'},
                }
            );
            return new FindArticleOutput(response.data);
        } catch (e: unknown) {
            console.error("Error requesting data");
            if (axios.isAxiosError(e) && e.response) {
                return new FindArticleOutput(e.response.data);
            }
        }
        return FindArticleOutput.empty();
    }

    async searchArticles(input: SearchArticlesInput): Promise<SearchArticlesOutput> {
        try {
            const response: AxiosResponse<SearchArticlesOutputFields> = await axios.get("/api/v1/articles", {
                params: input.toPayloadHash(),
                headers: {Accept: "application/json"},
            });
            return new SearchArticlesOutput(response.data);
        } catch (e: unknown) {
            console.error("Error requesting data");
            if (axios.isAxiosError(e) && e.response) {
                return new SearchArticlesOutput(e.response.data);
            }
        }
        return SearchArticlesOutput.empty();
    }

    async editArticle(formData: FormData): Promise<BasicApiResult> {
        const postData: { [k: string]: string } = {};

        formData.forEach((value, key) => {
            if (typeof value === "string") {
                postData[key] = encodeURIComponent(value); // 文字列だけを追加
            }
        });

        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post("/api/v1/articles", JSON.stringify(postData), {
                headers: {'Content-Type': 'application/json;charset=utf-8',},
            });
            return new BasicApiResult(response.data);
        } catch (e: unknown) {
            console.error("Error submitting form");
            if (axios.isAxiosError(e) && e.response) {
                return new BasicApiResult(e.response.data);
            }
        }
        return BasicApiResult.empty();
    }

    async visitArticle(input: VisitArticleInput): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post(
                "/api/v1/articles/" + input.getArticleId() + "/visit",
                {}, {
                    headers: {'Content-Type': 'application/json;charset=utf-8',},
                });
            return new BasicApiResult(response.data);
        } catch (e: unknown) {
            console.error("Error submitting form");
            if (axios.isAxiosError(e) && e.response) {
                return new BasicApiResult(e.response.data);
            }
        }
        return BasicApiResult.empty();
    }

    async convertImportedText(input: ConvertImportedTextInput): Promise<ConvertImportedTextOutput> {
        try {
            const response: AxiosResponse<ConvertImportedTextOutputFields> = await axios.post(
                "/api/v1/articles/convertsourcecomments",
                input.toPayloadHash(), {
                    headers: {'Content-Type': 'application/json;charset=utf-8',},
                });
            return new ConvertImportedTextOutput(response.data);
        } catch (e: unknown) {
            console.error("Error submitting form");
            if (axios.isAxiosError(e) && e.response) {
                return new ConvertImportedTextOutput(e.response.data);
            }
        }
        return ConvertImportedTextOutput.empty();
    }
}