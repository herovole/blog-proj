import React from 'react';
import axios from 'axios';

import {IdsEditingForm} from '../atomic/idsEditingForm';
import {TextEditingForm} from '../atomic/textEditingForm';
import {ImageSelectingModal} from '../image/imageSelectingModal';
import {DateSelectingForm} from '../atomic/dateSelectingForm';
import {BooleanSelectingForm} from '../atomic/booleanSelectingForm';
import {AdminCommentEditor} from './commenteditor/adminCommentEditor';
import {TagSelectingForm} from '../atomic/tagselectingform/tagSelectingForm';
import {TagUnitList} from '../atomic/tagselectingform/tagUnitList';

export class AdminArticleBody extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.content : Article
            //this.props.topicTagOptions, : TagUnitList
            //this.props.countryTagOptions : TagUnitList
        };
    }

    componentDidMount() {
    }


    handleSubmit = async (event) => {
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

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <div className="flex-container">
                    <button type="submit">Submit</button>
                    <div>
                        <div className="flex-container">
                            <p className="item-title">Image</p>
                            <ImageSelectingModal
                                postKey={this.props.postKey.append("imageName")}
                                imageName={this.props.content.imageName}
                            />
                        </div>
                        <div className="flex-container">
                            <p className="item-title">Article ID</p>
                            <IdsEditingForm
                                postKey={this.props.postKey.append("articleId")}
                                ids={this.props.content.articleId}
                                isFixed={true}
                            />
                        </div>
                        <div>
                            <p className="item-title-large">Source Info</p>
                            <p>
                                <span className="item-title">URL</span>
                                <TextEditingForm
                                    postKey={this.props.postKey.append("sourceUrl")}>
                                    {this.props.content.sourceUrl}
                                </TextEditingForm>
                            </p>
                            <p>
                                <span className="item-title">Title</span>
                                <TextEditingForm
                                    postKey={this.props.postKey.append("sourceTitle")}>
                                    {this.props.content.sourceTitle}
                                </TextEditingForm>
                            </p>
                            <p>
                                <span className="item-title">Date</span>
                                <DateSelectingForm
                                    postKey={this.props.postKey.append("sourceDate")}>
                                    {this.props.content.sourceDate}
                                </DateSelectingForm>
                            </p>
                        </div>
                        <div className="flex-container">
                            <p className="item-title">Published?</p>
                            <BooleanSelectingForm
                                postKey={this.props.postKey.append("isPublished")}>
                                {this.props.content.isPublished}
                            </BooleanSelectingForm>
                        </div>
                        <div className="flex-container">
                            <p className="item-title">Editors</p>
                            <TagSelectingForm
                                allowsMultipleOptions={true}
                                postKey={this.props.postKey.append("editors")}
                                candidates={this.props.topicTagOptions ? this.props.topicTagOptions : new TagUnitList()}
                                isFixed={true}
                                selectedTagIds={[]}
                            />
                        </div>
                        <div>
                            <p className="item-title">Topic Tags</p>
                            <TagSelectingForm
                                allowsMultipleOptions={true}
                                postKey={this.props.postKey.append("topicTags")}
                                candidates={this.props.topicTagOptions ? this.props.topicTagOptions : new TagUnitList()}
                                selectedTagIds={this.props.content.topicTags}
                            />
                        </div>
                        <div>
                            <p className="item-title">Countries</p>
                            <TagSelectingForm
                                allowsMultipleOptions={true}
                                postKey={this.props.postKey.append("countries")}
                                candidates={this.props.countryTagOptions ? this.props.countryTagOptions : new TagUnitList()}
                                selectedTagIds={this.props.content.countries}
                            />
                        </div>
                        <div>
                            <p className="item-title">Article Title</p>
                            <TextEditingForm
                                postKey={this.props.postKey.append("articleTitle")}>
                                {this.props.content.title}
                            </TextEditingForm>
                        </div>
                        <div>
                            <p className="item-title">Article Text</p>
                            <TextEditingForm
                                postKey={this.props.postKey.append("articleText")}
                                isLarge={true}
                            >
                                {this.props.content.text}
                            </TextEditingForm>
                        </div>
                        <div>
                            <p className="item-title-large">Original Comments</p>
                            <AdminCommentEditor
                                postKey={this.props.postKey.append("originalComments")}
                                content={this.props.content.originalComments}
                                countryTagOptions={this.props.countryTagOptions}
                            />
                        </div>
                    </div>
                </div>
            </form>
        );
    }
}

