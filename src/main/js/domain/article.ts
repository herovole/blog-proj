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