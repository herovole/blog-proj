import axios, {AxiosResponse} from "axios";
import {AdminLoginInput} from "./adminLoginInput";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";

export class AuthService {

    async loginAdmin(input: AdminLoginInput): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post(
                "/api/v1/auth/login",
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

    async validateToken(): Promise<BasicApiResult> {
        try {
            const response: AxiosResponse<BasicApiResultFields> = await axios.post(
                "/api/v1/auth/validate",
                {},
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