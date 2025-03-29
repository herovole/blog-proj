import React, {RefObject, useState} from 'react';
import {PublicUserCommentForm} from "./usercomment/publicUserCommentForm";
import {PublicUserCommentView} from "./usercomment/publicUserCommentView";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {ElementId} from "../../../domain/elementId/elementId";
import {Article} from "../../../domain/article";
import {PublicSourceCommentView} from "./sourcecomment/publicSourceCommentView";
import {TagButtons} from "../../../admin/fragment/atomic/tagselectingform/tagButtons";
import {SearchRatingHistoryOutput} from "../../../service/user/searchRatingHistoryOutput";
import {ResourceManagement} from "../../../service/resourceManagement";
import {DivText} from "../../../admin/fragment/atomic/divText";


type PublicArticleBodyProps = {
    postKey: ElementId;
    article: Article;
    ratingHistory: SearchRatingHistoryOutput;
    reRender: () => void;
}

export const PublicArticleBody: React.FC<PublicArticleBodyProps> = ({
                                                                        postKey,
                                                                        article,
                                                                        ratingHistory,
                                                                        reRender
                                                                    }) => {
    const refText: RefObject<HTMLTextAreaElement | null> = React.useRef(null);
    const directoryToIndividualPage: string = "undefined";

    const [resourcePrefix, setResourcePrefix] = useState<string | null>(null);
    const [topicTagsOptions, setTopicTagsOptions] = React.useState<TagUnits>(TagUnits.empty());
    const [countryTagsOptions, setCountryTagsOptions] = React.useState<TagUnits>(TagUnits.empty());

    React.useEffect(() => {
        ResourceManagement.getInstance().getTopicTags().then(setTopicTagsOptions);
        ResourceManagement.getInstance().getCountryTags().then(setCountryTagsOptions);
        ResourceManagement.getInstance().articlesImagePrefixWithSlash().then(setResourcePrefix);
    }, []);

    const handleReference = (commentIdReferred: number) => {
        if (refText.current) {
            refText.current.focus();
            refText.current.value += ">>" + commentIdReferred.toString();
        }
    }

    if (!resourcePrefix || topicTagsOptions.isEmpty() || countryTagsOptions.isEmpty()) {
        return <div>loading...</div>
    } else {
        return (
            <div>
                <h2 className="article-title">{article.title}</h2>
                <img className="article-image" src={resourcePrefix + article.imageName} alt="not rendered"/>
                <DivText className="article-text">{article.text}</DivText>
                <div className="article-tag-alignment">
                    <TagButtons tagUnitList={topicTagsOptions} tagIds={article.topicTags}
                                searchBaseUrl={directoryToIndividualPage}/>
                </div>
                <div className="article-tag-alignment">
                    <TagButtons tagUnitList={countryTagsOptions} tagIds={article.countries}
                                searchBaseUrl={directoryToIndividualPage}/>
                </div>
                <div className="article-source-url">引用元: {article.sourceUrl}</div>
                <div className="article-timestamp">ブログ内掲載: {article.registrationTimestamp}</div>


                <div>
                    <PublicSourceCommentView
                        commentUnits={article.sourceComments}
                        countryTagsOptions={countryTagsOptions}
                        handleReference={handleReference}
                    />
                </div>
                <div>
                    <PublicUserCommentView
                        commentUnits={article.userComments}
                        ratingHistory={ratingHistory}
                        handleReference={handleReference}
                    />
                </div>
                <div>
                    <PublicUserCommentForm
                        postKey={postKey.append("userCommentForm")}
                        articleId={article.articleId}
                        articleTitle={article.title}
                        refText={refText}
                        reRender={reRender}
                    />
                </div>
            </div>
        );
    }
}

