import {SearchTagsInput} from "./searchTagsInput";
import {SearchTagsOutput, SearchTagsOutputFields} from "./searchTagsOutput";
import axios from "axios";
import {AdminLoginInput} from "../../admin/fragment/login/adminLoginInput";
import {AdminLoginOutput} from "../../admin/fragment/login/adminLoginOutput";

export class AuthService {

    async loginAdmin(input: AdminLoginInput): Promise<AdminLoginOutput> {
        const response = await axios.post(
            "/api/v1/auth/login", {
                params: input.toPayloadHash(),
                headers: {'Content-Type': 'application/json',},
            }
        );
        console.log(response.data);
        return new SearchTagsOutput(response.data as SearchTagsOutputFields);
    }

}