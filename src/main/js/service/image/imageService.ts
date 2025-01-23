import {SearchImagesInput} from "./searchImagesInput";
import {SearchImagesOutput, SearchImagesOutputFields} from "./searchImagesOutput";
import axios, {AxiosResponse} from "axios";

export class ImageService {

    async searchImages(input: SearchImagesInput): Promise<SearchImagesOutput> {
        try {
            const topicResponse: AxiosResponse<SearchImagesOutputFields> = await axios.get(
                "/api/v1/topicImages", {
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

}