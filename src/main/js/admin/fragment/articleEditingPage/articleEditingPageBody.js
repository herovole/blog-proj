import React from 'react';
import axios from 'axios';

import { TextEditingForm } from '../atomic/plain/textEditingForm';
import { ImageSelectingForm } from '../atomic/plain/imageSelectingForm';
import { DateSelectingForm } from '../atomic/plain/dateSelectingForm';
import { BooleanSelectingForm } from '../atomic/plain/booleanSelectingForm';
import { CommentEditor } from './commentEditor/commentEditor';

export class ArticleEditingPageBody extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.content : Article
        };
    }

    componentDidMount() { }

    handleSubmit = async (event) => {
        event.preventDefault(); // Prevent page reload
        var formData = new FormData(event.target);
        var formObj = Object.fromEntries(formData.entries());

        try {
            const response = await axios.post("/b/api/article", {"input" : JSON.stringify(formObj)});

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
                                    postKey = {this.props.postKey.append("publish")}>
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
                                <p class="item-title">Article Image</p>
                                <ImageSelectingForm
                                    postKey = {this.props.postKey.append("img")}>
                                    {this.props.content.image}
                                </ImageSelectingForm>
                            </div>
                            <div>
                                <p class="item-title">Article Text</p>
                                <TextEditingForm
                                    postKey = {this.props.postKey.append("articleText")}>
                                    {this.props.content.text}
                                </TextEditingForm>
                            </div>
                            <div>
                                <p class="item-title-large">Original Comments</p>
                                <CommentEditor
                                    postKey = {this.props.postKey.append("commentEditor")}
                                    content = {this.props.content.originalComments}
                                    />
                            </div>
                        </div>
                    </div>
                </form>
            );
    }
}

