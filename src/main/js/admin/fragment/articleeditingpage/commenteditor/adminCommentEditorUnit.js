import React from 'react';
import {IdsEditingForm} from '../../atomic/idsEditingForm';
import {TextEditingForm} from '../../atomic/textEditingForm';
import {BooleanSelectingForm} from '../../atomic/booleanSelectingForm';
import {TagSelectingForm} from '../../atomic/tagselectingform/tagSelectingForm';
import {TagUnits} from '../../atomic/tagselectingform/tagUnits';

export class AdminCommentEditorUnit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            //this.props.postKey : form component name
            //this.props.content : CommentUnitList
            //this.props.countryTagOptions : TagUnits
        };
    }

    componentDidMount() {
    }

    render() {
        return (
            <div className="frame-unit">
                <div className="flex-container">
                    <p className="item-title">Comment Id</p>
                    <IdsEditingForm
                        postKey={this.props.postKey.append("commentId")}
                        ids={this.props.content.commentId}
                    />
                </div>
                <div className="flex-container">
                    <p className="item-title">Country</p>
                    <TagSelectingForm
                        postKey={this.props.postKey.append("country")}
                        candidates={this.props.countryTagOptions ? this.props.countryTagOptions : new TagUnits()}
                        selectedTagIds={this.props.content.country}
                    />
                </div>
                <div className="flex-container">
                    <p className="item-title">is Hidden</p>
                    <BooleanSelectingForm postKey={this.props.postKey.append("isHidden")}>
                        {this.props.content.isHidden}
                    </BooleanSelectingForm>
                </div>
                <div className="flex-container">
                    <p className="item-title">Referring Ids</p>
                    <IdsEditingForm
                        postKey={this.props.postKey.append("referringCommentIds")}
                        ids={this.props.content.referringIds}
                    />
                </div>
                <div>
                    <p className="item-title">Text</p>
                    <TextEditingForm
                        postKey={this.props.postKey.append("text")}
                        isLarge={true}
                    >
                        {this.props.content.text}
                    </TextEditingForm>
                </div>
            </div>
        );
    }
}

