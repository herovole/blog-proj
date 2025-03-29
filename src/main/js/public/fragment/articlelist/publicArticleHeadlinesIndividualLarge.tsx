import {TagButtons} from "../../../admin/fragment/atomic/tagselectingform/tagButtons";
import React, {useState} from "react";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {useNavigate} from "react-router-dom";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {ResourceManagement} from "../../../service/resourceManagement";
import {YyyyMMDd} from "../../../domain/yyyyMMDd";

type PublicArticleHeadlinesIndividualLargeProps = {
    article: ArticleSummary;
    directoryToIndividualPage: string;
}

export const PublicArticleHeadlinesIndividualLarge: React.FC<PublicArticleHeadlinesIndividualLargeProps> = ({
                                                                                                                article,
                                                                                                                directoryToIndividualPage,
                                                                                                            }
) => {
    const LETTERS_PICKUP = 30;
    const navigate = useNavigate();
    const [resourcePrefix, setResourcePrefix] = useState<string | null>(null);
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());

    React.useEffect(() => {
        ResourceManagement.getInstance().articlesImagePrefixWithSlash().then(setResourcePrefix);
        ResourceManagement.getInstance().getTopicTags().then(setTopicTagsOptions);
        ResourceManagement.getInstance().getCountryTags().then(setCountryTagsOptions);
    }, []);

    const goToIndividualPage = (articleId: number) => {
        navigate(directoryToIndividualPage + "/" + articleId);
    }

    if (!resourcePrefix || topicTagsOptions.isEmpty() || countryTagsOptions.isEmpty()) {
        return <div>loading...</div>;
    } else {
        return (
            <div key="" className="headline-item">
                <button className="headline-clickable" onClick={() => goToIndividualPage(article.articleId)}>
                    {article.title ? article.title.slice(0, LETTERS_PICKUP) : ""}
                    <br/>
                    <img className="article-image" src={resourcePrefix + article.imageName} alt={article.imageName}/>
                </button>
                <div className="article-text">{article.text}</div>
                <div className="article-tag-alignment">
                    <TagButtons tagUnitList={topicTagsOptions} tagIds={article.topicTags}
                                searchBaseUrl={directoryToIndividualPage}/>
                </div>
                <div className="article-tag-alignment">
                    <TagButtons tagUnitList={countryTagsOptions} tagIds={article.countries}
                                searchBaseUrl={directoryToIndividualPage}/>
                </div>
                <div className="article-source-url">引用元: {article.sourceUrl}</div>
                <div className="article-timestamp">引用元日付: {YyyyMMDd.valueOfYyyyMMDd(article.sourceDate).toYyyySlashMMSlashDd()}</div>
                <div className="article-timestamp">ブログ内掲載: {article.registrationTimestamp}</div>
            </div>);
    }
}