import {SearchTagsInput} from "./searchTagsInput";
import {SearchTagsOutput, SearchTagsOutputFields} from "./searchTagsOutput";
import axios, {AxiosResponse} from "axios";

export class TagService {

    async searchTopicTags(input: SearchTagsInput): Promise<SearchTagsOutput> {
        try {
            const topicResponse: AxiosResponse<SearchTagsOutputFields> = await axios.get(
                "/api/v1/topicTags", {
                    params: input.toPayloadHash(),
                    headers: {Accept: 'application/json',},
                }
            );
            console.log(topicResponse.data);
            return new SearchTagsOutput(topicResponse.data);
        } catch (e: unknown) {
            console.error("Error requesting data");
            if (axios.isAxiosError(e) && e.response) {
                return new SearchTagsOutput(e.response.data);
            }
        }
        return SearchTagsOutput.empty();
    }

    async searchCountries(input: SearchTagsInput): Promise<SearchTagsOutput> {
        try {
            const countriesResponse: AxiosResponse<SearchTagsOutputFields> = await axios.get(
                "/api/v1/countries", {
                    params: input.toPayloadHash(),
                    headers: {Accept: 'application/json',},
                }
            );
            console.log(countriesResponse.data);
            return new SearchTagsOutput(countriesResponse.data);
        } catch (e: unknown) {
            console.error("Error requesting data");
            if (axios.isAxiosError(e) && e.response) {
                return new SearchTagsOutput(e.response.data);
            }
        }
        return SearchTagsOutput.empty();
    }
}