import React from 'react';
import axios from 'axios';
import {TagSelectingForm} from "../../../admin/fragment/atomic/tagselectingform/tagSelectingForm";
import {CommentEditor} from "../../../admin/fragment/articleeditingpage/commenteditor/commentEditor";
import {UserCommentForm} from "./usercomment/userCommentForm";
import {IdsEditingForm} from "../../../admin/fragment/atomic/idsEditingForm";
import {ImageSelectingModal} from "../../../admin/fragment/image/imageSelectingModal";
import {UserCommentViewer} from "./usercomment/userCommentViewer";
import {TagUnitList} from "../../../admin/fragment/atomic/tagselectingform/tagUnitList";

//  postKey : ElementId
//  article : Article
//  topicTagOptions, : TagUnitList
//  countryTagOptions : TagUnitList
export const ArticleViewBody = ({postKey, article, topicTagOptions, countryTagOptions}) => {
    const [refresh, setRefresh] = React.useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault(); // Prevent page reload
        const formData = new FormData(event.target);
        const postData = Object.fromEntries(formData.entries());

        try {
            const response = await axios.post("/api/v1/articles", postData, {
                headers: {'Content-Type': 'application/json',},
            });

            const data = await response.json();
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

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
                        candidates={topicTagOptions || new TagUnitList()}
                        selectedTagIds={article.topicTags}
                        isFixed={true}
                    />
                </div>
                <div>
                    <p className="item-title">Countries</p>
                    <TagSelectingForm
                        allowsMultipleOptions={true}
                        postKey={postKey.append("countries")}
                        candidates={countryTagOptions || new TagUnitList()}
                        selectedTagIds={article.countries}
                        isFixed={true}
                    />
                </div>
                <div>
                    <div>{article.text}</div>
                </div>
                <div>
                    <p className="item-title-large">Original Comments</p>
                    <CommentEditor
                        postKey={postKey.append("originalComments")}
                        content={article.originalComments}
                        countryTagOptions={countryTagOptions}
                    />
                </div>
                <div>
                    <UserCommentViewer
                        postKey={postKey.append("userComments")}
                        commentUnitList={article.userComments}
                    />
                </div>
                <div>
                    <UserCommentForm
                        postKey={postKey.append("userCommentForm")}
                        articleId={article.articleId}
                        functionToRerenderParent={reRender}
                    />
                </div>
            </div>
        </div>
    );
}

