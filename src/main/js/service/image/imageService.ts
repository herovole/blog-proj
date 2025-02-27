import {SearchImagesInput} from "./searchImagesInput";
import {SearchImagesOutput, SearchImagesOutputFields} from "./searchImagesOutput";
import axios, {AxiosResponse} from "axios";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {PostImageInput} from "./postImageInput";
import {RemoveImageInput} from "./removeImageInput";
import {GetResourcePrefixOutput, GetResourcePrefixOutputFields} from "./getResourcePrefixOutput";

export class ImageService {

    async getResourcePrefix(): Promise<GetResourcePrefixOutput> {
        try {
            const response: AxiosResponse<GetResourcePrefixOutputFields> = await axios.get(
                "/api/v1/images/prefix", {
                    params: {},
                    headers: {Accept: 'application/json',},
                }
            );
            console.log(response.data);
            return new GetResourcePrefixOutput(response.data);
        } catch (e: unknown) {
            console.error("Error requesting data");
            if (axios.isAxiosError(e) && e.response) {
                return new GetResourcePrefixOutput(e.response.data);
            }
        }
        return GetResourcePrefixOutput.empty();
    }

    async searchImages(input: SearchImagesInput): Promise<SearchImagesOutput> {
        try {
            const response: AxiosResponse<SearchImagesOutputFields> = await axios.get(
                "/api/v1/images", {
                    params: input.toPayloadHash(),
                    headers: {Accept: 'application/json',},
                }
            );
            console.log(response.data);
            return new SearchImagesOutput(response.data);
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
                    headers: {'Content-Type': 'multipart/form-data'},
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

    async removeImage(input: RemoveImageInput): Promise<BasicApiResult> {

        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.delete(
                "/api/v1/images", {
                    data: input.toPayloadHash(),
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
    };

}