import React, {RefObject, useEffect, useState} from 'react';
import {PublicUserCommentForm} from "./usercomment/publicUserCommentForm";
import {PublicUserCommentView} from "./usercomment/publicUserCommentView";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {ElementId} from "../../../domain/elementId/elementId";
import {Article} from "../../../domain/article";
import {PublicSourceCommentView} from "./sourcecomment/publicSourceCommentView";
import {TagButtons} from "../../../admin/fragment/atomic/tagselectingform/tagButtons";
import {SearchRatingHistoryOutput} from "../../../service/user/searchRatingHistoryOutput";
import {ResourcePrefix} from "../../../service/image/resourcePrefix";


type PublicArticleBodyProps = {
    postKey: ElementId;
    article: Article;
    topicTagOptions: TagUnits;
    countryTagOptions: TagUnits;
    ratingHistory: SearchRatingHistoryOutput;
    reRender: () => void;
}

export const PublicArticleBody: React.FC<PublicArticleBodyProps> = ({
                                                                        postKey,
                                                                        article,
                                                                        topicTagOptions,
                                                                        countryTagOptions,
                                                                        ratingHistory,
                                                                        reRender
                                                                    }) => {
    const [resourcePrefix, setResourcePrefix] = useState<string | null>(null);
    const refText: RefObject<HTMLTextAreaElement | null> = React.useRef(null);
    const directoryToIndividualPage: string = "undefined";


    useEffect(() => {
        ResourcePrefix.getInstance().articlesWithSlash().then(setResourcePrefix);
    }, []);

    const handleReference = (commentIdReferred: number) => {
        if (refText.current) {
            refText.current.focus();
            refText.current.value += ">>" + commentIdReferred.toString();
        }
    }

    return (
        <div>
            <h2 className="article-title">{article.title}</h2>
            <img className="article-image" src={resourcePrefix + article.imageName} alt="not rendered"/>
            <div className="article-text">{article.text}</div>
            <div className="article-tag-alignment">
                <TagButtons tagUnitList={topicTagOptions} tagIds={article.topicTags}
                            searchBaseUrl={directoryToIndividualPage}/>
            </div>
            <div className="article-tag-alignment">
                <TagButtons tagUnitList={countryTagOptions} tagIds={article.countries}
                            searchBaseUrl={directoryToIndividualPage}/>
            </div>
            <div className="article-source-url">引用元: {article.sourceUrl}, {article.sourceDate}</div>
            <div className="article-timestamp">ブログ内掲載: {article.registrationTimestamp}</div>


            <div>
                <PublicSourceCommentView
                    commentUnits={article.sourceComments}
                    countryTagsOptions={countryTagOptions}
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

