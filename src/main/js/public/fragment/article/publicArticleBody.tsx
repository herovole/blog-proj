import React from 'react';
import {TagSelectingForm} from "../../../admin/fragment/atomic/tagselectingform/tagSelectingForm";
import {PublicUserCommentForm} from "./usercomment/publicUserCommentForm";
import {IdsEditingForm} from "../../../admin/fragment/atomic/idsEditingForm";
import {ImageSelectingModal} from "../../../admin/fragment/image/imageSelectingModal";
import {PublicUserCommentView} from "./usercomment/publicUserCommentView";
import {TagUnits} from "../../../admin/fragment/atomic/tagselectingform/tagUnits";
import {ElementId} from "../../../domain/elementId/elementId";
import {Article} from "../../../domain/article";
import {PublicSourceCommentView} from "./sourcecomment/publicSourceCommentView";


type PublicArticleBodyProps = {
    postKey: ElementId;
    article: Article;
    topicTagOptions: TagUnits;
    countryTagOptions: TagUnits;
}

export const PublicArticleBody: React.FC<PublicArticleBodyProps> = ({
                                                                        postKey,
                                                                        article,
                                                                        topicTagOptions,
                                                                        countryTagOptions
                                                                    }) => {
    const [refresh, setRefresh] = React.useState(false);

    const reRender = () => {
        setRefresh(r => !r);
    }

    return (
        <div className="flex-container">
            <div>
                <div className="flex-container">
                    <p className="item-title">{article.title}</p>
                </div>
                <div className="flex-container">
                    <ImageSelectingModal
                        postKey={postKey.append("imageName")}
                        imageName={article.imageName}
                    />
                </div>
                <div className="flex-container">
                    <p className="item-title">Article ID</p>
                    <IdsEditingForm
                        postKey={postKey.append("id")}
                        ids={article.articleId}
                        isFixed={true}
                    />
                </div>
                <div>
                    <p className="item-title-large">Source Info</p>
                    <p>
                        <span className="item-title">URL</span>
                        <span>{article.sourceUrl}</span>
                    </p>
                    <p>
                        <span className="item-title">Title</span>
                        <span>{article.sourceTitle}</span>
                    </p>
                    <p>
                        <span className="item-title">Date</span>
                        <span>{article.sourceDate}</span>
                    </p>
                </div>
                <div>
                    <p className="item-title">Topic Tags</p>
                    <TagSelectingForm
                        allowsMultipleOptions={true}
                        postKey={postKey.append("topicTags")}
                        candidates={topicTagOptions || TagUnits.empty()}
                        selectedTagIds={article.topicTags}
                        isFixed={true}
                    />
                </div>
                <div>
                    <p className="item-title">Countries</p>
                    <TagSelectingForm
                        allowsMultipleOptions={true}
                        postKey={postKey.append("countries")}
                        candidates={countryTagOptions || TagUnits.empty()}
                        selectedTagIds={article.countries}
                        isFixed={true}
                    />
                </div>
                <div>
                    <div>{article.text}</div>
                </div>
                <div>
                    <p className="item-title-large">Original Comments</p>
                    <PublicSourceCommentView
                        postKey={postKey.append("originalComments")}
                        commentUnits={article.originalComments}
                        countryTagsOptions={countryTagOptions}
                    />
                </div>
                <div>
                    <PublicUserCommentView
                        postKey={postKey.append("userComments")}
                        commentUnits={article.userComments}
                    />
                </div>
                <div>
                    <PublicUserCommentForm
                        postKey={postKey.append("userCommentForm")}
                        articleId={article.articleId}
                        functionToRerenderParent={reRender}
                    />
                </div>
            </div>
        </div>
    );
}

