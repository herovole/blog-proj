import {SearchTagsInput} from "./searchTagsInput";
import {SearchTagsOutput, SearchTagsOutputFields} from "./searchTagsOutput";
import axios from "axios";

export class TagService {

    async searchTopicTags(input: SearchTagsInput): Promise<SearchTagsOutput> {
        const topicResponse = await axios.get(
            "/api/v1/topicTags", {
                params: input.toPayloadHash(),
                headers: {Accept: 'application/json',},
            }
        );
        console.log(topicResponse.data);
        return new SearchTagsOutput(topicResponse.data as SearchTagsOutputFields);
    }

    async searchCountries(input: SearchTagsInput): Promise<SearchTagsOutput> {
        const countriesResponse = await axios.get(
            "/api/v1/countries", {
                params: input.toPayloadHash(),
                headers: {Accept: 'application/json',},
            }
        );
        console.log(countriesResponse.data);
        return new SearchTagsOutput(countriesResponse.data as SearchTagsOutputFields);
    }
}