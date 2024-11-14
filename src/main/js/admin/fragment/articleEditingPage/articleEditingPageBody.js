import React from 'react';
import axios from 'axios';

import { TextEditingForm } from '../atomic/textEditingForm';
import { ImageSelectingModal } from '../image/imageSelectingModal';
import { DateSelectingForm } from '../atomic/dateSelectingForm';
import { BooleanSelectingForm } from '../atomic/booleanSelectingForm';
import { CommentEditor } from './commentEditor/commentEditor';
import { TagSelectingForm } from '../atomic/tagSelectingForm/tagSelectingForm';
import { TagUnitList } from '../atomic/tagSelectingForm/tagUnitList';

export class ArticleEditingPageBody extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.content : Article
            //this.props.topicTagOptions, : TagUnitList
            //this.props.countryTagOptions : TagUnitList
        };
    }

    componentDidMount() { }

    handleSubmit = async (event) => {
        event.preventDefault(); // Prevent page reload
        var formData = new FormData(event.target);

        try {
            const response = await axios.post("/b/api/article", formData, {
                headers: { 'Content-Type': 'multipart/form-data', },
            });

            const data = await response.json();
            setResponseMessage(data.message);
        } catch (error) {
            console.error('Error submitting form:', error);
            setResponseMessage('Failed to submit form');
        }
    };

    render() {
            return (
                <form onSubmit={this.handleSubmit}>
                    <div class="flex-container">
                        <button type="submit">Submit</button>
                        <div>
                            <div class="flex-container">
                                <p class="item-title">Article ID</p>
                                <TextEditingForm
                                    postKey = {this.props.postKey.append("id")}
                                    isFixed = {true}>
                                    {this.props.content.articleId}
                                </TextEditingForm>
                            </div>
                            <div class="flex-container">
                                <p class="item-title">Date</p>
                                <DateSelectingForm
                                    postKey = {this.props.postKey.append("date")}>
                                    {this.props.content.date}
                                </DateSelectingForm>
                            </div>
                            <div class="flex-container">
                                <p class="item-title">Published?</p>
                                <BooleanSelectingForm
                                    postKey = {this.props.postKey.append("isPublished")}>
                                    {this.props.content.isPublished}
                                </BooleanSelectingForm>
                            </div>
                            <div class="flex-container">
                                <p class="item-title">Editors</p>
                                <TextEditingForm
                                    postKey = {this.props.postKey.append("editors")}
                                    isFixed = {true}>
                                    {this.props.content.editors}
                                </TextEditingForm>
                            </div>
                            <div>
                                <TagSelectingForm
                                  postKey={this.props.postKey.append("country")}
                                  candidates={this.props.countryTagOptions ? this.props.countryTagOptions : new TagUnitList()}
                                  selectedTagIds={[]}
                                />
                            </div>
                            <div>
                                <p class="item-title">Article Text</p>
                                <TextEditingForm
                                    postKey = {this.props.postKey.append("articleText")}>
                                    {this.props.content.text}
                                </TextEditingForm>
                            </div>
                            <div>
                                <TagSelectingForm
                                  postKey={this.props.postKey.append("topicTags")}
                                  candidates={this.props.topicTagOptions ? this.props.topicTagOptions : new TagUnitList()}
                                  selectedTagIds={[]}
                                />
                            </div>
                            <div>
                                <p class="item-title-large">Original Comments</p>
                                <CommentEditor
                                    postKey = {this.props.postKey.append("originalComments")}
                                    content = {this.props.content.originalComments}
                                    countryTagOptions = {this.props.countryTagOptions}
                                    />
                            </div>
                        </div>
                    </div>
                </form>
            );
    }
}

