import {SearchImagesInput} from "./searchImagesInput";
import {SearchImagesOutput, SearchImagesOutputFields} from "./searchImagesOutput";
import axios, {AxiosResponse} from "axios";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {PostImageInput} from "./postImageInput";

export class ImageService {

    async searchImages(input: SearchImagesInput): Promise<SearchImagesOutput> {
        try {
            const topicResponse: AxiosResponse<SearchImagesOutputFields> = await axios.get(
                "/api/v1/images", {
                    params: input.toPayloadHash(),
                    headers: {Accept: 'application/json',},
                }
            );
            console.log(topicResponse.data);
            return new SearchImagesOutput(topicResponse.data);
        } catch (e: unknown) {
            console.error("Error requesting data");
            if (axios.isAxiosError(e) && e.response) {
                return new SearchImagesOutput(e.response.data);
            }
        }
        return SearchImagesOutput.empty();
    }

    async postImage(input: PostImageInput): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post(
                "/api/v1/images",
                input.toPayloadHash(),
                {
                    headers: {'Content-Type': 'application/json',},
                }
            );
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

}