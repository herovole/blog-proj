import {TagButtons} from "../../../admin/fragment/atomic/tagselectingform/tagButtons";
import React from "react";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {useNavigate} from "react-router-dom";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {ResourcePrefix} from "../../../service/image/resourcePrefix";

type PublicArticleHeadlinesIndividualLargeProps = {
    article: ArticleSummary;
    directoryToIndividualPage: string;
    topicTagList: TagUnits;
    countryTagList: TagUnits;
}

export const PublicArticleHeadlinesIndividualLarge: React.FC<PublicArticleHeadlinesIndividualLargeProps> = ({
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

    ResourcePrefix.getInstance().articlesWithSlash().then(resourcePrefix => {
        return (
            <div key="" className="headline-item">
                <button className="headline-clickable" onClick={() => goToIndividualPage(article.articleId)}>
                    {article.title ? article.title.slice(0, LETTERS_PICKUP) : ""}
                    <br/>
                    <img className="article-image" src={resourcePrefix + article.imageName} alt={article.imageName}/>
                </button>
                <div className="article-text">{article.text}</div>
                <div className="article-tag-alignment">
                    <TagButtons tagUnitList={topicTagList} tagIds={article.topicTags}
                                searchBaseUrl={directoryToIndividualPage}/>
                </div>
                <div className="article-tag-alignment">
                    <TagButtons tagUnitList={countryTagList} tagIds={article.countries}
                                searchBaseUrl={directoryToIndividualPage}/>
                </div>
                <div className="article-source-url">引用元: {article.sourceUrl}, {article.sourceDate}</div>
                <div className="article-timestamp">ブログ内掲載: {article.registrationTimestamp}</div>
            </div>);
    });
    return <div>Loading...</div>
}