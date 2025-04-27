import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ArticleSummaryList} from "../../../domain/articlelist/articleSummaryList";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {PublicArticleHeadlinesIndividualSmall} from "./publicArticleHeadlinesIndividualSmall";
import {PublicArticleHeadlinesIndividualLarge} from "./publicArticleHeadlinesIndividualLarge";


export enum HeadlinesMode {
    SMALL,
    LARGE
}

type PublicArticleHeadlinesProps = {
    mode: HeadlinesMode;
    articles: ArticleSummaryList;
    directoryToIndividualPage: string;
    reRender: boolean;
}


//articles : ArticleSummaryList
export const PublicArticleHeadlines: React.FC<PublicArticleHeadlinesProps> = ({
                                                                                  mode,
                                                                                  articles,
                                                                                  directoryToIndividualPage,
                                                                                  reRender
                                                                              }) => {


    if (mode === HeadlinesMode.SMALL) {
        return (
            <div className="headlines-section"><span>Headlines</span>
                {
                    articles.getElements().map((article: ArticleSummary) => (
                        <div key="key">
                            <PublicArticleHeadlinesIndividualSmall
                                article={article}
                                directoryToIndividualPage={directoryToIndividualPage}
                                hasTagClickable={false}
                            /></div>
                    ))}
            </div>
        );
    }
    if (mode === HeadlinesMode.LARGE) {
        return (
            <div className="headlines-section"><span>Headlines</span>
                {
                    articles.getElements().map((article: ArticleSummary) => (
                        <div key="key">
                            <PublicArticleHeadlinesIndividualLarge
                                article={article}
                                directoryToIndividualPage={directoryToIndividualPage}
                            /></div>
                    ))}
            </div>
        );
    }
};
