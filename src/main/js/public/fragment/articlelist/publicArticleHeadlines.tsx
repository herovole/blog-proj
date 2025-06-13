import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {ArticleSummaryList} from "../../../domain/articlelist/articleSummaryList";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {PublicArticleHeadlinesIndividualSmall} from "./publicArticleHeadlinesIndividualSmall";
import {PublicArticleHeadlinesIndividualLarge} from "./publicArticleHeadlinesIndividualLarge";
import {PublicArticleHeadlinesIndividualImage} from "./publicArticleHeadlinesIndividualImage";


export enum HeadlinesMode {
    HOME,
    LINE,
    IMAGE
}

type PublicArticleHeadlinesProps = {
    mode: HeadlinesMode;
    articles: ArticleSummaryList;
    directoryToIndividualPage: string;
}


//articles : ArticleSummaryList
export const PublicArticleHeadlines: React.FC<PublicArticleHeadlinesProps> = ({
                                                                                  mode,
                                                                                  articles,
                                                                                  directoryToIndividualPage
                                                                              }) => {


    if (mode === HeadlinesMode.LINE) {
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
    if (mode === HeadlinesMode.HOME) {
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
    if (mode === HeadlinesMode.IMAGE) {
        return (
            <div className="flex-container">
                {
                    articles.getElements().map((article: ArticleSummary) => (
                        <div key="key">
                            <PublicArticleHeadlinesIndividualImage
                                article={article}
                                directoryToIndividualPage={directoryToIndividualPage}
                            /></div>
                    ))}
            </div>
        );
    }

};
