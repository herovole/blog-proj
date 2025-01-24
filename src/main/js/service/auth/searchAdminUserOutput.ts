import {BasicApiResult, BasicApiResultFields} from "../../domain/basicApiResult";

export interface SearchAdminUserOutputFields extends BasicApiResultFields {
    content: {
        users: ReadonlyArray<{ id:number, name:string, role:string, timestampLastLogin:string }>,
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

    getUsers= (): ReadonlyArray<{ id:number, name:string, role:string, timestampLastLogin:string }> => {
        if (!this.fields) return [];
        const fields: SearchAdminUserOutputFields = this.fields as SearchAdminUserOutputFields;
        return fields.content.users;
    }
}