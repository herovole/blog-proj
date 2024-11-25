import React from 'react';
import { IdsEditingForm } from '../../atomic/idsEditingForm';
import { TextEditingForm } from '../../atomic/textEditingForm';
import { BooleanSelectingForm } from '../../atomic/booleanSelectingForm';
import { TagSelectingForm } from '../../atomic/tagselectingform/tagSelectingForm';
import { TagUnitList } from '../../atomic/tagselectingform/tagUnitList';

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
                        <IdsEditingForm
                          postKey={this.props.postKey.append("commentId")}
                          ids={this.props.content.commentId}
                        />
                    </div>
                    <div class="flex-container">
                        <p class="item-title">Country</p>
                        <TagSelectingForm
                          postKey={this.props.postKey.append("country")}
                          candidates={this.props.countryTagOptions ? this.props.countryTagOptions : new TagUnitList()}
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
                        <IdsEditingForm
                          postKey={this.props.postKey.append("referringCommentIds")}
                          ids={this.props.content.referringIds}
                        />
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

