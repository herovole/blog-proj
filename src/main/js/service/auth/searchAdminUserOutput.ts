import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";
import {UserFields} from "../../domain/userFields";

export interface SearchAdminUserOutputFields extends BasicApiResultFields {
    content: {
        users: ReadonlyArray<UserFields>,
        total: number
    }
}


export class SearchAdminUserOutput extends BasicApiResult {

    static empty(): SearchAdminUserOutput {
        return new SearchAdminUserOutput(null);
    }

    constructor(fields: SearchAdminUserOutputFields | null) {
        super(fields);
    }

    isEmpty = (): boolean => {
        return !this.fields;
    }

    getTotal = (): number => {
        if (!this.fields) return 0;
        const fields: SearchAdminUserOutputFields = this.fields as SearchAdminUserOutputFields;
        return fields.content.total;
    }

    getUsers = (): ReadonlyArray<UserFields> => {
        if (!this.fields) return [];
        const fields: SearchAdminUserOutputFields = this.fields as SearchAdminUserOutputFields;
        return fields.content.users;
    }
}