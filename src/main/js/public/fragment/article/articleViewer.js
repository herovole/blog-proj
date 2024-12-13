import React from 'react';
import axios from 'axios';

import { IdsEditingForm } from '../atomic/idsEditingForm';
import { TextEditingForm } from '../atomic/textEditingForm';
import { ImageSelectingModal } from '../image/imageSelectingModal';
import { DateSelectingForm } from '../atomic/dateSelectingForm';
import { BooleanSelectingForm } from '../atomic/booleanSelectingForm';
import { CommentEditor } from './commentEditor/commentEditor';
import { TagSelectingForm } from '../atomic/tagselectingform/tagSelectingForm';
import { TagUnitList } from '../atomic/tagselectingform/tagUnitList';

//  postKey : ElementId
//  article : Article
//  topicTagOptions, : TagUnitList
//  countryTagOptions : TagUnitList
export const ArticleViewer = ({postKey, article, topicTagOptions, countryTagOptions}) => {

    const handleSubmit = async (event) => {
        event.preventDefault(); // Prevent page reload
        const formData = new FormData(event.target);
        const postData = Object.fromEntries(formData.entries());

        try {
            const response = await axios.post("/api/v1/articles", postData, {
                headers: { 'Content-Type': 'application/json', },
            });

            const data = await response.json();
        } catch (error) {
            console.error('Error submitting form:', error);
        }
    };

    render() {
            return (
                    <div class="flex-container">
                        <div>
                            <div class="flex-container">
                                <p class="item-title">{article.title}</p>
                            </div>
                            <div class="flex-container">
                                <ImageSelectingModal
                                    postKey = {postKey.append("imageName")}
                                    imageName = {article.imageName}
                                />
                            </div>
                            <div class="flex-container">
                                <p class="item-title">Article ID</p>
                                <IdsEditingForm
                                    postKey = {this.props.postKey.append("id")}
                                    ids = {article.articleId}
                                    isFixed = {true}
                                />
                            </div>
                            <div>
                                <p class="item-title-large">Source Info</p>
                                <p>
                                    <span class="item-title">URL</span>
                                    <span>{article.sourceUrl}</span>
                                </p>
                                <p>
                                    <span class="item-title">Title</span>
                                    <span>{article.sourceTitle}</span>
                                </p>
                                <p>
                                    <span class="item-title">Date</span>
                                    <span>{article.sourceDate}</span>
                                </p>
                            </div>
                            <div>
                                <p class="item-title">Topic Tags</p>
                                <TagSelectingForm
                                  allowsMultipleOptions={true}
                                  postKey={this.props.postKey.append("topicTags")}
                                  candidates={this.props.topicTagOptions ? this.props.topicTagOptions : new TagUnitList()}
                                  selectedTagIds={article.topicTags}
                                  isFixed={true}
                                />
                            </div>
                            <div>
                                <p class="item-title">Countries</p>
                                <TagSelectingForm
                                  allowsMultipleOptions={true}
                                  postKey={this.props.postKey.append("countries")}
                                  candidates={this.props.countryTagOptions ? this.props.countryTagOptions : new TagUnitList()}
                                  selectedTagIds={article.countries}
                                  isFixed={true}
                                />
                            </div>
                            <div>
                                <div>{article.text}</div>
                            </div>
                            <div>
                                <p class="item-title-large">Original Comments</p>
                                <CommentEditor
                                    postKey = {this.props.postKey.append("originalComments")}
                                    content = {article.originalComments}
                                    countryTagOptions = {this.props.countryTagOptions}
                                    />
                            </div>
                            <div>
                                <UserCommentViewer
                                    postKey={this.props.postKey.append("userComments")}
                                    commentUnitList={article.userComments}
                                />
                            </div>
                            <div>
                                <UserCommentForm
                                    postKey={this.props.postKey.append("userCommentForm")}
                                    articleId={article.articleId}
                                />
                            </div>
                        </div>
                    </div>
            );
    }
}

