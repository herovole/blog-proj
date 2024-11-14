import React from 'react';
import { TextEditingForm } from '../../atomic/textEditingForm';
import { BooleanSelectingForm } from '../../atomic/booleanSelectingForm';
import { TagSelectingForm } from '../../atomic/tagSelectingForm/tagSelectingForm';
import { TagUnitList } from '../../atomic/tagSelectingForm/tagUnitList';

export class CommentEditorUnit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.content : CommentUnitList
            //this.props.countryTagOptions : TagUnitList
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
                        <TagSelectingForm
                          postKey={this.props.postKey.append("country")}
                          candidates={this.props.countryTagsOptions ? this.props.countryTagsOptions : new TagUnitList()}
                          selectedTagIds={this.props.content.country}
                        />
                    </div>
                    <div class="flex-container">
                        <p class="item-title">is Hidden</p>
                        <BooleanSelectingForm postKey={this.props.postKey.append("isHidden")}>
                            {this.props.content.isHidden}
                        </BooleanSelectingForm>
                    </div>
                    <div class="flex-container">
                        <p class="item-title">Referring Ids</p>
                        <TextEditingForm type="id" postKey={this.props.postKey.append("referringCommentIds")}>
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

