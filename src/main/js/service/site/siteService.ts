import axios, {AxiosResponse} from "axios";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {GenerateRss2Input} from "./generateRss2Input";

export class SiteService {

    async generateRss(input: GenerateRss2Input): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post(
                "/api/v1/site/rss2",
                input.toPayloadHash(), {
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

}