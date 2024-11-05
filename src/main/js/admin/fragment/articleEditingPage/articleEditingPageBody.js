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
            //this.props.content : CommentUnit
        };
    }

    componentDidMount() { }

    render() {
            return (
                <div>
                    <TextEditingForm postKey = "id" isFixed = {true}>

                    </TextEditingForm>
                    <ImageSelectingForm postKey = "img">

                    </ImageSelectingForm>
                    <TextEditingForm postKey = "articleText">

                    </TextEditingForm>
                    <DateSelectingForm postKey = "date">

                    </DateSelectingForm>
                    <BooleanSelectingForm postKey = "publish">

                    </BooleanSelectingForm>
                    <TextEditingForm postKey = "editors" isFixed = {true}>

                    </TextEditingForm>

                    <CommentEditor/>
                </div>
            );
    }
}

