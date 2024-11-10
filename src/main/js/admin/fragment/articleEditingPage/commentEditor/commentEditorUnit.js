import React from 'react';
import { TextEditingForm } from '../../atomic/plain/textEditingForm';
import { BooleanSelectingForm } from '../../atomic/plain/booleanSelectingForm';
import { CountrySelectBox } from '../../atomic/countrySelectBox';

export class CommentEditorUnit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.content : CommentUnitList
        };
    }

    componentDidMount() { }

    render() {
            return (
                <div class="frame-unit">
                    <div class="flex-container">
                        <p class="item-title">Comment Id</p>
                        <TextEditingForm type="id" postKey={this.props.postKey.append("commentId")}>
                            {this.props.content.commentId}
                        </TextEditingForm>
                    </div>
                    <div class="flex-container">
                        <p class="item-title">Country</p>
                        <CountrySelectBox postKey={this.props.postKey.append("country")}>
                            {this.props.content.country}
                        </CountrySelectBox>
                    </div>
                    <div class="flex-container">
                        <p class="item-title">is Hidden</p>
                        <BooleanSelectingForm postKey={this.props.postKey.append("isHidden")}>
                            {this.props.content.isHidden}
                        </BooleanSelectingForm>
                    </div>
                    <div class="flex-container">
                        <p class="item-title">Referring Ids</p>
                        <TextEditingForm type="id" postKey={this.props.postKey.append("referringId")}>
                            {JSON.stringify(this.props.content.referringIds)}
                        </TextEditingForm>
                    </div>
                    <div>
                        <p class="item-title">Text</p>
                        <TextEditingForm postKey={this.props.postKey.append("text")}>
                            {this.props.content.text}
                        </TextEditingForm>
                    </div>
                </div>
            );
    }
}

