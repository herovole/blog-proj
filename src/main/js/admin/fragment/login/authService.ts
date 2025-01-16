import axios from "axios";
import {AdminLoginInput} from "./adminLoginInput";
import {AdminLoginOutput, AdminLoginOutputFields} from "./adminLoginOutput";

export class AuthService {

    async loginAdmin(input: AdminLoginInput): Promise<AdminLoginOutput> {
        const response = await axios.post(
            "/api/v1/auth/login", {
                params: input.toPayloadHash(),
                headers: {'Content-Type': 'application/json',},
            }
        );
        console.log(response.data);
        return new AdminLoginOutput(response.data as AdminLoginOutputFields);
    }

}