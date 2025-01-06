import {CommentUnits} from "./comment/commentUnits";

export interface Article {
    articleId: number;
    imageName: string;
    title: string;
    text: string;
    sourceUrl: string;
    sourceTitle: string;
    sourceDate: string;
    isPublished: string;
    countries: ReadonlyArray<string>;
    topicTags: ReadonlyArray<string>;
    editors: ReadonlyArray<number>;
    originalComments: CommentUnits;
    userComments: CommentUnits;
    registrationTimestamp: string;
    latestEditTimestamp: string;
}
/*

export class Article {

    static readonly API_KEY_ROOT: string = "article";
    static readonly API_KEY_ID: string = "articleId";
    static readonly API_KEY_IMAGE: string = "imageName";
    static readonly API_KEY_TITLE: string = "title";
    static readonly API_KEY_TEXT: string = "text";
    static readonly API_KEY_COUNTRIES: string = "countries";
    static readonly API_KEY_TOPIC_TAGS: string = "topicTags";
    static readonly API_KEY_SOURCE_URL: string = "sourceUrl";
    static readonly API_KEY_SOURCE_TITLE: string = "sourceTitle";
    static readonly API_KEY_SOURCE_DATE: string = "sourceDate";
    static readonly API_KEY_IS_PUBLISHED: string = "isPublished";
    static readonly API_KEY_EDITORS: string = "editors";
    static readonly API_KEY_ORIGINAL_COMMENTS: string = "sourceComments";
    static readonly API_KEY_USER_COMMENTS: string = "userComments";
    static readonly API_KEY_REGISTRATION_TIMESTAMP: string = "registrationTimestamp";
    static readonly API_KEY_LATEST_EDIT_TIMESTAMP: string = "latestEditTimestamp";

    static fromHash(hash: { [key: string]: any }) {
        console.log("ARTICLE DATA" + JSON.stringify(hash));
        const content = hash[Article.API_KEY_ROOT];
        console.log("ARTICLE ID " + content[Article.API_KEY_ID]);
        return new Article(
            content[Article.API_KEY_ID],
            content[Article.API_KEY_IMAGE],
            content[Article.API_KEY_TITLE],
            content[Article.API_KEY_TEXT],
            content[Article.API_KEY_SOURCE_URL],
            content[Article.API_KEY_SOURCE_TITLE],
            content[Article.API_KEY_SOURCE_DATE],
            content[Article.API_KEY_IS_PUBLISHED],
            content[Article.API_KEY_COUNTRIES],
            content[Article.API_KEY_TOPIC_TAGS],
            content[Article.API_KEY_EDITORS],
            CommentUnits.fromHashAsSourceCommentUnits(content[Article.API_KEY_ORIGINAL_COMMENTS]),
            CommentUnits.fromHashAsUserCommentUnits(content[Article.API_KEY_USER_COMMENTS]),
            content[Article.API_KEY_REGISTRATION_TIMESTAMP],
            content[Article.API_KEY_LATEST_EDIT_TIMESTAMP]
        );
    }

    articleId: number;
    imageName: string;
    title: string;
    text: string;
    sourceUrl: string;
    sourceTitle: string;
    sourceDate: string;
    isPublished: string;
    countries: ReadonlyArray<string>;
    topicTags: ReadonlyArray<string>;
    editors: ReadonlyArray<number>;
    originalComments: CommentUnits;
    userComments: CommentUnits;
    registrationTimestamp: string;
    latestEditTimestamp: string;

    constructor(
        articleId: number,
        imageName: string, title: string, text: string,
        sourceUrl: string, sourceTitle: string, sourceDate: string,
        isPublished: string,
        countries: ReadonlyArray<string>,
        topicTags: ReadonlyArray<string>,
        editors: ReadonlyArray<number>,
        originalComments: CommentUnits,
        userComments: CommentUnits,
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
        this.originalComments = originalComments;
        this.userComments = userComments;
        this.registrationTimestamp = registrationTimestamp;
        this.latestEditTimestamp = latestEditTimestamp;
    }

}

 */