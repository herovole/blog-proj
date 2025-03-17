import axios, {AxiosResponse} from "axios";
import {AdminLoginInput} from "./adminLoginInput";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {CreateAdminUserInput} from "./createAdminUserInput";
import {SearchAdminUserOutput, SearchAdminUserOutputFields} from "./searchAdminUserOutput";
import {SearchAdminUserInput} from "./searchAdminUserInput";

export class AuthService {

    async loginAdminPhase1(input: AdminLoginInput): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post(
                "/api/v1/auth/login",
                input.toPayloadHash(),
                {
                    headers: {'Content-Type': 'application/json;charset=utf-8',},
                }
            );
            return new BasicApiResult(response.data);
        } catch (e: unknown) {
            console.error("Error submitting form");
            if (axios.isAxiosError(e) && e.response) {
                return new BasicApiResult(e.response.data);
            }
        }
        return BasicApiResult.empty();
    }

    async validateToken(): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post(
                "/api/v1/auth/validate",
                {},
                {
                    headers: {'Content-Type': 'application/json;charset=utf-8',},
                }
            );
            return new BasicApiResult(response.data);
        } catch (e: unknown) {
            console.error("Error submitting form");
            if (axios.isAxiosError(e) && e.response) {
                return new BasicApiResult(e.response.data);
            }
        }
        return BasicApiResult.empty();
    }

    async searchAdminUser(input: SearchAdminUserInput): Promise<SearchAdminUserOutput> {
        try {
            const response: AxiosResponse<SearchAdminUserOutputFields> = await axios.get(
                "/api/v1/auth/adminuser", {
                    params: input.toPayloadHash(),
                    headers: {Accept: 'application/json',},
                }
            );
            return new SearchAdminUserOutput(response.data);
        } catch (e: unknown) {
            console.error("Error requesting data");
            if (axios.isAxiosError(e) && e.response) {
                return new SearchAdminUserOutput(e.response.data);
            }
        }
        return SearchAdminUserOutput.empty();

    }

    async createAdminUser(input: CreateAdminUserInput): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post(
                "/api/v1/auth/adminuser",
                input.toPayloadHash(),
                {
                    headers: {'Content-Type': 'application/json;charset=utf-8',},
                }
            );
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