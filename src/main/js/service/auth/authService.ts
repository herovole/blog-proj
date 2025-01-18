import axios, {AxiosResponse} from "axios";
import {AdminLoginInput} from "./adminLoginInput";
import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";

export class AuthService {

    async loginAdmin(input: AdminLoginInput): Promise<BasicApiResult> {
        const response: AxiosResponse<BasicApiResultFields> = await axios.post(
            "/api/v1/auth/login", {
                params: input.toPayloadHash(),
                headers: {'Content-Type': 'application/json',},
            }
        );
        console.log(response.data);
        return new BasicApiResult(response.data);
    }

}