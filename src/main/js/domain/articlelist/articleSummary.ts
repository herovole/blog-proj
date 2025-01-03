import {GenericSwitch} from "../genericSwitch";

export class ArticleSummary {

    static readonly API_KEY_ID: string = "articleId";
    static readonly API_KEY_IMAGE: string = "imageName";
    static readonly API_KEY_TITLE: string = "title";
    static readonly API_KEY_TEXT: string = "text";
    static readonly API_KEY_SOURCE_URL: string = "sourceUrl";
    static readonly API_KEY_SOURCE_TITLE: string = "sourceTitle";
    static readonly API_KEY_SOURCE_DATE: string = "sourceDate";
    static readonly API_KEY_IS_PUBLISHED: string = "isPublished";
    static readonly API_KEY_COUNTRIES: string = "countries";
    static readonly API_KEY_TOPIC_TAGS: string = "topicTags";
    static readonly API_KEY_EDITORS: string = "editors";
    static readonly API_KEY_COUNT_ORIGINAL_COMMENTS: string = "sourceComments";
    static readonly API_KEY_COUNT_USER_COMMENTS: string = "userComments";
    static readonly API_KEY_REGISTRATION_TIMESTAMP: string = "registrationTimestamp";
    static readonly API_KEY_LATEST_EDIT_TIMESTAMP: string = "latestEditTimestamp";

    static fromHash(hash: { [key: string]: any }): ArticleSummary {
        return new ArticleSummary(
            Number(hash[ArticleSummary.API_KEY_ID]),
            hash[ArticleSummary.API_KEY_IMAGE],
            hash[ArticleSummary.API_KEY_TITLE],
            hash[ArticleSummary.API_KEY_TEXT],
            hash[ArticleSummary.API_KEY_SOURCE_URL],
            hash[ArticleSummary.API_KEY_SOURCE_TITLE],
            hash[ArticleSummary.API_KEY_SOURCE_DATE],
            new GenericSwitch(hash[ArticleSummary.API_KEY_IS_PUBLISHED]),
            hash[ArticleSummary.API_KEY_COUNTRIES],
            hash[ArticleSummary.API_KEY_TOPIC_TAGS],
            hash[ArticleSummary.API_KEY_EDITORS],
            hash[ArticleSummary.API_KEY_COUNT_ORIGINAL_COMMENTS],
            hash[ArticleSummary.API_KEY_COUNT_USER_COMMENTS],
            hash[ArticleSummary.API_KEY_REGISTRATION_TIMESTAMP],
            hash[ArticleSummary.API_KEY_LATEST_EDIT_TIMESTAMP]
        );
    }

    articleId: number;
    imageName: string;
    title: string;
    text: string;
    sourceUrl: string;
    sourceTitle: string;
    sourceDate: string;
    isPublished: GenericSwitch;
    countries: Array<string>;
    topicTags: Array<number>;
    editors: Array<number>;
    countOriginalComments: number;
    countUserComments: number;
    registrationTimestamp: string;
    latestEditTimestamp: string

    constructor(
        articleId: number,
        imageName: string, title: string, text: string,
        sourceUrl: string, sourceTitle: string, sourceDate: string,
        isPublished: GenericSwitch,
        countries: Array<string>,
        topicTags: Array<number>,
        editors: Array<number>,
        countOriginalComments: number,
        countUserComments: number,
        registrationTimestamp: string,
        latestEditTimestamp: string
    ) {
        this.articleId = articleId;
        this.imageName = imageName;
        this.title = title;
        this.text = text;
        this.sourceUrl = sourceUrl;
        this.sourceTitle = sourceTitle;
        this.sourceDate = sourceDate;
        this.isPublished = isPublished;
        this.countries = countries;
        this.topicTags = topicTags;
        this.editors = editors;
        this.countOriginalComments = countOriginalComments;
        this.countUserComments = countUserComments;
        this.registrationTimestamp = registrationTimestamp;
        this.latestEditTimestamp = latestEditTimestamp;
    }

    getSlicedTitle(lettersToPickUp) {
        return this.title ? this.title.slice(0, lettersToPickUp) : "";
    }

}