import {TagButtons} from "../../../admin/fragment/atomic/tagselectingform/tagButtons";
import React from "react";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {Link} from "react-router-dom";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {ResourceManagement} from "../../../service/resourceManagement";

type PublicArticleHeadlinesIndividualSmallProps = {
    article: ArticleSummary;
    directoryToIndividualPage: string;
    hasTagClickable?: boolean;
}

export const PublicArticleHeadlinesIndividualSmall: React.FC<PublicArticleHeadlinesIndividualSmallProps> = ({
                                                                                                                article,
                                                                                                                directoryToIndividualPage,
                                                                                                                hasTagClickable = true,
                                                                                                            }
) => {
    const LETTERS_PICKUP = 30;
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());

    React.useEffect(() => {
        ResourceManagement.getInstance().getTopicTags().then(setTopicTagsOptions);
        ResourceManagement.getInstance().getCountryTags().then(setCountryTagsOptions);
    }, []);

    if (topicTagsOptions.isEmpty() || countryTagsOptions.isEmpty()) {
        return <div>loading...</div>;
    } else {
        return (
            <div key="" className="headline-item">
                <Link className="headline-clickable-small" to={directoryToIndividualPage + "/" + article.articleId}>
                    {article.title ? article.title.length > LETTERS_PICKUP ? article.title.slice(0, LETTERS_PICKUP) + "..." : article.title : ""}
                </Link>
                <br/>
                <span className="small-memo">
                            <span>{article.countUserComments} Comments</span>
                            <TagButtons tagUnitList={topicTagsOptions} tagIds={article.topicTags}
                                        searchBaseUrl={directoryToIndividualPage} searchKey={hasTagClickable ? "topicTagId" : null}/>
                            <TagButtons tagUnitList={countryTagsOptions} tagIds={article.countries}
                                        searchBaseUrl={directoryToIndividualPage} searchKey={hasTagClickable ? "country" : null}/>
                        </span>
            </div>);
    }
}