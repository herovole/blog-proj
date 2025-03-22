import {CommentUnit} from "./comment/commentUnit";

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
    sourceComments: ReadonlyArray<CommentUnit>;
    userComments: ReadonlyArray<CommentUnit>;
    registrationTimestamp: string;
    latestEditTimestamp: string;
}