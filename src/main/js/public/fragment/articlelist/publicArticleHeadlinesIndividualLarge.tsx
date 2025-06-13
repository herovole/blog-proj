import {TagButtons} from "../../../admin/fragment/atomic/tagselectingform/tagButtons";
import React, {useState} from "react";
import {ArticleSummary} from "../../../domain/articlelist/articleSummary";
import {Link} from "react-router-dom";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {ResourceManagement} from "../../../service/resourceManagement";
import {YyyyMMDd} from "../../../domain/yyyyMMDd";
import {YyyyMMDdHhMmSs} from "../../../domain/yyyyMMDdHhMmSs";

type PublicArticleHeadlinesIndividualLargeProps = {
    article: ArticleSummary;
    directoryToIndividualPage: string;
}

export const PublicArticleHeadlinesIndividualLarge: React.FC<PublicArticleHeadlinesIndividualLargeProps> = ({
                                                                                                                article,
                                                                                                                directoryToIndividualPage,
                                                                                                            }
) => {
    const [resourcePrefix, setResourcePrefix] = useState<string | null>(null);
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());

    React.useEffect(() => {
        ResourceManagement.getInstance().articlesImagePrefixWithSlash().then(setResourcePrefix);
        ResourceManagement.getInstance().getTopicTags().then(setTopicTagsOptions);
        ResourceManagement.getInstance().getCountryTags().then(setCountryTagsOptions);
    }, []);


    if (!resourcePrefix || topicTagsOptions.isEmpty() || countryTagsOptions.isEmpty()) {
        return <div>loading...</div>;
    } else {
        return (
            <div key="" className="headline-item">
                <Link className="headline-clickable-large" to={directoryToIndividualPage + "/" + article.articleId}>
                    {article.title ? article.title : ""}
                    <br/>
                    <img className="article-image" src={resourcePrefix + article.imageName} alt={article.imageName}/>
                </Link>
                <div className="article-text">{article.text}</div>
                <div className="article-tag-alignment">
                    <TagButtons tagUnitList={topicTagsOptions} tagIds={article.topicTags}
                                searchBaseUrl={directoryToIndividualPage} searchKey="topicTagId"/>
                </div>
                <div className="article-tag-alignment">
                    <TagButtons tagUnitList={countryTagsOptions} tagIds={article.countries}
                                searchBaseUrl={directoryToIndividualPage} searchKey="country"/>
                </div>
                <div className="article-source-url">引用元: {article.sourceUrl}</div>
                <div className="article-timestamp">引用元日付: {YyyyMMDd.valueOfYyyyMMDd(article.sourceDate).toYyyySlashMMSlashDd()}</div>
                <div className="article-timestamp">ブログ内掲載: {YyyyMMDdHhMmSs.valueOfYyyyMMDdHhMmSs(article.registrationTimestamp).toYyyySlashMMSlashDdSpaceHhColonMm()}</div>
            </div>);
    }
}