export interface AdminLoginOutputFields {
    isSuccessful: boolean;
}

export class AdminLoginOutput {

    private readonly fields: AdminLoginOutputFields;

    constructor(fields: AdminLoginOutputFields) {
        this.fields = fields;
    }

    isSuccessful(): boolean {
        return this.fields.isSuccessful;
    }

}