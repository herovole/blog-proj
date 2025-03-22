
export interface ArticleSummary {

    articleId: number;
    imageName: string;
    title: string;
    text: string;
    sourceUrl: string;
    sourceTitle: string;
    sourceDate: string;
    isPublished: boolean;
    countries: ReadonlyArray<string>;
    topicTags: ReadonlyArray<string>;
    editors: ReadonlyArray<number>;
    countSourceComments: number;
    countUserComments: number;
    registrationTimestamp: string;
    latestEditTimestamp: string

}