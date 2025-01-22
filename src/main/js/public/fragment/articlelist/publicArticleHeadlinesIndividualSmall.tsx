import {TagButtons} from "../../../admin/fragment/atomic/tagselectingform/tagButtons";
import React from "react";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {useNavigate} from "react-router-dom";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";

type PublicArticleHeadlinesIndividualSmallProps = {
    article: ArticleSummary;
    directoryToIndividualPage: string;
    topicTagList: TagUnits;
    countryTagList: TagUnits;
}

export const PublicArticleHeadlinesIndividualSmall: React.FC<PublicArticleHeadlinesIndividualSmallProps> = ({
                                                                                                                article,
                                                                                                                directoryToIndividualPage,
                                                                                                                topicTagList,
                                                                                                                countryTagList
                                                                                                            }
) => {
    const LETTERS_PICKUP = 30;
    const navigate = useNavigate();
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
                            <TagButtons tagUnitList={topicTagList} tagIds={article.topicTags}
                                        searchBaseUrl={directoryToIndividualPage}/>
                            <TagButtons tagUnitList={countryTagList} tagIds={article.countries}
                                        searchBaseUrl={directoryToIndividualPage}/>
                        </span>
        </div>);
}