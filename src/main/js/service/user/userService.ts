import axios, {AxiosResponse} from "axios";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {PostUserCommentInput} from "./postUserCommentInput";
import {RateUserCommentInput} from "./rateUserCommentInput";
import {ReportUserCommentInput} from "./reportUserCommentInput";
import {SearchRatingHistoryOutput, SearchRatingHistoryOutputFields} from "./searchRatingHistoryOutput";
import {SearchRatingHistoryInput} from "./searchRatingHistoryInput";
import {SearchCommentsOutput, SearchCommentsOutputFields} from "./searchCommentsOutput";
import {SearchCommentsInput} from "./searchCommentsInput";

export class UserService {

    async postUserComment(input: PostUserCommentInput): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post("/api/v1/usercomments", input.toPayloadHash(), {
                headers: {'Content-Type': 'application/json',},
            });
            console.log(response.data);
            return new BasicApiResult(response.data);
        } catch (e: unknown) {
            console.error("Error submitting form");
            if (axios.isAxiosError(e) && e.response) {
                return new BasicApiResult(e.response.data);
            }
        }
        return BasicApiResult.empty();
    }

    async searchRatingHistory(input: SearchRatingHistoryInput): Promise<SearchRatingHistoryOutput> {
        try {
            const response: AxiosResponse<SearchRatingHistoryOutputFields> = await axios.get(
                "/api/v1/usercomments/ratings", {
                    params: input.toPayloadHash(),
                    headers: {Accept: 'application/json',},
                }
            );
            console.log(response.data);
            return new SearchRatingHistoryOutput(response.data);
        } catch (e: unknown) {
            console.error("Error submitting form");
            if (axios.isAxiosError(e) && e.response) {
                return new SearchRatingHistoryOutput(e.response.data);
            }
        }
        return SearchRatingHistoryOutput.empty();
    }

    async rateUserComment(input: RateUserCommentInput): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post(input.buildUrl(), input.toPayloadHash(), {
                headers: {'Content-Type': 'application/json',},
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

    async reportUserComment(input: ReportUserCommentInput): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post(input.buildUrl(), input.toPayloadHash(), {
                headers: {'Content-Type': 'application/json',},
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

    async searchComments(input: SearchCommentsInput): Promise<SearchCommentsOutput> {
        try {
            const response: AxiosResponse<SearchCommentsOutputFields> = await axios.get(
                "/api/v1/usercomments", {
                    params: input.toPayloadHash(),
                    headers: {Accept: 'application/json',},
                }
            );
            console.log(response.data);
            return new SearchCommentsOutput(response.data);
        } catch (e: unknown) {
            console.error("Error submitting form");
            if (axios.isAxiosError(e) && e.response) {
                return new SearchCommentsOutput(e.response.data);
            }
        }
        return SearchCommentsOutput.empty();
    }
}