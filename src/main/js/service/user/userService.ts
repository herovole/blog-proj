import axios, {AxiosResponse} from "axios";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {PostUserCommentInput} from "./postUserCommentInput";
import {RateUserCommentInput} from "./rateUserCommentInput";
import {ReportUserCommentInput} from "./reportUserCommentInput";

export class UserService {

    async postUserComment(input: PostUserCommentInput): Promise<BasicApiResult | undefined> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post("/api/v1/usercomments", input.toPayloadHash(), {
                headers: {'Content-Type': 'application/json',},
            });
            console.log(response.data);
            return new BasicApiResult(response.data);
        } catch (e: unknown) {
            if (axios.isAxiosError(e) && e.response) {
                return new BasicApiResult(e.response.data);
            }
        }
    }

    async rateUserComment(input: RateUserCommentInput): Promise<BasicApiResult> {
        const response: AxiosResponse<BasicApiResultFields> = await axios.post(input.buildUrl(), input.toPayloadHash(), {
            headers: {'Content-Type': 'application/json',},
        });
        return new BasicApiResult(response.data);
    }

    async reportUserComment(input: ReportUserCommentInput): Promise<BasicApiResult> {
        const response: AxiosResponse<BasicApiResultFields> = await axios.post(input.buildUrl(), input.toPayloadHash(), {
            headers: {'Content-Type': 'application/json',},
        });
        return new BasicApiResult(response.data);
    }
}