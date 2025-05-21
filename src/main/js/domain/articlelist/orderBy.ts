export enum OrderByEnum {
    NONE = 0,
    ARTICLE_ID = 1,
    REGISTRATION_TIMESTAMP = 2,
    LATEST_COMMENT_TIMESTAMP = 3
}

export class OrderBy {
    static fromSignature(signature: string): OrderBy {
        switch (signature) {
            case "none":
                return new OrderBy(OrderByEnum.NONE);
            case "id":
                return new OrderBy(OrderByEnum.ARTICLE_ID);
            case "registration":
                return new OrderBy(OrderByEnum.REGISTRATION_TIMESTAMP);
            case "comment":
                return new OrderBy(OrderByEnum.LATEST_COMMENT_TIMESTAMP);
            default:
                return new OrderBy(OrderByEnum.NONE);
        }

    }

    static getTagOptionsJapanese(): ReadonlyArray<{ [key: string]: string }> {
        return [new OrderBy(OrderByEnum.REGISTRATION_TIMESTAMP).getTagOptionJapanese()];
    }

    static getTagOptionsJapaneseSelected(selectedValue: string): ReadonlyArray<{ [key: string]: string }> {
        return [OrderBy.fromSignature(selectedValue).getTagOptionJapanese()];
    }

    static getTagOptionsJapaneseAdmin(): ReadonlyArray<{ [key: string]: string }> {
        return [
            new OrderBy(OrderByEnum.NONE).getTagOptionJapanese(),
            new OrderBy(OrderByEnum.ARTICLE_ID).getTagOptionJapanese(),
            new OrderBy(OrderByEnum.REGISTRATION_TIMESTAMP).getTagOptionJapanese(),
            new OrderBy(OrderByEnum.LATEST_COMMENT_TIMESTAMP).getTagOptionJapanese()
        ];
    }

    classification: OrderByEnum;

    constructor(
        classification: OrderByEnum
    ) {
        this.classification = classification;
    }

    getSignature(): string {
        switch (this.classification) {
            case OrderByEnum.NONE:
                return "none";
            case OrderByEnum.ARTICLE_ID:
                return "id";
            case OrderByEnum.REGISTRATION_TIMESTAMP:
                return "registration";
            case OrderByEnum.LATEST_COMMENT_TIMESTAMP:
                return "comment";
            default:
                return "none";
        }
    }

    getJapaneseLabel(): string {
        switch (this.classification) {
            case OrderByEnum.NONE:
                return "（なし）";
            case OrderByEnum.ARTICLE_ID:
                return "通番";
            case OrderByEnum.REGISTRATION_TIMESTAMP:
                return "最新記事";
            case OrderByEnum.LATEST_COMMENT_TIMESTAMP:
                return "最新コメント";
            default:
                return "none";
        }
    }

    getTagOptionJapanese(): { [key: string]: string } {
        return {value: this.getSignature(), label: this.getJapaneseLabel()};
    }

}