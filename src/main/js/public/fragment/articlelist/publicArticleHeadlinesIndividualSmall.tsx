import {TagButtons} from "../../../admin/fragment/atomic/tagselectingform/tagButtons";
import React from "react";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {useNavigate} from "react-router-dom";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {ResourceManagement} from "../../../service/resourceManagement";

type PublicArticleHeadlinesIndividualSmallProps = {
    article: ArticleSummary;
    directoryToIndividualPage: string;
}

export const PublicArticleHeadlinesIndividualSmall: React.FC<PublicArticleHeadlinesIndividualSmallProps> = ({
                                                                                                                article,
                                                                                                                directoryToIndividualPage,
                                                                                                            }
) => {
    const LETTERS_PICKUP = 30;
    const navigate = useNavigate();
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());

    React.useEffect(() => {
        ResourceManagement.getInstance().getTopicTags().then(setTopicTagsOptions);
        ResourceManagement.getInstance().getCountryTags().then(setCountryTagsOptions);
    }, []);

    const goToIndividualPage = (articleId: number) => {
        navigate(directoryToIndividualPage + "/" + articleId);
    }

    return (
        <div key="" className="headline-item">
            <button className="headline-clickable" onClick={() => goToIndividualPage(article.articleId)}>
                {article.title ? article.title.slice(0, LETTERS_PICKUP) : ""}
            </button>
            <br/>
            <span className="small-memo">
                            <span>{article.countUserComments} Comments</span>
                            <TagButtons tagUnitList={topicTagsOptions} tagIds={article.topicTags}
                                        searchBaseUrl={directoryToIndividualPage}/>
                            <TagButtons tagUnitList={countryTagsOptions} tagIds={article.countries}
                                        searchBaseUrl={directoryToIndividualPage}/>
                        </span>
        </div>);
}