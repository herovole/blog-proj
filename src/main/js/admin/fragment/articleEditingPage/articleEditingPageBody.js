import React from 'react';

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

    render() {
            return (
                <div>
                    <div>
                        <p class="item-title">Article ID</p>
                        <TextEditingForm postKey = {this.props.postKey.append("id")} isFixed = {true}>

                        </TextEditingForm>
                    </div>
                    <div>
                        <p class="item-title">Article Image</p>
                        <ImageSelectingForm postKey = {this.props.postKey.append("img")}>

                        </ImageSelectingForm>
                    </div>
                    <div>
                        <p class="item-title">Article Text</p>
                        <TextEditingForm postKey = {this.props.postKey.append("articleText")}>

                        </TextEditingForm>
                    </div>
                    <div>
                        <p class="item-title">Date</p>
                        <DateSelectingForm postKey = {this.props.postKey.append("date")}>

                        </DateSelectingForm>
                    </div>
                    <div>
                        <p class="item-title">Published?</p>
                        <BooleanSelectingForm postKey = {this.props.postKey.append("publish")}>

                        </BooleanSelectingForm>
                    </div>
                    <div>
                        <p class="item-title">Editors</p>
                        <TextEditingForm postKey = {this.props.postKey.append("editors")} isFixed = {true}>

                        </TextEditingForm>
                    </div>
                    <div>
                        <p class="item-title-large">Original Comments</p>
                        <CommentEditor postKey = {this.props.postKey.append("commentEditor")}/>
                    </div>
                </div>
            );
    }
}

